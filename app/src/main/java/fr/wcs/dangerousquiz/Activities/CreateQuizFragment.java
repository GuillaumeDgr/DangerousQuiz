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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fr.wcs.dangerousquiz.Controllers.QuizController;
import fr.wcs.dangerousquiz.Models.CategoryEnum;
import fr.wcs.dangerousquiz.Models.LevelEnum;
import fr.wcs.dangerousquiz.Models.QuestionModel;
import fr.wcs.dangerousquiz.Models.QuizModel;
import fr.wcs.dangerousquiz.R;

public class CreateQuizFragment extends Fragment {

    private EditText mEditTextQuizName, mEditTextAddQuestion, mEditTextAddAnswer1,
            mEditTextAddAnswer2, mEditTextAddAnswer3, mEditTextAddAnswer4;
    private Spinner mSpinnerQuizTheme, mSpinnerQuizDifficulty,mSpinnerAnswerIndex;
    private ImageView mImageViewCard1, mImageViewCard2, mImageViewCard3, mImageViewCard4;
    private Button mButtonCreateQuiz, mButtonConfirmQuestion;
    private String mQuizName, mQuizTheme, mQuizDifficulty;
    private QuizController mQuizController;
    private CardView mCardViewQuestion1, mCardViewQuestion2, mCardViewQuestion3, mCardViewQuestion4;
    private TextView mTextViewCard1, mTextViewCard2, mTextViewCard3, mTextViewCard4;
    private android.support.v7.app.AlertDialog mAlertDialog;
    private int mAnswerIndex1, mAnswerIndex2, mAnswerIndex3, mAnswerIndex4;
    private QuestionModel mQuestionModel1 = new QuestionModel();
    private QuestionModel mQuestionModel2 = new QuestionModel();
    private QuestionModel mQuestionModel3 = new QuestionModel();
    private QuestionModel mQuestionModel4 = new QuestionModel();
    private List<QuestionModel> mQuestionModelList = new ArrayList<>();
    private ProgressDialog mProgressDialog;

    public CreateQuizFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_quiz, container, false);

        mQuizController = QuizController.getInstance();

        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage(getString(R.string.quiz_creation));

        mEditTextQuizName = view.findViewById(R.id.editTextQuizName);
        mSpinnerQuizTheme = view.findViewById(R.id.spinnerQuizTheme);
        mSpinnerQuizDifficulty = view.findViewById(R.id.spinnerQuizDifficulty);
        mButtonCreateQuiz = view.findViewById(R.id.buttonCreateQuiz);

        mCardViewQuestion1 = view.findViewById(R.id.cardViewQuestion1);
        mCardViewQuestion2 = view.findViewById(R.id.cardViewQuestion2);
        mCardViewQuestion3 = view.findViewById(R.id.cardViewQuestion3);
        mCardViewQuestion4 = view.findViewById(R.id.cardViewQuestion4);

        mTextViewCard1 = view.findViewById(R.id.textViewCard1);
        mTextViewCard2 = view.findViewById(R.id.textViewCard2);
        mTextViewCard3 = view.findViewById(R.id.textViewCard3);
        mTextViewCard4 = view.findViewById(R.id.textViewCard4);

        mImageViewCard1 = view.findViewById(R.id.imageViewCard1);
        mImageViewCard2 = view.findViewById(R.id.imageViewCard2);
        mImageViewCard3 = view.findViewById(R.id.imageViewCard3);
        mImageViewCard4 = view.findViewById(R.id.imageViewCard4);

        initiateSpinners();

        mCardViewQuestion1.setOnClickListener(v -> {
            addFirstQuestion();
        });

        mCardViewQuestion2.setOnClickListener(v -> {
           addSecondQuestion();
        });

        mCardViewQuestion3.setOnClickListener(v -> {
            addThirdQuestion();
        });

        mCardViewQuestion4.setOnClickListener(v -> {
            addFourthQuestion();
        });

        mButtonCreateQuiz.setOnClickListener(v -> {
            mProgressDialog.show();

            mQuizName = mEditTextQuizName.getText().toString();

            mQuizController.getQuizBuilder().name(mQuizName).theme(mQuizTheme).level(mQuizDifficulty)
                    .questionList(mQuestionModelList).build();
            mQuizController.setQuizCreatedListener(new QuizController.QuizCreatedListener() {
                @Override
                public void onSuccess(boolean success, @Nullable QuizModel quizModel, @Nullable Exception e) {
                    mProgressDialog.dismiss();
                    Toast.makeText(getActivity(), getString(R.string.quiz_created), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }

                @Override
                public void onFailure(String error) {
                    mProgressDialog.dismiss();
                    Toast.makeText(getActivity(), getString(R.string.quiz_not_created), Toast.LENGTH_SHORT).show();
                }
            });
        });

        return view;
    }

    public void initiateSpinners() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.quiz_theme, R.layout.item_spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerQuizTheme.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getActivity(),
                R.array.quiz_difficulty, R.layout.item_spinner);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerQuizDifficulty.setAdapter(adapter2);

        mSpinnerQuizTheme.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CategoryEnum.Category[] values = CategoryEnum.Category.values();
                CategoryEnum.Category category = values[position];
                switch (category) {
                    case HISTORY:
                        mQuizTheme = CategoryEnum.Category.HISTORY.toString();
                        break;
                    case MUSIC:
                        mQuizTheme = CategoryEnum.Category.MUSIC.toString();
                        break;
                    case SPORT:
                        mQuizTheme = CategoryEnum.Category.SPORT.toString();
                        break;
                    case SCIENCE:
                        mQuizTheme = CategoryEnum.Category.SCIENCE.toString();
                        break;
                    case ART:
                        mQuizTheme = CategoryEnum.Category.ART.toString();
                        break;
                    case CINEMA:
                        mQuizTheme = CategoryEnum.Category.CINEMA.toString();
                        break;
                    case GEOGRAPHY:
                        mQuizTheme = CategoryEnum.Category.GEOGRAPHY.toString();
                        break;
                    case NATURE:
                        mQuizTheme = CategoryEnum.Category.NATURE.toString();
                        break;
                    case NEWS:
                        mQuizTheme = CategoryEnum.Category.NEWS.toString();
                        break;
                    case PEOPLE:
                        mQuizTheme = CategoryEnum.Category.PEOPLE.toString();
                        break;
                    case OTHER:
                        mQuizTheme = CategoryEnum.Category.OTHER.toString();
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mSpinnerQuizDifficulty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LevelEnum.Level values[] = LevelEnum.Level.values();
                LevelEnum.Level level = values[position];
                switch (level) {
                    case BEGINNER:
                        mQuizDifficulty = LevelEnum.Level.BEGINNER.toString();
                        break;
                    case INTERMEDIATE:
                        mQuizDifficulty = LevelEnum.Level.INTERMEDIATE.toString();
                        break;
                    case ADVANCE:
                        mQuizDifficulty = LevelEnum.Level.ADVANCE.toString();
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void addFirstQuestion() {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getContext());
        View view14 = getLayoutInflater().inflate(R.layout.item_add_quiz_question, null);
        mEditTextAddQuestion = view14.findViewById(R.id.editTextAddQuestion);
        mEditTextAddAnswer1 = view14.findViewById(R.id.editTextAddAnswer1);
        mEditTextAddAnswer2 = view14.findViewById(R.id.editTextAddAnswer2);
        mEditTextAddAnswer3 = view14.findViewById(R.id.editTextAddAnswer3);
        mEditTextAddAnswer4 = view14.findViewById(R.id.editTextAddAnswer4);
        mSpinnerAnswerIndex = view14.findViewById(R.id.spinnerAnswerIndex);
        mButtonConfirmQuestion = view14.findViewById(R.id.buttonConfirmQuestion);

        ArrayAdapter<CharSequence> adapter14 = ArrayAdapter.createFromResource(getActivity(),
                R.array.answer_index, R.layout.item_spinner_white);
        adapter14.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerAnswerIndex.setAdapter(adapter14);
        mSpinnerAnswerIndex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view14, int i, long l) {
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

        builder.setView(view14);
        mAlertDialog = builder.create();
        mAlertDialog.show();

        mButtonConfirmQuestion.setOnClickListener(v14 -> {
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

            mQuestionModel1.setQuestion(question);
            mQuestionModel1.setChoiceList(choiceList);
            mQuestionModel1.setAnswerIndex(mAnswerIndex1);

            mTextViewCard1.setText(getString(R.string.quiz_question_added));
            mImageViewCard1.setVisibility(View.VISIBLE);

            mQuestionModelList.add(mQuestionModel1);

            mAlertDialog.dismiss();
        });
    }

    public void addSecondQuestion() {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getContext());
        View view13 = getLayoutInflater().inflate(R.layout.item_add_quiz_question, null);
        mEditTextAddQuestion = view13.findViewById(R.id.editTextAddQuestion);
        mEditTextAddAnswer1 = view13.findViewById(R.id.editTextAddAnswer1);
        mEditTextAddAnswer2 = view13.findViewById(R.id.editTextAddAnswer2);
        mEditTextAddAnswer3 = view13.findViewById(R.id.editTextAddAnswer3);
        mEditTextAddAnswer4 = view13.findViewById(R.id.editTextAddAnswer4);
        mSpinnerAnswerIndex = view13.findViewById(R.id.spinnerAnswerIndex);
        mButtonConfirmQuestion = view13.findViewById(R.id.buttonConfirmQuestion);

        ArrayAdapter<CharSequence> adapter13 = ArrayAdapter.createFromResource(getActivity(),
                R.array.answer_index, R.layout.item_spinner_white);
        adapter13.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerAnswerIndex.setAdapter(adapter13);
        mSpinnerAnswerIndex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view13, int i, long l) {
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

        builder.setView(view13);
        mAlertDialog = builder.create();
        mAlertDialog.show();

        mButtonConfirmQuestion.setOnClickListener(v13 -> {
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

            mQuestionModel2.setQuestion(question);
            mQuestionModel2.setChoiceList(choiceList);
            mQuestionModel2.setAnswerIndex(mAnswerIndex2);

            mTextViewCard2.setText(getString(R.string.quiz_question_added));
            mImageViewCard2.setVisibility(View.VISIBLE);

            mQuestionModelList.add(mQuestionModel2);

            mAlertDialog.dismiss();
        });
    }

    public void addThirdQuestion() {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getContext());
        View view12 = getLayoutInflater().inflate(R.layout.item_add_quiz_question, null);
        mEditTextAddQuestion = view12.findViewById(R.id.editTextAddQuestion);
        mEditTextAddAnswer1 = view12.findViewById(R.id.editTextAddAnswer1);
        mEditTextAddAnswer2 = view12.findViewById(R.id.editTextAddAnswer2);
        mEditTextAddAnswer3 = view12.findViewById(R.id.editTextAddAnswer3);
        mEditTextAddAnswer4 = view12.findViewById(R.id.editTextAddAnswer4);
        mSpinnerAnswerIndex = view12.findViewById(R.id.spinnerAnswerIndex);
        mButtonConfirmQuestion = view12.findViewById(R.id.buttonConfirmQuestion);

        ArrayAdapter<CharSequence> adapter12 = ArrayAdapter.createFromResource(getActivity(),
                R.array.answer_index, R.layout.item_spinner_white);
        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerAnswerIndex.setAdapter(adapter12);
        mSpinnerAnswerIndex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view12, int i, long l) {
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

        builder.setView(view12);
        mAlertDialog = builder.create();
        mAlertDialog.show();

        mButtonConfirmQuestion.setOnClickListener(v12 -> {
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

            mQuestionModel3.setQuestion(question);
            mQuestionModel3.setChoiceList(choiceList);
            mQuestionModel3.setAnswerIndex(mAnswerIndex3);

            mTextViewCard3.setText(getString(R.string.quiz_question_added));
            mImageViewCard3.setVisibility(View.VISIBLE);

            mQuestionModelList.add(mQuestionModel3);

            mAlertDialog.dismiss();
        });
    }

    public void addFourthQuestion() {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getContext());
        View view1 = getLayoutInflater().inflate(R.layout.item_add_quiz_question, null);
        mEditTextAddQuestion = view1.findViewById(R.id.editTextAddQuestion);
        mEditTextAddAnswer1 = view1.findViewById(R.id.editTextAddAnswer1);
        mEditTextAddAnswer2 = view1.findViewById(R.id.editTextAddAnswer2);
        mEditTextAddAnswer3 = view1.findViewById(R.id.editTextAddAnswer3);
        mEditTextAddAnswer4 = view1.findViewById(R.id.editTextAddAnswer4);
        mSpinnerAnswerIndex = view1.findViewById(R.id.spinnerAnswerIndex);
        mButtonConfirmQuestion = view1.findViewById(R.id.buttonConfirmQuestion);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getActivity(),
                R.array.answer_index, R.layout.item_spinner_white);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerAnswerIndex.setAdapter(adapter1);
        mSpinnerAnswerIndex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view1, int i, long l) {
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

        builder.setView(view1);
        mAlertDialog = builder.create();
        mAlertDialog.show();

        mButtonConfirmQuestion.setOnClickListener(v1 -> {
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

            mQuestionModel4.setQuestion(question);
            mQuestionModel4.setChoiceList(choiceList);
            mQuestionModel4.setAnswerIndex(mAnswerIndex4);

            mTextViewCard4.setText(getString(R.string.quiz_question_added));
            mImageViewCard4.setVisibility(View.VISIBLE);

            mQuestionModelList.add(mQuestionModel4);

            mAlertDialog.dismiss();
        });
    }
}
