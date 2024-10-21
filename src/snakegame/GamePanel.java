package snakegame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    private static final long serialVersionUID = 1L;

    static final int WIDTH = 500;
    static final int HEIGHT = 500;
    static final int UNIT_SIZE = 20;
    static final int NUMBER_OF_UNITS = (WIDTH * HEIGHT) / (UNIT_SIZE * UNIT_SIZE);

    final int x[] = new int[NUMBER_OF_UNITS];
    final int y[] = new int[NUMBER_OF_UNITS];

    int length = 1;
    int foodeaten;
    int foodx;
    int foody;
    char direction = 'D';
    boolean running = false;
    Random random;
    Timer timer;
    Image backgroundImage;
    JButton restartButton;

    GamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.BLUE);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());

       backgroundImage = new ImageIcon("C:/Users/21697/Desktop/karimhammamidlsiadbd/snakegame/photos/wallhaven-g73xve_3840x2160.png").getImage();

        play();

        restartButton = new JButton("Restart");
        restartButton.setBounds(WIDTH / 2 - 50, HEIGHT / 2 + 50, 100, 40);
        restartButton.setFont(new Font("Arial", Font.PLAIN, 18));
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGame();  
            }
        });
        this.setLayout(null); 
        this.add(restartButton);
        restartButton.setVisible(false); 
    }

    public void play() {
        addfood();
        running = true;
        timer = new Timer(80, this);
        timer.start();
    }

    public void resetGame() {
        length = 1;
        foodeaten = 0;
        direction = 'D';
        running = true;

        x[0] = WIDTH / 2;
        y[0] = HEIGHT / 2;

        addfood();
        timer.start();
        restartButton.setVisible(false);  // Hide the restart button
        repaint();
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.drawImage(backgroundImage, 0, 0, WIDTH, HEIGHT, this);
        draw(graphics);
    }

    public void draw(Graphics graphics) {
        if (running) {
            graphics.setColor(Color.GREEN);
            graphics.fillOval(foodx, foody, UNIT_SIZE, UNIT_SIZE);

            graphics.setColor(Color.WHITE);
            graphics.fillOval(x[0], y[0], UNIT_SIZE, UNIT_SIZE);

            for (int i = 1; i < length; i++) {
                graphics.setColor(Color.RED);
                graphics.fillOval(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            }

            graphics.setColor(Color.WHITE);
            graphics.setFont(new Font("sans serif", Font.ROMAN_BASELINE, 25));
            FontMetrics metrics = getFontMetrics(graphics.getFont());
            graphics.drawString("Score: " + foodeaten, (WIDTH - metrics.stringWidth("Score: " + foodeaten)) / 2, graphics.getFont().getSize());
        } else {
            gameOver(graphics);
        }
    }

    public void gameOver(Graphics graphics) {
        graphics.setColor(Color.RED);
        graphics.setFont(new Font("sans serif", Font.ROMAN_BASELINE, 50));
        FontMetrics metrics = getFontMetrics(graphics.getFont());
        graphics.drawString("Game Over", (WIDTH - metrics.stringWidth("Game Over")) / 2, HEIGHT / 2);

        graphics.setColor(Color.WHITE);
        graphics.setFont(new Font("sans serif", Font.ROMAN_BASELINE, 25));
        metrics = getFontMetrics(graphics.getFont());
        graphics.drawString("Score: " + foodeaten, (WIDTH - metrics.stringWidth("Score: " + foodeaten)) / 2, graphics.getFont().getSize() + 30);

    }
    public void move() {
        for (int i = length; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        if (direction == 'L') {
            x[0] = x[0] - UNIT_SIZE;
        } else if (direction == 'R') {
            x[0] = x[0] + UNIT_SIZE;
        } else if (direction == 'U') {
            y[0] = y[0] - UNIT_SIZE;
        } else {
            y[0] = y[0] + UNIT_SIZE;
        }
    }

    public void addfood() {
        foodx = random.nextInt((int) (WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        foody = random.nextInt((int) (HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
    }

    public void checkfood() {
        if (x[0] == foodx && y[0] == foody) {
            length++;
            foodeaten++;
            addfood();
        }
    }

    public void checkHit() {
        for (int i = length; i > 0; i--) {
            if (x[0] == x[i] && y[0] == y[i]) {
                running = false;
            }
        }

        if (x[0] < 0 || x[0] > WIDTH || y[0] < 0 || y[0] > HEIGHT) {
            running = false;
        }

        if (!running) {
            timer.stop();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkfood();
            checkHit();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
            }
        }
    }
}
