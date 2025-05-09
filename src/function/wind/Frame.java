package src.function.wind;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;

public class Frame implements GameEnvironment {
   public Frame() {
      // Constructor code here
        javax.swing.JFrame frame = new javax.swing.JFrame("Game Frame");
        frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        frame.setSize(backgroundWidth, backgroundHeight);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null); // Center the frame on the screen
        Grid grid = new Grid();
        frame.add(grid);
        grid.setLayout(null);
        GamePanel gamePanel = new GamePanel(new src.function.game.GameStruct());
        grid.add(gamePanel);
        
        
        grid.setFocusable(true);
        grid.requestFocusInWindow(); // Request focus for the grid panel
        grid.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e){
                gamePanel.move(e.getKeyCode());
            }
        });
        grid.addMouseListener(new MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int mouseClickX = e.getX() - 250;
                int mouseClickY = e.getY() - 250;
                if(mouseClickX < 0){
                    if(Math.abs(mouseClickX) > Math.abs(mouseClickY)){
                        gamePanel.move(37);
                    }else{
                        if(mouseClickY < 0){
                            gamePanel.move(38);
                    
                        }else{
                            gamePanel.move(40);
                        }
                    }
                }else{
                    if(Math.abs(mouseClickX) > Math.abs(mouseClickY)){
                        gamePanel.move(39);
                    }else{
                        if(mouseClickY < 0){
                            gamePanel.move(38);
                    
                        }else{
                            gamePanel.move(40);
                        }
                    }
                }
                //System.out.println("Mouse clicked at: " + mouseClickX + ", " + mouseClickY);
            }
        });

        frame.setVisible(true);
   }
   public static void main(String[] args) {
      // Main method code here
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Frame(); // Create an instance of the Frame class
            }
        });
    }
}
