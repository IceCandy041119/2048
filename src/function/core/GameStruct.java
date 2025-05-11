package src.function.core;

public class GameStruct {
   public int[][] matrix = new int[4][4];
   public boolean[][] isMergeMatrix = new boolean[4][4];
   public int score = 0;
   public int isMerge = 0; // 0: no merge, 1: merge
   public int isMove = 0; // 0: no move, 1: move


   public void setMatrixElement(GameStruct s, int x, int y, int value) {
      s.matrix[x][y] = value;
   }

   public void reStart(){
      score = 0;
      isMove = 0;
      isMerge = 0;
      for(int i = 0; i < matrix.length; i++){
         for(int j = 0; j < matrix[i].length; j++){
            matrix[i][j] = 0;
         }
      }
      RandomNumberGenerate.generateNumber(this);
   }

}
