package src.function.music;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class PlayBGM {
    public static Clip clip;
    public static File file;
    public static int musicCount;
    public static int currentMusicTime = 0;
    public static int musicIndex = 0;
    public static int isBGMPasue = 0;
    public static String[] soundFiles;
    public static String currentMusicName;

    public static void playBGM(){
        file = new File("sound/");
        if(!file.exists()){
            file.mkdirs();
        }
        soundFiles = file.list();
        musicCount = soundFiles.length;
        currentMusicName = soundFiles[musicIndex];
        playMusic(soundFiles);

        clip.addLineListener(event -> {
            if (event.getType() == javax.sound.sampled.LineEvent.Type.STOP && isBGMPasue != 1) {
                clip.close();
                musicIndex = (musicIndex + 1) % musicCount;
                currentMusicName = soundFiles[musicIndex];
                playMusic(soundFiles);
            }
        });

    }

    public static void playMusic(String[] soundFiles){
        try{
            clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("sound/" + soundFiles[musicIndex]));
            clip.open(inputStream);

           // clip.addLineListener(event -> {
           //     if (event.getType() == javax.sound.sampled.LineEvent.Type.STOP) {
            //        clip.close();
            //   }
           // });

            clip.start();

        }catch (Exception e){
            System.out.println("Error playing sound: " + e.getMessage());
        }
    }

    public static void pauseBGM(){
        try{
            currentMusicTime = (int) clip.getMicrosecondPosition();
            clip.stop();
        }catch (Exception e){
            System.out.println("Error pausing sound: " + e.getMessage());
        }
    }

    public static void resumeBGM(){
        try{
            clip.setMicrosecondPosition(currentMusicTime);
            clip.start();
        }catch (Exception e){
            System.out.println("Error resuming sound: " + e.getMessage());
        }
    }

    public static void preBGM(){
        try{
            clip.close();
            musicIndex = (musicIndex - 1 + musicCount) % musicCount;
            currentMusicName = soundFiles[musicIndex];
            playMusic(soundFiles);
        }catch (Exception e){
            System.out.println("Error playing sound: " + e.getMessage());
        }
    }

    public static void nextBGM(){
        try{
            clip.close();
            musicIndex = (musicIndex + 1) % musicCount;
            currentMusicName = soundFiles[musicIndex];
            playMusic(soundFiles);
        }catch (Exception e){
            System.out.println("Error playing sound: " + e.getMessage());
        }
    }
}
