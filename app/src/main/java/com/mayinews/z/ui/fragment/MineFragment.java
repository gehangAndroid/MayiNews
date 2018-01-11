package com.mayinews.z.ui.fragment;

import android.util.Log;

import com.mayinews.z.R;
import com.mayinews.z.ui.base.BaseFragment;

/**
 * Created by Administrator on 2018/1/3 0003.
 */

public class MineFragment extends BaseFragment {
    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initData() {
        super.initData();
        Log.e("TAG","initData-Mine...");
    }

    @Override
    public void lazyLoad() {

    }
}
