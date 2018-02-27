package com.mayinews.z.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.animation.BaseAnimation;
import com.github.nukc.stateview.StateView;
import com.mayinews.z.R;
import com.mayinews.z.domain.News;
import com.mayinews.z.ui.activity.WebViewActivity;
import com.mayinews.z.ui.adapter.NewsAdapter;
import com.mayinews.z.ui.base.BaseFragment;
import com.mayinews.z.utils.CommonUtil;
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

import static com.chad.library.adapter.base.BaseQuickAdapter.SCALEIN;

/**
 * Created by h on 2018/1/4 0004.
 */

@SuppressLint("ValidFragment")
public class NewsListFragment extends BaseFragment {
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;
    @BindView(R.id.tipView)
    TipView tipView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.fl_content)
    FrameLayout flContent;
    @BindView(R.id.loadview)
    ImageView loadview;


    //    @BindView(R.id.refresh_lay)
//    TwinklingRefreshLayout refreshLayout;
    private String mChannelId;//当前的频道Id
    private List<News.ResultBean> data = new ArrayList<>();
    private NewsAdapter adapter;
    private int currentPage = 1;//当前页
    private boolean isRefresh = false; //标记是在刷新
    private boolean isVideoList;

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_newslist;
    }

    @Override
    protected void loadData() {
        Log.e("TAG","第一次可见NewsList");
        getData(Constant.NEWS_LIST_PULLUP + "/channel/" + mChannelId);
    }

    /**
     * 第一次可见时加载数据
     */

//    @Override
//    public void lazyLoad() {
//        //显示加载中的试图
////        mStateView.showLoading();
//        Log.e("TAG", "渠道号" + mChannelId);
//        getData(Constant.NEWS_LIST_PULLUP + "/channl/" + mChannelId);
//        //
//    }

    private void getData(String url) {

        boolean available = CommonUtil.isNetworkAvailable(mActivity);
        if (available) {
            OkHttpUtils.get().url(url).build().execute(new StringCallback() {
                @Override
                public void onError(Request request, Exception e) {
                            Log.e("TAG","getData错误信息"+e.getMessage());
                           loadview.setVisibility(View.GONE);

                }

                @Override
                public void onResponse(String response) {
                    News news = JSON.parseObject(response, News.class);
                    List<News.ResultBean> result = news.getResult();
                    Log.e("TAG","result"+result+  "   result.size"+result.size());
                    if (result != null && result.size() > 0) {
                        if (isRefresh) { //如果是上拉刷新 ，增加数据到最前面
                            data.addAll(0, result);
                            isRefresh = false;
                        } else {

                            data.addAll(result);
                            adapter.loadMoreComplete();
                        }

                        adapter.notifyDataSetChanged(); //刷新数据
                        mStateView.showContent();

                          loadview.setVisibility(View.GONE);
                        //显示刷新了多少
                        if (srl.isRefreshing()) {
                            tipView.show("「蚂蚁资讯」已为您更新" + news.getCount() + "条数据");
                            //停止刷新
                            srl.setRefreshing(false);
                        }


                    } else {

                        if (isRefresh) {
                            tipView.show("没有更多数据");
                        } else {
                            adapter.loadMoreEnd();//没有数据全部加载完成

                        }

                    }


                }
            });
        } else {
            loadview.setVisibility(View.GONE);
            tipView.show("网络错误");
            mStateView.showRetry();
            //停止刷新
            srl.setRefreshing(false);

        }


    }

    @Override
    public void initData() {
        super.initData();
        mChannelId = getArguments().getString(Constant.CHANNEL_ID); //获取频道号
        isVideoList = getArguments().getBoolean(Constant.IS_VIDEO_LIST); //获取s是否是视频列表


    }

    @Override
    public void initView(View view) {

        super.initView(view);
        srl.setColorSchemeColors(getResources().getColor(R.color.appColor));
        //设置加载新闻时的资源布局
        mStateView.setLoadingResource(R.layout.news_page_loading);


        //添加适配器
        adapter = new NewsAdapter(mActivity, data);
        adapter.isFirstOnly(false);

         adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        //初始化RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerView.addItemDecoration(new DividerDecoration(mActivity, LinearLayoutManager.VERTICAL, R.color.re_divider_grey, 1, 0, 0));
//        adapter.disableLoadMoreIfNotFullPage();
        recyclerView.setAdapter(adapter);


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void initListener() {

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                News.ResultBean resultBean = data.get(position);
//                resultBean.getTitle();
                String id = resultBean.getId();
                String title = resultBean.getTitle();
                String imageUrl = resultBean.getImages().get(0);
                Intent intent = new Intent(mActivity, WebViewActivity.class);
                intent.putExtra("channelId", mChannelId); //当前页的channelId
                intent.putExtra("id", id);//文章的id；
                intent.putExtra("title",title);//文章的标题
                intent.putExtra("imageUrl",imageUrl);//文章的封面
                //还需要文章的内容传递过去




                startActivity(intent);


            }
        });

        //上拉刷新的监听
        adapter.setEnableLoadMore(true);
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                /**
                 *   下拉刷新数据
                 */
                isRefresh = true;
                //先清除页面数据
//                data.clear();
                adapter.notifyDataSetChanged();
//
                getData(Constant.NEWS_REFRESH + "/channel/" + mChannelId);
                adapter.setEnableLoadMore(true);

            }
        });

        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                currentPage++;
                Log.e("TAG", "加载更多....");
//                try {
//                    Thread.sleep(500);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                getData(Constant.NEWS_LIST_PULLUP + "/channel/" + mChannelId + "/page/" + currentPage);


            }
        }, recyclerView);
        //点击重试的监听
        mStateView.setOnRetryClickListener(new StateView.OnRetryClickListener() {
            @Override
            public void onRetryClick() {
                getData(Constant.NEWS_LIST_PULLUP + "/channel/" + mChannelId);
            }
        });
    }


    @Override
    public void onDestroyView() {

        super.onDestroyView();

    }



}
