package src.function.music;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import java.io.File;

public class MoveSound {

    public static void playMoveSound(){
        try{
            Clip clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("src/function/music/move.wav"));
            clip.open(inputStream);
            clip.start();
        }catch (Exception e){
            System.out.println("Error playing sound: " + e.getMessage());
        }
    }

   public static void main(String[] args) {
        playMoveSound();
    }
}
