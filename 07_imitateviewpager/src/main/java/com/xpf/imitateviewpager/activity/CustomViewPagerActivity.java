package com.xpf.imitateviewpager.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.xpf.imitateviewpager.widget.CustomViewPager;
import com.xpf.imitateviewpager.R;

public class CustomViewPagerActivity extends AppCompatActivity {

    private CustomViewPager viewPager;
    private int ids[] = {R.drawable.a1, R.drawable.a2, R.drawable.a3,
            R.drawable.a4, R.drawable.a5, R.drawable.a6};
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view_pager);
        viewPager = (CustomViewPager) findViewById(R.id.vp_viewpager);
        radioGroup = (RadioGroup) findViewById(R.id.rg_group);
        initData();
        initListener();
    }

    private void initData() {
        for (int id : ids) {
            ImageView iv = new ImageView(this);
            iv.setBackgroundResource(id);
            viewPager.addView(iv);
        }
        // 添加测试页面
        View testView = View.inflate(this, R.layout.test_page, null);
        viewPager.addView(testView, 2);
        // 添加单选按钮
        for (int i = 0; i < viewPager.getChildCount(); i++) {
            RadioButton rb = new RadioButton(this);
            rb.setId(i);
            radioGroup.addView(rb);
            if (i == 0) {
                rb.setChecked(true);
            }
        }
    }

    private void initListener() {
        // 点击radioButton跳到对应的页面
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                viewPager.moveTo(checkedId);
            }
        });

        // 滑动页面RadioButton也跟着移动
        viewPager.setOnPageChangeListener(new CustomViewPager.onPageChangeListener() {
            @Override
            public void onPageSelect(int position) {
                radioGroup.check(position);
            }
        });
    }
}
