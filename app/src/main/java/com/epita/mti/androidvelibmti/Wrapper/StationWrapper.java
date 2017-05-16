package com.epita.mti.androidvelibmti.Wrapper;

import com.epita.mti.androidvelibmti.DBO.Station;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by hadri on 16/05/2017.
 */

public class StationWrapper implements Serializable {

    private ArrayList<Station> stations;

    public StationWrapper(ArrayList<Station> data) {
        this.stations = data;
    }

    public ArrayList<Station> getstations() {
        return this.stations;
    }
}
