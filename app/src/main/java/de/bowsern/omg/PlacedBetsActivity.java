package de.bowsern.omg;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import de.bowsern.omg.de.bowsern.omg.adapter.ChampionAdapter;
import de.bowsern.omg.de.bowsern.omg.adapter.PlacedBetsListRowAdapter;

public class PlacedBetsActivity extends AppCompatActivity {
    private ListView listView;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    List<Match> matches = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placed_bets);


        try {
            MatchData.INSTANCE.waitForData();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        Iterator<Match> iter = MatchData.INSTANCE.getAllMatches().iterator();
        while(iter.hasNext()){
            Match m = iter.next();
            if(m.hasStarted()){
                matches.add(m);
            }
        }
        listView = (ListView) findViewById(R.id.placedBetsList);


       PlacedBetsListRowAdapter adapter = new PlacedBetsListRowAdapter(this, matches.toArray(new Match[]{}));
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(), PlacedBetsDetailActivity.class);
                intent.putExtra("home", matches.get(position).home);
                intent.putExtra("away", matches.get(position).away);
               if(matches.get(position).result != null){
                intent.putExtra("scoreHome", matches.get(position).result.home);
                intent.putExtra("scoreAway", matches.get(position).result.away);
               }
                intent.putExtra("date", format.format(matches.get(position).now().getTime()));
                intent.putExtra("group", matches.get(position).group);
                intent.putExtra("site", matches.get(position).site);
                StringBuilder sb = new StringBuilder();
                for(Bet b : matches.get(position).getPlacedBets()){
                    sb.append(b.username + " " + b.homeScore  + ":" + b.awayScore + "\n");
                }

                intent.putExtra("bets", sb.toString());
                startActivity(intent);
                finish();
            }
        });
    }
}
