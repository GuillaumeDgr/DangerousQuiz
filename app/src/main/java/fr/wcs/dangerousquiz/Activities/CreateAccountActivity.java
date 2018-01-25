package fr.wcs.dangerousquiz.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;

import fr.wcs.dangerousquiz.Controllers.AuthController;
import fr.wcs.dangerousquiz.Controllers.UserController;
import fr.wcs.dangerousquiz.Models.UserModel;
import fr.wcs.dangerousquiz.R;

public class CreateAccountActivity extends AppCompatActivity implements AuthController.AuthListener {

    private TextView mTextViewUserName,mTextViewUserMail, mTextViewUserPassword;
    private String mUserName, mUserMail, mUserPassword;
    private Button mButtonCreateAccount;
    private ProgressDialog mProgressDialog;
    private UserController mUserController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        AuthController.getInstance().setAuthListener(this);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.registration_in_progress));
        mProgressDialog.setIndeterminate(true);

        mTextViewUserName = findViewById(R.id.textViewUserName);
        mTextViewUserMail = findViewById(R.id.textViewUserMail);
        mTextViewUserPassword = findViewById(R.id.textViewUserPassword);

        mButtonCreateAccount = findViewById(R.id.buttonCreateAccount);
        mButtonCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserName = mTextViewUserName.getText().toString();
                mUserMail = mTextViewUserMail.getText().toString();
                mUserPassword = mTextViewUserPassword.getText().toString();

                mProgressDialog.show();
                AuthController.getInstance().createUserWithMail(CreateAccountActivity.this, mUserMail,
                        mUserPassword);
            }
        });
    }

    @Override
    public void onSuccess() {
        mUserController = UserController.getInstance();
        mUserController.createUser();

        mUserController.setUserCreatedListener(new UserController.UserCreatedListener() {
            @Override
            public void onSuccess(boolean success, @Nullable UserModel userModel, @Nullable Exception e) {
                mUserController.setFirstName(mUserName);
                mUserController.setScore(0);
            }

            @Override
            public void onFailure(String error) {
            }
        });
        mProgressDialog.cancel();
        Intent intent = new Intent(CreateAccountActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onFailure(String error) {
        mProgressDialog.cancel();
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSignOut() {

    }
}
