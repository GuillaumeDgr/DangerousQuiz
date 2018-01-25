package fr.wcs.dangerousquiz.Activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.google.firebase.auth.FirebaseUser;
import fr.wcs.dangerousquiz.Controllers.AuthController;
import fr.wcs.dangerousquiz.Controllers.QuizController;
import fr.wcs.dangerousquiz.Controllers.UserController;
import fr.wcs.dangerousquiz.R;
import fr.wcs.dangerousquiz.Utils.Constants;

public class SplashScreenActivity extends AppCompatActivity {

    private AuthController mAuthController;
    private UserController mUserController;
    private FirebaseUser mUser;
    private Handler mHandler;

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
                    return;
                }
//                mUserController = UserController.getInstance();
//                mUserController.setUserReadyListener(user -> {
//                    mUserController.removeUserReadyListener();
//                });
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
            startActivity(new Intent(SplashScreenActivity.this, SignInActivity.class));
            finish();
            mHandler.removeMessages(0);
        }, Constants.SPLASH_TIME_OUT);
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
