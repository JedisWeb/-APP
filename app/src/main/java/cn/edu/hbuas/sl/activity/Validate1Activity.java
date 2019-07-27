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

public class Validate1Activity extends AppCompatActivity {
    @BindView(R.id.validate1_layout_back_img)
    ImageView validate1LayoutbackImg;
    @BindView(R.id.validate1_layout_head_linearLayout)
    RelativeLayout validate1LayoutHeadLinearLayout;
    @BindView(R.id.validate1_layout_tel_editText)
    EditText validate1LayoutTelEditText;
    @BindView(R.id.validate1_layout_next_btn)
    Button validate1LayoutNextBtn;
    @BindView(R.id.validate1_layout_tel_textInputLayout)
    TextInputLayout validate1LayoutTelTextInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate1_layout);
        ButterKnife.bind(this);
    }

    @OnClick({
            R.id.validate1_layout_back_img,
            R.id.validate1_layout_head_linearLayout,
            R.id.validate1_layout_tel_editText,
            R.id.validate1_layout_next_btn
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.validate1_layout_back_img: {
                finish();
            }
            break;
            case R.id.validate1_layout_head_linearLayout:
                break;
            case R.id.validate1_layout_tel_editText:
                break;
            case R.id.validate1_layout_next_btn: {
                String tel = validate1LayoutTelTextInputLayout.getEditText().getText().toString().trim();
                validate1LayoutTelTextInputLayout.setErrorEnabled(false);
                if (validateTel(tel)) {
                    User user = UserDaoUtils.queryUserById(Validate1Activity.this, Long.parseLong(tel));
                    Intent intent = new Intent(Validate1Activity.this, Validate2Activity.class);
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
            showError(validate1LayoutTelTextInputLayout, "不能为空");
            return false;
        }

        if (tel.length() != 11) {
            showError(validate1LayoutTelTextInputLayout, "长度必须为11位");
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
