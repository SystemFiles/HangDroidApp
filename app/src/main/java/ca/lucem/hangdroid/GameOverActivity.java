package ca.lucem.hangdroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class GameOverActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        TextView textView = (TextView) findViewById(R.id.textViewScore);
        textView.setText(textView.getText() + " " + SinglePlayerActivity.sessionScore);
    }

    /**
     * This method returns the user to the game activity
     *
     * @param view The current view.
     */
    public void dontSave(View view) {
        Intent singePlayerIntent = new Intent(this, SinglePlayerActivity.class);

        startActivity(singePlayerIntent); // Returns user to SinglePlayerActivity
    }

    /**
     * This method will save the score to high scores and returns the user to the start screen.
     *
     * @param view The current view.
     */
    public void saveScore(View view) {
        Intent intent = new Intent(this, StartScreen.class);

        // TODO: Save high scores to local file.

        startActivity(intent);
    }

}
