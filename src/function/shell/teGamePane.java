package src.function.shell;
import javax.swing.*;

import src.function.core.GameStruct;
import src.function.core.IsGameOver;
import src.function.core.Move;
import src.function.core.RandomNumberGenerate;
import src.function.music.MoveSound;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class teGamePane extends JPanel implements GameEnvironment{
    private Map<Integer, BufferedImage> imageMap;
    private GameStruct gameStruct;
    private animation[] moveMatrix;
    private boolean isAnimating = false; // 标记是否正在执行动画

    class animation {
        float fromX;
        float fromY;
        float toX;
        float toY;
        int value;
    }

    public teGamePane(GameStruct gameStruct) {
        this.gameStruct = gameStruct;
        setOpaque(false);
        setSize(200, 200);
        setBounds(0, 0, backgroundHeight, backgroundHeight);
        imageMap = new HashMap<>();
        LoadImage();
        RandomNumberGenerate.generateNumber(gameStruct);

        moveMatrix = new animation[16];
        for (int i = 0; i < 16; i++) {
            moveMatrix[i] = new animation();
        }
        syncMoveMatrix(); // 初始化 moveMatrix
    }

    // 同步 moveMatrix 和 gameStruct.matrix
    private void syncMoveMatrix() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                moveMatrix[4 * i + j].value = gameStruct.matrix[i][j];
                moveMatrix[4 * i + j].fromX = (float) j;
                moveMatrix[4 * i + j].fromY = (float) i;
                moveMatrix[4 * i + j].toX = (float) j;
                moveMatrix[4 * i + j].toY = (float) i;
            }
        }
    }

    public void animationMove(int direction) {
        if (isAnimating) return; // 防止重复触发动画

        // 执行移动逻辑
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

        if (!(gameStruct.isMove == 1 || gameStruct.isMerge == 1)) {
            return; // 如果没有移动或合并，直接返回
        }

        // 计算目标位置
        calculateDestination(direction);

        // 播放移动音效
        if (gameStruct.isMove == 1 || gameStruct.isMerge == 1) {
            MoveSound.playMoveSound();
        }

        // 启动动画线程
        isAnimating = true;
        new Thread(() -> {
            long lastTime = System.nanoTime();
            float animationDuration = 200; // 动画持续时间（毫秒）
            float elapsedTime = 0;
            int fps = 0;
            while (elapsedTime < animationDuration) {
                // 计算时间步
                fps++;
                long currentTime = System.nanoTime();
                float deltaTime = (currentTime - lastTime) / 1_000_000.0f; // 转换为毫秒
                lastTime = currentTime;
                elapsedTime += deltaTime;

                // 更新所有动画对象的坐标
                float progress = Math.min(elapsedTime / animationDuration, 1.0f); // 动画进度 [0,1]
                boolean allDone = true;

                for (int i = 0; i < 16; i++) {
                    if (moveMatrix[i].value != 0) {
                        // 使用线性插值计算当前位置
                        moveMatrix[i].fromX = moveMatrix[i].fromX + (moveMatrix[i].toX - moveMatrix[i].fromX) * progress;
                        moveMatrix[i].fromY = moveMatrix[i].fromY + (moveMatrix[i].toY - moveMatrix[i].fromY) * progress;

                        // 检查是否接近目标
                        if (Math.abs(moveMatrix[i].fromX - moveMatrix[i].toX) > 0.01 ||
                            Math.abs(moveMatrix[i].fromY - moveMatrix[i].toY) > 0.01) {
                            allDone = false;
                        }
                    }
                }

                // 在 EDT 上触发重绘
                SwingUtilities.invokeLater(this::repaint);

                // 控制帧率
                try {
                    Thread.sleep(16); // 约 60 FPS
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (allDone) break; // 所有动画完成，退出循环
            }
            System.out.println("fps: " + fps);
            fps = 0;
            // 动画完成后，更新游戏状态
            SwingUtilities.invokeLater(() -> {
                // 将 moveMatrix 的最终位置同步到 gameStruct.matrix
                for (int i = 0; i < 16; i++) {
                    if (moveMatrix[i].value != 0) {
                        int row = (int) moveMatrix[i].toY;
                        int col = (int) moveMatrix[i].toX;
                        moveMatrix[i].value = gameStruct.matrix[row][col];

                    }
                }

                // 生成新数字
                if (gameStruct.isMove == 1 || gameStruct.isMerge == 1) {
                    RandomNumberGenerate.generateNumber(gameStruct);
                }

                // 同步 moveMatrix
                syncMoveMatrix();

                // 检查游戏是否结束
                if (IsGameOver.isGameOver(gameStruct)) {
                    JOptionPane.showMessageDialog(this, "Game Over! Your score is: " + gameStruct.score + "\nPress 'R' to restart game", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                }

                isAnimating = false;
                repaint();
            });
        }).start();
    }



    public void calculateDestination(int direction){
        switch (direction) {
            case KeyEvent.VK_UP:
                for(int j = 0; j < cols; j++){
                    if(gameStruct.matrix[0][j] == 0)
                        continue;
                    int k = 0;
                    for(int i = 0; i < rows; i++){
                        if(gameStruct.isMergeMatrix[i][j] == true){
                            for(;moveMatrix[4 * k + j].value == 0 && k <= 2; k++);
                            if(k >= 3)
                                break;
                            moveMatrix[4 * k + j].toX = (float)j;
                            moveMatrix[4 * k + j].toY = (float)i;
                            moveMatrix[4 * (k + 1) + j].toX = (float)j;
                            moveMatrix[4 * (k + 1) + j].toY = (float)i;
                            k++;
                        } else {
                            for(;moveMatrix[4 * k + j].value == 0 && k <= 2; k++);
                            if(k == 3 && moveMatrix[4 * k + j].value == 0){
                                break;
                            }
                            moveMatrix[4 * k + j].toX = (float)j;
                            moveMatrix[4 * k + j].toY = (float)i;
                        }
                        k++;
                        if(k >= 4){
                            break;
                        }
                    }
                }
                break;
        
            case KeyEvent.VK_DOWN:
                for(int j = cols -1; j >= 0; j--){
                    if(gameStruct.matrix[3][j] == 0)
                        continue;
                    int k = 3;
                    for(int i = 3; i >= 0; i--){
                        if(gameStruct.isMergeMatrix[i][j] == true){
                            for(;moveMatrix[4 * k + j].value == 0 && k >= 1; k--);
                            if(k <= 0)
                                break;
                            moveMatrix[4 * k + j].toX = (float)j;
                            moveMatrix[4 * k + j].toY = (float)i;
                            moveMatrix[4 * (k - 1) + j].toX = (float)j;
                            moveMatrix[4 * (k - 1) + j].toY = (float)i;
                            k--;
                        } else {
                            for(;moveMatrix[4 * k + j].value == 0 && k >= 1; k--);
                            if(k == 0 && moveMatrix[4 * k + j].value == 0){
                                break;
                            }
                            moveMatrix[4 * k + j].toX = (float)j;
                            moveMatrix[4 * k + j].toY = (float)i;
                        }
                        k--;
                        if(k < 0){
                            break;
                        }
                    }
                }
                break;

            case KeyEvent.VK_LEFT:
                for(int i = 0; i < rows; i++){
                    if(gameStruct.matrix[i][0] == 0)
                        continue;
                    int k = 0;
                    for(int j = 0; j < cols; j++){
                        if(gameStruct.isMergeMatrix[i][j] == true){
                            for(;moveMatrix[4 * i + k].value == 0 && k <= 2; k++);
                            if(k >= 3) 
                                break;
                            moveMatrix[4 * i + k].toX = (float)j;
                            moveMatrix[4 * i + k].toY = (float)i;
                            moveMatrix[4 * i + (k + 1)].toX = (float)j;
                            moveMatrix[4 * i + (k + 1)].toY = (float)i;
                            k++;
                        } else {
                            for(;moveMatrix[4 * i + k].value == 0 && k <= 2; k++);
                            if(k == 3 && moveMatrix[4 * i + k].value == 0){
                                break;
                            }
                            moveMatrix[4 * i + k].toX = (float)j;
                            moveMatrix[4 * i + k].toY = (float)i;
                        }
                        k++;
                        if(k >= 4){
                            break;
                        }
                    }
                }
                break;
            
            case KeyEvent.VK_RIGHT:
                for(int i = 0; i < rows; i++){
                    if(gameStruct.matrix[i][3] == 0)
                        continue;
                    int k = 3;
                    for(int j = 3; j >= 0; j--){
                        if(gameStruct.isMergeMatrix[i][j] == true){
                            for(;moveMatrix[4 * i + k].value == 0 && k >= 1; k--);
                            if(k <= 0)
                                break;
                            moveMatrix[4 * i + k].toX = (float)j;
                            moveMatrix[4 * i + k].toY = (float)i;
                            moveMatrix[4 * i + (k - 1)].toX = (float)j;
                            moveMatrix[4 * i + (k - 1)].toY = (float)i;
                            k--;
                        } else {
                            for(;moveMatrix[4 * i + k].value == 0 && k >= 1; k--);
                            if(k == 0 && moveMatrix[4 * i + k].value == 0){
                                break;
                            }
                            moveMatrix[4 * i + k].toX = (float)j;
                            moveMatrix[4 * i + k].toY = (float)i;
                        }
                        k--;
                        if(k < 0){
                            break;
                        }
                    }
                }
                break;
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        for (int i = 0; i < 16; i++) {
            if (moveMatrix[i].value != 0) {
                BufferedImage image = imageMap.get(moveMatrix[i].value);
                g2d.drawImage(image, 85 + (int) (moveMatrix[i].fromX * (10 + cellSize)),
                        85 + (int) (moveMatrix[i].fromY * (10 + cellSize)), cellSize, cellSize, null);
            }
        }
        g.setFont(getFont().deriveFont(20f));
        g.setColor(Color.orange);
        g.drawString("Score: " + gameStruct.score, 75, 435);
    }

    public void move(int direction) {
        if (gameStruct.isMove == 1 || gameStruct.isMerge == 1)
            MoveSound.playMoveSound();
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

        if (gameStruct.isMove == 1 || gameStruct.isMerge == 1) {
            RandomNumberGenerate.generateNumber(this.gameStruct);
        }

        syncMoveMatrix(); // 同步 moveMatrix
        repaint();

        if (IsGameOver.isGameOver(this.gameStruct)) {
            JOptionPane.showMessageDialog(this, "Game Over! Your score is: " + gameStruct.score + "\nPress 'R' to restart game", "Game Over", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void restartGame() {
        gameStruct.reStart();
        syncMoveMatrix();
        repaint();
    }

    public void LoadImage() {
        try {
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
}
