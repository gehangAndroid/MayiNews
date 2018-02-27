package com.mayinews.z.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.gxz.PagerSlidingTabStrip;
import com.mayinews.z.R;
import com.mayinews.z.domain.Channel;
import com.mayinews.z.domain.NewsChannel;
import com.mayinews.z.domain.VideoChannel;
import com.mayinews.z.ui.adapter.VideoChannelPagerAdapter;
import com.mayinews.z.ui.base.BaseFragment;
import com.mayinews.z.utils.Constant;
import com.mayinews.z.utils.SPUtils;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.weyye.library.colortrackview.ColorTrackTabLayout;

import static com.mayinews.z.utils.Constant.TITLE_SELECTED;
import static com.mayinews.z.utils.Constant.TITLE_UNSELECTED;
import static com.mayinews.z.utils.Constant.VIDEO_CHANNEL_JSON;

/**
 * Created by Administrator on 2018/1/3 0003.
 */

public class VideoFragment extends BaseFragment {
    @BindView(R.id.tab)
    TabLayout tab;

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    //准备数据源
    List<VideoListFragment> mFragments = new ArrayList<>();
    private List<Channel> videoChannels=new ArrayList<>();//保存视频分类信息
    private VideoChannelPagerAdapter mTitlePagerAdapter;

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_video;
    }

    @Override
    public void initData() {
        super.initData();


    }

    /**
     * 第一次可见时加载数据
     */
    @Override

    public void loadData() {
        Log.e("TAG","第一次可见Video");
        getTitleData();
    }


    /**
     * 获取导航栏的分类信息
     */
    private void getTitleData() {
        String videoChannelJson = (String) SPUtils.get(mActivity,VIDEO_CHANNEL_JSON, "");
        if (TextUtils.isEmpty(videoChannelJson)) {
            //本地没有title，去请求title分类
            OkHttpUtils.get().url(Constant.VIDEO_CHANNEL_LIST).build().execute(new StringCallback() {
                @Override
                public void onError(Request request, Exception e) {
                    Log.e("TAG","标题请求失败"+e.getMessage());
                }

                @Override
                public void onResponse(String response) {
                    VideoChannel videoChannel = JSON.parseObject(response, VideoChannel.class);
                    if (videoChannel.getStatus() == 1) {
                        List<VideoChannel.ResultBean> result = videoChannel.getResult();

                        for (int i = 0; i < result.size(); i++) {
                            VideoChannel.ResultBean resultBean = result.get(i);
                            String tid = resultBean.getTid();
                            String name = resultBean.getName();
                            videoChannels.add(new Channel(name, "", tid));
                            //保存分类数据
                            Log.e("TAG", "videoChannels" + videoChannels.size());
                            String channelJson = JSON.toJSONString(videoChannels);
                            SPUtils.put(mActivity,VIDEO_CHANNEL_JSON, channelJson);



                        }
                        setTitle();

                    } else {


                    }
                }
                });

            }else {
                //之前添加过

                List<Channel> selecteData = JSON.parseArray(videoChannelJson,Channel.class);
                videoChannels.addAll(selecteData);
                setTitle();
            }
        }

    private void setTitle() {
         viewPager.setOffscreenPageLimit(videoChannels.size());
//        mFragments = new ArrayList<>();
        Log.e("TAG","视频分类数量  "+videoChannels.size());
        for (int i = 0; i < videoChannels.size(); i++) {
            VideoListFragment videoListFragment = new VideoListFragment();
            Bundle bundle = new Bundle();

            bundle.putString(Constant.CHANNEL_ID,videoChannels.get(i).getId());

            videoListFragment.setArguments(bundle);
            mFragments.add(videoListFragment);
        }
        mTitlePagerAdapter = new VideoChannelPagerAdapter(mFragments, videoChannels, getChildFragmentManager());
        viewPager.setAdapter(mTitlePagerAdapter);
        tab.setupWithViewPager(viewPager);
    }

}
