package fr.wcs.dangerousquiz.Activities;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;

import de.hdodenhof.circleimageview.CircleImageView;
import fr.wcs.dangerousquiz.Controllers.UserController;
import fr.wcs.dangerousquiz.Models.UserModel;
import fr.wcs.dangerousquiz.R;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.ColorFilterTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;
import static fr.wcs.dangerousquiz.Utils.Constants.BLUR_RADIUS;

public class ProfileFragment extends Fragment  {

    private UserController mUserController;
    private UserModel mUser = null;
    private EditText mEditTextUserName, mEditTextUserScore;
    private CircleImageView mImageViewUserAvatar;
    private ImageView mImageViewBackground;

    public ProfileFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        mUserController = UserController.getInstance();
        mUser = mUserController.getUser();

        mEditTextUserName = view.findViewById(R.id.editTextUserName);
        mEditTextUserScore = view.findViewById(R.id.editTextUserScore);
        mImageViewUserAvatar = view.findViewById(R.id.imageViewUserAvatar);
        mImageViewBackground = view.findViewById(R.id.imageViewProfilBackground);

        mEditTextUserScore.setText(String.valueOf(mUser.getScore()));
        mEditTextUserName.setText(mUser.getFirstName());

        MultiTransformation multi = new MultiTransformation(
                new ColorFilterTransformation(getResources().getColor(R.color.drawerAvatarBackgroundFilter)),
                new BlurTransformation(BLUR_RADIUS));
        Glide.with(this)
                .load(mUser.getAvatar()).apply(bitmapTransform(multi))
                .into(mImageViewBackground);
        Glide.with(this)
                .load(mUser.getAvatar())
                .into(mImageViewUserAvatar);

        return view;
    }
}
