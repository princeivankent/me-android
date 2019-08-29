package com.prince.android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class MainActivity extends AppCompatActivity {

    public FirebaseFirestore db;
    private RecyclerView recyclerView;
    private ArrayList<User> users;
    private UserViewAdapter userViewAdapter;

    EditText txtUserName;
    Button btnAdd, btnNavigate, btnOpenGoogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        users = new ArrayList<>();
        txtUserName = findViewById(R.id.txtUserName);
        btnAdd = findViewById(R.id.btnAdd);
        btnNavigate = findViewById(R.id.btnNavigate);
        btnOpenGoogle = findViewById(R.id.btnOpenGoogle);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUserName();
            }
        });

        btnNavigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNewScreen();
            }
        });

        btnOpenGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGoogle("https://www.google.com");
            }
        });

        setUpRecyclerView();
        setUpFireBase();
        loadDataFromFireBase();
    }

    private void setUpRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setUpFireBase() {
        db = FirebaseFirestore.getInstance();
    }

    public void loadDataFromFireBase() {
        if (users.size() > 0) {
            users.clear();
        }

        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot querySnapshot: task.getResult()) {
                            User user = new User(
                                    querySnapshot.getId(),
                                    querySnapshot.getString("name"),
                                    querySnapshot.getString("status")
                            );
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

    private void addUserName() {
        String username = txtUserName.getText().toString();

        if (username.matches("")) {
            Toast.makeText(this, "Please provide any string", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("name", username);
        dataMap.put("status", "active");

        db.collection("users")
                .add(dataMap)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(MainActivity.this, "User has been added", Toast.LENGTH_SHORT).show();
                        txtUserName.setText("");
                        loadDataFromFireBase();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                        Log.v("CREATION_ERROR", e.getMessage());
                    }
                });
    }

    private void openNewScreen() {
        String data = txtUserName.getText().toString();

        Context context = MainActivity.this;
        Class destinationActivity = ChildActivity.class;

        Intent intent = new Intent(context, destinationActivity);
        intent.putExtra(Intent.EXTRA_TEXT, data);
        startActivity(intent);
    }

    private void openGoogle(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(webpage);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
