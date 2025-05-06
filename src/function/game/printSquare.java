package src.function.game;

public class printSquare {
    public static void print(square square) {
        System.out.println("Current Square:");
        for (int i = 0; i < square.square.length; i++) {
            for (int j = 0; j < square.square[i].length; j++) {
                System.out.print(square.square[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println("Score: " + square.score);
    }
}
