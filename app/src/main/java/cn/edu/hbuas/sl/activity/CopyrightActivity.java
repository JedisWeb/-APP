package cn.edu.hbuas.sl.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.edu.hbuas.sl.R;

public class CopyrightActivity extends AppCompatActivity {
    @BindView(R.id.copyright_layout_back_img)
    ImageView copyrightLayoutBackImg;
    @BindView(R.id.copyright_layout_title_textView)
    TextView copyrightLayoutTitleTextView;
    @BindView(R.id.copyright_layout_contact_us_textView)
    TextView copyrightLayoutContactUsTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiti_copyright_layout);
        ButterKnife.bind(this);
    }

    @OnClick({
            R.id.copyright_layout_back_img,
            R.id.copyright_layout_title_textView,
            R.id.copyright_layout_contact_us_textView
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.copyright_layout_back_img: {
                finish();
            }
            break;
            case R.id.copyright_layout_title_textView:
                break;
            case R.id.copyright_layout_contact_us_textView: {
                try {
                    String url = "mqqwpa://im/chat?chat_type=wpa&uin=3277741866";
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                } catch (Exception e) {
                    // 获取系统剪贴板
                    ClipboardManager myClipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    myClipboard.setPrimaryClip(ClipData.newPlainText("text", "3277741866"));
                    Toast.makeText(this, "作者QQ已经复制", Toast.LENGTH_SHORT).show();
                }
            }
            break;
        }
    }
}
