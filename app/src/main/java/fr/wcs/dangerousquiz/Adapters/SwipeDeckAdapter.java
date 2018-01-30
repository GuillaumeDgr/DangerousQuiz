package fr.wcs.dangerousquiz.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import fr.wcs.dangerousquiz.Controllers.QuizController;
import fr.wcs.dangerousquiz.Controllers.UserController;
import fr.wcs.dangerousquiz.Models.QuizModel;
import fr.wcs.dangerousquiz.Models.UserModel;
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
        holder.mTextViewQuizDifficulty.setText(quizModel.getQuizLevel());
        holder.mTextViewQuizQuestionList.setText(quizModel.getQuestionList().toString());

        UserModel creatorModel = QuizController.getInstance().getQuizCreator(quizModel.getCreatorId());
        holder.mTextViewQuizCreatorName.setText(creatorModel.getFirstName());
        holder.mTextViewQuizCreatorScore.setText(String.valueOf(creatorModel.getScore()));
        Glide.with(convertView.getContext())
                .load(creatorModel.getAvatar())
                .into(holder.mCircleImageViewQuizCreatorAvatar);

        return convertView;
    }

    private class ViewHolder {

        private TextView mTextViewQuizName, mTextViewQuizTheme, mTextViewQuizDifficulty, mTextViewQuizQuestionList,
                mTextViewQuizCreatorName, mTextViewQuizCreatorScore;
        private CircleImageView mCircleImageViewQuizCreatorAvatar;

        public ViewHolder(final View view) {

            mTextViewQuizName = view.findViewById(R.id.textViewQuizName);
            mTextViewQuizTheme = view.findViewById(R.id.textViewQuizTheme);
            mTextViewQuizDifficulty = view.findViewById(R.id.textViewQuizDifficulty);
            mTextViewQuizQuestionList = view.findViewById(R.id.textViewQuizQuestionList);

            mTextViewQuizCreatorName = view.findViewById(R.id.textViewQuizCreatorName);
            mTextViewQuizCreatorScore = view.findViewById(R.id.textViewQuizCreatorScore);
            mCircleImageViewQuizCreatorAvatar = view.findViewById(R.id.circleImageViewQuizCreatorAvatar);
        }
    }
}