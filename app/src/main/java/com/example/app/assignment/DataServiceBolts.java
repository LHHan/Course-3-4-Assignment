package com.example.app.assignment;

import com.example.app.assignment.object.ObjectAccount;
import com.example.app.assignment.object.ObjectFullData;
import com.google.gson.Gson;

import org.androidannotations.annotations.EBean;

import bolts.Continuation;
import bolts.Task;
import bolts.TaskCompletionSource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.androidannotations.annotations.EBean.Scope.Singleton;

/**
 * Created by LeHoangHan on 3/27/2017.
 */

@EBean(scope = Singleton)
public class DataServiceBolts {
    private Retrofit retrofit;

    private RestServiceInterface restServiceInterface;

    private ObjectAccount objectAccount;

    private ObjectFullData objectFullData;

    private TaskCompletionSource<ObjectAccount> taskToken;

    private TaskCompletionSource<ObjectFullData> taskData;

    public DataServiceBolts() {
        objectAccount = new ObjectAccount();
        objectFullData = new ObjectFullData();
        taskToken = new TaskCompletionSource<>();
        taskData = new TaskCompletionSource<>();
        retrofit = new Retrofit.Builder().baseUrl("http://ibss.io:5599/")
                .addConverterFactory(GsonConverterFactory.create(new Gson())).build();
        restServiceInterface = retrofit.create(RestServiceInterface.class);
    }

    public ObjectAccount getAccount() {
        return objectAccount;
    }

    public ObjectFullData getListData() {
        return objectFullData;
    }

    public Task<ObjectAccount> login(final String grant_type, final String username, final String password) {

        restServiceInterface.login(grant_type, username, password).enqueue(new Callback<ObjectAccount>() {
            @Override
            public void onResponse(Call<ObjectAccount> call, Response<ObjectAccount> response) {
                taskToken.setResult(response.body());
            }

            @Override
            public void onFailure(Call<ObjectAccount> call, Throwable t) {
                taskToken.setError((Exception) t);
            }
        });

        return taskToken.getTask();
    }

    public Task<ObjectFullData> loadData(final String grant_type, final String username, final String password) {
        login(grant_type, username, password).continueWithTask(new Continuation<ObjectAccount, Task<ObjectAccount>>() {
            @Override
            public Task<ObjectAccount> then(Task<ObjectAccount> task) throws Exception {
                restServiceInterface.loadData("Bearer " + task.getResult().getAccessToken()).enqueue(new Callback<ObjectFullData>() {
                    @Override
                    public void onResponse(Call<ObjectFullData> call, Response<ObjectFullData> response) {
                        taskData.setResult(response.body());
                    }

                    @Override
                    public void onFailure(Call<ObjectFullData> call, Throwable t) {
                        taskData.setError((Exception) t);
                    }
                });
                return null;
            }
        });

        return taskData.getTask();
    }

}
