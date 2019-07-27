package cn.edu.hbuas.sl.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.edu.hbuas.sl.R;

public class RegisterSuccessActivity extends AppCompatActivity {
    @BindView(R.id.register_success_layout_login_btn)
    Button registerSuccessLayoutLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_success_layout);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.register_success_layout_login_btn)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.register_success_layout_login_btn: {
                Intent intent = new Intent(RegisterSuccessActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
            break;
        }
    }
}
