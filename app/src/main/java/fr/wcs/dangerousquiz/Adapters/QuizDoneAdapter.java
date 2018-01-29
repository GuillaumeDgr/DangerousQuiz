package fr.wcs.dangerousquiz.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import fr.wcs.dangerousquiz.Models.QuizModel;
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
    }

    @Override
    public int getItemCount() {
        return mQuizModelList.size();
    }

    public class QuizViewHolder extends RecyclerView.ViewHolder {

        TextView mTextViewQuizName, mTextViewQuizTheme;

        public QuizViewHolder(View itemView) {
            super(itemView);

            mTextViewQuizName = itemView.findViewById(R.id.textViewQuizName);
            mTextViewQuizTheme = itemView.findViewById(R.id.textViewQuizTheme);
        }
    }
}
