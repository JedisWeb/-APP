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

import com.google.gson.Gson;

import org.apache.commons.lang3.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.edu.hbuas.sl.R;
import cn.edu.hbuas.sl.bean.User;
import cn.edu.hbuas.sl.bean.utils.UserDaoUtils;
import cn.edu.hbuas.sl.fragment.MineFragment;

public class ForgetPassword3Activity extends AppCompatActivity {
    @BindView(R.id.forget_password3_layout_back_img)
    ImageView forgetPassword3LayoutbackImg;
    @BindView(R.id.forget_password3_layout_head_linearLayout)
    RelativeLayout forgetPassword3LayoutHeadLinearLayout;
    @BindView(R.id.forget_password3_layout_new_password_editText)
    EditText forgetPassword3LayoutNewPasswordEditText;
    @BindView(R.id.forget_password3_layout_new_password_sec_editText)
    EditText forgetPassword3LayoutNewPasswordSecEditText;
    @BindView(R.id.forget_password3_layout_login_btn)
    Button forgetPassword3LayoutLoginBtn;
    @BindView(R.id.forget_password3_layout_new_password_textInputLayout)
    TextInputLayout forgetPassword3LayoutNewPasswordTextInputLayout;
    @BindView(R.id.forget_password3_layout_new_password_sec_textInputLayout)
    TextInputLayout forgetPassword3LayoutNewPasswordSecTextInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password3_layout);
        ButterKnife.bind(this);
    }

    @OnClick({
            R.id.forget_password3_layout_back_img,
            R.id.forget_password3_layout_head_linearLayout,
            R.id.forget_password3_layout_new_password_editText,
            R.id.forget_password3_layout_new_password_sec_editText,
            R.id.forget_password3_layout_login_btn
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.forget_password3_layout_back_img: {
                finish();
            }
            break;
            case R.id.forget_password3_layout_head_linearLayout:
                break;
            case R.id.forget_password3_layout_new_password_editText:
                break;
            case R.id.forget_password3_layout_new_password_sec_editText:
                break;
            case R.id.forget_password3_layout_login_btn: {
                String password = forgetPassword3LayoutNewPasswordTextInputLayout.getEditText().getText().toString().trim();
                String passwordSec = forgetPassword3LayoutNewPasswordSecTextInputLayout.getEditText().getText().toString().trim();
                forgetPassword3LayoutNewPasswordTextInputLayout.setErrorEnabled(false);
                forgetPassword3LayoutNewPasswordSecTextInputLayout.setErrorEnabled(false);
                User user = new Gson().fromJson(getIntent().getStringExtra("user"), User.class);
                if (validatePassword(password, passwordSec)) {
//                    Toast.makeText(this, "修改密码成功", Toast.LENGTH_SHORT).show();
                    user.setPassword(password);
                    if (MineFragment.user == null) {
                        MineFragment.user = user;
                    }
                    UserDaoUtils.updateData(ForgetPassword3Activity.this, user);
                    Intent intent = new Intent(ForgetPassword3Activity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
            break;
        }
    }

    /**
     * 验证密码
     *
     * @param password
     * @return
     */
    private boolean validatePassword(String password, String passwordSec) {
        if (StringUtils.isEmpty(password)) {
            showError(forgetPassword3LayoutNewPasswordTextInputLayout, "不能为空");
            return false;
        }

        if (password.length() < 6 || password.length() >= 18) {
            showError(forgetPassword3LayoutNewPasswordTextInputLayout, "密码长度为6-18位");
            return false;
        }

        if (StringUtils.isEmpty(passwordSec)) {
            showError(forgetPassword3LayoutNewPasswordSecTextInputLayout, "不能为空");
            return false;
        }

        if (!(password.equals(passwordSec))) {
            showError(forgetPassword3LayoutNewPasswordSecTextInputLayout, "密码不一致");
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
