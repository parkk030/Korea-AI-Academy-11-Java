package com.calc.ui;

import com.calc.model.CalculatorEngine;
import com.calc.model.Operator.BinaryOp;
import com.calc.model.Operator.UnaryOp;
import com.calc.ui.RoundedButton.ButtonCategory;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 계산기 메인 윈도우 프레임 - 히스토리 사이드 패널 연동 및 테마 전환 완성
 */
public class CalculatorFrame extends JFrame {

    public static final int DEFAULT_WIDTH = 420;
    public static final int EXPANDED_WIDTH = 680;
    public static final int DEFAULT_HEIGHT = 660;

    protected final CalculatorEngine engine = new CalculatorEngine();
    protected ThemePalette currentTheme = ThemePalette.DARK;

    protected JPanel rootPanel;
    protected JPanel mainCalcPanel;
    protected DisplayPanel displayPanel;
    protected HistoryPanel historyPanel;
    protected JLabel headerTitleLabel;
    protected RoundedButton themeToggleBtn;
    protected RoundedButton historyToggleBtn;
    private boolean historyVisible = false;

    protected final Map<String, RoundedButton> buttonMap = new LinkedHashMap<>();
    protected final List<RoundedButton> memoryButtons = new ArrayList<>();

    public CalculatorFrame() {
        setTitle("Java 21 Smart Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        setMinimumSize(new Dimension(380, 580));
        setLocationRelativeTo(null);

        initComponents();
        bindButtonEvents();
        installKeyboardShortcuts();
        applyTheme(currentTheme);
        refreshUI();
    }

    private void initComponents() {
        rootPanel = new JPanel(new BorderLayout(12, 0));
        rootPanel.setBorder(BorderFactory.createEmptyBorder(12, 14, 14, 14));

        mainCalcPanel = new JPanel(new BorderLayout(0, 10));
        mainCalcPanel.setOpaque(false);

        JPanel northContainer = new JPanel(new BorderLayout(0, 8));
        northContainer.setOpaque(false);
        northContainer.add(createHeaderBar(), BorderLayout.NORTH);

        displayPanel = new DisplayPanel();
        northContainer.add(displayPanel, BorderLayout.CENTER);
        northContainer.add(createMemoryBar(), BorderLayout.SOUTH);

        mainCalcPanel.add(northContainer, BorderLayout.NORTH);
        mainCalcPanel.add(createKeypadPanel(), BorderLayout.CENTER);

        historyPanel = new HistoryPanel(
                item -> {
                    engine.recallFromHistory(item);
                    refreshUI();
                },
                () -> {
                    engine.clearHistory();
                    refreshUI();
                }
        );

        rootPanel.add(mainCalcPanel, BorderLayout.CENTER);
        setContentPane(rootPanel);
    }

    private JPanel createHeaderBar() {
        JPanel header = new JPanel(new BorderLayout(8, 0));
        header.setOpaque(false);

        headerTitleLabel = new JLabel("Java 21 계산기");
        headerTitleLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 15));

        JPanel rightControls = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 0));
        rightControls.setOpaque(false);

        themeToggleBtn = new RoundedButton("라이트 테마", ButtonCategory.TOOLBAR, 10);
        themeToggleBtn.setPreferredSize(new Dimension(102, 30));
        themeToggleBtn.setToolTipText("다크 / 라이트 테마 전환");

        historyToggleBtn = new RoundedButton("기록 (0)", ButtonCategory.TOOLBAR, 10);
        historyToggleBtn.setPreferredSize(new Dimension(92, 30));
        historyToggleBtn.setToolTipText("계산 기록 사이드바 열기/닫기");

        rightControls.add(themeToggleBtn);
        rightControls.add(historyToggleBtn);

        header.add(headerTitleLabel, BorderLayout.WEST);
        header.add(rightControls, BorderLayout.EAST);
        return header;
    }

    private JPanel createMemoryBar() {
        JPanel memBar = new JPanel(new GridLayout(1, 5, 6, 0));
        memBar.setOpaque(false);
        memBar.setPreferredSize(new Dimension(360, 34));

        String[] memKeys = {"MC", "MR", "M+", "M-", "MS"};
        for (String key : memKeys) {
            RoundedButton btn = new RoundedButton(key, ButtonCategory.TOOLBAR, 10);
            memoryButtons.add(btn);
            buttonMap.put(key, btn);
            memBar.add(btn);
        }
        return memBar;
    }

    private JPanel createKeypadPanel() {
        JPanel grid = new JPanel(new GridLayout(6, 4, 8, 8));
        grid.setOpaque(false);

        String[][] layout = {
                {"%", "CE", "C", "←"},
                {"1/x", "x²", "√x", "÷"},
                {"7", "8", "9", "×"},
                {"4", "5", "6", "-"},
                {"1", "2", "3", "+"},
                {"±", "0", ".", "="}
        };

        for (String[] row : layout) {
            for (String label : row) {
                ButtonCategory cat = classifyButton(label);
                RoundedButton btn = new RoundedButton(label, cat, 14);
                buttonMap.put(label, btn);
                grid.add(btn);
            }
        }
        return grid;
    }

    private ButtonCategory classifyButton(String label) {
        return switch (label) {
            case "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", ".", "±" -> ButtonCategory.NUMBER;
            case "÷", "×", "-", "+" -> ButtonCategory.OPERATOR;
            case "=" -> ButtonCategory.ACCENT_EQUALS;
            default -> ButtonCategory.FUNCTION;
        };
    }

    private void bindButtonEvents() {
        themeToggleBtn.addActionListener(e -> {
            ThemePalette next = currentTheme.isDark() ? ThemePalette.LIGHT : ThemePalette.DARK;
            applyTheme(next);
        });

        historyToggleBtn.addActionListener(e -> toggleHistorySidebar());

        for (Map.Entry<String, RoundedButton> entry : buttonMap.entrySet()) {
            String key = entry.getKey();
            RoundedButton btn = entry.getValue();
            btn.addActionListener(e -> handleCommand(key, false));
        }
    }

    private void toggleHistorySidebar() {
        historyVisible = !historyVisible;
        if (historyVisible) {
            rootPanel.add(historyPanel, BorderLayout.EAST);
            setSize(new Dimension(EXPANDED_WIDTH, getHeight()));
        } else {
            rootPanel.remove(historyPanel);
            setSize(new Dimension(DEFAULT_WIDTH, getHeight()));
        }
        rootPanel.revalidate();
        rootPanel.repaint();
    }

    /**
     * 버튼 클릭 또는 키보드 입력 명령을 처리
     */
    public void handleCommand(String command, boolean flashVisual) {
        if (flashVisual) {
            RoundedButton btn = buttonMap.get(command);
            if (btn != null && btn.isEnabled()) {
                btn.flashPress();
            }
        }

        switch (command) {
            case "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" ->
                    engine.inputDigit(Integer.parseInt(command));
            case "." -> engine.inputDecimalPoint();
            case "←", "⌫" -> engine.backspace();
            case "CE" -> engine.clearEntry();
            case "C" -> engine.clearAll();
            case "+" -> engine.applyBinaryOperator(BinaryOp.ADD);
            case "-", "−" -> engine.applyBinaryOperator(BinaryOp.SUBTRACT);
            case "×" -> engine.applyBinaryOperator(BinaryOp.MULTIPLY);
            case "÷" -> engine.applyBinaryOperator(BinaryOp.DIVIDE);
            case "=" -> engine.evaluate();
            case "%" -> engine.applyUnaryOperator(UnaryOp.PERCENT);
            case "1/x" -> engine.applyUnaryOperator(UnaryOp.RECIPROCAL);
            case "x²" -> engine.applyUnaryOperator(UnaryOp.SQUARE);
            case "√x" -> engine.applyUnaryOperator(UnaryOp.SQRT);
            case "±" -> engine.applyUnaryOperator(UnaryOp.NEGATE);
            case "MC" -> engine.memoryClear();
            case "MR" -> engine.memoryRecall();
            case "M+" -> engine.memoryAdd();
            case "M-" -> engine.memorySubtract();
            case "MS" -> engine.memoryStore();
            default -> {
            }
        }

        refreshUI();
    }

    private void installKeyboardShortcuts() {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                if (!isActive() || e.getID() != KeyEvent.KEY_PRESSED) {
                    return false;
                }
                char ch = e.getKeyChar();
                int code = e.getKeyCode();

                if (ch >= '0' && ch <= '9') {
                    handleCommand(String.valueOf(ch), true);
                    return true;
                }
                if (ch == '.') {
                    handleCommand(".", true);
                    return true;
                }
                if (ch == '+') {
                    handleCommand("+", true);
                    return true;
                }
                if (ch == '-') {
                    handleCommand("-", true);
                    return true;
                }
                if (ch == '*') {
                    handleCommand("×", true);
                    return true;
                }
                if (ch == '/') {
                    handleCommand("÷", true);
                    return true;
                }
                if (ch == '%') {
                    handleCommand("%", true);
                    return true;
                }
                if (code == KeyEvent.VK_ENTER || ch == '=') {
                    handleCommand("=", true);
                    return true;
                }
                if (code == KeyEvent.VK_BACK_SPACE) {
                    handleCommand("←", true);
                    return true;
                }
                if (code == KeyEvent.VK_ESCAPE) {
                    handleCommand("C", true);
                    return true;
                }
                if (code == KeyEvent.VK_DELETE) {
                    handleCommand("CE", true);
                    return true;
                }
                if (code == KeyEvent.VK_F9) {
                    handleCommand("±", true);
                    return true;
                }
                return false;
            }
        });
    }

    protected void refreshUI() {
        boolean hasMem = engine.hasMemory();
        String memText = hasMem ? CalculatorEngine.formatNumber(engine.getMemoryValue()) : "";

        displayPanel.updateDisplay(
                engine.getExpressionPreview(),
                engine.getDisplayText(),
                engine.isErrorState(),
                hasMem,
                memText
        );

        RoundedButton mcBtn = buttonMap.get("MC");
        RoundedButton mrBtn = buttonMap.get("MR");
        if (mcBtn != null) {
            mcBtn.setEnabled(hasMem);
        }
        if (mrBtn != null) {
            mrBtn.setEnabled(hasMem);
        }

        int historyCount = engine.getHistory().size();
        historyToggleBtn.setText("기록 (" + historyCount + ")");
        if (historyPanel != null) {
            historyPanel.updateHistory(engine.getHistory());
        }
    }

    public void applyTheme(ThemePalette palette) {
        this.currentTheme = palette;
        rootPanel.setBackground(palette.windowBg());
        headerTitleLabel.setForeground(palette.primaryText());
        themeToggleBtn.setText(palette.isDark() ? "라이트 테마" : "다크 테마");
        themeToggleBtn.applyTheme(palette);
        historyToggleBtn.applyTheme(palette);
        displayPanel.applyTheme(palette);
        if (historyPanel != null) {
            historyPanel.applyTheme(palette);
        }

        for (RoundedButton btn : buttonMap.values()) {
            btn.applyTheme(palette);
        }
        refreshUI();
        repaint();
    }
}
