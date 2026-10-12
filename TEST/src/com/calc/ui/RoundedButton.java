package com.calc.ui;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 안티앨리어싱 둥근 모서리와 호버/클릭 효과가 적용된 커스텀 버튼
 */
public class RoundedButton extends JButton {

    public enum ButtonCategory {
        NUMBER,
        FUNCTION,
        OPERATOR,
        ACCENT_EQUALS,
        TOOLBAR
    }

    private final ButtonCategory category;
    private final int cornerRadius;
    private ThemePalette palette = ThemePalette.DARK;
    private boolean hovered = false;
    private boolean pressed = false;

    public RoundedButton(String text, ButtonCategory category) {
        this(text, category, 14);
    }

    public RoundedButton(String text, ButtonCategory category, int cornerRadius) {
        super(text);
        this.category = category;
        this.cornerRadius = cornerRadius;

        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
        setMargin(new java.awt.Insets(2, 6, 2, 6));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        Font baseFont = switch (category) {
            case NUMBER -> new Font("Malgun Gothic", Font.BOLD, 20);
            case OPERATOR, ACCENT_EQUALS -> new Font("Malgun Gothic", Font.BOLD, 22);
            case FUNCTION -> new Font("Malgun Gothic", Font.PLAIN, 16);
            case TOOLBAR -> new Font("Malgun Gothic", Font.BOLD, 13);
        };
        setFont(baseFont);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                hovered = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hovered = false;
                pressed = false;
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (isEnabled()) {
                    pressed = true;
                    repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                pressed = false;
                repaint();
            }
        });

        applyTheme(palette);
    }

    public void applyTheme(ThemePalette newPalette) {
        this.palette = newPalette;
        Color fg = switch (category) {
            case NUMBER -> palette.numberBtnText();
            case FUNCTION, TOOLBAR -> palette.funcBtnText();
            case OPERATOR -> palette.opBtnText();
            case ACCENT_EQUALS -> palette.accentBtnText();
        };
        setForeground(fg);
        repaint();
    }

    /**
     * 키보드 입력 시 시각적인 버튼 눌림 피드백 효과
     */
    public void flashPress() {
        pressed = true;
        repaint();
        javax.swing.Timer timer = new javax.swing.Timer(110, e -> {
            pressed = false;
            repaint();
        });
        timer.setRepeats(false);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        Color baseBg = switch (category) {
            case NUMBER -> palette.numberBtnBg();
            case FUNCTION, TOOLBAR -> palette.funcBtnBg();
            case OPERATOR -> palette.opBtnBg();
            case ACCENT_EQUALS -> palette.accentBtnBg();
        };

        Color hoverBg = switch (category) {
            case NUMBER -> palette.numberBtnHover();
            case FUNCTION, TOOLBAR -> palette.funcBtnHover();
            case OPERATOR -> palette.opBtnHover();
            case ACCENT_EQUALS -> palette.accentBtnHover();
        };

        Color fill = baseBg;
        if (!isEnabled()) {
            fill = new Color(baseBg.getRed(), baseBg.getGreen(), baseBg.getBlue(), 110);
        } else if (pressed) {
            fill = hoverBg.darker();
        } else if (hovered) {
            fill = hoverBg;
        }

        g2.setColor(fill);
        g2.fillRoundRect(1, 1, w - 2, h - 2, cornerRadius, cornerRadius);

        // 미세한 테두리 렌더링
        g2.setColor(palette.borderColor());
        g2.drawRoundRect(1, 1, w - 3, h - 3, cornerRadius, cornerRadius);

        g2.dispose();
        super.paintComponent(g);
    }
}
