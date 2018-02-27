package com.mayinews.z.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * 签到的7个圆形图形
 */

public class SignInView extends ViewGroup {
    public SignInView(Context context) {
        this(context,null);
    }

    public SignInView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }
    //进行初始化
    public SignInView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);




    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }



}
