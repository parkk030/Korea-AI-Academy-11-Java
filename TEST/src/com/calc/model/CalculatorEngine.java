package com.calc.model;

import com.calc.model.Operator.BinaryOp;
import com.calc.model.Operator.UnaryOp;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * BigDecimal 정밀 연산과 Java 21 패턴 매칭을 활용한 핵심 계산기 엔진
 */
public class CalculatorEngine {

    private static final MathContext MATH_CONTEXT = new MathContext(16, RoundingMode.HALF_UP);
    private static final int MAX_INPUT_DIGITS = 16;

    private String currentInput = "0";
    private BigDecimal storedOperand = null;
    private BinaryOp pendingOp = null;
    private BigDecimal lastRightOperand = null;
    private BinaryOp lastExecutedOp = null;

    private String expressionPreview = "";
    private boolean startNewInput = true;
    private boolean errorState = false;
    private String errorMessage = "";

    private BigDecimal memoryValue = BigDecimal.ZERO;
    private boolean memoryActive = false;

    private final List<HistoryItem> history = new ArrayList<>();

    /**
     * 숫자(0~9) 입력 처리
     */
    public void inputDigit(int digit) {
        if (digit < 0 || digit > 9) {
            throw new IllegalArgumentException("Digit must be between 0 and 9");
        }
        if (errorState) {
            clearAll();
        }
        if (startNewInput) {
            currentInput = String.valueOf(digit);
            startNewInput = false;
            if (pendingOp == null) {
                expressionPreview = "";
            }
            return;
        }
        if (countDigits(currentInput) >= MAX_INPUT_DIGITS) {
            return;
        }
        if ("0".equals(currentInput)) {
            currentInput = String.valueOf(digit);
        } else if ("-0".equals(currentInput)) {
            currentInput = "-" + digit;
        } else {
            currentInput += digit;
        }
    }

    /**
     * 소수점(.) 입력 처리
     */
    public void inputDecimalPoint() {
        if (errorState) {
            clearAll();
        }
        if (startNewInput) {
            currentInput = "0.";
            startNewInput = false;
            if (pendingOp == null) {
                expressionPreview = "";
            }
            return;
        }
        if (!currentInput.contains(".")) {
            currentInput += ".";
        }
    }

    /**
     * 백스페이스(한 글자 지우기) 처리
     */
    public void backspace() {
        if (errorState) {
            clearAll();
            return;
        }
        if (startNewInput) {
            return;
        }
        if (currentInput.length() <= 1 || (currentInput.length() == 2 && currentInput.startsWith("-"))) {
            currentInput = "0";
        } else {
            currentInput = currentInput.substring(0, currentInput.length() - 1);
        }
    }

    /**
     * 현재 입력 초기화 (CE)
     */
    public void clearEntry() {
        if (errorState) {
            clearAll();
            return;
        }
        currentInput = "0";
        startNewInput = true;
    }

    /**
     * 전체 상태 초기화 (C)
     */
    public void clearAll() {
        currentInput = "0";
        storedOperand = null;
        pendingOp = null;
        lastRightOperand = null;
        lastExecutedOp = null;
        expressionPreview = "";
        startNewInput = true;
        errorState = false;
        errorMessage = "";
    }

    /**
     * 사칙연산 이항 연산자 선택
     */
    public void applyBinaryOperator(BinaryOp op) {
        if (errorState) {
            return;
        }
        BigDecimal currentVal = parseCurrentInput();

        // 이미 연산자가 대기 중이고 새 숫자를 입력한 상태라면 중간 계산 수행
        if (pendingOp != null && !startNewInput && storedOperand != null) {
            CalculationResult midResult = computeBinary(storedOperand, currentVal, pendingOp);
            switch (midResult) {
                case CalculationResult.Success s -> {
                    storedOperand = s.value();
                    currentInput = s.value().stripTrailingZeros().toPlainString();
                }
                case CalculationResult.Error e -> {
                    setErrorState(e);
                    return;
                }
            }
        } else if (storedOperand == null || pendingOp == null) {
            storedOperand = currentVal;
        }

        pendingOp = op;
        lastRightOperand = null;
        lastExecutedOp = null;
        expressionPreview = formatNumber(storedOperand) + " " + op.symbol();
        startNewInput = true;
    }

    /**
     * 단항 연산자(%, 1/x, x², √x, ±) 즉시 실행
     */
    public CalculationResult applyUnaryOperator(UnaryOp op) {
        if (errorState) {
            clearAll();
        }
        BigDecimal currentVal = parseCurrentInput();

        if (op == UnaryOp.NEGATE) {
            if (currentVal.compareTo(BigDecimal.ZERO) != 0) {
                if (currentInput.startsWith("-")) {
                    currentInput = currentInput.substring(1);
                } else {
                    currentInput = "-" + currentInput;
                }
            }
            BigDecimal negated = parseCurrentInput();
            return new CalculationResult.Success(negated, getDisplayText(), expressionPreview);
        }

        String operandFormatted = formatNumber(currentVal);
        String unaryExpr = op.formatExpression(operandFormatted);

        CalculationResult result = switch (op) {
            case PERCENT -> {
                BigDecimal base = (storedOperand != null && (pendingOp == BinaryOp.ADD || pendingOp == BinaryOp.SUBTRACT))
                        ? storedOperand.multiply(currentVal, MATH_CONTEXT).divide(BigDecimal.valueOf(100), MATH_CONTEXT)
                        : currentVal.divide(BigDecimal.valueOf(100), MATH_CONTEXT);
                yield createSuccess(base, unaryExpr);
            }
            case RECIPROCAL -> {
                if (currentVal.compareTo(BigDecimal.ZERO) == 0) {
                    yield new CalculationResult.Error("0으로 나눌 수 없습니다", unaryExpr);
                }
                BigDecimal rec = BigDecimal.ONE.divide(currentVal, MATH_CONTEXT);
                yield createSuccess(rec, unaryExpr);
            }
            case SQUARE -> {
                BigDecimal sq = currentVal.multiply(currentVal, MATH_CONTEXT);
                yield createSuccess(sq, unaryExpr);
            }
            case SQRT -> {
                if (currentVal.compareTo(BigDecimal.ZERO) < 0) {
                    yield new CalculationResult.Error("잘못된 입력입니다 (음수 제곱근)", unaryExpr);
                }
                BigDecimal root = currentVal.sqrt(MATH_CONTEXT);
                yield createSuccess(root, unaryExpr);
            }
            case NEGATE -> createSuccess(currentVal.negate(), unaryExpr);
        };

        switch (result) {
            case CalculationResult.Success s -> {
                currentInput = toCleanPlainString(s.value());
                startNewInput = true;
                if (pendingOp == null) {
                    expressionPreview = s.expression() + " =";
                    history.addFirst(new HistoryItem(s.expression() + " =", s.formattedValue(), s.value()));
                } else {
                    expressionPreview = formatNumber(storedOperand) + " " + pendingOp.symbol() + " " + s.expression();
                }
            }
            case CalculationResult.Error e -> setErrorState(e);
        }
        return result;
    }

    /**
     * 등호(=) 계산 수행 (반복 등호 연산 포함)
     */
    public CalculationResult evaluate() {
        if (errorState) {
            clearAll();
            return new CalculationResult.Success(BigDecimal.ZERO, "0", "");
        }

        BigDecimal currentVal = parseCurrentInput();
        BigDecimal left;
        BigDecimal right;
        BinaryOp opToUse;

        if (pendingOp != null && storedOperand != null) {
            left = storedOperand;
            right = currentVal;
            opToUse = pendingOp;
            lastRightOperand = right;
            lastExecutedOp = opToUse;
            pendingOp = null;
        } else if (lastExecutedOp != null && lastRightOperand != null) {
            left = currentVal;
            right = lastRightOperand;
            opToUse = lastExecutedOp;
        } else {
            String formatted = formatNumber(currentVal);
            expressionPreview = formatted + " =";
            startNewInput = true;
            return new CalculationResult.Success(currentVal, formatted, expressionPreview);
        }

        CalculationResult result = computeBinary(left, right, opToUse);
        switch (result) {
            case CalculationResult.Success s -> {
                storedOperand = s.value();
                currentInput = toCleanPlainString(s.value());
                expressionPreview = s.expression();
                startNewInput = true;
                history.addFirst(new HistoryItem(s.expression(), s.formattedValue(), s.value()));
            }
            case CalculationResult.Error e -> setErrorState(e);
        }
        return result;
    }

    private CalculationResult computeBinary(BigDecimal left, BigDecimal right, BinaryOp op) {
        String expr = formatNumber(left) + " " + op.symbol() + " " + formatNumber(right) + " =";
        try {
            BigDecimal computed = switch (op) {
                case ADD -> left.add(right, MATH_CONTEXT);
                case SUBTRACT -> left.subtract(right, MATH_CONTEXT);
                case MULTIPLY -> left.multiply(right, MATH_CONTEXT);
                case DIVIDE -> {
                    if (right.compareTo(BigDecimal.ZERO) == 0) {
                        yield null;
                    }
                    yield left.divide(right, MATH_CONTEXT);
                }
            };
            if (computed == null) {
                return new CalculationResult.Error("0으로 나눌 수 없습니다", expr);
            }
            return createSuccess(computed, expr);
        } catch (ArithmeticException ex) {
            return new CalculationResult.Error("연산 범위 초과", expr);
        }
    }

    private CalculationResult.Success createSuccess(BigDecimal raw, String expr) {
        BigDecimal normalized = raw.stripTrailingZeros();
        if (normalized.compareTo(BigDecimal.ZERO) == 0) {
            normalized = BigDecimal.ZERO;
        }
        return new CalculationResult.Success(normalized, formatNumber(normalized), expr);
    }

    private void setErrorState(CalculationResult.Error error) {
        this.errorState = true;
        this.errorMessage = error.message();
        this.expressionPreview = error.expression();
        this.pendingOp = null;
        this.storedOperand = null;
        this.startNewInput = true;
    }

    // ================= 메모리 연산 (MC, MR, M+, M-, MS) =================

    public void memoryClear() {
        memoryValue = BigDecimal.ZERO;
        memoryActive = false;
    }

    public void memoryRecall() {
        if (!memoryActive) {
            return;
        }
        if (errorState) {
            clearAll();
        }
        currentInput = toCleanPlainString(memoryValue);
        startNewInput = true;
    }

    public void memoryStore() {
        if (errorState) {
            return;
        }
        memoryValue = parseCurrentInput();
        memoryActive = true;
        startNewInput = true;
    }

    public void memoryAdd() {
        if (errorState) {
            return;
        }
        memoryValue = memoryValue.add(parseCurrentInput(), MATH_CONTEXT).stripTrailingZeros();
        memoryActive = true;
        startNewInput = true;
    }

    public void memorySubtract() {
        if (errorState) {
            return;
        }
        memoryValue = memoryValue.subtract(parseCurrentInput(), MATH_CONTEXT).stripTrailingZeros();
        memoryActive = true;
        startNewInput = true;
    }

    public boolean hasMemory() {
        return memoryActive;
    }

    public BigDecimal getMemoryValue() {
        return memoryValue;
    }

    // ================= 히스토리 관리 =================

    public List<HistoryItem> getHistory() {
        return Collections.unmodifiableList(history);
    }

    public void clearHistory() {
        history.clear();
    }

    public void recallFromHistory(HistoryItem item) {
        if (item == null) {
            return;
        }
        if (errorState) {
            clearAll();
        }
        currentInput = toCleanPlainString(item.rawValue());
        expressionPreview = item.expression();
        startNewInput = true;
    }

    // ================= 조회 및 포맷팅 유틸리티 =================

    public boolean isErrorState() {
        return errorState;
    }

    public String getExpressionPreview() {
        return expressionPreview;
    }

    public String getDisplayText() {
        if (errorState) {
            return errorMessage;
        }
        if (!startNewInput && currentInput.contains(".")) {
            int dotIndex = currentInput.indexOf('.');
            String intPart = currentInput.substring(0, dotIndex);
            String decPart = currentInput.substring(dotIndex);
            try {
                BigDecimal intBd = new BigDecimal(intPart);
                String formattedInt = formatIntegerPart(intBd);
                if (intPart.startsWith("-") && intBd.compareTo(BigDecimal.ZERO) == 0) {
                    formattedInt = "-0";
                }
                return formattedInt + decPart;
            } catch (NumberFormatException ignored) {
                return currentInput;
            }
        }
        return formatNumber(parseCurrentInput());
    }

    public BigDecimal parseCurrentInput() {
        try {
            return new BigDecimal(currentInput);
        } catch (NumberFormatException e) {
            return BigDecimal.ZERO;
        }
    }

    public static String formatNumber(BigDecimal value) {
        if (value == null) {
            return "0";
        }
        BigDecimal stripped = value.stripTrailingZeros();
        if (stripped.compareTo(BigDecimal.ZERO) == 0) {
            return "0";
        }
        double absVal = Math.abs(stripped.doubleValue());
        if ((absVal >= 1e15 || (absVal > 0 && absVal < 1e-9))) {
            DecimalFormat sciFormat = new DecimalFormat("0.########E0");
            return sciFormat.format(stripped);
        }
        DecimalFormat df = new DecimalFormat("#,##0.############");
        return df.format(stripped);
    }

    private static String formatIntegerPart(BigDecimal value) {
        DecimalFormat df = new DecimalFormat("#,##0");
        return df.format(value);
    }

    private static String toCleanPlainString(BigDecimal value) {
        BigDecimal stripped = value.stripTrailingZeros();
        if (stripped.compareTo(BigDecimal.ZERO) == 0) {
            return "0";
        }
        return stripped.toPlainString();
    }

    private static int countDigits(String text) {
        int count = 0;
        for (int i = 0; i < text.length(); i++) {
            if (Character.isDigit(text.charAt(i))) {
                count++;
            }
        }
        return count;
    }
}
