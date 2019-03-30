package cs.ut.ee.vote;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class VotingActivity extends AppCompatActivity {
    private static final String TAG = "VotingActivity";

    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<Integer> mImageUrls = new ArrayList<>();
    private Integer recycleViewPos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_layout);

        Intent intent = getIntent();
        recycleViewPos = intent.getIntExtra("pos", 0);

        ImageButton closeButton = findViewById(R.id.closeButtonGallery);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(VotingActivity.this, TitleActivity.class);
                startActivity(intent1);
            }
        });

        TextView picDesc = findViewById(R.id.picDesc);
        TextView voteDesc = findViewById(R.id.voteDesc);
        picDesc.setText(MLString.getLanguageString("picDesc"));
        voteDesc.setText(MLString.getLanguageString("voteDesc"));

        initImageBitmaps();
    }

    private void initImageBitmaps(){
        Log.d(TAG, "initImageBitmaps: preparing bitmaps");

        mImageUrls.add(R.raw.pic1);
        mImageUrls.add(R.raw.pic2);
        mImageUrls.add(R.raw.pic3);
        mImageUrls.add(R.raw.pic4);
        mImageUrls.add(R.raw.pic5);
        mImageUrls.add(R.raw.pic6);
        mImageUrls.add(R.raw.pic7);
        mImageUrls.add(R.raw.pic8);
        mImageUrls.add(R.raw.pic9);
        mImageUrls.add(R.raw.pic10);
        mImageUrls.add(R.raw.pic11);
        mImageUrls.add(R.raw.pic12);

        //Collections.shuffle(mImageUrls);
        initRecyclerView();
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerview.");
        final RecyclerView recyclerView = findViewById(R.id.myRecyclerView);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(mImageUrls, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.getLayoutManager().scrollToPosition(recycleViewPos);
    }
}
