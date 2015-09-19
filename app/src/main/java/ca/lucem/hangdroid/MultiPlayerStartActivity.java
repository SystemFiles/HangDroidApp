package ca.lucem.hangdroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

/**
 * Full Screen Activity for entering a word to be guessed.
 */
public class MultiPlayerStartActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //set content view AFTER WINDOW_FEATURE sequence (to avoid crash)
        this.setContentView(R.layout.activity_multi_player_start);
    }

    /**
     * Starts the next part of the game
     *
     * @param view The current view
     */
    public void continueGame(View view) {
        EditText editText = (EditText) findViewById(R.id.editTextWord);
        String wordToSend = editText.getText().toString();

        // Create intent and have word included.
        Intent intent = new Intent(this,MultiPlayerGameActivity.class);
        intent.putExtra("WORD_INTRODUCED", wordToSend);

        startActivity(intent); // Start Multiplayer game.
    }
}
