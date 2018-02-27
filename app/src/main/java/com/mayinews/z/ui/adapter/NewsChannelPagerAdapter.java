package com.mayinews.z.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.mayinews.z.domain.Channel;
import com.mayinews.z.ui.base.BaseFragment;
import com.mayinews.z.ui.fragment.NewsListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by h on 2018/1/4 0004.
 */

public class NewsChannelPagerAdapter extends FragmentStatePagerAdapter{
    private List<NewsListFragment> mFragments;
    private List<Channel> mChannels;

    public NewsChannelPagerAdapter(List<NewsListFragment> fragmentList, List<Channel> channelList, FragmentManager fm) {
        super(fm);
        mFragments = fragmentList != null ? fragmentList :  new ArrayList<NewsListFragment>();
        mChannels = channelList != null ? channelList : new ArrayList<Channel>();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        Log.e("TAG","也是两边吗");
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Log.e("TAG","进不来吗");
        return mChannels.get(position).title;
    }
}
