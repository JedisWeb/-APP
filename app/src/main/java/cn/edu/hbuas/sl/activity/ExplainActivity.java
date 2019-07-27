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

public class ExplainActivity extends AppCompatActivity {


    @BindView(R.id.explain_layout_back_img)
    ImageView explainLayoutBackImg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiti_explain_layout);
        ButterKnife.bind(this);
    }

    @OnClick({
            R.id.explain_layout_back_img
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.explain_layout_back_img: {
                finish();
            }
            break;
        }
    }
}
