package de.bowsern.omg;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;

public class BetTodaysMatches extends AppCompatActivity {

    Iterator<Match> matchesIterator;

    TextView home;
    TextView away;

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
            Match match = matchesIterator.next();
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

        showNextMatch();


    }

}
