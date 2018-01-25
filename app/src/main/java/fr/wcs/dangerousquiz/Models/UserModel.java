package fr.wcs.dangerousquiz.Models;

public class UserModel {

    private String firstName = "";
    private int score = 0;
    private String uid = "";

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
}
