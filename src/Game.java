
/**
 * Primary handler of game processes: ship placement, hit/miss/sunk markers, tallying final scores (win/loss).
 * X, Y coordinates start from the top left corner, not from the bottom left.
 * @author Derrick Ha, Eric Cao
 */
public class Game {
    /**
     * The total amount of hits from a player over the course of a game.
     */
    public static int userHits = 0;

    /**
     * 2D grid of all the spots the user has hit the AI.
     * Indicators range from 0 to 2: 0 -> vacant; 1 -> hit; 2 -> miss.
     */
    public static int[][] userToAiBoardHits = new int[10][10];

    /**
     * 2D grid of all the spots the AI has hit the user.
     * Indicators range from 0 to 2: 0 -> vacant; 1 -> hit; 2 -> miss.
     */
    public static int[][] aiToUserBoardHits = new int[10][10];

    /**
     * Indicators for AI ships that are sunk. "true" means the ship is sunk.
     * i = 0 -> the destroyer.
     * i = 1 -> the submarine.
     * i = 2 -> the cruiser.
     * i = 3 -> the battleship.
     * i = 4 -> the carrier.
     */
    public static boolean[] isAiShipSunk = new boolean[5];

    /**
     * Indicators for AI ships that are sunk. "true" -> the ship is sunk.
     * i = 0 -> the destroyer.
     * i = 1 -> the submarine.
     * i = 2 -> the cruiser.
     * i = 3 -> the battleship.
     * i = 4 -> the carrier.
     */
    public static boolean[] isUserShipSunk = new boolean[5];



    // AI ships -> (-1, -1) indicates that the ship has been hit, otherwise it is just the coordinates
    private static int[] aiDestroyer = new int[2*2];
    private static int[] aiSubmarine = new int[3*2];
    private static int[] aiCruiser = new int[3*2];
    private static int[] aiBattleship = new int[4*2];
    private static int[] aiCarrier = new int[5*2];

    // Player Ships -> (-1, -1) indicates that the ship has been hit, otherwise it is just the coordinates
    private static int[] uDestroyer = new int[2*2];
    private static int[] uSubmarine = new int[3*2];
    private static int[] uCruiser = new int[3*2];
    private static int[] uBattleship = new int[4*2];
    private static int[] uCarrier = new int[5*2];

    /**
     * HaHaYT Orz dijkstra moment
     * Will run after every turn to check if the user or AI has won.
     * @return if the user or AI has won using an int (1 = user, 2 = AI, -1 = neither)
     */
    public static int hasUserOrAIWon () {
        int numUser = 0;
        for(boolean sunk : isAiShipSunk) {
            if(sunk) numUser++;
        }
        if(numUser == 5) return 1;

        numUser = 0;
        for(boolean sunk : isUserShipSunk) {
            if(sunk) numUser++;
        }
        if(numUser == 5) return 2;

        return -1;
    }

    /**
     * Determines the length of a ship given its type in String.
     * Does NOT account for how the coordinates are formatted (as they are formatted in 1D arrays.)
     * @param shipType the type of ship in String format.
     * @return the length of the ship.
     *
     * NOTE: if you wish to use this method outside this class, you are welcome to change the keyword.
     */
    private static int findShipLength(String shipType) {
        switch (shipType) {
            case "Carrier":
                return 5;
            case "Battleship":
                return 4;
            case "Cruiser":
            case "Submarine":
                return 3;
            case "Destroyer":
                return 2;
            default:
                return -1;
        }


    }

    /**
     * Places a ship given from the user.
     * Method takes coordinates of a given ship & places it under the correct class variable.
     * @param shipType the type of ship in String format.
     * @param newShipCoords the array of coordinates the ship comprises.
     * @return false if the ship placement has failed &/or is invalid.
     */
    public static boolean userPlaceShip(String shipType, int[] newShipCoords) {
        // check if the ship is valid length (conditions to try again)
        int shipLength = findShipLength(shipType);
        if (newShipCoords.length != shipLength * 2) return false;

        // check if coordinate placement is correct (condition to try again)
        int startX = newShipCoords[0]; int startY = newShipCoords[1]; // the row/column of the start-coordinate
        int endX = newShipCoords[newShipCoords.length - 2]; int endY = newShipCoords[newShipCoords.length - 1]; // the row/column of the end-coordinate

        boolean isVerticallyPlaced; // true if it is vertical; false if it is horizontal
        if (startX == endX) isVerticallyPlaced = true;
        else if (startY == endY) isVerticallyPlaced = false;
        else return false;


        for (int i = 0; i < shipLength; i += 2) {
            int coordX = newShipCoords[i];
            int coordY = newShipCoords[i + 1];

            if (isVerticallyPlaced) {
                if (coordX != startX) return false;
            } else {
                if (coordY != startY) return false;
            }
        }

        // which ship is it? place coords given in correct ship coords
        switch(shipType) {
            // sets coordinates for carrier ship
            case "Carrier":
                for (int i = 0; i < newShipCoords.length; i++) {
                    uCarrier[i] = newShipCoords[i];
                }
                break;
            // sets coordinates for carrier ship
            case "Battleship":
                for (int i = 0; i < newShipCoords.length; i++) {
                    uBattleship[i] = newShipCoords[i];
                }
                break;
            // sets coordinates for carrier ship
            case "Cruiser":
                for (int i = 0; i < newShipCoords.length; i++) {
                    uCruiser[i] = newShipCoords[i];
                }
                break;
            case "Submarine":
                for (int i = 0; i < newShipCoords.length; i++) {
                    uSubmarine[i] = newShipCoords[i];
                }
                break;
            case "Destroyer":
                for (int i = 0; i < newShipCoords.length; i++) {
                    uDestroyer[i] = newShipCoords[i];
                }
                break;
        }

        MusicSound.playClick();

        return true; // ship has been placed
    }

    /**
     * Places a ship given from the AI.
     * Takes the starting coordinates & the ending coordinates of a ship & places it under the correct class variable.
     * @param shipType the type of ship in String format.
     * @param newShipCoords the array of coordinates the ship comprises.
     * @return false if the ship placement has failed &/or is invalid.
     */
    public static boolean aiPlaceShip(String shipType, int[] newShipCoords) {
        // check if the ship is valid length (conditions to try again)
        int shipLength = findShipLength(shipType);
        if (newShipCoords.length != shipLength * 2) return false;

        // check if coordinate placement is correct (condition to try again)
        int startX = newShipCoords[0]; int startY = newShipCoords[1]; // the row/column of the start-coordinate
        int endX = newShipCoords[newShipCoords.length - 2]; int endY = newShipCoords[newShipCoords.length - 1]; // the row/column of the end-coordinate

        boolean isVerticallyPlaced; // true if it is vertical; false if it is horizontal
        if (startX == endX) isVerticallyPlaced = true;
        else if (startY == endY) isVerticallyPlaced = false;
        else return false;


        for (int i = 0; i < shipLength; i += 2) {
            int coordX = newShipCoords[i];
            int coordY = newShipCoords[i + 1];

            if (isVerticallyPlaced) {
                if (coordX != startX) return false;
            } else {
                if (coordY != startY) return false;
            }
        }

        // which ship is it? place coords in the correct ship
        switch(shipType) {
            case "Carrier":
                for (int i = 0; i < newShipCoords.length; i++) {
                    aiCarrier[i] = newShipCoords[i];
                }
                break;
            case "Battleship":
                for (int i = 0; i < newShipCoords.length; i++) {
                    aiBattleship[i] = newShipCoords[i];
                }
                break;
            case "Cruiser":
                for (int i = 0; i < newShipCoords.length; i++) {
                    aiCruiser[i] = newShipCoords[i];
                }
                break;
            case "Submarine":
                for (int i = 0; i < newShipCoords.length; i++) {
                    aiSubmarine[i] = newShipCoords[i];
                }
                break;
            case "Destroyer":
                for (int i = 0; i < newShipCoords.length; i++) {
                    aiDestroyer[i] = newShipCoords[i];
                }
                break;
        }

        return true; // ship has been placed
    }

    /**
     * Records a marker from the user at a set X, Y coordinates.
     * If the marker hits an enemy ship then it will be recorded.
     * @param coordsX the x-coordinate of the marker.
     * @param coordsY the y-coordinate of the marker.
     *
     * NOTE: you are in charge of making sure the coordinates are within boundaries & checking if the spot is hit multiple times.
     */
    public static boolean userFire(int coordsX, int coordsY) {
        //make sure that it is not already either a hit or miss
        if (userToAiBoardHits[coordsX][coordsY] == 1 || userToAiBoardHits[coordsX][coordsY] == 2) {
            return false;
        }

        //check each ship to see if it was hit.
        boolean isHit = false; // attached boolean to derrick's code: it wasn't finished

        for(int i = 0; i < aiDestroyer.length; i += 2) {
            if(coordsX == aiDestroyer[i] && coordsY == aiDestroyer[i + 1]) {
                aiDestroyer[i] = -1;
                aiDestroyer[i + 1] = -1;
                isHit = true;

                manageIfSunk("AI Destroyer");
                break;
            }
        }
        for(int i = 0; i < aiSubmarine.length; i += 2) {
            if(coordsX == aiSubmarine[i] && coordsY == aiSubmarine[i + 1]) {
                aiSubmarine[i] = -1;
                aiSubmarine[i + 1] = -1;
                isHit = true;

                manageIfSunk("AI Submarine");
                break;
            }
        }
        for(int i = 0; i < aiCruiser.length; i += 2) {
            if(coordsX == aiCruiser[i] && coordsY == aiCruiser[i + 1]) {
                aiCruiser[i] = -1;
                aiCruiser[i + 1] = -1;
                isHit = true;

                manageIfSunk("AI Cruiser");
                break;
            }
        }
        for(int i = 0; i < aiBattleship.length; i += 2) {
            if(coordsX == aiBattleship[i] && coordsY == aiBattleship[i + 1]) {
                aiBattleship[i] = -1;
                aiBattleship[i + 1] = -1;
                isHit = true;

                manageIfSunk("AI Battleship");
                break;
            }
        }
        for(int i = 0; i < aiCarrier.length; i += 2) {
            if(coordsX == aiCarrier[i] && coordsY == aiCarrier[i + 1]) {
                aiCarrier[i] = -1;
                aiCarrier[i + 1] = -1;
                isHit = true;

                manageIfSunk("AI Carrier");
                break;
            }
        }

        // if it hasn't been hit then set the board location to 2
        if (!isHit) {
            MusicSound.playFire(1);
            userToAiBoardHits[coordsX][coordsY] = 2;
        } else {
            MusicSound.playFire(2);
            userToAiBoardHits[coordsX][coordsY] = 1;
        }

        userHits++;
        return isHit;
    }


    /**
     * Records a marker from the user at a set X, Y coordinates.
     * If the marker hits an enemy ship then it will be recorded.
     * @param coordsX the x-coordinate of the marker.
     * @param coordsY the y-coordinate of the marker.
     */
     public static boolean aiFire(int coordsX, int coordsY) {
         // check if spot is not already fired at
         if (aiToUserBoardHits[coordsX][coordsY] != 0) {
             return false;
         }

         //check each ship to see if it was hit.
        boolean isHit = false;

         for(int i = 0; i < uDestroyer.length; i += 2) {
            if(coordsX == uDestroyer[i] && coordsY == uDestroyer[i + 1]) {
                uDestroyer[i] = -1;
                uDestroyer[i + 1] = -1;
                isHit = true;

                manageIfSunk("User Destroyer");
                break;
            }
        }
        for(int i = 0; i < uSubmarine.length; i += 2) {
            if(coordsX == aiSubmarine[i] && coordsY == uSubmarine[i + 1]) {
                uSubmarine[i] = -1;
                uSubmarine[i + 1] = -1;
                isHit = true;

                manageIfSunk("User Submarine");
                break;
            }
        }
        for(int i = 0; i < uCruiser.length; i += 2) {
            if(coordsX == uCruiser[i] && coordsY == uCruiser[i + 1]) {
                uCruiser[i] = -1;
                uCruiser[i + 1] = -1;
                isHit = true;

                manageIfSunk("User Cruiser");
                break;
            }
        }
        for(int i = 0; i < uBattleship.length; i += 2) {
            if(coordsX == uBattleship[i] && coordsY == uBattleship[i + 1]) {
                uBattleship[i] = -1;
                uBattleship[i + 1] = -1;
                isHit = true;

                manageIfSunk("User Battleship");
                break;
            }
        }
        for(int i = 0; i < uCarrier.length; i += 2) {
            if(coordsX == uCarrier[i] && coordsY == uCarrier[i + 1]) {
                uCarrier[i] = -1;
                uCarrier[i + 1] = -1;
                isHit = true;

                manageIfSunk("User Carrier");
                break;
            }
        }

        // if it hasn't been hit then set the board location to 2
        if (!isHit) {
            MusicSound.playFire(1);
            aiToUserBoardHits[coordsX][coordsY] = 2;
        } else {
            MusicSound.playFire(2);
            aiToUserBoardHits[coordsX][coordsY] = 1;
        }

        return isHit;
     }

     /**
      * Modifies the correct boolean based on whether the ship is sunk.
      * @param shipName the name/type of the ship.
      *
      * NOTE: Relies on you to correctly spell the ship name.
      */
    private static void manageIfSunk(String shipName) {
        switch(shipName) {
            case "User Destroyer":
                if (isSunk(uDestroyer)) isUserShipSunk[0] = true; // if the destroyer is sunk, mark it as sunk in boolean
                break;
            case "User Submarine":
                if (isSunk(uSubmarine)) isUserShipSunk[1] = true;
                break;
            case "User Cruiser":
                if (isSunk(uCruiser)) isUserShipSunk[2] = true;
                break;
            case "User Battleship":
                if (isSunk(uBattleship)) isUserShipSunk[3] = true;
                break;
            case "User Carrier":
                if (isSunk(uCarrier)) isUserShipSunk[4] = true;
                break;
            case "AI Destroyer":
                if (isSunk(aiDestroyer)) isAiShipSunk[0] = true;
                break;
            case "AI Submarine":
                if (isSunk(aiSubmarine)) isAiShipSunk[1] = true;
                break;
            case "AI Cruiser":
                if (isSunk(aiCruiser)) isAiShipSunk[2] = true;
                break;
            case "AI Battleship":
                if (isSunk(aiBattleship)) isAiShipSunk[3] = true;
                break;
            case "AI Carrier":
                if (isSunk(aiBattleship)) isAiShipSunk[4] = true;
                break;
         }
     }

     /**
      * Determines if the ship object given is sunk.
      * @param shipCoords the coordinates of the ship.
      * @return true if the ship is sunk.
      */
    private static boolean isSunk(int[] shipCoords) {
        for (int coordinate : shipCoords) {
            if (coordinate != -1) {
                return false; // return false if any coordinate is not -1
            }
        }

        MusicSound.playSunk();
        return true;
    }

    /**
     * Resets all the objects to their default values.
     */
    public static void resetGame() {
        aiDestroyer = new int[2*2];
        aiSubmarine = new int[3*2];
        aiCruiser = new int[3*2];
        aiBattleship = new int[4*2];
        aiCarrier = new int[5*2];

        uDestroyer = new int[2*2];
        uSubmarine = new int[3*2];
        uCruiser = new int[3*2];
        uBattleship = new int[4*2];
        uCarrier = new int[5*2];

        isAiShipSunk = new boolean[5];
        isUserShipSunk = new boolean[5];

        userHits = 0;
    }
}
