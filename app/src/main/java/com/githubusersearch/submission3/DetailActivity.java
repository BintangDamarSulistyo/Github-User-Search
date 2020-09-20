package com.githubusersearch.submission3;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.githubusersearch.submission3.Adapter.ViewPagerAdapter;
import com.githubusersearch.submission3.Connection.ApiService;
import com.githubusersearch.submission3.Connection.RetrofitConfiguration;
import com.githubusersearch.submission3.Database.DatabaseHelper;
import com.githubusersearch.submission3.Database.UserDatabase;
import com.githubusersearch.submission3.Database.UserHelper;
import com.githubusersearch.submission3.Model.User;
import com.githubusersearch.submission3.Model.UserDetail;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.githubusersearch.submission3.Database.UserDatabase.UserColumns.TABLE_USER_NAME;

public class DetailActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView username,company,location;
    private ImageView avatar;
    private User user;
    private ViewPagerAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private UserHelper userHelper;
    public ArrayList<User> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        progressBar = findViewById(R.id.progressBar);
        init();
        setDataDetailUser();
        setOnClickFavoriteButton();
        userHelper = UserHelper.getInstance(getApplicationContext());
    }

    private void init() {
        adapter = new ViewPagerAdapter(this,getSupportFragmentManager());
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.vp);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(adapter);
        tabLayout.setElevation(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu2, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu1:
                Intent iSetting = new Intent(this, SettingsActivity.class);
                startActivity(iSetting);
                return true;
            default:
                return true;
        }
    }

    private void setDataDetailUser(){
        user = getIntent().getParcelableExtra("DATA_USER");
        if (user != null){
            username = findViewById(R.id.tv_username);
            company = findViewById(R.id.tv_company);
            location = findViewById(R.id.tv_location);
            avatar = findViewById(R.id.image_user_detail);
            progressBar.setVisibility(View.VISIBLE);

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
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<UserDetail> call, Throwable t) {
                    Toast.makeText(DetailActivity.this, "not load data", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void setOnClickFavoriteButton(){
        MaterialFavoriteButton materialFavoriteButton = findViewById(R.id.mfb_favorite);
        if (EXIST(user.getLogin())){
            materialFavoriteButton.setFavorite(true);
            materialFavoriteButton.setOnFavoriteChangeListener(new MaterialFavoriteButton.OnFavoriteChangeListener() {
                @Override
                public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                    if (favorite){
                        userList = userHelper.getDataUser();
                        userHelper.userInsert(user);
                        Toast.makeText(DetailActivity.this, "Success added to favorite", Toast.LENGTH_SHORT).show();
                    }else {
                        userList = userHelper.getDataUser();
                        userHelper.userDelete(String.valueOf(user.getId()));
                        Toast.makeText(DetailActivity.this, "Success delete from favorite", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else {
            materialFavoriteButton.setOnFavoriteChangeListener(new MaterialFavoriteButton.OnFavoriteChangeListener() {
                @Override
                public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                    if (favorite){
                        userList = userHelper.getDataUser();
                        userHelper.userInsert(user);
                        Toast.makeText(DetailActivity.this, "Success added to favorite", Toast.LENGTH_SHORT).show();
                    }else {
                        userList = userHelper.getDataUser();
                        userHelper.userDelete(String.valueOf(user.getId()));
                        Toast.makeText(DetailActivity.this, "Success delete from favorite", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private boolean EXIST(String username){
        String change = UserDatabase.UserColumns.USERNAME + "=?";
        String[] changeArg = {username};
        String limit = "1";
        userHelper = new UserHelper(this);
        userHelper.open();
        user = getIntent().getParcelableExtra("DATA_USER");

        DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = database.query(TABLE_USER_NAME,null,change,changeArg,null,null,null,limit);
        boolean exist = (cursor.getCount() > 0 );
        cursor.close();
        return exist;
    }
}