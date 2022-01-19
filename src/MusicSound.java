import java.io.File;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

/**
 * Handles music & SFX.
 */
public class MusicSound {
    public static FloatControl gainControl;

    // types of clips
    public static Clip musicClip;

    private static final File[] hitFiles = new File[4];
    private static final File[] missFiles = new File[4];
    private static final File sunkFile = new File("MusicSounds/sunk.wav");
    private static final File hornFile = new File("MusicSounds/shiphorn.wav");
    private static final File clickFile = new File("MusicSounds/click.wav");
    private static final File tickFile = new File("MusicSounds/tick.wav");
    private static final File bellsFile = new File("MusicSounds/bells.wav");

    private static int lastPlayed = 0; // so that files aren't played twice in a row

    /**
     * The procedure type method continuously plays background music.
     * @return false if the music failed to play.
     */
    public static boolean importSounds() {
        try {
            // music SFX
            File music = new File("MusicSounds/BackgroundMusic.wav");
            musicClip = AudioSystem.getClip();

            AudioInputStream myAudioInputStream = AudioSystem.getAudioInputStream(music); // getAudioInputStream() also accepts a File or InputStream
            musicClip.open(myAudioInputStream);
            // gain
            gainControl = (FloatControl) musicClip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-20.0f); // Reduce volume by 10 decibels.

            playMusic();

            // hit SFX
            for (int i = 0; i < 4; i++) {
                hitFiles[i] = new File("MusicSounds/hit" + i + ".wav");
            }

            // miss SFX
            for (int i = 0; i < 4; i++) {
                missFiles[i] = new File("MusicSounds/miss" + i + ".wav");
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void playMusic() {
        musicClip.start();
        musicClip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public static void stopMusic() {
        musicClip.stop();
    }

    /**
     * Plays an audio file for firing.
     * @param status 1 = miss, 2 = hit
     */
    public static void playFire(int status) {
        try {
            Clip clip = AudioSystem.getClip();
            File[] files = missFiles;
            if (status == 2) {
                files = hitFiles;
            }

            // get random file from 0 to 3
            Random rand = new Random();
            int suffix = rand.nextInt(4);
            if (suffix == lastPlayed) { // pick other file that is not last played
                suffix += (suffix == 3) ? -1 : 1;
            }

            AudioInputStream inputStream = AudioSystem.getAudioInputStream(files[suffix]);
            clip.open(inputStream);

            // volume
            FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            control.setValue(gainControl.getValue() + 5.0f);

            clip.start();

            if ((status == 2) && (rand.nextBoolean())) { // 50% chance of playing the sound of bells when hit
                playBells();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Plays an audio file for a massive explosion.
     */
    public static void playSunk() {
        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(sunkFile);

            clip.open(inputStream);
            clip.start();

            // volume
            FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            control.setValue(gainControl.getValue() + 5.0f);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Plays a tick noise. Useful for button hovers.
     */
    public static void playTick() {
        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(tickFile);
            clip.open(inputStream);

            // volume
            FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            control.setValue(gainControl.getValue() + 5.0f);

            clip.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Plays a button pressing noise.
     */
    public static void playClick() {
        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(clickFile);
            clip.open(inputStream);

            // volume
            FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            control.setValue(gainControl.getValue());

            clip.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Plays the sound of a ship horn.
     */
    public static void playHorn() {
        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(hornFile);
            clip.open(inputStream);

            // volume
            FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            control.setValue(gainControl.getValue() + 5.0f);

            clip.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Plays the sound of bells.
     */
    public static void playBells() {
        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(bellsFile);
            clip.open(inputStream);

            // volume
            FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            control.setValue(gainControl.getValue() + 5.0f);

            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void increaseMusic() {
        gainControl.setValue(gainControl.getValue() + 2.0f);
    }

    public static void decreaseMusic() {
        gainControl.setValue(gainControl.getValue() - 2.0f);
    }
}