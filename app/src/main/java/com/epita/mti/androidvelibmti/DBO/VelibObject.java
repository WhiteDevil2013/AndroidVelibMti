package com.epita.mti.androidvelibmti.DBO;

import java.util.List;

public class VelibObject {
    private final List<StationInfo> records;

    public VelibObject(List<StationInfo> records) {
        this.records = records;
    }

    public List<StationInfo> getRecords() {
        return records;
    }
}
