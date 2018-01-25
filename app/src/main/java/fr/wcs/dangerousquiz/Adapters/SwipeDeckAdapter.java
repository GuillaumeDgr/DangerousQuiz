package fr.wcs.dangerousquiz.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

import fr.wcs.dangerousquiz.Models.QuizModel;
import fr.wcs.dangerousquiz.R;

/**
 * Created by apprenti on 11/23/17.
 */

public class SwipeDeckAdapter extends BaseAdapter {

    private List<QuizModel> mQuizModelList;
    private Context mContext;

    public SwipeDeckAdapter(List<QuizModel> mQuizModelList, Context context) {
        this.mQuizModelList = mQuizModelList;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mQuizModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return mQuizModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_card_quiz, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        final QuizModel quizModel = mQuizModelList.get(position);

        holder.mTextViewQuizName.setText(quizModel.getQuizName());
        holder.mTextViewQuizTheme.setText(quizModel.getQuizTheme());
        holder.mTextViewQuizQuestionList.setText(quizModel.getQuestionList().toString());
        holder.mTextViewQuizCreatorId.setText(quizModel.getCreatorId());
        holder.mTextViewQuizId.setText(quizModel.getQuizId());

        return convertView;
    }

    private class ViewHolder {

        private TextView mTextViewQuizName, mTextViewQuizTheme, mTextViewQuizQuestionList, mTextViewQuizCreatorId,
                mTextViewQuizId;

        public ViewHolder(final View view) {

            mTextViewQuizName = view.findViewById(R.id.textViewQuizName);
            mTextViewQuizTheme = view.findViewById(R.id.textViewQuizTheme);
            mTextViewQuizQuestionList = view.findViewById(R.id.textViewQuizQuestionList);
            mTextViewQuizCreatorId = view.findViewById(R.id.textViewQuizCreatorId);
            mTextViewQuizId = view.findViewById(R.id.textViewQuizId);
        }
    }
}