package de.bowsern.omg;

import android.net.Uri;

/**
 * Created by felix on 08-Jun-16.
 */
public class Bet {

    public String userID;
    public int homeScore;
    public int awayScore;
    public String userDisplayName;
    public String userEmail;
    public String fotoUrl;

    public Bet() {

    }

    public Bet(int homeScore, int awayScore, String userID, String fotoUrl, String userEmail, String userDisplayName){
        this.userID = userID;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        this.userDisplayName = userDisplayName;
        this.userEmail = userEmail;
        this.fotoUrl = fotoUrl;
    }


}
