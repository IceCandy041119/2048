package src.function.core;

public class PrintSquare {
    public static void print(GameStruct square) {
        System.out.println("Current Square:");
        for (int i = 0; i < square.matrix.length; i++) {
            for (int j = 0; j < square.matrix[i].length; j++) {
                System.out.print(square.matrix[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println("Score: " + square.score);
    }
}
