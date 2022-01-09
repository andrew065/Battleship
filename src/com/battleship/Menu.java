package com.battleship;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Menu extends JDialog {
    int[] PLAY_CO = {145, 400};
    int[] LEADER_CO = {145, 490};
    int[] STAT_CO = {145, 580};
    int[] QUIT_CO = {145, 670};
    int[] SETTINGS_CO = {1835, 970};

    JLabel bg = new JLabel(new ImageIcon("Images/Battleship-Menu.png"));
    JLabel playButton = new JLabel(new ImageIcon("Images/Play_Button.png"));
    JLabel leaderButton = new JLabel(new ImageIcon("Images/Leaderboard_Button.png"));
    JLabel statButton = new JLabel(new ImageIcon("Images/Stats_Button.png"));
    JLabel quitButton = new JLabel(new ImageIcon("Images/Quit_Button.png"));
    JLabel playSel = new JLabel(new ImageIcon("Images/Play_Highlight.png"));
    JLabel leaderSel = new JLabel(new ImageIcon("Images/Leaderboard_Highlight.png"));
    JLabel statSel = new JLabel(new ImageIcon("Images/Stats_Highlight.png"));
    JLabel quitSel = new JLabel(new ImageIcon("Images/Quit_Highlight.png"));
    JLabel settings = new JLabel(new ImageIcon("Images/Settings_Button.png"));

    ArrayList<JLabel> menuButtons = new ArrayList<>();
    ArrayList<JLabel> buttonEffects = new ArrayList<>();

    public Menu() {
        //dialogue settings
        setSize(1920, 1080);
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
        playSel.addMouseListener(menuListener);

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
        leaderSel.addMouseListener(menuListener);

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
        statSel.addMouseListener(menuListener);

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
        quitSel.addMouseListener(menuListener);

        //settings button
        add(settings);
        settings.setLocation(SETTINGS_CO[0], SETTINGS_CO[1]);
        settings.setSize(settings.getPreferredSize());
        settings.addMouseListener(menuListener);
        menuButtons.add(settings);

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
