package com.example.pc.energywatch;

/**
 * Created by PC on 30/03/2018.
 */

public class data_struct {
    public String full;
    public Integer hour;
    public Integer min;
    public Integer sec;

    public data_struct() {
    }

    public data_struct(String full, Integer hour, Integer min, Integer sec) {
        this.full = full;
        this.hour = hour;
        this.min = min;
        this.sec = sec;
    }
}
