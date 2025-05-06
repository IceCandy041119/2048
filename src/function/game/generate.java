package src.function.game;

public class generate {
    public static int generateRandomNumber(){
        int num = (int)(Math.random()*10);
        if(num < 7){
            return 2;
        }else{
            return 4;
        }
    }
}
