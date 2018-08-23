package com.xpf.android.loadingview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.lang.ref.WeakReference;

/**
 * Created by x-sir on 2018/8/22 :)
 * Function:
 */
public class LoadingView {

    private String mText;
    private int mTextSize;
    private int mGifWidth;
    private int mGifHeight;
    private int mDrawableId;
    private View mPopupView;
    private Context mContext;
    private String mTextColor;
    private int mCornerRadius;
    private int mLoadingWidth;
    private int mLoadingHeight;
    private int mTextMarginTop;
    private boolean mIsFocusable;
    private String mLoadingBgColor;
    private PopupWindow mPopupWindow;
    private WeakReference<View> mView;
    private OnLoadingListener mListener;

    private static final String DEFAULT_TEXT = "加载中..."; // default text
    private static final int DEFAULT_TEXT_SIZE = 12; // default text size
    private static final int DEFAULT_TEXT_MARGIN_TOP = 6; // default text margin top
    private static final String DEFAULT_TEXT_COLOR = "#FFFFFF"; // default text color
    private static final int DEFAULT_CORNER_RADIUS = 4; // default loading background radius size
    private static final String DEFAULT_LOADING_BG_COLOR = "#CC000000"; // default loading background color
    private static final int DEFAULT_DRAWABLE_ID = R.drawable.loading1; // default loading drawable
    private static final int DEFAULT_GIF_WIDTH = 30; // default gif width
    private static final int DEFAULT_GIF_HEIGHT = 30; // default gif height

    /**
     * Constructor.
     *
     * @param builder
     */
    public LoadingView(Builder builder) {
        this.mText = builder.text;
        this.mView = builder.view;
        this.mListener = builder.listener;
        this.mTextSize = builder.textSize;
        this.mTextColor = builder.textColor;
        this.mCornerRadius = builder.cornerRadius;
        this.mContext = builder.applicationContext;
        this.mLoadingBgColor = builder.loadingBgColor;
        this.mDrawableId = builder.drawableId;
        this.mGifWidth = builder.gifWidth;
        this.mGifHeight = builder.gifHeight;
        this.mLoadingWidth = builder.loadingWidth;
        this.mLoadingHeight = builder.loadingHeight;
        this.mTextMarginTop = builder.textMarginTop;
        this.mIsFocusable = builder.isFocusable;
        initView();
    }

    /**
     * Initialize view parameters.
     */
    private void initView() {
        if (mPopupView == null) {
            mPopupView = View.inflate(mContext, R.layout.popupwindow_loading, null);
        }
        if (mPopupWindow == null) {
            mPopupWindow = new PopupWindow(mPopupView, WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT);
        }

        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (mListener != null) {
                    mListener.onDismiss();
                }
            }
        });
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        // 当 mIsFocusable 为 true 时，响应返回键消失，为 false 时响应 activity 返回操作，默认为 false
        mPopupWindow.setFocusable(mIsFocusable);

        LinearLayout llLoadingBg = (LinearLayout) mPopupView.findViewById(R.id.llLoadingBg);
        ImageView ivLoading = (ImageView) mPopupView.findViewById(R.id.ivLoading);
        TextView tvContent = (TextView) mPopupView.findViewById(R.id.tvContent);

        RelativeLayout.LayoutParams rlParams = (RelativeLayout.LayoutParams) llLoadingBg.getLayoutParams();
        if (mLoadingWidth != -1 && mLoadingHeight != -1) {
            rlParams.width = dp2px(mLoadingWidth);
            rlParams.height = dp2px(mLoadingHeight);
        } else {
            rlParams.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
            rlParams.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
        }
        llLoadingBg.setLayoutParams(rlParams);

        GradientDrawable mGroupDrawable = new GradientDrawable();
        /*设置 Drawable 的形状为矩形*/
        mGroupDrawable.setShape(GradientDrawable.RECTANGLE);
        /*设置背景颜色*/
        mGroupDrawable.setColor(Color.parseColor(mLoadingBgColor));
        /*设置圆角大小*/
        mGroupDrawable.setCornerRadius(dp2px(mCornerRadius));
        llLoadingBg.setBackground(mGroupDrawable);

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tvContent.getLayoutParams();
        params.topMargin = dp2px(mTextMarginTop);
        tvContent.setLayoutParams(params);
        /*设置显示文本*/
        tvContent.setText(mText);
        /*设置文本大小(以 SP 为单位)*/
        tvContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTextSize);
        /*设置文本颜色*/
        tvContent.setTextColor(Color.parseColor(mTextColor));

        LinearLayout.LayoutParams llParams = (LinearLayout.LayoutParams) ivLoading.getLayoutParams();
        llParams.width = dp2px(mGifWidth);
        llParams.height = dp2px(mGifHeight);
        ivLoading.setLayoutParams(llParams);
        /*加载 GIF 图片*/
        Glide.with(mContext).load(mDrawableId)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(ivLoading);
    }

    /**
     * Show popupWindow.
     */
    public void show() {
        dismiss();
        if (mPopupWindow != null) {
            // 必须要 post runnable，如果在onCreate中调用则会抛：android.view.WindowManager$BadTokenException: Unable to add window -- token
            mView.get().post(new Runnable() {
                @Override
                public void run() {
                    mPopupWindow.showAtLocation(mView.get(),
                            Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                }
            });
        }
    }

    /**
     * Cancel popupWindow showing.
     */
    public void dismiss() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }

    /**
     * Invoke on Activity onDestroy() method.
     */
    public void dispose() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
        mPopupWindow = null;
        if (mView != null) {
            mView.clear();
            mView = null;
        }
    }

    /**
     * PopupWindow is or not showing.
     *
     * @return
     */
    public boolean isShowing() {
        return mPopupWindow != null && mPopupWindow.isShowing();
    }

    /**
     * Builder inner class.
     */
    public static final class Builder {
        private String text;
        private String textColor;
        private int textSize = -1;
        private int gifWidth = -1;
        private int gifHeight = -1;
        private int drawableId = -1;
        private String loadingBgColor;
        private int cornerRadius = -1;
        private int loadingWidth = -1;
        private int loadingHeight = -1;
        private int textMarginTop = -1;
        private boolean isFocusable = false;
        private WeakReference<View> view;
        private OnLoadingListener listener;
        private Context applicationContext;

        /**
         * Constructor
         */
        public Builder(Context context) {
            this.applicationContext = context.getApplicationContext();
        }

        /**
         * Set content text.
         *
         * @param text
         * @return
         */
        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        /**
         * Set text size.
         *
         * @param textSize
         * @return
         */
        public Builder setTextSize(int textSize) {
            this.textSize = textSize;
            return this;
        }

        /**
         * Set text margin top dimen.
         *
         * @param textMarginTop
         * @return
         */
        public Builder setTextMarginTop(int textMarginTop) {
            this.textMarginTop = textMarginTop;
            return this;
        }

        /**
         * Set popupWindow's focusable.
         *
         * @param isFocusable
         * @return
         */
        public Builder setFocusable(boolean isFocusable) {
            this.isFocusable = isFocusable;
            return this;
        }

        /**
         * Set gif imageView width.
         *
         * @param gifWidth
         * @return
         */
        public Builder setGifWidth(int gifWidth) {
            this.gifWidth = gifWidth;
            return this;
        }

        /**
         * Set gif imageView height.
         *
         * @param gifHeight
         * @return
         */
        public Builder setGifHeight(int gifHeight) {
            this.gifHeight = gifHeight;
            return this;
        }

        /**
         * Set gif loadingView width.
         *
         * @param loadingWidth
         * @return
         */
        public Builder setLoadingWidth(int loadingWidth) {
            this.loadingWidth = loadingWidth;
            return this;
        }

        /**
         * Set gif loadingView height.
         *
         * @param loadingHeight
         * @return
         */
        public Builder setLoadingHeight(int loadingHeight) {
            this.loadingHeight = loadingHeight;
            return this;
        }

        /**
         * Set text color.
         *
         * @param textColor
         * @return
         */
        public Builder setTextColor(String textColor) {
            this.textColor = textColor;
            return this;
        }

        /**
         * Set loadingView corner radius.
         *
         * @param cornerRadius
         * @return
         */
        public Builder setCornerRadius(int cornerRadius) {
            this.cornerRadius = cornerRadius;
            return this;
        }

        /**
         * Set loadingView background color.
         *
         * @param loadingBgColor
         * @return
         */
        public Builder setLoadingBgColor(String loadingBgColor) {
            this.loadingBgColor = loadingBgColor;
            return this;
        }

        /**
         * Set gif drawable resource.
         *
         * @param drawableId
         * @return
         */
        public Builder setGifDrawable(int drawableId) {
            this.drawableId = drawableId;
            return this;
        }

        /**
         * Set location at parent view, because popupWindow must be dependency activity.
         *
         * @param view
         * @return
         */
        public Builder setDropView(View view) {
            if (view != null) {
                this.view = new WeakReference<>(view);
            } else {
                throw new IllegalArgumentException("must be point parent view!");
            }
            return this;
        }

        /**
         * set on popupWindow dismiss listener.
         *
         * @param listener
         * @return
         */
        public Builder setListener(OnLoadingListener listener) {
            this.listener = listener;
            return this;
        }

        public LoadingView build() {
            if (TextUtils.isEmpty(text)) {
                text = DEFAULT_TEXT;
            }
            if (textSize == -1) {
                textSize = DEFAULT_TEXT_SIZE;
            }
            if (textMarginTop == -1) {
                textMarginTop = DEFAULT_TEXT_MARGIN_TOP;
            }
            if (TextUtils.isEmpty(textColor)) {
                textColor = DEFAULT_TEXT_COLOR;
            }
            if (TextUtils.isEmpty(loadingBgColor)) {
                loadingBgColor = DEFAULT_LOADING_BG_COLOR;
            }
            if (cornerRadius == -1) {
                cornerRadius = DEFAULT_CORNER_RADIUS;
            }
            if (view == null) {
                throw new IllegalArgumentException("must be point parent view!");
            }
            if (drawableId == -1) {
                drawableId = DEFAULT_DRAWABLE_ID;
            }
            if (gifWidth == -1) {
                gifWidth = DEFAULT_GIF_WIDTH;
            }
            if (gifHeight == -1) {
                gifHeight = DEFAULT_GIF_HEIGHT;
            }
            return new LoadingView(this);
        }
    }

    /**
     * dp convert to px.
     *
     * @param dpValue
     * @return
     */
    private int dp2px(float dpValue) {
        float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * Define popupWindow dismiss listener.
     */
    interface OnLoadingListener {
        void onDismiss();
    }
}
