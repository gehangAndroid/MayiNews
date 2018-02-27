package com.mayinews.z.ui.activity;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
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

import com.mayinews.z.R;
import com.mayinews.z.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/1/31 0031.
 */

public class CommentListActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_news_comment)
    TextView tvNewsComment;
    @BindView(R.id.iv_original)
    ImageView ivOriginal;
    @BindView(R.id.iv_coll)
    ImageView ivColl;
    @BindView(R.id.iv_share)
    ImageView ivShare;
    @BindView(R.id.parentLayout)
    LinearLayout parentLayout;

    @Override
    protected int provideContentViewId() {
        return R.layout.comment_list_activity;

    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void initView() {
        super.initView();
        title.setText("点赞是一种态度");
    }

    @Override
    public void initListener() {
        super.initListener();
        ivOriginal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvNewsComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCommentPop();
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
        commentPopWindow.showAtLocation(parentLayout, Gravity.BOTTOM, 0, 0);
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

}
