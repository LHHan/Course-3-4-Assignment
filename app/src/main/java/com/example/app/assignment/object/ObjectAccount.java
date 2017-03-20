package com.example.app.assignment.object;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by LeHoangHan on 3/16/2017.
 */

public class ObjectAccount  implements Serializable {

    @SerializedName("access_token")
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
