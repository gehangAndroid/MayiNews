package com.mayinews.z.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.astuetz.PagerSlidingTabStrip;
import com.mayinews.z.R;
import com.mayinews.z.domain.Channel;
import com.mayinews.z.domain.NewsChannel;
import com.mayinews.z.ui.adapter.NewsChannelPagerAdapter;
import com.mayinews.z.ui.base.BaseFragment;
import com.mayinews.z.utils.Constant;
import com.mayinews.z.utils.SPUtils;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.weyye.library.colortrackview.ColorTrackTabLayout;

import static com.mayinews.z.utils.Constant.TITLE_SELECTED;

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

    @BindView(R.id.icon_category)
    ImageView iconCategory;
    @BindView(R.id.vp)
    ViewPager mVp;

    @BindView(R.id.tab)
    TabLayout tab;

    private List<NewsListFragment> mFragments = new ArrayList<>();
    private NewsChannelPagerAdapter mTitlePagerAdapter;


    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void loadData() {

        getTitleData();
    }
    /**
     * 第一次可见时加载数据
     */


    /**
     * 获取导航栏的分类信息
     */
    private void getTitleData() {
        String selectTitle = (String) SPUtils.get(mActivity, TITLE_SELECTED, "");
        Log.e("TAG", "新闻标题缓存没" + selectTitle);
//        String unselectTitle = (String) SPUtils.get(mActivity,TITLE_UNSELECTED, "");
//        String unselectTitle = SharedPreferencesMgr.getString(TITLE_UNSELECTED, "");
        if (TextUtils.isEmpty(selectTitle)) {
            //本地没有title，去请求title分类
            OkHttpUtils.get().url(Constant.NEWS_CHANNEL_LIST).build().execute(new StringCallback() {
                @Override
                public void onError(Request request, Exception e) {
                    Log.e("TAG", "标题请求失败" + e.getMessage());
                }

                @Override
                public void onResponse(String response) {
                    NewsChannel channel = JSON.parseObject(response, NewsChannel.class);
                    int status = channel.getStatus();
                    if (status == 1) { //请求成功
                        List<NewsChannel.ResultBean> result = channel.getResult();
                        for (int i = 0; i < result.size(); i++) {
                            String title = result.get(i).getTitle();
                            String name = result.get(i).getName();
                            String id = result.get(i).getId();
                            mSelectedDatas.add(new Channel(title, name, id));

                        }
                        //添加到Sp
                        String channelJson = JSON.toJSONString(mSelectedDatas);
                        SPUtils.put(mActivity, TITLE_SELECTED, channelJson);

                        setTitle();
                    } else { //请求失败


                    }

                }
            });

        } else {
            //之前添加过

            List<Channel> selecteData = JSON.parseArray(selectTitle, Channel.class);
//            List<Channel> unselecteData = mGson.fromJson(unselectTitle, new TypeToken<List<Channel>>() {
//            }.getType());
            mSelectedDatas.addAll(selecteData);
            setTitle();
//            mUnSelectedDatas.addAll(unselecteData);
        }
    }

    @Override
    public void initView(View view) {
        super.initView(view);


    }

    @Override
    public void initData() {
        super.initData();


    }


    private void setTitle() {

        mVp.setOffscreenPageLimit(mSelectedDatas.size());
//        mFragments = new ArrayList<>();
        for (int i = 0; i < mSelectedDatas.size(); i++) {
            NewsListFragment newsListFragment = new NewsListFragment();
            Bundle bundle = new Bundle();
            bundle.putString(Constant.CHANNEL_ID, mSelectedDatas.get(i).getId());
            newsListFragment.setArguments(bundle);
            mFragments.add(newsListFragment);
        }

        mTitlePagerAdapter = new NewsChannelPagerAdapter(mFragments, mSelectedDatas, getChildFragmentManager());
         mVp.setAdapter(mTitlePagerAdapter);

         tab.setupWithViewPager(mVp);



    }







}
