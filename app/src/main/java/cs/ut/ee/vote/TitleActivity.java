package cs.ut.ee.vote;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import java.io.IOException;

public class TitleActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String TAG = "TitleActivity";
    Context mContext;
    VideoView mVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        mContext = this.getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.title);

        Button englishButton = findViewById(R.id.en_bt);
        Button estonianButton = findViewById(R.id.et_bt);
        englishButton.setOnClickListener(this);
        estonianButton.setOnClickListener(this);

        mVideoView = findViewById(R.id.videoView);
    }
    @Override
    public void onClick(View view) {
        try {
            ScoreKeeper.init(mContext);
        }catch (IOException ex){
            Log.d(TAG, "Using 0s as scores");
            for(int i = 0; i<= 12; i++){
                ScoreKeeper.scores.put(i, 0);
            }
        }

        if(view.getId() == R.id.et_bt) {
            MLString.setCurrentLanguage(MLString.Language.Estonian);
        }
        else if(view.getId() == R.id.en_bt) {
            MLString.setCurrentLanguage(MLString.Language.English);
        }
        Intent intent = new Intent(mContext, AppDescActivity.class);
        startActivity(intent);
    }
    @Override
    public void onStop(){
        super.onStop();
        mVideoView.stopPlayback();
    }
    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG, "onStart called:");
        initializePlayer();
    }

    private void initializePlayer() {
        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.chalk);
        mVideoView.setVideoURI(uri);

        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
                mp.setVolume(0,0);
            }
        });
        mVideoView.start();
    }
}
