package fr.wcs.dangerousquiz.Models;

import java.util.ArrayList;
import java.util.List;

public class UserModel {

    private String firstName = "";
    private int score = 0;
    private String uid = "";
    private String avatar = "";
    private List<String> quizDone = new ArrayList<>();

    public UserModel() {}

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<String> getQuizDone() {
        return quizDone;
    }

    public void setQuizDone(List<String> quizDone) {
        this.quizDone = quizDone;
    }
}
