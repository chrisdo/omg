package de.bowsern.omg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Iterator;

public class BetTodaysMatches extends AppCompatActivity {

    private final SimpleDateFormat df = new SimpleDateFormat("dd.MM / HH:00");

    Iterator<Match> matchesIterator;

    TextView home;
    TextView away;

    TextView groupText;
    TextView dateText;

    Match match;

    NumberPicker scoreHome;
    NumberPicker scoreAway;

    ImageView homeFlag;
    ImageView awayFlag;

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

        homeFlag = (ImageView) findViewById(R.id.home_flag);
        awayFlag = (ImageView) findViewById(R.id.away_flag);
homeFlag.getLayoutParams().width = (int) getResources().getDimension(R.dimen.flag_width);
        homeFlag.getLayoutParams().height = (int) getResources().getDimension(R.dimen.flag_height);
        awayFlag.getLayoutParams().width = (int) getResources().getDimension(R.dimen.flag_width);
       awayFlag.getLayoutParams().height = (int) getResources().getDimension(R.dimen.flag_height);


        scoreHome.setMinValue(0);
        scoreHome.setMaxValue(10);
        scoreAway.setMinValue(0);
        scoreAway.setMaxValue(10);

        groupText = (TextView) findViewById(R.id.match_group);
        dateText = (TextView) findViewById(R.id.match_date);

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

            Bet bet = match.getUserBet(this);
            if (bet != null) {
                scoreAway.setValue(bet.awayScore);
                scoreHome.setValue(bet.homeScore);
            } else {
                scoreHome.setValue(0);
                scoreAway.setValue(0);
            }

            int homeFlagId = Util.getResourceId(this,match.home);
            int awayFlagId = Util.getResourceId(this,match.away);

            homeFlag.setImageResource(homeFlagId);
            awayFlag.setImageResource(awayFlagId);

            groupText.setText("Gruppe " + match.group);
            dateText.setText(df.format(match.start.getTime()) + " / " + match.site);

        }

        if (!matchesIterator.hasNext()) {
            Button button =  ((Button) findViewById(R.id.placeBet));
            button.setText(R.string.fertig);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }


    public void placeBet(View view) {

        Bet bet = new Bet(scoreHome.getValue(), scoreAway.getValue(), User.getUsername(this));
        match.placeBet(bet);

        showNextMatch();


    }

}
