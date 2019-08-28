package com.prince.android;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UserViewHolder extends RecyclerView.ViewHolder {

    public TextView userName, userStatus;
    public Button btnDelete;

    public UserViewHolder(@NonNull View itemView) {
        super(itemView);

        userName = itemView.findViewById(R.id.userName);
        userStatus = itemView.findViewById(R.id.userStatus);
        btnDelete = itemView.findViewById(R.id.btnDelete);
    }
}
