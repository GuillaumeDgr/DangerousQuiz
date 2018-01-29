package fr.wcs.dangerousquiz.Activities;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;
import fr.wcs.dangerousquiz.Controllers.AuthController;
import fr.wcs.dangerousquiz.Controllers.QuizController;
import fr.wcs.dangerousquiz.Controllers.UserController;
import fr.wcs.dangerousquiz.Models.UserModel;
import fr.wcs.dangerousquiz.R;

import static fr.wcs.dangerousquiz.Utils.Constants.NAVIGATION_ITEM;
import static fr.wcs.dangerousquiz.Utils.Constants.PROFILE_INDEX;
import static fr.wcs.dangerousquiz.Utils.Constants.SCORE_INDEX;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AuthController mAuthController;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private ActionBarDrawerToggle mToggle;
    private FragmentManager mFragmentManager;
    private Toolbar mToolbarMainActivity;
    private CircleImageView mImageViewAvatarTopRightCorner;
    private int mCurrentFragmentId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuthController = AuthController.getInstance(this);
        mAuthController.setAuthListener(new AuthController.AuthListener() {
            @Override
            public void onSuccess() {

            }
            @Override
            public void onFailure(String error) {

            }
            @Override
            public void onSignOut() {
            }
        });

        // Toolbar
        mToolbarMainActivity = findViewById(R.id.toolbarMainActivity);
        setSupportActionBar(mToolbarMainActivity);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Drawer Menu
        mDrawerLayout = findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbarMainActivity, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        mNavigationView = findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        Intent intent = getIntent();
        int itemIndex = intent.getIntExtra(NAVIGATION_ITEM, 0);

        mFragmentManager = getSupportFragmentManager();
        onNavigationItemSelected(mNavigationView.getMenu().getItem(itemIndex));
        mNavigationView.getMenu().getItem(itemIndex).setChecked(true);

        mImageViewAvatarTopRightCorner = findViewById(R.id.circleImageViewAvatarTopRightCorner);
//        loadUserAvatar();
    }

    // Drawer Menu
    @Override
    public void onBackPressed() {
        Fragment currentFragment = mFragmentManager.findFragmentById(R.id.layoutMainContent);
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else if (!(currentFragment instanceof MainFragment)) {
            mFragmentManager.beginTransaction().replace(R.id.layoutMainContent, new MainFragment()).commit();
            mNavigationView.getMenu().getItem(0).setChecked(true);
            mCurrentFragmentId = R.id.drawer_home;
            if (!getSupportActionBar().isShowing()) getSupportActionBar().show();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_profile:
                if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                }
//                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                goToProfileFragment();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id != mCurrentFragmentId) {
            if (!getSupportActionBar().isShowing()) getSupportActionBar().show();
            mCurrentFragmentId = id;
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            Fragment fragment = null;
            Class fragmentClass = null;

            switch (id) {
                case R.id.drawer_home:
                    fragmentClass = MainFragment.class;
                    break;
                case R.id.drawer_create_quiz:
                    fragmentClass = CreateQuizFragment.class;
                    break;
                case R.id.drawer_best_scores:
                    fragmentClass = ScoreFragment.class;
                    break;
                case R.id.drawer_profile:
                    fragmentClass = ProfileFragment.class;
                    break;
                case R.id.drawer_sign_out:
                    signOut();
                    break;
            }

            if (fragmentClass != null) {
                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                fragmentTransaction.replace(R.id.layoutMainContent, fragment, fragment.getClass().getSimpleName()).commit();
            }
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void goToProfileFragment() {
        onNavigationItemSelected(mNavigationView.getMenu().getItem(PROFILE_INDEX));
    }

    private void signOut() {
        UserController.destroy();
        QuizController.destroy();
        mAuthController.signOut();
        Intent intent = new Intent(MainActivity.this, SignInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_toolbar, menu);
        MenuItem menuItem = menu.findItem(R.id.action_profile);
        View actionView = menuItem.getActionView();
        mImageViewAvatarTopRightCorner = actionView.findViewById(R.id.circleImageViewAvatarTopRightCorner);
        UserModel user = UserController.getInstance().getUser();
        Glide.with(this)
                .load(user.getAvatar())
                .into(mImageViewAvatarTopRightCorner);
        actionView.setOnClickListener(v -> onOptionsItemSelected(menuItem));
        return true;
    }
}