package cn.edu.hbuas.sl.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dou361.dialogui.DialogUIUtils;
import com.dou361.dialogui.bean.TieBean;
import com.dou361.dialogui.listener.DialogUIItemListener;
import com.google.gson.Gson;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.edu.hbuas.sl.R;
import cn.edu.hbuas.sl.bean.User;
import cn.edu.hbuas.sl.bean.utils.UserDaoUtils;
import cn.edu.hbuas.sl.fragment.MineFragment;


public class LoginActivity extends AppCompatActivity {


    @BindView(R.id.login_layout_back_img)
    ImageView loginLayoutbackImg;
    @BindView(R.id.login_layou_title_textView)
    TextView loginLayouTitleTextView;
    @BindView(R.id.login_layout_head_linearLayout)
    RelativeLayout loginLayoutHeadLinearLayout;
    @BindView(R.id.login_layout_tel_textInputLayout)
    TextInputLayout loginLayoutTelTextInputLayout;
    @BindView(R.id.login_layout_tel_editText)
    TextInputEditText loginLayoutTelEditText;
    @BindView(R.id.login_layout_password_textInputLayout)
    TextInputLayout loginLayoutPasswordTextInputLayout;
    @BindView(R.id.login_layout_password_editText)
    TextInputEditText loginLayoutPasswordEditText;
    @BindView(R.id.login_layout_login_btn)
    Button loginLayoutLoginBtn;
    @BindView(R.id.login_layout_forget_password_textView)
    TextView loginLayoutForgetPasswordTextView;
    @BindView(R.id.login_layout_register_textView)
    TextView loginLayoutRegisterTextView;

    private LocalBroadcastManager localBroadcastManager;

    private SharedPreferences sp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_layout);
        ButterKnife.bind(this);
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        sp = LoginActivity.this.getSharedPreferences("loginData", MODE_PRIVATE);
    }

    @SuppressLint("ResourceType")
    @OnClick({
            R.id.login_layout_back_img,
            R.id.login_layou_title_textView,
            R.id.login_layout_head_linearLayout,
            R.id.login_layout_tel_editText,
            R.id.login_layout_password_editText,
            R.id.login_layout_login_btn,
            R.id.login_layout_forget_password_textView,
            R.id.login_layout_register_textView
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_layout_back_img: {
                finish();
            }
            break;
            case R.id.login_layou_title_textView:
                break;
            case R.id.login_layout_head_linearLayout:
                break;
            case R.id.login_layout_tel_editText:
                break;
            case R.id.login_layout_password_editText:
                break;
            case R.id.login_layout_login_btn: {
                String tel = loginLayoutTelTextInputLayout.getEditText().getText().toString().trim();
                String password = loginLayoutPasswordTextInputLayout.getEditText().getText().toString().trim();
                loginLayoutTelTextInputLayout.setErrorEnabled(false);
                loginLayoutPasswordTextInputLayout.setErrorEnabled(false);
                if (validateTel(tel) && validatePassword(password)) {
                    User user = UserDaoUtils.queryUserById(this, Long.valueOf(tel));
                    if (user == null) {
                        showError(loginLayoutTelTextInputLayout, "用户不存在");
                    } else if (user.getPassword().equals(password)) {
                        Intent intent = new Intent("cn.edu.hbuas.sl.MineFragment");
                        intent.putExtra("user", new Gson().toJson(user));
                        localBroadcastManager.sendBroadcast(intent);
                        loginLayoutTelEditText.setText("");
                        loginLayoutPasswordEditText.setText("");
                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(i);
                    } else {
                        showError(loginLayoutPasswordTextInputLayout, "用户名或密码错误");
                    }
                }
            }
            break;
            case R.id.login_layout_forget_password_textView: {
                List<TieBean> strings = new ArrayList<TieBean>();
                strings.add(new TieBean("找回密码"));
                strings.add(new TieBean("短信验证登录"));
                DialogUIUtils.showSheet(LoginActivity.this, strings, "取消", Gravity.BOTTOM, true, true, new DialogUIItemListener() {
                    @Override
                    public void onItemClick(CharSequence text, int position) {
                        switch (position) {
                            case 0: {
                                Intent intent = new Intent(LoginActivity.this, ForgetPassword1Activity.class);
                                startActivity(intent);
                            }
                            break;
                            case 1: {
                                Intent intent = new Intent(LoginActivity.this, Validate1Activity.class);
                                startActivity(intent);
                            }
                            break;
                        }
                    }

                    @Override
                    public void onBottomBtnClick() {
                    }
                }).show();
            }
            break;
            case R.id.login_layout_register_textView: {
                UserDaoUtils.deleteAllData(LoginActivity.this);
                User user = new User();
                user.setTelphone(Long.valueOf("18827519355"));
                user.setNickname("Jedis");
                user.setPassword("111111");
                user.setGender("男");
                user.setBirthday("1998-12-10");
                user.setArea("湖北省武穴市");
                UserDaoUtils.insertData(LoginActivity.this, user);
                MineFragment.user = user;
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("tel", user.getTelphone() + "");
                editor.putString("password", user.getPassword());
                editor.putString("nickname", user.getNickname());
                editor.putString("gender", user.getGender());
                editor.putString("birthday", user.getBirthday());
                editor.putString("area", user.getArea());
                editor.commit();
//                if (userDaoUtils.queryUserByTel("18827519355") == null) {
//                    userDaoUtils.insertUser(user);
//                }
                Intent intent = new Intent(LoginActivity.this, Register1Activity.class);
                startActivity(intent);
//                Toast.makeText(this, "" + UserDaoUtils.queryAll(this).size(), Toast.LENGTH_SHORT).show();
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
            showError(loginLayoutTelTextInputLayout, "不能为空");
            return false;
        }

        if (tel.length() != 11) {
            showError(loginLayoutTelTextInputLayout, "长度必须为11位");
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
            showError(loginLayoutPasswordTextInputLayout, "不能为空");
            return false;
        }

        if (password.length() < 6 || password.length() > 18) {
            showError(loginLayoutPasswordTextInputLayout, "密码长度为6-18位");
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

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
