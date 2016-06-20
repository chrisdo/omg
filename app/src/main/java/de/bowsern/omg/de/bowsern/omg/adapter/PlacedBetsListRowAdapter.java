package de.bowsern.omg.de.bowsern.omg.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import de.bowsern.omg.Match;
import de.bowsern.omg.R;
import de.bowsern.omg.Util;

/**
 * Created by chris on 09.06.2016.
 */
public class PlacedBetsListRowAdapter extends ArrayAdapter<Match> {

    private final Context context;
    private final Match[] values;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public PlacedBetsListRowAdapter(Context context, Match[] values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.placed_bets_list_row, parent, false);

        TextView title = (TextView) rowView.findViewById(R.id.bet_list_label_title);
        TextView date = (TextView) rowView.findViewById(R.id.bet_list_label_date);
       /* TextView scoreHome = (TextView) rowView.findViewById(R.id.bet_list_score_home);
        TextView scoreAway = (TextView) rowView.findViewById(R.id.bet_list_score_away);*/
        ImageView homeFlag = (ImageView) rowView.findViewById(R.id.bet_list_home_flag);
        ImageView awayFlag = (ImageView) rowView.findViewById(R.id.bet_list_away_flag);

        title.setText(values[position].group);
        date.setText(format.format(values[position].now().getTime()));
        // change the icon for Windows and iPhone

        homeFlag.getLayoutParams().width = (int)context.getResources().getDimension(R.dimen.flag_width);
        homeFlag.getLayoutParams().height = (int) context.getResources().getDimension(R.dimen.flag_height);
        awayFlag.getLayoutParams().width = (int)context.getResources().getDimension(R.dimen.flag_width);
        awayFlag.getLayoutParams().height = (int) context.getResources().getDimension(R.dimen.flag_height);
        homeFlag.setImageResource(Util.getResourceId(context, values[position].home));
        awayFlag.setImageResource(Util.getResourceId(context, values[position].away));
/*if(values[position].getResult() != null) {
    scoreHome.setText(values[position].getResult().home);
    scoreAway.setText(values[position].getResult().away);
}*/

        return rowView;
    }
}
