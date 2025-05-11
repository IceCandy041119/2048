package src.function.wind;

import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import src.function.music.PlayBGM;

public class MusicPlayer extends JPanel implements GameEnvironment {
    public MusicPlayer(){
        setOpaque(false);
        setSize(200, 200);
        setBounds(0, 0, backgroundHeight , backgroundHeight);
        new Thread(new Runnable() {
            public void run() {
                PlayBGM.playBGM();
            }
        }).start();
    }

    public void paintComponent(java.awt.Graphics g){
        super.paintComponent(g);
        if(PlayBGM.isBGMPasue == 1){
            Image image = new ImageIcon("pic/pause.png").getImage();
            g.drawImage(image, 0, 0, 80, 80, null);
        }else{
            Image image = new ImageIcon("pic/play.png").getImage();
            g.drawImage(image, 0, 0, 80, 80, null);
        }

        Image image = new ImageIcon("pic/next.png").getImage();
        g.drawImage(image, 0, 70, 80, 80, null);

        image = new ImageIcon("pic/previous.png").getImage();
        g.drawImage(image, 0, 140, 80, 80, null);

        g.setColor(Color.yellow);
        g.fillRoundRect(75, 20, 350, 40,20,20);
        g.setColor(Color.orange);
        g.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 20)); 
        g.drawString("Current BGM: " + PlayBGM.currentMusicName, 80, 45);
    }

    public void pauseBGM(){
        PlayBGM.pauseBGM();
        repaint();
    }

    public void resumeBGM(){
        PlayBGM.resumeBGM();
        repaint();
    }

    public void nextBGM(){
        PlayBGM.nextBGM();
        repaint();
    }

    public void preBGM(){
        PlayBGM.preBGM();
        repaint();
    }

}
