package de.bowsern.omg;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    LinearLayout mMainContent;
    ProgressBar mMainProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainContent = (LinearLayout) findViewById(R.id.main_content);
        mMainProgress = (ProgressBar) findViewById(R.id.main_progress);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(getSupportActionBar().getTitle() + " - " + User.getUsername(this));

        AsyncTask<Void, Void, Void> task = new AsyncTask<Void,Void,Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    MatchData.INSTANCE.waitForData();
                } catch (InterruptedException e) {
                }
                return null;
            }

            @Override
            protected void onPreExecute() {
                showProgress(true);
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                showProgress(false);
            }
        };

        task.execute();

    }



    @Override

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.actionbarmenu, menu);
        return true;
    }


    public void showStandings(View view) {
        Intent intent = new Intent(this, Standings.class);
        startActivity(intent);
    }

    public void showMyBets(View view) {
        Intent intent = new Intent(this, BetTodaysMatches.class);
        startActivity(intent);
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);


            mMainContent.setVisibility(show ? View.GONE : View.VISIBLE);
            mMainContent.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mMainContent.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mMainProgress.setVisibility(show ? View.VISIBLE : View.GONE);
            mMainProgress.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mMainProgress.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mMainProgress.setVisibility(show ? View.VISIBLE : View.GONE);
            mMainContent.setVisibility(show ? View.GONE : View.VISIBLE);
        }

        if (show) {
            getSupportActionBar().hide();
        } else {
            getSupportActionBar().show();
        }

    }

}
