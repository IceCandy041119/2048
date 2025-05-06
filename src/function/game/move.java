package src.function.game;

public class Move{
   public static void moveLeft(GameStruct s){
      for(int i = 0; i < 4; i++){
         for(int j = 1; j < 4; j++){
            if(s.matrix[i][j] != 0){
               int k = j;
               while(k > 0 && s.matrix[i][k-1] == 0){
                  s.matrix[i][k-1] = s.matrix[i][k];
                  s.matrix[i][k] = 0;
                  k--;
               }
            }
         }
      }
      for(int i = 0; i < 4; i++){
         for(int j = 0; j < 3; j++){
            if(s.matrix[i][j] == s.matrix[i][j+1] && s.matrix[i][j] != 0){
               s.matrix[i][j] *= 2;
               s.score += s.matrix[i][j];
               s.matrix[i][j+1] = 0;
               for(int k = j+1; k < 3; k++){
                  s.matrix[i][k] = s.matrix[i][k+1];
                  s.matrix[i][k+1] = 0;
               }
            }
         }
      }  
   }
   public static void moveRight(GameStruct s){
      for(int i = 0; i < 4; i++){
         for(int j = 2; j >= 0; j--){
            if(s.matrix[i][j] != 0){
               int k = j;
               while(k < 3 && s.matrix[i][k+1] == 0){
                  s.matrix[i][k+1] = s.matrix[i][k];
                  s.matrix[i][k] = 0;
                  k++;
               }
            }
         }
      }
      for(int i = 0; i < 4; i++){
         for(int j = 3; j > 0; j--){
            if(s.matrix[i][j] == s.matrix[i][j-1] && s.matrix[i][j] != 0){
               s.matrix[i][j] *= 2;
               s.score += s.matrix[i][j];
               s.matrix[i][j-1] = 0;
               for(int k = j-1; k > 0; k--){
                  s.matrix[i][k] = s.matrix[i][k-1];
                  s.matrix[i][k-1] = 0;
               }
            }
         }
      }
   }
   public static void moveUp(GameStruct s){
      for(int j = 0; j < 4; j++){
         for(int i = 1; i < 4; i++){
            if(s.matrix[i][j] != 0){
               int k = i;
               while(k > 0 && s.matrix[k-1][j] == 0){
                  s.matrix[k-1][j] = s.matrix[k][j];
                  s.matrix[k][j] = 0;
                  k--;
               }
            }
         }
      }
      for(int j = 0; j < 4; j++){
         for(int i = 0; i < 3; i++){
            if(s.matrix[i][j] == s.matrix[i+1][j] && s.matrix[i][j] != 0){
               s.matrix[i][j] *= 2;
               s.score += s.matrix[i][j];
               s.matrix[i+1][j] = 0;
               for(int k = i+1; k < 3; k++){
                  s.matrix[k][j] = s.matrix[k+1][j];
                  s.matrix[k+1][j] = 0;
               }
            }
         }
      }  
   }
   public static void moveDown(GameStruct s){
      for(int j = 0; j < 4; j++){
         for(int i = 2; i >= 0; i--){
            if(s.matrix[i][j] != 0){
               int k = i;
               while(k < 3 && s.matrix[k+1][j] == 0){
                  s.matrix[k+1][j] = s.matrix[k][j];
                  s.matrix[k][j] = 0;
                  k++;
               }
            }
         }
      }
      for(int j = 0; j < 4; j++){
         for(int i = 3; i > 0; i--){
            if(s.matrix[i][j] == s.matrix[i-1][j] && s.matrix[i][j] != 0){
               s.matrix[i][j] *= 2;
               s.score += s.matrix[i][j];
               s.matrix[i-1][j] = 0;
               for(int k = i-1; k > 0; k--){
                  s.matrix[k][j] = s.matrix[k-1][j];
                  s.matrix[k-1][j] = 0;
               }
            }
         }
      }  
   }
}
