import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * @author Andrew Lian
 */

public class Battleship implements MouseListener {
    private Marker[][] AIGrid = new Marker[10][10];
    private Marker[][] userGrid = new Marker[10][10];

    private int userSunk;
    private int AISunk;

    private final JPanel layer;

    public Battleship(JPanel layer) {
        this.layer = layer;
        this.layer.addMouseListener(this);

        createMarkers(userGrid, 63, 170);
        createMarkers(AIGrid, 793, 170);

        int[][] ships = AI.randomPlaceShip();
        for (int i = 0; i < ships.length; i++) {
            for (int j = 0; j < ships[i].length; j++) {
                System.out.print(ships[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void createMarkers(Marker[][] grid, int x, int y) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                grid[i][j] = new Marker(layer, x + 61 * i, y + 61 * j);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getX() > 700) {
            int x = (e.getX() - 793) / 61; //x coordinate (1-10)
            int y = (e.getY() - 170) / 61; //y coordinate (1-10)
            AIGrid[x][y].displayMarker(new JLabel(new ImageIcon("Images/Game/Hit_Marker.png")), true); //change later
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
