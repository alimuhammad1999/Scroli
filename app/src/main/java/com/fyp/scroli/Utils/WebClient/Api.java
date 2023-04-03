package com.fyp.scroli.Utils.WebClient;

import com.fyp.scroli.Data.Models.AddedUser;
import com.fyp.scroli.Data.Models.Person;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {
    String PERSON_BASE_URL = "https://mocki.io/v1/";    //"https://simplifiedcoding.net/demos/";
    String USER_BASE_URL = "https://reqres.in/api/";

    @GET("d555dfbc-9dde-4a34-be29-356cb8b9b71f")   //"marvel")
    Call<List<Person>> getPersonWebResults();

    @GET("users")
    Call<JsonObject> getUserWebResults(@Query("page") String page );

    @POST("useers")
    Call<AddedUser> postUserWebResult(@Body AddedUser user);

}