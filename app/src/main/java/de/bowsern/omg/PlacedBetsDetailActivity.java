package de.bowsern.omg;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class PlacedBetsDetailActivity extends AppCompatActivity {


    TextView betsView;

    TextView groupView;
    TextView awayView;
    TextView homeView;
    TextView scoreAwayView;
    TextView scoreHomeView;
    TextView dateView;
ImageView homeFlag;
    ImageView awayFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placed_bets_detail);

        String bets = getIntent().getExtras().getString("bets");
        String home = getIntent().getExtras().getString("home");
        String away =getIntent().getExtras().getString("away");
        String scoreHome = getIntent().getExtras().getString("scoreHome","");
        String scoreAway = getIntent().getExtras().getString("scoreAway","");
        String date =getIntent().getExtras().getString("date");
        String group = getIntent().getExtras().getString("group");
        String site = getIntent().getExtras().getString("site");

        betsView = (TextView)findViewById(R.id.placed_bets_detail_bets);

        groupView = (TextView)findViewById(R.id.placed_bets_detail_match_group);
        dateView = (TextView)findViewById(R.id.placed_bets_detail_match_date);
        homeView = (TextView)findViewById(R.id.placed_bets_detail_home_name);
        awayView = (TextView)findViewById(R.id.placed_bets_detail_away_name);
        homeFlag = (ImageView)findViewById(R.id.placed_bets_detail_home_flag);
        awayFlag = (ImageView)findViewById(R.id.placed_bets_detail_away_flag);

        scoreHomeView = (TextView)findViewById(R.id.placed_bets_detail_home_score);
        scoreAwayView = (TextView)findViewById(R.id.placed_bets_detail_away_score);
        betsView.setText(bets);

        groupView.setText(group);
        dateView.setText(date + " " + site);
        homeView.setText(home);
        awayView.setText(away);
        scoreHomeView.setText(scoreHome);
        scoreAwayView.setText(scoreAway);
        homeFlag.setImageResource(Util.getResourceId(getApplicationContext(),home));
        awayFlag.setImageResource(Util.getResourceId(getApplicationContext(),away));

        homeFlag.getLayoutParams().width = (int) getResources().getDimension(R.dimen.flag_width);
        homeFlag.getLayoutParams().height = (int) getResources().getDimension(R.dimen.flag_height);
        awayFlag.getLayoutParams().width = (int) getResources().getDimension(R.dimen.flag_width);
        awayFlag.getLayoutParams().height = (int) getResources().getDimension(R.dimen.flag_height);

    }
}
