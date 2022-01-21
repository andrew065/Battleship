import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

/**
 * @author Andrew Lian
 */

public class GameSystem {
    public static long lastRecordTime;
    public static long timeUsed;


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

    public static void createTime(JLabel timerLabel) {
        // initialize the timer
        Timer timer = new Timer(1000, e -> {
            long thisTime = System.currentTimeMillis();// record the current time
            timeUsed += thisTime - lastRecordTime;// add the new time interval to sum of time used
            timerLabel.setText(formatTime(timeUsed));// display the calculated time used
            lastRecordTime = thisTime;// record this action of recording
        });
    }

    private static String formatTime(long secondNum) {
        secondNum /= 1000;// convert millisecond to seconds
        String[] timeInfo = new String[3];// store the converted number in a String array
        timeInfo[0] = Long.toString(secondNum / 3600);// calculate number of hours
        timeInfo[1] = Long.toString((secondNum % 3600) / 60);// calculate the number of minutes
        timeInfo[2] = Long.toString(secondNum % 60);// calculate the number of seconds
        String formattedTime = "";// create a String to return the result
        // for converted number, add to the result by format of no less than two digits
        for (int i = 0; i < 3; i++) {
            if (timeInfo[i].length() == 1) {
                formattedTime += "0";
            }
            formattedTime += (timeInfo[i] + ":");
        }
        return formattedTime.substring(0, formattedTime.length() - 1);// return the answer
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
