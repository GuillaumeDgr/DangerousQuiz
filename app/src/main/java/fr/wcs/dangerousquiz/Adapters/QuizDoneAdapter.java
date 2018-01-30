package fr.wcs.dangerousquiz.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import fr.wcs.dangerousquiz.Controllers.QuizController;
import fr.wcs.dangerousquiz.Models.QuizModel;
import fr.wcs.dangerousquiz.Models.UserModel;
import fr.wcs.dangerousquiz.R;

/**
 * Created by apprenti on 1/29/18.
 */

public class QuizDoneAdapter extends RecyclerView.Adapter<QuizDoneAdapter.QuizViewHolder> {

    private List<QuizModel> mQuizModelList;

    public QuizDoneAdapter(List<QuizModel> quizModelList) {
        mQuizModelList = quizModelList;
    }

    @Override
    public QuizDoneAdapter.QuizViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_quiz_done, parent, false);

        return new QuizViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(QuizDoneAdapter.QuizViewHolder holder, int position) {

        QuizModel quizModel = mQuizModelList.get(position);

        holder.mTextViewQuizName.setText(quizModel.getQuizName());
        holder.mTextViewQuizTheme.setText(quizModel.getQuizTheme());
        holder.mTextViewQuizDifficulty.setText(quizModel.getQuizLevel());

        UserModel creatorModel = QuizController.getInstance().getQuizCreator(quizModel.getCreatorId());
        Glide.with(holder.itemView.getContext())
                .load(creatorModel.getAvatar())
                .into(holder.mCircleImageViewCreator);
    }

    @Override
    public int getItemCount() {
        return mQuizModelList.size();
    }

    public class QuizViewHolder extends RecyclerView.ViewHolder {

        TextView mTextViewQuizName, mTextViewQuizTheme, mTextViewQuizDifficulty;
        CircleImageView mCircleImageViewCreator;

        public QuizViewHolder(View itemView) {
            super(itemView);

            mTextViewQuizName = itemView.findViewById(R.id.textViewQuizName);
            mTextViewQuizTheme = itemView.findViewById(R.id.textViewQuizTheme);
            mTextViewQuizDifficulty = itemView.findViewById(R.id.textViewQuizDifficulty);
            mCircleImageViewCreator = itemView.findViewById(R.id.circleImageViewCreator);
        }
    }
}
