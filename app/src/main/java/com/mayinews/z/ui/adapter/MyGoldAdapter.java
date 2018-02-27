package com.mayinews.z.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mayinews.z.R;
import com.mayinews.z.domain.MyGoldBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by h on 2018/2/26 0026.
 */

public class MyGoldAdapter extends BaseAdapter {


    private Context context;
    private List<MyGoldBean> datas = new ArrayList<>();

    public MyGoldAdapter(Context context) {
        this.context = context;
    }

    public  void  addData(List<MyGoldBean> datas){
       this.datas=datas;

   }
    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
         ViewHolder viewHolder;
        MyGoldBean bean = datas.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.mygold_coin_item, null);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        //设置数据

        viewHolder.tvTitle.setText(bean.getTitle());
        viewHolder.tvDesc.setText(bean.getDesc());
        viewHolder.tvGold.setText(bean.getGold());
        viewHolder.tvTitle.setText(bean.getTime());


        return convertView;
    }




    class ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_desc)
        TextView tvDesc;
        @BindView(R.id.tv_gold)
        TextView tvGold;
        @BindView(R.id.tv_time)
        TextView tvTime;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
