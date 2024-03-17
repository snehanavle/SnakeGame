package snakegame;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.logging.Logger;

import javax.swing.*;

public class SnakeGame extends JFrame {
    
    SnakeGame() {
    	
        super("Snake Game");
        System.out.println("Snake constructor called");
        add(new Board());
        pack();
        
        setLocationRelativeTo(null);
        setResizable(false);
    }

    public static void main(String[] args)  {
        
        
        
        try {
        	System.setOut(new PrintStream(new FileOutputStream("output.txt")));
        }catch(Exception e) {
        	e.printStackTrace();
        }
        
        new SnakeGame().setVisible(true);
        
        
        
    }
}