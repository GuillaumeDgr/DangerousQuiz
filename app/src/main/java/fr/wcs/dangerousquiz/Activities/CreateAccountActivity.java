package fr.wcs.dangerousquiz.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseUser;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import de.hdodenhof.circleimageview.CircleImageView;
import fr.wcs.dangerousquiz.Controllers.AuthController;
import fr.wcs.dangerousquiz.Controllers.UserController;
import fr.wcs.dangerousquiz.Models.UserModel;
import fr.wcs.dangerousquiz.R;
import fr.wcs.dangerousquiz.Utils.FirebaseHelper;

public class CreateAccountActivity extends AppCompatActivity implements AuthController.AuthListener {

    private UserController mUserController;
    private AuthController mAuthController;
    private TextView mTextViewUserName,mTextViewUserMail, mTextViewUserPassword;
    private String mUserName, mUserMail, mUserPassword;
    private CircleImageView mImageViewAvatar;
    private Button mButtonCreateAccount;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        mAuthController = AuthController.getInstance();
        mAuthController.setAuthListener(this);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.registration_in_progress));
        mProgressDialog.setIndeterminate(true);

        mImageViewAvatar = findViewById(R.id.imageViewAvatar);
        mTextViewUserName = findViewById(R.id.textViewUserName);
        mTextViewUserMail = findViewById(R.id.textViewUserMail);
        mTextViewUserPassword = findViewById(R.id.textViewUserPassword);

        Uri uri = mAuthController.getUser().getPhotoUrl();

        if(uri != null){
            Glide.with(this)
                    .load(uri)
                    .into(mImageViewAvatar);
        }

        mImageViewAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1)
                        .setCropShape(CropImageView.CropShape.OVAL)
                        .start(CreateAccountActivity.this);
            }
        });

        mButtonCreateAccount = findViewById(R.id.buttonCreateAccount);
        mButtonCreateAccount.setOnClickListener(v -> {
            mUserName = mTextViewUserName.getText().toString();
            mUserMail = mTextViewUserMail.getText().toString();
            mUserPassword = mTextViewUserPassword.getText().toString();

            mProgressDialog.show();

            AuthController.getInstance().createUserWithMail(CreateAccountActivity.this, mUserMail,
                    mUserPassword);
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
                FirebaseHelper.uploadAvatar(mImageViewAvatar.getDrawable(),
                        AuthController.getInstance().getUser().getUid())
                        .setUploadImageListener(new FirebaseHelper.UploadImageListener() {
                            @Override
                            public void onSuccess(Uri imageUri) {
                                mProgressDialog.cancel();
                                mUserController.setAvatar(imageUri.toString());
                                Intent intent = new Intent(CreateAccountActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                            @Override
                            public void onFailure(String error) {
                                mProgressDialog.cancel();
                            }
                        });
            }
            @Override
            public void onFailure(String error) {
                mProgressDialog.cancel();
            }
        });
    }

    @Override
    public void onFailure(String error) {
        mProgressDialog.cancel();
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSignOut() {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                mImageViewAvatar.setImageDrawable(Drawable.createFromPath(resultUri.getPath()));
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(CreateAccountActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
