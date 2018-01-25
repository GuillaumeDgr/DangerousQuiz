package fr.wcs.dangerousquiz.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import fr.wcs.dangerousquiz.Models.UserModel;
import fr.wcs.dangerousquiz.R;

/**
 * Created by apprenti on 1/18/18.
 */

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder> {

    private List<UserModel> mUserModelList;

    public ScoreAdapter(List<UserModel> userModelList) {
        mUserModelList = userModelList;
    }

    @Override
    public ScoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_score, parent, false);

        return new ScoreViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ScoreViewHolder holder, int position) {

        UserModel userModel = mUserModelList.get(position);

        holder.mTextViewPlayerName.setText(userModel.getFirstName());
        holder.mTextViewPlayerScore.setText(String.valueOf(userModel.getScore()));
    }

    @Override
    public int getItemCount() {
        return mUserModelList.size();
    }


    public class ScoreViewHolder extends RecyclerView.ViewHolder {

        TextView mTextViewPlayerName, mTextViewPlayerScore;

        public ScoreViewHolder(View itemView) {
            super(itemView);

            mTextViewPlayerName = itemView.findViewById(R.id.textViewPlayerName);
            mTextViewPlayerScore = itemView.findViewById(R.id.textViewPlayerScore);
        }
    }
}
