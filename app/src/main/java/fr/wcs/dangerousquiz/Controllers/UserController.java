package fr.wcs.dangerousquiz.Controllers;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import fr.wcs.dangerousquiz.Models.QuizModel;
import fr.wcs.dangerousquiz.Models.UserModel;
import fr.wcs.dangerousquiz.Utils.FirebaseHelper;

import static fr.wcs.dangerousquiz.Utils.Constants.USERS_ENTRY;

/**
 * Created by apprenti on 1/19/18.
 */

public class UserController {

    private static volatile UserController sInstance = null;
    private AuthController mAuthController;
    private UserModel mUser = null;
    private DatabaseReference mDatabaseReference;
    private UserCreatedListener mUserCreatedListener = null;

    private UserController(){
        if(sInstance != null) {
            throw new RuntimeException("Use getInstance() to get the single instance of this class.");
        }
        init();
    }

    public static UserController getInstance(){
        // Check for the first time
        if(sInstance == null) {
            // Check for the second time
            synchronized (UserController.class) {
                // If no Instance available create new One
                if(sInstance == null) {
                    sInstance = new UserController();
                }
            }
        }
        return sInstance;
    }

    private void init(){
        mAuthController = AuthController.getInstance();
        mUser = new UserModel();
        mUser.setUid(mAuthController.getUser().getUid());

        mDatabaseReference = FirebaseHelper.getDatabase().getReference().child(USERS_ENTRY).child(mUser.getUid());
        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                UserModel userModel = snapshot.getValue(UserModel.class);
                if (mUser != null) {
                    mUser = userModel;
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }

    public UserModel getUser(){
        return mUser;
    }

    // Create User
    public void createUser() {
        mUser = new UserModel();
        mUser.setUid(mAuthController.getUser().getUid());

        mDatabaseReference.child("uid").setValue(mUser.getUid()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                if (mUserCreatedListener != null) {
                    mUserCreatedListener.onSuccess(true, mUser, null);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (mUserCreatedListener != null) {
                    mUserCreatedListener.onFailure(e.getMessage());
                }
            }
        });
    }

    public void setFirstName(String firstName) {
        mUser.setFirstName(firstName);
        mDatabaseReference.child("firstName").setValue(firstName);
    }

    public void setScore(int score) {
        mUser.setScore(score);
        mDatabaseReference.child("score").setValue(score);
    }

    public interface UserCreatedListener {
        void onSuccess(boolean success, @Nullable UserModel userModel, @Nullable Exception e);
        void onFailure(String error);
    }

    public void setUserCreatedListener(UserCreatedListener userCreatedListener) {
        mUserCreatedListener = userCreatedListener;
    }

    public static boolean isInstanciated() {
        return sInstance != null;
    }

    public static void destroy(){
        sInstance = null;
    }
}
