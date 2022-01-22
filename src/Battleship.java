import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

/**
 * @author Andrew Lian
 */

public class Battleship implements MouseListener {
    private final Marker[][] AIGrid = new Marker[10][10];
    private final Marker[][] userGrid = new Marker[10][10];

    private int userSunk = 0;
    private int AISunk = 0;

    private final JPanel mLayer;
    private final JPanel sLayer;

    public Ship[] ships;

    public Battleship(JPanel mLayer, JPanel sLayer) {
        this.mLayer = mLayer;
        this.mLayer.addMouseListener(this);
        this.sLayer = sLayer;

        GameSystem.createTime(mLayer);

        createMarkers(userGrid, 63, 170);
        createMarkers(AIGrid, 793, 170);

        ships = AI.randomPlaceShip(this.sLayer);

        for (Ship x : ships) System.out.println(Arrays.deepToString(x.getPosition(793)));
        // add code here
    }

    public void createMarkers(Marker[][] grid, int x, int y) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                grid[i][j] = new Marker(mLayer, x + 61 * i, y + 61 * j);
            }
        }
    }

    public void userHit(int x, int y) {
        boolean hit = false;

        for (Ship s : ships) {
            for (int[] co : s.getPosition(793)) {
                if (co[0] == x && co[1] == y) {
                    hit = true;
                    s.hits++;

                    if (s.hits == s.length) {
                        s.sunk();
                        AISunk++;
                    }
                    break;
                }
            }
        }

        if (hit) AIGrid[x][y].displayMarker(new JLabel(new ImageIcon("Images/Game/Hit_Marker.png")), true);
        else AIGrid[x][y].displayMarker(new JLabel(new ImageIcon("Images/Game/Miss_Marker.png")), false);

        //call AI fire methods
        //check if game is over after both sides fire
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getX() >= 793 && e.getY() >= 170) {
            int x = (e.getX() - 793) / 61; //x coordinate (1-10)
            int y = (e.getY() - 170) / 61; //y coordinate (1-10)
            if (x <= 10 && y <= 10) userHit(x, y);
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
