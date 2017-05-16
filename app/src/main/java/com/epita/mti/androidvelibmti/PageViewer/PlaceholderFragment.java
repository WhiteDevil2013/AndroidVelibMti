package com.epita.mti.androidvelibmti.PageViewer;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.epita.mti.androidvelibmti.DBO.Station;
import com.epita.mti.androidvelibmti.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TimeZone;

/**
 * Created by hadri on 13/05/2017.
 */

public class PlaceholderFragment extends Fragment {
    public static final String ARG_PAGE = "STATION_ID";
    private Station mStation;

    TextView mTextViewName;
    TextView mTextViewLocation;
    TextView mTextViewUpdateDate;
    TextView mTextViewBikeTotal;
    TextView mTextViewBikeAvailable;
    TextView mTextViewStatus;

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

        mTextViewName = (TextView)rootView.findViewById(R.id.section_label);
        mTextViewLocation = (TextView)rootView.findViewById(R.id.StationLocationText);
        mTextViewUpdateDate = (TextView)rootView.findViewById(R.id.LastUpdateText);
        mTextViewBikeTotal = (TextView)rootView.findViewById(R.id.bikeStatsTextTotal);
        mTextViewBikeAvailable = (TextView)rootView.findViewById(R.id.bikeStatsTextAvailable);
        mTextViewStatus = (TextView)rootView.findViewById(R.id.Status);

        mTextViewName.setText(String.valueOf(mStation.getName()));

        if (mStation.getStatus().equals("CLOSED")) {
            mTextViewStatus.setText("FERMER");
            mTextViewStatus.setTextColor(Color.RED);
        }
        else {
            mTextViewStatus.setText("OUVERT");
            mTextViewStatus.setTextColor(Color.GREEN);
        }

        mTextViewLocation.setText(String.valueOf(mStation.getAddress()));


        try {
            String dateFormat = String.valueOf(mStation.getLast_update());
            int ind = dateFormat.lastIndexOf(":");
            if(ind >= 0) {
                dateFormat = new StringBuilder(dateFormat).replace(ind, ind + 1, "").toString();
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssz");
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            long time = 0;
            time = sdf.parse(String.valueOf(dateFormat)).getTime();
            long now = System.currentTimeMillis();
            CharSequence ago =
                    DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS);
            mTextViewUpdateDate.setText(ago);
        } catch (ParseException e) {
            e.printStackTrace();
            mTextViewUpdateDate.setText(String.valueOf(mStation.getLast_update()));
        }




        mTextViewBikeAvailable.setText(String.valueOf(mStation.getAvailable_bike_stands()));
        if (mStation.getAvailable_bike_stands() <= 0) {
            mTextViewBikeAvailable.setTextColor(Color.RED);
        }
        else {
            mTextViewBikeAvailable.setTextColor(Color.GREEN);
        }
        String TotalBikeText = " / " + String.valueOf(mStation.getBike_stands());
        mTextViewBikeTotal.setText(TotalBikeText);


        return rootView;
    }

}
