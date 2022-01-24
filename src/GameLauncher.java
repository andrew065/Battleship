import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Enumeration;

/**
 * @author Eric K., Andrew L., Derrick, Eric C.
 * @description: This class is used for launching the game and game orchestration purposes.
 * @teacher: Ms. Andrighetti
 */
public class GameLauncher extends JFrame {

    /**
     * Main class which gets launched and launches the menus, music and font.
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        AI.setVisited();

        setUIFont(new javax.swing.plaf.FontUIResource(Font.createFont(Font.TRUETYPE_FONT,
                new File("Fonts/customFont.otf")).deriveFont(15f)));
        MusicSound.importSounds();
        menuDirector();
    }

    /**
     * Makes GetUsername object to open login screen and get username.
     */
    public static void menuDirector() {
        new GetUsername();
    }

    /**
     * Sets custom font across the program
     * @param f FontUIResource which is the specific font to set
     */
    public static void setUIFont (javax.swing.plaf.FontUIResource f){
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get (key);
            if (value instanceof javax.swing.plaf.FontUIResource)
                UIManager.put(key, f);
        }
    }
}