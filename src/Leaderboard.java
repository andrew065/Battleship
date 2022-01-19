import java.util.*;
import java.io.*;

public class Leaderboard {

    //USE METHOD AFTER EACH GAME!!!!!!!!!!!!!!!!
    public static void UpdateLeaderboard(String username) throws FileNotFoundException {
        Scanner f = new Scanner("Players/" + username + ".txt");
        File file = new File("Players/" + username + ".txt");
        PrintWriter p = new PrintWriter(file);

        //update data - trolling
    }
}
