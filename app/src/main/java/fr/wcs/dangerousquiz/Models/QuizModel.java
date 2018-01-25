package fr.wcs.dangerousquiz.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apprenti on 1/22/18.
 */

public class QuizModel implements Parcelable {

    private String quizName;
    private String quizTheme;
    private List<Question> questionList = new ArrayList<>();
    private String creatorId;
    private String quizId;

    public QuizModel() {}

    public QuizModel(String quizName, String quizTheme, List<Question> questionList) {
        this.quizName = quizName;
        this.quizTheme = quizTheme;
        this.questionList = questionList;
    }

    protected QuizModel(Parcel in) {
        quizName = in.readString();
        quizTheme = in.readString();
        creatorId = in.readString();
        quizId = in.readString();
    }

    public static final Creator<QuizModel> CREATOR = new Creator<QuizModel>() {
        @Override
        public QuizModel createFromParcel(Parcel in) {
            return new QuizModel(in);
        }

        @Override
        public QuizModel[] newArray(int size) {
            return new QuizModel[size];
        }
    };

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public String getQuizTheme() {
        return quizTheme;
    }

    public void setQuizTheme(String quizTheme) {
        this.quizTheme = quizTheme;
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getQuizId() {
        return quizId;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(quizName);
        dest.writeString(quizTheme);
        dest.writeString(creatorId);
        dest.writeString(quizId);
    }
}
