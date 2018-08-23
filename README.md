
# android-custom-view

### 1.AddSubView

购物页面和购物车中实现一个+1、-1按钮的功能

![image](https://github.com/xinpengfei520/AddSubView/blob/master/screenshot/image.gif)

### 2.YoukuMenu

仿优酷三级旋转菜单

![image](https://github.com/xinpengfei520/AddSubView/blob/master/screenshot/youku_menu.png)

### 3.Banner

广告自动轮播图效果，按住停止轮播，松开自动无限轮播

![image](https://github.com/xinpengfei520/AddSubView/blob/master/screenshot/03_banner.png)

### 4.Spinner

使用Popupwindow实现的 List 下拉框

![image](https://github.com/xinpengfei520/AddSubView/blob/master/screenshot/04_spinner.png)

### 5.ToggleButton

自定义可以滑动并可以点击的 ToggleButton

![image](https://github.com/xinpengfei520/AddSubView/blob/master/screenshot/toggle_button.png)

### 6.AttributeView

自定义属性(背景、姓名、年龄)的 View

![image](https://github.com/xinpengfei520/AddSubView/blob/master/screenshot/attribute_view.png)

### 7.ImitateViewPager

仿 ViewPager 实现

![image](https://github.com/xinpengfei520/AddSubView/blob/master/screenshot/07_imitate_viewpager.png)

### 8.ContactQuickIndex

自定义联系人快速索引 View

![image](https://github.com/xinpengfei520/AddSubView/blob/master/screenshot/contact_quick_index.png)

### 9.SlidingMenu

ListView 的侧滑删除

![image](https://github.com/xinpengfei520/AddSubView/blob/master/screenshot/sliding_menu.png)

### 10.EventTest

EventTest 触摸事件测试 Demo

### 11.ArcView

弧形View(凸出的弧形)

![image](https://github.com/xinpengfei520/AddSubView/blob/master/screenshot/arcview.png)

## 12.LoadingView

### 12.1初始化Loading

1.最基本的用法:

```
        // 因为 PopupWindow 依赖于Activity，所以必须要调用 setDropView 方法设置要挂载的 View，
        // 一般是 Activity 或 Fragment 的根 View，其他参数可根据需求进行设置。
        mLoadingView = new LoadingView.Builder(this)
                .setDropView(activity_main)
                .build();
```

2.自定义设置各种参数:

```
        mLoadingView = new LoadingView.Builder(this)
                .setText("拼命加载中...") // 设置文案
                .setTextSize(12) // 设置字体大小(sp)
                .setTextColor("#FFFFFF") // 设置字体颜色(#RGB & #ARGB)
                .setTextMarginTop(10) // 设置文字距上的距离(dp)
                .setCornerRadius(4) // 设置圆角半径(dp)
                .setLoadingBgColor("#CC000000") // 设置背景颜色(#RGB & #ARGB)
                .setLoadingWidth(120) // 设置 loading 的宽(dp)
                .setLoadingHeight(100) // 设置 loading 的高(dp)
                .setListener(listener) // 设置监听
                .setDropView(activity_main) // 设置要挂载的 View(必须要设置)，一般是 Activity 或 Fragment 的根 View
                .setGifDrawable(R.drawable.loading4) // 设置 gif 资源
                .setFocusable(false) // 为 true 时，响应返回键消失，为 false 时响应 activity 返回操作，默认为 false
                .setGifWidth(16) // 设置 gif 的宽(dp)
                .setGifHeight(16) // 设置 gif 的高(dp)
                .build();
```

### 12.2 显示Loading

```
mLoadingView.show();
```

### 12.3 取消Loading

```
mLoadingView.dismiss();
```

## License

```
	Copyright 2016 android-custom-view of copyright x-sir

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```
