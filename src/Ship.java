import javax.swing.*;
import java.util.Arrays;

/**
 * @author Andrew Lian, Eric K., Derrick Ha
 * @description This class is for ships and all the different functions related to them when placing and in gameplay and uses OBJECT INHERITANCE by extending GameObject.
 */

public class Ship extends GameObject {
    public int length;
    public int hits;
    public boolean horizontal = true;
    private final int[][] position;

    private JLabel VERTICAL = new JLabel();
    private JLabel HORIZONTAL = new JLabel();
    private JLabel current;

    public Ship(JPanel layer, JLabel current, int x, int y, int length, boolean horizontal) {
        super(layer, x * 61 + 793, y * 61 + 170);

        this.current = current;
        this.length = length;
        this.horizontal = horizontal;

        position = new int[length][2];
        hits = 0;

        this.layer.add(current);
        refresh();
        current.setVisible(false);
    }

    public Ship(JPanel layer, JLabel horizontal, JLabel vertical, int x, int y, int length) {
        super(layer, x, y);
        VERTICAL = vertical;
        HORIZONTAL = horizontal;

        this.length = length;

        position = new int[length][2];
        current = HORIZONTAL;

        this.layer.add(current);
        refresh();
    }

    /**
     * This method refreshes the sprite in the JPanel by updating its position and size
     */
    @Override
    public void refresh() {
        current.setLocation(x, y);
        current.setSize(current.getPreferredSize());
        layer.repaint();
    }

    /**
     * This method sets sprite to visible, and also indicates that the current object is sunk
     */
    public void sunk() {
        current.setVisible(true);
        refresh();
    }

    /**
     * This method takes in a direction and moves the ship in that direction by 1 square on the grid
     * @param dir - the direction (0-3) - up, down, left, right
     */
    public void move(int dir) {
        if (dir == 0 && y >= 226) y -= 61; //move up
        else if (dir == 1 && x >= 116) x -= 61; //move left
        else if (dir == 2) { //move down
            if (!horizontal && y <= 170 + 61 * (9 - length)) y += 61;
            else if (horizontal && y <= 658) y = y + 61;
        }
        else if (dir == 3) { //move right
            if (horizontal && x <= 55 + 61 * (9 - length)) x += 61;
            else if (!horizontal && x <= 604) x += 61;
        }
        refresh();
    }

    /**
     * This method rotates the ship 90º when called
     */
    public void rotate() {
        layer.remove(current);

        //error correction to ensure the ship stays within each grid
        if (!horizontal && x > 55 + 61 * (10 - length)) x = 60 + 61 * (10 - length);
        else if (horizontal && y > 170 + 61 * (10 - length)) y = 170 + 61 * (10 - length);

        if (current == HORIZONTAL) { //rotate from horizontal -> vertical
            current = VERTICAL;
            x += 5;
            y -= 5;
        }
        else { //rotate from vertical -> horizontal
            current = HORIZONTAL;
            x -= 5;
            y += 5;
        }

        //update system and GUI
        horizontal = !horizontal;
        layer.add(current);
        refresh();
    }

    /**
     * This method will calculate the coordinates of the ship
     * @return - A 2D array containing the coordinates of the ship
     */
    public int[][] getPosition(int xBuffer) {
        int x1 = (int) Math.floor((x - xBuffer) / 61.0); //find leftmost x coordinate and convert to 1-10
        int y1 = (int) Math.floor((y - 170 + 5) / 61.0); //find topmost y coordinate and convert to 1-10

        if (horizontal) { //gets the horizontal position
            for (int[] coords : position) Arrays.fill(coords, y1);
            for (int i = 0; i < position.length; i++) {
                position[i][0] = x1;
                x1++;
            }
        }
        else { //gets the vertical position
            for (int[] coords : position) Arrays.fill(coords, x1);
            for (int i = 0; i < position.length; i++) {
                position[i][1] = y1;
                y1++;
            }
        }
        return position; //return the 2D array containing the ship's position
    }
}
