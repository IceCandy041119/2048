package src.function.game;

public class IsGameOver {
    public static boolean isGameOver(GameStruct s) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (s.matrix[i][j] == 0) {
                    return false; // There is an empty cell, game is not over
                }
                if (i < 3 && s.matrix[i][j] == s.matrix[i + 1][j]) {
                    return false; // There is a mergeable cell below
                }
                if (j < 3 && s.matrix[i][j] == s.matrix[i][j + 1]) {
                    return false; // There is a mergeable cell to the right
                }
            }
        }
        return true; // No empty cells and no mergeable cells, game is over
    }
}
