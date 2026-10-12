package com.calc.ui;

import com.calc.model.HistoryItem;
import com.calc.ui.RoundedButton.ButtonCategory;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.function.Consumer;

/**
 * 계산 히스토리 목록 표시, 항목 클릭 시 값 복원, 기록 삭제를 지원하는 사이드 패널
 */
public class HistoryPanel extends JPanel {

    private final JLabel titleLabel;
    private final JLabel emptyStateLabel;
    private final JPanel listContainer;
    private final JScrollPane scrollPane;
    private final RoundedButton clearHistoryBtn;
    private final Consumer<HistoryItem> onItemSelected;

    private ThemePalette palette = ThemePalette.DARK;

    public HistoryPanel(Consumer<HistoryItem> onItemSelected, Runnable onClearHistory) {
        this.onItemSelected = onItemSelected;

        setOpaque(false);
        setPreferredSize(new Dimension(245, 520));
        setLayout(new BorderLayout(0, 8));
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        titleLabel = new JLabel("계산 기록 (클릭하여 불러오기)");
        titleLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 13));

        listContainer = new JPanel();
        listContainer.setLayout(new BoxLayout(listContainer, BoxLayout.Y_AXIS));
        listContainer.setOpaque(false);

        emptyStateLabel = new JLabel("아직 계산 기록이 없습니다.", SwingConstants.CENTER);
        emptyStateLabel.setFont(new Font("Malgun Gothic", Font.PLAIN, 13));

        scrollPane = new JScrollPane(listContainer);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.getVerticalScrollBar().setUnitIncrement(14);

        clearHistoryBtn = new RoundedButton("기록 전체 삭제", ButtonCategory.TOOLBAR, 10);
        clearHistoryBtn.setPreferredSize(new Dimension(200, 34));
        clearHistoryBtn.addActionListener(e -> onClearHistory.run());

        add(titleLabel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(clearHistoryBtn, BorderLayout.SOUTH);

        applyTheme(palette);
    }

    public void updateHistory(List<HistoryItem> items) {
        listContainer.removeAll();

        if (items == null || items.isEmpty()) {
            JPanel emptyWrap = new JPanel(new BorderLayout());
            emptyWrap.setOpaque(false);
            emptyWrap.setBorder(BorderFactory.createEmptyBorder(40, 8, 40, 8));
            emptyStateLabel.setForeground(palette.secondaryText());
            emptyWrap.add(emptyStateLabel, BorderLayout.CENTER);
            listContainer.add(emptyWrap);
            clearHistoryBtn.setEnabled(false);
        } else {
            clearHistoryBtn.setEnabled(true);
            for (HistoryItem item : items) {
                listContainer.add(createHistoryCard(item));
                listContainer.add(Box.createVerticalStrut(6));
            }
        }

        titleLabel.setText("계산 기록 (" + (items == null ? 0 : items.size()) + ")");
        listContainer.revalidate();
        listContainer.repaint();
    }

    private JPanel createHistoryCard(HistoryItem item) {
        JPanel card = new JPanel(new BorderLayout(2, 2)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(palette.numberBtnBg());
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 12, 12);
                g2.setColor(palette.borderColor());
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 12, 12);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        card.setOpaque(false);
        card.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 64));
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JPanel topLine = new JPanel(new BorderLayout());
        topLine.setOpaque(false);

        JLabel timeLbl = new JLabel(item.formattedTime());
        timeLbl.setFont(new Font("Malgun Gothic", Font.PLAIN, 11));
        timeLbl.setForeground(palette.secondaryText());

        JLabel exprLbl = new JLabel(item.expression(), SwingConstants.RIGHT);
        exprLbl.setFont(new Font("Malgun Gothic", Font.PLAIN, 12));
        exprLbl.setForeground(palette.secondaryText());

        topLine.add(timeLbl, BorderLayout.WEST);
        topLine.add(exprLbl, BorderLayout.CENTER);

        JLabel resLbl = new JLabel(item.formattedResult(), SwingConstants.RIGHT);
        resLbl.setFont(new Font("Malgun Gothic", Font.BOLD, 17));
        resLbl.setForeground(palette.primaryText());

        card.add(topLine, BorderLayout.NORTH);
        card.add(resLbl, BorderLayout.CENTER);

        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (onItemSelected != null) {
                    onItemSelected.accept(item);
                }
            }
        });

        return card;
    }

    public void applyTheme(ThemePalette newPalette) {
        this.palette = newPalette;
        titleLabel.setForeground(palette.primaryText());
        emptyStateLabel.setForeground(palette.secondaryText());
        clearHistoryBtn.applyTheme(palette);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(palette.displayBg());
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 16, 16);

        g2.setColor(palette.borderColor());
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 16, 16);

        g2.dispose();
        super.paintComponent(g);
    }
}
