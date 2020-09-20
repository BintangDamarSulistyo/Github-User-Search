package com.githubusersearch.submission3.Adapter;

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

import java.util.List;

public class AdapterSearch extends RecyclerView.Adapter<AdapterSearch.UserViewHolder> {

    private List<User> userList;
    public AdapterSearch(List<User> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public AdapterSearch.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user,parent,false);

        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSearch.UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.tv1.setText(user.getLogin());
        holder.tv2.setText(user.getUrl());
        Glide.with(holder.itemView.getContext())
                .load(user.getAvatarUrl())
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView img;
        TextView tv1,tv2;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_user);
            tv1 = itemView.findViewById(R.id.tv_1);
            tv2 = itemView.findViewById(R.id.tv_2);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            User user = userList.get(getAdapterPosition());
            Intent intent = new Intent(v.getContext(), DetailActivity.class);
            intent.putExtra("DATA_USER", user);
            v.getContext().startActivity(intent);
        }
    }
}
