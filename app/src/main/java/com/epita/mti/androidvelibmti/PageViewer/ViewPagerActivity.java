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
import com.epita.mti.androidvelibmti.DBO.Station;
import com.epita.mti.androidvelibmti.R;
import com.epita.mti.androidvelibmti.Wrapper.StationWrapper;

import java.util.ArrayList;

public class ViewPagerActivity extends AppCompatActivity {


    private int NUM_PAGES = 5;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;


    private String name;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pager);

        // INIT TOOLBAR
        initToolBar();

        // RETREIVE DATAS
        StationWrapper dw = (StationWrapper) getIntent().getSerializableExtra("DATA");
        ArrayList<Station> stations = dw.getstations();
        this.NUM_PAGES = stations.size();
        int CurrentPosition = getIntent().getIntExtra("POSITION", 0);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.container);
        mPager.setPageTransformer(true, new ZoomOutPageTransformer());
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), stations);
        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(CurrentPosition);
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
                shareIntent();
                break;
        }
        return true;
    }

    public void shareIntent() {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/*");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, name);
        startActivity(Intent.createChooser(sharingIntent, "Share using"));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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

        private ArrayList<Station> stations;

        public ScreenSlidePagerAdapter(FragmentManager fm, ArrayList<Station> stations)
        {
            super(fm);
            this.stations = stations;
        }

        @Override
        public Fragment getItem(int position) {
            name = stations.get(position).getName();
            return PlaceholderFragment.create(stations.get(position));
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
