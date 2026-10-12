package com.calc;

import com.calc.model.CalculationResult;
import com.calc.model.CalculatorEngine;
import com.calc.model.Operator.BinaryOp;
import com.calc.model.Operator.UnaryOp;
import com.calc.ui.CalculatorFrame;
import com.calc.ui.ThemePalette;

import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.math.BigDecimal;

/**
 * Java 21 계산기 엔진 자동 검증 테스트 러너 및 UI 스냅샷 검증기
 */
public final class CalculatorEngineTest {

    private static int passed = 0;
    private static int failed = 0;

    private CalculatorEngineTest() {
    }

    public static void main(String[] args) throws Exception {
        System.out.println("===================================================");
        System.out.println("   Java 21 Smart Calculator - 자동 단위 테스트 실행");
        System.out.println("===================================================");

        testBasicAdditionAndSubtraction();
        testFloatingPointPrecision();
        testChainedAndRepeatedOperations();
        testDivisionByZeroAndRecovery();
        testUnaryOperations();
        testMemoryAndHistory();

        if (args.length > 0 && "--capture-ui".equals(args[0]) && args.length > 1) {
            captureUiScreenshots(args[1]);
        }

        System.out.println("---------------------------------------------------");
        System.out.printf("테스트 결과: 통과 %d건 / 실패 %d건%n", passed, failed);
        System.out.println("===================================================");

        if (failed > 0) {
            System.exit(1);
        }
    }

    private static void testBasicAdditionAndSubtraction() {
        CalculatorEngine engine = new CalculatorEngine();
        enterNumber(engine, "1250");
        engine.applyBinaryOperator(BinaryOp.ADD);
        enterNumber(engine, "3750");
        CalculationResult res = engine.evaluate();

        assertResultSuccess(res, new BigDecimal("5000"), "5,000", "기본 덧셈 및 천 단위 포맷팅 (1250 + 3750)");
    }

    private static void testFloatingPointPrecision() {
        CalculatorEngine engine = new CalculatorEngine();
        engine.inputDigit(0);
        engine.inputDecimalPoint();
        engine.inputDigit(1);
        engine.applyBinaryOperator(BinaryOp.ADD);
        engine.inputDigit(0);
        engine.inputDecimalPoint();
        engine.inputDigit(2);
        CalculationResult res = engine.evaluate();

        assertResultSuccess(res, new BigDecimal("0.3"), "0.3", "BigDecimal 부동소수점 정밀 연산 (0.1 + 0.2 == 0.3)");
    }

    private static void testChainedAndRepeatedOperations() {
        CalculatorEngine engine = new CalculatorEngine();
        enterNumber(engine, "10");
        engine.applyBinaryOperator(BinaryOp.ADD);
        enterNumber(engine, "5");
        engine.evaluate(); // 15
        engine.evaluate(); // 20
        CalculationResult res = engine.evaluate(); // 25

        assertResultSuccess(res, new BigDecimal("25"), "25", "반복 등호(=) 연속 연산 (10 + 5 = = = -> 25)");
    }

    private static void testDivisionByZeroAndRecovery() {
        CalculatorEngine engine = new CalculatorEngine();
        enterNumber(engine, "42");
        engine.applyBinaryOperator(BinaryOp.DIVIDE);
        engine.inputDigit(0);
        CalculationResult res = engine.evaluate();

        switch (res) {
            case CalculationResult.Error err -> {
                assertTrue(engine.isErrorState(), "0으로 나누기 시 에러 상태 전환 확인 (" + err.message() + ")");
            }
            case CalculationResult.Success s -> fail("0으로 나누기가 실패해야 하나 성공함: " + s);
        }

        // 에러 상태에서 새 숫자 입력 시 자동 복구 확인
        engine.inputDigit(7);
        assertTrue(!engine.isErrorState() && "7".equals(engine.getDisplayText()), "에러 상태 이후 숫자 입력 시 정상 복구 확인");
    }

    private static void testUnaryOperations() {
        CalculatorEngine engine = new CalculatorEngine();
        enterNumber(engine, "81");
        CalculationResult sqrtRes = engine.applyUnaryOperator(UnaryOp.SQRT);
        assertResultSuccess(sqrtRes, new BigDecimal("9"), "9", "단항 제곱근 연산 (√81 = 9)");

        CalculationResult sqRes = engine.applyUnaryOperator(UnaryOp.SQUARE);
        assertResultSuccess(sqRes, new BigDecimal("81"), "81", "단항 제곱 연산 (9² = 81)");
    }

    private static void testMemoryAndHistory() {
        CalculatorEngine engine = new CalculatorEngine();
        enterNumber(engine, "100");
        engine.memoryStore();
        enterNumber(engine, "50");
        engine.memoryAdd();
        engine.memoryRecall();

        assertTrue("150".equals(engine.getDisplayText()), "메모리 저장(MS), 더하기(M+), 불러오기(MR) 검증 (100 + 50 = 150)");
        engine.memoryClear();
        assertTrue(!engine.hasMemory(), "메모리 초기화(MC) 검증");
    }

    private static void captureUiScreenshots(String outputDir) throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            try {
                File dir = new File(outputDir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                CalculatorFrame frame = new CalculatorFrame();
                frame.handleCommand("1", false);
                frame.handleCommand("2", false);
                frame.handleCommand("5", false);
                frame.handleCommand("0", false);
                frame.handleCommand("+", false);
                frame.handleCommand("3", false);
                frame.handleCommand("7", false);
                frame.handleCommand("5", false);
                frame.handleCommand("=", false);

                frame.handleCommand("MS", false);
                frame.handleCommand("9", false);
                frame.handleCommand("x²", false);

                // 1. 다크 모드 기본 화면 캡처
                frame.addNotify();
                frame.setSize(new Dimension(CalculatorFrame.DEFAULT_WIDTH, CalculatorFrame.DEFAULT_HEIGHT));
                layoutRecursively(frame.getContentPane());
                saveComponentImage(frame.getContentPane(), CalculatorFrame.DEFAULT_WIDTH, CalculatorFrame.DEFAULT_HEIGHT, new File(dir, "calculator_dark.png"));

                // 2. 라이트 모드 + 히스토리 패널 확장 화면 캡처
                frame.applyTheme(ThemePalette.LIGHT);
                java.lang.reflect.Method toggleMethod = CalculatorFrame.class.getDeclaredMethod("toggleHistorySidebar");
                toggleMethod.setAccessible(true);
                toggleMethod.invoke(frame);
                frame.setSize(new Dimension(CalculatorFrame.EXPANDED_WIDTH, CalculatorFrame.DEFAULT_HEIGHT));
                layoutRecursively(frame.getContentPane());
                saveComponentImage(frame.getContentPane(), CalculatorFrame.EXPANDED_WIDTH, CalculatorFrame.DEFAULT_HEIGHT, new File(dir, "calculator_light_history.png"));

                frame.dispose();
                System.out.println("[PASS] GUI 다크/라이트 테마 및 히스토리 렌더링 스냅샷 생성 완료");
                passed++;
            } catch (Exception e) {
                e.printStackTrace();
                failed++;
            }
        });
    }

    private static void layoutRecursively(java.awt.Component comp) {
        if (comp instanceof java.awt.Container container) {
            container.doLayout();
            for (java.awt.Component child : container.getComponents()) {
                layoutRecursively(child);
            }
        }
    }

    private static void saveComponentImage(java.awt.Component comp, int w, int h, File targetFile) throws Exception {
        comp.setSize(w, h);
        layoutRecursively(comp);
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();
        comp.printAll(g2);
        g2.dispose();
        ImageIO.write(image, "png", targetFile);
    }

    private static void enterNumber(CalculatorEngine engine, String digits) {
        for (char c : digits.toCharArray()) {
            if (c == '.') {
                engine.inputDecimalPoint();
            } else if (Character.isDigit(c)) {
                engine.inputDigit(c - '0');
            }
        }
    }

    private static void assertResultSuccess(CalculationResult res, BigDecimal expectedVal, String expectedFormatted, String label) {
        switch (res) {
            case CalculationResult.Success s -> {
                if (s.value().compareTo(expectedVal) == 0 && expectedFormatted.equals(s.formattedValue())) {
                    System.out.println("[PASS] " + label + " -> " + s.formattedValue());
                    passed++;
                } else {
                    fail(label + " (기대값: " + expectedFormatted + ", 실제값: " + s.formattedValue() + ")");
                }
            }
            case CalculationResult.Error err -> fail(label + " (에러 발생: " + err.message() + ")");
        }
    }

    private static void assertTrue(boolean condition, String label) {
        if (condition) {
            System.out.println("[PASS] " + label);
            passed++;
        } else {
            fail(label);
        }
    }

    private static void fail(String label) {
        System.err.println("[FAIL] " + label);
        failed++;
    }
}
