package com.githubusersearch.githubfavorite.Adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.githubusersearch.githubfavorite.DetailActivity;
import com.githubusersearch.githubfavorite.Model.User;
import com.githubusersearch.githubfavorite.R;


public class AdapterFavorite extends RecyclerView.Adapter<AdapterFavorite.UserViewHolder> {
    private Context context;
    private Cursor cursor;

    public AdapterFavorite(Context c) {
        this.context = c;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }

    private User getItemData(int position){
        if (!cursor.moveToPosition(position)){
            throw new IllegalStateException("INVALID");
        }
        return new User(cursor);
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite,parent,false);

        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User userGithub = getItemData(position);
        holder.tv1.setText(userGithub.getLogin());
        Glide.with(holder.itemView.getContext())
                .load(userGithub.getAvatarUrl())
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        if (cursor == null) return 0;
        return cursor.getCount();
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
            User userGithub = getItemData(getAdapterPosition());
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("DATA_USER",userGithub);
            v.getContext().startActivity(intent);
        }
    }
}