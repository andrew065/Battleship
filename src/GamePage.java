import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author Andrew Lian, Eric K.
 * @description This class is used for displaying all the GUI elements for the game and for some gameplay functions such as placing ships.
 */

public class GamePage extends JDialog implements KeyListener, MouseListener {
    JLayeredPane frame; //layered pane of game page frame

    //JPanels for different displays
    JPanel background;
    JPanel shipsPage;
    JPanel coinTossPage;
    JPanel difficulty;
    JPanel markers;
    JPanel endPage;

    //initializing coin toss sprites
    JLabel tossButton = new JLabel(new ImageIcon("Images/Game/Toss_Coin_Button.png"));
    JLabel tossSel = new JLabel(new ImageIcon("Images/Game/Toss_Coin_Highlight.png"));
    JLabel userStarts = new JLabel(new ImageIcon("Images/Game/User_Starts.png"));
    JLabel aiStarts = new JLabel(new ImageIcon("Images/Game/AI_Starts.png"));
    JLabel tossing = new JLabel(new ImageIcon("Images/Game/Tossing.png"));
    JLabel tossBg = new JLabel(new ImageIcon("Images/Game/Toss_Coin_Bg.png"));

    //initializing ai difficulty sprites
    JLabel easy = new JLabel(new ImageIcon("Images/Game/1_Easy.png"));
    JLabel med = new JLabel(new ImageIcon("Images/Game/2_Medium.png"));
    JLabel hard = new JLabel(new ImageIcon("Images/Game/3_Hard.png"));
    JLabel diffSel1 = new JLabel(new ImageIcon("Images/Game/AI_Selection.png"));
    JLabel diffSel2 = new JLabel(new ImageIcon("Images/Game/AI_Selection.png"));
    JLabel diffSel3 = new JLabel(new ImageIcon("Images/Game/AI_Selection.png"));
    JLabel aiDiffBg = new JLabel(new ImageIcon("Images/Game/AI_Difficulty_Bg.png"));

    //list of buttons for difficulty selection
    List<JLabel> buttons = Arrays.asList(easy, med, hard); //buttons
    List<JLabel> selection = Arrays.asList(diffSel1, diffSel2, diffSel3); //button highlights

    //initializing background/game icons & sprites
    JLabel bg = new JLabel(new ImageIcon("Images/Game/Game_Page.png"));
    JLabel win = new JLabel(new ImageIcon("Images/Game/Win_Page.png"));
    JLabel lose = new JLabel(new ImageIcon("Images/Game/Lose_Page.png"));
    JLabel menuButton = new JLabel(new ImageIcon("Images/Game/Return_Menu_Button.png"));
    JLabel menuSel = new JLabel(new ImageIcon("Images/Game/Return_Menu_Highlight.png"));
    JLabel carrier = new JLabel(new ImageIcon("Images/Ships/Aircraft_Carrier.png"));
    JLabel carrierR = new JLabel(new ImageIcon("Images/Ships/Aircraft_Carrier_Rotated.png"));
    JLabel battleship = new JLabel(new ImageIcon("Images/Ships/Battleship.png"));
    JLabel battleshipR = new JLabel(new ImageIcon("Images/Ships/Battleship_Rotated.png"));
    JLabel cruiser = new JLabel(new ImageIcon("Images/Ships/Cruiser.png"));
    JLabel cruiserR = new JLabel(new ImageIcon("Images/Ships/Cruiser_Rotated.png"));
    JLabel destroyer = new JLabel(new ImageIcon("Images/Ships/Destroyer.png"));
    JLabel destroyerR = new JLabel(new ImageIcon("Images/Ships/Destroyer_Rotated.png"));
    JLabel sub = new JLabel(new ImageIcon("Images/Ships/Submarine.png"));
    JLabel subR = new JLabel(new ImageIcon("Images/Ships/Submarine_Rotated.png"));

    //list containing ships for tracking
    JLabel[] ships = {carrier, battleship, cruiser, sub, destroyer};
    JLabel[] shipsR = {carrierR, battleshipR, cruiserR, subR, destroyerR};

    //trackers for ship placement
    Ship[] userShips = new Ship[5];
    int[] shipLengthList = {5, 4, 3, 3, 2};
    int shipsPlaced = 0;
    int curShip = 0;
    boolean allPlaced = false;

    Menu menu; //instance of menu to run methods in the menu
    User user; //instance of user to update user info
    Leaderboard leader;

    public boolean userStart = true;

    public GamePage(Menu menu, User user, Leaderboard leader) {
        this.menu = menu;
        this.user = user;
        this.leader = leader;
        frame = getLayeredPane(); //initialize frame

        //frame settings
        setSize(1459, 821);
        setName("Battleship");
        setLocationRelativeTo(null);

        //initialize game components
        initializeBackground();
        initializeCoinToss();

        //frame settings
        frame.setSize(1459, 821);
        frame.setLocation(0, 0);
        setVisible(true);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addKeyListener(this);
        addMouseListener(this);
    }

    /**
     * This method initializes the background of the game and adds the background components to that panel
     */
    public void initializeBackground() {
        background = new JPanel();
        background.setSize(1459, 821);
        background.setLayout(null);
        background.setOpaque(false);

        GameSystem.addElement(background, bg, 0, 0);

        frame.add(background, Integer.valueOf(1)); //first layer in frame
    }

    /**
     * This method initializes the JPanel that holds the ship sprites
     */
    public void initializeShipsPage() {
        shipsPage = new JPanel();
        shipsPage.setSize(1459, 821);
        shipsPage.setLayout(null);
        shipsPage.setOpaque(false);

        addShip(); //add the first ship for the user to start placing

        frame.add(shipsPage, Integer.valueOf(3)); //third layer in frame
    }

    /**
     * This method initializes a new ship from the Ship class
     */
    public void addShip() {
        Ship ship = new Ship(shipsPage, ships[curShip], shipsR[curShip], 55, 170, shipLengthList[curShip]);
        userShips[curShip] = ship;
    }

    /**
     * This method checks whether a ships position is valid or not by comparing it with the position of other ships
     * @param ship - the object that is currently trying to be placed by the user
     * @return - a boolean indicating the validity of the position
     */
    public boolean positionValid(Ship ship) {
        int[][] curCoords = ship.getPosition(55); //get the coordinates of the current ship

        for (int i = 0; i < 5; i++) {
            if (userShips[i] != null && userShips[i] != ship) {
                int[][] existingShipCoord = userShips[i].getPosition(55); //get coordinates of existing ship

                for (int[] co1 : existingShipCoord) { //loop through both arrays and compare values
                    for (int[] co2 : curCoords) {
                        if (Arrays.equals(co1, co2)) return false; //if there are matching values, return false
                    }
                }
            }
        }
        return true; //return true if all values are new and not overlapping
    }

    /**
     * This method initializes the coin toss panel and adds the components to it
     */
    public void initializeCoinToss() {
        coinTossPage = new JPanel();
        coinTossPage.setSize(1459, 821);
        coinTossPage.setLayout(null);
        coinTossPage.setOpaque(false);

        GameSystem.addElement(coinTossPage, tossButton, 537, 385, this);
        GameSystem.addElement(coinTossPage, tossSel, 522, 370);
        tossSel.setVisible(false);

        GameSystem.addElement(coinTossPage, tossBg, 494, 270); //toss page background

        frame.add(coinTossPage, Integer.valueOf(2)); //second layer in frame
    }

    /**
     * This method simulates a coin toss and displays whether the user or AI starts shooting first
     */
    public void tossCoin() {
        coinTossPage.removeAll(); //remove previous components
        GameSystem.addElement(coinTossPage, tossing, 494, 270);

        //run coin toss on new thread to prevent lagging
        Thread thread = new Thread(() -> {
            //pause thread to simulate coin toss
            try {
                Thread.sleep(700);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            tossing.setVisible(false);
            if (new Random().nextBoolean()) GameSystem.addElement(coinTossPage, userStarts, 494, 270);
            else {
                GameSystem.addElement(coinTossPage, aiStarts, 494, 270);
                userStart = false;
            }

            //pauses thread to allow user to read the updated info
            try {
                Thread.sleep(700);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            coinTossPage.setVisible(false);
            initializeShipsPage();
        });
        thread.start();
    }

    /**
     * This method creates buttons and displays it on the current frame to get the users desired AI difficulty
     */
    public void getAIDifficulty() {
        difficulty = new JPanel();
        difficulty.setSize(1459, 821);
        difficulty.setLayout(null);
        difficulty.setOpaque(false);

        int xPos = 590;
        for (int i = 0; i < 3; i++) {
            GameSystem.addElement(difficulty, buttons.get(i), xPos, 450, this);
            GameSystem.addElement(difficulty, selection.get(i), xPos - 10, 440, this);
            selection.get(i).setVisible(false);

            xPos += 100;
        }
        GameSystem.addElement(difficulty, aiDiffBg, 454, 218);

        frame.add(difficulty, Integer.valueOf(4));
    }

    /**
     * This method starts the game by initializing a JPanel for the markers and creating an instance of the battleship class
     */
    public void startGame(int diff) throws FileNotFoundException {
        markers = new JPanel();
        markers.setSize(1459, 821);
        markers.setLayout(null);
        markers.setOpaque(false);

        frame.add(markers, Integer.valueOf(5));
        new Battleship(this, markers, shipsPage, userShips, userStart);
        AI.difficulty = diff;
        if(diff == 2) {
            AI.addStartCoor();
        }
    }

    /**
     * This method initializes the end page
     * @param won - boolean indicating a win/loss from the user
     */
    public void initializeEndPage(boolean won, int sunk) throws FileNotFoundException {
        GameSystem.stopTime();
        user.updateData(sunk, won);
        leader.updateLeaderboard();

        endPage = new JPanel();
        endPage.setSize(1459, 821);
        endPage.setLayout(null);
        endPage.setOpaque(false);

        GameSystem.addElement(endPage, menuButton, 600, 490, this);
        GameSystem.addElement(endPage, menuSel, 600, 490, this);
        menuSel.setVisible(false);

        if (won) GameSystem.addElement(endPage, win, 0, 0);
        else GameSystem.addElement(endPage, lose, 0, 0);

        frame.add(endPage, Integer.valueOf(6));
    }

    /**
     * This method disposes the current frame, saves the game stats, and goes back to the menu
     */
    public void returnToMenu() {
        setVisible(false);
        dispose();
        //add end game operations (save stats, etc)
        menu.showMenu();
    }

    //KeyListener methods
    @Override
    public void keyPressed(KeyEvent e) {
        if (!allPlaced) {
            if (e.getKeyCode() == KeyEvent.VK_UP) { //move the ship up
                userShips[curShip].move(0);
                MusicSound.playTick();
            }
            if (e.getKeyCode() == KeyEvent.VK_LEFT) { //move the ship left
                userShips[curShip].move(1);
                MusicSound.playTick();
            }
            else if (e.getKeyCode() == KeyEvent.VK_DOWN) { //move the ship down
                userShips[curShip].move(2);
                MusicSound.playTick();
            }
            else if (e.getKeyCode() == KeyEvent.VK_RIGHT) { //move the ship right
                userShips[curShip].move(3);
                MusicSound.playTick();
            }
            else if (e.getKeyCode() == KeyEvent.VK_SPACE) { //rotate the ship
                userShips[curShip].rotate();
                MusicSound.playTick();
            }
            else if (e.getKeyCode() == KeyEvent.VK_ENTER) { //try placing the ship
                if (positionValid(userShips[curShip])) { //if the position is valid, add the next ship to be placed
                    shipsPlaced++;
                    curShip++;
                    if (curShip < 5) addShip();
                    else {
                        allPlaced = true;
                        getAIDifficulty();
                    }
                    MusicSound.playClick();
                }
                else {
                    System.out.println("invalid placement"); //display error message if position is invalid
                }
            }
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}

    //MouseListener methods
    @Override
    public void mouseClicked(MouseEvent e) {
        try {
            if (e.getComponent() == menuButton) {
                returnToMenu();
            }
            else if (e.getComponent() == tossButton) {
                tossCoin();
            }
            else {
                int index = buttons.indexOf((JLabel) e.getComponent()); //get ai difficulty
                difficulty.setVisible(false);
                GameSystem.signalStart();
                startGame(index);
            }
            MusicSound.playClick();
        } catch (Exception ignored) {}
    }
    @Override
    public void mouseEntered(MouseEvent e) {
        try {
            if (e.getComponent() == menuButton) menuSel.setVisible(true);
            else if (e.getComponent() == tossButton) tossSel.setVisible(true);
            else {
                int index = buttons.indexOf((JLabel) e.getComponent());
                selection.get(index).setVisible(true);
            }
            MusicSound.playTick();
        } catch (Exception ignored) {}
    }
    @Override
    public void mouseExited(MouseEvent e) {
        try {
            if (e.getComponent() == menuButton) menuSel.setVisible(false);
            else if (e.getComponent() == tossButton) tossSel.setVisible(false);
            else {
                int index = buttons.indexOf((JLabel) e.getComponent());
                selection.get(index).setVisible(false);
            }
        } catch (Exception ignored) {}
    }
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {}
}
