package src.function.game;

public class move {
   public static void moveleft(square square){
      for(int i = 0; i < square.square.length; i++){
         for(int j = 0; j < square.square[i].length; j++){
            if(square.square[i][j] != 0){
               int k = j - 1;
               while(k >= 0 && square.square[i][k] == 0){
                  square.square[i][k] = square.square[i][j];
                  square.square[i][j] = 0;
                  k--;
               }
            }
         }
      }
      for(int i = 0; i < square.square.length; i++){
         for(int j = 0; j < square.square[i].length - 1; j++){
            if(square.square[i][j] == square.square[i][j + 1]){
               square.square[i][j] *= 2;
               square.square[i][j + 1] = 0;
               for(int k = j + 1; k < square.square[i].length - 1; k++){
                  square.square[i][k] = square.square[i][k + 1];
               }
            }
         }
      }

   } 
   public static void moveright(square square){
      for(int i = 0; i < square.square.length; i++){
         for(int j = square.square[i].length - 1; j >= 0; j--){
            if(square.square[i][j] != 0){
               int k = j + 1;
               while(k < square.square[i].length && square.square[i][k] == 0){
                  square.square[i][k] = square.square[i][j];
                  square.square[i][j] = 0;
                  k++;
               }
            }
         }
      }
      for(int i = 0; i < square.square.length; i++){
         for(int j = square.square[i].length - 1; j > 0; j--){
            if(square.square[i][j] == square.square[i][j - 1]){
               square.square[i][j] *= 2;
               square.square[i][j - 1] = 0;
               for(int k = j - 1; k > 0; k--){
                  square.square[i][k] = square.square[i][k - 1];
               }
            }
         }
      }
   } 
   public static void moveup(square square){
      for(int i = 0; i < square.square.length; i++){
         for(int j = 0; j < square.square[i].length; j++){
            if(square.square[j][i] != 0){
               int k = j - 1;
               while(k >= 0 && square.square[k][i] == 0){
                  square.square[k][i] = square.square[j][i];
                  square.square[j][i] = 0;
                  k--;
               }
            }
         }
      }
      for(int i = 0; i < square.square.length; i++){
         for(int j = 0; j < square.square[i].length - 1; j++){
            if(square.square[j][i] == square.square[j + 1][i]){
               square.square[j][i] *= 2;
               square.square[j + 1][i] = 0;
               for(int k = j + 1; k < square.square[i].length - 1; k++){
                  square.square[k][i] = square.square[k + 1][i];
               }
            }
         }
      }
   } 
   public static void movedown(square square){
      for(int i = 0; i < square.square.length; i++){
         for(int j = square.square[i].length - 1; j >= 0; j--){
            if(square.square[j][i] != 0){
               int k = j + 1;
               while(k < square.square[i].length && square.square[k][i] == 0){
                  square.square[k][i] = square.square[j][i];
                  square.square[j][i] = 0;
                  k++;
               }
            }
         }
         printSquare.print(square);
      }
      for(int i = 0; i < square.square.length; i++){
         for(int j = square.square[i].length - 1; j > 0; j--){
            if(square.square[j][i] == square.square[j - 1][i]){
               square.square[j][i] *= 2;
               square.square[j - 1][i] = 0;
               for(int k = j - 1; k > 0; k--){
                  square.square[k][i] = square.square[k - 1][i];
               }
            }
         }
      }
   } 
}
