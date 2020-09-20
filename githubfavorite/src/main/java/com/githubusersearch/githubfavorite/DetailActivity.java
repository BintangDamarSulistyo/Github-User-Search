package com.githubusersearch.githubfavorite;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.githubusersearch.githubfavorite.Connection.ApiService;
import com.githubusersearch.githubfavorite.Connection.RetrofitConfiguration;
import com.githubusersearch.githubfavorite.Model.User;
import com.githubusersearch.githubfavorite.Model.UserDetail;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    private TextView username,company,location;
    private ImageView avatar;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        setDataDetailUser();

    }

    private void setDataDetailUser(){
        user = getIntent().getParcelableExtra("DATA_USER");
        if (user != null){
            username = findViewById(R.id.tv_username);
            company = findViewById(R.id.tv_company);
            location = findViewById(R.id.tv_location);
            avatar = findViewById(R.id.image_user_detail);

            ApiService apiService = RetrofitConfiguration.getRetrofit().create(ApiService.class);
            Call<UserDetail> call = apiService.getUserDetail(user.getLogin());
            call.enqueue(new Callback<UserDetail>() {
                @Override
                public void onResponse(Call<UserDetail> call, Response<UserDetail> response) {
                    if (response.body() != null){
                        username.setText(response.body().getName());
                        company.setText(response.body().getCompany());
                        location.setText(response.body().getLocation());
                        Glide.with(getApplicationContext())
                                .load(response.body().getAvatarUrl())
                                .into(avatar);
                        username.setVisibility(View.VISIBLE);
                        company.setVisibility(View.VISIBLE);
                        location.setVisibility(View.VISIBLE);
                        avatar.setVisibility(View.VISIBLE);
                    }
                }
                @Override
                public void onFailure(Call<UserDetail> call, Throwable t) {
                    Toast.makeText(DetailActivity.this, "not load data", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}