package com.epita.mti.androidvelibmti.DBO;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by William on 12/05/2017.
 */

public class Station implements Serializable {
    private final String status;
    private final String contract_name;
    private final String name;
    private final String bonus;
    private final Integer bike_stands;
    private final Integer number;
    private final String last_update;
    private final Integer available_bike_stands;
    private final String banking;
    private final Integer available_bikes;
    private final String address;
    private final ArrayList<Double> position;

    public Station(String status, String contract_name, String name, String bonus,
                   Integer bike_stands, Integer number, String last_update,
                   Integer available_bike_stands, String banking, Integer available_bikes,
                   String address, ArrayList<Double> position) {
        this.status = status;
        this.contract_name = contract_name;
        this.name = name;
        this.bonus = bonus;
        this.bike_stands = bike_stands;
        this.number = number;
        this.last_update = last_update;
        this.available_bike_stands = available_bike_stands;
        this.banking = banking;
        this.available_bikes = available_bikes;
        this.address = address;
        this.position = position;
    }

    public String getStatus() {
        return status;
    }

    public String getContract_name() {
        return contract_name;
    }

    public String getName() {
        return name;
    }

    public String getBonus() {
        return bonus;
    }

    public Integer getBike_stands() {
        return bike_stands;
    }

    public Integer getNumber() {
        return number;
    }

    public String getLast_update() {
        return last_update;
    }

    public Integer getAvailable_bike_stands() {
        return available_bike_stands;
    }

    public String getBanking() {
        return banking;
    }

    public Integer getAvailable_bikes() {
        return available_bikes;
    }

    public String getAddress() {
        return address;
    }

    public ArrayList<Double> getPosition() {
        return position;
    }
}
