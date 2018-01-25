package fr.wcs.dangerousquiz.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import fr.wcs.dangerousquiz.Controllers.QuizController;
import fr.wcs.dangerousquiz.Models.Question;
import fr.wcs.dangerousquiz.Models.QuizModel;
import fr.wcs.dangerousquiz.R;

public class CreateQuizFragment extends Fragment {

    private EditText mEditTextQuizName, mEditTextQuizTheme, mEditTextAddQuestion, mEditTextAddAnswer1,
            mEditTextAddAnswer2, mEditTextAddAnswer3, mEditTextAddAnswer4;
    private Button mButtonCreateQuiz, mButtonConfirmQuestion;
    private String mQuizName, mQuizTheme;
    private QuizController mQuizController;
    private CardView mCardViewQuestion1, mCardViewQuestion2, mCardViewQuestion3, mCardViewQuestion4;
    private TextView mTextViewCard1, mTextViewCard2, mTextViewCard3, mTextViewCard4;
    private android.support.v7.app.AlertDialog mAlertDialog;
    private Spinner mSpinnerAnswerIndex;
    private int mAnswerIndex1, mAnswerIndex2, mAnswerIndex3, mAnswerIndex4;
    private Question mQuestion1 = new Question();
    private Question mQuestion2 = new Question();
    private Question mQuestion3 = new Question();
    private Question mQuestion4 = new Question();
    private List<Question> mQuestionList = new ArrayList<>();
    private ProgressDialog mProgressDialog;

    public CreateQuizFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_quiz, container, false);

        mQuizController = QuizController.getInstance();

        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage("Quiz creation ...");

        mEditTextQuizName = view.findViewById(R.id.editTextQuizName);
        mEditTextQuizTheme = view.findViewById(R.id.editTextQuizTheme);
        mButtonCreateQuiz = view.findViewById(R.id.buttonCreateQuiz);

        mCardViewQuestion1 = view.findViewById(R.id.cardViewQuestion1);
        mCardViewQuestion2 = view.findViewById(R.id.cardViewQuestion2);
        mCardViewQuestion3 = view.findViewById(R.id.cardViewQuestion3);
        mCardViewQuestion4 = view.findViewById(R.id.cardViewQuestion4);

        mTextViewCard1 = view.findViewById(R.id.textViewCard1);
        mTextViewCard2 = view.findViewById(R.id.textViewCard2);
        mTextViewCard3 = view.findViewById(R.id.textViewCard3);
        mTextViewCard4 = view.findViewById(R.id.textViewCard4);

        mCardViewQuestion1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getContext());
                View view = getLayoutInflater().inflate(R.layout.item_add_quiz_question, null);
                mEditTextAddQuestion = view.findViewById(R.id.editTextAddQuestion);
                mEditTextAddAnswer1 = view.findViewById(R.id.editTextAddAnswer1);
                mEditTextAddAnswer2 = view.findViewById(R.id.editTextAddAnswer2);
                mEditTextAddAnswer3 = view.findViewById(R.id.editTextAddAnswer3);
                mEditTextAddAnswer4 = view.findViewById(R.id.editTextAddAnswer4);
                mSpinnerAnswerIndex = view.findViewById(R.id.spinnerAnswerIndex);
                mButtonConfirmQuestion = view.findViewById(R.id.buttonConfirmQuestion);

                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                        R.array.answer_index, R.layout.item_spinner);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinnerAnswerIndex.setAdapter(adapter);
                mSpinnerAnswerIndex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        switch (i) {
                            case 0:
                                mAnswerIndex1 = 0;
                                break;
                            case 1:
                                mAnswerIndex1 = 1;
                                break;
                            case 2:
                                mAnswerIndex1 = 2;
                                break;
                            case 3:
                                mAnswerIndex1 = 3;
                                break;
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });

                builder.setView(view);
                mAlertDialog = builder.create();
                mAlertDialog.show();

                mButtonConfirmQuestion.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String question = mEditTextAddQuestion.getText().toString();
                        String answer1Content = mEditTextAddAnswer1.getText().toString();
                        String answer2Content = mEditTextAddAnswer2.getText().toString();
                        String answer3Content = mEditTextAddAnswer3.getText().toString();
                        String answer4Content = mEditTextAddAnswer4.getText().toString();

                        List<String> choiceList = new ArrayList<>();
                        choiceList.add(answer1Content);
                        choiceList.add(answer2Content);
                        choiceList.add(answer3Content);
                        choiceList.add(answer4Content);

                        mQuestion1.setQuestion(question);
                        mQuestion1.setChoiceList(choiceList);
                        mQuestion1.setAnswerIndex(mAnswerIndex1);

                        mTextViewCard1.setText("OK");

                        mQuestionList.add(mQuestion1);

                        mAlertDialog.dismiss();
                    }
                });
            }
        });

        mCardViewQuestion2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getContext());
                View view = getLayoutInflater().inflate(R.layout.item_add_quiz_question, null);
                mEditTextAddQuestion = view.findViewById(R.id.editTextAddQuestion);
                mEditTextAddAnswer1 = view.findViewById(R.id.editTextAddAnswer1);
                mEditTextAddAnswer2 = view.findViewById(R.id.editTextAddAnswer2);
                mEditTextAddAnswer3 = view.findViewById(R.id.editTextAddAnswer3);
                mEditTextAddAnswer4 = view.findViewById(R.id.editTextAddAnswer4);
                mSpinnerAnswerIndex = view.findViewById(R.id.spinnerAnswerIndex);
                mButtonConfirmQuestion = view.findViewById(R.id.buttonConfirmQuestion);

                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                        R.array.answer_index, R.layout.item_spinner);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinnerAnswerIndex.setAdapter(adapter);
                mSpinnerAnswerIndex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        switch (i) {
                            case 0:
                                mAnswerIndex2 = 0;
                                break;
                            case 1:
                                mAnswerIndex2 = 1;
                                break;
                            case 2:
                                mAnswerIndex2 = 2;
                                break;
                            case 3:
                                mAnswerIndex2 = 3;
                                break;
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });

                builder.setView(view);
                mAlertDialog = builder.create();
                mAlertDialog.show();

                mButtonConfirmQuestion.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String question = mEditTextAddQuestion.getText().toString();
                        String answer1Content = mEditTextAddAnswer1.getText().toString();
                        String answer2Content = mEditTextAddAnswer2.getText().toString();
                        String answer3Content = mEditTextAddAnswer3.getText().toString();
                        String answer4Content = mEditTextAddAnswer4.getText().toString();

                        List<String> choiceList = new ArrayList<>();
                        choiceList.add(answer1Content);
                        choiceList.add(answer2Content);
                        choiceList.add(answer3Content);
                        choiceList.add(answer4Content);

                        mQuestion2.setQuestion(question);
                        mQuestion2.setChoiceList(choiceList);
                        mQuestion2.setAnswerIndex(mAnswerIndex2);

                        mTextViewCard2.setText("OK");

                        mQuestionList.add(mQuestion2);

                        mAlertDialog.dismiss();
                    }
                });
            }
        });

        mCardViewQuestion3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getContext());
                View view = getLayoutInflater().inflate(R.layout.item_add_quiz_question, null);
                mEditTextAddQuestion = view.findViewById(R.id.editTextAddQuestion);
                mEditTextAddAnswer1 = view.findViewById(R.id.editTextAddAnswer1);
                mEditTextAddAnswer2 = view.findViewById(R.id.editTextAddAnswer2);
                mEditTextAddAnswer3 = view.findViewById(R.id.editTextAddAnswer3);
                mEditTextAddAnswer4 = view.findViewById(R.id.editTextAddAnswer4);
                mSpinnerAnswerIndex = view.findViewById(R.id.spinnerAnswerIndex);
                mButtonConfirmQuestion = view.findViewById(R.id.buttonConfirmQuestion);

                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                        R.array.answer_index, R.layout.item_spinner);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinnerAnswerIndex.setAdapter(adapter);
                mSpinnerAnswerIndex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        switch (i) {
                            case 0:
                                mAnswerIndex3 = 0;
                                break;
                            case 1:
                                mAnswerIndex3 = 1;
                                break;
                            case 2:
                                mAnswerIndex3 = 2;
                                break;
                            case 3:
                                mAnswerIndex3 = 3;
                                break;
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });

                builder.setView(view);
                mAlertDialog = builder.create();
                mAlertDialog.show();

                mButtonConfirmQuestion.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String question = mEditTextAddQuestion.getText().toString();
                        String answer1Content = mEditTextAddAnswer1.getText().toString();
                        String answer2Content = mEditTextAddAnswer2.getText().toString();
                        String answer3Content = mEditTextAddAnswer3.getText().toString();
                        String answer4Content = mEditTextAddAnswer4.getText().toString();

                        List<String> choiceList = new ArrayList<>();
                        choiceList.add(answer1Content);
                        choiceList.add(answer2Content);
                        choiceList.add(answer3Content);
                        choiceList.add(answer4Content);

                        mQuestion3.setQuestion(question);
                        mQuestion3.setChoiceList(choiceList);
                        mQuestion3.setAnswerIndex(mAnswerIndex3);

                        mTextViewCard3.setText("OK");

                        mQuestionList.add(mQuestion3);

                        mAlertDialog.dismiss();
                    }
                });
            }
        });

        mCardViewQuestion4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getContext());
                View view = getLayoutInflater().inflate(R.layout.item_add_quiz_question, null);
                mEditTextAddQuestion = view.findViewById(R.id.editTextAddQuestion);
                mEditTextAddAnswer1 = view.findViewById(R.id.editTextAddAnswer1);
                mEditTextAddAnswer2 = view.findViewById(R.id.editTextAddAnswer2);
                mEditTextAddAnswer3 = view.findViewById(R.id.editTextAddAnswer3);
                mEditTextAddAnswer4 = view.findViewById(R.id.editTextAddAnswer4);
                mSpinnerAnswerIndex = view.findViewById(R.id.spinnerAnswerIndex);
                mButtonConfirmQuestion = view.findViewById(R.id.buttonConfirmQuestion);

                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                        R.array.answer_index, R.layout.item_spinner);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinnerAnswerIndex.setAdapter(adapter);
                mSpinnerAnswerIndex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        switch (i) {
                            case 0:
                                mAnswerIndex4 = 0;
                                break;
                            case 1:
                                mAnswerIndex4 = 1;
                                break;
                            case 2:
                                mAnswerIndex4 = 2;
                                break;
                            case 3:
                                mAnswerIndex4 = 3;
                                break;
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });

                builder.setView(view);
                mAlertDialog = builder.create();
                mAlertDialog.show();

                mButtonConfirmQuestion.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String question = mEditTextAddQuestion.getText().toString();
                        String answer1Content = mEditTextAddAnswer1.getText().toString();
                        String answer2Content = mEditTextAddAnswer2.getText().toString();
                        String answer3Content = mEditTextAddAnswer3.getText().toString();
                        String answer4Content = mEditTextAddAnswer4.getText().toString();

                        List<String> choiceList = new ArrayList<>();
                        choiceList.add(answer1Content);
                        choiceList.add(answer2Content);
                        choiceList.add(answer3Content);
                        choiceList.add(answer4Content);

                        mQuestion4.setQuestion(question);
                        mQuestion4.setChoiceList(choiceList);
                        mQuestion4.setAnswerIndex(mAnswerIndex4);

                        mTextViewCard4.setText("OK");

                        mQuestionList.add(mQuestion4);

                        mAlertDialog.dismiss();
                    }
                });
            }
        });

        mButtonCreateQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressDialog.show();

                mQuizName = mEditTextQuizName.getText().toString();
                mQuizTheme = mEditTextQuizTheme.getText().toString();

                mQuizController.getQuizBuilder().name(mQuizName).theme(mQuizTheme).questionList(mQuestionList)
                .build();
                mQuizController.setQuizCreatedListener(new QuizController.QuizCreatedListener() {
                    @Override
                    public void onSuccess(boolean success, @Nullable QuizModel quizModel, @Nullable Exception e) {
                        mProgressDialog.dismiss();
                        Toast.makeText(getActivity(),"Quizz created!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(String error) {
                        mProgressDialog.dismiss();
                        Toast.makeText(getActivity(),"Quizz not created", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return view;
    }
}
