import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.List;

public class GamePage extends JDialog implements KeyListener, MouseListener {
    JLayeredPane frame; //layered pane of game page frame

    //JPanels for different displays
    JPanel background;
    JPanel shipsPage;
    JPanel difficulty;
    JPanel markers;

    //initializing ai difficulty sprites
    JLabel easy = new JLabel(new ImageIcon("Images/Game/1_Easy.png"));
    JLabel med = new JLabel(new ImageIcon("Images/Game/2_Medium.png"));
    JLabel hard = new JLabel(new ImageIcon("Images/Game/3_Hard.png"));
    JLabel diffSel1 = new JLabel(new ImageIcon("Images/Game/AI_Selection.png"));
    JLabel diffSel2 = new JLabel(new ImageIcon("Images/Game/AI_Selection.png"));
    JLabel diffSel3 = new JLabel(new ImageIcon("Images/Game/AI_Selection.png"));
    JLabel aiDiffBg = new JLabel(new ImageIcon("Images/Game/AI_Difficulty_Bg.png"));

    List<JLabel> buttons = Arrays.asList(easy, med, hard);
    List<JLabel> selection = Arrays.asList(diffSel1, diffSel2, diffSel3);

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
    Ship[] allShips = new Ship[5];
    int[] shipLengthList = {5, 4, 3, 3, 2};
    int shipsPlaced = 0;
    int curShip = 0;

    public GamePage() {
        frame = getLayeredPane(); //initialize frame

        //frame settings
        setSize(1459, 821);
        setName("Battleship");
        setLocationRelativeTo(null);

        //initialize game components
        initializeBackground();
        initializeShipsPage();
        addShip();

        //frame settings
        frame.setSize(1459, 821);
        frame.setLocation(0, 0);
        setVisible(true);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addKeyListener(this);
        addMouseListener(this);
    }

    /**
     * This method initializes the background of the game
     */
    public void initializeBackground() {
        background = new JPanel();
        background.setSize(1459, 821);
        background.setLayout(null);
        background.setOpaque(false);

        GameSystem.addElement(background, bg, 0, 0);

        frame.add(background, new Integer(1));
    }

    /**
     * This method initializes the JPanel that holds the ship sprites
     */
    public void initializeShipsPage() {
        shipsPage = new JPanel();
        shipsPage.setSize(1459, 821);
        shipsPage.setLayout(null);
        shipsPage.setOpaque(false);

        frame.add(shipsPage, new Integer(2));
    }

    /**
     * This method initializes a new ship from the Ship class
     */
    public void addShip() {
        Ship ship = new Ship(shipsPage, ships[curShip], shipsR[curShip], 55, 170, shipLengthList[curShip]);
        allShips[curShip] = ship;
    }

    /**
     * This method checks whether a ships position is valid or not by comparing it with the position of other ships
     * @param ship - the object that is currently trying to be placed by the user
     * @return - a boolean indicating the validity of the position
     */
    public boolean positionValid(Ship ship) {
        int[][] curCoords = ship.getPosition(); //get the coordinates of the current ship

        for (int i = 0; i < 5; i++) {
            if (allShips[i] != null && allShips[i] != ship) {
                int[][] existingShipCoord = allShips[i].getPosition(); //get coordinates of existing ship

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

        frame.add(difficulty, new Integer(3));
    }

    /**
     * Adds a marker to the player or enemy side of the board
     * @param x - x coordinate of coordinate
     * @param y - y coordinate of coordinate
     * @param type - player/enemy side of board
     * @param hit - hit/miss
     */
    public void addMarker(int x, int y, int type, boolean hit) {

    }

    //KeyListener methods
    @Override
    public void keyPressed(KeyEvent e) {
        if (curShip < 4) {
            if (e.getKeyCode() == KeyEvent.VK_UP) { //move the ship up
                allShips[curShip].move(0);
            }
            if (e.getKeyCode() == KeyEvent.VK_LEFT) { //move the ship left
                allShips[curShip].move(1);
            }
            else if (e.getKeyCode() == KeyEvent.VK_DOWN) { //move the ship down
                allShips[curShip].move(2);
            }
            else if (e.getKeyCode() == KeyEvent.VK_RIGHT) { //move the ship right
                allShips[curShip].move(3);
            }
            else if (e.getKeyCode() == KeyEvent.VK_SPACE) { //rotate the ship
                allShips[curShip].rotate();
            }
            else if (e.getKeyCode() == KeyEvent.VK_ENTER) { //try placing the ship
                if (positionValid(allShips[curShip])) { //if the position is valid, add the next ship to be placed
                    shipsPlaced++;
                    curShip++;
                    addShip();
                }
                else {
                    System.out.println("invalid placement"); //display error message if position is invalid
                }
            }
        }
        else {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) { //start the game if all ships have been placed
                    shipsPlaced++;
                    curShip++;
                    System.out.println("confirm placement");
                    getAIDifficulty();
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
        System.out.println(e.getX() + " " + e.getY());
        try {
            int index = buttons.indexOf((JLabel) e.getComponent());
            difficulty.setVisible(false);
            GameSystem.signalStart();
        } catch (Exception ignored) {}
    }
    @Override
    public void mouseEntered(MouseEvent e) {
        try {
            int index = buttons.indexOf((JLabel) e.getComponent());
            selection.get(index).setVisible(true);
        } catch (Exception ignored) {}
    }
    @Override
    public void mouseExited(MouseEvent e) {
        try {
            int index = buttons.indexOf((JLabel) e.getComponent());
            selection.get(index).setVisible(false);
        } catch (Exception ignored) {}
    }
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {}
}
