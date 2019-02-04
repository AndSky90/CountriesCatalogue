package com.i550.countriescatalogue.Model;

import android.util.Log;

import com.i550.countriescatalogue.App;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.i550.countriescatalogue.MainActivity.LOGGING_TAG;

public class Repository {

    public static void loadDataFromApi() {

        App.getRetrofitApi().callAllData().enqueue(new Callback<ArrayList<Country>>() {
            @Override
            public void onResponse(Call<ArrayList<Country>> call, Response<ArrayList<Country>> response) {
                if (response.isSuccessful()) {
                    Log.d(LOGGING_TAG, " onResponse response.body(): " + response.body());

                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(new ArrayList<>(response.body()));
                    realm.commitTransaction();
                    realm.close();

                } else {
                    int statusCode = response.code();
                    try {
                        Log.e(LOGGING_TAG, "statusCode: " + statusCode + "onResponse response.errorBody(): " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Country>> call, Throwable t) {
                Log.e(LOGGING_TAG, " onFailure: " + call);
            }
        });
    }
}
