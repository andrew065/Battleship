import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Enumeration;

public class GameLauncher extends JFrame {

    public static void main(String[] args) throws Exception {
        AI.setVisited();
        for(int i = 0; i < AI.visitedCoor.size(); i++) {
            System.out.print(AI.visitedCoor.get(i)[0] + ", ");
            System.out.print(AI.visitedCoor.get(i)[1]);
            System.out.println();
        }
        System.out.println(AI.visitedCoor);

        setUIFont(new javax.swing.plaf.FontUIResource(Font.createFont(Font.TRUETYPE_FONT,
                new File("Fonts/customFont.otf")).deriveFont(15f)));
        MusicSound.importSounds();
        menuDirector();
    }

    public static void menuDirector() throws Exception {
        GetUsername loginScreen = new GetUsername();
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