package activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.RelativeLayout;
import com.larypipot.ozo.app.R;
import content.ArtificialIntelligences;


public class StartActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        RelativeLayout playerVsComputerButton = (RelativeLayout) findViewById(R.id.player_vs_computer);
        RelativeLayout computerVsComputerButton = (RelativeLayout) findViewById(R.id.computer_vs_computer);

        computerVsComputerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ComputerActivityVSComputerActivity.startActivity(StartActivity.this);
            }
        });
        playerVsComputerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(StartActivity.this);
                builder.setMessage(R.string.ai_dialog_message)
                        .setTitle(R.string.ai_dialog_title)
                        .setPositiveButton(R.string.ai_easy, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                PlayerVSComputerActivity.startActivity(StartActivity.this, ArtificialIntelligences.Difficulty.dumb);

                            }
                        }).setNegativeButton(R.string.ai_normal, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        PlayerVSComputerActivity.startActivity(StartActivity.this, ArtificialIntelligences.Difficulty.elephant);

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
}
