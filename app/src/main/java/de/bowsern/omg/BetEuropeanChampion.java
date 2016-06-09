package de.bowsern.omg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import de.bowsern.omg.de.bowsern.omg.adapter.ChampionAdapter;

public class BetEuropeanChampion extends AppCompatActivity {

    private ListView listView;
    String[] values = new String[]{"Albanien", "Belgien", "Deutschland",
            "England", "Frankreich", "Island", "Italien", "Kroatien",
            "Nordirland", "Österreich", "Polen", "Portugal", "Irland", "Rumänien",
            "Russland", "Schweden", "Schweiz", "Slowakei", "Spanien", "Tschechien",
            "Türkei", "Ukraine", "Ungarn", "Wales"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bet_european_champion);
       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.listviewBetEuropeanChampion);


        ChampionAdapter adapter = new ChampionAdapter(this, values);
        listView.setAdapter(adapter);

        //TODO: set Selection when started or restarted
        //listView.setSelection(row);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String country = values[position];
                Intent intent = new Intent(BetEuropeanChampion.this, MainActivity.class);
                intent.putExtra("champion_tip", country);
                startActivity(intent);
                finish();
            }
        });

    }

}
