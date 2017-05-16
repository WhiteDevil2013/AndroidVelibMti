package com.epita.mti.androidvelibmti.PageViewer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.epita.mti.androidvelibmti.DBO.Station;
import com.epita.mti.androidvelibmti.R;

import java.util.ArrayList;

/**
 * Created by hadri on 13/05/2017.
 */

public class PlaceholderFragment extends Fragment {
    public static final String ARG_PAGE = "STATION_ID";
    private Station mStation;

    TextView mTextView;

    public static PlaceholderFragment create(Station station) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PAGE, station);
        fragment.setArguments(args);
        return fragment;
    }

    public PlaceholderFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStation = (Station) getArguments().getSerializable(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_main, container, false);


        mTextView = (TextView)rootView.findViewById(R.id.section_label);

        mTextView.setText(String.valueOf(mStation.getName()));
        return rootView;
    }

}
