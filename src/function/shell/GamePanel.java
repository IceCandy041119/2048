package src.function.shell;

import java.util.HashMap;
import java.util.Map;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import src.function.core.GameStruct;
import src.function.core.IsGameOver;
import src.function.core.Move;
import src.function.core.RandomNumberGenerate;
import src.function.music.MoveSound;



public class GamePanel extends JPanel implements GameEnvironment {
    private Map<Integer, BufferedImage> imageMap;
    private GameStruct gameStruct;
    private animation[] moveMatrix;
    private boolean isAnimating = false;
    private float mergeCellSize = 0;
    private int randomNumberIndex = 0;
    private float randomNumberCellSize = 75;

    class animation{
        float fromX;
        float fromY;
        float toX;
        float toY;
        int value; 
    }

    public void animationMove(int direction){
        if(isAnimating)
            return ;

        switch (direction) {
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

        if(!(gameStruct.isMove == 1 || gameStruct.isMerge == 1)){
            return;
        }


        if(gameStruct.isMove == 1 || gameStruct.isMerge == 1){
            MoveSound.playMoveSound();
        }
    
        calculateDestination(direction);

    isAnimating = true;
    new Thread(()->{    
        long x = System.currentTimeMillis();
        long animationTime = 200;
        //long frameTime = 1000 / 60;
        //long nextFrame = x;
        while(System.currentTimeMillis() - x < animationTime){
            long currentTime = System.currentTimeMillis();
            //while((currentTime = System.currentTimeMillis()) < nextFrame);
            //nextFrame += frameTime; 

            float elapsed = currentTime - x;
            float progress = Math.min((elapsed / animationTime),1.0f);

            boolean alldone = true;
            for(int i = 0; i < 16;i++){
                if(moveMatrix[i].value != 0){
                    moveMatrix[i].fromX = moveMatrix[i].fromX + (moveMatrix[i].toX - moveMatrix[i].fromX) * progress;
                    moveMatrix[i].fromY = moveMatrix[i].fromY + (moveMatrix[i].toY - moveMatrix[i].fromY) * progress;

                    if(Math.abs(moveMatrix[i].fromX - moveMatrix[i].toX) > 0.01 || Math.abs(moveMatrix[i].fromY - moveMatrix[i].toY) > 0.01)
                        alldone = false;
                }
            }
            
            SwingUtilities.invokeLater(this::repaint);

            if(alldone)
                break;

            try{
                Thread.sleep(16);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }


        SwingUtilities.invokeLater(()->{
            if(gameStruct.isMerge == 1 || gameStruct.isMove == 1){
                randomNumberIndex = RandomNumberGenerate.generateNumber(gameStruct);
                System.out.println(randomNumberIndex);
                new Thread(()->{
                    randomNumberCellSize = 37;
                    float randomNumberEnlarge = 80;

                    long z = System.currentTimeMillis();
                    int randomNumberTime = 300;

                    while(System.currentTimeMillis() - z < randomNumberTime){
                        long currentRandomTime = System.currentTimeMillis();
                        float elapsedRandom = currentRandomTime - z;
                        float progressRandom = elapsedRandom / randomNumberTime;

                        if(progressRandom < 0.5){
                            randomNumberCellSize = (float) (randomNumberCellSize + (randomNumberEnlarge - randomNumberCellSize) * (progressRandom / 0.5));
                        }else{
                            randomNumberCellSize = (float) (randomNumberCellSize + (cellSize - randomNumberCellSize) * ((progressRandom - 0.5f) / 0.5f ));
                        }

                        SwingUtilities.invokeLater(this::repaint);

                        try{
                            Thread.sleep(16);
                        }catch(InterruptedException e){
                            e.printStackTrace();
                        }

                    }
                    
                }).start();

            }
            syncMatrix();
            repaint();
            isAnimating = false;
            if(gameStruct.isMerge == 1){
                new Thread(()->{
                    mergeCellSize = 37;
                    float mergeEnlarge = 80;

                    long y = System.currentTimeMillis();
                    int mergeTime = 300;
                    while((System.currentTimeMillis() - y) < mergeTime){
                        long currentMergeTime = System.currentTimeMillis();
                        float elapsedMerge = currentMergeTime - y;
                        float progressMerge = elapsedMerge / mergeTime;
                        if(progressMerge < 0.5f){
                            mergeCellSize = mergeCellSize + (mergeEnlarge - mergeCellSize) * (progressMerge / 0.5f);
                        }else{
                            mergeCellSize = mergeCellSize + (cellSize - mergeCellSize) * ((progressMerge - 0.5f) / 0.5f);
                        }
                        SwingUtilities.invokeLater(this::repaint);
                    
                        try{
                            Thread.sleep(16);
                        }catch(InterruptedException e){
                            e.printStackTrace();
                        }
                    
                    
                    }

                }).start();
            }
            if(IsGameOver.isGameOver(gameStruct)){
                JOptionPane.showMessageDialog(this, "Game Over! Your score is: " + gameStruct.score + "\nPress 'R' to restart game", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        });



    }).start();
  

    

    }

    public void calculateDestination(int direction){
        switch(direction){
            case KeyEvent.VK_UP:
                for(int j = 0;j < cols;j++){
                    if(gameStruct.matrix[0][j] == 0)
                        continue;
                    int targetRow = 0;
                    for(int i = 0;i < rows;i++){
                        int idx = 4 * i + j;
                        if(moveMatrix[idx].value == 0)
                            continue;
                        if(gameStruct.isMergeMatrix[targetRow][j]){
                            moveMatrix[idx].toX = j;
                            moveMatrix[idx].toY = targetRow;
                            int k = findNextNonEmpte(i + 1, j, direction);
                            if(k == -1)
                            break;
                            idx = 4 * k + j;
                            moveMatrix[idx].toX = j;
                            moveMatrix[idx].toY = targetRow;
                            targetRow++;
                            i = k;
                        }else{
                            moveMatrix[idx].toX = j;
                            moveMatrix[idx].toY = targetRow;
                            targetRow++;
                        }
                    }
                }
                break;
            case KeyEvent.VK_DOWN:
                for(int j = cols - 1; j >= 0;j--){
                    if(gameStruct.matrix[rows - 1][j] == 0)
                        continue;
                    int targetRow = rows - 1;
                    for(int i = rows - 1; i >= 0;i--){
                        int idx = i * 4 + j;
                        if(moveMatrix[idx].value == 0)
                            continue;
                        if(gameStruct.isMergeMatrix[targetRow][j]){
                            moveMatrix[idx].toX = j;
                            moveMatrix[idx].toY = targetRow;
                            int k = findNextNonEmpte(i - 1, j, direction);
                            if(k == -1)
                                break;
                            idx = 4 * k + j;
                            moveMatrix[idx].toX = j;
                            moveMatrix[idx].toY = targetRow;
                            targetRow--;
                            i = k;
                        }else{
                            moveMatrix[idx].toX = j;
                            moveMatrix[idx].toY = targetRow;
                            targetRow--;
                        }
                    }
                }
                break;
            case KeyEvent.VK_LEFT:
                 for(int i = 0; i < rows;i++){
                    if(gameStruct.matrix[i][0] == 0)
                        continue;
                    int targetCol = 0;
                    for(int j = 0; j < cols;j++){
                        int idx = i * 4 + j;
                        if(moveMatrix[idx].value == 0)
                            continue;
                        if(gameStruct.isMergeMatrix[i][targetCol]){
                            moveMatrix[idx].toX = targetCol; 
                            moveMatrix[idx].toY = i;
                            int k = findNextNonEmpte(j + 1, i, direction);
                            if(k == -1)
                                break;
                            idx = 4 * i + k;
                            moveMatrix[idx].toX = targetCol;
                            moveMatrix[idx].toY = i;
                            targetCol++;
                            j = k;
                        }else{
                            moveMatrix[idx].toX = targetCol; 
                            moveMatrix[idx].toY = i;
                            targetCol++;
                        }
                    }
                }
                
                break;
            case KeyEvent.VK_RIGHT:
                for(int i = rows - 1; i >= 0;i--){
                    if(gameStruct.matrix[i][cols - 1] == 0)
                        continue;
                    int targetCol = cols - 1;
                    for(int j = cols - 1; j >= 0;j --){
                        int idx = i * 4 + j;
                        if(moveMatrix[idx].value == 0)
                            continue;
                        if(gameStruct.isMergeMatrix[i][targetCol]){
                            moveMatrix[idx].toX = targetCol; 
                            moveMatrix[idx].toY = i;
                            int k = findNextNonEmpte(j - 1, i, direction);
                            if(k == -1)
                                break;
                            idx = 4 * i + k;
                            moveMatrix[idx].toX = targetCol;
                            moveMatrix[idx].toY = i;
                            targetCol--;
                            j = k;
                        }else{
                            moveMatrix[idx].toX = targetCol; 
                            moveMatrix[idx].toY = i;
                            targetCol--;
                        }
                    }
                }
                break;
        }
    }

    public int findNextNonEmpte(int start,int fixed,int direction){
        switch(direction){
            case KeyEvent.VK_UP:
                for(int i = start;i < rows;i++){
                    int idx = 4 * i + fixed;
                    if(moveMatrix[idx].value != 0)
                        return i;
                }
                break;
            case KeyEvent.VK_DOWN:
                for(int i = start;i >= 0;i--){
                    int idx = 4 * i + fixed;
                    if(moveMatrix[idx].value != 0 )
                        return i;
                }
                break;
            case KeyEvent.VK_LEFT:
                for(int j = start;j < cols;j++){
                    int idx = 4 * fixed + j;
                    if(moveMatrix[idx].value != 0)
                        return j;
                }
                break;
            case KeyEvent.VK_RIGHT:
                for(int j = start;j >= 0;j--){
                    int idx = 4 * fixed + j;
                    if(moveMatrix[idx].value != 0)
                        return j;
                }
                break;
        } 
        return -1;
    }


    public GamePanel(GameStruct gameStruct) {
        this.gameStruct = gameStruct;
        setOpaque(false);
        setSize(200, 200);
        setBounds(0, 0, backgroundHeight , backgroundHeight);
        imageMap = new HashMap<>();
        LoadImage();
        randomNumberIndex = RandomNumberGenerate.generateNumber(gameStruct);

        moveMatrix = new animation[16];
        for(int i = 0; i < 16; i++){
            moveMatrix[i] = new animation();
        }

        syncMatrix(); 
        
    }

    public void syncMatrix(){
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                moveMatrix[4 * i + j].value = gameStruct.matrix[i][j];
                moveMatrix[4 * i + j].fromY = (float)i;
                moveMatrix[4 * i + j].toY = (float)i;
                moveMatrix[4 * i + j].fromX = (float)j;
                moveMatrix[4 * i + j].toX = (float)j;
            }
        }
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
            System.out.println("Load image error");
        }
    }

    public void paintComponent(Graphics g){
    Graphics2D g2d = (Graphics2D) g;
    drawMove(g2d);
    drawMerge(g2d);
    drawRandomNuber(g2d);
    g.setFont(getFont().deriveFont(20f));
    g.setColor(Color.orange);
    g.drawString("Score: " + gameStruct.score, 75, 435);

    }

    public void drawMove(Graphics2D g2d){
        for (int i = 0; i < 16; i++) {
            if (moveMatrix[i].value != 0 && !gameStruct.isMergeMatrix[i / 4][i % 4] && i != randomNumberIndex) {
                BufferedImage image = imageMap.get(moveMatrix[i].value);
                if (image != null) {
                    g2d.drawImage(image, 85 + (int) (moveMatrix[i].fromX * (10 + cellSize)),
                            85 + (int) (moveMatrix[i].fromY * (10 + cellSize)), cellSize, cellSize, null);
                }
            }
        }
    }

    public void drawRandomNuber(Graphics2D g2d){
        BufferedImage image = imageMap.get(moveMatrix[randomNumberIndex].value);
        g2d.drawImage(image, 85 + (int)(moveMatrix[randomNumberIndex].fromX * (10 + cellSize)) + ((cellSize - (int)randomNumberCellSize)/2),85 + (int)(moveMatrix[randomNumberIndex].fromY * (10 + cellSize)) + ((cellSize - (int)randomNumberCellSize)/2),(int)randomNumberCellSize,(int)randomNumberCellSize,null);
    }

    public void drawMerge(Graphics2D g2d){
        for (int i = 0; i < 16; i++) {
            if (moveMatrix[i].value != 0 && gameStruct.isMergeMatrix[i / 4][i % 4]) {
                BufferedImage image = imageMap.get(moveMatrix[i].value);
                if (image != null) {
                    g2d.drawImage(image, 85 + (int) (moveMatrix[i].fromX * (10 + cellSize)) + ((cellSize - (int)mergeCellSize)/2),
                            85 + (int) (moveMatrix[i].fromY * (10 + cellSize)) + ((cellSize - (int)mergeCellSize) / 2), (int)mergeCellSize, (int)mergeCellSize, null);
                }
            }
        }
    }

    public void restartGame(){
        gameStruct.reStart();
        syncMatrix();
        repaint();
    }

}
