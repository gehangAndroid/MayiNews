package com.mayinews.z.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mayinews.z.R;
import com.mayinews.z.domain.VideoDetail;
import com.mayinews.z.utils.CommonUtil;

import java.util.List;

/**
 * Created by Administrator on 2018/1/17 0017.
 */

public class MoreVideosAdapter extends BaseQuickAdapter<VideoDetail.MorevideoBean,BaseViewHolder> {
     private Context context;

    public MoreVideosAdapter(Context context,int layoutResId, @Nullable List<VideoDetail.MorevideoBean> data) {
        super(layoutResId, data);
        this.context=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, VideoDetail.MorevideoBean item) {
            //绑定数据
        //设置封面
        Glide.with(context).load(CommonUtil.buildGlideUrl(item.getCover())).into((ImageView) helper.getView(R.id.more_video_cover));
        //设置标题
        helper.setText(R.id.tv_title,item.getTitle());
        //设置评论个数
        helper.setText(R.id.tv_comment_count,item.getComment());
        //设置作者头像


    }
}
