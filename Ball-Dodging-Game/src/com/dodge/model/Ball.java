package com.dodge.model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class Ball {
    private double x;
    private double y;
    private double vx;
    private double vy;
    private final double radius;
    private final Color color;
    private final Color glowColor;

    public Ball(double x, double y, double vx, double vy, double radius, Color color) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.radius = radius;
        this.color = color;
        this.glowColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), 60);
    }

    public void update(int boundWidth, int boundHeight) {
        x += vx;
        y += vy;

        // 좌/우 벽 충돌 반사
        if (x - radius <= 0) {
            x = radius;
            vx = -vx;
        } else if (x + radius >= boundWidth) {
            x = boundWidth - radius;
            vx = -vx;
        }

        // 상/하 벽 충돌 반사
        if (y - radius <= 0) {
            y = radius;
            vy = -vy;
        } else if (y + radius >= boundHeight) {
            y = boundHeight - radius;
            vy = -vy;
        }
    }

    public void render(Graphics2D g) {
        int drawX = (int) Math.round(x - radius);
        int drawY = (int) Math.round(y - radius);
        int diameter = (int) Math.round(radius * 2);

        // 글로우 효과
        g.setColor(glowColor);
        g.fillOval(drawX - 3, drawY - 3, diameter + 6, diameter + 6);

        // 공 본체
        g.setColor(color);
        g.fillOval(drawX, drawY, diameter, diameter);

        // 하이라이트 (입체감)
        g.setColor(new Color(255, 255, 255, 180));
        int highlightSize = (int) Math.max(2, radius * 0.4);
        int hX = (int) Math.round(x - radius * 0.4);
        int hY = (int) Math.round(y - radius * 0.4);
        g.fillOval(hX, hY, highlightSize, highlightSize);

        // 외곽선
        g.setColor(new Color(0, 0, 0, 60));
        g.setStroke(new BasicStroke(1.0f));
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

    public double getVx() {
        return vx;
    }

    public void setVx(double vx) {
        this.vx = vx;
    }

    public double getVy() {
        return vy;
    }

    public void setVy(double vy) {
        this.vy = vy;
    }

    public Color getColor() {
        return color;
    }
}
