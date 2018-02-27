package com.mayinews.z.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.mayinews.z.R;
import com.mayinews.z.domain.Channel;
import com.mayinews.z.domain.NewsChannel;
import com.mayinews.z.domain.VideoChannel;
import com.mayinews.z.ui.base.BaseActivity;
import com.mayinews.z.utils.Constant;
import com.squareup.okhttp.Request;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import static com.mayinews.z.utils.Constant.TITLE_SELECTED;

public class SplashActivity extends BaseActivity {


    // IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI api;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initData() {

        //注册应用到微信
// 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, Constant.APPID, true);
        api.registerApp(Constant.APPID);
    }

    @Override
    public void initView() {


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }, 2000);
    }


}
