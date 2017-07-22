package com.example.noushad.blogbee.view;


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

import com.example.noushad.blogbee.R;


public class BlogsFeedActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ListFragment.OnItemSelectedInterface {

    private static final String TAG = "BlogsFeedActivity";
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private DrawerLayout mDrawerLayout;
    public static final int CREATE_NEW = 1;
    public static final int REPLACE = 2;
    public static final String BLOG_CREATION_FRAGMENT = "blog_creation_fragment";
    public static final String BLOG_VIEW_FRAGMENT = "blog_view_fragment";
    public static final String LIST_FRAGMENT = "list_fragment";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blogs_feed);
        setNavigationViewListner();

        Toolbar toolbar = (Toolbar) findViewById(R.id.custom_toolbar);
        setSupportActionBar(toolbar);


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Fragment savedFragment = getSupportFragmentManager().findFragmentByTag(LIST_FRAGMENT);

        if (savedFragment == null)
            callFragment(new ListFragment(), LIST_FRAGMENT, CREATE_NEW);


    }

    private void setNavigationViewListner() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.blog_navigation);
        navigationView.setNavigationItemSelectedListener(this);

    }

    private void callFragment(Fragment fragment, String TAG, int command) {


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (command == CREATE_NEW)
            fragmentTransaction.add(R.id.placeHolder, fragment, TAG);
        else {
            fragmentTransaction.replace(R.id.placeHolder, fragment, TAG).addToBackStack(null);
        }
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
                callFragment(new ListFragment(), LIST_FRAGMENT, REPLACE);
                break;
            case R.id.my_account:
                //account mangement activity;
                break;
            case R.id.write_blog:
                callFragment(new BlogCreationFragment(), BLOG_CREATION_FRAGMENT, REPLACE);
                break;
            case R.id.my_posts:
            case R.id.favourites:
            case R.id.bookmarks:
            case R.id.settings:
            case R.id.action_logout:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;


        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onListBlogSelected(int index) {

        BlogViewFragment blogViewFragment = new BlogViewFragment();

        callFragment(blogViewFragment, BLOG_VIEW_FRAGMENT, REPLACE);

    }
}
