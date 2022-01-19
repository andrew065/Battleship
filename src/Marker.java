import javax.swing.*;

public class Marker extends GameObject {

    boolean hit = false;
    private JLabel marker;

    public Marker(JPanel layer, int x, int y) {
        super(layer, x, y);

    }

    public void displayMarker(JLabel marker) {
        this.marker = marker;
    }

    @Override
    public void refresh() {
        marker.setLocation(x, y);
        marker.setSize(marker.getPreferredSize());
        layer.repaint();
    }

}
