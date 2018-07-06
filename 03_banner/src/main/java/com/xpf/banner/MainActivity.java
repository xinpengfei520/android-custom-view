package com.xpf.banner;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ViewPager viewPager;
    private TextView tv_title;
    private LinearLayout ll_point_group;
    private ArrayList<ImageView> imageViews;

    // 图片资源ID
    private final int[] imageIds = {
            R.drawable.a,
            R.drawable.b,
            R.drawable.c,
            R.drawable.d,
            R.drawable.e};

    // 图片标题集合
    private final String[] imageDescriptions = {
            "Beijing市拔河争霸赛！",
            "凝聚你我，放飞梦想！",
            "抱歉！您来晚啦。",
            "7月就业名单全部曝光！",
            "大数据平均起薪31345元"
    };
    /**
     * 上一次被高亮显示的位置
     */
    private int prePosition;

    /*
    ListView绑定数据
    1.在布局文件定义
    2.在代码中实例化
    3.准备数据
    4.设置适配器-getCount-->getView
     */

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int item = viewPager.getCurrentItem() + 1;
            viewPager.setCurrentItem(item);
            handler.removeMessages(0);
            handler.sendEmptyMessageDelayed(0, 4000);
        }
    };

    private boolean isDragging = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        setAdapter();
        setParams();
    }

    private void setParams() {
        //设置文本
        tv_title.setText(imageDescriptions[prePosition]);
        //设置监听页面的改变
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());
        //设置第0个点高亮
        ll_point_group.getChildAt(prePosition).setEnabled(true);
        //保证是imageViews.size()整数倍
        int item = Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % imageViews.size();
        //11->10
        viewPager.setCurrentItem(item);
        handler.sendEmptyMessageDelayed(0, 4000);
    }

    private void setAdapter() {
        //设置适配器
        viewPager.setAdapter(new MyPagerAdapter());
    }

    private void initData() {
        //准备数据
        imageViews = new ArrayList<>();
        for (int i = 0; i < imageIds.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(imageIds[i]);//一定要用背景
            //imageView.setImageResource();

            //添加到集合中
            imageViews.add(imageView);

            //创建点
            ImageView point = new ImageView(this);
            point.setBackgroundResource(R.drawable.point_selector);
            //当成dp-->像数(不是8)
            int widthDp = DensityUtil.dip2px(this, 8);
            Log.e(TAG, widthDp + "");

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(widthDp, widthDp);
            if (i != 0) {
                params.leftMargin = widthDp;
                point.setEnabled(false);
            }
            point.setLayoutParams(params);
            //添加到线性布局
            ll_point_group.addView(point);
        }
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tv_title = (TextView) findViewById(R.id.tv_title);
        ll_point_group = (LinearLayout) findViewById(R.id.ll_point_group);
    }

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        /**
         * 当页面滚动的时候回调这个方法
         *
         * @param position             当前滚动页面的位置
         * @param positionOffset       当前滑动的百分比
         * @param positionOffsetPixels 屏幕滑动的像数
         */
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        /**
         * 当某个页面被选中的时候回调
         *
         * @param position 被选中页面的位置
         */
        @Override
        public void onPageSelected(int position) {
            int realPosition = position % imageViews.size();
            tv_title.setText(imageDescriptions[realPosition]);
            //把之前高亮显示的设置灰色
            ll_point_group.getChildAt(prePosition).setEnabled(false);
            //把当前位置对应的点设置红色
            ll_point_group.getChildAt(realPosition).setEnabled(true);
            prePosition = realPosition;
        }

        /**
         * 当页面滑动状态改变的时候回调
         * 拖拽-->滑动
         * 滑动-->静止
         * 静止-->拖拽
         *
         * @param state
         */
        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                isDragging = true;
                handler.removeMessages(0);
                Log.e(TAG, "ViewPager拖拽中----");
            } else if (state == ViewPager.SCROLL_STATE_IDLE) {
                Log.e(TAG, "ViewPager静止中----");
            } else if (state == ViewPager.SCROLL_STATE_SETTLING && isDragging) {
                isDragging = false;
                handler.removeMessages(0);
                handler.sendEmptyMessageDelayed(0, 4000);
                Log.e(TAG, "ViewPager滑动----");
            }
        }
    }

    class MyPagerAdapter extends PagerAdapter {

        /**
         * 相当于getView方法
         *
         * @param container ViewPager容器
         * @param position  要创建页面的位置
         * @return
         */
        @SuppressLint("ClickableViewAccessibility")
        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            final ImageView imageView = imageViews.get(position % imageViews.size());
            //添加到容器(ViewPager)中
            container.addView(imageView);
            Log.e("MainActivity", "instantiateItem==position==" + position);
//            return position;
            //设置触摸事件
            imageView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN://手指按下图片
                            Log.e(TAG, "手指按下图片");
                            handler.removeMessages(0);
                            break;
                        case MotionEvent.ACTION_UP://手指松开图片
                            Log.e(TAG, "手指松开图片");
                            handler.sendEmptyMessageDelayed(0, 4000);
                            break;
//                        case MotionEvent.ACTION_CANCEL://事件取消
//                            Log.e(TAG,"事件取消");
//                            handler.sendEmptyMessageDelayed(0,4000);
//                            break;
                    }
                    return false;
                }
            });

            //设置tag
            imageView.setTag(position);
            //设置点击事件
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) imageView.getTag();
                    Toast.makeText(MainActivity.this, "" + imageDescriptions[position % imageViews.size()], Toast.LENGTH_SHORT).show();
                }
            });
            return imageView;
        }

        /**
         * 比较视图是否是同一个页面
         *
         * @param view   比较的页面
         * @param object 由instantiateItem返回回来的值
         * @return
         */
        @Override
        public boolean isViewFromObject(View view, Object object) {
//            if(view==object){
//                return true;
//            }else{
//                return false;
//            }
//            return view==imageViews.get(Integer.parseInt(object.toString()));
            return view == object;
        }

        /**
         * 销毁某一条
         *
         * @param container
         * @param position
         * @param object
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //super.destroyItem(container, position, object);//注意了，这个方法要注释掉
            container.removeView((View) object);
            Log.e(TAG, "destroyItem==position==" + position);
        }

        /**
         * 得到数据的总条数
         *
         * @return
         */
        @Override
        public int getCount() {
            //return imageViews.size();
            return Integer.MAX_VALUE;
        }
    }

}
