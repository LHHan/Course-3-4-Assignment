package com.example.app.assignment;

import com.example.app.assignment.object.ObjectAccount;
import com.example.app.assignment.object.ObjectFullData;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by LeHoangHan on 3/16/2017.
 */

public interface RestServiceInterface {
    @FormUrlEncoded
    @POST("/api/token")
    Call<ObjectAccount> login(
            @Field("grant_type") String grant_type,
            @Field("username") String username,
            @Field("password") String password);

    @Headers("Accept: application/vnd.app.atoms.mobile-v1+json")
    @GET("/api/stories")
    Call<ObjectFullData> loadData(@Header("Authorization") String bearerToken);
}
