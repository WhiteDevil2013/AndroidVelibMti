package com.epita.mti.androidvelibmti;

import java.util.List;

/**
 * Created by hadri on 15/05/2017.
 */

public class VelibObject {
    private final List<StationInfo> records;

    public VelibObject(List<StationInfo> records) {
        this.records = records;
    }

    public List<StationInfo> getRecords() {
        return records;
    }
}
