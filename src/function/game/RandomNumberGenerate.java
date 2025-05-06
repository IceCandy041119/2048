package src.function.game;

import java.util.ArrayList;

public class RandomNumberGenerate {
   public static int generateRandomNumber() {
      double random = Math.random();
      int number = (int) (random * 10); // Generates a number between 0 and 9
      if (number < 8) {
         return 2; // 80% chance to return 2
      } else {
         return 4; // 20% chance to return 4
      }
   }
   public static void generateNumber(GameStruct s) {
        ArrayList<Position> emptyPositions = new ArrayList<>();
        for(int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (s.matrix[i][j] == 0) {
                    emptyPositions.add(new Position(i, j));
                }
            }
        }

        assert emptyPositions.size() > 0 : "No empty positions available to place a new number.";


        double random = Math.random();
        int randomIndex = (int) (random * emptyPositions.size());
        Position randomPosition = emptyPositions.get(randomIndex);
        int randomNumber = generateRandomNumber();
        GameStruct.setMatrixElement(s, randomPosition.x, randomPosition.y, randomNumber);
   }

}
