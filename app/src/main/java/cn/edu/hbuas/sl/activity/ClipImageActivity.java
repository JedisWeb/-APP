package cn.edu.hbuas.sl.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.edu.hbuas.sl.R;
import cn.edu.hbuas.sl.view.ClipViewLayout;

/**
 * 头像裁剪Activity
 */
public class ClipImageActivity extends AppCompatActivity {
    private static final String TAG = "ClipImageActivity";

    @BindView(R.id.activity_clip_image_layout_back_img)
    ImageView activityClipImageLayoutBackImg;
    @BindView(R.id.activity_clip_image_layout_head_relativeLayout)
    RelativeLayout activityClipImageLayoutHeadRelativeLayout;
    @BindView(R.id.activity_clip_image_layout_cancel_textVeiw)
    TextView activityClipImageLayoutCancelTextVeiw;
    @BindView(R.id.activity_clip_image_layout_select_textView)
    TextView activityClipImageLayoutSelectTextView;
    @BindView(R.id.activity_clip_image_layout_bottom_relativeLayout)
    RelativeLayout activityClipImageLayoutBottomRelativeLayout;

    private ClipViewLayout activityClipImageLayoutClipViewLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clip_image_layout);
        ButterKnife.bind(this);
        initView();
    }

    /**
     * 初始化组件
     */
    public void initView() {
        activityClipImageLayoutClipViewLayout = findViewById(R.id.activity_clip_image_layout_clipViewLayout);
    }

    @Override
    protected void onResume() {
        super.onResume();
        activityClipImageLayoutClipViewLayout.setVisibility(View.VISIBLE);
        //设置图片资源
        activityClipImageLayoutClipViewLayout.setImageSrc(getIntent().getData());
    }

    /**
     * 生成Uri并且通过setResult返回给打开的activity
     */
    private void generateUriAndReturn() {
        //调用返回剪切图
        Bitmap zoomedCropBitmap;
        zoomedCropBitmap = activityClipImageLayoutClipViewLayout.clip();
        if (zoomedCropBitmap == null) {
            Log.e("android", "zoomedCropBitmap == null");
            return;
        }
        Uri mSaveUri = Uri.fromFile(new File(getCacheDir(), "cropped_" + System.currentTimeMillis() + ".jpg"));
        if (mSaveUri != null) {
            OutputStream outputStream = null;
            try {
                outputStream = getContentResolver().openOutputStream(mSaveUri);
                if (outputStream != null) {
                    zoomedCropBitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
                }
            } catch (IOException ex) {
                Log.e("android", "Cannot open file: " + mSaveUri, ex);
            } finally {
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            Intent intent = new Intent();
            intent.setData(mSaveUri);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @OnClick({
            R.id.activity_clip_image_layout_back_img,
            R.id.activity_clip_image_layout_head_relativeLayout,
            R.id.activity_clip_image_layout_clipViewLayout,
            R.id.activity_clip_image_layout_cancel_textVeiw,
            R.id.activity_clip_image_layout_select_textView,
            R.id.activity_clip_image_layout_bottom_relativeLayout
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.activity_clip_image_layout_back_img: {
                finish();
            }
            break;
            case R.id.activity_clip_image_layout_head_relativeLayout:
                break;
            case R.id.activity_clip_image_layout_clipViewLayout:
                break;
            case R.id.activity_clip_image_layout_cancel_textVeiw: {
                finish();
            }
            break;
            case R.id.activity_clip_image_layout_select_textView: {
                generateUriAndReturn();
            }
            break;
            case R.id.activity_clip_image_layout_bottom_relativeLayout:
                break;
        }
    }
}
