package src.function.game;

public class game {
    public static void runGame(){
        square s = new square();
        s.square[0][1] = 2;
        s.square[0][2] = 2;
        s.square[0][3] = 2;
        move.moveleft(s);
        printSquare.print(s);
    }
}
