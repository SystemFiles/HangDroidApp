package ca.lucem.hangdroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.util.Scanner;

public class MultiPlayerGameActivity extends Activity {

    private String phrase;
    private String hint;
    private int failCounter;
    private int guessedLetters;
    public double sessionScore; // The score you got this session.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //set content view AFTER WINDOW_FEATURE sequence (to avoid crash)
        this.setContentView(R.layout.activity_multi_player_game);

        // Reset all counters/scores.
        failCounter = 0;
        sessionScore = 0;
        guessedLetters = 0;
        if (phrase == null) { // Only load the file once.
            phrase = getIntent().getStringExtra("WORD_INTRODUCED");
            Log.d("INFO","The phrase introduced is " + phrase);
            hint = getIntent().getStringExtra("HINT_INTRODUCED");
            Log.d("INFO", "The Hint Introduced is " + hint);
        }
        createTextViews(phrase);
    }

    /**
     * Creates text views based upon how many characters are in phrase.
     *
     * @param phrase Used to count the number of letters in phrase to determine how many '_' to put.
     */
    public void createTextViews(String phrase) {
        // The layout to be added to.
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.LayoutWord);

        for (int x = 0; x < phrase.length(); x++) {
            TextView newTextView = (TextView) getLayoutInflater().inflate(R.layout.text_view_dinamic, null);

            linearLayout.addView(newTextView); // Adds the new word view to the layout.
        }
    }

    /**
     * Opens the game over activity.
     */
    public void showGameOverScreen() {
        Intent gameOverIntent = new Intent(this, GameOverActivity.class);

        gameOverIntent.putExtra("SESSION_SCORE", sessionScore);
        gameOverIntent.putExtra("SESSION_WORD", phrase);
        gameOverIntent.putExtra("SENDER", "Multi");

        startActivity(gameOverIntent); // Opens game over screen.
    }

    /**
     *
     * When the CheckValue button is clicked this method will check
     * if the letter is part of the word then do something based on that.
     *
     * @param view The view being accessed.
     */
    public void checkLetter(View view) {
        EditText input = (EditText) findViewById(ca.lucem.hangdroid.R.id.editTextInput);
        String l = input.getText().toString();

        if (!l.isEmpty()) {
            Log.d("HANGDROIDLOG", "Letter(l) has been assigned the value: " + l);
            containsLetter(l);
        } else {
            Toast.makeText(this,"Please enter a letter", Toast.LENGTH_SHORT).show();
            Log.d("HANGDROIDLOG", "Letter(l) was not assigned a value..");
        }

    }

    /**
     * Sends the user a Toast containing the given hint.
     */
    public void getHint(View view) {
        Toast.makeText(this, "Hint: " + hint, Toast.LENGTH_LONG).show();
    }

    /**
     * Gets score from this game session.
     *
     * @return sessionScore The score from the current game session.
     */
    public double getSessionScore() {
        sessionScore = Math.round(guessedLetters * 15.042 / failCounter + 1); // Only if you win do you get a score.
        return sessionScore;
    }

    /**
     * This method returns a boolean value of true or false depending on whether
     * the given letter is contained in the given phrase
     *
     * @param letter The letter to be tested.
     */
    public void containsLetter(String letter) {
        boolean containsLetter = false;
        String testPhrase = phrase.toLowerCase();

        for (int x = 0; x < phrase.length(); x++) { // Look through phrase.
            if (testPhrase.charAt(x) == letter.charAt(0)) {
                containsLetter = true;

                // Contains letter: Display on correct guesses
                showLettersAtIndex(x,letter.charAt(0));

                if (guessedLetters == phrase.length()) {
                    AlertDialog alertDialog = new AlertDialog.Builder(MultiPlayerGameActivity.this).create();
                    alertDialog.setTitle("You WIN!!!!");
                    alertDialog.setMessage("You won! You saved Jeffery <3");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "You're Welcome :)",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    getSessionScore();
                                    dialog.dismiss();
                                    showGameOverScreen();
                                }
                            });
                    alertDialog.show();
                }
            }
        }

        if (!containsLetter) { // if phrase does not contain letter.
            showWrongLetters(letter.charAt(0));
            Log.d("HANGDROIDLOG", "Killing droid");
            if (failCounter >= 5) {
                AlertDialog alertDialog = new AlertDialog.Builder(MultiPlayerGameActivity.this).create();
                alertDialog.setTitle("Game Over!");
                alertDialog.setMessage("Poor Jeffery, the Android, died due to your stupidity! \n\nThe phrase: " + phrase);
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "I'm Sorry :(",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                getSessionScore();
                                dialog.dismiss();
                                showGameOverScreen();
                            }
                        });
                alertDialog.show();
            }
        }
    }

    /**
     * Displays a new body part on the image of the "Hang Droid"
     */
    private void showNewBodyPart() {
        ImageView imageView = (ImageView) findViewById(ca.lucem.hangdroid.R.id.hangDroid);

        switch (failCounter) {
            case 1:
                imageView.setImageResource(ca.lucem.hangdroid.R.drawable.hangdroid_1);
                break;
            case 2:
                imageView.setImageResource(ca.lucem.hangdroid.R.drawable.hangdroid_2);
                break;
            case 3:
                imageView.setImageResource(ca.lucem.hangdroid.R.drawable.hangdroid_3);
                break;
            case 4:
                imageView.setImageResource(ca.lucem.hangdroid.R.drawable.hangdroid_4);
                break;
            case 5:
                imageView.setImageResource(ca.lucem.hangdroid.R.drawable.hangdroid_5);
                break;
            case 6:
                imageView.setImageResource(ca.lucem.hangdroid.R.drawable.game_over);
                break;
            default:
                break;
        }
    }

    public void showWrongLetters(char letter) {
        TextView tv = (TextView) findViewById(ca.lucem.hangdroid.R.id.textViewWrong);
        EditText textInput = (EditText) findViewById(ca.lucem.hangdroid.R.id.editTextInput);

        if (!tv.getText().toString().contains(Character.toString(letter))) {
            tv.setText(tv.getText() + " " + letter);
            Log.d("HANGDROIDLOG", letter + " was added to the wrong guess list.");
            failCounter++; // Increment the fail counter.
            showNewBodyPart();

            textInput.setText(""); // Clear input box
        }
    }

    /**
     * Displays the letter in the specified position of the phrase.
     *
     * @param pos The position to put the charater.
     * @param letter The letter to be displayed.
     */
    public void showLettersAtIndex(int pos, char letter) {
        LinearLayout layout = (LinearLayout) findViewById(ca.lucem.hangdroid.R.id.LayoutWord);
        TextView textView = (TextView) layout.getChildAt(pos);
        EditText textInput = (EditText) findViewById(ca.lucem.hangdroid.R.id.editTextInput);

        guessedLetters++; // Increment how many letters were correctly guessed.
        textView.setText(Character.toString(letter));
        Log.d("HANGDROIDLOG", "Word entered into correct spot");


        textInput.setText("");
    }

}
