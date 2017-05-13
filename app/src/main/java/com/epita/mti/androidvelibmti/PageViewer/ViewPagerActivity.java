package com.epita.mti.androidvelibmti.PageViewer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.epita.mti.androidvelibmti.Authors.AuthorsActivity;
import com.epita.mti.androidvelibmti.MainActivity;
import com.epita.mti.androidvelibmti.R;

/**
 * Created by hadri on 13/05/2017.
 */

public class ViewPagerActivity extends AppCompatActivity {


    private static final int NUM_PAGES = 5;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pager);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.container);
        mPager.setPageTransformer(true, new ZoomOutPageTransformer());
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        initToolBar();
    }

    public void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.authors:
                showAuthors();
                break;
            case R.id.share:
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    public void showAuthors()
    {
        Intent intent = new Intent(ViewPagerActivity.this, AuthorsActivity.class);
        startActivity(intent);
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.create(position);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
