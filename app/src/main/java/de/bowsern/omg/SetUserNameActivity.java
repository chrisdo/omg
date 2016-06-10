package de.bowsern.omg;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SetUserNameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_user_name);
    }


    public void losGehts(View source){

        EditText text = (EditText) findViewById(R.id.fanname);

        if (text.length() > 0) {
            User.setUsername(text.getText().toString(), this);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        } else {
            text.setError("Bitte gib einen Fannamen ein");
        }

    }

}
