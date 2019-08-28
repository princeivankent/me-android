package com.prince.android.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prince.android.R;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.User> {

    Context context;
    String[] users;

    public UserListAdapter(Context context, String[] users) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public UserListAdapter.User onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(R.layout.user_list, parent, false);
        User user = new User(row);
        return user;
    }

    @Override
    public void onBindViewHolder(@NonNull UserListAdapter.User holder, int position) {
        holder.user_text.setText(users[position]);
    }

    @Override
    public int getItemCount() {
        return users.length;
    }

    public class User extends RecyclerView.ViewHolder {
        TextView user_text;

        public User(@NonNull View itemView) {
            super(itemView);

            user_text = itemView.findViewById(R.id.user);
        }
    }
}
