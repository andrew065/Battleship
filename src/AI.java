import java.io.IOException;
import java.util.Random;
import java.util.ArrayList;

public class AI {
    boolean lastShotHit = false;
    int[][] shootGrid = new int[10][10];
    static int[] currentCoor = {4, 4};
    static int layer = 0;
    boolean triedLeft = false;
    boolean triedRight = false;
    boolean triedDown = false;
    boolean triedUp = false;

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
    public int[] hard() {
        if(lastShotHit) {
            return hunt();
        } else {
            if(layer == 0) {
                layer++;
                return currentCoor;
            } else {
                if(checkDoneLayer()) {
                    layer++;
                }
                int[] possibleCoor = currentCoor;
                possibleCoor[0]+=2;
                possibleCoor[1]+=2;
                while(!(currentCoor[0] <= layer && currentCoor[1] <= layer)) {
                    possibleCoor = currentCoor;
                    possibleCoor[0]-=-2;
                    possibleCoor[1]-=-2;
                }
            }
        }
        return currentCoor;
    }

    private boolean checkDoneLayer() {
        for(int i  = 0; i < shootGrid.length; i++) {
            for(int j  = 0; j < shootGrid[i].length; j++) {
                if(i <= layer && j <= layer && i % 2 == 0 && j % 2 == 0) {
                    if(shootGrid[i][j] == 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private int[] hunt() {
        int[] huntCoor = currentCoor;
        return huntCoor;
    }
}
