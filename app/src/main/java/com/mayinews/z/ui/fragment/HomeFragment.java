package com.mayinews.z.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mayinews.z.R;
import com.mayinews.z.domain.Channel;
import com.mayinews.z.ui.adapter.ChannelPagerAdapter;
import com.mayinews.z.ui.base.BaseFragment;
import com.mayinews.z.utils.CommonUtil;
import com.mayinews.z.utils.Constant;
import com.mayinews.z.utils.SharedPreferencesMgr;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.weyye.library.colortrackview.ColorTrackTabLayout;

import static com.mayinews.z.utils.Constant.TITLE_SELECTED;
import static com.mayinews.z.utils.Constant.TITLE_UNSELECTED;

/**
 * Created by h on 2018/1/3 0003.
 */

public class HomeFragment extends BaseFragment {

    public List<Channel> mSelectedDatas = new ArrayList<>();
    public List<Channel> mUnSelectedDatas = new ArrayList<>();
    @BindView(R.id.hot_news)
    ImageView hotNews;
    @BindView(R.id.search)
    ImageView search;
    @BindView(R.id.tab)
    ColorTrackTabLayout tab;
    @BindView(R.id.icon_category)
    ImageView iconCategory;
    @BindView(R.id.vp)
    ViewPager mVp;
    Unbinder unbinder;
    private List<Fragment> mFragments;
    private Gson mGson = new Gson();
    private ChannelPagerAdapter mTitlePagerAdapter;

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_home;
    }







    /**
     * 获取导航栏的分类信息
     */
    private void getTitleData() {
        String selectTitle = SharedPreferencesMgr.getString(TITLE_SELECTED, "");
        String unselectTitle = SharedPreferencesMgr.getString(TITLE_UNSELECTED, "");
        if (TextUtils.isEmpty(selectTitle) || TextUtils.isEmpty(unselectTitle)) {
            //本地没有title
            String[] titleStr = getResources().getStringArray(R.array.home_title);
            String[] titlesCode = getResources().getStringArray(R.array.home_title_code);
            //默认添加了全部频道
            for (int i = 0; i < titlesCode.length; i++) {
                String t = titleStr[i];
                String code = titlesCode[i];
                mSelectedDatas.add(new Channel(t, code));
            }

            String selectedStr = mGson.toJson(mSelectedDatas);
            SharedPreferencesMgr.setString(TITLE_SELECTED, selectedStr);
        } else {
            //之前添加过
            List<Channel> selecteData = mGson.fromJson(selectTitle, new TypeToken<List<Channel>>() {
            }.getType());
            List<Channel> unselecteData = mGson.fromJson(unselectTitle, new TypeToken<List<Channel>>() {
            }.getType());
            mSelectedDatas.addAll(selecteData);
            mUnSelectedDatas.addAll(unselecteData);
        }
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        mVp.setOffscreenPageLimit(mSelectedDatas.size());
    }

    @Override
    public void initData() {
        super.initData();
    }




    /**
     * 第一次可见时加载数据
     */
    @Override
    public void lazyLoad() {

        getTitleData();
        mFragments = new ArrayList<>();
        for (int i = 0; i < mSelectedDatas.size(); i++) {
            Fragment newsListFragment = new NewsListFragment();
            Bundle bundle = new Bundle();
            bundle.putString(Constant.CHANNEL_CODE,mSelectedDatas.get(i).titleCode);
            newsListFragment.setArguments(bundle);
            mFragments.add(newsListFragment);
        }
        mTitlePagerAdapter = new ChannelPagerAdapter(mFragments, mSelectedDatas, getChildFragmentManager());
        mVp.setAdapter(mTitlePagerAdapter);
        mVp.setOffscreenPageLimit(mSelectedDatas.size());

        tab.setTabPaddingLeftAndRight(CommonUtil.dip2px(10), CommonUtil.dip2px(10));
        tab.setupWithViewPager(mVp);
        tab.post(new Runnable() {
            @Override
            public void run() {
                //设置最小宽度，使其可以在滑动一部分距离
                ViewGroup slidingTabStrip = (ViewGroup) tab.getChildAt(0);
                slidingTabStrip.setMinimumWidth(slidingTabStrip.getMeasuredWidth() + iconCategory.getMeasuredWidth());
            }
        });
        //隐藏指示器
        tab.setSelectedTabIndicatorHeight(0);


    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
        Log.e("TAG","看看第一次会不会进来");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
