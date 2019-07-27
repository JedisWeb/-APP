package cn.edu.hbuas.sl.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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

public class ForgetPassword1Activity extends AppCompatActivity {
    @BindView(R.id.forget_password1_layout_back_img)
    ImageView forgetPassword1LayoutbackImg;
    @BindView(R.id.add_lost_layout_head_relativeLayout)
    RelativeLayout addLostLayoutHeadRelativeLayout;
    @BindView(R.id.forget_password1_layout_next_btn)
    Button forgetPassword1LayoutNextBtn;
    @BindView(R.id.forget_password1_layout_tel_editText)
    TextInputEditText forgetPassword1LayoutTelEditText;
    @BindView(R.id.forget_password1_layout_password_textInputLayout)
    TextInputLayout forgetPassword1LayoutPasswordTextInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password1_layout);
        ButterKnife.bind(this);
    }

    @OnClick({
            R.id.forget_password1_layout_back_img,
            R.id.add_lost_layout_head_relativeLayout,
            R.id.forget_password1_layout_tel_editText,
            R.id.forget_password1_layout_next_btn
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.forget_password1_layout_back_img: {
                finish();
            }
            break;
            case R.id.add_lost_layout_head_relativeLayout:
                break;
            case R.id.forget_password1_layout_tel_editText:
                break;
            case R.id.forget_password1_layout_next_btn: {
                String tel = forgetPassword1LayoutPasswordTextInputLayout.getEditText().getText().toString().trim();
                forgetPassword1LayoutPasswordTextInputLayout.setErrorEnabled(false);
                User user = UserDaoUtils.queryUserById(ForgetPassword1Activity.this, Long.parseLong(tel));
                if (validateTel(tel)) {
                    Intent intent = new Intent(ForgetPassword1Activity.this, ForgetPassword2Activity.class);
                    intent.putExtra("user", new Gson().toJson(user));
                    startActivity(intent);
                }
            }
            break;
        }
    }

    /**
     * 验证用户名
     *
     * @param tel
     * @return
     */
    private boolean validateTel(String tel) {
        if (StringUtils.isEmpty(tel)) {
            showError(forgetPassword1LayoutPasswordTextInputLayout, "不能为空");
            return false;
        }

        if (tel.length() != 11) {
            showError(forgetPassword1LayoutPasswordTextInputLayout, "长度必须为11位");
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
