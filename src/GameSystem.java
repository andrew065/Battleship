import javax.swing.*;
import java.awt.*;
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

    /**
     * This method creates a start signal for the game and keeps it open for 1.5 seconds before closing it
     */
    public static void signalStart() {
        Thread thread = new Thread(() -> {
            JFrame frame = createStartSignal();
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            closeStartSignal(frame);
        });
        thread.start();
    }

    /**
     * This method creates and initializes the start signal for the game
     * @return - the frame that contains the start signal
     */
    public static JFrame createStartSignal() {
        JFrame frame = new JFrame();
        frame.setSize(470, 280);
        frame.setUndecorated(true);
        frame.setBackground(new Color(1, 1, 1, 0));

        JLabel start = new JLabel(new ImageIcon("Images/Game/Game_Start.png"));
        frame.add(start);
        start.setSize(start.getPreferredSize());
        start.setLocation(0, 0);

        frame.toFront();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        return frame;
    }

    /**
     * This method closes the specified frame and disposes it
     * @param frame - the frame to be closed
     */
    public static void closeStartSignal(JFrame frame) {
        frame.setVisible(false);
        frame.dispose();
    }
}
