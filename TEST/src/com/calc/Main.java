package com.calc;

import com.calc.ui.CalculatorFrame;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Java 21 GUI 계산기 애플리케이션 메인 진입점
 */
public final class Main {

    private Main() {
        // 인스턴스화 방지
    }

    public static void main(String[] args) {
        // 고해상도 디스플레이 및 폰트 안티앨리어싱 활성화
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
            // 기본 LookAndFeel 유지
        }

        SwingUtilities.invokeLater(() -> {
            CalculatorFrame frame = new CalculatorFrame();
            frame.setVisible(true);
        });
    }
}
