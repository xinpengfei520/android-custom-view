package com.atguigu.add_sub_view;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity {

    private addSubView add_sub_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add_sub_view = (addSubView) findViewById(R.id.add_sub_view);

        add_sub_view.setOnAddSubClickListener(new addSubView.OnAddSubClickListener() {
            @Override
            public void onNumberChange(int value) {
                Toast.makeText(MainActivity.this, "当前value===" + value, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
