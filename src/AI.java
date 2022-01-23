import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class AI {
    static boolean lastShotHit = false;
    public static int[][] shootGrid = new int[10][10];
    static int[] currentCoor = {4, 4};
    static ArrayList<int[]> visitedCoor = new ArrayList<>();
    static ArrayList<int[]> coorToVisit = new ArrayList<>();
    static int currentShootCoor = 0;

    public static int difficulty;

    public static int[] getShot() {
        int[] coords = new int[2];
        if (difficulty == 0) coords = easy();
        return coords;
    }

    public static int[] easy() {
        int[] hitCoor = new int[2];
        hitCoor[0] = (int) (Math.random() * 10);
        hitCoor[1] = (int) (Math.random() * 10);
        while(shootGrid[hitCoor[0]][hitCoor[1]] == 1 || shootGrid[hitCoor[0]][hitCoor[1]] == 2) {
            hitCoor[0] = (int) (Math.random() * 10);
            hitCoor[1] = (int) (Math.random() * 10);
        }
        return hitCoor;
    }

    public int[] medium() { // shoots strategically but does not zone in on ships when it hits
        currentCoor = visitedCoor.get(currentShootCoor);
        currentShootCoor+=2;
        return currentCoor;
    }

    public int[] hard() {
        shootGrid = Game.aiToUserBoardHits;

        if(shootGrid[currentCoor[1]][currentCoor[0]] == 2) {
            lastShotHit = true;
            hunt();
        }

        if(!coorToVisit.isEmpty()) {
            int[] nextShot = coorToVisit.get(0);
            coorToVisit.remove(0);
            return nextShot;
        }

        do {
            currentCoor = visitedCoor.get(currentShootCoor);
            currentShootCoor+=2;
        } while(shootGrid[currentCoor[1]][currentCoor[0]] == 1 || shootGrid[currentCoor[1]][currentCoor[0]] == 2);

        return currentCoor;
    }

    public static void setVisited() { // call outside class
        int degree = 0;
        int lastDegree = 0;
        int[] currentCoor = {4, 5};

        while(true) {
            if(visitedCoor.size() == 100) {
                break;
            }
            if(degree == 0 && !checkVisited(currentCoor, degree)) {
                visitedCoor.add(currentCoor.clone());
                currentCoor[0]++;
                lastDegree = degree;
                degree++;
            } else if(degree == 1 && !checkVisited(currentCoor, degree)) {
                visitedCoor.add(currentCoor.clone());
                currentCoor[1]--;
                lastDegree = degree;
                degree++;
            } else if(degree == 2 && !checkVisited(currentCoor, degree)) {
                visitedCoor.add(currentCoor.clone());
                currentCoor[0]--;
                lastDegree = degree;
                degree++;
            } else if(degree == 3 && !checkVisited(currentCoor, degree)) {
                visitedCoor.add(currentCoor.clone());
                currentCoor[1]++;
                lastDegree = degree;
                degree = 0;
            } else {
                if(lastDegree == 0) {
                    visitedCoor.add(currentCoor.clone());
                    currentCoor[0]++;
                } else if(lastDegree == 1) {
                    visitedCoor.add(currentCoor.clone());
                    currentCoor[1]--;
                } else if(lastDegree == 2) {
                    visitedCoor.add(currentCoor.clone());
                    currentCoor[0]--;
                } else if(lastDegree == 3) {
                    visitedCoor.add(currentCoor.clone());
                    currentCoor[1]++;
                }
            }
        }
    }

    private static boolean checkVisited(int[] currentCoor, int degree) {
        int[] tempCoor = currentCoor.clone();
        if(degree == 0) {
            tempCoor[0]++;
        } else if(degree == 1) {
            tempCoor[1]--;
        } else if(degree == 2) {
            tempCoor[0]--;
        } else if(degree == 3) {
            tempCoor[1]++;
        }

        for(int[] coor: visitedCoor) {
            if(coor[0] == tempCoor[0] && coor[1] == tempCoor[1]) {
                return true;
            }
        }
        return false;
    }

    private void hunt() {
        int[] currentCoorPass = {currentCoor[0], currentCoor[1] - 1};
        if(Game.aiToUserBoardHits[currentCoor[1] - 1][currentCoor[0]] == 0 && notQueued(currentCoorPass)) { // up
            coorToVisit.add(currentCoorPass);
        }

        currentCoorPass[0] = currentCoor[0];
        currentCoorPass[1] = currentCoor[1] + 1;
        if(Game.aiToUserBoardHits[currentCoor[1] + 1][currentCoor[0]] == 0 && notQueued(currentCoorPass)) { // down
            coorToVisit.add(currentCoorPass);
        }

        currentCoorPass[0] = currentCoor[0] + 1;
        currentCoorPass[1] = currentCoor[1];
        if(Game.aiToUserBoardHits[currentCoor[1]][currentCoor[0] + 1] == 0 && notQueued(currentCoorPass)) { // right
            coorToVisit.add(currentCoorPass);
        }

        currentCoorPass[0] = currentCoor[0];
        currentCoorPass[1] = currentCoor[1] - 1;
        if(Game.aiToUserBoardHits[currentCoor[1] - 1][currentCoor[0] - 1] == 0 && notQueued(currentCoorPass)) { // left
            coorToVisit.add(currentCoorPass);
        }
    }

    private static boolean notQueued(int[] coorCheck) {
        for(int[] coor: coorToVisit) {
            if (coor[0] == coorCheck[0] && coor[1] == coorCheck[1]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Places 5 ships randomly & returns integer values based on it.
     */
    public static Ship[] randomPlaceShip(JPanel panel) {
        Random rand = new Random(System.currentTimeMillis());

        boolean[][] marked = new boolean[10][10]; // places where other ships can't be placed

        Ship[] shipObjs = new Ship[5];

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

                // create ship object
                String append = switch (shipI) {
                    case 0: yield "Aircraft_Carrier";
                    case 1: yield "Battleship";
                    case 2: yield "Cruiser";
                    case 3: yield "Submarine";
                    case 4: yield "Destroyer";
                    default:
                        System.out.println("unauthorized ship size from AI");
                        yield "";
                };
                JLabel shipLabel = new JLabel(new ImageIcon("Images/Ships/" +
                        append + (isVertical ? "_Rotated" : "") + ".png"));
                shipObjs[shipI] = new Ship(panel, shipLabel, ships[shipI][0], ships[shipI][1], shipSize, !isVertical);
                // change JPanel soon

                // because submarine exists
                if (shipI == 2) {
                    shipSize++;
                }
                shipSize--;
            } while (isRetry);


        }

        return shipObjs;
    }

    /**
     * Places ships randomly & returns integer values based on it.
     * Weighted placing: destroyer is near the middle & the others are near the border.
     */
    public static Ship[] weightedPlaceShip(JPanel panel) {
        Random rand = new Random(System.currentTimeMillis());

        boolean[][] marked = new boolean[10][10]; // places where other ships can't be placed

        Ship[] shipObjs = new Ship[5];

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

                // generate near-border coordinates
                int rowCoord;
                int columnCoord;
                boolean isVertical;
                if (shipI != 4) {
                    rowCoord = rand.nextInt(10);
                    if ((rowCoord >= 1) && (rowCoord <= 8)) { // case 1: middle row (restrict to left/right side)
                        isVertical = false;
                        columnCoord = (rand.nextBoolean()) ? rand.nextInt(2) : rand.nextInt(2) + 8;
                    } else { // case 2: top/bottom row (no restrictions)
                        isVertical = true;
                        columnCoord = rand.nextInt(10);
                    }
                } else { // place destroyer in middle
                    isVertical = rand.nextBoolean();
                    rowCoord = rand.nextInt(6) + 4;
                    columnCoord = rand.nextInt(6) + 4;
                }

                ships[shipI][0] = rowCoord; // even number is row number
                ships[shipI][1] = columnCoord; // odd number is column number

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

                // create ship object
                String append = switch (shipI) {
                    case 0:
                        yield "Aircraft_Carrier";
                    case 1:
                        yield "Battleship";
                    case 2:
                        yield "Cruiser";
                    case 3:
                        yield "Submarine";
                    case 4:
                        yield "Destroyer";
                    default:
                        System.out.println("unauthorized ship size from AI");
                        yield "";
                };
                JLabel shipLabel = new JLabel(new ImageIcon("Images/Ships/" +
                        append + (isVertical ? "_Rotated" : "") + ".png"));
                shipObjs[shipI] = new Ship(panel, shipLabel, ships[shipI][0], ships[shipI][1], shipSize, !isVertical);

                // because submarine exists
                if (shipI == 2) {
                    shipSize++;
                }
                shipSize--;
            } while (isRetry);
        }

        return shipObjs;
    }
}
