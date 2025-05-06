package src.function.game;

public class GameStruct {
   public int[][] matrix = new int[4][4];
   public int score = 0;

   public static void setMatrixElement(GameStruct s, int x, int y, int value) {
      s.matrix[x][y] = value;
   }

}
