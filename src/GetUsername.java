import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import javax.swing.*;
import javax.swing.border.LineBorder;

/**
 * @author Eric K.
 * @description Shows the login dialog and prompts for the user's username.
 */
public class GetUsername extends JDialog implements ActionListener {
    private Boolean firstHideClick = true; // for hiding existing text once clicked

    static JTextField loginText = new JTextField("Please enter a username"); // text field for user to enter name
    JButton confirmationButton = new JButton(); // okay button
    public JFrame loginFrame = new JFrame();

    /**
     * Sets up and displays GUI
     */
    public GetUsername() {
        loginFrame.setUndecorated(true);// hide top bars of frame
        loginFrame.setBackground(new Color(0, 0, 0, 0));// set background transparent
        loginFrame.setResizable(false);

        loginFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);// after login, make sure it closes
        loginFrame.setSize(500, 250);// set size of login interface

        JLabel bgi = new JLabel(new ImageIcon("Images/Login/BattleshipLogin.png")); // background for login frame
        bgi.setBounds(0, 0, 500, 250);
        loginFrame.setLocationRelativeTo(null);

        loginFrame.setVisible(true);// make login dialog visible

        loginText.setBounds(300, 140, 175, 25);// set login text field
        loginText.setBackground(new Color(173, 216, 230));
        loginText.setBorder(new LineBorder(new Color(200, 200, 200)));
        loginText.setForeground(Color.BLACK);
        loginText.setHorizontalAlignment(SwingConstants.CENTER);

        loginFrame.add(loginText);

        loginText.addMouseListener(new MouseListener() {// when login text field is changed
            /**
             * When login text field is clicked
             * @param e
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                if (firstHideClick) { // if it is first click on text field, delete existing text
                    loginText.setText("");
                    firstHideClick = false;
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
        });

        confirmationButton.setBounds(370, 174, 55, 20);// set OK Button
        confirmationButton.setBorder(new LineBorder(new Color(224, 255, 255)));
        confirmationButton.setBackground(new Color(224, 255, 255));
        confirmationButton.setText("Confirm");
        confirmationButton.setHorizontalTextPosition(SwingConstants.CENTER);
        confirmationButton.setVerticalTextPosition(SwingConstants.CENTER);
        confirmationButton.setMargin(new Insets(0,-30, 0,-30));

        loginFrame.add(confirmationButton);
        loginFrame.add(bgi);// add background image
        loginFrame.setVisible(true);// display login interface
        loginFrame.repaint();// refresh

        confirmationButton.addActionListener(this);// add ActionListener to the okay Button
    }

    /**
     * Listens for user to click ok button
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String username = GetUsername.loginText.getText();// get username from text field
        if(username.length() > 0 && !username.equals("Please enter a username")) { // if username is valid
            loginFrame.setVisible(false);
            loginFrame.dispose();
            User user = null;
            try { // creates a new user from username provided
                user = new User(username);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            try { // creates a new menu object for user
                new Menu(user);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        } else { // if invalid name, show error message
            userErrorMessage();
        }
    }

    /**
     * Used to show error message to user when invalid name is entered.
     */
    public static void userErrorMessage() {
        JFrame jFrame = new JFrame();
        JOptionPane.showMessageDialog(jFrame, "Please enter a valid username.");
    }
}