package com.mayinews.z.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mayinews.z.R;
import com.mayinews.z.ui.base.BaseActivity;
import com.mayinews.z.utils.CommonUtil;
import com.mayinews.z.utils.Constant;
import com.mayinews.z.utils.SPUtils;
import com.mayinews.z.utils.ToastUtils;
import com.squareup.okhttp.Request;
import com.yalantis.ucrop.UCrop;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.picker.SinglePicker;
import cn.qqtheme.framework.widget.WheelView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2018/1/29 0029.
 */

public class PerfectInforActivity extends BaseActivity {

    private static final int TAKE_PHOTO = 1;
    private static final int CHOOSE_PHOTO = 2;
    private static final int REQUESTEXTRASTORAGE = 0;

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.ll_usericon)
    LinearLayout llUsericon;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.ll_nickname)
    LinearLayout llNickname;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.ll_sex)
    LinearLayout llSex;
    @BindView(R.id.cir_usericon)
    CircleImageView headView;
    @BindView(R.id.rootView)
    LinearLayout rootView;
    @BindView(R.id.progress)
    ProgressBar progress;
    private AlertDialog dialog;
    private Uri imageUrl;//拍摄后图片的地址
    private File outputImage;
    private String token;//token 信息
    PopupWindow nicknamePop;//
    @Override
    protected int provideContentViewId() {
        return R.layout.perfectinfor_activity;
    }

    @Override
    public void initView() {
        title.setText("完善资料");
        //展现个人资料
        //在这里设置用户的信息
        token = (String) SPUtils.get(this, Constant.TOKEN, "");
        String avatar = (String) SPUtils.get(this, Constant.USERAVATAR, "");
        String name = (String) SPUtils.get(this, Constant.USERNICKNAME, "");
        String gender = (String) SPUtils.get(this, Constant.GENDER, "");
        if (!avatar.equals("")) {

            //设置头像
            Glide.with(this).load(CommonUtil.buildGlideUrl(avatar)).into(headView);
        }
        if (!name.equals("")) {
            tvNickname.setText(name);
        }
        if (gender.equals("1")) {

            tvSex.setText("男");
        } else {
            tvSex.setText("女");
        }

    }


    @Override
    public void initListener() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        llUsericon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //显示选择头像Dialog
                showUserIconDialog();

            }
        });
        llNickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //显示修改昵称的popwindow
                showModiNickNamePop();
            }
        });
        llSex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出性别选择框
                showSexPop();

            }
        });
    }

    private void showSexPop() {

        SinglePicker picker = new SinglePicker<>(this, new String[]{"男", "女"});
        picker.setCanceledOnTouchOutside(true);
        picker.setDividerRatio(WheelView.DividerConfig.FILL);
        picker.setShadowColor(Color.WHITE, 40);
        picker.setSelectedIndex(1);
        picker.setCycleDisable(true);
//        picker.setGravity(Gravity.CENTER);
        picker.setTextSize(16);
        picker.setDividerColor(Color.GRAY);
        picker.setCancelTextColor(Color.BLACK);
        picker.setSubmitTextColor(Color.BLACK);
        picker.setCancelTextSize(16);
        picker.setSubmitTextSize(16);

        picker.setContentPadding(0, 0);
        picker.setTopLineVisible(false);

        picker.setOnItemPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, String item) {
                //更改性别
                saveGender(item);
            }
        });
        picker.show();


    }

    private void saveGender(String item) {
        OkHttpUtils.post().url(Constant.SAVEGENDER)
                .addHeader("authorization", "Bearer " + token);


    }

    private void showModiNickNamePop() {

        View view = LayoutInflater.from(this).inflate(R.layout.nickname_pop, null, false);
         nicknamePop = new PopupWindow(this);
        nicknamePop.setContentView(view);
        nicknamePop.setBackgroundDrawable(new ColorDrawable(0x00000000));
        nicknamePop.setFocusable(true);
        nicknamePop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        nicknamePop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //让popupwindow不被输入法隐藏
        nicknamePop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        nicknamePop.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
        final EditText et_nickname = view.findViewById(R.id.et_nickname);
        TextView tv_con_nickname = view.findViewById(R.id.tv_con_nickname);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showInputMethod();
            }
        }, 100);
        tv_con_nickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nickname = et_nickname.getText().toString();
                if (TextUtils.isEmpty(nickname)) {
                    ToastUtils.showToast("昵称不能为空");
                } else {
                    //请求更改昵称的接口
                    alterNickName(nickname);
                }
            }
        });

    }

    private void alterNickName(final String nickname) {
        //请求接口去更改用户的用户名
        OkHttpUtils.post().url(Constant.SETNICKNAME)
                .addHeader("authorization", "Bearer " + token)
                .addParams("nickname", nickname)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                Log.e("TAG", "昵称报错出错" + e.getMessage());
                ToastUtils.showToast("系统错误,更改昵称失败");
            }

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status = jsonObject.optInt("status");
                    if (status == 200) {
                        //更改成功
                        //更改最新的昵称在本地
                        SPUtils.put(PerfectInforActivity.this, Constant.USERNICKNAME, nickname);
                        tvNickname.setText(nickname);
                        ToastUtils.showToast("昵称修改成功");

                         //关闭
                        nicknamePop.dismiss();
                    } else {
                        Log.e("TAG", "昵称报错出错" + response);
                        ToastUtils.showToast("系统错误,更改昵称失败");

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });


    }

    private void showInputMethod() {
        //自动弹出键盘
        InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        //强制隐藏Android输入法窗口
        // inputManager.hideSoftInputFromWindow(edit.getWindowToken(),0);
    }

    private void showUserIconDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View choseHeadView = View.inflate(this, R.layout.chose_head_dialog, null);

        dialog = builder.create();
        dialog.setView(choseHeadView);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = (int) (display.getWidth()); //设置宽度
        dialog.getWindow().setAttributes(lp);
        TextView tvPhoto = (TextView) choseHeadView.findViewById(R.id.tv_photo);
        TextView tvCamera = (TextView) choseHeadView.findViewById(R.id.tv_camera);
        TextView tvCancle = (TextView) choseHeadView.findViewById(R.id.tv_cancle);
        tvCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开相机
                openCamera();
            }

        });
        tvPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开相册选择图片
                getPicture();  //打开图库
            }
        });
    }

    private void getPicture() {
        //检查权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //没有授权
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUESTEXTRASTORAGE);

        } else {

            openAlbum();
        }
    }

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);
        dialog.dismiss();
    }

    private void openCamera() {
        //创建File对象，用于保存拍摄后的照片
        File file = new File(getExternalCacheDir(), "take_photo.jpg");
        try {
            if (file.exists()) {
                file.delete();
            } else {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//打开相机
//                CreateFile();
        if (Build.VERSION.SDK_INT >= 24) {
            imageUrl = FileProvider.getUriForFile(PerfectInforActivity.this, "com.mayinews.z", file);

        } else {
            imageUrl = Uri.fromFile(file);
        }

        //启动相机

        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUrl);
        startActivityForResult(intent, TAKE_PHOTO);
        dialog.dismiss();


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case REQUESTEXTRASTORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //已经授予权限，打开相册
                    openAlbum();
                } else {
                    //拒绝授权


                }
                break;


        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CHOOSE_PHOTO:
//

                    startCropActivity(data.getData());
                    break;

                case UCrop.REQUEST_CROP:

                    Uri resultUri = UCrop.getOutput(data);
                    displayImage(outputImage.getAbsolutePath());
                    //在这里执行保存头像上传的操作
                    saveAvatar();


                    break;

                case TAKE_PHOTO:
                    //进入裁剪
                    startCropActivity(imageUrl);
                    break;
            }

        }
    }

    private void saveAvatar() {
        progress.setVisibility(View.VISIBLE);
        if (outputImage != null) {


            OkHttpUtils.post()
                    .url(Constant.SETUSERAVATAR)
                    .addHeader("Authorization", "Bearer " + token)
                    .addFile("file", "avatar.jpg", outputImage)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Request request, Exception e) {

                        }

                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                int status = jsonObject.optInt("status");
                                if (status == 200) {
                                    JSONObject result = jsonObject.optJSONObject("result");
                                    String path = result.optString("path");//新的头像地址
                                    ToastUtils.showToast("头像设置成功");
                                    //保存头像
                                    SPUtils.put(PerfectInforActivity.this, Constant.USERAVATAR, path);
                                    //设置头像
                                    Glide.with(PerfectInforActivity.this).load(CommonUtil.buildGlideUrl(path)).into(headView);
                                    //关闭加载框
                                    progress.setVisibility(View.GONE);

                                } else {
                                    ToastUtils.showToast("头像上传失败,请稍后再试");
                                    progress.setVisibility(View.GONE);

                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                                ToastUtils.showToast("头像上传失败,请稍后再试");
                                progress.setVisibility(View.GONE);
                            }
                        }
                    });
        }else{

            progress.setVisibility(View.GONE);
        }


    }

    public void startCropActivity(Uri uri) {
        CreateFile();

        UCrop crop = UCrop.of(uri, Uri.fromFile(outputImage));
        crop.withAspectRatio(1, 1);
        crop.withMaxResultSize(512, 512);

        crop.start(this);


    }

    private void CreateFile() {
        outputImage = new File(getExternalCacheDir(), "head.jpg");//保存裁剪图片的File
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            } else {
                outputImage.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displayImage(String imagePath) {

        if (imagePath != null) {
//
            Bitmap testbitmap = BitmapFactory.decodeFile(imagePath);//解析为bitmap

            Glide.with(this).load(imagePath).into(headView);
//            headView.setImageBitmap(testbitmap);
        } else {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
        }

    }


}
