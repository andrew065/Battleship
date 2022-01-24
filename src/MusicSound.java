import java.io.File;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

/**
 * Handles music & SFX found within the game.
 * @author Eric K., Eric C., Andrew
 */
public class MusicSound {
    public static FloatControl gainControl; // general volume of background music

    // types of clips
    public static Clip musicClip;

    private static final File[] hitFiles = new File[4]; // all hit SFX (to assign later)
    private static final File[] missFiles = new File[4]; // all miss SFX
    private static final File sunkFile = new File("MusicSounds/sunk.wav");
    private static final File hornFile = new File("MusicSounds/shiphorn.wav");
    private static final File clickFile = new File("MusicSounds/click.wav");
    private static final File tickFile = new File("MusicSounds/tick.wav");
    private static final File bellsFile = new File("MusicSounds/bells.wav");

    private static int lastPlayed = 0; // so that files aren't played twice in a row

    /**
     * The procedure type method continuously plays background music.
     */
    public static void importSounds() {
        try {
            // get/play music SFX
            File music = new File("MusicSounds/BackgroundMusic.wav");
            musicClip = AudioSystem.getClip();

            AudioInputStream myAudioInputStream = AudioSystem.getAudioInputStream(music); // getAudioInputStream() also accepts a File or InputStream
            musicClip.open(myAudioInputStream);
            // gain
            gainControl = (FloatControl) musicClip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-20.0f); // Reduce volume by 10 decibels

            playMusic();

            // get hit SFX files
            for (int i = 0; i < 4; i++) {
                hitFiles[i] = new File("MusicSounds/hit" + i + ".wav");
            }

            // get miss SFX
            for (int i = 0; i < 4; i++) {
                missFiles[i] = new File("MusicSounds/miss" + i + ".wav");
            }

        } catch (Exception e) {
            e.printStackTrace();
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
            File[] files = missFiles;
            if (status == 2) { // if it's a hit use hit sounds
                files = hitFiles;
            }
            // get random file from 0 to 3
            Random rand = new Random();
            int suffix = rand.nextInt(4);
            if (suffix == lastPlayed) { // pick other file that is not last played
                suffix += (suffix == 3) ? -1 : 1;
            }

            playSound(files[suffix], 5f); // call method to play sound from file

            if ((status == 2) && (rand.nextInt(10) < 3)) { // 0% chance of playing the sound of bells when hit
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
        playSound(sunkFile, 5.0f);
    }

    /**
     * Plays a tick noise. Useful for button hovers.
     */
    public static void playTick() {
        playSound(tickFile, 10.0f);
    }

    /**
     * Plays a button pressing noise.
     */
    public static void playClick() { playSound(clickFile, 0.0f); }

    /**
     * Plays the sound of a ship horn.
     */
    public static void playHorn() {
        playSound(hornFile,5.0f);
    }

    /**
     * Plays the sound of bells.
     */
    public static void playBells() {
        playSound(bellsFile,5.0f);
    }

    /**
     * Creates a new clip based on sound file; plays the clip based on volume given.
     * @param soundFile the file to play a sound from.
     * @param gainVal the difference from the master volume.
     */
    private static void playSound(File soundFile, float gainVal) {
        try {
            Clip clip = AudioSystem.getClip(); // create new clip every time so multiple of the same sound can play at once
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(soundFile);
            clip.open(inputStream);

            // volume
            FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN); // get volume control
            control.setValue(gainControl.getValue() + gainVal); // connect volume to background music volume (master) + difference

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