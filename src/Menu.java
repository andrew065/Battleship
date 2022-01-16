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
    JLabel instrucPage = new JLabel(new ImageIcon("Images/Menu/Instructions_Page.png"));
    JLabel leaderPage = new JLabel(new ImageIcon("Images/Menu/Leaderboard_Page.png"));
    JLabel statPage = new JLabel(new ImageIcon("Images/Menu/Stats_Page.png"));
    JLabel goBack = new JLabel(new ImageIcon("Images/Menu/Back_Button.png"));

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

    JLayeredPane frame;

    JPanel menu;
    JPanel instructions;
    JPanel leaderboard;
    JPanel stats;
    JPanel vF;
    JPanel back;

    JLabel vIndicator; //text for volume indicator
    JLabel nameDisplay; //label to display the name of the user

    JComponent curOpen;

    String username; //name of current user

    int volume = 5; //volume level

    public Menu(String username) {
        this.username = username;

        frame = getLayeredPane();

        //dialogue settings
        setSize(1400, 800);
        setName("Battleship");
        setLocationRelativeTo(null); //position in centre of screen

        //frame settings
        frame.setSize(1400, 800);
        frame.setLocation(0, 0);

        //initializing layered panels
        initializeBg();
        initializeInstructions();
        initializeLeaderboard();
        initializeStats();
        initializeVolume();
        initializeBack();

        //JDialog settings
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    public void initializeBg() {
        //menu panel settings
        menu = new JPanel();
        menu.setSize(1400, 800);
        menu.setLayout(null);

        //play button settings
        menuButtons.add(playButton);
        buttonEffects.add(playSel);
        addElement(menu, playButton, PLAY_CO[0], PLAY_CO[1], this);
        addElement(menu, playSel, PLAY_CO[0] - 15, PLAY_CO[1] - 13, this);
        playSel.setVisible(false);

        //instructions button settings
        menuButtons.add(instrucButton);
        buttonEffects.add(instrucSel);
        addElement(menu, instrucButton, INS_CO[0], INS_CO[1], this);
        addElement(menu, instrucSel, INS_CO[0] - 15, INS_CO[1] - 13, this);
        instrucSel.setVisible(false);

        //leaderboard button settings
        menuButtons.add(leaderButton);
        buttonEffects.add(leaderSel);
        addElement(menu, leaderButton, LEADER_CO[0], LEADER_CO[1], this);
        addElement(menu, leaderSel, LEADER_CO[0] - 15, LEADER_CO[1] - 13, this);
        leaderSel.setVisible(false);

        //stat button settings
        menuButtons.add(statButton);
        buttonEffects.add(statSel);
        addElement(menu, statButton, STAT_CO[0], STAT_CO[1], this);
        addElement(menu, statSel, STAT_CO[0] - 15, STAT_CO[1] - 13, this);
        statSel.setVisible(false);

        //quit button settings
        menuButtons.add(quitButton);
        buttonEffects.add(quitSel);
        addElement(menu, quitButton, QUIT_CO[0], QUIT_CO[1], this);
        addElement(menu, quitSel, QUIT_CO[0] - 15, QUIT_CO[1] - 13, this);
        quitSel.setVisible(false);

        //settings button
        menuButtons.add(settings);
        buttonEffects.add(settingSel);
        addElement(menu, settings, SETTINGS_CO[0], SETTINGS_CO[1], this);
        addElement(menu, settingSel, SETTINGS_CO[0] - 3, SETTINGS_CO[1] - 3, this);
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
        addElement(menu, profile, PROFILE_CO[0], PROFILE_CO[1]);

        //background settings
        menu.add(bg);
        addElement(menu, bg, 0, 0);

        //adding components to current frame
        frame.add(menu, new Integer(1));
    }

    public void initializeInstructions() {
        instructions = new JPanel();
        instructions.setSize(1400, 800);
        instructions.setLayout(null);
        instructions.setOpaque(false);

        addElement(instructions, instrucPage, 0, 0);

        frame.add(instructions, new Integer(2));
        instructions.setLocation(0, 0);
        instructions.setVisible(false);
    }

    public void initializeLeaderboard() {
        leaderboard = new JPanel();
        leaderboard.setSize(1400, 800);
        leaderboard.setLayout(null);
        leaderboard.setOpaque(false);

        addElement(leaderboard, leaderPage, 0, 0);

        frame.add(leaderboard, new Integer(3));
        leaderboard.setLocation(0, 0);
        leaderboard.setVisible(false);
    }

    public void initializeStats() {
        stats = new JPanel();
        stats.setSize(1400, 800);
        stats.setLayout(null);
        stats.setOpaque(false);

        addElement(stats, statPage, 0, 0);

        frame.add(stats, new Integer(4));
        stats.setLocation(0, 0);
        stats.setVisible(false);
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
        volumeButtons.add(vDown);
        addElement(vF, vDown, 10, 8, this);

        //volume up button
        volumeButtons.add(vUp);
        addElement(vF, vUp, 100, 7, this);

        //volume on/off button
        volumeButtons.add(vOn);
        volumeButtons.add(vOff);
        addElement(vF, vOn, 170, 12, this);
        addElement(vF, vOff, 173, 15, this);
        vOff.setVisible(false);

        //volume frame background
        vF.add(vBg);
        addElement(vF, vBg, 0, 0);

        frame.add(vF, new Integer(5));
        vF.setLocation(1100, 635);
        vF.setVisible(false);
    }

    public void initializeBack() {
        back = new JPanel();
        back.setSize(75, 50);
        back.setLayout(null);
        back.setOpaque(false);

        back.add(goBack);
        goBack.setSize(goBack.getPreferredSize());
        goBack.setLocation(0, 0);
        goBack.addMouseListener(this);

        frame.add(goBack, new Integer(6));
        goBack.setLocation(40, 40);
        goBack.setVisible(false);
    }

    public void addElement(JPanel panel, JLabel object, int x, int y) {
        panel.add(object);
        object.setLocation(x, y);
        object.setSize(object.getPreferredSize());
    }

    public void addElement(JPanel panel, JLabel object, int x, int y, MouseListener listener) {
        panel.add(object);
        object.setLocation(x, y);
        object.setSize(object.getPreferredSize());
        object.addMouseListener(listener);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        try {
            if (!goBack.isVisible()) {
                int index = menuButtons.indexOf((JLabel) e.getComponent());
                if (index == 0) ;//play
                else if (index == 1) {
                    instructions.setVisible(true);
                    goBack.setVisible(true);
                    curOpen = instructions;
                } else if (index == 2) {
                    leaderboard.setVisible(true);
                    goBack.setVisible(true);
                    curOpen = leaderboard;
                } else if (index == 3) {
                    stats.setVisible(true);
                    goBack.setVisible(true);
                    curOpen = stats;
                } else if (index == 4) {
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
                    } else if (i == 1) {
                        if (volume < 9) {
                            MusicSound.increaseMusic();
                            volume++;
                            vIndicator.setText(String.valueOf(volume));
                        }
                    } else if (i == 2) {
                        MusicSound.stopMusic();
                        vOn.setVisible(false);
                        vOff.setVisible(true);
                    } else if (i == 3) {
                        MusicSound.playMusic();
                        vOn.setVisible(true);
                        vOff.setVisible(false);
                    }
                }
            }
            if (goBack.isVisible()) {
                if (e.getComponent() == goBack) {
                    goBack.setVisible(false);
                    curOpen.setVisible(false);
                }
            }
        } catch (Exception ignored) {}
    }
    @Override
    public void mouseEntered(MouseEvent e) {
        try {
            if (!goBack.isVisible()) {
                int index = menuButtons.indexOf((JLabel) e.getComponent());
                buttonEffects.get(index).setVisible(true);
            }
        } catch (Exception ignored) {}
    }
    @Override
    public void mouseExited(MouseEvent e) {
        try {
            if (!goBack.isVisible()) {
                int index = menuButtons.indexOf((JLabel) e.getComponent());
                buttonEffects.get(index).setVisible(false);
            }
        } catch (Exception ignored) {}
    }
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
}
