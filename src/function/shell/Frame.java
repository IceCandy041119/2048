package src.function.shell;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;

import javax.swing.ImageIcon;

import src.function.music.PlayBGM;

public class Frame implements GameEnvironment {
    public Frame() {
        // Constructor code here
        javax.swing.JFrame frame = new javax.swing.JFrame("Game Frame");
        frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        frame.setSize(backgroundWidth, backgroundHeight);
        frame.setResizable(false);
        frame.setIconImage(new ImageIcon("pic/2048.png").getImage());
        frame.setLocationRelativeTo(null); // Center the frame on the screen
        GameBackground gbg = new GameBackground();
        frame.add(gbg);
        gbg.setLayout(null);
        GamePanel gamePanel = new GamePanel(new src.function.core.GameStruct());
        gbg.add(gamePanel);

        MusicPlayer musicPlayer = new MusicPlayer();
        gbg.add(musicPlayer);

        gbg.setFocusable(true);
        gbg.requestFocusInWindow(); // Request focus for the grid panel
        gbg.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE)
                    if (PlayBGM.isBGMPasue == 1) {
                        PlayBGM.isBGMPasue = 0;
                        musicPlayer.resumeBGM();
                    } else {
                        PlayBGM.isBGMPasue = 1;
                        musicPlayer.pauseBGM();
                    }
                else if(e.getKeyCode() == KeyEvent.VK_N)
                    musicPlayer.nextBGM();
                else if(e.getKeyCode() == KeyEvent.VK_M)
                    musicPlayer.preBGM();
                else if(e.getKeyCode() == KeyEvent.VK_R)
                    gamePanel.restartGame();
                else if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT)
                   gamePanel.animationMove(e.getKeyCode());
            }
        });
        gbg.addMouseListener(new MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int mouseClickX = e.getX() - 250;
                int mouseClickY = e.getY() - 250;
                if (mouseClickX < -180 && mouseClickY < 30 && mouseClickX > -240 && mouseClickY > -240) {
                    if(mouseClickY <= -180 && mouseClickY >= -240){
                        if (PlayBGM.isBGMPasue == 1) {
                            PlayBGM.isBGMPasue = 0;
                            musicPlayer.resumeBGM();
                        } else {
                            PlayBGM.isBGMPasue = 1;
                            musicPlayer.pauseBGM();
                        }
                    }else if(mouseClickY <= -110 && mouseClickY >= -170){
                        musicPlayer.nextBGM();
                    }else if(mouseClickY <= -40 && mouseClickY >= -100){
                        musicPlayer.preBGM();
                    }else if(mouseClickY <= 30 && mouseClickY >= -30){
                        gamePanel.restartGame();
                    }
                } else {
                    if (mouseClickX < 0) {
                        if (Math.abs(mouseClickX) > Math.abs(mouseClickY)) {
                            gamePanel.animationMove(37);
                        } else {
                            if (mouseClickY < 0) {
                                gamePanel.animationMove(38);

                            } else {
                                gamePanel.animationMove(40);
                            }
                        }
                    } else {
                        if (Math.abs(mouseClickX) > Math.abs(mouseClickY)) {
                            gamePanel.animationMove(39);
                        } else {
                            if (mouseClickY < 0) {
                                gamePanel.animationMove(38);

                            } else {
                                gamePanel.animationMove(40);
                            }
                        }
                    }
                }
                System.out.println("Mouse clicked at: " + mouseClickX + ", " + mouseClickY);
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
