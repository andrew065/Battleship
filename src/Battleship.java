import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Andrew Lian, Eric K.
 * @description This class updates the game board and records stats and gets AI and user shots.
 */
public class Battleship implements MouseListener {
    private final Marker[][] AIGrid = new Marker[10][10];
    private final Marker[][] userGrid = new Marker[10][10];

    private int userSunk = 0;
    private int AISunk = 0;

    private int userHit = 0;
    private int AIHit = 0;
    private int userMiss = 0;
    private int AIMiss = 0;

    private final boolean userStart;
    private boolean aiTurn;

    private boolean gameOver = false;

    private final JPanel mLayer;

    private JLabel userTotal;
    private JLabel AITotal;
    private JLabel userHitLabel;
    private JLabel AIHitLabel;
    private JLabel userMissLabel;
    private JLabel AIMissLabel;

    public Ship[] AIShips;
    public Ship[] userShips;

    public List<int[]> prevHits;

    public GamePage game;

    public Battleship(GamePage game, JPanel mLayer, JPanel sLayer, Ship[] userShips, boolean userStart) throws FileNotFoundException {
        this.mLayer = mLayer;
        this.mLayer.addMouseListener(this);
        this.userShips = userShips;
        this.game = game;
        this.userStart = userStart;

        GameSystem.createTime(mLayer);

        createMarkers(userGrid, 63);
        createMarkers(AIGrid, 793);
        addCounters();

        if(AI.difficulty != 2) {
            AIShips = AI.randomPlaceShip(sLayer);
        } else {
            AIShips = AI.weightedPlaceShip(sLayer);
        }
        GameSystem.exportShip(AIShips);

        prevHits = new ArrayList<>();
        if (!userStart) AIShot();
    }

    /**
     * This method creates the hit/miss markers for both the AI and user
     * @param grid - the grid belonging to AI/User
     * @param xBuff - x buffer
     */
    public void createMarkers(Marker[][] grid, int xBuff) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                grid[i][j] = new Marker(mLayer, xBuff + 61 * i, 170 + 61 * j);
            }
        }
    }

    /**
     * This method adds the in-game counters to the frame
     */
    public void addCounters() {
        userTotal = new JLabel(String.valueOf(userHit + userMiss));
        userHitLabel = new JLabel(String.valueOf(userHit));
        userMissLabel = new JLabel(String.valueOf(userMiss));
        AITotal = new JLabel(String.valueOf(AIHit + AIMiss));
        AIHitLabel = new JLabel(String.valueOf(AIHit));
        AIMissLabel = new JLabel(String.valueOf(AIMiss));

        GameSystem.addText(mLayer, userTotal, 475, 29, 50, 50, 25, false);
        GameSystem.addText(mLayer, userMissLabel, 465, 53, 50, 50, 25, false);
        GameSystem.addText(mLayer, userHitLabel, 513, 79, 50, 50, 25, false);
        GameSystem.addText(mLayer, AITotal, 938, 29, 50, 50, 25, true);
        GameSystem.addText(mLayer, AIMissLabel, 948, 53, 50, 50, 25, true);
        GameSystem.addText(mLayer, AIHitLabel, 898, 79, 50, 50, 25, true);

        mLayer.repaint();
    }

    public void fireShots(int x, int y) {
        if (userStart) {
            userShot(x, y);
        }
        else {
            if (!aiTurn) {
                userShot(x, y);
            }
        }
    }

    /**
     * This method checks if the user's shot is valid, and then updates the board and get a hit from the AI
     * @param x - x coordinate of user's shot
     * @param y - y coordiante of user's shot
     */
    public void userShot(int x, int y) {
        int[] co = {x, y};
        boolean unique = true;

        for (int[] coords : prevHits) {
            if (Arrays.equals(coords, co)) {
                unique = false;
                break;
            }
        }

        if (unique) {
            prevHits.add(co);
            checkHit(AIShips, AIGrid, x, y, 793, true); //check user's hit
            updateUserStats(); //updates the user's stats
            AIShot(); //call method for AI to shoot
        }
    }

    /**
     * This method gets the AI's shot and updates the grid on a separate thread 1 second after the user shoots
     */
    public void AIShot() {
        Thread thread = new Thread(() -> {
            aiTurn = true;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int[] AICoord = AI.getShot(); //get AI hit
            checkHit(userShips, userGrid, AICoord[0], AICoord[1], 55, false); //check AI hit
            updateAIStats(); //update the AI's stats
            aiTurn = false;
            checkGameOver(); //check if game is over
        });
        thread.start(); //start the thread
    }

    /**
     * This method checks if the given shot from the given side is a hit/miss
     * @param ships - the array of ships to check if hit
     * @param grid - the grid to update
     * @param x - x coordinate of shot
     * @param y - y coordinate of shot
     * @param buff - x buffer
     * @param user - boolean indicating if the shot is from user/AI
     */
    public void checkHit(Ship[] ships, Marker[][] grid, int x, int y, int buff, boolean user) {
        boolean hit = false;
        boolean sunk = false;

        for (Ship s : ships) {
            for (int[] co : s.getPosition(buff)) {
                if (co[0] == x && co[1] == y) {
                    hit = true;
                    s.hits++;

                    if (s.hits == s.length) {
                        s.sunk();
                        sunk = true; //for sound effects
                        if (user) userSunk++;
                        else AISunk++;
                        MusicSound.playSunk();
                    }
                    break;
                }
            }
        }
        if (hit) {
            grid[x][y].displayMarker(new JLabel(new ImageIcon("Images/Game/Hit_Marker.png")), true);
            if (user) userHit++;
            else {
                AIHit++;
                AI.shootGrid[y][x] = 2;
            }
            if (!sunk) MusicSound.playFire(2);
        }
        else {
            grid[x][y].displayMarker(new JLabel(new ImageIcon("Images/Game/Miss_Marker.png")), false);
            if (user) userMiss++;
            else {
                AIMiss++;
                AI.shootGrid[y][x] = 1;
            }
            MusicSound.playFire(1);
        }
    }

    /**
     * This method updates the user's in-game stats
     */
    public void updateUserStats() {
        userTotal.setText(String.valueOf(userHit + userMiss));
        userHitLabel.setText(String.valueOf(userHit));
        userMissLabel.setText(String.valueOf(userMiss));
        AITotal.setText(String.valueOf(AIHit + AIMiss));
        AIHitLabel.setText(String.valueOf(AIHit));
        AIMissLabel.setText(String.valueOf(AIMiss));
    }

    /**
     * This method updates the AI's in-game stats
     */
    public void updateAIStats() {
        AITotal.setText(String.valueOf(AIHit + AIMiss));
        AIHitLabel.setText(String.valueOf(AIHit));
        AIMissLabel.setText(String.valueOf(AIMiss));
    }

    /**
     * This method checks if the game is over. If it is, it calls the endPage method from the GamePage class
     */
    public void checkGameOver() {
        if (userSunk == 5) {
            System.out.println();
            gameOver = true;
            game.initializeEndPage(true, userSunk);
            MusicSound.playBells();
        }
        else if (AISunk == 5) {
            System.out.println("AI wins");
            gameOver = true;
            game.initializeEndPage(false, userSunk);
            MusicSound.playHorn();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getX() >= 793 && e.getY() >= 170) {
            int x = (int) Math.floor((e.getX() - 793) / 61.0); //x coordinate (1-10)
            int y = (int) Math.floor((e.getY() - 170) / 61.0); //y coordinate (1-10)
            if (x <= 10 && y <= 10 && !gameOver) {
                fireShots(x, y);
            }
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
}
