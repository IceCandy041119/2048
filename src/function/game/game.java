package src.function.game;

public class Game {
    public static void runGame(){
        GameStruct s = new GameStruct();
        s.matrix[0][1] = 2;
        s.matrix[0][2] = 2;
        s.matrix[0][3] = 2;
        Move.moveLeft(s);
        PrintSquare.print(s);
    }
}
