package com.example.app.assignment;

import android.widget.EditText;
import android.widget.Toast;

import com.example.app.assignment.object.ObjectAccount;
import com.example.app.assignment.object.ObjectFullData;
import com.google.gson.Gson;

import org.androidannotations.annotations.EBean;

import java.security.Signature;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.androidannotations.annotations.EBean.Scope.Singleton;

@EBean(scope = Singleton)
public class DataService {
    private Retrofit retrofit;
    private RestServiceInterface restServiceInterface;
    private ObjectAccount objectAccount = new ObjectAccount();
    private ObjectFullData objectFullData = new ObjectFullData();

    public DataService() {

        retrofit = new Retrofit.Builder().baseUrl("http://ibss.io:5599/")
                .addConverterFactory(GsonConverterFactory.create(new Gson())).build();
        restServiceInterface = retrofit.create(RestServiceInterface.class);
    }

    void login(final String grant_type, final String username, final String password,
               final LoginSuccessListener loginSuccessListener) {

        restServiceInterface.login(grant_type, username, password).enqueue(new Callback<ObjectAccount>() {
            @Override
            public void onResponse(Call<ObjectAccount> call, Response<ObjectAccount> response) {
                objectAccount = response.body();
                if (loginSuccessListener != null) {
                    loginSuccessListener.onSuccess(objectAccount);
                }
            }

            @Override
            public void onFailure(Call<ObjectAccount> call, Throwable throwable) {

            }
        });
    }

    void loadData(final String header, final LoadDataSuccessListener loadDataSuccessListener) {
        restServiceInterface.loadData("Bearer " + header).enqueue(new Callback<ObjectFullData>() {
            @Override
            public void onResponse(Call<ObjectFullData> call, Response<ObjectFullData> response) {
                objectFullData = response.body();
                if (loadDataSuccessListener != null)
                    loadDataSuccessListener.onSuccess(objectFullData);
            }

            @Override
            public void onFailure(Call<ObjectFullData> call, Throwable t) {
            }
        });
    }

}
