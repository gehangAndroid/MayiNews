package com.mayinews.z.ui.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.alibaba.fastjson.JSON;
import com.chaychan.library.UIUtils;
import com.mayinews.z.R;
import com.mayinews.z.domain.News;
import com.mayinews.z.ui.adapter.NewsAdapter;
import com.mayinews.z.ui.base.BaseFragment;
import com.mayinews.z.utils.Constant;
import com.mayinews.z.view.DividerDecoration;
import com.mayinews.z.view.TipView;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by h on 2018/1/4 0004.
 */

@SuppressLint("ValidFragment")
public class NewsListFragment extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate{
    @BindView(R.id.tipView)
    TipView tipView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    Unbinder unbinder;
    @BindView(R.id.fl_content)
    FrameLayout flContent;

    @BindView(R.id.refresh_lay)
    BGARefreshLayout refreshLayout;
    private String mChannelCode;//当前的频道号
    private List<News.ResultBean> data=new ArrayList<>();
    private NewsAdapter adapter;

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_newslist;
    }

    @Override
    public void lazyLoad() {
        //根据Model层请求数据
        Log.e("TAG","RE222"+refreshLayout);
        refreshLayout.beginRefreshing();

        getData();
        //
    }

    private void getData() {
        OkHttpUtils.get().url(Constant.NEWS_LIST).build().execute(new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                News news = JSON.parseObject(response, News.class);
                data.addAll(news.getResult());
                adapter.notifyDataSetChanged();
                refreshLayout.endRefreshing();

            }
        });

    }

    @Override
    public void initData() {
        super.initData();
        mChannelCode = getArguments().getString(Constant.CHANNEL_CODE); //获取频道号


    }

    @Override
    public void initView(View view) {
        Log.e("TAG","refreshLayout="+refreshLayout);
        super.initView(view);
        refreshLayout.setDelegate(this);
        BGANormalRefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(mActivity, false);
        // 设置下拉刷新
        refreshViewHolder.setRefreshViewBackgroundColorRes(R.color.pull_refresh_bg);//背景色
        refreshViewHolder.setPullDownRefreshText("下拉刷新");//下拉的提示文字
        refreshViewHolder.setReleaseRefreshText("松开刷新");//松开的提示文字
        refreshViewHolder.setRefreshingText("刷新中");//刷新中的提示文字


        // 设置下拉刷新和上拉加载更多的风格
        refreshLayout.setRefreshViewHolder(refreshViewHolder);
        refreshLayout.shouldHandleRecyclerViewLoadingMore(recyclerView);


        //添加适配器
        adapter=new NewsAdapter(mActivity,data);
        refreshLayout.shouldHandleRecyclerViewLoadingMore(recyclerView);
         //初始化RecyclerView
         recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
         recyclerView.addItemDecoration(new DividerDecoration(mActivity,LinearLayoutManager.VERTICAL,R.color.re_divider_grey,1,0,0));
         recyclerView.setAdapter(adapter);



    }

    @Override
    public void initListener() {
        super.initListener();

    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();
        unbinder.unbind();
    }




    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
           Log.e("TAG","GENGXIN。。。。。。");
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }
}
