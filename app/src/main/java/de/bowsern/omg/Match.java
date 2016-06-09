package de.bowsern.omg;

import android.provider.ContactsContract;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

/**
 * Created by felix on 07-Jun-16.
 */
public class Match {

    private final Calendar start;
    private int scoreHome;
    private int scoreAway;
    public final String home;
    public final String away;
    public final String site;
    public final String group;
    public final DatabaseReference ref;

    public Result result;

    public final Collection<Bet> placedBets = new ArrayList<Bet>();

    public Match(Calendar start, String group, String home, String away, String site, DatabaseReference ref){
        this.home = home;
        this.away = away;
        this.site = site;
        this.start = start;
        this.group = group;
        this.ref = ref;
    }

    public String toString(){

        return String.format("Group %s %s-%s", group, home,away);

    }

    public boolean hasStarted(){
        return Calendar.getInstance().after(start);
    }

    public boolean isToday() {
              return now().get(Calendar.YEAR) == start.get(Calendar.YEAR)
                &&  now().get(Calendar.DAY_OF_YEAR) == start.get(Calendar.DAY_OF_YEAR);
    }

    public boolean canBet(){
        return isToday() && !hasStarted();
    }

    public Calendar now() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH");

        try {
            Date date = sdf.parse("2016-06-11-14");
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            return c;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return Calendar.getInstance();

    }


    public void placeBet(Bet bet){
        DatabaseReference betRef = ref.child("bets").push();
        betRef.setValue(bet);
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Result getResult() {
        return result;
    }

    public Collection<Bet> getPlacedBets() {
        return placedBets;
    }
}
