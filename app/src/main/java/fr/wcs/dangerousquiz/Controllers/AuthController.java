package fr.wcs.dangerousquiz.Controllers;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Patterns;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import fr.wcs.dangerousquiz.R;

/**
 * Created by apprenti on 1/22/18.
 */

public class AuthController {

    private static volatile AuthController sInstance = null;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private AuthListener mAuthListener = null;
    private FirebaseAuth.AuthStateListener mFirebaseAuthListener;
    private OnCompleteListener<AuthResult> mFirebaseAuthCompleteListener;

    private AuthController(Context context) {
        // Prevent from the reflection API.
        if (sInstance != null) {
            throw new RuntimeException("Use getInstance() to get the single instance of this class.");
        }

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        // Firebase Auth Listener
        mFirebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                mUser = firebaseAuth.getCurrentUser();
                if (mUser != null) {
                    mUser = mAuth.getCurrentUser();
                    connectionSuccess();
                } else {
                    // User is signed out
                    connectionClose();
                }
            }
        };

        // Firebase Auth Completed Listener
        mFirebaseAuthCompleteListener = new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    mUser = mAuth.getCurrentUser();
                    connectionSuccess();
                } else {
                    connectionFailure(task.getException().getMessage().toString());
                }
            }
        };
    }

    public static AuthController getInstance(Context context){
        // Check for the first time
        if(sInstance == null) {
            // Check for the second time
            synchronized (AuthController.class) {
                // If no Instance available create new One
                if(sInstance == null) {
                    sInstance = new AuthController(context);
                }
            }
        }
        return sInstance;
    }

    public static AuthController getInstance() {
        if(sInstance == null) {
            throw new RuntimeException("The first call to getInstance() should contains the activity");
        }
        else {
            return sInstance;
        }
    }

    public FirebaseUser getUser() {
        return mUser;
    }

    public void getAuthWithEmail(String eMail, String password) {
        mAuth.signInWithEmailAndPassword(eMail, password).
                addOnCompleteListener(mFirebaseAuthCompleteListener);
    }

    public void createUserWithMail(Activity activity, String email, String password) {
        // Bad Formatted mail
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            connectionFailure(activity.getString(R.string.error_email_badly_formated));
        }
        // Too short mPassword
        else if(password.length() < 6) {
            connectionFailure(activity.getString(R.string.error_too_short_password));
        }
        // Everything is ok, so let's try to register
        else {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(mFirebaseAuthCompleteListener);
        }
    }

    public void signOut(){
        mAuth.signOut();
        connectionClose();
    }

    public void setAuthListener(AuthListener listener) {
        mAuthListener = listener;
    }

    public void removeAuthListener() {
        mAuthListener = null;
    }

    public interface AuthListener {
        void onSuccess();
        void onFailure(String error);
        void onSignOut();
    }

    public void attach() {
        mAuth.addAuthStateListener(mFirebaseAuthListener);
    }

    public void detach(){
        mAuth.removeAuthStateListener(mFirebaseAuthListener);
    }

    private void connectionSuccess(){
        if(mAuthListener != null) {
            mAuthListener.onSuccess();
        }
    }

    private void connectionFailure(String error) {
        if(mAuthListener != null) {
            mAuthListener.onFailure(error);
        }
    }

    private void connectionClose() {
        if(mAuthListener != null) {
            mAuthListener.onSignOut();
        }
    }

    public static class AuthBuilder {
        private static AuthBuilder sInstance;
        private String mEmail = "";
        private String mPassword = "";

        private AuthBuilder() {
        }

        public static AuthBuilder getInstance() {
            if (sInstance == null) {
                sInstance = new AuthBuilder();
            }
            return sInstance;
        }

        public AuthBuilder email(String email) {
            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                throw new RuntimeException("Email badly formatted.");
            }
            else {
                mEmail = email;
                return this;
            }
        }

        public AuthBuilder password(String password) {
            if(password.length() < 6) {
                throw new RuntimeException("Password should be at least 6 characters.");
            }
            else {
                mPassword = password;
                return this;
            }
        }

        public void build(Activity activity) {
            if(mEmail.isEmpty() || mPassword.isEmpty()) {
                throw new RuntimeException("Email, password and confirm password must be filled.");
            }
            else {
                AuthController authController = AuthController.getInstance(activity);
                authController.createUserWithMail(activity, mEmail, mPassword);
            }
        }
    }
}
