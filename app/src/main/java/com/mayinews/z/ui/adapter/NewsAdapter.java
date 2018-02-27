package com.mayinews.z.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.mayinews.z.R;
import com.mayinews.z.domain.News;
import com.mayinews.z.utils.CommonUtil;
import com.mayinews.z.utils.Constant;

import java.util.List;

/**
 *    新闻列表的适配器
 */

public class NewsAdapter extends BaseQuickAdapter<News.ResultBean,BaseViewHolder>{

    /**
     * 一张图片的布局类型
     */
    private static final int ONE_PICS_NEWS=1;


    /**
     * 三张图片的布局类型
     */
    private static final int THREE_PICS_NEWS = 2;



    private  Context context;

    public NewsAdapter(Context context, @Nullable List<News.ResultBean> data) {
        super(data);
        this.context=context;

        /**
         * 分类布局
         */

        //step1
        setMultiTypeDelegate(new MultiTypeDelegate<News.ResultBean>() {
            @Override
            protected int getItemType(News.ResultBean resultBean) {

                if(resultBean.getImages().size()==1){

                    return  ONE_PICS_NEWS;
                }else if(resultBean.getImages().size()==3){


                    return THREE_PICS_NEWS;
                }
                return ONE_PICS_NEWS;
            }
        });
        //step2  注册类型
        getMultiTypeDelegate().registerItemType(ONE_PICS_NEWS, R.layout.item_one_pic_news)
                .registerItemType(THREE_PICS_NEWS,R.layout.item_three_pic_news);
    }


    /**
     * 设置数据
     * @param helper
     * @param item
     */


    @Override
    protected void convert(BaseViewHolder helper, News.ResultBean item) {
        int itemViewType = helper.getItemViewType();
        switch (itemViewType){

            case ONE_PICS_NEWS:
                Log.e("TAG","helper.getView(R.id.image_one)"+helper.getView(R.id.image_one));
                    helper.setText(R.id.tv_title,item.getTitle());
                Glide.with(context).load(CommonUtil.buildGlideUrl(item.getImages().get(0))).into((ImageView) helper.getView(R.id.image_one));

                break;
            case  THREE_PICS_NEWS:
                helper.setText(R.id.tv_title,item.getTitle());
                Glide.with(context).load(CommonUtil.buildGlideUrl(item.getImages().get(0))).into((ImageView) helper.getView(R.id.image1));
                Glide.with(context).load(CommonUtil.buildGlideUrl(item.getImages().get(1))).into((ImageView) helper.getView(R.id.image2));
                Glide.with(context).load(CommonUtil.buildGlideUrl(item.getImages().get(2))).into((ImageView) helper.getView(R.id.image3));
                break;







        }

    }



}
