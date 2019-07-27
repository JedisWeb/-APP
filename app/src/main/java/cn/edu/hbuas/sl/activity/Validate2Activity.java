package cn.edu.hbuas.sl.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.commons.lang3.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.edu.hbuas.sl.R;
import cn.edu.hbuas.sl.bean.User;

public class Validate2Activity extends AppCompatActivity {
    @BindView(R.id.validate2_layout_back_img)
    ImageView validate2LayoutbackImg;
    @BindView(R.id.validate2_layout_head_linearLayout)
    RelativeLayout validate2LayoutHeadLinearLayout;
    @BindView(R.id.validate2_layout_tel_textView)
    TextView validate2LayoutTelTextView;
    @BindView(R.id.validate2_layout_validate_code_editText)
    EditText validate2LayoutValidateCodeEditText;
    @BindView(R.id.validate2_layout_get_validate_code_textView)
    TextView validate2LayoutGetValidateCodeTextView;
    @BindView(R.id.validate2_layout_login_btn)
    Button validate2LayoutLoginBtn;
    @BindView(R.id.validate2_layout_validate_code_textInputLayout)
    TextInputLayout validate2LayoutValidateCodeTextInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate2_layout);
        ButterKnife.bind(this);
    }

    @OnClick({
            R.id.validate2_layout_back_img,
            R.id.validate2_layout_head_linearLayout,
            R.id.validate2_layout_tel_textView,
            R.id.validate2_layout_validate_code_editText,
            R.id.validate2_layout_get_validate_code_textView,
            R.id.validate2_layout_login_btn
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.validate2_layout_back_img: {
                finish();
            }
            break;
            case R.id.validate2_layout_head_linearLayout:
                break;
            case R.id.validate2_layout_tel_textView:
                break;
            case R.id.validate2_layout_validate_code_editText:
                break;
            case R.id.validate2_layout_get_validate_code_textView: {
                Toast.makeText(this, "发送", Toast.LENGTH_SHORT).show();
            }
            break;
            case R.id.validate2_layout_login_btn: {
                String validateCode = validate2LayoutValidateCodeTextInputLayout.getEditText().getText().toString().trim();
                validate2LayoutValidateCodeTextInputLayout.setErrorEnabled(false);
                User user = new Gson().fromJson(getIntent().getStringExtra("user"), User.class);
                if (validateCode(validateCode)) {
                    if (validateCode.equals(user.getTelphone().toString().substring(6))) {
                        Intent intent = new Intent(Validate2Activity.this, ForgetPassword3Activity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
            break;
        }
    }

    /**
     * 验证用户名
     *
     * @param validateCode
     * @return
     */
    private boolean validateCode(String validateCode) {
        if (StringUtils.isEmpty(validateCode)) {
            showError(validate2LayoutValidateCodeTextInputLayout, "不能为空");
            return false;
        }

        if (validateCode.length() != 6) {
            showError(validate2LayoutValidateCodeTextInputLayout, "验证码长度错误");
            return false;
        }
        return true;
    }

    /**
     * 显示错误提示，并获取焦点
     *
     * @param textInputLayout
     * @param error
     */
    private void showError(TextInputLayout textInputLayout, String error) {
        textInputLayout.setError(error);
        textInputLayout.getEditText().setFocusable(true);
        textInputLayout.getEditText().setFocusableInTouchMode(true);
        textInputLayout.getEditText().requestFocus();
    }
}
