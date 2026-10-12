package com.dodge.ui;

import com.dodge.model.Ball;
import com.dodge.model.GameState;
import com.dodge.model.Player;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener {
    private final int width;
    private final int height;

    private final Player player;
    private final List<Ball> balls = new ArrayList<>();
    private final Random random = new Random();
    private final Set<Integer> pressedKeys = new HashSet<>();
    private final Timer gameLoopTimer;

    private GameState gameState = GameState.READY;

    // 생존 시간 및 점수
    private long startTime;
    private long elapsedTime;
    private long pauseStartTime;
    private long totalPausedTime;
    private long finalSurvivalTime;
    private long bestSurvivalTime = 0;

    // 난이도 조절
    private static final int INITIAL_BALL_COUNT = 8;
    private static final int MAX_BALL_COUNT = 45;
    private int currentLevel = 1;

    private static final Color[] BALL_COLORS = {
        new Color(255, 68, 68),     // 네온 레드
        new Color(255, 140, 20),    // 네온 오렌지
        new Color(255, 225, 30),    // 네온 옐로우
        new Color(255, 45, 150),    // 핫 핑크
        new Color(175, 60, 255),    // 사이버 퍼플
        new Color(0, 240, 150)      // 민트 그린
    };

    public GamePanel(int width, int height) {
        this.width = width;
        this.height = height;
        setPreferredSize(new Dimension(width, height));
        setBackground(new Color(15, 18, 25));
        setFocusable(true);

        this.player = new Player(width / 2.0, height / 2.0, 12, 5.5);
        initBalls(INITIAL_BALL_COUNT);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                pressedKeys.add(keyCode);

                if (gameState == GameState.READY) {
                    if (keyCode == KeyEvent.VK_SPACE || keyCode == KeyEvent.VK_ENTER) {
                        startGame();
                    }
                } else if (gameState == GameState.PLAYING) {
                    if (keyCode == KeyEvent.VK_P || keyCode == KeyEvent.VK_ESCAPE) {
                        pauseGame();
                    }
                } else if (gameState == GameState.PAUSED) {
                    if (keyCode == KeyEvent.VK_P || keyCode == KeyEvent.VK_ESCAPE || keyCode == KeyEvent.VK_SPACE) {
                        resumeGame();
                    }
                } else if (gameState == GameState.GAME_OVER) {
                    if (keyCode == KeyEvent.VK_R || keyCode == KeyEvent.VK_SPACE || keyCode == KeyEvent.VK_ENTER) {
                        startGame();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                pressedKeys.remove(e.getKeyCode());
            }
        });

        this.gameLoopTimer = new Timer(16, this);
        this.gameLoopTimer.start();
    }

    public void startGame() {
        player.reset(width / 2.0, height / 2.0);
        initBalls(INITIAL_BALL_COUNT);
        startTime = System.currentTimeMillis();
        elapsedTime = 0;
        totalPausedTime = 0;
        currentLevel = 1;
        gameState = GameState.PLAYING;
    }

    public void pauseGame() {
        pauseStartTime = System.currentTimeMillis();
        gameState = GameState.PAUSED;
    }

    public void resumeGame() {
        totalPausedTime += (System.currentTimeMillis() - pauseStartTime);
        gameState = GameState.PLAYING;
    }

    public void initBalls(int count) {
        balls.clear();
        for (int i = 0; i < count; i++) {
            balls.add(createRandomBall(2.5, 4.5));
        }
    }

    public Ball createRandomBall(double minSpeed, double maxSpeed) {
        double radius = 9 + random.nextDouble() * 8;
        double spawnX, spawnY;
        double safeDistance = 160.0;

        int attempts = 0;
        do {
            spawnX = radius + random.nextDouble() * (width - 2 * radius);
            spawnY = radius + random.nextDouble() * (height - 2 * radius);
            double distSq = Math.pow(spawnX - player.getX(), 2) + Math.pow(spawnY - player.getY(), 2);
            if (distSq >= safeDistance * safeDistance || ++attempts > 50) {
                break;
            }
        } while (true);

        double angle = random.nextDouble() * 2 * Math.PI;
        double speed = minSpeed + random.nextDouble() * (maxSpeed - minSpeed);
        double vx = Math.cos(angle) * speed;
        double vy = Math.sin(angle) * speed;
        Color color = BALL_COLORS[random.nextInt(BALL_COLORS.length)];

        return new Ball(spawnX, spawnY, vx, vy, radius, color);
    }

    public static boolean checkCollision(Player player, Ball ball) {
        double dx = player.getX() - ball.getX();
        double dy = player.getY() - ball.getY();
        double distSq = dx * dx + dy * dy;
        double radiusSum = player.getRadius() + ball.getRadius();
        return distSq <= radiusSum * radiusSum;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameState == GameState.PLAYING) {
            updateGame();
        }
        repaint();
    }

    private void updateGame() {
        elapsedTime = System.currentTimeMillis() - startTime - totalPausedTime;

        // 4초마다 새로운 공 추가 및 난이도 증가
        int calculatedLevel = 1 + (int) (elapsedTime / 4000);
        if (calculatedLevel > currentLevel) {
            currentLevel = calculatedLevel;
            if (balls.size() < MAX_BALL_COUNT) {
                double minSpd = Math.min(6.5, 2.8 + currentLevel * 0.25);
                double maxSpd = Math.min(8.5, 4.8 + currentLevel * 0.25);
                balls.add(createRandomBall(minSpd, maxSpd));
            }
        }

        boolean up = pressedKeys.contains(KeyEvent.VK_UP) || pressedKeys.contains(KeyEvent.VK_W);
        boolean down = pressedKeys.contains(KeyEvent.VK_DOWN) || pressedKeys.contains(KeyEvent.VK_S);
        boolean left = pressedKeys.contains(KeyEvent.VK_LEFT) || pressedKeys.contains(KeyEvent.VK_A);
        boolean right = pressedKeys.contains(KeyEvent.VK_RIGHT) || pressedKeys.contains(KeyEvent.VK_D);

        player.move(up, down, left, right, width, height);

        for (Ball ball : balls) {
            ball.update(width, height);

            if (checkCollision(player, ball)) {
                gameState = GameState.GAME_OVER;
                finalSurvivalTime = elapsedTime;
                if (finalSurvivalTime > bestSurvivalTime) {
                    bestSurvivalTime = finalSurvivalTime;
                }
                break;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // 아케이드 레트로 그리드 배경
        drawGridBackground(g2d);

        // 장애물 공 렌더링
        for (Ball ball : balls) {
            ball.render(g2d);
        }

        // 플레이어 렌더링
        player.render(g2d);

        // 상단 HUD 바
        drawTopHud(g2d);

        // 상태별 오버레이
        if (gameState == GameState.READY) {
            renderReadyOverlay(g2d);
        } else if (gameState == GameState.PAUSED) {
            renderPausedOverlay(g2d);
        } else if (gameState == GameState.GAME_OVER) {
            renderGameOverOverlay(g2d);
        }
    }

    private void drawGridBackground(Graphics2D g2d) {
        g2d.setColor(new Color(25, 30, 45, 120));
        g2d.setStroke(new BasicStroke(1.0f));
        int gridSize = 40;
        for (int x = 0; x < width; x += gridSize) {
            g2d.drawLine(x, 0, x, height);
        }
        for (int y = 0; y < height; y += gridSize) {
            g2d.drawLine(0, y, width, y);
        }
    }

    private void drawTopHud(Graphics2D g2d) {
        // 상단 반투명 HUD 패널 바
        g2d.setColor(new Color(10, 14, 22, 200));
        g2d.fillRoundRect(15, 12, width - 30, 48, 12, 12);
        g2d.setColor(new Color(60, 80, 120, 150));
        g2d.setStroke(new BasicStroke(1.2f));
        g2d.drawRoundRect(15, 12, width - 30, 48, 12, 12);

        double seconds = (gameState == GameState.READY) ? 0.0 : (elapsedTime / 1000.0);
        int score = (int) (elapsedTime / 10);

        g2d.setFont(new Font("SansSerif", Font.BOLD, 15));

        // 생존 시간
        g2d.setColor(new Color(0, 220, 255));
        g2d.drawString(String.format("⏱ 생존: %.2f초", seconds), 30, 42);

        // 점수
        g2d.setColor(new Color(255, 220, 80));
        g2d.drawString(String.format("★ 점수: %d", score), 200, 42);

        // 난이도 및 공 개수
        g2d.setColor(new Color(255, 120, 120));
        g2d.drawString(String.format("⚡ 난이도: Lv.%d (공 %d개)", currentLevel, balls.size()), 350, 42);

        // 최고 기록
        g2d.setColor(new Color(180, 220, 255));
        String bestStr = (bestSurvivalTime > 0)
                ? String.format("🏆 최고 기록: %.2f초", bestSurvivalTime / 1000.0)
                : "🏆 최고 기록: --";
        g2d.drawString(bestStr, width - 210, 42);
    }

    private void renderReadyOverlay(Graphics2D g2d) {
        g2d.setColor(new Color(5, 8, 15, 200));
        g2d.fillRect(0, 0, width, height);

        // 모던 타이틀 카드
        g2d.setColor(new Color(20, 30, 50, 230));
        int cardW = 560;
        int cardH = 320;
        int cardX = (width - cardW) / 2;
        int cardY = (height - cardH) / 2;
        g2d.fillRoundRect(cardX, cardY, cardW, cardH, 20, 20);
        g2d.setColor(new Color(0, 200, 255, 180));
        g2d.setStroke(new BasicStroke(2.0f));
        g2d.drawRoundRect(cardX, cardY, cardW, cardH, 20, 20);

        // 게임 타이틀
        g2d.setFont(new Font("SansSerif", Font.BOLD, 38));
        g2d.setColor(new Color(0, 225, 255));
        String title = "공피하기 게임 (Dodge Ball)";
        FontMetrics fm = g2d.getFontMetrics();
        g2d.drawString(title, (width - fm.stringWidth(title)) / 2, cardY + 70);

        // 조작 설명
        g2d.setFont(new Font("SansSerif", Font.PLAIN, 16));
        g2d.setColor(new Color(230, 235, 245));
        String guide1 = "🎮 조작: 방향키(↑ ↓ ← →) 또는 W, A, S, D";
        FontMetrics fm1 = g2d.getFontMetrics();
        g2d.drawString(guide1, (width - fm1.stringWidth(guide1)) / 2, cardY + 125);

        String guide2 = "🎯 목표: 화면을 튕겨다니는 공들을 최대한 오래 피하세요!";
        FontMetrics fm2 = g2d.getFontMetrics();
        g2d.drawString(guide2, (width - fm2.stringWidth(guide2)) / 2, cardY + 160);

        String guide3 = "⏸ 일시정지: [ P ] 또는 [ ESC ]";
        FontMetrics fm3 = g2d.getFontMetrics();
        g2d.drawString(guide3, (width - fm3.stringWidth(guide3)) / 2, cardY + 195);

        // 시작 안내
        g2d.setFont(new Font("SansSerif", Font.BOLD, 20));
        g2d.setColor(new Color(255, 215, 0));
        String guideStart = "[ SPACE ] 또는 [ ENTER ] 키를 눌러 게임 시작";
        FontMetrics fmStart = g2d.getFontMetrics();
        g2d.drawString(guideStart, (width - fmStart.stringWidth(guideStart)) / 2, cardY + 260);
    }

    private void renderPausedOverlay(Graphics2D g2d) {
        g2d.setColor(new Color(0, 0, 0, 160));
        g2d.fillRect(0, 0, width, height);

        g2d.setFont(new Font("SansSerif", Font.BOLD, 46));
        g2d.setColor(new Color(255, 220, 50));
        String pauseText = "PAUSED";
        FontMetrics fm = g2d.getFontMetrics();
        g2d.drawString(pauseText, (width - fm.stringWidth(pauseText)) / 2, height / 2 - 20);

        g2d.setFont(new Font("SansSerif", Font.PLAIN, 18));
        g2d.setColor(Color.WHITE);
        String resumeText = "[ P ] 또는 [ SPACE ] 키를 눌러 계속하기";
        FontMetrics fmResume = g2d.getFontMetrics();
        g2d.drawString(resumeText, (width - fmResume.stringWidth(resumeText)) / 2, height / 2 + 30);
    }

    private void renderGameOverOverlay(Graphics2D g2d) {
        g2d.setColor(new Color(15, 0, 0, 200));
        g2d.fillRect(0, 0, width, height);

        int cardW = 540;
        int cardH = 340;
        int cardX = (width - cardW) / 2;
        int cardY = (height - cardH) / 2;

        g2d.setColor(new Color(30, 15, 20, 230));
        g2d.fillRoundRect(cardX, cardY, cardW, cardH, 20, 20);
        g2d.setColor(new Color(255, 70, 70, 200));
        g2d.setStroke(new BasicStroke(2.0f));
        g2d.drawRoundRect(cardX, cardY, cardW, cardH, 20, 20);

        g2d.setFont(new Font("SansSerif", Font.BOLD, 44));
        g2d.setColor(new Color(255, 70, 70));
        String gameOverText = "GAME OVER";
        FontMetrics fm = g2d.getFontMetrics();
        g2d.drawString(gameOverText, (width - fm.stringWidth(gameOverText)) / 2, cardY + 65);

        g2d.setFont(new Font("SansSerif", Font.BOLD, 22));
        g2d.setColor(Color.WHITE);
        String finalTimeText = String.format("최종 생존 시간: %.2f초", finalSurvivalTime / 1000.0);
        FontMetrics fmTime = g2d.getFontMetrics();
        g2d.drawString(finalTimeText, (width - fmTime.stringWidth(finalTimeText)) / 2, cardY + 120);

        String scoreText = String.format("획득 점수: %d 점", (int) (finalSurvivalTime / 10));
        FontMetrics fmScore = g2d.getFontMetrics();
        g2d.setColor(new Color(255, 220, 80));
        g2d.drawString(scoreText, (width - fmScore.stringWidth(scoreText)) / 2, cardY + 160);

        g2d.setFont(new Font("SansSerif", Font.PLAIN, 18));
        g2d.setColor(new Color(150, 220, 255));
        String bestText = String.format("최고 기록: %.2f초", bestSurvivalTime / 1000.0);
        FontMetrics fmBest = g2d.getFontMetrics();
        g2d.drawString(bestText, (width - fmBest.stringWidth(bestText)) / 2, cardY + 205);

        g2d.setFont(new Font("SansSerif", Font.BOLD, 21));
        g2d.setColor(new Color(0, 255, 170));
        String restartText = "[ R ] 키 또는 [ SPACE ]를 눌러 다시 도전";
        FontMetrics fmRestart = g2d.getFontMetrics();
        g2d.drawString(restartText, (width - fmRestart.stringWidth(restartText)) / 2, cardY + 275);
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public long getBestSurvivalTime() {
        return bestSurvivalTime;
    }

    public List<Ball> getBalls() {
        return balls;
    }

    public Player getPlayer() {
        return player;
    }
}
