package fr.wcs.dangerousquiz.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import fr.wcs.dangerousquiz.Adapters.SwipeDeckAdapter;
import fr.wcs.dangerousquiz.Controllers.AuthController;
import fr.wcs.dangerousquiz.Controllers.QuizController;
import fr.wcs.dangerousquiz.Controllers.UserController;
import fr.wcs.dangerousquiz.Models.QuizModel;
import fr.wcs.dangerousquiz.Models.UserModel;
import fr.wcs.dangerousquiz.R;
import fr.wcs.dangerousquiz.Utils.FirebaseHelper;
import link.fls.swipestack.SwipeStack;

import static fr.wcs.dangerousquiz.Utils.Constants.QUIZ_ENTRY;

public class MainFragment extends Fragment {

    private AuthController mAuthController;
    private UserController mUserController;
    private UserModel mUser;
    private SwipeStack mSwipeDeckCards;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SwipeDeckAdapter mSwipeDeckAdapter;
    private List<QuizModel> mQuizModelList = new ArrayList<>();
    private DatabaseReference mDatabaseReference;
    private QuizController mQuizController;

    public MainFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        mAuthController = AuthController.getInstance();
        mAuthController.setAuthListener(new AuthController.AuthListener() {
            @Override
            public void onSuccess() {
                mUserController = UserController.getInstance();
                mUser = mUserController.getUser();
            }
            @Override
            public void onFailure(String error) {}

            @Override
            public void onSignOut() {
            }
        });

        setRetainInstance(true);

        mSwipeDeckCards = view.findViewById(R.id.swipeDeckCards);
        mSwipeRefreshLayout = view.findViewById(R.id.swipeRefreshCards);

        mSwipeRefreshLayout.setOnRefreshListener(this::loadQuizList);
        if(! mQuizModelList.isEmpty()) {
            mSwipeRefreshLayout.setEnabled(false);
        }

        mSwipeDeckAdapter = new SwipeDeckAdapter(mQuizModelList, getActivity());
        mSwipeDeckCards.setAdapter(mSwipeDeckAdapter);

        mSwipeDeckCards.setListener(new SwipeStack.SwipeStackListener() {
            @Override
            public void onViewSwipedToLeft(int position) {
            }

            @Override
            public void onViewSwipedToRight(int position) {
                QuizModel quizModelMatched = mQuizModelList.get(position);

                mQuizController = QuizController.getInstance();
                mQuizController.matchQuiz(quizModelMatched);

                ProgressDialog progressDialog = new ProgressDialog(getContext());
                progressDialog.setMessage("Quiz Loading ...");
                progressDialog.show();

                mQuizController.setOnQuizMatchedListener(new QuizController.QuizMatchedListener() {
                    @Override
                    public void onSuccess(boolean success, @Nullable QuizModel quizModel, @Nullable Exception e) {
                        progressDialog.cancel();
                        Intent intent = new Intent(getActivity(), PlayActivity.class);
                        mQuizController.setSelectedQuiz(quizModelMatched);
                        startActivity(intent);
                    }
                    @Override
                    public void onFailure(String error) {
                    }
                });
            }

            @Override
            public void onStackEmpty() {
                mSwipeRefreshLayout.setEnabled(true);
            }
        });

        mSwipeDeckCards.setSwipeProgressListener(new SwipeStack.SwipeProgressListener() {
            @Override
            public void onSwipeStart(int position) {

            }

            @Override
            public void onSwipeProgress(int position, float progress) {
                View view = mSwipeDeckCards.getTopView();
                view.findViewById(R.id.imageViewLeftSwipe).setAlpha(progress < 0 ? -progress : 0);
                view.findViewById(R.id.imageViewRightSwipe).setAlpha(progress > 0 ? progress : 0);
            }

            @Override
            public void onSwipeEnd(int position) {
                View view = mSwipeDeckCards.getTopView();
                view.findViewById(R.id.imageViewLeftSwipe).setAlpha(0);
                view.findViewById(R.id.imageViewRightSwipe).setAlpha(0);
            }
        });

        loadQuizList();

        return view;
    }

    private void loadQuizList() {
        if(! mSwipeRefreshLayout.isRefreshing()) mSwipeRefreshLayout.setRefreshing(true);

        mDatabaseReference = FirebaseHelper.getDatabase().getReference().child(QUIZ_ENTRY);
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    mQuizModelList.add(snap.getValue(QuizModel.class));
                }
                mSwipeDeckAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
        if (mQuizModelList.isEmpty()) {
            mSwipeRefreshLayout.setRefreshing(false);
            mSwipeRefreshLayout.setEnabled(true);
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            mSwipeRefreshLayout.setEnabled(false);
        }
    }
}
