package com.xpf.eventtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.xpf.eventtest.activity.DispatcherEventActivity;
import com.xpf.eventtest.activity.TouchEventActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnTouchEvent1, btnDispatcherEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnTouchEvent1 = findViewById(R.id.btnTouchEvent1);
        btnDispatcherEvent = findViewById(R.id.btnDispatcherEvent);
        btnTouchEvent1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TouchEventActivity.class));
            }
        });
        btnDispatcherEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DispatcherEventActivity.class));
            }
        });
    }
}
