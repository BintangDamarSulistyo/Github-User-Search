package com.githubusersearch.githubfavorite;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.githubusersearch.githubfavorite.Adapter.AdapterFavorite;

import static com.githubusersearch.githubfavorite.Database.UserDatabase.UserColumns.CONTENT_URI;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv_favorite);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (savedInstanceState == null){
            new Favorite().execute();
        }
    }

    private class Favorite extends AsyncTask<Void,Void, Cursor> {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected Cursor doInBackground(Void... voids) {
            return getApplicationContext().getContentResolver().query(CONTENT_URI,null,null,null,null);
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            AdapterFavorite adapterUserFavorite = new AdapterFavorite(getApplicationContext());
            adapterUserFavorite.setCursor(cursor);
            adapterUserFavorite.notifyDataSetChanged();
            recyclerView.setAdapter(adapterUserFavorite);
        }
    }
}