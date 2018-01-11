package com.mayinews.z.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mayinews.z.domain.Channel;
import com.mayinews.z.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by h on 2018/1/4 0004.
 */

public class ChannelPagerAdapter  extends FragmentStatePagerAdapter{
    private List<Fragment> mFragments;
    private List<Channel> mChannels;

    public ChannelPagerAdapter(List<Fragment> fragmentList, List<Channel> channelList, FragmentManager fm) {
        super(fm);
        mFragments = fragmentList != null ? fragmentList :  new ArrayList<Fragment>();
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
