package com;

import java.awt.Color;
import java.awt.Graphics2D;

public class Paddle {

    private int x;
    private int y;
    private int width;
    private int height;
    private int dx;

    public Paddle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void update() {
        x += dx;
        if (x < 0) {
            x = 0;
        }
        if (x + width > BreakoutGame.WIDTH) {
            x = BreakoutGame.WIDTH - width;
        }
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fillRect(x, y, width, height);
    }

    public void setDX(int dx) {
        this.dx = dx;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
