package com.prince.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ChildActivity extends AppCompatActivity {

    TextView txtDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);

        txtDisplay = findViewById(R.id.txtDisplay);

        Intent data = getIntent();

        if (data.hasExtra(Intent.EXTRA_TEXT)) {
            String extraText = data.getStringExtra(Intent.EXTRA_TEXT);
            txtDisplay.setText(extraText);
        }
    }
}
