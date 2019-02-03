package com.i550.countriescatalogue;


import com.i550.countriescatalogue.Model.Country;

import retrofit2.http.GET;
import retrofit2.Call;

import java.util.ArrayList;


public interface ServerApi {

    @GET("all")
    Call<ArrayList<Country>> callAllData();

}


