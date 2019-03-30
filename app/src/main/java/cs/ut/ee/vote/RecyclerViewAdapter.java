package cs.ut.ee.vote;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import static cs.ut.ee.vote.ScoreKeeper.picNrToId;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";
    private ArrayList<Integer> mImages;
    private Context mContext;

    public RecyclerViewAdapter(ArrayList<Integer> mImages, Context mContext) {
        this.mImages = mImages;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_listitem, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        Log.d(TAG, "onBindViewHolder: called");
        Log.d(TAG, "Loading image " + mImages.get(i) + " into image");
        Glide.with(mContext).load(mImages.get(i)).into(viewHolder.image);

        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "clicked on: " + mImages.get(i));
                Intent intent = new Intent(mContext, SinglePictureActivity.class);
                intent.putExtra("pos", i);
                intent.putExtra("picture", mImages.get(i));
                mContext.startActivity(intent);
            }
        });

        try {
            viewHolder.scoreOfImage.setText(Integer.toString(ScoreKeeper.scores.get(picNrToId(mImages.get(i)))));
        }catch (NullPointerException ex){
            Log.e(TAG, "Not such value in scores: " + picNrToId(mImages.get(i)) + " Using 0 as default");
            ScoreKeeper.logScores();
            viewHolder.scoreOfImage.setText("0");
        }
    }
    @Override
    public int getItemCount(){
        return mImages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        RelativeLayout parentLayout;
        TextView scoreOfImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.small_image);
            parentLayout = itemView.findViewById(R.id.parent_layout);
            scoreOfImage = itemView.findViewById(R.id.score_of_image);
        }
    }
}
