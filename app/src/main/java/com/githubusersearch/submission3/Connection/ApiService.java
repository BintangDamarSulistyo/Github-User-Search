package com.githubusersearch.submission3.Connection;

import com.githubusersearch.submission3.Model.Search;
import com.githubusersearch.submission3.Model.User;
import com.githubusersearch.submission3.Model.UserDetail;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("users/{username}")
    Call<List<User>> getGithubUser(@Header("Authentication") String url);

    @GET("users/{username}")
    Call<UserDetail> getUserDetail(@Path("username") String username);

    @GET("users/{username}/followers")
    Call<List<User>> getUserFollowers(@Path("username") String username);

    @GET("users/{username}/following")
    Call<List<User>> getUserFollowing(@Path("username") String username);

    @GET("search/users")
    Call<Search> getGithubSearch(
            @Query("q") String username
    );
}
