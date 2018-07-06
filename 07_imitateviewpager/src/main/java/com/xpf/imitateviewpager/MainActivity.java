package com.xpf.imitateviewpager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.xpf.imitateviewpager.activity.CustomViewPagerActivity;
import com.xpf.imitateviewpager.activity.MyViewPagerActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnMyViewPager, btnCustomViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnMyViewPager = findViewById(R.id.btnMyViewPager);
        btnCustomViewPager = findViewById(R.id.btnCustomViewPager);
        btnMyViewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MyViewPagerActivity.class));
            }
        });
        btnCustomViewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CustomViewPagerActivity.class));
            }
        });
    }
}
