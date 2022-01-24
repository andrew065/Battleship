import java.io.*;
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

//    public void updateLeaderboard() throws FileNotFoundException {
//        user.loadData();
//
//        int winner = Game.hasUserOrAIWon();
//        boolean hasUserWon = false;
//        int cnt = 0;
//        if(winner == 1) hasUserWon = true;
//        else {
//            for(int i = 0; i < Game.isAiShipSunk.length; i++) {
//                if(Game.isAiShipSunk[i]) cnt++;
//            }
//        }
//        user.updateData(hasUserWon ? 5 : cnt, hasUserWon);
//    }

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
