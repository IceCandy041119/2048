package test;
import src.*;
import src.function.game.printSquare;

public class test {
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
    } 

    public static boolean testMoveLeft() {
        src.function.game.square s = new src.function.game.square();
        s.square[0][0] = 4;
        s.square[0][1] = 2;
        s.square[0][2] = 2;
        s.square[0][3] = 0;
        s.square[1][0] = 0;
        s.square[1][1] = 2;
        s.square[1][2] = 8;
        s.square[1][3] = 0;
        int[][] expected = {
            {4, 4, 0, 0},
            {2, 8, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0}
        };
        src.function.game.move.moveleft(s);
        return java.util.Arrays.deepEquals(s.square, expected);
    }

    public static boolean testMoveRight() {
        src.function.game.square s = new src.function.game.square();
        s.square[0][0] = 4;
        s.square[0][1] = 2;
        s.square[0][2] = 2;
        s.square[0][3] = 0;
        s.square[1][0] = 0;
        s.square[1][1] = 2;
        s.square[1][2] = 8;
        s.square[1][3] = 0;
        int[][] expected = {
            {0, 0, 4, 4},
            {0, 0, 2, 8},
            {0, 0, 0, 0},
            {0, 0, 0, 0}
        };
        src.function.game.move.moveright(s);
        return java.util.Arrays.deepEquals(s.square, expected);
    }
    public static boolean testMoveUp() {
        src.function.game.square s = new src.function.game.square();
        s.square[0][0] = 4;
        s.square[0][1] = 2;
        s.square[0][2] = 2;
        s.square[0][3] = 0;
        s.square[1][0] = 0;
        s.square[1][1] = 2;
        s.square[1][2] = 8;
        s.square[1][3] = 0;
        int[][] expected = {
            {4, 4, 2, 0},
            {0, 0, 8, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0}
        };
        src.function.game.move.moveup(s);
        return java.util.Arrays.deepEquals(s.square, expected);
    }

    public static boolean testMoveDown() {
        src.function.game.square s = new src.function.game.square();
        s.square[0][0] = 4;
        s.square[0][1] = 2;
        s.square[0][2] = 2;
        s.square[0][3] = 0;
        s.square[1][0] = 0;
        s.square[1][1] = 2;
        s.square[1][2] = 8;
        s.square[1][3] = 0;
        int[][] expected = {
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 2, 0},
            {4, 4, 8, 0}
        };
        src.function.game.move.movedown(s);
        return java.util.Arrays.deepEquals(s.square, expected);
    }
}
