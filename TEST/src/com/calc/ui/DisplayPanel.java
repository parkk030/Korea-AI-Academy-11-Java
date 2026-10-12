package com.calc.ui;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * 수식 미리보기, 메모리 상태 배지, 메인 결과값을 표시하는 계산기 상단 디스플레이 패널
 */
public class DisplayPanel extends JPanel {

    private final JLabel memoryBadgeLabel;
    private final JLabel expressionLabel;
    private final JLabel mainValueLabel;
    private ThemePalette palette = ThemePalette.DARK;

    public DisplayPanel() {
        setOpaque(false);
        setLayout(new BorderLayout(4, 6));
        setBorder(BorderFactory.createEmptyBorder(14, 18, 14, 18));
        setPreferredSize(new Dimension(360, 125));

        JPanel topRow = new JPanel(new BorderLayout(8, 0));
        topRow.setOpaque(false);

        memoryBadgeLabel = new JLabel("");
        memoryBadgeLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 12));
        memoryBadgeLabel.setVisible(false);

        expressionLabel = new JLabel(" ", SwingConstants.RIGHT);
        expressionLabel.setFont(new Font("Malgun Gothic", Font.PLAIN, 15));

        topRow.add(memoryBadgeLabel, BorderLayout.WEST);
        topRow.add(expressionLabel, BorderLayout.CENTER);

        mainValueLabel = new JLabel("0", SwingConstants.RIGHT);
        mainValueLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 38));

        add(topRow, BorderLayout.NORTH);
        add(mainValueLabel, BorderLayout.CENTER);

        applyTheme(palette);
    }

    public void updateDisplay(String expressionText, String mainText, boolean isError, boolean hasMemory, String memoryFormatted) {
        expressionLabel.setText((expressionText == null || expressionText.isBlank()) ? " " : expressionText);
        mainValueLabel.setText(mainText);

        if (hasMemory) {
            memoryBadgeLabel.setText(" [M: " + memoryFormatted + "] ");
            memoryBadgeLabel.setVisible(true);
        } else {
            memoryBadgeLabel.setText("");
            memoryBadgeLabel.setVisible(false);
        }

        // 문자열 길이에 따른 동적 폰트 크기 조절
        int len = mainText != null ? mainText.length() : 1;
        int fontSize;
        if (isError) {
            fontSize = 22;
        } else if (len <= 10) {
            fontSize = 38;
        } else if (len <= 14) {
            fontSize = 30;
        } else if (len <= 18) {
            fontSize = 24;
        } else {
            fontSize = 20;
        }
        mainValueLabel.setFont(new Font("Malgun Gothic", Font.BOLD, fontSize));
        mainValueLabel.setForeground(isError ? palette.errorText() : palette.primaryText());
        repaint();
    }

    public void applyTheme(ThemePalette newPalette) {
        this.palette = newPalette;
        expressionLabel.setForeground(palette.secondaryText());
        memoryBadgeLabel.setForeground(palette.opBtnText());
        mainValueLabel.setForeground(palette.primaryText());
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        g2.setColor(palette.displayBg());
        g2.fillRoundRect(0, 0, w - 1, h - 1, 18, 18);

        g2.setColor(palette.borderColor());
        g2.drawRoundRect(0, 0, w - 1, h - 1, 18, 18);

        g2.dispose();
        super.paintComponent(g);
    }
}
