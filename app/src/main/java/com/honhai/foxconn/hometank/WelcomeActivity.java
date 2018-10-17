package com.honhai.foxconn.hometank;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;

import com.honhai.foxconn.hometank.gameplay.GameData;

public class WelcomeActivity extends AppCompatActivity {

    private final String TAG = "WelcomeActivity";
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_layout);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        findViews();
        setListener();
        GameData.getInstance().reset();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        GameData.getInstance().reset();
    }

    private void setListener() {
        button.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(this, PreparationActivity.class);
            startActivity(intent);
        });
    }

    private void findViews() {
        button = findViewById(R.id.button);
    }
}
