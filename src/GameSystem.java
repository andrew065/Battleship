import javax.swing.*;
import java.awt.event.MouseListener;

public class GameSystem {
    /**
     * This method takes in an object and its location and adds it to the specified JPanel
     * @param panel - the JPanel to add the object to
     * @param object - the object that will be added to the panel
     * @param x - the x coordinate of the object
     * @param y - the y coordinate of the object
     */
    public static void addElement(JPanel panel, JLabel object, int x, int y) {
        panel.add(object);
        object.setLocation(x, y);
        object.setSize(object.getPreferredSize());
    }

    /**
     * This method takes in an object, its location, and a mouse listener and adds it to the specified JPanel
     * @param panel - the JPanel to add the object to
     * @param object - the object to be added to the panel
     * @param x - the x coordinate of the object
     * @param y - the y coordinate of the object
     * @param listener - the mouse listener to add to the object
     */
    public static void addElement(JPanel panel, JLabel object, int x, int y, MouseListener listener) {
        panel.add(object);
        object.setLocation(x, y);
        object.setSize(object.getPreferredSize());
        object.addMouseListener(listener);
    }
}
