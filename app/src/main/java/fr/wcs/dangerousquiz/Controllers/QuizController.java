package fr.wcs.dangerousquiz.Controllers;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import fr.wcs.dangerousquiz.Activities.PlayActivity;
import fr.wcs.dangerousquiz.Models.Question;
import fr.wcs.dangerousquiz.Models.QuizModel;
import fr.wcs.dangerousquiz.Models.UserModel;
import fr.wcs.dangerousquiz.Utils.FirebaseHelper;

import static fr.wcs.dangerousquiz.Utils.Constants.QUIZ_ENTRY;

/**
 * Created by apprenti on 1/22/18.
 */

public class QuizController {

    private static volatile QuizController sInstance = null;
    private QuizBuilder mQuizBuilder = null;
    private UserModel mUser;
    private String mUserId;
    private FirebaseDatabase mDatabaseReference;
    private DatabaseReference mQuizRef;
    private QuizCreatedListener mQuizCreatedListener = null;
    private QuizMatchedListener mQuizMatchedListener = null;
    private QuizModel mSelectedQuiz = null;

    // Constructor
    private QuizController() {
        if (sInstance != null) {
            throw new RuntimeException("Use getInstance() to get the single instance of this class.");
        }

        mUser = UserController.getInstance().getUser();
        mUserId = mUser.getUid();

        mDatabaseReference = FirebaseHelper.getDatabase();
        mQuizRef = mDatabaseReference.getReference().child(QUIZ_ENTRY);
    }

    // Instance
    public static QuizController getInstance() {
        // Check for the first time
        if (sInstance == null) {
            // Check for the second time
            synchronized (QuizController.class) {
                // If no Instance available create new One
                if (sInstance == null) {
                    sInstance = new QuizController();
                }
            }
        }
        return sInstance;
    }

    public static void destroy() {
        sInstance = null;
    }

    public static boolean isInstantiated() {
        return sInstance != null;
    }

    public QuizModel getSelectedQuiz() {
        return mSelectedQuiz;
    }

    public void setSelectedQuiz(QuizModel selectedQuiz) {
        mSelectedQuiz = selectedQuiz;
    }

    // Create Quiz Method
    public void createQuiz(final QuizModel quizModel) {
        quizModel.setCreatorId(mUserId);
        final String quizId = mQuizRef.push().getKey();
        quizModel.setQuizId(quizId);

        mQuizRef.child(quizId).setValue(quizModel).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                if (mQuizCreatedListener != null) {
                    mQuizCreatedListener.onSuccess(true, quizModel, null);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (mQuizCreatedListener != null) {
                    mQuizCreatedListener.onFailure(e.getMessage());
                }
            }
        });
    }

    public interface QuizCreatedListener {
        void onSuccess(boolean success, @Nullable QuizModel quizModel, @Nullable Exception e);
        void onFailure(String error);
    }

    public void setQuizCreatedListener(QuizCreatedListener quizCreatedListener) {
        mQuizCreatedListener = quizCreatedListener;
    }

    // Match Quiz Method
    public void matchQuiz(QuizModel quizModel) {
        mQuizRef.child(quizModel.getQuizId()).child("players").setValue(mUser.getUid())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                if (mQuizMatchedListener != null) {
                    mQuizMatchedListener.onSuccess(true, quizModel, null);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (mQuizMatchedListener != null) {
                    mQuizMatchedListener.onFailure(e.getMessage());
                }
            }
        });
    }

    public interface QuizMatchedListener {
        void onSuccess(boolean success, @Nullable QuizModel quizModel, @Nullable Exception e);
        void onFailure(String error);
    }

    public void setOnQuizMatchedListener(QuizMatchedListener quizMatchedListener) {
        mQuizMatchedListener = quizMatchedListener;
    }

    // Builder
    public class QuizBuilder {

        private String mQuizName;
        private String mQuizTheme;
        private List<Question> mQuestionList;

        private QuizBuilder() {
        }

        public QuizBuilder name(String name) {
            mQuizName = name;
            return this;
        }

        public QuizBuilder theme(String theme) {
            mQuizTheme = theme;
            return this;
        }

        public QuizBuilder questionList(List<Question> questionList) {
            mQuestionList = questionList;
            return this;
        }

        public void build() {
            QuizModel quizModel = new QuizModel(mQuizName, mQuizTheme, mQuestionList);
            createQuiz(quizModel);
        }
    }

    public QuizBuilder getQuizBuilder() {
        if (mQuizBuilder == null) {
            mQuizBuilder = new QuizBuilder();
        }
        return mQuizBuilder;
    }
}
