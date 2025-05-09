package src.function.game;

public class GameStruct {
   public int[][] matrix = new int[4][4];
   public int score = 0;
   public int isMerge = 0; // 0: no merge, 1: merge
   public int isMove = 0; // 0: no move, 1: move

   public void setMatrixElement(GameStruct s, int x, int y, int value) {
      s.matrix[x][y] = value;
   }

}
