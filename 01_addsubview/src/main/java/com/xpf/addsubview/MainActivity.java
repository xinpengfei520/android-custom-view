package com.xpf.addsubview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private AddSubView addSubView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addSubView = (AddSubView) findViewById(R.id.add_sub_view);
        addSubView.setOnAddSubClickListener(new AddSubView.OnAddSubClickListener() {
            @Override
            public void onNumberChange(int value) {
                Toast.makeText(MainActivity.this, "当前value===" + value, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
