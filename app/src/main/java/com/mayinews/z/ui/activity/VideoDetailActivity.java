package com.mayinews.z.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alivc.player.VcPlayerLog;
import com.aliyun.vodplayer.media.AliyunPlayAuth;
import com.aliyun.vodplayer.media.IAliyunVodPlayer;
import com.aliyun.vodplayerview.utils.ScreenUtils;
import com.aliyun.vodplayerview.widget.AliyunVodPlayerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mayinews.z.R;
import com.mayinews.z.domain.VideoDetail;
import com.mayinews.z.ui.adapter.MoreVideosAdapter;
import com.mayinews.z.ui.base.BaseActivity;
import com.mayinews.z.utils.CommonUtil;
import com.mayinews.z.utils.Constant;
import com.mayinews.z.utils.ScreenStatusController;
import com.mayinews.z.utils.ToastUtils;
import com.mayinews.z.view.DividerDecoration;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoDetailActivity extends BaseActivity {


    @BindView(R.id.video_view)
    AliyunVodPlayerView mAliyunVodPlayerView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_video_comment)
    TextView tvVideoComment;
    @BindView(R.id.rootView)
    LinearLayout rootView;
    @BindView(R.id.iv_comment)
    ImageView ivComment;
    @BindView(R.id.iv_coll)
    ImageView ivColl;
    @BindView(R.id.iv_share)
    ImageView ivShare;
    private String channl;//当前视频的类别
    private String vid;//当前视频的vid
    private List<VideoDetail.MorevideoBean> morevideos = new ArrayList<>();//更多视频

    private MoreVideosAdapter adapter;

    ScreenStatusController mScreenStatusController;

    private boolean isStrangePhone() {
        boolean strangePhone = Build.DEVICE.equalsIgnoreCase("mx5")
                || Build.DEVICE.equalsIgnoreCase("Redmi Note2")
                || Build.DEVICE.equalsIgnoreCase("Z00A_1")
                || Build.DEVICE.equalsIgnoreCase("hwH60-L02")
                || Build.DEVICE.equalsIgnoreCase("hermes")
                || (Build.DEVICE.equalsIgnoreCase("V4") && Build.MANUFACTURER.equalsIgnoreCase("Meitu"));

        VcPlayerLog.e("lfj1115 ", " Build.Device = " + Build.DEVICE + " , isStrange = " + strangePhone);
        return strangePhone;

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_video_detail;
    }

    @Override
    public void initView() {
        mAliyunVodPlayerView.setKeepScreenOn(true);//保持屏幕敞亮
        mAliyunVodPlayerView.setTheme(AliyunVodPlayerView.Theme.Orange);//设置主题
        mAliyunVodPlayerView.setCirclePlay(false);//默认不循环播放
        recyclerView.setLayoutManager(new LinearLayoutManager(VideoDetailActivity.this));
        recyclerView.addItemDecoration(new DividerDecoration(VideoDetailActivity.this, LinearLayoutManager.VERTICAL, R.color.re_divider_grey, 1, 0, 0));
        adapter = new MoreVideosAdapter(VideoDetailActivity.this, R.layout.more_videos_item, morevideos);
        recyclerView.setAdapter(adapter);
//        adapter.addHeaderView(getHeadView());
    }

    private View getHeadView() {

//        View view = View.inflate(this, R.layout.video_head_view, null);

        return null;
    }

    @Override
    public void initData() {

        Intent intent = getIntent();
        channl = intent.getStringExtra("channl");
        vid = intent.getStringExtra("vid");
        String cover = intent.getStringExtra("cover");

//设置封面
        mAliyunVodPlayerView.setCoverUri(CommonUtil.buildGlideUrl(cover).toString());
        //请求当前视频播放权限
        OkHttpUtils.get().url(Constant.VIDEO_DETAIL + "/channel/" + channl + "/vid/" + vid)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                VideoDetail videoDetail = JSON.parseObject(response, VideoDetail.class);
                int status = videoDetail.getStatus();
                if (status == 200) {
                    //获取播放权限
                    String playauth = videoDetail.getResult().getPlayauth();
                    //进行播放的操作
                    playVideo(playauth);
                    List<VideoDetail.MorevideoBean> morevideo = videoDetail.getMorevideo();
                    morevideos.addAll(morevideo);
                    adapter.notifyDataSetChanged();

                }
            }
        });

    }

    //播放视频
    private void playVideo(String playauth) {

//播放方式三：使用vid+playAuth方式播放（V3.2.0之前版本使用，兼容老用户）
        AliyunPlayAuth.AliyunPlayAuthBuilder aliyunPlayAuthBuilder = new AliyunPlayAuth.AliyunPlayAuthBuilder();
        aliyunPlayAuthBuilder.setVid(vid);
        aliyunPlayAuthBuilder.setPlayAuth(playauth);
        aliyunPlayAuthBuilder.setQuality(IAliyunVodPlayer.QualityValue.QUALITY_LOW);
        //aliyunVodPlayer.setAuthInfo(aliyunPlayAuthBuilder.build());
        AliyunPlayAuth mPlayAuth = aliyunPlayAuthBuilder.build();
        mAliyunVodPlayerView.setAuthInfo(mPlayAuth);

    }

    @Override
    public void initListener() {
        //设置播放器监听
        mAliyunVodPlayerView.setOnPreparedListener(new IAliyunVodPlayer.OnPreparedListener() {
            @Override
            public void onPrepared() {
                //准备完成时触发
//                ToastUtils.showToast("准备成功");
                mAliyunVodPlayerView.start();
            }
        });
        mAliyunVodPlayerView.setOnCompletionListener(new IAliyunVodPlayer.OnCompletionListener() {
            @Override
            public void onCompletion() {
                //播放正常完成时触发
                ToastUtils.showToast("播放完毕");
            }
        });
        mAliyunVodPlayerView.setOnFirstFrameStartListener(new IAliyunVodPlayer.OnFirstFrameStartListener() {
            @Override
            public void onFirstFrameStart() {
                //首帧显示时触发
            }
        });
        mAliyunVodPlayerView.setOnChangeQualityListener(new IAliyunVodPlayer.OnChangeQualityListener() {
            @Override
            public void onChangeQualitySuccess(String finalQuality) {
                //清晰度切换成功时触发
            }

            @Override
            public void onChangeQualityFail(int code, String msg) {
                //清晰度切换失败时触发
            }
        });
        mAliyunVodPlayerView.setOnStoppedListner(new IAliyunVodPlayer.OnStoppedListener() {
            @Override
            public void onStopped() {
                //使用stop接口时触发
            }
        });
        mAliyunVodPlayerView.setOnCircleStartListener(new IAliyunVodPlayer.OnCircleStartListener() {
            @Override
            public void onCircleStart() {
                //循环播放开始
            }
        });

        mScreenStatusController = new ScreenStatusController(this);
        mScreenStatusController.setScreenStatusListener(new ScreenStatusController.ScreenStatusListener() {
            @Override
            public void onScreenOn() {
            }

            @Override
            public void onScreenOff() {

            }
        });
        mScreenStatusController.startListen();


        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                VideoDetail.MorevideoBean bean = morevideos.get(position);
                String cid = bean.getCid();//视频的分类id
                String vid = bean.getVid();//视频的id;
                Intent intent = new Intent(VideoDetailActivity.this, VideoDetailActivity.class);
                intent.putExtra("vid", vid);
                intent.putExtra("channl", cid); //当前页的channelId
                intent.putExtra("cover", bean.getCover());//封面
                startActivity(intent);
            }
        });

        tvVideoComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCommentPop();
            }
        });

        ivComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VideoDetailActivity.this, CommentListActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showCommentPop() {
        View view = LayoutInflater.from(this).inflate(R.layout.nickname_pop, null, false);
        PopupWindow commentPopWindow = new PopupWindow(this);
        commentPopWindow.setContentView(view);
        commentPopWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        commentPopWindow.setFocusable(true);
        commentPopWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        commentPopWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //让popupwindow不被输入法隐藏
        commentPopWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        commentPopWindow.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showInputMethod();
            }
        }, 100);
    }

    private void showInputMethod() {
        //自动弹出键盘
        InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        //强制隐藏Android输入法窗口
        // inputManager.hideSoftInputFromWindow(edit.getWindowToken(),0);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAliyunVodPlayerView != null) {
            mAliyunVodPlayerView.onStop();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updatePlayerViewMode();
        if (mAliyunVodPlayerView != null) {
            mAliyunVodPlayerView.onResume();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d("lfj1019", " orientation = " + getResources().getConfiguration().orientation);
        updatePlayerViewMode();
    }

    private void updatePlayerViewMode() {
        if (mAliyunVodPlayerView != null) {
            int orientation = getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {                //转为竖屏了。
                //显示状态栏
//                if (!isStrangePhone()) {
//                    getSupportActionBar().show();
//                }

                this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                mAliyunVodPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);

                //设置view的布局，宽高之类
                LinearLayout.LayoutParams aliVcVideoViewLayoutParams = (LinearLayout.LayoutParams) mAliyunVodPlayerView.getLayoutParams();
                aliVcVideoViewLayoutParams.height = (int) (ScreenUtils.getWidth(this) * 9.0f / 16);
                aliVcVideoViewLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
//                if (!isStrangePhone()) {
//                    aliVcVideoViewLayoutParams.topMargin = getSupportActionBar().getHeight();
//                }

            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {                //转到横屏了。
                //隐藏状态栏
                if (!isStrangePhone()) {
                    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    mAliyunVodPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
                }

                //设置view的布局，宽高
                LinearLayout.LayoutParams aliVcVideoViewLayoutParams = (LinearLayout.LayoutParams) mAliyunVodPlayerView.getLayoutParams();
                aliVcVideoViewLayoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
                aliVcVideoViewLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
//                if (!isStrangePhone()) {
//                    aliVcVideoViewLayoutParams.topMargin = 0;
//                }

            }

        }
    }

    @Override
    protected void onDestroy() {
        if (mAliyunVodPlayerView != null) {
            mAliyunVodPlayerView.onDestroy();
            mAliyunVodPlayerView = null;
        }
        mScreenStatusController.stopListen();
        super.onDestroy();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        //解决某些手机上锁屏之后会出现标题栏的问题。
        VcPlayerLog.d("lfj1030", "onWindowFocusChanged = " + hasFocus);
        updatePlayerViewMode();
    }


}
