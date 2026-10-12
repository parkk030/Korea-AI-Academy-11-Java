package com.dodge.model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.LinkedList;

public class Player {
    private double x;
    private double y;
    private final double radius;
    private final double speed;

    // 움직임 잔상(트레일) 효과용 큐
    private final LinkedList<Point2D.Double> trail = new LinkedList<>();
    private static final int MAX_TRAIL_LENGTH = 6;

    public Player(double startX, double startY, double radius, double speed) {
        this.x = startX;
        this.y = startY;
        this.radius = radius;
        this.speed = speed;
    }

    public void move(boolean up, boolean down, boolean left, boolean right, int boundWidth, int boundHeight) {
        double dx = 0;
        double dy = 0;

        if (up) dy -= 1;
        if (down) dy += 1;
        if (left) dx -= 1;
        if (right) dx += 1;

        if (dx != 0 || dy != 0) {
            trail.addFirst(new Point2D.Double(x, y));
            if (trail.size() > MAX_TRAIL_LENGTH) {
                trail.removeLast();
            }
        } else if (!trail.isEmpty()) {
            trail.removeLast();
        }

        // 대각선 이동 시 속도 정규화
        if (dx != 0 && dy != 0) {
            dx *= 0.7071067811865475;
            dy *= 0.7071067811865475;
        }

        x += dx * speed;
        y += dy * speed;

        // 화면 경계 이탈 방지
        if (x - radius < 0) {
            x = radius;
        }
        if (x + radius > boundWidth) {
            x = boundWidth - radius;
        }
        if (y - radius < 0) {
            y = radius;
        }
        if (y + radius > boundHeight) {
            y = boundHeight - radius;
        }
    }

    public void reset(double startX, double startY) {
        this.x = startX;
        this.y = startY;
        this.trail.clear();
    }

    public void render(Graphics2D g) {
        // 잔상(트레일) 렌더링
        int index = 0;
        for (Point2D.Double pt : trail) {
            float alpha = 0.35f * (1.0f - (float) index / MAX_TRAIL_LENGTH);
            double trailR = radius * (1.0 - (double) index / (MAX_TRAIL_LENGTH * 1.5));
            g.setColor(new Color(0.0f, 0.8f, 1.0f, Math.max(0f, alpha)));
            int tx = (int) Math.round(pt.x - trailR);
            int ty = (int) Math.round(pt.y - trailR);
            int td = (int) Math.round(trailR * 2);
            g.fillOval(tx, ty, td, td);
            index++;
        }

        int drawX = (int) Math.round(x - radius);
        int drawY = (int) Math.round(y - radius);
        int diameter = (int) Math.round(radius * 2);

        // 외곽 네온 글로우 효과
        g.setColor(new Color(0, 220, 255, 70));
        g.fillOval(drawX - 5, drawY - 5, diameter + 10, diameter + 10);

        // 플레이어 본체 (네온 시안)
        g.setColor(new Color(0, 210, 255));
        g.fillOval(drawX, drawY, diameter, diameter);

        // 중앙 코어 (화이트)
        g.setColor(Color.WHITE);
        int coreRadius = (int) Math.max(3, radius * 0.45);
        g.fillOval((int) Math.round(x - coreRadius), (int) Math.round(y - coreRadius), coreRadius * 2, coreRadius * 2);

        // 외곽 하이라이트 라인
        g.setColor(new Color(200, 250, 255));
        g.setStroke(new BasicStroke(1.8f));
        g.drawOval(drawX, drawY, diameter, diameter);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getRadius() {
        return radius;
    }

    public double getSpeed() {
        return speed;
    }
}
