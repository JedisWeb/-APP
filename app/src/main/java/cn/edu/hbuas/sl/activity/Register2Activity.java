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

public class Register2Activity extends AppCompatActivity {
    @BindView(R.id.register2_layout_back_img)
    ImageView register2LayoutbackImg;
    @BindView(R.id.register2_layout_head_linearLayout)
    RelativeLayout register2LayoutHeadLinearLayout;
    @BindView(R.id.register2_layout_nickname_editText)
    EditText register2LayoutNicknameEditText;
    @BindView(R.id.register2_layout_password_editText)
    EditText register2LayoutPasswordEditText;
    @BindView(R.id.register2_layout_next_btn)
    Button register2LayoutNextBtn;
    @BindView(R.id.register2_layout_nickname_textInputLayout)
    TextInputLayout register2LayoutNicknameTextInputLayout;
    @BindView(R.id.register2_layout_password_textInputLayout)
    TextInputLayout register2LayoutPasswordTextInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2_layout);
        ButterKnife.bind(this);

    }

    @OnClick({
            R.id.register2_layout_back_img,
            R.id.register2_layout_head_linearLayout,
            R.id.register2_layout_nickname_editText,
            R.id.register2_layout_password_editText,
            R.id.register2_layout_next_btn
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.register2_layout_back_img: {
                finish();
            }
            break;
            case R.id.register2_layout_head_linearLayout:
                break;
            case R.id.register2_layout_nickname_editText:
                break;
            case R.id.register2_layout_password_editText:
                break;
            case R.id.register2_layout_next_btn: {
                String nickname = register2LayoutNicknameTextInputLayout.getEditText().getText().toString().trim();
                String password = register2LayoutPasswordTextInputLayout.getEditText().getText().toString().trim();
                register2LayoutNicknameTextInputLayout.setErrorEnabled(false);
                register2LayoutPasswordTextInputLayout.setErrorEnabled(false);
                if (validateNickname(nickname) && validatePassword(password)) {
                    User user = new Gson().fromJson(getIntent().getStringExtra("user"), User.class);
                    user.setNickname(nickname);
                    user.setPassword(password);
                    if (MineFragment.user == null) {
                        MineFragment.user = user;
                    }
                    UserDaoUtils.insertData(this, user);
                    Intent intent = new Intent(Register2Activity.this, RegisterSuccessActivity.class);
                    startActivity(intent);
                }
            }
            break;
        }
    }

    /**
     * 验证用户名
     *
     * @param nickname
     * @return
     */
    private boolean validateNickname(String nickname) {
        if (StringUtils.isEmpty(nickname)) {
            showError(register2LayoutNicknameTextInputLayout, "不能为空");
            return false;
        }

        if (nickname.length() < 6 || nickname.length() > 20) {
            showError(register2LayoutNicknameTextInputLayout, "用户名长度为6-20位");
            return false;
        }
        return true;
    }

    /**
     * 验证密码
     *
     * @param password
     * @return
     */
    private boolean validatePassword(String password) {
        if (StringUtils.isEmpty(password)) {
            showError(register2LayoutPasswordTextInputLayout, "不能为空");
            return false;
        }

        if (password.length() < 6 || password.length() > 18) {
            showError(register2LayoutPasswordTextInputLayout, "密码长度为6-18位");
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