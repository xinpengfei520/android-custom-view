package com.xpf.spinner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText et_input;
    private ImageView iv_arrow;
    private PopupWindow popupWindow;
    private ListView listview;
    private ArrayList<String> msg;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        setAdapter();
    }

    private void setAdapter() {
        //设置适配器
        myAdapter = new MyAdapter();
        listview.setAdapter(myAdapter);

        //设置ListView的item点击事件
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //1.得到具体的内容
                String text = msg.get(position);
                //2.设置EditText的值
                et_input.setText(text);
                //3.把popupWindow消掉
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    ;
                    popupWindow = null;
                }
            }
        });
    }

    /**
     * 准备数据
     */
    private void initData() {
        msg = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            //msg.add(new String("aaaaaaa"+i));
            msg.add(i + "aaaaaaaaaa" + i);
        }
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return msg.size();
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
                viewHolder.iv_delete = (ImageView) convertView.findViewById(R.id.iv_delete);
                viewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            //根据位置得到对应的数据
            final String itemData = msg.get(position);
            viewHolder.tv_title.setText(itemData);
            //设置删除红色按钮的点击事件
            viewHolder.iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //1.从集合中移除这条数据
                    msg.remove(itemData);
                    //2.刷新适配器
                    notifyDataSetChanged();
                }
            });
            return convertView;
        }
    }

    static class ViewHolder {
        TextView tv_title;
        ImageView iv_delete;
    }

    private void initView() {
        setContentView(R.layout.activity_main);
        et_input = (EditText) findViewById(R.id.et_input);
        iv_arrow = (ImageView) findViewById(R.id.iv_arrow);

        listview = new ListView(this);
        //设置ListView的背景
        listview.setBackgroundResource(R.drawable.listview_background);

        iv_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow == null) {
                    popupWindow = new PopupWindow(MainActivity.this);
                    popupWindow.setWidth(et_input.getWidth());
                    popupWindow.setHeight(DensityUtil.dip2px(MainActivity.this, 200));
                    //设置PopupWindow里面装的视图
                    popupWindow.setContentView(listview);
                    popupWindow.setFocusable(true);//设置焦点

                }

                //显示在EditText的下边
                popupWindow.showAsDropDown(et_input, 0, 0);
            }
        });
    }
}
