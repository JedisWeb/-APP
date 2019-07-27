package cn.edu.hbuas.sl.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.edu.hbuas.sl.R;
import cn.edu.hbuas.sl.fragment.MineFragment;
import cn.edu.hbuas.sl.listViewAdapter.AccountAndSafetyListViewAdapter;
import cn.edu.hbuas.sl.listViewArray.AccountAndSafetyListViewArray;

public class AccountAndSafetyActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @BindView(R.id.account_and_safety_layout_back_img)
    ImageView accountAndSafetyLayoutBackImg;
    @BindView(R.id.account_and_safety_layout_title_textView)
    TextView accountAndSafetyLayoutTitleTextView;
    @BindView(R.id.account_and_safety_layout_head_linearLayout)
    RelativeLayout accountAndSafetyLayoutHeadLinearLayout;

    private List<AccountAndSafetyListViewArray> listViewArrays = new ArrayList<>();
    private ListView listView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_and_safety_layout);
        ButterKnife.bind(this);

        listView = findViewById(R.id.account_and_safety_layout_listview); //将适配器导入Listview

        initListView(); //初始化数据
    }

    /*
    导入数据
     */
    public void initListView() {
        AccountAndSafetyListViewArray sounds = new AccountAndSafetyListViewArray("更改密码", R.drawable.right);
        listViewArrays.add(sounds);
        AccountAndSafetyListViewArray newNotice = new AccountAndSafetyListViewArray("退出当前账号", R.drawable.right);
        listViewArrays.add(newNotice);

        //创建适配器，在适配器中导入数据 1.当前类 2.list_view一行的布局 3.数据集合
        AccountAndSafetyListViewAdapter accountAndSafetyListViewAdapter = new AccountAndSafetyListViewAdapter(AccountAndSafetyActivity.this, R.layout.activity_account_and_safety_listview_layout, listViewArrays);
        listView.setAdapter(accountAndSafetyListViewAdapter);
        listView.setOnItemClickListener(this);
    }

    @OnClick({
            R.id.account_and_safety_layout_back_img,
            R.id.account_and_safety_layout_title_textView,
            R.id.account_and_safety_layout_head_linearLayout
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.account_and_safety_layout_back_img: {
                finish();
            }
            break;
            case R.id.account_and_safety_layout_title_textView: {
                Intent intent = new Intent(AccountAndSafetyActivity.this, ChangePasswordActivity.class);
                startActivity(intent);
            }
            break;
            case R.id.account_and_safety_layout_head_linearLayout: {
                Intent intent = new Intent(AccountAndSafetyActivity.this, ThemeActivity.class);
                startActivity(intent);
            }
            break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            // 更改密码
            case 0: {
                Intent intent = new Intent(AccountAndSafetyActivity.this, ChangePasswordActivity.class);
                startActivity(intent);
            }
            break;

            // 退出账号
            case 1: {
                MineFragment.user = null;
                Intent intent = new Intent(AccountAndSafetyActivity.this, LoginActivity.class);
                startActivity(intent);
            }
            break;
        }
    }
}
