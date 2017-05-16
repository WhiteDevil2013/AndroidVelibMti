package com.epita.mti.androidvelibmti;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.epita.mti.androidvelibmti.Adapter.VelibAdapter;
import com.epita.mti.androidvelibmti.Authors.AuthorsActivity;
import com.epita.mti.androidvelibmti.DBO.Station;
import com.epita.mti.androidvelibmti.DBO.StationInfo;
import com.epita.mti.androidvelibmti.DBO.VelibObject;

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
    private ArrayList<Station> strings = new ArrayList<Station>();

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        VelibService velibService = buildService();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new VelibAdapter(strings);
        recyclerView.setAdapter(mAdapter);

        Call<VelibObject> repoList = velibService.listStation();
        repoList.enqueue(new Callback<VelibObject>() {
            @Override
            public void onResponse(Call<VelibObject> call, Response<VelibObject> response) {
                if(response.isSuccessful()) {
                    VelibObject repoList = response.body();

                    for (StationInfo tuto : repoList.getRecords()) {
                        strings.add(tuto.getFields());
                    }
                    mAdapter.notifyDataSetChanged();
                } else {
                }
            }
            @Override
            public void onFailure(Call<VelibObject> call, Throwable t) {
            }
        });


        initToolBar();
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
            case R.id.mapViewer:
                showMap();
                break;
        }
        return true;
    }

    private VelibService buildService()
    {
        Retrofit retrofit =
                new Retrofit.Builder().baseUrl(VelibService.ENDPOINT).
                        addConverterFactory(GsonConverterFactory.create()).build();
        VelibService velibService = retrofit.create(VelibService.class);
        return velibService;
    }

    public void showAuthors()
    {
        Intent intent = new Intent(MainActivity.this, AuthorsActivity.class);
        startActivity(intent);
    }

    public void showMap() {
        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
        startActivity(intent);
    }

}
