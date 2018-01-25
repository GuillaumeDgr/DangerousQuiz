package fr.wcs.dangerousquiz.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseUser;

import fr.wcs.dangerousquiz.Controllers.AuthController;
import fr.wcs.dangerousquiz.Controllers.UserController;
import fr.wcs.dangerousquiz.R;

public class SignInActivity extends AppCompatActivity implements AuthController.AuthListener {

    private AuthController mAuthController;
    private FirebaseUser mUser;
    private UserController mUserController;
    private TextView mTextViewUserMail, mTextViewUserPassword;
    private Button mButtonSignIn, mButtonCreateAccount;
    private String mUserMail, mUserPassword;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        AuthController.getInstance().setAuthListener(this);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Connexion");
        mProgressDialog.setIndeterminate(true);

//        AuthController.getInstance().setAuthListener(new AuthController.AuthListener() {
//            @Override
//            public void onSuccess() {
//                mUserController = UserController.getInstance();
//            }
//
//            @Override
//            public void onFailure(String error) {
//
//            }
//
//            @Override
//            public void onSignOut() {
//
//            }
//        });

        mTextViewUserMail = findViewById(R.id.textViewUserMail);
        mTextViewUserPassword = findViewById(R.id.textViewUserPassword);
        mButtonSignIn = findViewById(R.id.buttonSignIn);
        mButtonCreateAccount = findViewById(R.id.buttonCreateAccount);

        mButtonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserMail = mTextViewUserMail.getText().toString();
                mUserPassword = mTextViewUserPassword.getText().toString();

                mProgressDialog.show();
                AuthController.getInstance().getAuthWithEmail(mUserMail, mUserPassword);
            }
        });

        mButtonCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mUserMail = mTextViewUserMail.getText().toString();
//                mUserPassword = mTextViewUserPassword.getText().toString();
//                mUserName = mTextViewUserName.getText().toString();
//                AuthController.AuthBuilder.getInstance().email(mUserMail).password(mUserPassword)
//                        .build(SignInActivity.this);
                Intent intent = new Intent(SignInActivity.this, CreateAccountActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onSuccess() {
        mProgressDialog.cancel();
        mUserController = UserController.getInstance();
//        mUserController.setUserReadyListener(user -> {
            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
//        });
    }

    @Override
    public void onFailure(String error) {
        Toast.makeText(SignInActivity.this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSignOut() {

    }
}
