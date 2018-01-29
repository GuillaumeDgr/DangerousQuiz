package fr.wcs.dangerousquiz.Activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseUser;
import fr.wcs.dangerousquiz.Controllers.AuthController;
import fr.wcs.dangerousquiz.Controllers.QuizController;
import fr.wcs.dangerousquiz.Controllers.UserController;
import fr.wcs.dangerousquiz.Models.UserModel;
import fr.wcs.dangerousquiz.R;
import fr.wcs.dangerousquiz.Utils.Constants;

public class SplashScreenActivity extends AppCompatActivity {

    private AuthController mAuthController;
    private UserController mUserController;
    private FirebaseUser mUser;
    private Handler mHandler;
    private boolean mLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        mAuthController = AuthController.getInstance(this);
        mAuthController.setAuthListener(new AuthController.AuthListener() {
            @Override
            public void onSuccess() {
                mUser = mAuthController.getUser();
                if(UserController.isInstanciated()) {
                    mUserController = UserController.getInstance();
                    init();
                    return;
                }
                mUserController = UserController.getInstance();
                mUserController.setUserReadyListener(user -> {
                    // Init Controllers
                    mUserController.removeUserReadyListener();
                    init();
                });
            }

            @Override
            public void onFailure(String error) {
            }
            @Override
            public void onSignOut() {
            }
        });
        mHandler = new Handler();
        mHandler.sendEmptyMessage(0);
        mHandler.postDelayed(() -> {
            if(mUser == null){
                startActivity(new Intent(SplashScreenActivity.this, SignInActivity.class));
                // close this activity
                finish();
            }else if(mLoaded){
                goToNextActivity();
            }
//            startActivity(new Intent(SplashScreenActivity.this, SignInActivity.class));
//            finish();
            mHandler.removeMessages(0);
        }, Constants.SPLASH_TIME_OUT);
    }

    private void init() {
        QuizController.getInstance();
        mLoaded = true;
        if(!mHandler.hasMessages(0)) {
            goToNextActivity();
        }
    }

    private void goToNextActivity(){
        UserModel userModel = mUserController.getUser();
        Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        mAuthController.attach();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mAuthController.detach();
        super.onStop();
    }
}
