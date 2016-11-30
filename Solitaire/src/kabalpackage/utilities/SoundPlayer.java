package kabalpackage.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class SoundPlayer implements Runnable{
    private AudioStream sound;
    private InputStream soundStream;
    private boolean SOUND_PLAYED = false;

    public SoundPlayer(){
        try{
            this.soundStream = getClass().getResourceAsStream("sounds/card.wav");
            this.sound = new AudioStream(this.soundStream);
        }catch (IOException ioe) {
            System.err.println("IOE");
        }catch (NullPointerException npe) {
            System.err.println("NPE");
        }
    }

    public synchronized void playSound(){
        AudioPlayer.player.start(this.sound);
        this.SOUND_PLAYED = true;
    }

    public void run(){
        while (!this.SOUND_PLAYED){
            try{
                Thread.sleep(1000L);
            }catch (Exception e) {}
        }
    }
}
