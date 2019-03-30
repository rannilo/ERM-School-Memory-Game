package cs.ut.ee.vote;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class AppDescActivity extends AppCompatActivity implements View.OnClickListener{
    private Context mContext;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_desc);
        mContext = this.getApplicationContext();
        TextView textView = findViewById(R.id.descText);
        Button startVoting = findViewById(R.id.startVoting);
        switch (MLString.getCurrentLanguage()){
            case English:
                textView.setText("Help us to choose the most beautiful Estonian school uniform – which picture do you like the most? \n \n To vote for a picture, click on it and then click on 'Vote'");
                startVoting.setText("Begin!");
                break;
            case Estonian:
                textView.setText("Aita valida näituse ilusaimat teost, milline pilt meeldib sulle kõige rohkem? \n \n Pildi poolt hääletamiseks vajuta pildile ning siis vajuta nupule 'Hääleta'");
                startVoting.setText("Alusta!");
                break;
        }

        TextView secretReset = findViewById(R.id.resetButton);
        final int[] count = {0};
        secretReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("AppDescActivity", "Click count = " + count[0]);
                if (count[0] > 20){
                    Log.e("AppDescActivity", "Tasked to reset scores using secret button");
                    try {
                        ScoreKeeper.resetScores(mContext);
                        Toast.makeText(AppDescActivity.this, "Skoorid manuaalselt seatud nulliks.", Toast.LENGTH_LONG).show();
                    }catch (IOException ex){

                    }
                }
                count[0]++;
            }
        });
        startVoting.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(mContext, VotingActivity.class);
        startActivity(intent);
    }
}
