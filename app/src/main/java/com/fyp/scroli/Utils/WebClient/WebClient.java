package com.fyp.scroli.Utils.WebClient;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebClient {

    private static WebClient instance = null;
    private Api myApi;


    private WebClient(String url) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        myApi = retrofit.create(Api.class);
    }

    public static synchronized WebClient getInstance(String url) {
        /*if (instance == null) {
            instance = new WebClient(url);
        }*/
        instance = new WebClient(url);
        return instance;
    }

    public Api getMyApi() {
        return myApi;
    }
}

