package com.xpf.youkumenu;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends Activity {

    private ImageView icon_home;
    private ImageView icon_menu;
    private RelativeLayout level3;
    private RelativeLayout level2;
    private RelativeLayout level1;

    /**
     * 是否显示一级菜单
     * true:显示
     * false:隐藏
     */
    private boolean isShowLevel1 = true;

    /**
     * 是否显示二级菜单
     * true:显示
     * false:隐藏
     */
    private boolean isShowLevel2 = true;


    /**
     * 是否显示三级菜单
     * true:显示
     * false:隐藏
     */
    private boolean isShowLevel3 = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        icon_menu = (ImageView) findViewById(R.id.icon_menu);
        icon_home = (ImageView) findViewById(R.id.icon_home);
        level1 = (RelativeLayout) findViewById(R.id.level1);
        level2 = (RelativeLayout) findViewById(R.id.level2);
        level3 = (RelativeLayout) findViewById(R.id.level3);
        MyOnClickListener myOnClickListener = new MyOnClickListener();
        //设置点击事件
        icon_menu.setOnClickListener(myOnClickListener);
        icon_home.setOnClickListener(myOnClickListener);
        level1.setOnClickListener(myOnClickListener);
        level2.setOnClickListener(myOnClickListener);
        level3.setOnClickListener(myOnClickListener);
    }

    class MyOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.icon_menu://菜单按钮
                    hideShowLevel3();
                    break;
                case R.id.icon_home://home按钮
                    hideShowHome();
                    break;
                case R.id.level1:
                    Toast.makeText(MainActivity.this, "level1", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.level2:
                    Toast.makeText(MainActivity.this, "level2", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.level3:
                    Toast.makeText(MainActivity.this, "level3", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }


    private void hideShowHome() {
        //如果二级菜单和三级菜单是显示的，就隐藏，如果隐藏就显示二级菜单
        if(isShowLevel2){
            //隐藏
            Tools.hideView(level2);
            isShowLevel2 = false;
            if(isShowLevel3){
                //隐藏
                Tools.hideView(level3,200);
                isShowLevel3 = false;
            }
        }else{
            //显示
            Tools.showView(level2);
            isShowLevel2 = true;
        }
    }

    private void hideShowLevel3() {
        if(isShowLevel3){
            //隐藏
            Tools.hideView(level3);
            isShowLevel3 = false;
        }else{
            //显示
            Tools.showView(level3);
            isShowLevel3 = true;
        }
    }

    /**
     * 监听物理健
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode ==KeyEvent.KEYCODE_MENU){
            //判断一级菜单，二级菜单，三级菜单，是否显示，如果显示就全部隐藏；
            if(isShowLevel1){
                //隐藏
                Tools.hideView(level1);
                isShowLevel1 = false;
                if(isShowLevel2){
                    //隐藏
                    Tools.hideView(level2,200);
                    isShowLevel2 = false;
                    if(isShowLevel3){
                        //隐藏
                        Tools.hideView(level3,400);
                        isShowLevel3 = false;
                    }
                }
            }else{
                // 如果都隐藏了，就显示一级菜单和二级菜单
                isShowLevel1 = true;
                Tools.showView(level1);
                isShowLevel2 = true;
                Tools.showView(level2,200);
            }

            return  true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
