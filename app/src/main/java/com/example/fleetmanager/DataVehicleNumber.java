package com.example.fleetmanager;

import java.io.Serializable;
/*
 * code by gurman and Rohit*/
public class DataVehicleNumber implements Serializable {
    private String number;
    public DataVehicleNumber(){}
    public DataVehicleNumber(String name) {
        this.number = name;
    }
    public String getName() {
        return number;
    }
    public void setName(String name) {
        this.number = name;
    }
}