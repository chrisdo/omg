package de.bowsern.omg;

import android.net.Uri;

/**
 * Created by felix on 08-Jun-16.
 */
public class Bet {

    public String username;
    public int homeScore;
    public int awayScore;
    public long timestamp;

    public Bet() {

    }

    public Bet(int homeScore, int awayScore, String username){
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        this.username = username;
        this.timestamp = System.currentTimeMillis();
    }


}
