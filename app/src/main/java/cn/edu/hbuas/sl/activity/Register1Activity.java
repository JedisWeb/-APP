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

public class Register1Activity extends AppCompatActivity {
    @BindView(R.id.register1_layout_back_img)
    ImageView register1LayoutbackImg;
    @BindView(R.id.register1_layout_head_linearLayout)
    RelativeLayout register1LayoutHeadLinearLayout;
    @BindView(R.id.register1_layout_termsOfUse_textView)
    TextView register1LayoutTermsOfUseTextView;
    @BindView(R.id.register1_layout_privacyPolicy_textView)
    TextView register1LayoutPrivacyPolicyTextView;
    @BindView(R.id.register1_layout_tel_editText)
    EditText register1LayoutTelEditText;
    @BindView(R.id.register1_layout_next_btn)
    Button register1LayoutNextBtn;
    @BindView(R.id.register1_layout_tel_textInputLayout)
    TextInputLayout register1LayoutTelTextInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1_layout);
        ButterKnife.bind(this);
    }

    @OnClick({
            R.id.register1_layout_back_img,
            R.id.register1_layout_head_linearLayout,
            R.id.register1_layout_termsOfUse_textView,
            R.id.register1_layout_privacyPolicy_textView,
            R.id.register1_layout_tel_editText,
            R.id.register1_layout_next_btn
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.register1_layout_back_img: {
                finish();
            }
            break;
            case R.id.register1_layout_head_linearLayout:
                break;
            case R.id.register1_layout_termsOfUse_textView: {
                Toast.makeText(this, "使用条款", Toast.LENGTH_SHORT).show();
            }
            break;
            case R.id.register1_layout_privacyPolicy_textView: {
                Toast.makeText(this, "隐私政策", Toast.LENGTH_SHORT).show();
            }
            break;
            case R.id.register1_layout_tel_editText:
                break;
            case R.id.register1_layout_next_btn: {
                String tel = register1LayoutTelTextInputLayout.getEditText().getText().toString().trim();
                register1LayoutTelTextInputLayout.setErrorEnabled(false);
                if (validateTel(tel)) {
                    User user = new User();
                    user.setTelphone(Long.valueOf(register1LayoutTelEditText.getText().toString().trim()));
                    Intent intent = new Intent(Register1Activity.this, Register2Activity.class);
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
            showError(register1LayoutTelTextInputLayout, "不能为空");
            return false;
        }

        if (tel.length() != 11) {
            showError(register1LayoutTelTextInputLayout, "长度必须为11位");
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
