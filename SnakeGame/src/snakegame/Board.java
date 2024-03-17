package snakegame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class Board extends JPanel implements ActionListener {
    
    private Image apple;
    private Image dot;
    private Image head;
    
    private final int ALL_DOTS = 900;
    private final int DOT_SIZE = 10;
    private final int RANDOM_POSITION = 29;
    
    private int apple_x;
    private int apple_y;
    
    private final int x[] = new int[ALL_DOTS];
    private final int y[] = new int[ALL_DOTS];
    
    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    
    private boolean inGame = true;
    
    private int dots;
    private Timer timer;
    
    Board() {
    	System.out.println("Board called ");
        addKeyListener(new TAdapter());
        
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(300, 300));
        setFocusable(true);
        
        loadImages();
        initGame();
    }
    
    public void loadImages() {
		System.out.println("loadImage gets called ");
		System.out.println(System.getProperty("user.dir"));
		
		ImageIcon i1 = new ImageIcon(System.getProperty("user.dir") + "\\src\\snakegame\\apple.png");
		System.out.println(i1);
		apple=i1.getImage();
		System.out.println(apple);
		
		ImageIcon i2 = new ImageIcon(System.getProperty("user.dir") + "\\src\\snakegame\\dot.png");
		dot=i2.getImage();
		
		ImageIcon i3 = new ImageIcon(System.getProperty("user.dir") + "\\src\\snakegame\\head.png");
		head=i3.getImage();
		
//		ImageIcon i3 = new ImageIcon(System.getProperty("user.dir") + "\\src\\snakegame\\head.png");
//		head=i3.getImage();
		
//		ImageIcon i1 = new ImageIcon(getClass().getClassLoader().getResource("./snakegame/dot.png"));
////		apple = i1.getImage();
//		
//		ImageIcon i2 =new ImageIcon(ClassLoader.getSystemResource("C:/Users/2076008/eclipse-workspace/SnakeGame/apple.png"));
//		dot = i2.getImage();
//			
		}
    
    public void initGame() {
    	System.out.println("init game called ");
        dots = 3;
        System.out.println("Dots in initAGme " + dots);
        
        for (int i = 0; i < dots; i++) {
            y[i] = 50;
            x[i] = 50 - i * DOT_SIZE;
        }
        System.out.println("X array "+Arrays.toString(x));
		System.out.println("Y array "+Arrays.toString(y));
        
        locateApple();
        
        timer = new Timer(140, this);
        timer.start();
    }
    
    public void locateApple() {
    	System.out.println("locateapple called");
        int r = (int)(Math.random() * RANDOM_POSITION);
        apple_x = r * DOT_SIZE;
        System.out.println("Apple x coordinate "+ apple_x);
                
        r = (int)(Math.random() * RANDOM_POSITION);
        apple_y = r * DOT_SIZE;
        System.out.println("Apple y coordinate "+ apple_y);
    }
    
    public void paintComponent(Graphics g) {
    	
        super.paintComponent(g);
        System.out.println("paintcomponents called ");

        
        draw(g);
    }
    
    public void draw(Graphics g) {
    	System.out.println("draw called ");
    	System.out.println("Dots in draw " + dots);
        if (inGame) {
            g.drawImage(apple, apple_x, apple_y, this);

            for (int i = 0 ; i < dots; i++) {
                if (i == 0) {
                    g.drawImage(head, x[i], y[i], this);
                } else {
                    g.drawImage(dot, x[i], y[i], this);
                }
            }

            Toolkit.getDefaultToolkit().sync();
        } else {
            gameOver(g);
        }
    }
    
    public void gameOver(Graphics g) {
    	System.out.println("Graphics called ");
        String msg = "Game Over!";
        Font font = new Font("SAN_SERIF", Font.BOLD, 14);
        FontMetrics metrices = getFontMetrics(font);
        
        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(msg, (300 - metrices.stringWidth(msg)) / 2, 300/2);
    }
    
    public void move() {
    	System.out.println("move called ");
		System.out.println("Dots in move() " + dots);
        for (int i = dots ; i > 0 ; i--) {
        	
        	System.out.println("i the value " +i);
			System.out.println("X array BL "+Arrays.toString(x));
			System.out.println("Y array BL "+Arrays.toString(y));
			
            x[i] = x[i - 1];
            System.out.println("x[" + i + "] "+ x[i]);
			System.out.println("x[ " + (i-1) + "] " +x[i-1]);
			
            y[i] = y[i - 1];
            System.out.println("y[ " + i + "] " +y[i]);
			System.out.println("y[ " + (i-1) + "] " +y[i-1]);
        }
        
        if (leftDirection) {
            x[0] = x[0] - DOT_SIZE;
        }
        if (rightDirection) {
            x[0] = x[0] + DOT_SIZE;
        }
        if (upDirection) {
            y[0] = y[0] - DOT_SIZE;
        }
        if (downDirection) {
            y[0] = y[0] + DOT_SIZE;
        }
    }
    
    public void checkApple() {
    	System.out.println("Check apple called ");
        if ((x[0] == apple_x) && (y[0] == apple_y)) {
            dots++;
            locateApple();
        }
    }
    
    public void checkCollision() {
    	System.out.println("check collision called ");
        for(int i = dots; i > 0; i--) {
            if (( i > 4) && (x[0] == x[i]) && (y[0] == y[i])) {
                inGame = false;
            }
        }
        
        if (y[0] >= 300) {
            inGame = false;
        }
        if (x[0] >= 300) {
            inGame = false;
        }
        if (y[0] < 0) {
            inGame = false;
        }
        if (x[0] < 0) {
            inGame = false;
        }
        
        if (!inGame) {
            timer.stop();
        }
    }
    
    public void actionPerformed(ActionEvent ae) {
    	System.out.println("actionperformed called ");
        if (inGame) {
            checkApple();
            checkCollision();
            move();
        }
        
        repaint();
    }
    
    public class TAdapter extends KeyAdapter {
    	
        @Override
        public void keyPressed(KeyEvent e) {
        	System.out.println("TAadpater keypressed called ");
            int key = e.getKeyCode();
            
            if (key == KeyEvent.VK_LEFT && (!rightDirection)) {
            	
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }
            
            if (key == KeyEvent.VK_RIGHT && (!leftDirection)) {
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }
            
            if (key == KeyEvent.VK_UP && (!downDirection)) {
                upDirection = true;
                leftDirection = false;
                rightDirection = false;
            }
            
            if (key == KeyEvent.VK_DOWN && (!upDirection)) {
                downDirection = true;
                leftDirection = false;
                rightDirection = false;
            }
        }
    }
    
}



























