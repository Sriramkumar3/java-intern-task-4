package com;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BreakoutGame extends JFrame implements KeyListener {
    static final int WIDTH = 700;
    static final int HEIGHT = 500;
    private static final int PADDLE_WIDTH = 80;
    private static final int PADDLE_HEIGHT = 30;
    private static final int BALL_RADIUS = 15;
    private static final int BRICK_WIDTH = 40;
    private static final int BRICK_HEIGHT = 20;
    private static final int BRICK_ROWS = 8;
    private static final int BRICK_COLUMNS = 15;
    private static final int BRICK_GAP = 1;
    private static final int GAME_SPEED = 10;
    private static final int PADDLE_SPEED = 10;

    private JPanel gamePanel;
    private Timer gameTimer;
    private List<Brick> bricks;
    private Paddle paddle;
    private Ball ball;
    private int score = 0;
    private int lives = 3;

    public BreakoutGame() {
        setTitle("Breakout Game");
        setSize(getWidth(), getHeight());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        gamePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                draw((Graphics2D) g);
            }
        };
        gamePanel.setPreferredSize(new Dimension(getWidth(), getHeight()));
        gamePanel.setBackground(Color.BLACK);
        add(gamePanel);
        addKeyListener(this);

        initializeGame();

        gameTimer = new Timer(GAME_SPEED, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update();
                repaint();
            }
        });
        gameTimer.start();
    }

    private void initializeGame() {
        bricks = new ArrayList<>();
        paddle = new Paddle(getWidth() / 2 - PADDLE_WIDTH / 2, getHeight() - 50, PADDLE_WIDTH, PADDLE_HEIGHT);
        ball = new Ball(getWidth() / 2 - BALL_RADIUS, getHeight() / 2 - BALL_RADIUS, BALL_RADIUS);

        // Create bricks
        for (int row = 0; row < BRICK_ROWS; row++) {
            for (int col = 0; col < BRICK_COLUMNS; col++) {
                int brickX = col * (BRICK_WIDTH + BRICK_GAP) + 30;
                int brickY = row * (BRICK_HEIGHT + BRICK_GAP) + 50;
                Brick brick = new Brick(brickX, brickY, BRICK_WIDTH, BRICK_HEIGHT);
                bricks.add(brick);
            }
        }
    }
    private void update() {
        paddle.update();
        ball.update();

        // Check collision with paddle
        Rectangle ballRect = new Rectangle(ball.getX(), ball.getY(), ball.getWidth(), ball.getHeight());
        Rectangle paddleRect = new Rectangle(paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight());
        if (ballRect.intersects(paddleRect)) {
            ball.setDY(-1);
        }

        // Check collision with bricks
        for (int i = 0; i < bricks.size(); i++) {
            Brick brick = bricks.get(i);
            Rectangle brickRect = new Rectangle(brick.getX(), brick.getY(), brick.getWidth(), brick.getHeight());
            if (ballRect.intersects(brickRect)) {
                bricks.remove(i);
                score += 10;
                ball.setDY(1);
                break; // Exit loop since ball can only collide with one brick at a time
            }
        }

        // Check collision with walls
        if (ball.getX() <= 0 || ball.getX() + ball.getWidth() >= WIDTH - BALL_RADIUS) {
            ball.setDX(-ball.getDX());
        }
        if (ball.getY() <= 0 || ball.getY() + ball.getHeight() >= HEIGHT - BALL_RADIUS) {
            ball.setDY(-ball.getDY());
        }

        // Check collision with bottom
        if (ball.getY() >= HEIGHT) {
            lives--;
            ball.reset();
        }

        // Check game over
        if (lives <= 0) {
            gameTimer.stop();
            JOptionPane.showMessageDialog(this, "Game Over! Your score: " + score);
            System.exit(0);
        }
    }




    private void draw(Graphics2D g) {
        paddle.draw(g);
        ball.draw(g);
        for (Brick brick : bricks) {
            brick.draw(g);
        }
        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, 10, 20);
        g.drawString("Lives: " + lives, getWidth() - 70, 20);
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_LEFT) {
            paddle.setDX(-PADDLE_SPEED);
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            paddle.setDX(PADDLE_SPEED);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_RIGHT) {
            paddle.setDX(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BreakoutGame().setVisible(true);
            }
        });
    }

	public int getWidth() {
		return WIDTH;
	}

	public int getHeight() {
		return HEIGHT;
	}
}
