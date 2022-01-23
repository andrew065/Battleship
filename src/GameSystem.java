import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;

/**
 * @author Andrew Lian
 */

public class GameSystem {
    public static Timer timer;
    public static long timeSec;

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
     * This method adds a text label to a specified panel
     * @param panel - panel to add label to
     * @param text - string text of label
     * @param x - x coordinate
     * @param y - y coordinate
     * @param w - width
     * @param h - height
     * @param size - font size
     */
    public static void addText(JPanel panel, String text, int x, int y, int w, int h, int size) {
        JLabel label = new JLabel(text);
        panel.add(label);
        label.setLocation(x, y);
        label.setSize(w, h);
        label.setFont(new Font("Copperplate", Font.PLAIN, size));
        label.setForeground(Color.WHITE);
    }

    /**
     * This method initializes a given text label and adds it to a specified panel
     * @param panel - the panel to add the label to
     * @param label - the label to the initialized
     * @param x - x coordinate
     * @param y - y coordinate
     * @param w - width
     * @param h - height
     * @param size - font size
     */
    public static void addText(JPanel panel, JLabel label, int x, int y, int w, int h, int size, boolean left) {
        panel.add(label);
        label.setLocation(x, y);
        label.setSize(w, h);
        label.setHorizontalAlignment(left? SwingConstants.LEFT: SwingConstants.RIGHT);
        label.setFont(new Font("Copperplate", Font.PLAIN, size));
        label.setForeground(Color.WHITE);
    }

    /**
     * This method creates a JLabel that contains the time and adds it to the given panel
     * @param panel - the panel to add the JLabel to
     */
    public static void createTime(JPanel panel) {
        JLabel t = new JLabel();
        addElement(panel, t, 637, 40);
        t.setSize(250, 80);
        t.setFont(new Font("Copperplate", Font.PLAIN, 68));
        t.setForeground(Color.WHITE);

        timeSec = 0; //reset counter

        timer = new Timer(1000, e -> {
            timeSec++;
            t.setText(formatTime());
        });
        timer.start();
    }

    /**
     * This method stops the timer/stopwatch
     */
    public static void stopTime() {
        timer.stop();
    }

    /**
     * This method formats the time and converts seconds into minutes:seconds
     * @return - a string containing the reformatted time
     */
    private static String formatTime() {
        String min = "00";
        String sec = timeSec % 60 < 10? "0" + timeSec % 60 : "" + timeSec % 60;
        if (timeSec < 600) min =  "0" + timeSec / 60;
        else if (timeSec > 600) min = "" + timeSec / 60;

        return min + ":" + sec;
    }

    /**
     * This method creates a start signal for the game and keeps it open for 1 second before closing it
     */
    public static void signalStart() {
        Thread thread = new Thread(() -> {
            JFrame frame = createStartSignal();
            try {
                Thread.sleep(1000);
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
