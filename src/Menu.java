import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Menu extends JDialog implements MouseListener {
    //menu button coordinates
    int[] PLAY_CO = {145, 260};
    int[] INS_CO = {145, 350};
    int[] LEADER_CO = {145, 440};
    int[] STAT_CO = {145, 530};
    int[] QUIT_CO = {145, 620};
    int[] SETTINGS_CO = {1323, 640};
    int[] PROFILE_CO = {1110, 690};

    //menu buttons
    JLabel bg = new JLabel(new ImageIcon("Images/Menu/Battleship-Menu.png"));
    JLabel playButton = new JLabel(new ImageIcon("Images/Menu/Play_Button.png"));
    JLabel instrucButton = new JLabel(new ImageIcon("Images/Menu/Instructions_Button.png"));
    JLabel leaderButton = new JLabel(new ImageIcon("Images/Menu/Leaderboard_Button.png"));
    JLabel statButton = new JLabel(new ImageIcon("Images/Menu/Stats_Button.png"));
    JLabel quitButton = new JLabel(new ImageIcon("Images/Menu/Quit_Button.png"));
    JLabel settings = new JLabel(new ImageIcon("Images/Menu/Settings_Button.png"));
    JLabel playSel = new JLabel(new ImageIcon("Images/Menu/Play_Highlight.png"));
    JLabel instrucSel = new JLabel(new ImageIcon("Images/Menu/Leaderboard_Highlight.png"));
    JLabel leaderSel = new JLabel(new ImageIcon("Images/Menu/Leaderboard_Highlight.png"));
    JLabel statSel = new JLabel(new ImageIcon("Images/Menu/Stats_Highlight.png"));
    JLabel quitSel = new JLabel(new ImageIcon("Images/Menu/Quit_Highlight.png"));
    JLabel settingSel = new JLabel(new ImageIcon("Images/Menu/Settings_Highlight.png"));
    JLabel profile = new JLabel(new ImageIcon("Images/Menu/Profile.png"));

    //volume buttons
    JLabel vUp = new JLabel(new ImageIcon("Images/Volume/Increase_Volume.png"));
    JLabel vDown = new JLabel(new ImageIcon("Images/Volume/Decrease_Volume.png"));
    JLabel vOn = new JLabel(new ImageIcon("Images/Volume/Volume_On.png"));
    JLabel vOff = new JLabel(new ImageIcon("Images/Volume/Volume_Off.png"));
    JLabel vBg = new JLabel(new ImageIcon("Images/Volume/Volume_Bg.png"));

    //menu button info (for mouse actions)
    ArrayList<JLabel> menuButtons = new ArrayList<>();
    ArrayList<JLabel> buttonEffects = new ArrayList<>();
    ArrayList<JLabel> volumeButtons = new ArrayList<>();

    JLayeredPane frame = getLayeredPane();

    JPanel menu;
    JPanel vF;

    JLabel vIndicator; //text for volume indicator
    JLabel nameDisplay; //label to display the name of the user

    String username; //name of current user

    int volume = 5; //volume level

    public Menu(String username) {
        this.username = username;

        //dialogue settings
        setSize(1400, 800);
        setName("Battleship");
        setLocationRelativeTo(null); //position in centre of screen

        //frame settings
        frame.setSize(1400, 800);
        frame.setLocation(0, 0);

        //menu panel settings
        menu = new JPanel();
        menu.setSize(1400, 800);
        menu.setLayout(null);

        //play button settings
        menu.add(playButton);
        menu.add(playSel);
        menuButtons.add(playButton);
        buttonEffects.add(playSel);
        playButton.setLocation(PLAY_CO[0], PLAY_CO[1]);
        playButton.setSize(playButton.getPreferredSize());
        playButton.addMouseListener(this);
        playSel.setLocation(PLAY_CO[0] - 15, PLAY_CO[1] - 13);
        playSel.setSize(playSel.getPreferredSize());
        playSel.setVisible(false);

        //instructions button settings
        menu.add(instrucButton);
        menu.add(instrucSel);
        menuButtons.add(instrucButton);
        buttonEffects.add(instrucSel);
        instrucButton.setLocation(INS_CO[0], INS_CO[1]);
        instrucButton.setSize(instrucButton.getPreferredSize());
        instrucButton.addMouseListener(this);
        instrucSel.setLocation(INS_CO[0] - 15, INS_CO[1] - 13);
        instrucSel.setSize(instrucSel.getPreferredSize());
        instrucSel.setVisible(false);

        //leaderboard button settings
        menu.add(leaderButton);
        menu.add(leaderSel);
        menuButtons.add(leaderButton);
        buttonEffects.add(leaderSel);
        leaderButton.setLocation(LEADER_CO[0], LEADER_CO[1]);
        leaderButton.setSize(leaderButton.getPreferredSize());
        leaderButton.addMouseListener(this);
        leaderSel.setLocation(LEADER_CO[0] - 15, LEADER_CO[1] - 13);
        leaderSel.setSize(leaderSel.getPreferredSize());
        leaderSel.setVisible(false);

        //stat button settings
        menu.add(statButton);
        menu.add(statSel);
        menuButtons.add(statButton);
        buttonEffects.add(statSel);
        statButton.setLocation(STAT_CO[0], STAT_CO[1]);
        statButton.setSize(statButton.getPreferredSize());
        statButton.addMouseListener(this);
        statSel.setLocation(STAT_CO[0] - 15, STAT_CO[1] - 13);
        statSel.setSize(statSel.getPreferredSize());
        statSel.setVisible(false);

        //quit button settings
        menu.add(quitButton);
        menu.add(quitSel);
        menuButtons.add(quitButton);
        buttonEffects.add(quitSel);
        quitButton.setLocation(QUIT_CO[0], QUIT_CO[1]);
        quitButton.setSize(quitButton.getPreferredSize());
        quitButton.addMouseListener(this);
        quitSel.setLocation(QUIT_CO[0] - 15, QUIT_CO[1] - 13);
        quitSel.setSize(quitSel.getPreferredSize());
        quitSel.setVisible(false);

        //settings button
        menu.add(settings);
        menu.add(settingSel);
        menuButtons.add(settings);
        buttonEffects.add(settingSel);
        settings.setLocation(SETTINGS_CO[0], SETTINGS_CO[1]);
        settings.setSize(settings.getPreferredSize());
        settings.addMouseListener(this);
        settingSel.setLocation(SETTINGS_CO[0] - 3, SETTINGS_CO[1] - 3);
        settingSel.setSize(settingSel.getPreferredSize());
        settingSel.setVisible(false);

        //username display
        nameDisplay = new JLabel(username);
        menu.add(nameDisplay);
        nameDisplay.setSize(username.length() * 25, 35);
        nameDisplay.setFont(new Font("Copperplate", Font.PLAIN, 35));
        nameDisplay.setForeground(Color.WHITE);
        nameDisplay.setLocation(menu.getWidth() - 80 - nameDisplay.getWidth(), 700);
        nameDisplay.setHorizontalAlignment(JLabel.CENTER);
        nameDisplay.setVerticalAlignment(JLabel.CENTER);

        //profile settings
        menu.add(profile);
        profile.setLocation(PROFILE_CO[0], PROFILE_CO[1]);
        profile.setSize(profile.getPreferredSize());

        //background settings
        menu.add(bg);
        bg.setLocation(0, 0);
        bg.setSize(bg.getPreferredSize());

        //adding components to current frame
        frame.add(menu, new Integer(1));
        initializeVolume();

        //JDialog settings
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    public void initializeVolume() {
        //panel settings
        vF = new JPanel();
        vF.setSize(200, 50);
        vF.setLayout(null);

        //volume indicator settings
        vIndicator = new JLabel(String.valueOf(volume), SwingConstants.CENTER);
        vF.add(vIndicator);
        vIndicator.setOpaque(false);
        vIndicator.setSize(37, 37);
        vIndicator.setLocation(52, 7);
        vIndicator.setFont(new Font("Copperplate", Font.PLAIN, 50));
        vIndicator.setForeground(Color.WHITE);


        //volume down button
        vF.add(vDown);
        volumeButtons.add(vDown);
        vDown.setSize(vDown.getPreferredSize());
        vDown.setLocation(10, 8);
        vDown.addMouseListener(this);

        //volume up button
        vF.add(vUp);
        volumeButtons.add(vUp);
        vUp.setSize(vDown.getPreferredSize());
        vUp.setLocation(100, 7);
        vUp.addMouseListener(this);

        //volume on/off button
        vF.add(vOn);
        vF.add(vOff);
        volumeButtons.add(vOn);
        volumeButtons.add(vOff);
        vOn.setSize(vOn.getPreferredSize());
        vOn.setLocation(170, 12);
        vOn.addMouseListener(this);
        vOff.setSize(vOff.getPreferredSize());
        vOff.setLocation(173, 15);
        vOff.setVisible(false);
        vOff.addMouseListener(this);

        //volume frame background
        vF.add(vBg);
        vBg.setSize(vBg.getPreferredSize());
        vBg.setLocation(0, 0);

        frame.add(vF, new Integer(2));
        vF.setLocation(1100, 635);
        vF.setVisible(false);

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        try {
            int index = menuButtons.indexOf((JLabel) e.getComponent());
            if (index == 0) ;//play
            else if (index == 1) ; //instructions
            else if (index == 2) ; //leaderboard
            else if (index == 3) ; //stats
            else if (index == 4) {
                MusicSound.stopMusic(); //stop music
                System.exit(0); //exit system
            } else if (index == 5) vF.setVisible(!vF.isVisible()); //open or close volume settings

            if (vF.isVisible()) {
                int i = volumeButtons.indexOf((JLabel) e.getComponent());
                if (i == 0) {
                    if (volume > 1) {
                        MusicSound.decreaseMusic();
                        volume--;
                        vIndicator.setText(String.valueOf(volume));
                    }
                }
                else if (i == 1) {
                    if (volume < 9) {
                        MusicSound.increaseMusic();
                        volume++;
                        vIndicator.setText(String.valueOf(volume));
                    }
                }
                else if (i == 2) {
                    MusicSound.stopMusic();
                    vOn.setVisible(false);
                    vOff.setVisible(true);
                }
                else if (i == 3) {
                    MusicSound.playMusic();
                    vOn.setVisible(true);
                    vOff.setVisible(false);
                }
            }

        } catch (Exception ignored) {}
    }
    @Override
    public void mouseEntered(MouseEvent e) {
        try {
            int index = menuButtons.indexOf((JLabel) e.getComponent());
            buttonEffects.get(index).setVisible(true);
        } catch (Exception ignored) {}
    }
    @Override
    public void mouseExited(MouseEvent e) {
        try {
            int index = menuButtons.indexOf((JLabel) e.getComponent());
            buttonEffects.get(index).setVisible(false);
        } catch (Exception ignored) {}
    }
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
}
