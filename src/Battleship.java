import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Objects;

/**
 * @author Andrew Lian
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

    public GamePage game;

    public Battleship(GamePage game, JPanel mLayer, JPanel sLayer, Ship[] userShips) {
        this.mLayer = mLayer;
        this.mLayer.addMouseListener(this);
        this.userShips = userShips;
        this.game = game;

        GameSystem.createTime(mLayer);

        createMarkers(userGrid, 63);
        createMarkers(AIGrid, 793);
        addCounters();

        AIShips = AI.randomPlaceShip(sLayer);
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

    /**
     * This method checks if the user's shot is valid, and then updates the board and get a hit from the AI
     * @param x - x coordinate of user's shot
     * @param y - y coordiante of user's shot
     */
    public void userShot(int x, int y) throws InterruptedException {
        boolean uniqueShot = true;
        for (Ship s : AIShips) {
            for (int[] co : s.getPosition(793)) {
                if (co[0] == x && co[1] == y) {
                    if (Objects.requireNonNull(s.pastHits).contains(co)) {
                        uniqueShot = false;
                    }
                }
            }
        }
        if (uniqueShot) {
            checkHit(AIShips, AIGrid, x, y, 793, true); //check user's hit
            updateUserStats();

            //run on new thread to prevent sound from cutting each other out and to have delay shot for AI
            Thread thread = new Thread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int[] AICoord = AI.getShot(); //get AI hit
                checkHit(userShips, userGrid, AICoord[0], AICoord[1], 55, false); //check AI hit
                updateAIStats();
                checkGameOver();
            });
            thread.start();
        }
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
                    s.addHit(co);

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
                AI.shootGrid[x][y] = 1;
            }
            if (!sunk) MusicSound.playFire(2);
        }
        else {
            grid[x][y].displayMarker(new JLabel(new ImageIcon("Images/Game/Miss_Marker.png")), false);
            if (user) userMiss++;
            else {
                AIMiss++;
                AI.shootGrid[x][y] = 2;
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
            int x = (e.getX() - 793) / 61; //x coordinate (1-10)
            int y = (e.getY() - 170) / 61; //y coordinate (1-10)
            if (x <= 10 && y <= 10 && !gameOver) {
                try {
                    userShot(x, y);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
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
