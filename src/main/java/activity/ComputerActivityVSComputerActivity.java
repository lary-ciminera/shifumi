package activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.larypipot.ozo.app.R;
import content.ArtificialIntelligences;
import content.PlayableMove;

/**
 * ComputerVSComputerActivity class -
 * Activity where the user can play against the computer
 * Created by Lary Ciminera on 27/03/2016.
 */
public class ComputerActivityVSComputerActivity extends Activity {
    // Intent DAta
    // For Saving/Retrieving instance
    public static final String COMPUTER2_WINS = "computer2Wins";
    public static final String COMPUTER_WINS = "computerWins";
    public static final String NUMBERS_OF_PLAYS = "numbersOfPlays";
    public static final String LAST_COMPUTER2 = "lastComputer2";
    public static final String LAST_COMPUTER = "lastComputer";
    public static final String COMPUTER_SAD = "computerSad";
    public static final String COMPUTER2_SAD = "computer2Sad";

    int numbersOfPlays = 0;
    //Ui field
    ImageView computer2Choice;
    ImageView computerChoice;
    ImageView computerImage;
    ImageView computer2Image;
    TextView matchNumber;
    TextView computer2Score;
    TextView computerScore;
    TextView start;

    ArtificialIntelligences bot;
    ArtificialIntelligences bot2;

    PlayableMove.MoveType lastComputer2 = null;
    PlayableMove.MoveType lastComputer = null;

    private int computer2Wins = 0;
    private int computerWins = 0;
    private boolean computerSad = false;
    private boolean computer2Sad = false;

    public static void startActivity(Context context) {
        Intent startThis = new Intent(context, ComputerActivityVSComputerActivity.class);
        context.startActivity(startThis);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //save data for screen rotation
        outState.putInt(COMPUTER2_WINS, computer2Wins);
        outState.putInt(COMPUTER_WINS, computerWins);
        outState.putInt(NUMBERS_OF_PLAYS, numbersOfPlays);
        if (lastComputer2 != null) {
            outState.putInt(
                    LAST_COMPUTER2, lastComputer2.ordinal());
        }
        if (lastComputer != null) {
            outState.putInt(
                    LAST_COMPUTER, lastComputer.ordinal());
        }
        outState.putBoolean(COMPUTER_SAD, computerSad);
        outState.putBoolean(COMPUTER2_SAD, computer2Sad);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set layout
        try {
            computer2Wins = savedInstanceState.getInt(COMPUTER2_WINS);
            computerWins = savedInstanceState.getInt(COMPUTER_WINS);
            numbersOfPlays = savedInstanceState.getInt(NUMBERS_OF_PLAYS);
            //get last player move - default -1
            lastComputer2 = PlayableMove.MoveType.indexToMoveType(savedInstanceState.getInt(LAST_COMPUTER2, -1));
            //get last computer move - default -1
            lastComputer = PlayableMove.MoveType.indexToMoveType(savedInstanceState.getInt(LAST_COMPUTER, -1));
            //get sadness of computer
            computerSad = savedInstanceState.getBoolean(COMPUTER_SAD, false);
        } catch (Exception e) {
            //oops
        }
        //InitAI

            bot = new ArtificialIntelligences(ArtificialIntelligences.Difficulty.dumb);
            bot2 = new ArtificialIntelligences(ArtificialIntelligences.Difficulty.dumb);

        setContentView(R.layout.activity_computer_vs_computer);
        //retrieve dynamic view component
        computerChoice = (ImageView) findViewById(R.id.computer_choice);
        computerImage = (ImageView) findViewById(R.id.computer_image);
        computerScore = (TextView) findViewById(R.id.computer_score);

        start = (TextView) findViewById(R.id.start);

        computer2Choice = (ImageView) findViewById(R.id.other_choice_image);
        computer2Image = (ImageView) findViewById(R.id.computer2_image);
        computer2Score = (TextView) findViewById(R.id.computer2_score);

        matchNumber = (TextView) findViewById(R.id.match_number);
        // init scores
        matchNumber.setText(numbersOfPlays + " !");
        computer2Score.setText(computer2Wins + " !");
        computerScore.setText(computerWins + " !");
        //if restored :
        if (lastComputer2 != null) {
            setComputer2Image(lastComputer2);
        }
        if (lastComputer != null) {
            setComputerImage(lastComputer);
        }
        if (computerSad) {
            computerImage.setImageResource(R.drawable.computer_white_sad);
        }        if (computer2Sad) {
            computer2Image.setImageResource(R.drawable.computer_white_sad);
        }
    }

    // on user launch match
    public void launch_match(View view) {
        numbersOfPlays++;
        start.setVisibility(View.GONE);
        matchNumber.setText(numbersOfPlays + " !");


            PlayableMove computer2 = bot2.playComputerAsMove();
            PlayableMove computerMove = bot.playComputerAsMove();
            // refresh ui
            setComputer2Image(computer2.moveType);
            setComputerImage(computerMove.moveType);


            PlayableMove.Outcome outcome = computer2.winsVs(computerMove);
            switch (outcome) {
                case tie:
                    computerImage.setImageResource(R.drawable.computer_white);
                    computer2Image.setImageResource(R.drawable.computer);
                    computerSad = false;
                    computer2Sad = false;
                    break;
                case win:
                    computerSad = true;
                    computer2Sad = false;
                    computerImage.setImageResource(R.drawable.computer_white_sad);
                    computer2Image.setImageResource(R.drawable.computer);
                    computer2Wins++;
                    computer2Score.setText(computer2Wins + " !");
                    break;
                case lose:
                    computerImage.setImageResource(R.drawable.computer_white);
                    computer2Image.setImageResource(R.drawable.computer_sad);
                    computerSad = false;
                    computer2Sad = true;
                    computerWins++;
                    computerScore.setText(computerWins + " !");
                    break;
            }
            lastComputer2 = computer2.moveType;
            lastComputer = computerMove.moveType;

    }

    // set computer image depending on play
    private void setComputerImage(PlayableMove.MoveType computerMove) {
        switch (computerMove) {
            case rock:
                computerChoice.setImageResource(R.drawable.rock_white);
                break;
            case paper:
                computerChoice.setImageResource(R.drawable.paper_white);
                break;
            case scissor:
                computerChoice.setImageResource(R.drawable.scissor_white);
                break;
        }
    }

    // set player image depending on play
    private void setComputer2Image(PlayableMove.MoveType userMoveType) {
        switch (userMoveType) {
            case rock:
                computer2Choice.setImageResource(R.drawable.rock);
                break;
            case paper:
                computer2Choice.setImageResource(R.drawable.paper);
                break;
            case scissor:
                computer2Choice.setImageResource(R.drawable.scissor);
                break;
        }
    }
}
