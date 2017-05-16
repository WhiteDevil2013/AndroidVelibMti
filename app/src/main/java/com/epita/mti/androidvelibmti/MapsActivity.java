package com.epita.mti.androidvelibmti;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.epita.mti.androidvelibmti.Authors.AuthorsActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends AppCompatActivity
        implements OnMapReadyCallback,ShareActionProvider.OnShareTargetSelectedListener {

    private GoogleMap mMap;

    private Double lat;
    private Double log;
    private String name;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        // PARIS BY DEFAULT
        lat = getIntent().getDoubleExtra("LATITUDE", 48.8566);
        log = getIntent().getDoubleExtra("LONGITUDE", 2.3522);
        name = getIntent().getStringExtra("NAME");

        //INIT TOOLBAR
        initToolBar();


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public boolean onShareTargetSelected(ShareActionProvider source,
                                         Intent intent) {
        Toast.makeText(this, intent.getComponent().toString(),
                Toast.LENGTH_LONG).show();

        return(false);
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

    public void showAuthors()
    {
        Intent intent = new Intent(MapsActivity.this, AuthorsActivity.class);
        startActivity(intent);
    }

    public void shareIntent() {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/*");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, name);
        startActivity(Intent.createChooser(sharingIntent, "Share using"));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in position and move the camera
        LatLng station = new LatLng(lat, log);
        mMap.addMarker(new MarkerOptions().position(station).title(name));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(station));
        mMap.animateCamera( CameraUpdateFactory.zoomTo( 10.0f ) );
    }
}
