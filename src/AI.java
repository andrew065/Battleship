import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * This AI class is used for returning the next shots to be fired and sets the AI ship positions.
 * @author Eric K., Eric C., Derrick
 */
public class AI {
    public static int[][] shootGrid = new int[10][10]; // grid to contain hits and misses and unknown squares
    static int[] currentCoor = {4, 4}; // int[] with current coordinates
    static ArrayList<int[]> visitedCoor = new ArrayList<>(); // arraylist of visited coordinates for intial generation of coordinates
    static ArrayList<int[]> coorToVisit = new ArrayList<>(); // list of coordinates surrounding a hit coordinate which will have to be hit next
    static int currentShootCoor = 0; // checks where in the process of the list of predefined coordinates it is
    static ArrayList<int[]> everyVisitedPoint = new ArrayList<>(); // list of every coordinate visited including predefined ones

    public static int difficulty; // difficulty value to see what difficulty the user picked

    /**
     * Adds 4 initial squares which it will hit which have a high chance of containing a ship
     */
    public static void addStartCoor() {
        int[][] startHits = {{0, 0}, {0, 9}, {9, 0}, {9, 9}}; // array of four corners
        for(int[] hit: startHits) { // for every coordinate
            coorToVisit.add(hit); // add to queue to visit
            everyVisitedPoint.add(hit); // add to list of points which were visited
        }
    }

    /**
     * Gets the coordinates for next shot depending on difficulty selected
     * @return int[] that contains the coordinates that will be used to fire
     */
    public static int[] getShot() {
        if (difficulty == 0) return easy(); // call easy() if user selected easy
        else if (difficulty == 1) return medium(); // call medium() if user selected medium
        else return hard(); // call hard() if user selected hard
    }

    /**
     * Easy difficulty AI that randomly fires
     * @return int[] that contains the coordinates that will be used to fire
     */
    public static int[] easy() {
        int[] hitCoor = new int[2]; // coordinate which will be returned
        int loopCount = 0;
        retry: do {
            if (loopCount > 10) {
                for (int r = 0; r < shootGrid.length; r++) {
                    for (int c = 0; c < shootGrid[r].length; c++) {
                        if (shootGrid[r][c] == 0) {
                            hitCoor[0] = r;
                            hitCoor[1] = c;
                            break retry;
                        }
                    }
                }
            } else {
                loopCount++;
            }

            hitCoor[0] = (int) (Math.random() * 10); // generate new rand x coordinate
            hitCoor[1] = (int) (Math.random() * 10); // generate new rand y coordinate
        } while (shootGrid[hitCoor[1]][hitCoor[0]] == 1 || shootGrid[hitCoor[1]][hitCoor[0]] == 2); // while coordinate is already hit;
        return hitCoor; // return coordinate
    }

    /**
     * Medium difficulty AI that randomly shoots until hitting ship, then zeros in on ship
     * @return int[] that contains the coordinates that will be used to fire
     */
    public static int[] medium() { // shoots strategically but does not zone in on ships when it hits
        if(shootGrid[currentCoor[1]][currentCoor[0]] == 2) { // if last shot was a hit
            hunt(); // hunt for ship
        }

        if(!coorToVisit.isEmpty()) { // if queue of coordinates from hunt has coordinates
            int[] nextShot = coorToVisit.get(0); // get next shot
            currentCoor = nextShot; // update current coordinate
            coorToVisit.remove(0); // remove current coordinate from list
            everyVisitedPoint.add(nextShot); // add to list of coordinates visited
            return nextShot; // return coordinate
        }

        currentCoor = easy(); // set current coordinate to return from easy()

        everyVisitedPoint.add(currentCoor); // add coordinate to list of visited coordinates
        return currentCoor; // returns current coordinate
    }

    /**
     * Hard difficulty AI that shoots strategically until it hits a ship, then it zeros in on the ship
     * @return
     */
    public static int[] hard() {
        if(shootGrid[currentCoor[1]][currentCoor[0]] == 2) { // if last shot was a hit
            hunt(); // hunt for ship
        }

        if(!coorToVisit.isEmpty()) { // if queue of coordinates from hunt has coordinates
            int[] nextShot = coorToVisit.get(0); // get next shot
            currentCoor = nextShot; // update current coordinate
            coorToVisit.remove(0); // remove current coordinate from list
            everyVisitedPoint.add(nextShot); // add to list of coordinates visited
            return nextShot; // return coordinate
        }

        do { // do while coordinate is already visited
            currentCoor = visitedCoor.get(currentShootCoor); // get the next coordinate from list
            currentShootCoor+=2; // increase counter by two to get the coordinate after the next, next time
        } while(shootGrid[currentCoor[1]][currentCoor[0]] == 1 || shootGrid[currentCoor[1]][currentCoor[0]] == 2 || !visitedBeforeAnySpace(currentCoor));

        everyVisitedPoint.add(currentCoor); // add current coordinate to list of visited coordinates
        return currentCoor; // return current coordinate
    }

    /**
     * Checks if a coordinate has been visited before
     * @param coorCheck int[] coordinate to check if it was visited before
     * @return true(was visited)/false(was not visited) value (boolean)
     */
    private static boolean visitedBeforeAnySpace(int[] coorCheck) {
        for(int[] coor: everyVisitedPoint) { // for every visited coordinate
            if (coor[0] == coorCheck[0] && coor[1] == coorCheck[1]) { // check if passed coordinate is the same
                return false; // if yes, return false
            }
        }
        return true; // if it is unique, return true
    }

    /**
     * Sets the original arraylist of coordinates to visit strategically
     * @implNote TO BE CALLED OUTSIDE OF AI CLASS
     */
    public static void setVisited() {
        int degree = 0; // set degree of direction when making the circle like pattern
        int lastDegree = 0; // set previous degree that worked
        int[] currentCoor = {4, 5}; // declare start coordinate

        while(true) { // while it is not broken
            if(visitedCoor.size() == 100) { // if it has covered every coordinate
                break; // break
            }
            if(degree == 0 && !checkVisited(currentCoor, degree)) { // if the next coordinate will be right, and it has not already been visited
                visitedCoor.add(currentCoor.clone());
                currentCoor[0]++;
                lastDegree = degree;
                degree++;
            } else if(degree == 1 && !checkVisited(currentCoor, degree)) { // if the next coordinate will be up, and it has not already been visited
                visitedCoor.add(currentCoor.clone());
                currentCoor[1]--;
                lastDegree = degree;
                degree++;
            } else if(degree == 2 && !checkVisited(currentCoor, degree)) { // if the next coordinate will be left, and it has not already been visited
                visitedCoor.add(currentCoor.clone());
                currentCoor[0]--;
                lastDegree = degree;
                degree++;
            } else if(degree == 3 && !checkVisited(currentCoor, degree)) { // if the next coordinate will be down, and it has not already been visited
                visitedCoor.add(currentCoor.clone());
                currentCoor[1]++;
                lastDegree = degree;
                degree = 0;
            } else { // if the coordinate matching to the degree has already been visited
                if(lastDegree == 0) { // use previous degree that worked (go right)
                    visitedCoor.add(currentCoor.clone());
                    currentCoor[0]++;
                } else if(lastDegree == 1) { // use previous degree that worked (go up)
                    visitedCoor.add(currentCoor.clone());
                    currentCoor[1]--;
                } else if(lastDegree == 2) { // use previous degree that worked (go left)
                    visitedCoor.add(currentCoor.clone());
                    currentCoor[0]--;
                } else if(lastDegree == 3) { // use previous degree that worked (go down)
                    visitedCoor.add(currentCoor.clone());
                    currentCoor[1]++;
                }
            }
        }
    }

    /**
     * Checks if coordinate was already added to arraylist for predefined coordinates
     * @param currentCoor int[] the current coordinate
     * @param degree int the degree that it has turned in the circle
     * @return boolean yes if added/no if not added
     */
    private static boolean checkVisited(int[] currentCoor, int degree) {
        int[] tempCoor = currentCoor.clone();
        if(degree == 0) { // if it goes right, change tempCoor accordingly
            tempCoor[0]++;
        } else if(degree == 1) { // if it goes up, change tempCoor accordingly
            tempCoor[1]--;
        } else if(degree == 2) { // if it goes left, change tempCoor accordingly
            tempCoor[0]--;
        } else if(degree == 3) { // if it goes down, change tempCoor accordingly
            tempCoor[1]++;
        }

        for(int[] coor: visitedCoor) { // for every visited coordinate
            if(coor[0] == tempCoor[0] && coor[1] == tempCoor[1]) { // if it matches
                return true; // return true as it is a duplicate
            }
        }
        return false; // return false as it is not a duplicate
    }

    /**
     * Zeros in on ship when it is hit and hits all the surrounding coordinates
     */
    private static void hunt() {
        if(currentCoor[1] - 1 >= 0) { // if coordinate up is within boundaries
            int[] currentCoorPass1 = {currentCoor[0], currentCoor[1] - 1};
            if(shootGrid[currentCoor[1] - 1][currentCoor[0]] == 0 && notQueued(currentCoorPass1) && visitedBeforeAnySpace(currentCoorPass1)) { // if new coordinate has not been visited yet, add to queue
                everyVisitedPoint.add(currentCoorPass1);
                coorToVisit.add(currentCoorPass1);
            }
        }

        if(currentCoor[1] + 1 <= 9) { // if coordinate down is within boundaries
            int[] currentCoorPass2 = {currentCoor[0], currentCoor[1] + 1};
            if (shootGrid[currentCoor[1] + 1][currentCoor[0]] == 0 && notQueued(currentCoorPass2) && visitedBeforeAnySpace(currentCoorPass2)) { // if new coordinate has not been visited yet, add to queue
                everyVisitedPoint.add(currentCoorPass2);
                coorToVisit.add(currentCoorPass2);
            }
        }

        if(currentCoor[0] + 1 <= 9) { // if coordinate right is within boundaries
            int[] currentCoorPass3 = {currentCoor[0] + 1, currentCoor[1]};
            if (shootGrid[currentCoor[1]][currentCoor[0] + 1] == 0 && notQueued(currentCoorPass3) && visitedBeforeAnySpace(currentCoorPass3)) { // if new coordinate has not been visited yet, add to queue
                everyVisitedPoint.add(currentCoorPass3);
                coorToVisit.add(currentCoorPass3);
            }
        }

        if(currentCoor[0] - 1 >= 0) { // if coordinate left is within boundaries
            int[] currentCoorPass4 = {currentCoor[0] - 1, currentCoor[1]};
            if (shootGrid[currentCoor[1]][currentCoor[0] - 1] == 0 && notQueued(currentCoorPass4) && visitedBeforeAnySpace(currentCoorPass4)) { // if new coordinate has not been visited yet, add to queue
                everyVisitedPoint.add(currentCoorPass4);
                coorToVisit.add(currentCoorPass4);
            }
        }
    }

    /**
     * Checks if a coordinate is already in the queue to be hit or not
     * @param coorCheck int[] coordinate to check
     * @return boolean yes if not in queue/no if in queue
     */
    private static boolean notQueued(int[] coorCheck) {
        for(int[] coor: coorToVisit) { // for every queued coordinate
            if (coor[0] == coorCheck[0] && coor[1] == coorCheck[1]) { // check if match
                return false; // returns false if it is a match
            }
        }
        return true; // returns true if no match
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
                    rowCoord = rand.nextInt(1) + 4;
                    columnCoord = rand.nextInt(1) + 4;
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
