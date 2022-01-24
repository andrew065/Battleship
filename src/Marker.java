import javax.swing.*;

/**
 * @author Andrew Lian
 */

public class Marker extends GameObject {
    private JLabel marker = new JLabel(new ImageIcon("Images/Game/Transparent_Tile.png"));

    public Marker(JPanel layer, int x, int y) {
        super(layer, x, y);

        this.layer.add(marker);
        refresh();
    }

    public void displayMarker(JLabel marker) {
        this.marker = marker;
        layer.add(this.marker);
        refresh();
    }

    @Override
    public void refresh() {
        marker.setLocation(x, y);
        marker.setSize(marker.getPreferredSize());
        layer.repaint();
    }
}
