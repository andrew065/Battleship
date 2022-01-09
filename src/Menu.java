import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Menu extends JDialog {
    int[] PLAY_CO = {145, 400};
    int[] INS_CO = {145, 490};
    int[] LEADER_CO = {145, 580};
    int[] STAT_CO = {145, 670};
    int[] QUIT_CO = {145, 760};
    int[] SETTINGS_CO = {1645, 870};
    int[] PROFILE_CO = {1380, 940};

    JLabel bg = new JLabel(new ImageIcon("Images/Battleship-Menu.png"));
    JLabel playButton = new JLabel(new ImageIcon("Images/Play_Button.png"));
    JLabel instrucButton = new JLabel(new ImageIcon("Images/Instructions_Button.png"));
    JLabel leaderButton = new JLabel(new ImageIcon("Images/Leaderboard_Button.png"));
    JLabel statButton = new JLabel(new ImageIcon("Images/Stats_Button.png"));
    JLabel quitButton = new JLabel(new ImageIcon("Images/Quit_Button.png"));
    JLabel settings = new JLabel(new ImageIcon("Images/Settings_Button.png"));
    JLabel playSel = new JLabel(new ImageIcon("Images/Play_Highlight.png"));
    JLabel instrucSel = new JLabel(new ImageIcon("Images/Leaderboard_Highlight.png"));
    JLabel leaderSel = new JLabel(new ImageIcon("Images/Leaderboard_Highlight.png"));
    JLabel statSel = new JLabel(new ImageIcon("Images/Stats_Highlight.png"));
    JLabel quitSel = new JLabel(new ImageIcon("Images/Quit_Highlight.png"));
    JLabel settingSel = new JLabel(new ImageIcon("Images/Settings_Highlight.png"));
    JLabel profile = new JLabel(new ImageIcon("Images/Profile.png"));

    ArrayList<JLabel> menuButtons = new ArrayList<>();
    ArrayList<JLabel> buttonEffects = new ArrayList<>();

    public Menu() {
        //dialogue settings
        setSize(1750, 1080);
        setName("Battleship");
        getContentPane().setLayout(null);

        //mouse listener for ui
        MouseListener menuListener = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    int index = menuButtons.indexOf((JLabel) e.getComponent());

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
        };

        //play button settings
        add(playButton);
        add(playSel);
        menuButtons.add(playButton);
        buttonEffects.add(playSel);
        playButton.setLocation(PLAY_CO[0], PLAY_CO[1]);
        playButton.setSize(playButton.getPreferredSize());
        playButton.addMouseListener(menuListener);
        playSel.setLocation(PLAY_CO[0] - 15, PLAY_CO[1] - 13);
        playSel.setSize(playSel.getPreferredSize());
        playSel.setVisible(false);

        //instructions button settings
        add(instrucButton);
        add(instrucSel);
        menuButtons.add(instrucButton);
        buttonEffects.add(instrucSel);
        instrucButton.setLocation(INS_CO[0], INS_CO[1]);
        instrucButton.setSize(instrucButton.getPreferredSize());
        instrucButton.addMouseListener(menuListener);
        instrucSel.setLocation(INS_CO[0] - 15, INS_CO[1] - 13);
        instrucSel.setSize(instrucSel.getPreferredSize());
        instrucSel.setVisible(false);

        //leaderboard button settings
        add(leaderButton);
        add(leaderSel);
        menuButtons.add(leaderButton);
        buttonEffects.add(leaderSel);
        leaderButton.setLocation(LEADER_CO[0], LEADER_CO[1]);
        leaderButton.setSize(leaderButton.getPreferredSize());
        leaderButton.addMouseListener(menuListener);
        leaderSel.setLocation(LEADER_CO[0] - 15, LEADER_CO[1] - 13);
        leaderSel.setSize(leaderSel.getPreferredSize());
        leaderSel.setVisible(false);

        //stat button settings
        add(statButton);
        add(statSel);
        menuButtons.add(statButton);
        buttonEffects.add(statSel);
        statButton.setLocation(STAT_CO[0], STAT_CO[1]);
        statButton.setSize(statButton.getPreferredSize());
        statButton.addMouseListener(menuListener);
        statSel.setLocation(STAT_CO[0] - 15, STAT_CO[1] - 13);
        statSel.setSize(statSel.getPreferredSize());
        statSel.setVisible(false);

        //quit button settings
        add(quitButton);
        add(quitSel);
        menuButtons.add(quitButton);
        buttonEffects.add(quitSel);
        quitButton.setLocation(QUIT_CO[0], QUIT_CO[1]);
        quitButton.setSize(quitButton.getPreferredSize());
        quitButton.addMouseListener(menuListener);
        quitSel.setLocation(QUIT_CO[0] - 15, QUIT_CO[1] - 13);
        quitSel.setSize(quitSel.getPreferredSize());
        quitSel.setVisible(false);

        //settings button
        add(settings);
        add(settingSel);
        menuButtons.add(settings);
        buttonEffects.add(settingSel);
        settings.setLocation(SETTINGS_CO[0], SETTINGS_CO[1]);
        settings.setSize(settings.getPreferredSize());
        settings.addMouseListener(menuListener);
        settingSel.setLocation(SETTINGS_CO[0] - 5, SETTINGS_CO[1] - 5);
        settingSel.setSize(settingSel.getPreferredSize());
        settingSel.setVisible(false);

        //profile settings
        add(profile);
        profile.setLocation(PROFILE_CO[0], PROFILE_CO[1]);
        profile.setSize(profile.getPreferredSize());

        //background settings
        add(bg);
        bg.setLocation(0, 0);
        bg.setSize(1920, 1080);

        //JDialog settings
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }
}
