import javax.swing.*;

/**
 * An object within a GUI frame.
 * @author Andrew Lian
 */
public abstract class GameObject {
    public int x;
    public int y;

    public final JPanel layer;

    protected GameObject(JPanel layer, int x, int y) {
        this.layer = layer;
        this.x = x;
        this.y = y;
    }

    public abstract void refresh();
}
