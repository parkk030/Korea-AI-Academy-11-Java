package com.calc.model;

/**
 * 계산기 이항 및 단항 연산자 정의 (Java 21 Enum & Switch Expression 활용)
 */
public final class Operator {

    private Operator() {
    }

    /**
     * 사칙연산 이항 연산자
     */
    public enum BinaryOp {
        ADD("+"),
        SUBTRACT("-"),
        MULTIPLY("×"),
        DIVIDE("÷");

        private final String symbol;

        BinaryOp(String symbol) {
            this.symbol = symbol;
        }

        public String symbol() {
            return symbol;
        }
    }

    /**
     * 단항 과학/보조 연산자
     */
    public enum UnaryOp {
        PERCENT("%"),
        RECIPROCAL("1/x"),
        SQUARE("x²"),
        SQRT("√x"),
        NEGATE("±");

        private final String label;

        UnaryOp(String label) {
            this.label = label;
        }

        public String label() {
            return label;
        }

        public String formatExpression(String operandText) {
            return switch (this) {
                case PERCENT -> operandText + "%";
                case RECIPROCAL -> "1/(" + operandText + ")";
                case SQUARE -> "sqr(" + operandText + ")";
                case SQRT -> "√(" + operandText + ")";
                case NEGATE -> "negate(" + operandText + ")";
            };
        }
    }
}
