package de.bowsern.omg;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Standings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standings);

        Map<String, Score> uid2Score = new HashMap<String, Score>();

        for (Match m : MatchData.INSTANCE.getAllMatches()) {

            if (m.getResult() != null) {

                Map<String, Bet> uid2LastBet = new HashMap<String, Bet>();

                for (Bet b : m.getPlacedBets()) {
                    uid2LastBet.put(b.username, b);
                }

                for (Bet bet : uid2LastBet.values()) {

                    Score score = uid2Score.get(bet.username);
                    if (score == null) {
                        score = new Score(bet.username);
                        uid2Score.put(bet.username, score);
                    }

                    score.points += getPoints(bet, m.getResult());
                }
            }

        }

        ArrayList<Score> scores = new ArrayList<Score>(uid2Score.values());
        Collections.sort(scores);
        Collections.reverse(scores);



        ListView listView = (ListView) findViewById(R.id.standigsList);
        StandingsAdapter adapter = new StandingsAdapter(this, android.R.layout.simple_list_item_1, scores);
        listView.setAdapter(adapter);


    }


    public int getPoints(Bet bet, Result result) {

        int points = 0;

        // one point for correct winner or draw

        // home loses
        if (result.home < result.away) {

            if (bet.homeScore < bet.awayScore) {
                points += 1;
            }

            // draw
        } else if (result.home == result.away ) {
            if (bet.homeScore == bet.awayScore) {
                points += 1;
            }

            // home wins
        } else {
            if (bet.homeScore > bet.awayScore) {
                points += 1;
            }
        }

        // correct result is two extra points
        if (bet.homeScore == result.home && bet.awayScore == result.away) {
            points += 2;
        }

        return points;

    }



    public class StandingsAdapter extends ArrayAdapter<Score> {

        private final Context context;
        private final List<Score> values;

        public StandingsAdapter(Context context, int resource, List<Score> objects) {
            super(context, resource, objects);
            this.context = context;
            this.values = objects;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView tv = (TextView) super.getView(position, convertView, parent);
            Score score = values.get(position);
            tv.setText(position + " " + score.userLabel + " " + score.points);
            return tv;

        }

    }

}
