package com.githubusersearch.githubfavorite.Connection;

import com.githubusersearch.githubfavorite.Model.UserDetail;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    @GET("users/{username}")
    Call<UserDetail> getUserDetail(@Path("username") String username);
}
