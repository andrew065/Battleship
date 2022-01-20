import java.io.IOException;
import java.util.Random;
import java.util.ArrayList;

public class AI {
    public static int[] getCoords() {
        int[] coordinate = new int[2];


        return coordinate;
    }

    public int[] easy() {
        int[] hitCoor = new int[2];
        hitCoor[0] = (int) (Math.random() * 10);
        hitCoor[1] = (int) (Math.random() * 10);
        while(Game.aiToUserBoardHits[hitCoor[0]][hitCoor[1]] == 1 || Game.aiToUserBoardHits[hitCoor[0]][hitCoor[1]] == 2) {
            hitCoor[0] = (int) (Math.random() * 10);
            hitCoor[1] = (int) (Math.random() * 10);
        }
        return hitCoor;
    }
    public void medium() {}
    public void hard() {}
    public void hunt() {

    }
}
