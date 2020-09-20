package com.githubusersearch.submission3;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.githubusersearch.submission3.Adapter.AdapterFavorite;
import com.githubusersearch.submission3.Database.UserHelper;
import com.githubusersearch.submission3.Model.User;

import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity {

    private UserHelper userHelper;
    private ArrayList<User> userGithubList =  new ArrayList<>();
    private AdapterFavorite adapterUserFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        userHelper = new UserHelper(getApplicationContext());
        userHelper.open();
        userGithubList = userHelper.getDataUser();
        setRecyclerView();
    }

    private void setRecyclerView(){
        RecyclerView rv = findViewById(R.id.rv_user);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setHasFixedSize(true);
        adapterUserFavorite = new AdapterFavorite(getApplicationContext());
        rv.setAdapter(adapterUserFavorite);
    }

    @Override
    protected void onResume() {
        super.onResume();
        userGithubList = userHelper.getDataUser();
        adapterUserFavorite.setUserGithubList(userGithubList);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userHelper.close();
    }
}