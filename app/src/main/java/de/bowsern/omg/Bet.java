package de.bowsern.omg;

/**
 * Created by felix on 08-Jun-16.
 */
public class Bet {

    public String userID;
    public int homeScore;
    public int awayScore;
    public String userDisplayName;

    public Bet(int homeScore, int awayScore, String userID, String userDisplayName){
        this.userID = userID;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        this.userDisplayName = userDisplayName;
    }


}
