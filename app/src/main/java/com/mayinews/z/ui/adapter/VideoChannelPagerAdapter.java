package com.mayinews.z.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mayinews.z.domain.Channel;
import com.mayinews.z.domain.Video;
import com.mayinews.z.ui.fragment.NewsListFragment;
import com.mayinews.z.ui.fragment.VideoListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by h on 2018/1/4 0004.
 */

public class VideoChannelPagerAdapter extends FragmentStatePagerAdapter{
    private List<VideoListFragment> mFragments;
    private List<Channel> mChannels;

    public VideoChannelPagerAdapter(List<VideoListFragment> fragmentList, List<Channel> channelList, FragmentManager fm) {
        super(fm);
        mFragments = fragmentList != null ? fragmentList :  new ArrayList<VideoListFragment>();
        mChannels = channelList != null ? channelList : new ArrayList<Channel>();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mChannels.get(position).title;
    }
}
