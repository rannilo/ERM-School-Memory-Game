package cs.ut.ee.vote;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.request.RequestOptions;
import com.github.chrisbanes.photoview.PhotoView;

import java.io.IOException;

public class SinglePictureActivity extends AppCompatActivity {
    public static final String TAG = "SinglePictureActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_image);

        Intent intent = getIntent();
        final Integer recycleViewPos = intent.getIntExtra("pos", 0);
        final Integer pic = intent.getIntExtra("picture", R.drawable.ic_launcher_background);
        PhotoView image = findViewById(R.id.single_image);
        Glide.with(this).load(pic)
                .apply(new RequestOptions()
                .fitCenter()
                .format(DecodeFormat.PREFER_ARGB_8888))
                .into(image);


        Button back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(view.getContext(), VotingActivity.class);
                intent1.putExtra("pos", recycleViewPos);
                startActivity(intent1);
            }
        });
        Button vote = findViewById(R.id.vote);
        vote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pictureId = ScoreKeeper.picNrToId(pic);
                Log.d(TAG, "Voted for picture " + pictureId);
                int oldScore = 0;
                try{
                    oldScore = ScoreKeeper.scores.get(pictureId);
                } catch (NullPointerException ex){
                    Log.e(TAG, "Could not get score: " + pictureId);
                }
                int newScore = (oldScore + 1);
                ScoreKeeper.scores.put(pictureId, newScore);
                Log.d(TAG, "Set new score as " + newScore);
                try {
                    ScoreKeeper.writeFile(SinglePictureActivity.this);
                }catch (IOException ex){
                    Log.e(TAG, "Error writing to file");
                }

                switch (MLString.currentLanguage){
                    case English:
                        Toast.makeText(view.getContext(), "Vote cast! Thank you!", Toast.LENGTH_LONG).show();
                        break;
                    case Estonian:
                        Toast.makeText(view.getContext(), "Hääletatud! Aitäh!", Toast.LENGTH_LONG).show();
                }
                Intent intent1 = new Intent(view.getContext(), TitleActivity.class);
                intent1.putExtra("pos", 0);
                startActivity(intent1);
            }
        });

        ImageButton closeButton = findViewById(R.id.closeButtonSingleImage);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(SinglePictureActivity.this, TitleActivity.class);
                startActivity(intent1);
            }
        });


        Button voteButton = findViewById(R.id.vote);
        Button backButton = findViewById(R.id.back);
        switch (MLString.getCurrentLanguage()){
            case Estonian:
                voteButton.setText("Hääleta");
                backButton.setText("Tagasi");
                break;
            case English:
                voteButton.setText("Vote");
                backButton.setText("Back");
                break;
        }


    }
}
