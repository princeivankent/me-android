package com.prince.android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    // TODO (1) Create a key String called LIFECYCLE_CALLBACKS_TEXT_KEY

    private static final String LIFECYCLE_CALLBACKS_TEXT_KEY = "callbacks";

    /* Constant values for the names of each respective lifecycle callback */
    private static final String ON_CREATE = "onCreate";
    private static final String ON_START = "onStart";
    private static final String ON_RESUME = "onResume";
    private static final String ON_PAUSE = "onPause";
    private static final String ON_STOP = "onStop";
    private static final String ON_RESTART = "onRestart";
    private static final String ON_DESTROY = "onDestroy";
    private static final String ON_SAVE_INSTANCE_STATE = "onSaveInstanceState";

    /*
     * This TextView will contain a running log of every lifecycle callback method called from this
     * Activity. This TextView can be reset to its default state by clicking the Button labeled
     * "Reset Log"
     */
    private TextView mLifecycleDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLifecycleDisplay = findViewById(R.id.tv_lifecycle_events_display);

        // TODO (6) If savedInstanceState is not null and contains LIFECYCLE_CALLBACKS_TEXT_KEY, set that text on our TextView

        logAndAppend(ON_CREATE);
    }

    private void logAndAppend(String lifecycleEvent) {
        Log.d(TAG, "Lifecycle Event: " + lifecycleEvent);

        mLifecycleDisplay.append(lifecycleEvent + "\n");
    }
}
