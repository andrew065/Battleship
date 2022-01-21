import java.util.Arrays;
import java.util.Random;

public class AI {
    boolean lastShotHit = false;

    private int difficulty;

    public void setDiff(int diff) {
        difficulty = diff;
    }

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
    public void hard() {
        if(lastShotHit) {

        }
    }
    public void hunt() {

    }

    /**
     * Places 5 ships randomly & returns integer values based on it.
     */
    public static int[][] randomPlaceShip() {
        Random rand = new Random(System.currentTimeMillis());

        boolean[][] marked = new boolean[10][10]; // places where other ships can't be placed

        int[][] ships = new int[5][10];
        for (int[] ship : ships) {
            Arrays.fill(ship, -2);
        }

        boolean isRetry;
        int shipSize = 5; // index in ships
        for (int shipI = 0; shipI < 5; shipI++) {
            repeat:
            do {
                isRetry = false;

                ships[shipI][0] = rand.nextInt(10); // even number is row number
                ships[shipI][1] = rand.nextInt(10); // odd number is column number

                boolean isVertical = rand.nextBoolean();

                if (isVertical) {
                    for (int i = 2; i < shipSize * 2; i += 2) { // fill vertically
                        ships[shipI][i] = ships[shipI][0];
                        ships[shipI][i + 1] = ships[shipI][1] + i / 2;
                    }
                } else {
                    for (int i = 2; i < shipSize * 2; i += 2) { // fill horizontally
                        ships[shipI][i] = ships[shipI][0] + i / 2;
                        ships[shipI][i + 1] = ships[shipI][1];
                    }
                }

                // retry conditions
                if ((ships[shipI][shipSize * 2 - 2] >= 10) || (ships[shipI][shipSize * 2 - 1] >= 10)) { // 1. out of bounds
                    isRetry = true;
                    continue;
                }
                for (int i = 0; i < shipSize * 2; i += 2) { // 2. touches a marked spot
                    if (marked[ships[shipI][i]][ships[shipI][i + 1]]) {
                        isRetry = true;
                        continue repeat;
                    }
                }

                // record as marked
                for (int i = 0; i < shipSize * 2; i += 2) {
                    marked[ships[shipI][i]][ships[shipI][i + 1]] = true;

                    if (isVertical) { // make buffer for the ship's long sides
                        if (ships[shipI][i] + 1 < 10) {
                            marked[ships[shipI][i] + 1][ships[shipI][i + 1]] = true;
                        }
                        if (ships[shipI][i] - 1 >= 0) {
                            marked[ships[shipI][i] - 1][ships[shipI][i + 1]] = true;
                        }
                    } else {
                        if (ships[shipI][i + 1] + 1 < 10) { // check for out of bounds
                            marked[ships[shipI][i]][ships[shipI][i + 1] + 1] = true;
                        }
                        if (ships[shipI][i + 1] - 1 >= 0) {
                            marked[ships[shipI][i]][ships[shipI][i + 1] - 1] = true;
                        }
                    }
                }

                if (shipI == 2) { // because submarine exists
                    shipSize++;
                }
                shipSize--;
            } while (isRetry);
/*
            String append = switch (shipI) {
                case 0: yield "Aircraft Carrier";
                case 1: yield "Battleship";
                case 2: yield "Cruiser";
                case 3: yield "Submarine";
                case 4: yield "Destroyer";
                default:
                    System.out.println("unauthorized ship size from AI");
                    yield "";
            }; // use for game class*/
        }

        return ships;
    }
}
