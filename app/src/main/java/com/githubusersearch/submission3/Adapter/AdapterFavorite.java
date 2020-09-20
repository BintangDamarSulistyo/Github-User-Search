package com.githubusersearch.submission3.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.githubusersearch.submission3.DetailActivity;
import com.githubusersearch.submission3.Model.User;
import com.githubusersearch.submission3.R;

import java.util.ArrayList;

public class AdapterFavorite extends RecyclerView.Adapter<AdapterFavorite.UserViewHolder> {
    private Context context;
    private ArrayList<User> userGithubList ;

    public AdapterFavorite(Context c) {
        this.context = c;
    }

    public void setUserGithubList(ArrayList<User> userGithubArrayList){
        this.userGithubList = userGithubArrayList;
    }

    @NonNull
    @Override
    public AdapterFavorite.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite,parent,false);

        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterFavorite.UserViewHolder holder, int position) {
        User userGithub = userGithubList.get(position);
        holder.tv1.setText(userGithub.getLogin());
        Glide.with(holder.itemView.getContext())
                .load(userGithub.getAvatarUrl())
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        return userGithubList.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView img;
        TextView tv1;
        UserViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_user);
            tv1 = itemView.findViewById(R.id.tv_1);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            User user = userGithubList.get(getAdapterPosition());
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("DATA_USER",user);
            v.getContext().startActivity(intent);
        }
    }
}