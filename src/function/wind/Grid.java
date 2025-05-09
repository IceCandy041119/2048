package src.function.wind;

import java.awt.Graphics;

import javax.swing.JPanel;


import java.awt.Color;

public class Grid extends JPanel implements GameEnvironment{

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBackground(g);
        drawGrid(g);
        drawSpaceCell(g);
    }

    public void drawBackground(Graphics g) {
        g.setColor(Color.orange);
        g.fillRect(0, 0, backgroundWidth, backgroundHeight);
    }

    public void drawGrid(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillRoundRect(75,75,matrixSize,matrixSize + 15,20,20);
    }

    public void drawSpaceCell(Graphics g){
        g.setColor(Color.pink);

        for(int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                g.fillRoundRect(85 + i * (10 + cellSize), 85 + j * (10 + cellSize), cellSize, cellSize, 20, 20);
            }
        }
    }

}