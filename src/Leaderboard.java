import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Handles the backend for the leaderboard page.
 * @author Derrick Ha, Andrew Lian
 */
public class Leaderboard {
    public User user;
    public String[] leaderboard;
    public File file;

    public boolean hasLeaderboard = false;

    /**
     * Creates a new leaderboard object.
     * @param user the user object.
     * @throws FileNotFoundException if the leaderboard text file is lost.
     */
    public Leaderboard(User user) throws FileNotFoundException {
        this.user = user;

        file = new File("Files/Leaderboard.txt");

        if (file.length() > 0) { // if there are no players then there's nothing to load
            hasLeaderboard = true;
            loadLeaderboard();
        }
    }

    /**
     * Updates the leaderboard when a user wins.
     * @throws FileNotFoundException if the leaderboard text file is lost.
     */
    public void updateLeaderboard() throws FileNotFoundException {
        int thisUserWins = user.data[0];

        ArrayList<String> leaders= new ArrayList<>();
        File file = new File("Files/Leaderboard.txt");
        Scanner LeaderboardScanner = new Scanner(file);
        int LeaderboardNum = 0;
        while(LeaderboardScanner.hasNext()) {
            String s = LeaderboardScanner.nextLine();
            leaders.add(s);
            LeaderboardNum++;
        }

        leaders.add(user.username);
        if(LeaderboardNum != 0) {
            for (int i = 0; i < leaders.size(); i++) {
                String cur = leaders.get(i);
                File userFile = new File("Files/Players/" + cur + ".txt");
                Scanner sc = new Scanner(userFile);
                int numGames = sc.nextInt();
                if (numGames < thisUserWins)
                    Collections.swap(leaders, leaders.indexOf(cur), leaders.indexOf(user.username));
            }
        }
        PrintWriter p = new PrintWriter("Files/Leaderboard.txt");
        for(String s : leaders) p.println(s);
        p.close();
    }

    /**
     * Loads the leaderboard's names.
     * @throws FileNotFoundException if the leaderboard file is lost.
     */
    public void loadLeaderboard() throws FileNotFoundException {
        if (hasLeaderboard) {
            Scanner count = new Scanner(file);
            Scanner scan = new Scanner(file);
            int numNames = 0;

            while (count.hasNext()) {
                numNames++;
                count.nextLine();
            }

            leaderboard = new String[numNames];
            for (int i = 0; i < numNames; i++) leaderboard[i] = scan.nextLine();
        }
    }
}
