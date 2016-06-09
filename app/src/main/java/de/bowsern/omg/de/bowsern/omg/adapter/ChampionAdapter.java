package de.bowsern.omg.de.bowsern.omg.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import de.bowsern.omg.R;
import de.bowsern.omg.Util;

/**
 * Created by chris on 09.06.2016.
 */
public class ChampionAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final String[] values;

    public ChampionAdapter(Context context, String[] values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_layout_country, parent, false);

        TextView textView = (TextView) rowView.findViewById(R.id.country_label);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.flag_image);
        textView.setText(values[position]);
        // change the icon for Windows and iPhone
        String s = values[position];
        imageView.getLayoutParams().width = (int)context.getResources().getDimension(R.dimen.flag_height);
        imageView.getLayoutParams().height = (int) context.getResources().getDimension(R.dimen.flag_height);
        imageView.setImageResource(Util.getResourceId(context, values[position]));

        return rowView;
    }
}
