package com.epita.mti.androidvelibmti;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.epita.mti.androidvelibmti.Authors.AuthorsActivity;
import com.epita.mti.androidvelibmti.PageViewer.ViewPagerActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private VelibAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<String> strings = new ArrayList<String>();

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

        Call<List<Station>> repoList = velibService.listStation();
        repoList.enqueue(new Callback<List<Station>>() {
            @Override
            public void onResponse(Call<List<Station>> call, Response<List<Station>> response) {
                if(response.isSuccessful()) {
                    List<Station> repoList = response.body();

                    for (Station tuto : repoList) {
                        strings.add(tuto.getAuteur());
                    }
                    mAdapter.notifyDataSetChanged();
                } else {
                    //Error
                }
            }
            @Override
            public void onFailure(Call<List<Station>> call, Throwable t) {
            }
        });
    }

    private VelibService buildService()
    {
        Retrofit retrofit =
                new Retrofit.Builder().baseUrl(VelibService.ENDPOINT).
                        addConverterFactory(GsonConverterFactory.create()).build();
        VelibService velibService = retrofit.create(VelibService.class);
        return velibService;
    }

    public void showAuthors(View view)
    {
        Intent intent = new Intent(MainActivity.this, AuthorsActivity.class);
        startActivity(intent);
    }
}
