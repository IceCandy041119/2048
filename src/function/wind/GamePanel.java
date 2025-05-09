package src.function.wind;

import java.util.HashMap;
import java.util.Map;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import src.function.game.GameStruct;
import src.function.game.IsGameOver;
import src.function.game.Move;
import src.function.game.RandomNumberGenerate;

public class GamePanel extends JPanel implements GameEnvironment {
    private Map<Integer, BufferedImage> imageMap;
    private GameStruct gameStruct;

    public GamePanel(GameStruct gameStruct) {
        this.gameStruct = gameStruct;
        setOpaque(false);
        setSize(200, 200);
        setBounds(0, 0, backgroundHeight , backgroundHeight);
        imageMap = new HashMap<>();
        LoadImage();

        RandomNumberGenerate.generateNumber(gameStruct);
        RandomNumberGenerate.generateNumber(gameStruct);
        
        
    }



    
     
    public void LoadImage(){
        try{
            imageMap.put(2, javax.imageio.ImageIO.read(new java.io.File("pic/2.png")));
            imageMap.put(4, javax.imageio.ImageIO.read(new java.io.File("pic/4.png")));
            imageMap.put(8, javax.imageio.ImageIO.read(new java.io.File("pic/8.png")));
            imageMap.put(16, javax.imageio.ImageIO.read(new java.io.File("pic/16.png")));
            imageMap.put(32, javax.imageio.ImageIO.read(new java.io.File("pic/32.png")));
            imageMap.put(64, javax.imageio.ImageIO.read(new java.io.File("pic/64.png")));
            imageMap.put(128, javax.imageio.ImageIO.read(new java.io.File("pic/128.png")));
            imageMap.put(256, javax.imageio.ImageIO.read(new java.io.File("pic/256.png")));
            imageMap.put(512, javax.imageio.ImageIO.read(new java.io.File("pic/512.png")));
            imageMap.put(1024, javax.imageio.ImageIO.read(new java.io.File("pic/1024.png")));
            imageMap.put(2048, javax.imageio.ImageIO.read(new java.io.File("pic/2048.png")));
        } catch (Exception e) {
            System.out.println("Load iamge error");
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        for(int i = 0 ;i < rows; i++){
            for(int j = 0; j < cols; j++){
                int value = gameStruct.matrix[i][j];
                if(value != 0){
                    BufferedImage image = imageMap.get(value);
                    g2d.drawImage(image, 85 + j * (10 + cellSize), 85 + i * (10 + cellSize), cellSize, cellSize, null);
                }
            }
        }
        g.setFont(getFont().deriveFont(20f));
        g.setColor(Color.orange);
        g.drawString("Score: " + gameStruct.score, 75, 435);

    }

    public void move(int direction){
        switch (direction){
            case KeyEvent.VK_UP:
                Move.moveUp(this.gameStruct);
                break;
            case KeyEvent.VK_DOWN:
                Move.moveDown(this.gameStruct);
                break;
            case KeyEvent.VK_LEFT:
                Move.moveLeft(this.gameStruct);
                break;
            case KeyEvent.VK_RIGHT:
                Move.moveRight(this.gameStruct);
                break;
        }

        if(gameStruct.isMove == 1 || gameStruct.isMerge == 1){
            RandomNumberGenerate.generateNumber(this.gameStruct);
        }

        repaint();

        if(IsGameOver.isGameOver(this.gameStruct)){
            JOptionPane.showMessageDialog(this, "Game Over! Your score is: " + gameStruct.score, "Game Over", JOptionPane.INFORMATION_MESSAGE);
        }
        
        
    }


}
