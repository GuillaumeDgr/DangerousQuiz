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
    private List<QuestionModel> questionList = new ArrayList<>();
    private String creatorId;
    private String creatorName;
    private String quizId;
    private String quizLevel;
    private List<String> players = new ArrayList<>();

//    public enum State {
//        DONE, PENDING
//    }

    public QuizModel() {}

    public QuizModel(String quizName, String quizTheme, List<QuestionModel> questionModelList, String quizLevel) {
        this.quizName = quizName;
        this.quizTheme = quizTheme;
        this.questionList = questionModelList;
        this.quizLevel = quizLevel;
    }

    protected QuizModel(Parcel in) {
        quizName = in.readString();
        quizTheme = in.readString();
        creatorId = in.readString();
        quizId = in.readString();
        quizLevel = in.readString();
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

    public List<QuestionModel> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<QuestionModel> questionList) {
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

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

    public String getQuizLevel() {
        return quizLevel;
    }

    public void setQuizLevel(String quizLevel) {
        this.quizLevel = quizLevel;
    }

//    public List<String> getPlayers() {
//        return players;
//    }
//
//    public void setPlayers(List<String> players) {
//        this.players = players;
//    }

//    public State getState(QuizModel quizModel) {
//        String uid = quizModel.getQuizId();
//        return getState(uid);
//    }

//    public State getState(String uid) {
//        if(players.contains(uid)){
//            return State.DONE;
//        }
//        return State.PENDING;
//    }

//    public State getState(String uid) {
//        for(String playerIdList: players) {
//            if(playerIdList.contains(uid))
//                return State.DONE;
//        }
//        return State.PENDING;
//    }

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
        dest.writeString(quizLevel);
    }
}
