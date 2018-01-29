package fr.wcs.dangerousquiz.Activities;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import fr.wcs.dangerousquiz.Adapters.QuizDoneAdapter;
import fr.wcs.dangerousquiz.Controllers.UserController;
import fr.wcs.dangerousquiz.Models.QuizModel;
import fr.wcs.dangerousquiz.Models.UserModel;
import fr.wcs.dangerousquiz.R;
import fr.wcs.dangerousquiz.Utils.FirebaseHelper;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.ColorFilterTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;
import static fr.wcs.dangerousquiz.Utils.Constants.BLUR_RADIUS;
import static fr.wcs.dangerousquiz.Utils.Constants.QUIZ_ENTRY;

public class ProfileFragment extends Fragment  {

    private UserController mUserController;
    private UserModel mUser = null;
    private TextView mTextViewUserName, mTextViewUserScore;
    private CircleImageView mImageViewUserAvatar;
    private ImageView mImageViewBackground;
    private RecyclerView mRecyclerViewQuizDone;
    private QuizDoneAdapter mQuizDoneAdapter;
    private List<QuizModel> mQuizModelList = new ArrayList<>();
    private DatabaseReference mQuizReference;

    public ProfileFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        mUserController = UserController.getInstance();
        mUser = mUserController.getUser();

        mTextViewUserName = view.findViewById(R.id.textViewUserFirstName);
        mTextViewUserScore = view.findViewById(R.id.textViewUserScore);
        mImageViewUserAvatar = view.findViewById(R.id.imageViewUserAvatar);
        mImageViewBackground = view.findViewById(R.id.imageViewUserAvatarBackground);

        mTextViewUserName.setText(mUser.getFirstName());
        mTextViewUserScore.setText(String.valueOf(mUser.getScore()));

        MultiTransformation multi = new MultiTransformation(
                new ColorFilterTransformation(getResources().getColor(R.color.drawerAvatarBackgroundFilter)),
                new BlurTransformation(BLUR_RADIUS));
        Glide.with(this)
                .load(mUser.getAvatar()).apply(bitmapTransform(multi))
                .into(mImageViewBackground);
        Glide.with(this)
                .load(mUser.getAvatar())
                .into(mImageViewUserAvatar);

        mRecyclerViewQuizDone = view.findViewById(R.id.recyclerViewQuizDone);
        mQuizDoneAdapter = new QuizDoneAdapter(mQuizModelList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerViewQuizDone.setLayoutManager(mLayoutManager);
        mRecyclerViewQuizDone.setItemAnimator(new DefaultItemAnimator());
        mRecyclerViewQuizDone.setAdapter(mQuizDoneAdapter);

        List<String> mQuizDoneList = mUser.getQuizDone();

        mQuizReference = FirebaseHelper.getDatabase().getReference().child(QUIZ_ENTRY);
        mQuizReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    QuizModel quizModel = snap.getValue(QuizModel.class);
                    for (int i = 0; i < mQuizDoneList.size(); i++) {
                        if ((mQuizDoneList.get(i)).equals(quizModel.getQuizId())) {
                            mQuizModelList.add(quizModel);
                        }
                    }
                    mQuizDoneAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

        return view;
    }
}
