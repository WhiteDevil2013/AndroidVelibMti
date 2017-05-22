package com.epita.mti.androidvelibmti;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.epita.mti.androidvelibmti.Adapter.VelibAdapter;
import com.epita.mti.androidvelibmti.Authors.AuthorsActivity;
import com.epita.mti.androidvelibmti.DBO.Station;
import com.epita.mti.androidvelibmti.DBO.StationInfo;
import com.epita.mti.androidvelibmti.DBO.VelibObject;
import com.epita.mti.androidvelibmti.WS.VelibService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private VelibAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Station> stations = new ArrayList<Station>();
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SwipeRefreshLayout mSwipeRefreshLayoutOnError;

    Toolbar toolbar;
    RelativeLayout progressBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolBar();

        final VelibService velibService = buildService();

        mSwipeRefreshLayoutOnError = (SwipeRefreshLayout) findViewById(R.id.refreshOnNoBikeMessage);
        mSwipeRefreshLayoutOnError.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                stations = new ArrayList<Station>();
                mSwipeRefreshLayoutOnError.setRefreshing(true);
                refreshContent(velibService);
                mSwipeRefreshLayoutOnError.setRefreshing(false);
            }
        });
        mSwipeRefreshLayoutOnError.setVisibility(View.GONE);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                stations = new ArrayList<Station>();
                mSwipeRefreshLayout.setRefreshing(true);
                refreshContent(velibService);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressBarLayout = (RelativeLayout) findViewById(R.id.progress_barLayout);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL) {
        });
        recyclerView.setHasFixedSize(true);

        refreshContent(velibService);
    }


    public void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    mAdapter.filter(query);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    mAdapter.filter(newText);
                    return true;
                }
            });
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.authors:
                showAuthors();
                break;
        }
        return true;
    }

    private VelibService buildService() {
        Retrofit retrofit =
                new Retrofit.Builder().baseUrl(VelibService.ENDPOINT).
                        addConverterFactory(GsonConverterFactory.create()).build();
        VelibService velibService = retrofit.create(VelibService.class);
        return velibService;
    }

    public void showAuthors() {
        Intent intent = new Intent(MainActivity.this, AuthorsActivity.class);
        startActivity(intent);
    }

    private void refreshContent(final VelibService velibService) {
        mSwipeRefreshLayoutOnError.setVisibility(View.GONE);
        progressBarLayout.setVisibility(View.VISIBLE);
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (!(networkInfo != null && networkInfo.isConnected())) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Warning");
            alertDialog.setMessage(getString(R.string.ConnectionAlert));
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            progressBarLayout.setVisibility(View.GONE);
                            mSwipeRefreshLayoutOnError.setVisibility(View.VISIBLE);
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
            return;
        }
        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                mAdapter = new VelibAdapter(stations, MainActivity.this);
                recyclerView.setAdapter(mAdapter);
                Call<VelibObject> repoList = velibService.listStation();
                repoList.enqueue(new Callback<VelibObject>() {
                    @Override
                    public void onResponse(Call<VelibObject> call, Response<VelibObject> response) {
                        if (response.isSuccessful()) {
                            VelibObject stationList = response.body();

                            for (StationInfo station : stationList.getRecords()) {
                                stations.add(station.getFields());
                            }
                            mAdapter.notifyDataSetChanged();
                            progressBarLayout.setVisibility(View.GONE);
                        } else {
                            progressBarLayout.setVisibility(View.GONE);
                            mSwipeRefreshLayoutOnError.setVisibility(View.VISIBLE);
                        }
                    }
                    @Override
                    public void onFailure(Call<VelibObject> call, Throwable t) {
                        progressBarLayout.setVisibility(View.GONE);
                        mSwipeRefreshLayoutOnError.setVisibility(View.VISIBLE);
                    }
                });
            }
        }, 0);
    }
}
