package com.prince.android;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

public class UserViewAdapter extends RecyclerView.Adapter<UserViewHolder> {

    MainActivity mainActivity;
    ArrayList<User> users;

    public UserViewAdapter(MainActivity mainActivity, ArrayList<User> users) {
        this.mainActivity = mainActivity;
        this.users = users;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mainActivity.getBaseContext());
        View view = inflater.inflate(R.layout.user, parent, false);
        UserViewHolder user = new UserViewHolder(view);
        return user;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, final int position) {
         holder.userName.setText(users.get(position).getUserName());
         holder.userStatus.setText(users.get(position).getUserStatus());
         holder.btnDelete.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 deleteSelectedRow(position);
             }
         });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    private void deleteSelectedRow(int position) {
        mainActivity.db.collection("users")
                .document(users.get(position).getUserId())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(mainActivity.getBaseContext(), "Deleted successfully", Toast.LENGTH_SHORT).show();
                        mainActivity.loadDataFromFireBase();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(mainActivity.getBaseContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                        Log.v("Error!", e.getMessage());
                    }
                });
    }
}
