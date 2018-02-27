package com.mayinews.z.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mayinews.z.R;
import com.mayinews.z.ui.MainTabAdapter;
import com.mayinews.z.ui.base.BaseActivity;
import com.mayinews.z.ui.base.BaseFragment;
import com.mayinews.z.ui.fragment.HomeFragment;
import com.mayinews.z.ui.fragment.MineFragment;
import com.mayinews.z.ui.fragment.VideoFragment;
import com.mayinews.z.utils.Constant;
import com.mayinews.z.utils.SPUtils;
import com.mayinews.z.utils.ToastUtils;
import com.mayinews.z.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

     public static  boolean ISLOGINOUT=false;
    @BindView(R.id.vp_content)
    NoScrollViewPager mVpContent;
    @BindView(R.id.iv_toutiao)
    ImageView ivToutiao;
    @BindView(R.id.tv_toutiao)
    TextView tvToutiao;
    @BindView(R.id.ll_toutiao)
    LinearLayout llToutiao;
    @BindView(R.id.iv_video)
    ImageView ivVideo;
    @BindView(R.id.tv_video)
    TextView tvVideo;
    @BindView(R.id.iv_mine)
    ImageView ivMine;
    @BindView(R.id.tv_mine)
    TextView tvMine;
    @BindView(R.id.llBottom)
    LinearLayout llBottom;
//    BottomBarLayout mBottomBarLayout;

    private List<BaseFragment> mFragments;
    private View lastSelectedIcon;
    private View lastSelectedText;
    private MainTabAdapter mTabAdapter;
    private boolean isExit=false;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit=false;
        }
    };
    private int oldSelectPosition; //记录点击下面导航栏的哪个
    @Override
    protected int provideContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        //准备数据
        mFragments = new ArrayList<>();

        mFragments.add(new HomeFragment());

        mFragments.add(new VideoFragment());
        mFragments.add(new MineFragment());

    }


    @Override
    public void initListener() {
        mTabAdapter=new MainTabAdapter(mFragments,getSupportFragmentManager());
        mVpContent.setAdapter(mTabAdapter);
        mVpContent.setOffscreenPageLimit(mFragments.size());
//        mBottomBarLayout.setViewPager(mVpContent);






         for (int i=0;i<llBottom.getChildCount();i++){
                 if(i==0){
                     //默认选中首页
                     setSelectIcon(ivToutiao, tvToutiao);

                 }
             final int finalI = i;
             llBottom.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {

                         LinearLayout rl = (LinearLayout) v;
                         ImageView icon = (ImageView) rl.getChildAt(0);
                         TextView text = (TextView) rl.getChildAt(1);


//                         mVpContent.setCurrentItem(finalI,false);

                         if(finalI==2){
//                             SPUtils.put(MainActivity.this,Constant.ISLOGIN,true);
                             //点击我的按钮，先判断是否是登陆状态,false代表没有登录，true代表登录状态
                             boolean loginStatus = (boolean) SPUtils.get(MainActivity.this,Constant.ISLOGIN,false);
                             if(!loginStatus){ //没有登录
                                 Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                 startActivity(intent);
                                 overridePendingTransition(R.anim.login_activity_in,R.anim.end_alpha);


                             }else{
                                 if (lastSelectedIcon != null) lastSelectedIcon.setSelected(false);
                                 if (lastSelectedText != null) lastSelectedText.setSelected(false);
                                 mVpContent.setCurrentItem(finalI,false);
                                 setSelectIcon(icon,text);
                             }
                         }else{
                             if (lastSelectedIcon != null) lastSelectedIcon.setSelected(false);
                             if (lastSelectedText != null) lastSelectedText.setSelected(false);
                             mVpContent.setCurrentItem(finalI,false);
                             setSelectIcon(icon,text);
                         }


                     }
                 });



         }

    }





    private void setSelectIcon(ImageView iv, TextView tv) {
        iv.setSelected(true);
        tv.setSelected(true);
        lastSelectedIcon = iv;
        lastSelectedText = tv;




    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            handler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finish();

        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if(ISLOGINOUT){
            setSelectIcon(ivToutiao, tvToutiao);
            ivVideo.setSelected(false);
            tvVideo.setSelected(false);
            ivMine.setSelected(false);
            tvMine.setSelected(false);
            mVpContent.setCurrentItem(0,false);
            ISLOGINOUT=false; }

    }
}
