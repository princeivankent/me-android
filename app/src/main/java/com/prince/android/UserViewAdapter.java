package com.prince.android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prince.android.adapters.UserListAdapter;

import java.util.ArrayList;

public class UserViewAdapter extends RecyclerView.Adapter<UserViewHolder> {

    Context context;
    ArrayList<User> users;

    public UserViewAdapter(Context context, ArrayList<User> users) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.user, parent, false);
        UserViewHolder user = new UserViewHolder(view);
        return user;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
         holder.userName.setText(users.get(position).getUserName());
         holder.userStatus.setText(users.get(position).getUserStatus());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}
