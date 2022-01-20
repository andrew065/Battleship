import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class User {
    public File user;
    public String username;

    public boolean newUser = true;
    public int[] data = new int[4]; // #games - #sunk - #win - #losses

    public User(String username) {
        this.username = username;
        user = new File("Players/" + username + ".txt");

        if (user.exists()) {
            newUser = false;
        }

    }

    /**
     * This method loads the data from the user's file and stores it in an array
     * @throws FileNotFoundException - file could not exist
     */
    public void loadData() throws FileNotFoundException {
        if (!newUser) {
            Scanner scan = new Scanner(user);

            for (int i = 0; i < data.length; i++) {
                data[i] = Integer.parseInt(scan.nextLine());
            }
        }
    }

    /**
     * This method updates the array containing the user's data with the specified parameters
     * @param sunk - the number of ships sunk in a game
     * @param win - win/lose
     */
    public void updateData(int sunk, boolean win) {
        data[0]++; //games
        data[1] += sunk; //sunk

        if (win) data[2]++; //win
        else data[3]++; //loss

        newUser = false;
    }

    /**
     * This method saves the current users data by rewriting its file with new data
     * @throws FileNotFoundException - file could not exist
     */
    public void saveData() throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(user);
        for (int i : data) writer.println(i);
        writer.close();
    }
}
