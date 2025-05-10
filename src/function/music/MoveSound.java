package src.function.music;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;

import java.io.File;

public class MoveSound {

    public static void playMoveSound(){
        try{
            Clip clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("src/function/music/move.wav"));
            clip.open(inputStream);
            
            //clip.addLineListener(event -> {
             //   if (event.getType() == LineEvent.Type.STOP) {
             //       clip.close();
             //   }
           // });
            clip.start();
           // Thread.sleep(clip.getMicrosecondLength()/400);
        }catch (Exception e){
            System.out.println("Error playing sound: " + e.getMessage());
        }
    }

   public static void main(String[] args) {
        playMoveSound();
    }
}
