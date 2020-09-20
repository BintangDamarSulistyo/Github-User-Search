package com.githubusersearch.submission3;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.githubusersearch.submission3.Adapter.AdapterSearch;
import com.githubusersearch.submission3.Adapter.AdapterUser;
import com.githubusersearch.submission3.Connection.ApiService;
import com.githubusersearch.submission3.Connection.RetrofitConfiguration;
import com.githubusersearch.submission3.Model.Search;
import com.githubusersearch.submission3.Model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        initViews();
        getDataUser();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu1, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void initViews() {
        recyclerView = findViewById(R.id.rv_user);
        searchView = findViewById(R.id.sv_user);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getSearchUser(newText);
                return false;
            }
        });
    }

    private void getSearchUser(String username){
        progressBar.setVisibility(View.VISIBLE);
        ApiService apiService = RetrofitConfiguration.getRetrofit().create(ApiService.class);
        Call<Search> call = apiService.getGithubSearch(username);
        call.enqueue(new Callback<Search>() {
            @Override
            public void onResponse(Call<Search> call, Response<Search> response) {
                progressBar.setVisibility(View.INVISIBLE);

                if (response.body() != null){
                    AdapterSearch adapterSearch = new AdapterSearch(response.body().getItems());
                    recyclerView.setAdapter(adapterSearch);
                }
            }

            @Override
            public void onFailure(Call<Search> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Could not load data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu2:
                Intent iFavorite = new Intent(this, FavoriteActivity.class);
                startActivity(iFavorite);
                return true;
            case R.id.menu1:
                Intent iSetting = new Intent(this, SettingsActivity.class);
                startActivity(iSetting);
                return true;
            default:
                return true;
        }
    }

    private void getDataUser(){
        ApiService apiService = RetrofitConfiguration.getRetrofit().create(ApiService.class);
        Call<List<User>> call = apiService.getGithubUser("https://api.github.com");
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {

                if (response.body() != null){
                    progressBar.setVisibility(View.INVISIBLE);
                    AdapterUser adapterUser = new AdapterUser(getApplicationContext(),response.body());
                    recyclerView.setAdapter(adapterUser);
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "cannot load data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}