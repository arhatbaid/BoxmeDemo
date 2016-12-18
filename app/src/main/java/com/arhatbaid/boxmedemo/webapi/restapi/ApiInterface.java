package com.arhatbaid.boxmedemo.webapi.restapi;


import com.arhatbaid.boxmedemo.webapi.model.RepoData;
import com.arhatbaid.boxmedemo.webapi.model.UserData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by babu on 12/17/2016.
 */

public interface ApiInterface {

    @GET("users/{username}")
    Call<UserData> getUserDetails(@Path("username") String username);

    @GET("users/{username}/repos")
    Call<ArrayList<RepoData>> getReposDetails(@Path("username") String username);
}
