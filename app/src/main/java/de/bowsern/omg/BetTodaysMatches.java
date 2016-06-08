package de.bowsern.omg;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;

public class BetTodaysMatches extends AppCompatActivity {

    Iterator<Match> matchesIterator;

    TextView home;
    TextView away;

    Match match;

    NumberPicker scoreHome;
    NumberPicker scoreAway;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bet_todays_matches);

        Collection<Match> toBet = MatchData.INSTANCE.getMatchesToBet();

        matchesIterator = toBet.iterator();

        home = (TextView) findViewById(R.id.home_name);
        away = (TextView) findViewById(R.id.away_name);

        scoreHome = (NumberPicker) findViewById(R.id.score_home);
        scoreAway = (NumberPicker) findViewById(R.id.score_away);

        scoreHome.setMinValue(0);
        scoreHome.setMaxValue(10);
        scoreAway.setMinValue(0);
        scoreAway.setMaxValue(10);


        if (matchesIterator.hasNext()) {
            showNextMatch();
        } else {
            // TODO no matches to bet on...
        }
    }

    private void showNextMatch(){

        if (matchesIterator.hasNext()) {
            match = matchesIterator.next();
            home.setText(match.home);
            away.setText(match.away);
            scoreHome.setValue(0);
            scoreAway.setValue(0);
        }

        if (!matchesIterator.hasNext()) {
            ((Button) findViewById(R.id.placeBet)).setText(R.string.fertig);
        }

    }

    public void placeBet(View view) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        Bet bet = new Bet(scoreHome.getValue(), scoreAway.getValue(), user.getUid(), user.getDisplayName());
        match.placeBet(bet);

        showNextMatch();


    }

}
