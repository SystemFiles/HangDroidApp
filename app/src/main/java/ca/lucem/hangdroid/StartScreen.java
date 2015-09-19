package ca.lucem.hangdroid;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class StartScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //set content view AFTER WINDOW_FEATURE sequence (to avoid crash)
        this.setContentView(R.layout.activity_start_screen);

        try { // Do font stuff after loading contentview
            // Font path
            String fontPath = "fonts/bebas.otf";
            // text view label
            TextView txtTitle = (TextView) findViewById(R.id.textViewTitle);
            // Loading Font Face
            Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
            // Applying font
            txtTitle.setTypeface(tf);
        } catch (RuntimeException ex) {
            Log.d("SEVRE", "ASSET NOT FOUND:" + ex.getMessage());
        }
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
