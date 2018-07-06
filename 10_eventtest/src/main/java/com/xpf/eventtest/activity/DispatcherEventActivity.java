package com.xpf.eventtest.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.xpf.eventtest.R;

public class DispatcherEventActivity extends AppCompatActivity {

    private ListView lv1;
    private ListView lv2;
    private ListView lv3;
    private int ids[] = new int[]{R.drawable.default1, R.drawable.girl1,
            R.drawable.girl2, R.drawable.girl3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispather_event);

        lv1 = (ListView) findViewById(R.id.lv1);
        lv2 = (ListView) findViewById(R.id.lv2);
        lv3 = (ListView) findViewById(R.id.lv3);

        try {
            lv1.setAdapter(new MyAdapter1());
            lv2.setAdapter(new MyAdapter1());
            lv3.setAdapter(new MyAdapter1());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class MyAdapter1 extends BaseAdapter {

        @Override
        public int getCount() {
            return 3000;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @SuppressLint("ViewHolder")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView iv = (ImageView) View.inflate(getApplicationContext(),R.layout.lv_item, null);
            int resId = (int) (Math.random() * 4);
            iv.setImageResource(ids[resId]);
            return iv;
        }
    }
}
