package com.mirzayogy.psbskatel.Retrofit;

import retrofit.RestAdapter;

/**
 * Created by AbhiAndroid
 */
public class Api {

    public static ApiInterface getClient() {

        // change your base URL
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint("http://10.0.2.2/") //Set the Root URL
                .build(); //Finally building the adapter

        //Creating object for our interface
        ApiInterface api = adapter.create(ApiInterface.class);
        return api;
    }
}
