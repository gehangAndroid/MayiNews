package com.mayinews.z.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mayinews.z.R;
import com.mayinews.z.ui.base.BaseActivity;
import com.mayinews.z.utils.Constant;
import com.mayinews.z.utils.SPUtils;
import com.mayinews.z.utils.ToastUtils;
import com.mayinews.z.view.CacheUtil;

import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.rl_perfect_info)
    RelativeLayout rlPerfectInfo;
    @BindView(R.id.tv_cachesize)
    TextView tvCachesize;
    @BindView(R.id.rl_clearcache)
    RelativeLayout rlClearcache;
    @BindView(R.id.tv_loginout)
    TextView tvLoginout;
    private boolean isLogin;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initListener() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        rlPerfectInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开个人完善资料界面
                Intent intent = new Intent(SettingActivity.this, PerfectInforActivity.class);

                startActivity(intent);
            }
        });
        rlClearcache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast("成功清理缓存");
                CacheUtil.clearAllCache(SettingActivity.this);
                tvCachesize.setText("0K");
            }
        });
        tvLoginout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isLogin) {

                    //执行退出登录操作
                    SPUtils.put(SettingActivity.this, Constant.ISLOGIN, false);
                    //关闭当前页面
                     finish();
                     MainActivity.ISLOGINOUT=true;
//                    //跳转到MainActivity
//                    startActivity(new Intent(SettingActivity.this, MainActivity.class));

                    finish();
                } else {

                    startActivity(new Intent(SettingActivity.this, LoginActivity.class));
                    finish();

                }


            }
        });
    }

    @Override
    public void initView() {
        title.setText("设置");
        //显示缓存大小
        try {
            tvCachesize.setText(CacheUtil.getTotalCacheSize(this));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //判断登录状态
        isLogin = (boolean) SPUtils.get(this, Constant.ISLOGIN, false);
        if (isLogin) {
            //是登录状态
            tvLoginout.setText("退出登录");
        } else {  //不是登录状态
            tvLoginout.setText("登录");
        }

    }


}
