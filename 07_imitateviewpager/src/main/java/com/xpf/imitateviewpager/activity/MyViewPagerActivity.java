package com.xpf.imitateviewpager.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.xpf.imitateviewpager.widget.MyViewPager;
import com.xpf.imitateviewpager.R;

public class MyViewPagerActivity extends Activity {

    private MyViewPager myViewPager;
    private RadioGroup radioGroup;
    private int[] ids = {R.drawable.a1, R.drawable.a2, R.drawable.a3,
            R.drawable.a4, R.drawable.a5, R.drawable.a6};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_viewpager);
        myViewPager = (MyViewPager) findViewById(R.id.myViewPager);
        radioGroup = (RadioGroup) findViewById(R.id.rg_main);
        // 添加6个页面
        for (int id : ids) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(id);
            //把6个页面添加到视图中
            myViewPager.addView(imageView);
        }

        //添加测试页面
        View test = View.inflate(this, R.layout.test_page, null);
        myViewPager.addView(test, 2);

        //根据有多少个页面就添加多少个RadioButton
        //添加RadioButton
        for (int i = 0; i < myViewPager.getChildCount(); i++) {
            RadioButton button = new RadioButton(this);
            button.setId(i);//0~5
            if (i != 0) {
                button.setChecked(false);
            } else {
                button.setChecked(true);
            }

            //添加到RadioGroup里面
            radioGroup.addView(button);
        }

        //设置按钮的点击状态改变的监听
        radioGroup.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
        //设置监听页面的改变
        myViewPager.setOnPagerChangeListener(new MyViewPager.OnPagerChangeListener() {
            /**
             * 0~5
             * @param index 页面的下标位置
             */
            @Override
            public void onChangePager(int index) {
                radioGroup.check(index);
            }
        });
    }

    class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {

        /**
         * 0~5
         *
         * @param group
         * @param checkedId
         */
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            myViewPager.moveToPager(checkedId);
        }
    }
}
