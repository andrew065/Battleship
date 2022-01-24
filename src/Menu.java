import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * @author Andrew Lian
 */

public class Menu extends JDialog implements MouseListener {
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
    JLabel noLeader = new JLabel(new ImageIcon("Images/Menu/No_Leaderboard.png"));
    JLabel statPage = new JLabel(new ImageIcon("Images/Menu/Stats_Page.png"));
    JLabel noStats = new JLabel(new ImageIcon("Images/Menu/No_Stats.png"));
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

    JComponent curOpen; //keeps track of the current opened window

    public User user; //name of current user
    public Leaderboard leader;

    int volume = 5; //volume level

    public Menu(User user) throws FileNotFoundException {
        this.user = user;
        this.leader = new Leaderboard(user);

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
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
    }

    /**
     * This method initializes the background for the menu frame
     */
    public void initializeBg() {
        //menu panel settings
        menu = new JPanel();
        menu.setSize(1400, 800);
        menu.setLayout(null);

        //play button settings
        menuButtons.add(playButton);
        buttonEffects.add(playSel);
        GameSystem.addElement(menu, playButton, 145, 260, this);
        GameSystem.addElement(menu, playSel, 130, 247, this);
        playSel.setVisible(false);

        //instructions button settings
        menuButtons.add(instrucButton);
        buttonEffects.add(instrucSel);
        GameSystem.addElement(menu, instrucButton, 145, 350, this);
        GameSystem.addElement(menu, instrucSel, 130, 337, this);
        instrucSel.setVisible(false);

        //leaderboard button settings
        menuButtons.add(leaderButton);
        buttonEffects.add(leaderSel);
        GameSystem.addElement(menu, leaderButton, 145, 440, this);
        GameSystem.addElement(menu, leaderSel, 130, 427, this);
        leaderSel.setVisible(false);

        //stat button settings
        menuButtons.add(statButton);
        buttonEffects.add(statSel);
        GameSystem.addElement(menu, statButton, 145, 530, this);
        GameSystem.addElement(menu, statSel, 130, 517, this);
        statSel.setVisible(false);

        //quit button settings
        menuButtons.add(quitButton);
        buttonEffects.add(quitSel);
        GameSystem.addElement(menu, quitButton, 145, 620, this);
        GameSystem.addElement(menu, quitSel, 130, 607, this);
        quitSel.setVisible(false);

        //settings button
        menuButtons.add(settings);
        buttonEffects.add(settingSel);
        GameSystem.addElement(menu, settings, 1323, 640, this);
        GameSystem.addElement(menu, settingSel, 1320, 637, this);
        settingSel.setVisible(false);

        //username display
        nameDisplay = new JLabel(user.username);
        menu.add(nameDisplay);
        nameDisplay.setSize(user.username.length() * 25, 35);
        nameDisplay.setFont(new Font("Copperplate", Font.PLAIN, 35));
        nameDisplay.setForeground(Color.WHITE);
        nameDisplay.setLocation(menu.getWidth() - 80 - nameDisplay.getWidth(), 700);
        nameDisplay.setHorizontalAlignment(JLabel.CENTER);
        nameDisplay.setVerticalAlignment(JLabel.CENTER);

        //profile settings
        GameSystem.addElement(menu, profile, 1110, 690);

        //background settings
        GameSystem.addElement(menu, bg, 0, 0);

        //adding components to current frame
        frame.add(menu, Integer.valueOf(1));
    }

    /**
     * This method initializes the instructions page on a separate JPanel
     */
    public void initializeInstructions() {
        instructions = new JPanel();
        instructions.setSize(1400, 800);
        instructions.setLayout(null);
        instructions.setOpaque(false);

        GameSystem.addElement(instructions, instrucPage, 0, 0);

        frame.add(instructions, Integer.valueOf(2));
        instructions.setVisible(false);
    }

    /**
     * This method initializes the leaderboard page on a separate JPanel
     */
    public void initializeLeaderboard() {
        leaderboard = new JPanel();
        leaderboard.setSize(1400, 800);
        leaderboard.setLayout(null);
        leaderboard.setOpaque(false);

        GameSystem.addElement(leaderboard, leaderPage, 0, 0);

        frame.add(leaderboard, Integer.valueOf(3));
        leaderboard.setVisible(false);
    }

    /**
     * This method displays the name of the users on the leaderboard
     */
    public void loadLeaderboard() {
        int x = 600;
        int y = 275;
        for (int i = 0; i < leader.leaderboard.length; i++) {
            GameSystem.addText(leaderboard, leader.leaderboard[i], x, y, 400, 50, 60);
            y += 75;
        }

        leaderboard.remove(noLeader);
        GameSystem.addElement(leaderboard, leaderPage, 0, 0);
        leaderboard.repaint();
    }

    /**
     * This method displays a "no stats" page if there isn't a leaderboard yet
     */
    public void noLeaderboard() {
        leaderboard.remove(leaderPage);
        GameSystem.addElement(leaderboard, noLeader, 0, 0);
        leaderboard.repaint();
    }

    /**
     * This method initializes the stats page on a separate JPanel
     */
    public void initializeStats() {
        stats = new JPanel();
        stats.setSize(1400, 800);
        stats.setLayout(null);
        stats.setOpaque(false);

        GameSystem.addElement(stats, statPage, 0, 0);

        frame.add(stats, Integer.valueOf(4));
        stats.setVisible(false);
    }

    /**
     * This method loads the stats of the current user from the user class, and displays it
     */
    public void loadStats() {
        stats.removeAll();
        int x = 800;
        int y = 310;
        for (int d : user.data) {
            GameSystem.addText(stats, String.valueOf(d), x, y, 150, 50, 80);
            y += 80;
        }

        stats.remove(noStats);
        GameSystem.addElement(stats, statPage, 0, 0);
        stats.repaint();
    }

    /**
     * This method displays a "no stats" page if the user has not played any games
     */
    public void noStats() {
        stats.remove(statPage);
        GameSystem.addElement(stats, noStats, 0, 0);
        stats.repaint();
    }

    /**
     * This method initializes the volume settings on the menu frame
     */
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
        GameSystem.addElement(vF, vDown, 10, 8, this);

        //volume up button
        volumeButtons.add(vUp);
        GameSystem.addElement(vF, vUp, 100, 7, this);

        //volume on/off button
        volumeButtons.add(vOn);
        volumeButtons.add(vOff);
        GameSystem.addElement(vF, vOn, 170, 12, this);
        GameSystem.addElement(vF, vOff, 173, 15, this);
        vOff.setVisible(false);

        //volume frame background
        vF.add(vBg);
        GameSystem.addElement(vF, vBg, 0, 0);

        frame.add(vF, Integer.valueOf(5));
        vF.setLocation(1100, 635);
        vF.setVisible(false);
    }

    /**
     * This method initializes a back button to be used for the menu
     */
    public void initializeBack() {
        back = new JPanel();
        back.setSize(75, 50);
        back.setLayout(null);
        back.setOpaque(false);

        GameSystem.addElement(back, goBack, 0, 0, this);

        frame.add(goBack, Integer.valueOf(6));
        goBack.setLocation(40, 40);
        goBack.setVisible(false);
    }

    /**
     * This method will initialize the game page to start a game of battleship
     */
    public void openGame() {
        new GamePage(this, user, leader);
        hideMenu();
    }

    /**
     * This method hides the current frame
     */
    public void hideMenu() {
        setVisible(false);
    }

    /**
     * This method shows the current frame
     */
    public void showMenu() {
        setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (isVisible()) {
            try {
                if (!goBack.isVisible()) {
                    int index = menuButtons.indexOf((JLabel) e.getComponent());
                    if (index == 0) {
                        buttonEffects.get(index).setVisible(false);
                        openGame();
                    }
                    else if (index == 1) {
                        buttonEffects.get(index).setVisible(false);
                        instructions.setVisible(true);
                        goBack.setVisible(true);
                        curOpen = instructions;
                    } else if (index == 2) {
                        buttonEffects.get(index).setVisible(false);
                        if (leader.hasLeaderboard) loadLeaderboard();
                        else noLeaderboard();

                        leaderboard.setVisible(true);
                        goBack.setVisible(true);
                        curOpen = leaderboard;
                    } else if (index == 3) {
                        buttonEffects.get(index).setVisible(false);
                        if (user.newUser) noStats();
                        else loadStats();
                        stats.setVisible(true);
                        goBack.setVisible(true);
                        curOpen = stats;
                    } else if (index == 4) {
                        MusicSound.playClick();
                        MusicSound.stopMusic(); //stop music
                        user.saveData();
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
                    MusicSound.playClick();
                }
                if (goBack.isVisible()) {
                    if (e.getComponent() == goBack) {
                        goBack.setVisible(false);
                        curOpen.setVisible(false);
                        MusicSound.playClick();
                    }
                }
            } catch (Exception ignored) {}
        }
    }
    @Override
    public void mouseEntered(MouseEvent e) {
        try {
            if (!goBack.isVisible() && isVisible()) {
                int index = menuButtons.indexOf((JLabel) e.getComponent());
                buttonEffects.get(index).setVisible(true);
                MusicSound.playTick();
            }
        } catch (Exception ignored) {}

    }
    @Override
    public void mouseExited(MouseEvent e) {
        try {
            if (!goBack.isVisible() && isVisible()) {
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
