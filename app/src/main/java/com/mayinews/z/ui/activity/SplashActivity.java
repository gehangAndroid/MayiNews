package com.mayinews.z.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mayinews.z.R;
import com.mayinews.z.ui.base.BaseActivity;

public class SplashActivity extends BaseActivity {



    @Override
    protected int provideContentViewId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView() {
         new Handler().postDelayed(new Runnable() {
             @Override
             public void run() {
                  startActivity(new Intent(SplashActivity.this,MainActivity.class));
                  finish();
             }
         },2000);
    }
}
