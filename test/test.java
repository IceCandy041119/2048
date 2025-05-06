package test;
import src.function.game.Move;
import src.function.game.PrintSquare;

public class Test {
    public static void main(String[] args) {
        if(testMoveLeft()) {
            System.out.println("testMoveLeft passed");
        } else {
            System.out.println("testMoveLeft failed");
        }
        if(testMoveRight()) {
            System.out.println("testMoveRight passed");
        } else {
            System.out.println("testMoveRight failed");
        }
        if(testMoveUp()) {
            System.out.println("testMoveUp passed");
        } else {
            System.out.println("testMoveUp failed");
        }
        if(testMoveDown()) {
            System.out.println("testMoveDown passed");
        } else {
            System.out.println("testMoveDown failed");
        }
        if(testComprehensive()) {
            System.out.println("testComprehensive passed");
        } else {
            System.out.println("testComprehensive failed");
        }
    } 

    public static boolean testMoveLeft() {
        src.function.game.GameStruct s = new src.function.game.GameStruct();
        s.matrix[0][0] = 4;
        s.matrix[0][1] = 8;
        s.matrix[0][2] = 2;
        s.matrix[0][3] = 2;
        s.matrix[1][0] = 0;
        s.matrix[1][1] = 2;
        s.matrix[1][2] = 8;
        s.matrix[1][3] = 0;
        int[][] expected = {
            {4, 8, 4, 0},
            {2, 8, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0}
        };
        src.function.game.Move.moveLeft(s);
        return java.util.Arrays.deepEquals(s.matrix, expected);
    }

    public static boolean testMoveRight() {
        src.function.game.GameStruct s = new src.function.game.GameStruct();
        s.matrix[0][0] = 4;
        s.matrix[0][1] = 2;
        s.matrix[0][2] = 2;
        s.matrix[0][3] = 0;
        s.matrix[1][0] = 0;
        s.matrix[1][1] = 2;
        s.matrix[1][2] = 8;
        s.matrix[1][3] = 0;
        int[][] expected = {
            {0, 0, 4, 4},
            {0, 0, 2, 8},
            {0, 0, 0, 0},
            {0, 0, 0, 0}
        };
        src.function.game.Move.moveRight(s);
        return java.util.Arrays.deepEquals(s.matrix, expected);
    }

    public static boolean testMoveUp() {
        src.function.game.GameStruct s = new src.function.game.GameStruct();
        s.matrix[2][0] = 4;
        s.matrix[1][0] = 2;
        s.matrix[3][0] = 4;
        int[][] expected = {
            {2, 0, 0, 0},
            {8, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0}
        };
        src.function.game.Move.moveUp(s);
        return java.util.Arrays.deepEquals(s.matrix, expected);
    }

    public static boolean testMoveDown() {
        src.function.game.GameStruct s = new src.function.game.GameStruct();
        s.matrix[0][0] = 4;
        s.matrix[1][0] = 4;
        s.matrix[2][0] = 4;
        int[][] expected = {
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {4, 0, 0, 0},
            {8, 0, 0, 0}
        };
        src.function.game.Move.moveDown(s);
        return java.util.Arrays.deepEquals(s.matrix, expected);
    }
    
    public static boolean testComprehensive(){
        src.function.game.GameStruct s = new src.function.game.GameStruct();
        s.matrix[0][0] = 4;
        s.matrix[0][1] = 2;
        s.matrix[0][2] = 2;
        s.matrix[0][3] = 0;
        s.matrix[1][0] = 2;
        s.matrix[1][1] = 8;
        s.matrix[1][2] = 0;
        s.matrix[1][3] = 0;
        s.matrix[2][0] = 0;
        s.matrix[2][1] = 0;
        s.matrix[2][2] = 8;
        s.matrix[2][3] = 2;
        s.matrix[3][0] = 2;
        s.matrix[3][1] = 4;
        s.matrix[3][2] = 0;
        s.matrix[3][3] = 2;

     /*  {{4,2,2,0},
        {2,8,0,0},
        {0,0,8,2},
        {2,4,0,2}};*/

        Move.moveDown(s);
        Move.moveLeft(s);
        Move.moveRight(s);
        Move.moveUp(s);

        int[][] expected = {
            {0,4,8,4},
            {0,0,16,4},
            {0,0,0,0},
            {0,0,0,0}
        };
        return java.util.Arrays.deepEquals(s.matrix, expected);
    }
}
