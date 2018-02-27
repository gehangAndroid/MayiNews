package com.mayinews.z.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.TouchDelegate;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.aliyun.clientinforeport.util.NetUtils;
import com.mayinews.z.R;
import com.mayinews.z.domain.UserInfoBean;
import com.mayinews.z.ui.base.BaseActivity;
import com.mayinews.z.utils.CommonUtil;
import com.mayinews.z.utils.Constant;
import com.mayinews.z.utils.SPUtils;
import com.mayinews.z.utils.StringUtil;
import com.mayinews.z.utils.ToastUtils;
import com.squareup.okhttp.Request;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity {


    @BindView(R.id.iv_finish)
    ImageView ivFinish;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_wx_login)
    TextView tvWxLogin;
    @BindView(R.id.bt_get_code)
    Button btGetCode;
    private static final int TIME = 1;
    private static int recentTime = 299;
    private IWXAPI api;
    Handler handler = new Handler() {


        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TIME:
                    recentTime--;

                    if (recentTime > 0) {
                        btGetCode.setText(recentTime + "秒");
                        this.sendEmptyMessageDelayed(TIME, 1000);
                    } else {

                        btGetCode.setEnabled(true);
                        this.removeCallbacksAndMessages(null);
                        btGetCode.setText("获取验证码");
                    }
                    break;
            }

        }

    };

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_login;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishActivity();
    }

    private void finishActivity() {
        finish();
        overridePendingTransition(R.anim.end_alpha, R.anim.login_activity_out);
    }


    @Override
    public void initListener() {
        //关闭当前页面
        ivFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishActivity();
            }
        });
        //获取验证码
        btGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取验证码
                getVerCode();
            }
        });


        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进行登录操作
                if(CommonUtil.isNetworkAvailable(LoginActivity.this)){

                      loginApp();

                }else{


                    ToastUtils.showToast("当前网络不可用");
            }


            }
        });


        tvWxLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//进行登录操作
                if(CommonUtil.isNetworkAvailable(LoginActivity.this)){

                    wxLoginApp();

                }else {

                    ToastUtils.showToast("当前网络不可用");
                }
            }
        });





    }

    private void wxLoginApp() {


        boolean weixinAvaliable = isWeixinAvaliable(this);
        if (weixinAvaliable) {
            final SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "none";
            api.sendReq(req);
            finish();

        } else {

            ToastUtils.showToast("您没有安装微信");

        }
    }
    private void loginApp() {

        //判断网络是否可用
        boolean networkAvailable = CommonUtil.isNetworkAvailable(this);
        if (networkAvailable) {
            //判断手机号是否合法

            boolean mobile = StringUtil.isMobile(etPhone.getText().toString());
            if (mobile) {


                //判断验证码是否为空
                boolean empty = StringUtil.isEmpty(etCode.getText().toString());
                if (!empty) {


                    //进行登录操作
                    loginByPhone();

                } else {

                    ToastUtils.showToast("验证码不能为空");
                }

            } else {

                ToastUtils.showToast("请输入正确的手机号");


            }


        } else {

            //提示网络不可用
            ToastUtils.showToast("当前网络不可用");
        }

    }

    //手机号登录
    private void loginByPhone() {

        OkHttpUtils.post().url(Constant.LOGIN)
                .addParams("mobile", etPhone.getText().toString())
                .addParams("number", etCode.getText().toString())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {

                        ToastUtils.showToast("登录失败，请稍后再试");
//                        handler.removeCallbacksAndMessages(null);
//                        btGetCode.setText("验证码");
                    }

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object != null) {
                                int status = object.optInt("status");
                                if (status == 200) {
                                    //获取token
                                    String token = object.optString("jwt");
                                    //保存token
                                    SPUtils.put(LoginActivity.this, Constant.TOKEN, token);
                                    //保存登录成功的状态
                                    SPUtils.put(LoginActivity.this, Constant.ISLOGIN, true);

                                    saveUserInfo();

                                    //取消handler的消息
                                    handler.removeCallbacksAndMessages(null);

                                }

                            } else {

                                ToastUtils.showToast("验证码失败");
                                etCode.setText("");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });


    }

    private void saveUserInfo() {
        //如果登录状态状态，就去获取用户的信息
        String token = (String) SPUtils.get(this, Constant.TOKEN, "");

        if (token != null && !token.equals("")) {

            //获取用户的信息
            OkHttpUtils.get().url(Constant.GETUSERINFO).addHeader("authorization", "Bearer " + token)
                    .build().execute(new StringCallback() {
                @Override
                public void onError(Request request, Exception e) {
                    ToastUtils.showToast("登录失败，请稍后再试");
                }

                @Override
                public void onResponse(String response) {

                    UserInfoBean userInfoBean = JSON.parseObject(response, UserInfoBean.class);
                    int status = userInfoBean.getStatus();
                    if (status == 200) {
                         UserInfoBean.ResultBean result =
                                userInfoBean.getResult();
                         String avatar = result.getAvatar();//获得头像
                         String gender = result.getGender();//获得性别
                         String nickname = result.getNickname();// 获得昵称
                         //进行保存
                        SPUtils.put(LoginActivity.this, Constant.USERAVATAR, avatar);
                        SPUtils.put(LoginActivity.this, Constant.USERNICKNAME, nickname);
                        SPUtils.put(LoginActivity.this, Constant.GENDER, gender);

                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                         finish();
                    }
                }
            });


        }


    }

    private void getVerCode() {

        //判断网络是否可用
        boolean networkAvailable = CommonUtil.isNetworkAvailable(this);
        if (networkAvailable) {
            //判断手机号是否合法

            boolean mobile = StringUtil.isMobile(etPhone.getText().toString());
            if (mobile) {


                //去获取验证码
                requireCode();
            } else {

                ToastUtils.showToast("请输入正确的手机号");


            }


        } else {

            //提示网络不可用
            ToastUtils.showToast("当前网络不可用");
        }


    }

    //请求验证码
    private void requireCode() {
        //发送消息
        btGetCode.setEnabled(false);

        btGetCode.setText(recentTime + "秒");
        handler.sendEmptyMessageDelayed(TIME, 1000);
        OkHttpUtils.post().url(Constant.SENDSMS)
                .addParams("mobile", etPhone.getText().toString())
                .build().execute(new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                Log.e("TAG", "验证码获取失败信息" + e.getMessage());
                ToastUtils.showToast("验证码请求失败，请稍后再试");
            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    int status = object.optInt("status");
                    if (status == 1) {

                        String substring = etPhone.getText().toString().substring(7);
                        //保存用户的手机号
                        SPUtils.put(LoginActivity.this, Constant.PHONENUMBER, etPhone.getText().toString());
////                                    ToastUtil.showToast(LoginActivity.this, "验证码已发送至尾号为"+substring+"的手机号");
                        Toast.makeText(LoginActivity.this, "验证码已发送至尾号为" + substring + "的手机号", Toast.LENGTH_SHORT).show();
                        Log.e("TAG", "验证码" + response);

                    } else {

                        ToastUtils.showToast("验证码请求失败，请稍后再试");
                        btGetCode.setEnabled(true);
                        handler.removeCallbacksAndMessages(null);
                        btGetCode.setText("获取验证码");


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });


    }

    @Override
    public void initData() {
        api = WXAPIFactory.createWXAPI(this, Constant.APPID, false);
//        SPUtils.put(this,Constant.ISLOGIN,true);//测试用，上线时要删除
    }

    public boolean isWeixinAvaliable(Context context){

        PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> packages = packageManager.getInstalledPackages(0);
        if(packages!=null && packages.size()!=0){
            for(PackageInfo info : packages){
                String name = info.packageName;
                if(name.equals("com.tencent.mm")){
                    return true;
                }
            }

          }
        return false;

        }

    }







