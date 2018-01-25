package fr.wcs.dangerousquiz.Activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import fr.wcs.dangerousquiz.Adapters.ScoreAdapter;
import fr.wcs.dangerousquiz.Models.UserModel;
import fr.wcs.dangerousquiz.R;
import fr.wcs.dangerousquiz.Utils.FirebaseHelper;

import static fr.wcs.dangerousquiz.Utils.Constants.USERS_ENTRY;

public class ScoreFragment extends Fragment {

    private RecyclerView mRecyclerViewBestScores;
    private ScoreAdapter mScoreAdapter;
    private List<UserModel> mUserModelList = new ArrayList<>();
    private ImageButton mImageButtonNameFilter, mImageButtonScoreFilter;
    private DatabaseReference mDatabaseReference;

    public ScoreFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_score, container, false);

        mRecyclerViewBestScores = view.findViewById(R.id.recyclerViewBestScores);
        mScoreAdapter = new ScoreAdapter(mUserModelList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerViewBestScores.setLayoutManager(mLayoutManager);
        mRecyclerViewBestScores.setItemAnimator(new DefaultItemAnimator());
        mRecyclerViewBestScores.setAdapter(mScoreAdapter);

        mDatabaseReference = FirebaseHelper.getDatabase().getReference().child(USERS_ENTRY);
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    mUserModelList.add(snap.getValue(UserModel.class));
                }
                mScoreAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

        mImageButtonNameFilter = view.findViewById(R.id.imageButtonNameFilter);
        mImageButtonNameFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterByName();
                mScoreAdapter.notifyDataSetChanged();
            }
        });
        mImageButtonScoreFilter = view.findViewById(R.id.imageButtonScoreFilter);
        mImageButtonScoreFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterByScore();
                mScoreAdapter.notifyDataSetChanged();
            }
        });

        return view;
    }

    private void filterByName() {
        Collections.sort(mUserModelList, new Comparator<UserModel>() {
            public int compare(UserModel one, UserModel other) {
                return one.getFirstName().compareTo(other.getFirstName());
            }
        });
    }

    private void filterByScore() {
        Collections.sort(mUserModelList, new Comparator<UserModel>() {
            public int compare(UserModel one, UserModel other) {
                return other.getScore() - one.getScore();
            }
        });
    }
}
