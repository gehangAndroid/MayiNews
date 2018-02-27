package com.mayinews.z.wxapi;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.mayinews.z.domain.UserInfoBean;
import com.mayinews.z.ui.activity.LoginActivity;
import com.mayinews.z.utils.Constant;
import com.mayinews.z.utils.SPUtils;
import com.mayinews.z.utils.ToastUtils;
import com.squareup.okhttp.Request;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

import static android.content.ContentValues.TAG;


/**
 * Created by Administrator on 2016/8/16.
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private static final int RETURN_MSG_TYPE_LOGIN = 1;
    private static final int RETURN_MSG_TYPE_SHARE = 2;
    private IWXAPI api;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //注册API
        api = WXAPIFactory.createWXAPI(this, Constant.APPID);
        api.handleIntent(getIntent(), this);
    }
    @Override
    public void onReq(BaseReq baseReq) {

    }
    //  发送到微信请求的响应结果
//	@Override
//	public void onResp(BaseResp resp) {
//
//			SendAuth.Resp newResp = (SendAuth.Resp) resp;
//			//获取微信传回的code
//			String code = newResp.code;
//		   //请求cookie
//		   Log.e("TAG","code"+code);
//            getWeixinCookie(code);
//
//
////		Intent intent=new Intent(this, CodeActivity.class);
////		intent.putExtra("code",code);
////		startActivity(intent);
//
//
//
//
//		switch (resp.errCode) {
//			case BaseResp.ErrCode.ERR_OK:
//				Log.i("savedInstanceState","发送成功ERR_OKERR_OK");
//				//发送成功
//				break;
//			case BaseResp.ErrCode.ERR_USER_CANCEL:
//				Log.i("savedInstanceState","发送取消ERR_USER_CANCEL");
//				//发送取消
//				break;
//			case BaseResp.ErrCode.ERR_AUTH_DENIED:
//				Log.i("savedInstanceState","发送取消ERR_AUTH_DENIEDERR_AUTH_DENIEDERR_AUTH_DENIED");
//				//发送被拒绝
//				break;
//			default:
//				Log.i("savedInstanceState","发送返回breakbreakbreak");
//				//发送返回
//				break;
//		}
//
//
//
//	}
//



    ///app发送消息给微信，处理返回消息的回调
//    @Override
//    public void onResp(BaseResp baseResp) {
//        Log.i("tag", "a:------>");
//        Log.i("tag", "error_code:---->" + baseResp.errCode);
//        int type = baseResp.getType(); //类型：分享还是登录
//        switch (baseResp.errCode) {
//            case BaseResp.ErrCode.ERR_AUTH_DENIED:
//                //用户拒绝授权
////                ToastUtils.showToast(mContext, "拒绝授权微信登录");
//            case BaseResp.ErrCode.ERR_USER_CANCEL:
//                //用户取消
//                String message = "";
//                if (type == RETURN_MSG_TYPE_LOGIN) {
//                    message = "取消了微信登录";
//                } else if (type == RETURN_MSG_TYPE_SHARE) {
//                    message = "取消了微信分享";
//                }
////                ToastUtils.showToast(mContext, message);
//                break;
//            case BaseResp.ErrCode.ERR_OK:
//                //用户同意
//                if (type == RETURN_MSG_TYPE_LOGIN) {
//                    //用户换取access_token的code，仅在ErrCode为0时有效
//                    String code = ((SendAuth.Resp) baseResp).code;
//                    Log.i("TAG", "code:------>" + code);
//
//                    //这里拿到了这个code，去做2次网络请求获取access_token和用户个人信息
//                    //请求cookie
//                    getWeixinCookie(code);
//                } else if (type == RETURN_MSG_TYPE_SHARE) {
////                    ToastUtils.showToast(WXEntryActivity.this, "微信分享成功");
//                }
//                break;
//        }
//    }

    @Override
    public void onResp(BaseResp resp) {
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK: //发送成功
                switch (resp.getType()) {
                    case ConstantsAPI.COMMAND_SENDAUTH:
                        //登录回调,处理登录成功的逻辑
                        String code = ((SendAuth.Resp) resp).code; //即为所需的code
                        //请求cookie
                        getWeixinCookie(code);
                        break;
                    case ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX:
                        //分享回调,处理分享成功后的逻辑
                        Log.e("TA","微信分享成功");
                        finish();
                        break;
                    default:

                        break;
                }


                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL: //发送取消
                switch (resp.getType()) {
                    case ConstantsAPI.COMMAND_SENDAUTH:
                        Log.e("TA","登录取消了");
                        break;
                    case ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX:
                        Log.e("TA","分享取消了");
                        break;
                }
                finish();
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED: //发送被拒绝
                Toast.makeText(this, "失败", Toast.LENGTH_SHORT).show();
                break;
            default://发送返回

                break;
        }

    }
    private void getWeixinCookie(String code) {


        Log.e("TAG","微信进来了");
        Log.e("TAG","微信code="+code);

        OkHttpUtils.post().url("http://m.mayinews.com/appapi/loginweixin").addParams("code",code).
                build().execute(new StringCallback() {

            @Override
            public void onError(Request request, Exception e) {
                    ToastUtils.showToast("登录失败，请稍后再试");
            }

            @Override
            public void onResponse(String response) {
                /**
                 * 请求成功
                 */
                Log.i("TAG", "微信请求结果" + response);

                JSONObject jsonObject = null;

                try {
                    jsonObject = new JSONObject(response);
                    int status = jsonObject.optInt("status");
                    if (status == 200) {
                        //获取token
                        String token = jsonObject.optString("jwt");
                        //保存token
                        SPUtils.put(WXEntryActivity.this, Constant.TOKEN, token);
                        //保存登录成功的状态
                        SPUtils.put(WXEntryActivity.this, Constant.ISLOGIN, true);

                        saveUserInfo();



                    }else{

                        Toast.makeText(WXEntryActivity.this, "认证失败，请稍后再试", Toast.LENGTH_SHORT).show();
                        finish();
                    }

//







                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(WXEntryActivity.this, "认证失败，请稍后再试", Toast.LENGTH_SHORT).show();
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
                    Log.e("TAG","微信登录失败"+e.getMessage());
                }

                @Override
                public void onResponse(String response) {

                    UserInfoBean userInfoBean = JSON.parseObject(response, UserInfoBean.class);
                    Log.e("TAG","微信登录后的信息");
                    int status = userInfoBean.getStatus();
                    if (status == 200) {
                        UserInfoBean.ResultBean result =
                                userInfoBean.getResult();
                        String avatar = result.getAvatar();//获得头像
                        String gender = result.getGender();//获得性别
                        String nickname = result.getNickname();// 获得昵称
                        //进行保存
                        SPUtils.put(WXEntryActivity.this, Constant.USERAVATAR, avatar);
                        SPUtils.put(WXEntryActivity.this, Constant.USERNICKNAME, nickname);
                        SPUtils.put(WXEntryActivity.this, Constant.GENDER, gender);

                        Toast.makeText(WXEntryActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            });


        }


    }
    }

