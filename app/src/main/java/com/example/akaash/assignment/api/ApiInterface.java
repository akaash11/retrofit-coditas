package com.example.akaash.assignment.api;

import com.example.akaash.assignment.model.User;
import com.example.akaash.assignment.model.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by akaash on 12/1/18.
 */

public interface ApiInterface {
    @GET("users")
    Call<List<User>> getGitHubUsers();

    @GET("/search/users")
    Call<UserResponse> getSearchResult(@Query("q") String keyword);

}
