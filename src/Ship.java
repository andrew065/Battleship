import javax.swing.*;
import java.util.Arrays;

public class Ship extends GameObject {
    public int length;
    public boolean horizontal = true;
    private int[][] position;

    private final JLabel VERTICAL;
    private final JLabel HORIZONTAL;
    private JLabel current;

    public Ship(JPanel layer, JLabel horizontal, JLabel vertical, int x, int y, int length) {
        super(layer, x, y);
        VERTICAL = vertical;
        HORIZONTAL = horizontal;

        this.length = length;

        position = new int[length][2];
        current = HORIZONTAL;

        layer.add(current);
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

        //error correction to ensure the ship stays within the grid
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
    public int[][] getPosition() {
        int x1 = (int) Math.round((x - 55) / 61.0); //find leftmost x coordinate and convert to 1-10
        int y1 = (int) Math.round((y - 170) / 61.0); //find topmost y coordinate and convert to 1-10

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
