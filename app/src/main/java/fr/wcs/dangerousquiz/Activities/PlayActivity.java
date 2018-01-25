package fr.wcs.dangerousquiz.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fr.wcs.dangerousquiz.Controllers.QuizController;
import fr.wcs.dangerousquiz.Controllers.UserController;
import fr.wcs.dangerousquiz.Models.Question;
import fr.wcs.dangerousquiz.Models.QuizModel;
import fr.wcs.dangerousquiz.Models.UserModel;
import fr.wcs.dangerousquiz.R;

public class PlayActivity extends AppCompatActivity implements View.OnClickListener {

    private UserController mUserController;
    private QuizController mQuizController;
    private UserModel mUser;
    private TextView mQuestionTextView, mCardViewText1, mCardViewText2, mCardViewText3, mCardViewText4;
    private CardView mCardView1, mCardView2, mCardView3, mCardView4;
    private List<Question> mQuestionList = new ArrayList<>();
    private Question mCurrentQuestion = new Question();
    private int mNumberOfQuestions = 0, mScore, mPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        mUserController = UserController.getInstance();
        mQuizController = QuizController.getInstance();

        QuizModel quizModel = mQuizController.getSelectedQuiz();
        mQuestionList = quizModel.getQuestionList();

        mUser = mUserController.getUser();
        mScore = mUser.getScore();

        mNumberOfQuestions = mQuestionList.size();

        mQuestionTextView = findViewById(R.id.textViewGameQuestion);
        mCardView1 = findViewById(R.id.cardViewAnswer1);
        mCardViewText1 = findViewById(R.id.textViewCard1);
        mCardView2 = findViewById(R.id.cardViewAnswer2);
        mCardViewText2 = findViewById(R.id.textViewCard2);
        mCardView3 = findViewById(R.id.cardViewAnswer3);
        mCardViewText3 = findViewById(R.id.textViewCard3);
        mCardView4 = findViewById(R.id.cardViewAnswer4);
        mCardViewText4 = findViewById(R.id.textViewCard4);

        mCardView1.setOnClickListener(this);
        mCardView2.setOnClickListener(this);
        mCardView3.setOnClickListener(this);
        mCardView4.setOnClickListener(this);

        mCardView1.setTag(0);
        mCardView2.setTag(1);
        mCardView3.setTag(2);
        mCardView4.setTag(3);

        mPosition = 0;
        mCurrentQuestion = generateNewQuestion(mQuestionList);
        displayQuestion(mCurrentQuestion);
    }

    private Question generateNewQuestion(List<Question> questionList) {
        if (mPosition < questionList.size()) {
            Question newQuestion = questionList.get(mPosition);
            return newQuestion;
        } else {
            endGame();
        }
        return null;
    }

    private void displayQuestion(final Question question) {
        mQuestionTextView.setText(question.getQuestion());
        mCardViewText1.setText(question.getChoiceList().get(0));
        mCardViewText2.setText(question.getChoiceList().get(1));
        mCardViewText3.setText(question.getChoiceList().get(2));
        mCardViewText4.setText(question.getChoiceList().get(3));
    }

    @Override
    public void onClick(View v) {
        int responseIndex = (int) v.getTag();

        if (responseIndex == mCurrentQuestion.getAnswerIndex()) {
            // Good answer
            switch (responseIndex){
                case 0:
                    mCardView1.setCardBackgroundColor(getResources().getColor(R.color.green));
                    mCardViewText1.setBackgroundColor(getResources().getColor(R.color.green));
                    break;
                case 1:
                    mCardView2.setCardBackgroundColor(getResources().getColor(R.color.green));
                    mCardViewText2.setBackgroundColor(getResources().getColor(R.color.green));
                    break;
                case 2:
                    mCardView3.setCardBackgroundColor(getResources().getColor(R.color.green));
                    mCardViewText3.setBackgroundColor(getResources().getColor(R.color.green));
                    break;
                case 3:
                    mCardView4.setCardBackgroundColor(getResources().getColor(R.color.green));
                    mCardViewText4.setBackgroundColor(getResources().getColor(R.color.green));
                    break;
            }
            mScore++;
        } else {
            // Wrong answer
            switch (responseIndex){
                case 0:
                    mCardView1.setCardBackgroundColor(getResources().getColor(R.color.red));
                    mCardViewText1.setBackgroundColor(getResources().getColor(R.color.red));
                    break;
                case 1:
                    mCardView2.setCardBackgroundColor(getResources().getColor(R.color.red));
                    mCardViewText2.setBackgroundColor(getResources().getColor(R.color.red));
                    break;
                case 2:
                    mCardView3.setCardBackgroundColor(getResources().getColor(R.color.red));
                    mCardViewText3.setBackgroundColor(getResources().getColor(R.color.red));
                    break;
                case 3:
                    mCardView4.setCardBackgroundColor(getResources().getColor(R.color.red));
                    mCardViewText4.setBackgroundColor(getResources().getColor(R.color.red));
                    break;
            }
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mCardView1.setCardBackgroundColor(getResources().getColor(R.color.white));
                mCardViewText1.setBackgroundColor(getResources().getColor(R.color.white));
                mCardView2.setCardBackgroundColor(getResources().getColor(R.color.white));
                mCardViewText2.setBackgroundColor(getResources().getColor(R.color.white));
                mCardView3.setCardBackgroundColor(getResources().getColor(R.color.white));
                mCardViewText3.setBackgroundColor(getResources().getColor(R.color.white));
                mCardView4.setCardBackgroundColor(getResources().getColor(R.color.white));
                mCardViewText4.setBackgroundColor(getResources().getColor(R.color.white));

                if (mNumberOfQuestions - mPosition == 1) {
                    endGame();
                } else {
                    mPosition++;
                    mCurrentQuestion = generateNewQuestion(mQuestionList);
                    displayQuestion(mCurrentQuestion);
                }
            }
        }, 1000);
    }

    private void endGame() {
        mUser.setScore(mScore);
        AlertDialog.Builder builder = new AlertDialog.Builder(PlayActivity.this);
        builder.setTitle("Well done " + mUser.getFirstName() + "!")
                .setMessage("Your score is now " + mUser.getScore() + "!")
                .setPositiveButton("New Quiz!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mUserController.setScore(mScore);
                        Intent intent = new Intent(PlayActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                })
                .setCancelable(false)
                .create()
                .show();
    }
}
