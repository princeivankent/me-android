package com.prince.android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private RecyclerView recyclerView;
    private ArrayList<User> users;
    private UserViewAdapter userViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        users = new ArrayList<>();
        setUpRecyclerView();
        setUpFireBase();
        addTestDatasToFireBase();
        loadDataFromFirebase();
    }

    private void setUpRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setUpFireBase() {
        db = FirebaseFirestore.getInstance();
    }

    private void addTestDatasToFireBase() {
        Random random = new Random();

        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("name", "try name "+random.nextInt(50));
        dataMap.put("status", "try status "+random.nextInt(50));

        db.collection("users")
                .add(dataMap)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(MainActivity.this, "Users has been added", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void loadDataFromFirebase() {
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot querySnapshot: task.getResult()) {
                            User user = new User(querySnapshot.getString("name"),
                                    querySnapshot.getString("status"));
                            users.add(user);
                        }

                        userViewAdapter = new UserViewAdapter(MainActivity.this, users);
                        recyclerView.setAdapter(userViewAdapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                        Log.v("Error!", e.getMessage());
                    }
                });
    }
}
