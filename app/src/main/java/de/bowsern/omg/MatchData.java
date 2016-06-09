package de.bowsern.omg;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.TimeZone;

import de.bowsern.omg.de.bowsern.omg.adapter.ChampionTip;

/**
 * Created by felix on 07-Jun-16.
 */
public final class MatchData implements ValueEventListener {

    public static final MatchData INSTANCE = new MatchData();

    public final static String KEY_DATE = "date";
    public final static String KEY_HOME = "home";
    public final static String KEY_AWAY = "away";
    public final static String KEY_SITE = "site";
    public final static String KEY_GROUP = "group";

    public final static String KEY_RESULT = "result";
    public final static String KEY_BETS = "bets";

    private final static SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy dd/MM HH");
    private final static TimeZone TZ = TimeZone.getTimeZone("Europe/Paris");

    private volatile Collection<Match> matches;

    private MatchData() {

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("groupStage");


        // Read from the database
        myRef.addValueEventListener(this);

    }


    // dates are formatted DD/MM HH
    private Calendar parseJsonDate(String date){

        Calendar result = null;

        try {
            Calendar c = Calendar.getInstance(TZ);
            c.setTime(FORMAT.parse("2016 " + date));
            result = c;

        } catch (ParseException e) {
            Log.w("jsonDate", "Wrong Date Format from Database", e);
        }

        return result;
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        // This method is called once with the initial value and again
        // whenever data at this location is updated.
        Iterable<DataSnapshot> values = dataSnapshot.getChildren();

        Collection<Match> matches = new ArrayList<Match>();

        for (DataSnapshot match : values) {

            Calendar calendar = parseJsonDate(match.child(KEY_DATE).getValue(String.class));

            if (calendar != null) {

                Match m = new Match(calendar,
                        match.child(KEY_GROUP).getValue(String.class),
                        match.child(KEY_HOME).getValue(String.class),
                        match.child(KEY_AWAY).getValue(String.class),
                        match.child(KEY_SITE).getValue(String.class),
                        match.getRef()
                );

                Result result = match.child(KEY_RESULT).getValue(Result.class);
                if (result != null) {
                    m.setResult(result);
                }

                for (DataSnapshot betshot : match.child(KEY_BETS).getChildren()){
                    Bet b = betshot.getValue(Bet.class);
                    m.getPlacedBets().add(b);
                }

                matches.add(m);
            }
        }

        Log.d("data", "onDataChange: something has updated!!");

        this.matches = matches;
    }

    @Override
    public void onCancelled(DatabaseError error) {
        // Failed to read value
        Log.w("Warn", "Failed to read value.", error.toException());
    }

    public boolean waitForData() throws InterruptedException {

        int slept = 0;

        while (matches == null){
            Thread.sleep(200);
            slept+=200;
            if (slept > 10000) {
                return false;
            }
        }

        return true;

    }

    public Collection<Match> getMatchesToBet(){
        if (matches == null) {
            return Collections.emptyList();
        }
        Collection<Match> result = new ArrayList<Match>();
        for (Match m : matches) {
            if (m.canBet()) {
                result.add(m);
            }
        }
        return result;
    }

    public Collection<Match> getAllMatches() {
        return new ArrayList<Match>(matches);
    }

    public void makeChampionTip(String champion_tip, Context context) {
        String username = User.getUsername(context);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("championTips");
        DatabaseReference child = myRef.push();

        ChampionTip tip = new ChampionTip();
        tip.champion = champion_tip;
        tip.user = User.getUsername(context);
        tip.timestamp = System.currentTimeMillis();

        child.setValue(tip);
    }
}
