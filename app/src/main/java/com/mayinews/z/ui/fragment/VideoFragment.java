package com.mayinews.z.ui.fragment;

import android.util.Log;

import com.mayinews.z.R;
import com.mayinews.z.ui.base.BaseFragment;

/**
 * Created by Administrator on 2018/1/3 0003.
 */

public class VideoFragment extends BaseFragment {
    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_video;
    }

    @Override
    public void initData() {
        super.initData();
        Log.e("TAG","data-VIDEO...");
    }

    @Override
    public void lazyLoad() {
        Log.e("TAG","lazyLoad-VIDEO...");
    }
}
