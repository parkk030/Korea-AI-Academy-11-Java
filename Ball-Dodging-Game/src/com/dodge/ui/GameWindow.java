package com.dodge.ui;

import javax.swing.JFrame;

public class GameWindow extends JFrame {
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 600;

    public GameWindow() {
        setTitle("공피하기 게임 (Ball Dodging Game)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        GamePanel gamePanel = new GamePanel(WINDOW_WIDTH, WINDOW_HEIGHT);
        add(gamePanel);
        pack();

        setLocationRelativeTo(null);
    }
}
