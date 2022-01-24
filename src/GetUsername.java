import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class GetUsername extends JDialog implements ActionListener {
    private Boolean firstClick = true; // first click boolean for hiding instructions

    static JTextField loginText = new JTextField("Please enter a username"); // text-field for input of username
    JButton okButton = new JButton(); // OK button to confirm username login
    public JFrame login = new JFrame();

    public GetUsername() {
        login.setUndecorated(true);// hide top bars of the dialog
        login.setBackground(new Color(0, 0, 0, 0));// set dialog background transparent
        login.setResizable(false);// avoid user to change the size of dialog
        login.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);// set action: dispose after login
        login.setSize(500, 250);// set size of login interface

        // background image of login interface
        JLabel bgi = new JLabel(new ImageIcon("Images/Login/BattleshipLogin.png"));
        bgi.setBounds(0, 0, 500, 250);// set background image
        login.setLocationRelativeTo(null);// display dialog at center of the screen to attract attention

        login.setVisible(true);// display login dialog

        loginText.setBounds(300, 140, 175, 25);// set login text field
        loginText.setBackground(new Color(173, 216, 230));
        loginText.setBorder(new LineBorder(new Color(200, 200, 200)));
        loginText.setForeground(Color.BLACK);
        loginText.setHorizontalAlignment(SwingConstants.CENTER);

        login.add(loginText);
        loginText.addMouseListener(new MouseListener() {// MouseListener for login text field
            @Override
            public void mouseClicked(MouseEvent e) {// hide the instruction on first click
                if (firstClick) {
                    loginText.setText("");
                    firstClick = false;
                } // end if
            }// end method

            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
        });// end MouseListener

        okButton.setBounds(370, 174, 55, 20);// set OK Button
        okButton.setBorder(new LineBorder(new Color(224, 255, 255)));
        okButton.setBackground(new Color(224, 255, 255));
        okButton.setText("Confirm");
        okButton.setHorizontalTextPosition(SwingConstants.CENTER);
        okButton.setVerticalTextPosition(SwingConstants.CENTER);
        okButton.setMargin(new Insets(0,-30, 0,-30));

        login.add(okButton);
        login.add(bgi);// add background image
        login.setVisible(true);// display login interface
        login.repaint();// refresh the interface

        okButton.addActionListener(this);// add ActionListener to the OK Button
    }

    @Override
    public void actionPerformed(ActionEvent e) {// ActionListener for login operations
        String username = GetUsername.loginText.getText();// get and write username to file
        if(username.length() > 0 && !username.equals("Please enter a username")) {
            login.setVisible(false);
            login.dispose();
            User user = null;
            try {
                user = new User(username); //try to create a new user with the username that they have set
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            try {
                new Menu(user); //create a menu for the user
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        } else {
            userErrorMessage();
        }
    }

    //if username is not valid
    public static void userErrorMessage() {
        JFrame jFrame = new JFrame();
        JOptionPane.showMessageDialog(jFrame, "Please enter a valid username.");
    }
}