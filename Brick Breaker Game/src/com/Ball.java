package com;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Ball {
    private int x;
    private int y;
    private int radius;
    private int dx;
    private int dy;

    public Ball(int x, int y, int radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        dx = 2;
        dy = -2;
    }

    public void update() {
        x += dx;
        y += dy;
        if (x <= 0 || x + 2 * radius >= BreakoutGame.WIDTH) {
            dx = -dx;
        }
        if (y <= 0) {
            dy = -dy;
        }
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fillOval(x, y, 2 * radius, 2 * radius);
    }

    public boolean intersects(Rectangle rect) {
        return new Rectangle(x, y, 2 * radius, 2 * radius).intersects(rect);
    }

    public void setDX(int dx) {
        this.dx = dx;
    }

    public void setDY(int dy) {
        this.dy = dy;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return 2 * radius;
    }

    public int getHeight() {
        return 2 * radius;
    }

    public void reset() {
        x = BreakoutGame.WIDTH / 2 - radius;
        y = BreakoutGame.HEIGHT / 2 - radius;
        dx = 2;
        dy = -2;
    }

	public int getDY() {
		
		return dy;
	}

	public int getDX() {
		
		return dx;
	}
}
