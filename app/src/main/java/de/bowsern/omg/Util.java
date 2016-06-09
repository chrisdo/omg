package de.bowsern.omg;

import android.content.Context;

/**
 * Created by chris on 09.06.2016.
 */
public class Util {

    public static int getResourceId(Context context, String team){
        return context.getResources().getIdentifier(team.toLowerCase().replaceAll("ä","").replaceAll("ö","").replaceAll("ü",""),"drawable", context.getPackageName());
    }
}
