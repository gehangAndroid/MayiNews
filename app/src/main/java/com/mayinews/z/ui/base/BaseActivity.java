package com.mayinews.z.ui.base;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mayinews.z.R;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //子类不再需要设置布局ID，也不再需要使用ButterKnife.bind()
        setContentView(provideContentViewId());
        ButterKnife.bind(this);
        initView();   //子类需要初始化View时重写
        initData();   //子类需要绑定数据时重写
        initListener();  //控件设置监听事件时重写
    }

    public void initListener() {

    }

    public void initData() {

    }


    public void initView() {

    }

    protected abstract int provideContentViewId();

}
