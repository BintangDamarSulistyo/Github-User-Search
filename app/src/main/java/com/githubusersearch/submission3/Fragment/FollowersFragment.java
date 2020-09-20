package com.githubusersearch.submission3.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.githubusersearch.submission3.Adapter.AdapterUser;
import com.githubusersearch.submission3.Connection.ApiService;
import com.githubusersearch.submission3.Connection.RetrofitConfiguration;
import com.githubusersearch.submission3.Model.User;
import com.githubusersearch.submission3.R;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FollowersFragment extends Fragment {

    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_followers, container, false);
        setDataFollowers(view);
        return view;
    }

    private void setDataFollowers(View view){
        recyclerView = view.findViewById(R.id.rv_user);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        User user = Objects.requireNonNull(getActivity()).getIntent().getParcelableExtra("DATA_USER");
        ApiService apiService = RetrofitConfiguration.getRetrofit().create(ApiService.class);
        Call<List<User>> call = apiService.getUserFollowers(Objects.requireNonNull(user).getLogin());
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                AdapterUser adapterUser = new AdapterUser(getContext(),response.body());
                recyclerView.setAdapter(adapterUser);
            }
            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
            }
        });
    }
}
