package com.mayinews.z.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mayinews.z.R;
import com.mayinews.z.utils.CommonUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class SystemTaskActivity extends AppCompatActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.title)
    TextView title;
    CircleImageView cir1;
    CircleImageView cir2;
    CircleImageView cir3;
    CircleImageView cir4;
    CircleImageView cir5;
    CircleImageView cir6;
    CircleImageView cir7;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private View headView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_task);
        headView = LayoutInflater.from(this).inflate(R.layout.system_tast_recycler_head, null, false);
        ButterKnife.bind(this);

        initView();
        initListener();
        initData();


    }

    private void initData() {


    }

    private void initListener() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        title.setText("任务");
        initCir();


    }

    private void initCir() {

        //获取recyclerView的顶部视图
        cir1 = headView.findViewById(R.id.cir1);
        cir2 = headView.findViewById(R.id.cir2);
        cir3 = headView.findViewById(R.id.cir3);
        cir4 = headView.findViewById(R.id.cir4);
        cir5 = headView.findViewById(R.id.cir5);
        cir6 = headView.findViewById(R.id.cir6);
        cir7 = headView.findViewById(R.id.cir7);
        //设置7个圆形的高和宽
        int width = CommonUtil.getScreenWidth(this); //屏幕宽度
        //计算出空余的可用宽度
        int usedWidth = CommonUtil.dip2px((2 * 4 + 6 * 10));
        int useableWidth = width - usedWidth;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(useableWidth / 7, useableWidth / 7);

        cir1.setLayoutParams(params);
        cir2.setLayoutParams(params);
        cir3.setLayoutParams(params);
        cir4.setLayoutParams(params);
        cir5.setLayoutParams(params);
        cir6.setLayoutParams(params);
        cir7.setLayoutParams(params);


//        Log.e("TAG","宽度我看看"+lp1.width+"  高度"+lp1.height);
    }
}
