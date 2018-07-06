package com.xpf.slidingmenu;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private ListView listView;
    private ArrayList<MyBean> mListBeans;
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        initData();
        setAdapter();
    }

    private void setAdapter() {
        if (mListBeans != null && mListBeans.size() > 0) {
            //设置适配器
            mAdapter = new MyAdapter();
            listView.setAdapter(mAdapter);
        }
    }

    private void initData() {
        //初始化数据
        mListBeans = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            mListBeans.add(new MyBean("Content" + i));
        }
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mListBeans.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(MainActivity.this, R.layout.item, null);
                viewHolder = new ViewHolder();
                viewHolder.item_content = (TextView) convertView.findViewById(R.id.item_content);
                viewHolder.item_menu = (TextView) convertView.findViewById(R.id.item_menu);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            MyBean myBean = mListBeans.get(position);
            viewHolder.item_content.setText(myBean.getName());

            return convertView;
        }
    }

    static class ViewHolder {
        TextView item_content;
        TextView item_menu;
    }

}
