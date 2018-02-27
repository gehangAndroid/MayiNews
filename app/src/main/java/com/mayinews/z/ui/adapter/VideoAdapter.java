package com.mayinews.z.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.mayinews.z.R;
import com.mayinews.z.domain.News;
import com.mayinews.z.domain.Video;
import com.mayinews.z.utils.CommonUtil;

import java.util.List;

/**
 * Created by Administrator on 2018/1/16 0016.
 */

public class VideoAdapter extends BaseQuickAdapter<Video.ResultBean,BaseViewHolder> {
    private Context context;
    private final static  int VIDEO_TYPE=1;  //视频类型
    private final static  int AD_TYPE=2; //广告类型

    public VideoAdapter(Context context,int layoutResId, @Nullable List<Video.ResultBean> data) {
        super(layoutResId, data);
        this.context=context;
    }


    @Override
    protected void convert(BaseViewHolder helper, Video.ResultBean item) {
        /**
         * 绑定数据
         */
        //设置封面
        Glide.with(context).load(CommonUtil.buildGlideUrl(item.getCover())).into((ImageView) helper.getView(R.id.iv_ccover));
        //设置标题
        helper.setText(R.id.tv_title,item.getTitle());
        //设置评论个数
        helper.setText(R.id.tv_comment_count,item.getComment());
        //设置作者头像

        //设置作者
        helper.setText(R.id.tv_nickname,item.getNickname());

        //设置时长

        helper.setText(R.id.tv_duration,CommonUtil.getDuration(item.getDuration()));





    }
}
