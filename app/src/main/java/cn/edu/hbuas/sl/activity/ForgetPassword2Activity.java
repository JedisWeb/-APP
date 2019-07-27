package cn.edu.hbuas.sl.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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


public class ForgetPassword2Activity extends AppCompatActivity {
    @BindView(R.id.forget_password2_layout_back_img)
    ImageView forgetPassword2LayoutbackImg;
    @BindView(R.id.forget_password2_layout_title_relativeLayout)
    RelativeLayout forgetPassword2LayoutTitleRelativeLayout;
    @BindView(R.id.forget_password2_layout_tel_textView)
    TextView forgetPassword2LayoutTelTextView;
    @BindView(R.id.forget_password2_layout_next_btn)
    Button forgetPassword2LayoutNextBtn;
    @BindView(R.id.forget_password2_layout_password_textInputLayout)
    TextInputLayout forgetPassword2LayoutPasswordTextInputLayout;
    @BindView(R.id.forget_password2_layout_get_validate_code_textView)
    TextView forgetPassword2LayoutGetValidateCodeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password2_layout);
        ButterKnife.bind(this);
    }

    @OnClick({
            R.id.forget_password2_layout_back_img,
            R.id.forget_password2_layout_title_relativeLayout,
            R.id.forget_password2_layout_tel_textView,
            R.id.forget_password2_layout_validate_code_editText,
            R.id.forget_password2_layout_get_validate_code_textView,
            R.id.forget_password2_layout_next_btn
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.forget_password2_layout_back_img: {
                finish();
            }
            break;
            case R.id.forget_password2_layout_title_relativeLayout:
                break;
            case R.id.forget_password2_layout_tel_textView:
                break;
            case R.id.forget_password2_layout_validate_code_editText:
                break;
            case R.id.forget_password2_layout_get_validate_code_textView: {
                Toast.makeText(this, "发送", Toast.LENGTH_SHORT).show();
            }
            break;
            case R.id.forget_password2_layout_next_btn: {
                String validateCode = forgetPassword2LayoutPasswordTextInputLayout.getEditText().getText().toString().trim();
                forgetPassword2LayoutPasswordTextInputLayout.setErrorEnabled(false);
                User user = new Gson().fromJson(getIntent().getStringExtra("user"), User.class);
                if (validateCode(validateCode)) {
                    if (validateCode.equals(user.getTelphone().toString().substring(6))) {
                        Intent intent = new Intent(ForgetPassword2Activity.this, ForgetPassword3Activity.class);
                        intent.putExtra("user", new Gson().toJson(user));
                        startActivity(intent);
                    } else {
                        showError(forgetPassword2LayoutPasswordTextInputLayout, "验证码错误，请重新输入");
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
            showError(forgetPassword2LayoutPasswordTextInputLayout, "不能为空");
            return false;
        }

        if (validateCode.length() != 6) {
            showError(forgetPassword2LayoutPasswordTextInputLayout, "验证码长度错误");
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
