package fr.wcs.dangerousquiz.Activities;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import fr.wcs.dangerousquiz.Controllers.UserController;
import fr.wcs.dangerousquiz.Models.QuestionModel;
//import fr.wcs.dangerousquiz.Models.QuestionBank;
import fr.wcs.dangerousquiz.R;

public class GameFragment extends Fragment  {

    private TextView mQuestionTextView, mCardViewText1, mCardViewText2, mCardViewText3, mCardViewText4;
    private CardView mCardView1, mCardView2, mCardView3, mCardView4;
    private QuestionModel mCurrentQuestionModel;
//    private QuestionBank mQuestionBank;
    private int mNumberOfQuestions, mScore;
    private UserController mUserController;

    public GameFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_game, container, false);

//        mUserController = UserController.getInstance();
//
//        mQuestionBank = this.generateQuestions();
//        mScore = 0;
//        mNumberOfQuestions = 4;
//
//        // Wire widgets
//        mQuestionTextView = view.findViewById(R.id.textViewGameQuestion);
//        mCardView1 = view.findViewById(R.id.cardViewAnswer1);
//        mCardViewText1 = view.findViewById(R.id.textViewCard1);
//        mCardView2 = view.findViewById(R.id.cardViewAnswer2);
//        mCardViewText2 = view.findViewById(R.id.textViewCard2);
//        mCardView3 = view.findViewById(R.id.cardViewAnswer3);
//        mCardViewText3 = view.findViewById(R.id.textViewCard3);
//        mCardView4 = view.findViewById(R.id.cardViewAnswer4);
//        mCardViewText4 = view.findViewById(R.id.textViewCard4);
//
//        mCardView1.setOnClickListener(this);
//        mCardView2.setOnClickListener(this);
//        mCardView3.setOnClickListener(this);
//        mCardView4.setOnClickListener(this);
//
//        mCardView1.setTag(0);
//        mCardView2.setTag(1);
//        mCardView3.setTag(2);
//        mCardView4.setTag(3);
//
//        mCurrentQuestionModel = mQuestionBank.getQuestion();
//        this.displayQuestion(mCurrentQuestionModel);

        return view;
    }

//    private QuestionBank generateQuestions() {
//        QuestionModel question1 = new QuestionModel("What is the name of the current french president?",
//                Arrays.asList("François Hollande", "Emmanuel Macron", "Jacques Chirac", "François Mitterand"),
//                1);
//        QuestionModel question2 = new QuestionModel("How many countries are there in the European Union?",
//                Arrays.asList("15", "24", "28", "32"),
//                2);
//        QuestionModel question3 = new QuestionModel("Who is the creator of the Android operating system?",
//                Arrays.asList("Andy Rubin", "Steve Wozniak", "Jake Wharton", "Paul Smith"),
//                0);
//        QuestionModel question4 = new QuestionModel("When did the first man land on the moon?",
//                Arrays.asList("1958", "1962", "1967", "1969"),
//                3);
//        QuestionModel question5 = new QuestionModel("What is the capital of Romania?",
//                Arrays.asList("Bucarest", "Warsaw", "Budapest", "Berlin"),
//                0);
//        QuestionModel question6 = new QuestionModel("Who did the Mona Lisa paint?",
//                Arrays.asList("Michelangelo", "Leonardo Da Vinci", "Raphael", "Carravagio"),
//                1);
//        QuestionModel question7 = new QuestionModel("In which city is the composer Frédéric Chopin buried?",
//                Arrays.asList("Strasbourg", "Warsaw", "Paris", "Moscow"),
//                2);
//        QuestionModel question8 = new QuestionModel("What is the country top-level domain of Belgium?",
//                Arrays.asList(".bg", ".bm", ".bl", ".be"),
//                3);
//        QuestionModel question9 = new QuestionModel("What is the house number of The Simpsons?",
//                Arrays.asList("42", "101", "666", "742"),
//                3);
//        QuestionModel question10 = new QuestionModel("Who is the best in the word?",
//                Arrays.asList("Guillaume", "Guillaume", "Guillaume", "Guillaume"),
//                2);
//
//        return new QuestionBank(Arrays.asList(question1, question2, question3, question4, question5,
//                question6, question7, question8, question9, question10));
//    }
//
//    private void displayQuestion(final QuestionModel question) {
//        mQuestionTextView.setText(question.getQuestion());
//        mCardViewText1.setText(question.getChoiceList().get(0));
//        mCardViewText2.setText(question.getChoiceList().get(1));
//        mCardViewText3.setText(question.getChoiceList().get(2));
//        mCardViewText4.setText(question.getChoiceList().get(3));
//    }
//
//    @Override
//    public void onClick(View v) {
//        int responseIndex = (int) v.getTag();
//
//        if (responseIndex == mCurrentQuestionModel.getAnswerIndex()) {
//            // Good answer
//            switch (responseIndex){
//                case 0:
//                    mCardView1.setCardBackgroundColor(getResources().getColor(R.color.green));
//                    mCardViewText1.setBackgroundColor(getResources().getColor(R.color.green));
//                    break;
//                case 1:
//                    mCardView2.setCardBackgroundColor(getResources().getColor(R.color.green));
//                    mCardViewText2.setBackgroundColor(getResources().getColor(R.color.green));
//                    break;
//                case 2:
//                    mCardView3.setCardBackgroundColor(getResources().getColor(R.color.green));
//                    mCardViewText3.setBackgroundColor(getResources().getColor(R.color.green));
//                    break;
//                case 3:
//                    mCardView4.setCardBackgroundColor(getResources().getColor(R.color.green));
//                    mCardViewText4.setBackgroundColor(getResources().getColor(R.color.green));
//                    break;
//            }
//            mScore++;
//        } else {
//            // Wrong answer
//            switch (responseIndex){
//                case 0:
//                    mCardView1.setCardBackgroundColor(getResources().getColor(R.color.red));
//                    mCardViewText1.setBackgroundColor(getResources().getColor(R.color.red));
//                    break;
//                case 1:
//                    mCardView2.setCardBackgroundColor(getResources().getColor(R.color.red));
//                    mCardViewText2.setBackgroundColor(getResources().getColor(R.color.red));
//                    break;
//                case 2:
//                    mCardView3.setCardBackgroundColor(getResources().getColor(R.color.red));
//                    mCardViewText3.setBackgroundColor(getResources().getColor(R.color.red));
//                    break;
//                case 3:
//                    mCardView4.setCardBackgroundColor(getResources().getColor(R.color.red));
//                    mCardViewText4.setBackgroundColor(getResources().getColor(R.color.red));
//                    break;
//            }
//        }
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mCardView1.setCardBackgroundColor(getResources().getColor(R.color.white));
//                mCardViewText1.setBackgroundColor(getResources().getColor(R.color.white));
//                mCardView2.setCardBackgroundColor(getResources().getColor(R.color.white));
//                mCardViewText2.setBackgroundColor(getResources().getColor(R.color.white));
//                mCardView3.setCardBackgroundColor(getResources().getColor(R.color.white));
//                mCardViewText3.setBackgroundColor(getResources().getColor(R.color.white));
//                mCardView4.setCardBackgroundColor(getResources().getColor(R.color.white));
//                mCardViewText4.setBackgroundColor(getResources().getColor(R.color.white));
//
//                // If this is the last question, ends the game.
//                // Else, display the next question.
//                if (--mNumberOfQuestions == 0) {
//                    // End the game
//                    endGame();
//                } else {
//                    mCurrentQuestionModel = mQuestionBank.getQuestion();
//                    displayQuestion(mCurrentQuestionModel);
//                }
//            }
//        }, 1000); // LENGTH_SHORT is usually 2 second long
//    }
//
//    private void endGame() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setTitle("Well done!")
//                .setMessage("Your score is " + mScore)
//                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                        mUserController.getUser().setScore(mScore);
//
//                        // End the activity
//                        Intent intent = new Intent(getContext(), MainActivity.class);
//                        startActivity(intent);
//                    }
//                })
//                .setCancelable(false)
//                .create()
//                .show();
//    }
}
