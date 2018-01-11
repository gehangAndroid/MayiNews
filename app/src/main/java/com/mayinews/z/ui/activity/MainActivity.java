package com.mayinews.z.ui.activity;


import com.chaychan.library.BottomBarLayout;
import com.mayinews.z.R;
import com.mayinews.z.ui.MainTabAdapter;
import com.mayinews.z.ui.base.BaseActivity;
import com.mayinews.z.ui.base.BaseFragment;
import com.mayinews.z.ui.fragment.HomeFragment;
import com.mayinews.z.ui.fragment.MineFragment;
import com.mayinews.z.ui.fragment.VideoFragment;
import com.mayinews.z.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {


    @BindView(R.id.vp_content)
    NoScrollViewPager mVpContent;
    @BindView(R.id.bottom_bar)
    BottomBarLayout mBottomBarLayout;

    private List<BaseFragment> mFragments;
    private MainTabAdapter mTabAdapter;

    @Override
    protected int provideContentViewId() { //
        return R.layout.activity_main;
     }

    @Override
    public void initData() {
        //准备数据
        mFragments=new ArrayList<>();

        mFragments.add(new HomeFragment());

        mFragments.add(new VideoFragment());
        mFragments.add(new MineFragment());

    }


    @Override
    public void initListener() {
        mTabAdapter=new MainTabAdapter(mFragments,getSupportFragmentManager());
        mVpContent.setAdapter(mTabAdapter);
        mVpContent.setOffscreenPageLimit(mFragments.size());
        mBottomBarLayout.setViewPager(mVpContent);
    }
}
