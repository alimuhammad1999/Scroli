package com.fyp.scroli.Utils.WebClient;

import com.fyp.scroli.Data.Person;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {
    String BASE_URL = "https://mocki.io/v1/";    //"https://simplifiedcoding.net/demos/";

    @GET("d555dfbc-9dde-4a34-be29-356cb8b9b71f")   //"marvel")
    Call<List<Person>> getWebResults();


}