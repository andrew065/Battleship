import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class User {
    public File user;

    public boolean newUser = true;
    public int[] data = new int[4];

    public User(String username) {
         user = new File("Players/" + username + ".txt");

        if (user.exists()) {
            newUser = false;
        }
    }

    public void loadData() throws FileNotFoundException {
        if (!newUser) {
            Scanner scan = new Scanner(user);

            for (int i = 0; i < data.length; i++) {
                data[i] = Integer.parseInt(scan.nextLine());
            }
        }
    }

    public void updateData(int sunk, boolean win) {
        data[0]++; //increment # of games by 1
        data[1] += sunk;

        if (win) data[2]++;
        else data[3]++;

        newUser = false;
    }

    public void saveData() throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(user);
        for (int i : data) writer.println(i);
        writer.close();
    }
}
