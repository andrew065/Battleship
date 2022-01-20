/**
 * @author Derrick Ha
 */
import java.util.*;
import java.io.*;

public class Leaderboard {

    //USE METHOD AFTER EACH GAME!!!!!!!!!!!!!!!!
    public static void UpdateLeaderboard(String username) throws FileNotFoundException {
        //Scanner f = new Scanner("Players/" + username + ".txt");
        File file = new File("Players/" + username + ".txt");
        PrintWriter p = new PrintWriter(file);
        User user = new User(username);
        user.loadData();

        int winner = Game.hasUserOrAIWon();
        boolean hasUserWon = false;
        int cnt = 0;
        if(winner == 1) hasUserWon = true;
        else {
            for(int i = 0; i < Game.isAiShipSunk.length; i++) {
                if(Game.isAiShipSunk[i]) cnt++;
            }
        }
        user.updateData(hasUserWon ? 5 : cnt, hasUserWon);
        user.saveData();
    }

    public static String[][] DisplayLeaderboard () {
        String[][] poo = new String[0][0];
        return poo;
    }
}
