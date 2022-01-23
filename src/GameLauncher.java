import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Enumeration;

public class GameLauncher extends JFrame {

    public static void main(String[] args) throws Exception {
        AI.setVisited();

        setUIFont(new javax.swing.plaf.FontUIResource(Font.createFont(Font.TRUETYPE_FONT,
                new File("Fonts/customFont.otf")).deriveFont(15f)));
        MusicSound.importSounds();
        menuDirector();
    }

    public static void menuDirector() {
        new GetUsername();
    }

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