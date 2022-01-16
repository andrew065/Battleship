import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class MusicSound {

    public static Clip clip;
    public static FloatControl gainControl;

    public static void main(String[] args) throws Exception {
        playBackgroundMusic();
    }

    /**
     * The procedure type method continuously plays background music.
     */
    public static void playBackgroundMusic() throws Exception {
        File music = new File("MusicSounds/BackgroundMusic.wav");
        clip = AudioSystem.getClip();
        // getAudioInputStream() also accepts a File or InputStream
        AudioInputStream myAudioInputStream = AudioSystem.getAudioInputStream(music);
        clip.open(myAudioInputStream);
        gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(-20.0f); // Reduce volume by 10 decibels.
        playMusic();
    }

    public static void playMusic() {
        clip.start();
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public static void stopMusic() {
        clip.stop();
    }

    public static void increaseMusic() {
        gainControl.setValue(gainControl.getValue() + 2.0f);
    }

    public static void decreaseMusic() {
        gainControl.setValue(gainControl.getValue() - 2.0f);
    }
}