package com.example.noushad.blogbee.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.noushad.blogbee.R;
import com.example.noushad.blogbee.fragment.BlogCreationFragment;
import com.example.noushad.blogbee.fragment.BlogViewFragment;
import com.example.noushad.blogbee.fragment.ListFragment;
import com.example.noushad.blogbee.fragment.MyOwnPostFragment;
import com.example.noushad.blogbee.model.ViewModel.UserViewModel;
import com.example.noushad.blogbee.utils.ImageUtils;
import com.example.noushad.blogbee.utils.PaginationAdapterCallback;
import com.example.noushad.blogbee.utils.SharedPrefManager;
import com.example.noushad.blogbee.utils.WebOperations;
import com.vstechlab.easyfonts.EasyFonts;

import java.io.File;


public class FragmentContainerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ListFragment.OnItemSelectedInterface, MyOwnPostFragment.OnItemSelectedInterface, PaginationAdapterCallback {

    private static final int PICK_IMAGE = 1;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private DrawerLayout mDrawerLayout;
    public static final int CREATE_NEW = 1;
    public static final int REPLACE = 2;
    public static final String BLOG_CREATION_FRAGMENT = "blog_creation_fragment";
    public static final String BLOG_VIEW_FRAGMENT = "blog_view_fragment";
    public static final String LIST_FRAGMENT = "list_fragment";
    public static final String RETRIEVE_FRAGMENT = "retrieve fragment";
    public static final String MY_OWN_POST_FRAGMENT = "own post fragment";
    public String CURRENT_FRAGMENT_TAG = LIST_FRAGMENT;
    private ImageView mNavUserProfileImage;
    private File mFile;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
        setContentView(R.layout.activity_fragment_container);
        setNavigationViewListner();
        initializeViews();
        Fragment savedFragment = getSupportFragmentManager().findFragmentByTag(CURRENT_FRAGMENT_TAG);
        if (savedFragment == null)
            startFragment(new ListFragment(), CREATE_NEW);

    }

    private void initializeViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.custom_toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setUserCredentials(View view) {
        UserViewModel user = SharedPrefManager.getInstance(getApplicationContext()).getUser();
        mNavUserProfileImage = (ImageView) view.findViewById(R.id.nav_header_profile_image);
        TextView userName = (TextView) view.findViewById(R.id.navigation_header_user_name);
        userName.setText(user.getName());
        userName.setTypeface(EasyFonts.caviarDreamsBold(this));
        if (user.getCoverPhoto() != null && WebOperations.hasValidPath(user.getCoverPhoto())) {
            WebOperations.loadImage(this, mNavUserProfileImage, user.getCoverPhoto());
        } else {
            mNavUserProfileImage.setImageResource(R.drawable.no_profile_image);
        }
        mNavUserProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chooserIntent = ImageUtils.getChooserIntent();
                startActivityForResult(chooserIntent, PICK_IMAGE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            mFile = ImageUtils.getImageFile(this, data.getData(), mNavUserProfileImage);
            WebOperations.updateUserPhoto(FragmentContainerActivity.this, "PROFILE PICTURE", mFile);
        }
    }

    @Override
    public void onBackPressed() {

        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();

        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void setNavigationViewListner() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.blog_navigation);
        navigationView.setNavigationItemSelectedListener(this);
        final View view = navigationView.getHeaderView(0);
        setUserCredentials(view);
        ImageButton reloadUserButton = (ImageButton) view.findViewById(R.id.navReloadButton);
        reloadUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUserCredentials(view);
            }
        });
    }

    private void startFragment(Fragment fragment, int command) {


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (command == CREATE_NEW)
            fragmentTransaction.add(R.id.placeHolder, fragment, CURRENT_FRAGMENT_TAG);
        else {
            fragmentTransaction.replace(R.id.placeHolder, fragment, CURRENT_FRAGMENT_TAG).addToBackStack(null);
        }
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                CURRENT_FRAGMENT_TAG = LIST_FRAGMENT;
                startFragment(new ListFragment(), REPLACE);
                break;
            case R.id.my_account:

                startActivity(new Intent(this, UserProfileActivity.class));
                break;
            case R.id.write_blog:
                CURRENT_FRAGMENT_TAG = BLOG_CREATION_FRAGMENT;
                startFragment(new BlogCreationFragment(), REPLACE);
                break;
            case R.id.my_posts:
                CURRENT_FRAGMENT_TAG = MY_OWN_POST_FRAGMENT;
                startFragment(new MyOwnPostFragment(), REPLACE);
                break;
            case R.id.favourites:
            case R.id.bookmarks:
            case R.id.settings:
            case R.id.action_logout:
                Toast.makeText(getApplicationContext(), "Logging Out...", Toast.LENGTH_SHORT).show();
                logout();
                break;
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logout() {
        SharedPrefManager.getInstance(this).logout();
        finish();
        startActivity(new Intent(this, LoginActivity.class));
    }

    @Override
    public void onListBlogSelected(int index) {

        BlogViewFragment blogViewFragment = BlogViewFragment.newInstance(index);
        startFragment(blogViewFragment, REPLACE);

    }

    @Override
    public void retryPageLoad() {
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
