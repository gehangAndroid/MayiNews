package com.mayinews.z.ui.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mayinews.z.R;
import com.mayinews.z.domain.MyGoldBean;
import com.mayinews.z.ui.adapter.MyGoldAdapter;
import com.mayinews.z.ui.fragment.MyGoldFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyGoldActivity extends AppCompatActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.rl_mygold)
    RelativeLayout rlMygold;
    @BindView(R.id.rl_mycoin)
    RelativeLayout rlMycoin;
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.tv_mygold)
    TextView tvMygold;
    @BindView(R.id.mygold_view)
    View mygoldView;
    @BindView(R.id.tv_mycoin)
    TextView tvMycoin;
    @BindView(R.id.mycoin_view)
    View mycoinView;

    private boolean stayMygoldfirst = false;//是否停留在我的金币是上
    private boolean stayMycoinfirst = false;//是否停留在我的零钱是否
    private List<MyGoldBean> datas=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_gold);
        ButterKnife.bind(this);
        initView();
        initListener();


    }

    private void initListener() {
        rlMygold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!stayMygoldfirst){
                    tvMygold.setSelected(true);
                    mygoldView.setVisibility(View.VISIBLE);
                    tvMycoin.setSelected(false);
                    mycoinView.setVisibility(View.INVISIBLE);
                    stayMygoldfirst=true;
                    stayMycoinfirst=false;

                }

            }
        });


        rlMycoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!stayMycoinfirst){
                    tvMygold.setSelected(false);
                    mygoldView.setVisibility(View.INVISIBLE);
                    tvMycoin.setSelected(true);
                    mycoinView.setVisibility(View.VISIBLE);
                    stayMycoinfirst=true;
                    stayMygoldfirst=false;
                }

            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        title.setText("我的收入");
        //设置listView
        for(int i=0;i<3;i++){
            MyGoldBean goldBean = new MyGoldBean("徒弟阅读进攻", "徒弟阅读进攻给您的金币", "+20 金币", "2018-02-26 09:52:06");
            datas.add(goldBean);
        }
        MyGoldAdapter adapter = new MyGoldAdapter(MyGoldActivity.this);
        listView.setAdapter(adapter);
        adapter.addData(datas);
        adapter.notifyDataSetChanged();
    }
}
