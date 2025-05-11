package test;

import java.awt.event.KeyEvent;
import java.util.Random;

import src.function.game.GameStruct;
import src.function.game.Move;
import src.function.game.PrintSquare;
import src.function.game.RandomNumberGenerate;
import src.function.wind.GameEnvironment;

public class test implements GameEnvironment {
    private GameStruct gameStruct;
    private animation[] moveMatrix;

    class animation{
        float fromX;
        float fromY;
        float toX;
        float toY;
        int value; 
    }

    public void printMoveMatrix(){
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                System.out.print(moveMatrix[4 * i + j].value + " ");
            }
            System.out.println();
        }
    }

    public void animationMove(int direction){
        moveMatrix = new animation[16];
        for(int i = 0; i < 16; i++){
            moveMatrix[i] = new animation();
        }
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                moveMatrix[4 * i + j].value = gameStruct.matrix[i][j];
                moveMatrix[4 * i + j].fromY = (float)i;
                moveMatrix[4 * i + j].fromX = (float)j;
            }
        }

        PrintSquare.print(gameStruct);

        switch (direction) {
            case KeyEvent.VK_UP:
                Move.moveUp(this.gameStruct);
                PrintSquare.print(gameStruct);
                calculateDestination(direction);
                printMoveMatrix();
                break;
        
            case KeyEvent.VK_DOWN:
                Move.moveDown(this.gameStruct);
                PrintSquare.print(gameStruct);
                calculateDestination(direction);
                printMoveMatrix();
                break;
            
            case KeyEvent.VK_LEFT:
                Move.moveLeft(this.gameStruct);
                PrintSquare.print(gameStruct);
                calculateDestination(direction);
                printMoveMatrix();
                break;
            
            case KeyEvent.VK_RIGHT:
                Move.moveRight(this.gameStruct);
                PrintSquare.print(gameStruct);
                calculateDestination(direction);
                printMoveMatrix(); 
                break;
        }

    }

    public static void main(String[] args) {
        test test = new test();
       GameStruct gameStruct = new GameStruct();

       
       test.gameStruct = gameStruct;
       test.gameStruct.setMatrixElement(gameStruct, 3, 3, 2);
       test.animationMove(KeyEvent.VK_LEFT);
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
}

