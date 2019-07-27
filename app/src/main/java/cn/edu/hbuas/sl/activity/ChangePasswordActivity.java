package cn.edu.hbuas.sl.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.edu.hbuas.sl.R;
import cn.edu.hbuas.sl.bean.utils.UserDaoUtils;
import cn.edu.hbuas.sl.fragment.MineFragment;

public class ChangePasswordActivity extends AppCompatActivity {

    @BindView(R.id.change_password_layout_back_img)
    ImageView changePasswordLayoutBackImg;
    @BindView(R.id.change_password_layout_title_textView)
    TextView changePasswordLayoutTitleTextView;
    @BindView(R.id.change_password_layout_head_linearLayout)
    RelativeLayout changePasswordLayoutHeadLinearLayout;
    @BindView(R.id.change_password_layout_old_password_editText)
    EditText changePasswordLayoutOldPasswordEditText;
    @BindView(R.id.change_password_layout_new_password_editText)
    EditText changePasswordLayoutNewPasswordEditText;
    @BindView(R.id.change_password_layout_confirm_password_editText)
    EditText changePasswordLayoutConfirmPasswordEditText;
    @BindView(R.id.change_password_layout_ok_btn)
    Button changePasswordLayoutOkBtn;
    @BindView(R.id.change_password_layout_old_password_textInputLayout)
    TextInputLayout changePasswordLayoutOldPasswordTextInputLayout;
    @BindView(R.id.change_password_layout_new_password_textInputLayout)
    TextInputLayout changePasswordLayoutNewPasswordTextInputLayout;
    @BindView(R.id.change_password_layout_confirm_password_textInputLayout)
    TextInputLayout changePasswordLayoutConfirmPasswordTextInputLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password_layout);
        ButterKnife.bind(this);
    }

    @OnClick({
            R.id.change_password_layout_back_img,
            R.id.change_password_layout_title_textView,
            R.id.change_password_layout_head_linearLayout,
            R.id.change_password_layout_old_password_editText,
            R.id.change_password_layout_new_password_editText,
            R.id.change_password_layout_confirm_password_editText,
            R.id.change_password_layout_ok_btn
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.change_password_layout_back_img: {
                finish();
            }
            break;
            case R.id.change_password_layout_title_textView:
                break;
            case R.id.change_password_layout_head_linearLayout:
                break;
            case R.id.change_password_layout_old_password_editText:
                break;
            case R.id.change_password_layout_new_password_editText:
                break;
            case R.id.change_password_layout_confirm_password_editText:
                break;
            case R.id.change_password_layout_ok_btn: {
                String oldPassword = changePasswordLayoutOldPasswordTextInputLayout.getEditText().getText().toString().trim();
                String newPassword = changePasswordLayoutNewPasswordTextInputLayout.getEditText().getText().toString().trim();
                String confirmPassword = changePasswordLayoutConfirmPasswordTextInputLayout.getEditText().getText().toString().trim();
                changePasswordLayoutOldPasswordTextInputLayout.setErrorEnabled(false);
                changePasswordLayoutNewPasswordTextInputLayout.setErrorEnabled(false);
                changePasswordLayoutConfirmPasswordTextInputLayout.setErrorEnabled(false);
                if (MineFragment.user == null) {
                    showError(changePasswordLayoutOldPasswordTextInputLayout, "请先登录");
                } else if (validatePassword(oldPassword, newPassword, confirmPassword)) {
                    MineFragment.user.setPassword(newPassword);
                    UserDaoUtils.updateData(ChangePasswordActivity.this, MineFragment.user);
                    MineFragment.user = null;
                    Intent intent = new Intent(ChangePasswordActivity.this, LoginActivity.class);
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
     * @return
     */
    private boolean validatePassword(String oldPassword, String newPassword, String confirmPassword) {
        if (StringUtils.isEmpty(oldPassword)) {
            showError(changePasswordLayoutOldPasswordTextInputLayout, "不能为空");
            return false;
        }

        if (!(oldPassword.equals(MineFragment.user.getPassword()))) {
            showError(changePasswordLayoutOldPasswordTextInputLayout, "密码错误");
        }

        if (StringUtils.isEmpty(newPassword)) {
            showError(changePasswordLayoutNewPasswordTextInputLayout, "不能为空");
            return false;
        }

        if (newPassword.length() < 6 || newPassword.length() >= 18) {
            showError(changePasswordLayoutNewPasswordTextInputLayout, "密码长度为6-18位");
            return false;
        }

        if (StringUtils.isEmpty(confirmPassword)) {
            showError(changePasswordLayoutConfirmPasswordTextInputLayout, "不能为空");
            return false;
        }


        if (!(newPassword.equals(confirmPassword))) {
            showError(changePasswordLayoutConfirmPasswordTextInputLayout, "密码不一致");
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
