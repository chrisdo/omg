package de.bowsern.omg;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class StartupActivity extends AppCompatActivity {

    public static final String PREFS_FILE = "omg_userprefs";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d("Startup", "onCreate");

        super.onCreate(savedInstanceState);

        Intent intent = null;

        if (User.getUsername(this) == null) {
            intent = new Intent(this, SetUserNameActivity.class);
        } else {
            intent = new Intent(this, MainActivity.class);
        }

        startActivity(intent);
        finish();

    }
}
