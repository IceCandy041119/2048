package src.function.shell;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;

import src.function.music.PlayBGM;

public class teFrame implements GameEnvironment {
    public teFrame() {
        // Constructor code here
        javax.swing.JFrame frame = new javax.swing.JFrame("teGame Frame");
        frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        frame.setSize(backgroundWidth, backgroundHeight);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null); // Center the frame on the screen
        GameBackground gbg = new GameBackground();
        frame.add(gbg);
        gbg.setLayout(null);
        teGamePane gamePanel = new teGamePane(new src.function.core.GameStruct());
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
                else
                   // gamePanel.move(e.getKeyCode());
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
                            gamePanel.move(37);
                        } else {
                            if (mouseClickY < 0) {
                                gamePanel.move(38);

                            } else {
                                gamePanel.move(40);
                            }
                        }
                    } else {
                        if (Math.abs(mouseClickX) > Math.abs(mouseClickY)) {
                            gamePanel.move(39);
                        } else {
                            if (mouseClickY < 0) {
                                gamePanel.move(38);

                            } else {
                                gamePanel.move(40);
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
                new teFrame(); // Create an instance of the Frame class
            }
        });
    }
}
