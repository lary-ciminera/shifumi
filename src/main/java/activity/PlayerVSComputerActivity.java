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
 * PlayerVSComputerActivity class -
 * Activity where the user can play against the computer
 * Created by Lary Ciminera on 27/03/2016.
 */
public class PlayerVSComputerActivity extends Activity {
    // Intent DAta
    // For Saving/Retrieving instance
    public static final String PLAYER_WINS = "playerWins";
    public static final String COMPUTER_WINS = "computerWins";
    public static final String NUMBERS_OF_PLAYS = "numbersOfPlays";
    public static final String LAST_PLAYER = "lastPlayer";
    public static final String LAST_COMPUTER = "lastComputer";
    public static final String COMPUTER_SAD = "computerSad";
    public static final String BOT_AI = "BOT_AI";
    // For start Activity
    public static final String BOT_DIFFICULTY = "Bot.Difficulty";

    int numbersOfPlays = 0;

    ImageView playerChoice;
    ImageView computerChoice;
    ImageView computerImage;
    TextView matchNumber;
    TextView playerScore;
    TextView computerScore;

    ArtificialIntelligences bot;

    PlayableMove.MoveType lastPlayer = null;
    PlayableMove.MoveType lastComputer = null;

    private int playerWins = 0;
    private int computerWins = 0;
    private boolean computerSad = false;

    public static void startActivity(Context context, ArtificialIntelligences.Difficulty d) {
        Intent startThis = new Intent(context, PlayerVSComputerActivity.class);
        startThis.putExtra(BOT_DIFFICULTY, d.ordinal());
        context.startActivity(startThis);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //save data for screen rotation
        outState.putInt(PLAYER_WINS, playerWins);
        outState.putInt(COMPUTER_WINS, computerWins);
        outState.putInt(NUMBERS_OF_PLAYS, numbersOfPlays);
        if (lastPlayer != null) {
            outState.putInt(
                    LAST_PLAYER, lastPlayer.ordinal());
        }
        if (lastComputer != null) {
            outState.putInt(
                    LAST_COMPUTER, lastComputer.ordinal());
        }
        outState.putBoolean(COMPUTER_SAD, computerSad);
        outState.putSerializable(BOT_AI, bot);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set layout
        try {
            playerWins = savedInstanceState.getInt(PLAYER_WINS);
            computerWins = savedInstanceState.getInt(COMPUTER_WINS);
            numbersOfPlays = savedInstanceState.getInt(NUMBERS_OF_PLAYS);
            //get last player move - default -1
            lastPlayer = PlayableMove.MoveType.indexToMoveType(savedInstanceState.getInt(LAST_PLAYER, -1));
            //get last computer move - default -1
            lastComputer = PlayableMove.MoveType.indexToMoveType(savedInstanceState.getInt(LAST_COMPUTER, -1));
            //get sadness of computer
            computerSad = savedInstanceState.getBoolean(COMPUTER_SAD, false);
            bot = (ArtificialIntelligences) savedInstanceState.get(BOT_AI);
        } catch (Exception e) {
            //oops
        }
        //InitAI
        if (bot == null) {
            Bundle extras = getIntent().getExtras();
            int difficultyIndex = 0;
            if (extras != null) {
                difficultyIndex = extras.getInt(BOT_DIFFICULTY, 0);
            }
            bot = new ArtificialIntelligences(ArtificialIntelligences.Difficulty.getFromIndex(difficultyIndex));
        }
        setContentView(R.layout.activity_player_vs_computer);
        //retrieve dynamic view component
        playerChoice = (ImageView) findViewById(R.id.other_choice_image);
        computerChoice = (ImageView) findViewById(R.id.computer_choice);
        computerImage = (ImageView) findViewById(R.id.computer_image);
        matchNumber = (TextView) findViewById(R.id.match_number);
        playerScore = (TextView) findViewById(R.id.player_score);
        computerScore = (TextView) findViewById(R.id.computer_score);
        // init scores
        matchNumber.setText(numbersOfPlays + " !");
        playerScore.setText(playerWins + " !");
        computerScore.setText(computerWins + " !");
        //if restored :
        if (lastPlayer != null) {
            setPlayerImage(lastPlayer);
        }
        if (lastComputer != null) {
            setComputerImage(lastComputer);
        }
        if (computerSad) {
            computerImage.setImageResource(R.drawable.computer_white_sad);
        }
    }

    // on player choice of move OncliclListener defined in xml
    public void play_choice(View view) {
        numbersOfPlays++;
        String tag;
        try {
            tag = (String) view.getTag();
        } catch (NullPointerException e) {
            tag = null;
        }
        matchNumber.setText(numbersOfPlays + " !");
        if (tag != null) {
            PlayableMove.MoveType userMoveType = PlayableMove.stringToType(tag);
            PlayableMove computerMove = bot.playComputerAsMove();
            // refresh ui
            setPlayerImage(userMoveType);
            setComputerImage(computerMove.moveType);

            PlayableMove player = new PlayableMove(userMoveType);
            PlayableMove.Outcome outcome = player.winsVs(computerMove);
            switch (outcome) {
                case tie:
                    computerImage.setImageResource(R.drawable.computer_white);
                    computerSad = false;
                    break;
                case win:
                    computerSad = true;
                    computerImage.setImageResource(R.drawable.computer_white_sad);
                    playerWins++;
                    playerScore.setText(playerWins + " !");
                    break;
                case lose:
                    computerImage.setImageResource(R.drawable.computer_white);
                    computerSad = false;
                    computerWins++;
                    computerScore.setText(computerWins + " !");
                    break;
            }
            // put user play in bot memory
            bot.saveResult(player.moveType);
            // for saving instance
            lastPlayer = player.moveType;
            lastComputer = computerMove.moveType;
        }
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
    private void setPlayerImage(PlayableMove.MoveType userMoveType) {
        switch (userMoveType) {
            case rock:
                playerChoice.setImageResource(R.drawable.rock);
                break;
            case paper:
                playerChoice.setImageResource(R.drawable.paper);
                break;
            case scissor:
                playerChoice.setImageResource(R.drawable.scissor);
                break;
        }
    }
}
