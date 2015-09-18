package ca.lucem.hangdroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class StartScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
    }

    /**
     * Start Singleplayer game activity
     * @param view The view being checked.
     */
    public void openSinglePlayer(View view) {
        Intent myIntent = new Intent(this,SinglePlayerActivity.class);

        startActivity(myIntent); // Start new singleplayer game.
    }

    /**
     * Start Multiplayer game activity
     * @param view
     */
    public void openMultiplayer(View view) {

    }

    /**
     * Start Highscores game activity
     * @param view
     */
    public void openScores(View view) {
        Intent scoreIntent = new Intent(this, HighScoreActivity.class);

        startActivity(scoreIntent);
    }
}
