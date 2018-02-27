package com.mayinews.z.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mayinews.z.R;
import com.mayinews.z.ui.activity.InviteCodeActivity;
import com.mayinews.z.ui.activity.MyAttenActivity;
import com.mayinews.z.ui.activity.MyCollActivity;
import com.mayinews.z.ui.activity.MyCommentActivity;
import com.mayinews.z.ui.activity.MyGoldActivity;
import com.mayinews.z.ui.activity.MyHistoryActivity;
import com.mayinews.z.ui.activity.SettingActivity;
import com.mayinews.z.ui.activity.SystemTaskActivity;
import com.mayinews.z.ui.base.BaseFragment;
import com.mayinews.z.utils.CommonUtil;
import com.mayinews.z.utils.Constant;
import com.mayinews.z.utils.SPUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2018/1/3 0003.
 */

public class MineFragment extends BaseFragment {
    @BindView(R.id.ll_setting)
    LinearLayout llSetting;
    @BindView(R.id.cir_user_icon)
    CircleImageView cirUserIcon;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    Unbinder unbinder;
    @BindView(R.id.ll_invite_code)
    LinearLayout llInviteCode;
    @BindView(R.id.ll_task)
    LinearLayout llTask;

    Unbinder unbinder1;
    @BindView(R.id.ll_mygold)
    LinearLayout llMygold;
    @BindView(R.id.ll_mycoin)
    LinearLayout llMycoin;
    Unbinder unbinder2;
    @BindView(R.id.ll_income_detail)
    LinearLayout llIncomeDetail;
    @BindView(R.id.ll_myatten)
    LinearLayout llMyatten;
    @BindView(R.id.ll_mycoll)
    LinearLayout llMycoll;
    @BindView(R.id.ll_history)
    LinearLayout llHistory;
    @BindView(R.id.ll_mycomment)
    LinearLayout llMycomment;

//    @BindView(R.id.srl)
//    SwipeRefreshLayout srl;
//    @BindView(R.id.recyclerView)
//    RecyclerView recyclerView;


    @Override
    protected int provideContentViewId() {
//        return R.layout.fragment_mine;
        return R.layout.fragment_mine_xiesi;
    }

    @Override
    public void initData() {
        super.initData();

    }

    @Override
    public void initView(View rootView) {
//        srl.setColorSchemeColors(getResources().getColor(R.color.appColor));
        //初始化RecyclerView


    }

    /**
     * 第一次可见时加载数据
     */
    @Override
    protected void loadData() {

        //判断登录状态
        //点击我的按钮，先判断是否是登陆状态,false代表没有登录，true代表登录状态
        boolean loginStatus = (boolean) SPUtils.get(mActivity, Constant.ISLOGIN, false);
        Log.e("tag", "登录状态loginStatus" + loginStatus);
        if (!loginStatus) { //没有登录


        } else {


        }

    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
        if (isVisible) {
            updateUserInfo();
        }
    }


    @Override
    public void initListener() {
        llSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开设置页面
                Intent intent = new Intent(mActivity, SettingActivity.class);
                startActivity(intent);
            }
        });


        llInviteCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //启动邀请码的Activity
                Intent intent = new Intent(getActivity(), InviteCodeActivity.class);
                startActivity(intent);
            }
        });

        llTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //启动系统任务的Activity
                Intent intent = new Intent(getActivity(), SystemTaskActivity.class);
                startActivity(intent);
            }
        });

        llMygold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开我的金币详情页
                Intent intent = new Intent(getActivity(), MyGoldActivity.class);
                startActivity(intent);


            }
        });

        llMycoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开我的零钱详情页
                //打开我的金币详情页
                Intent intent = new Intent(getActivity(), MyGoldActivity.class);
                startActivity(intent);
            }
        });

        llIncomeDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //打开我的金币详情页
                Intent intent = new Intent(getActivity(), MyGoldActivity.class);
                startActivity(intent);

            }
        });


         llMyatten.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                   //打开我的关注
                 Intent intent = new Intent(getActivity(), MyAttenActivity.class);
                 startActivity(intent);


             }
         });
        llMycoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开我的收藏
                Intent intent = new Intent(getActivity(), MyCollActivity.class);
                startActivity(intent);
            }
        });

        llHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开历史记录
                Intent intent = new Intent(getActivity(), MyHistoryActivity.class);
                startActivity(intent);
            }
        });

        llMycomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开我的评论
                Intent intent = new Intent(getActivity(), MyCommentActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onFragmentFirstVisible() {
        updateUserInfo();
    }

    private void updateUserInfo() {
        //在这里设置用户的信息

        String avatar = (String) SPUtils.get(mActivity, Constant.USERAVATAR, "");
        String name = (String) SPUtils.get(mActivity, Constant.USERNICKNAME, "");
        if (!avatar.equals("")) {

            //设置头像
            Glide.with(mActivity).load(CommonUtil.buildGlideUrl(avatar)).into(cirUserIcon);
        }
        if (!name.equals("")) {
            tvUsername.setText(name);
        }


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder2 = ButterKnife.bind(this, rootView);
        return rootView;
    }
}
