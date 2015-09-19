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

public class GameOverActivity extends Activity {

    private String cameFrom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //set content view AFTER WINDOW_FEATURE sequence (to avoid crash)
        this.setContentView(R.layout.activity_game_over);

        TextView textViewWord = (TextView) findViewById(R.id.textViewWord);
        TextView textView = (TextView) findViewById(R.id.textViewScore);
        TextView textViewGameOver = (TextView) findViewById(R.id.textViewGameOver);
        try { // Do font stuff after loading contentview
            // Font path
            String fontPath = "fonts/bebas.otf";
            // Loading Font Face
            Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
            // Applying font
            textViewWord.setTypeface(tf);
            textView.setTypeface(tf);
            textViewGameOver.setTypeface(tf);
        } catch (RuntimeException ex) {
            Log.d("SEVRE", "ASSET NOT FOUND:" + ex.getMessage());
        }

        double endScore = getIntent().getDoubleExtra("SESSION_SCORE",0.0);
        String endWord = getIntent().getStringExtra("SESSION_WORD");
        cameFrom = getIntent().getStringExtra("SENDER");

        textView.setText(textView.getText() + " " + endScore);
        textViewWord.setText("The word was  '" + endWord + "'");

        Log.d("INFO", "The Package this came from is: " + cameFrom);
    }

    /**
     * This method returns the user to the game activity
     *
     * @param view The current view.
     */
    public void dontSave(View view) {
        if (cameFrom.equalsIgnoreCase("single")) {
            Intent singePlayerIntent = new Intent(this, SinglePlayerActivity.class);

            startActivity(singePlayerIntent); // Returns user to SinglePlayerActivity
        } else {
            Intent multiPlayerIntent = new Intent(this, MultiPlayerStartActivity.class);

            startActivity(multiPlayerIntent); // Returns user to multiplayer game.
        }
    }

    /**
     * This method will save the score to high scores and returns the user to the start screen.
     *
     * @param view The current view.
     */
    public void saveScore(View view) {
        if(cameFrom.equalsIgnoreCase("single")) {
            Intent singlePlayerIntent = new Intent(this, StartScreen.class);

            // TODO: Save high scores to local file.
            startActivity(singlePlayerIntent);
        } else {
            Intent multiPlayerIntent = new Intent(this, MultiPlayerStartActivity.class);

            startActivity(multiPlayerIntent);
        }
    }

}
