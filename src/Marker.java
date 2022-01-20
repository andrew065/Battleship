import javax.swing.*;

public class Marker extends GameObject {
    boolean hit = false;
    private JLabel marker = new JLabel(new ImageIcon("Images/Game/Transparent_Tile.png"));

    public Marker(JPanel layer, int x, int y) {
        super(layer, x, y);

        this.layer.add(marker);
        refresh();
    }

    public void displayMarker(JLabel marker, boolean hit) {
        layer.remove(this.marker);

        this.hit = hit;
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
