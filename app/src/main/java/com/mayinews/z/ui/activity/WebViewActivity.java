package com.mayinews.z.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.mayinews.z.R;
import com.mayinews.z.ui.base.BaseActivity;
import com.mayinews.z.utils.Constant;
import com.mayinews.z.utils.Util;
import com.squareup.okhttp.Request;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.ByteArrayOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.KeyEvent.KEYCODE_BACK;

public class WebViewActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.wv_content)
    WebView webView;
    @BindView(R.id.parentLayout)
    LinearLayout parentLayout;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_news_comment)
    TextView tvNewsComment;
    @BindView(R.id.iv_comment)
    ImageView ivComment;
    @BindView(R.id.iv_coll)
    ImageView ivColl;
    @BindView(R.id.iv_share)
    ImageView ivShare;
    private String channelId;
    private String id;
    private PopupWindow SharedPopupWindow;
    private String  articleTitle;//网页的标题
    private String  shareImageUrl;//分享的图片封面的url
    private IWXAPI api;
    @Override
    protected int provideContentViewId() {
        return R.layout.activity_web_view;
    }

    @Override
    public void initListener() {
        super.initListener();
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvNewsComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开编写评论框
                showCommentPop();


            }
        });

        ivComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开新闻评论列表
                Intent intent = new Intent(WebViewActivity.this, CommentListActivity.class);
                startActivity(intent);
            }
        });
        ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 //进行微信分享操作
                showSharedPop();
            }
        });
    }
    public void showSharedPop() {
        View view = LayoutInflater.from(this).inflate(R.layout.shared_dialog, null);
        View parent = LayoutInflater.from(this).inflate(R.layout.activity_web_view, null);
        SharedPopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


        LinearLayout llWx = (LinearLayout) view.findViewById(R.id.ll_wx);//微信好友
        LinearLayout llPengyou = (LinearLayout) view.findViewById(R.id.ll_pengyou);//微信朋友圈
        TextView cancle = (TextView) view.findViewById(R.id.cancle);//微信朋友圈
        llWx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 sharePicture("haoyou");
            }
        });
        llPengyou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharePicture("pengyouquan");
            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPopupWindow.dismiss();
            }
        });
        SharedPopupWindow.setOutsideTouchable(true);
        SharedPopupWindow.setFocusable(true);
        //让pop可以点击外面消失掉
        SharedPopupWindow.setBackgroundDrawable(new ColorDrawable(0));
        SharedPopupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);

        SharedPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1);
            }
        });
        backgroundAlpha(0.5f);
    }

    private void sharePicture(final String type) {


            OkHttpUtils.get().url(shareImageUrl)
                    .addHeader("Referer", "http://m.mayinews.com")
                    .build().execute(new BitmapCallback() {
                @Override
                public void onError(Request request, Exception e) {
                    //dialog消失
                    SharedPopupWindow.dismiss();
                    backgroundAlpha(1);
                    Log.e("TAG","分享失败 "+e.getMessage());
                }

                @Override
                public void onResponse(Bitmap bitmap) {
                    WXWebpageObject webpage = new WXWebpageObject();
                    webpage.webpageUrl= Constant.NEWS_DETAIL_INFO + "/channel/" + channelId + "/docid/" + id;
                    WXMediaMessage message = new WXMediaMessage(webpage);
                    message.title=articleTitle;
//                    message.thumbData= Util.bmpToByteArray(bitmap,true);
                    byte[] bytes = bitmap2Bytes(bitmap, 32);
//                msg.thumbData = bytes;
                    Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, 80, 150, true);
                    message.setThumbImage(thumbBmp);
                    thumbBmp.recycle();
                    SendMessageToWX.Req req = new SendMessageToWX.Req();
                    req.transaction=buildTransaction("webpage");
                    req.message=message;
                    if (type.equals("haoyou")) {
                        req.scene = SendMessageToWX.Req.WXSceneSession;
                    } else if (type.equals("pengyouquan")) {
                        req.scene = SendMessageToWX.Req.WXSceneTimeline;
                    }

                    api.sendReq(req);
                    //dialog消失
                    SharedPopupWindow.dismiss();
                    backgroundAlpha(1);

                }
            });





    }
    /**
     * Bitmap转换成byte[]并且进行压缩,压缩到不大于maxkb
     *
     * @param bitmap
     * @param
     * @return
     */
    public static byte[] bitmap2Bytes(Bitmap bitmap, int maxkb) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
        int options = 100;
        while (output.toByteArray().length > maxkb && options != 10) {
            output.reset(); //清空output
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, output);//这里压缩options%，把压缩后的数据存放到output中
            options -= 10;
        }
        return output.toByteArray();
    }
    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);

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

    @Override
    public void initData() {
        //获取传递过来的数据
        Intent intent = getIntent();
        channelId = intent.getStringExtra("channelId");
        id = intent.getStringExtra("id");
        articleTitle = intent.getStringExtra("title");
        shareImageUrl = intent.getStringExtra("imageUrl");
        //加载网页
        Log.e("TAG", "访问的网站     " + Constant.NEWS_DETAIL_INFO + "/channel/" + channelId + "/docid/" + id);
        webView.loadUrl(Constant.NEWS_DETAIL_INFO + "/channel/" + channelId + "/docid/" + id);
    }


    @Override
    public void initView() {
        super.initView();
        api = WXAPIFactory.createWXAPI(this, Constant.APPID);
        //配置WebView
        //声明WebSettings子类
        WebSettings webSettings = webView.getSettings();

//如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
// 若加载的 html 里有JS 在执行动画等操作，会造成资源浪费（CPU、电量）
// 在 onStop 和 onResume 里分别把 setJavaScriptEnabled() 给设置成 false 和 true 即可


//设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
//缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

//其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
//优先使用缓存:
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //缓存模式如下：
        //LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
        //LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据。
        //LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
        //LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
        // 复写shouldOverrideUrlLoading()方法，使得打开网页时不调用系统浏览器， 而是在本WebView中显示
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                //在页面载入前调用，可以设置显示加载动画页面
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                //在页面载入完成后调用，可以设置隐藏加载动画页面
            }
        });

        //作用：获得网页的加载进度并显示
        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
//                if (newProgress < 100) {
//                    String progress = newProgress + "%";
//                    progress.setText(progress);
//                } else {
            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
        webView.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        parentLayout.removeView(webView);
        webView.destroy();

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("TAG", "RESUME");
    }
}
