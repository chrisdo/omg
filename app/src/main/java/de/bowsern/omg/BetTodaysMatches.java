package de.bowsern.omg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Collection;
import java.util.Iterator;

public class BetTodaysMatches extends AppCompatActivity {

    Iterator<Match> matchesIterator;

    TextView home;
    TextView away;

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
            int homeFlagId = getResourceId(match.home);
            int awayFlagId = getResourceId(match.away);

            homeFlag.setImageResource(homeFlagId);
            awayFlag.setImageResource(awayFlagId);
        }

        if (!matchesIterator.hasNext()) {
            Button button =  ((Button) findViewById(R.id.placeBet));
            button.setText(R.string.fertig);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //showProgress(false);
                    Intent intent = new Intent(BetTodaysMatches.this, Standings.class);
                    startActivity(intent);
                    finish();
                }
            });

        }

    }

    private int getResourceId(String team){
        return getResources().getIdentifier(team.toLowerCase().replaceAll("ä","").replaceAll("ö","").replaceAll("ü",""),"drawable", getApplicationContext().getPackageName());
    }

    public void placeBet(View view) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String photoUrl = user.getPhotoUrl() != null ? user.getPhotoUrl().toString() : null;
        String displayName = user.getDisplayName() != null ? user.getDisplayName() : null;

        Bet bet = new Bet(scoreHome.getValue(), scoreAway.getValue(), user.getUid(), photoUrl, user.getEmail(), displayName);
        match.placeBet(bet);

        showNextMatch();


    }

}
