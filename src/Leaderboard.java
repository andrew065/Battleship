import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * @author Derrick Ha, Andrew Lian
 */

public class Leaderboard {
    public User user;
    public String[] leaderboard;
    public File file;

    public boolean hasLeaderboard = false;

    public Leaderboard(User user) throws FileNotFoundException {
        this.user = user;

        file = new File("Files/Leaderboard.txt");

        if (file.length() > 0) {
            hasLeaderboard = true;
            loadLeaderboard();
        }
    }

    //run after every game
    public void updateLeaderboard(boolean hasUserWon, boolean[] isAiShipSunk) throws FileNotFoundException {
        user.loadData();
        int cnt = 0;
        for(int i = 0; i < isAiShipSunk.length; i++) {
            if(isAiShipSunk[i]) cnt++;
        }
        user.updateData(hasUserWon ? 5 : cnt, hasUserWon);

        Scanner currentUserScanner = new Scanner("Players/" + user.username + ".txt");
        int thisUserWins = currentUserScanner.nextInt();

        ArrayList<String> leaders= new ArrayList<String>();
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
                File userFile = new File("Users/" + cur + ".txt");
                Scanner sc = new Scanner(userFile);
                int numGames = sc.nextInt();
                if (numGames < thisUserWins)
                    Collections.swap(leaders, leaders.indexOf(cur), leaders.indexOf(user.username));
            }
        }
        PrintWriter p = new PrintWriter("Files/Leaderboard.txt");
        for(String s : leaders) p.println(s);
    }

    public void loadLeaderboard () throws FileNotFoundException {
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
