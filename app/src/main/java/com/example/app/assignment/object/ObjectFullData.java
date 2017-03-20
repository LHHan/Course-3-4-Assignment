package com.example.app.assignment.object;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by LeHoangHan on 3/16/2017.
 */

public class ObjectFullData {

    @SerializedName("data")
    private ArrayList<ObjectData> data;

    public ArrayList<ObjectData> getData() {
        return data;
    }

    public void setData(ArrayList<ObjectData> data) {
        this.data = data;
    }
}
