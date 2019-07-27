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
import cn.edu.hbuas.sl.listViewAdapter.SettingListViewAdapter;
import cn.edu.hbuas.sl.listViewArray.SettingListViewArray;

public class SettingActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @BindView(R.id.setting_layout_back_img)
    ImageView settingLayoutBackImg;
    @BindView(R.id.setting_layout_title_textView)
    TextView settingLayoutTitleTextView;
    @BindView(R.id.setting_layout_head_linearLayout)
    RelativeLayout settingLayoutHeadLinearLayout;

    private List<SettingListViewArray> listViewArrays = new ArrayList<>();
    private ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_layout);
        ButterKnife.bind(this);

        listView = findViewById(R.id.setting_layout_listview); //将适配器导入Listview

        initListView(); //初始化数据
    }

    /*
    导入数据
     */
    public void initListView() {
        SettingListViewArray accountAndSafety = new SettingListViewArray(R.drawable.account_and_safety, "账号与安全", R.drawable.right);
        listViewArrays.add(accountAndSafety);
        SettingListViewArray theme = new SettingListViewArray(R.drawable.theme, "主题", R.drawable.right);
        listViewArrays.add(theme);
        SettingListViewArray notice = new SettingListViewArray(R.drawable.notice, "消息通知", R.drawable.right);
        listViewArrays.add(notice);
        SettingListViewArray privacy = new SettingListViewArray(R.drawable.privacy, "隐私", R.drawable.right);
        listViewArrays.add(privacy);
        SettingListViewArray feekback = new SettingListViewArray(R.drawable.feedback, "意见反馈", R.drawable.right);
        listViewArrays.add(feekback);
        SettingListViewArray aboutUs = new SettingListViewArray(R.drawable.about, "关于我们", R.drawable.right);
        listViewArrays.add(aboutUs);

        //创建适配器，在适配器中导入数据 1.当前类 2.list_view一行的布局 3.数据集合
        SettingListViewAdapter settingListViewAdapter = new SettingListViewAdapter(SettingActivity.this, R.layout.activity_setting_listview_layout, listViewArrays);
        listView.setAdapter(settingListViewAdapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            // 账号和安全
            case 0: {
                Intent intent = new Intent(SettingActivity.this, AccountAndSafetyActivity.class);
                startActivity(intent);
            }
            break;

            // 主题
            case 1: {
                Intent intent = new Intent(SettingActivity.this, ThemeActivity.class);
                startActivity(intent);
            }
            break;

            // 声音设置
            case 2: {
                Intent intent = new Intent(SettingActivity.this, NoticeActivity.class);
                startActivity(intent);
            }
            break;

            // 隐私
            case 3: {
                Intent intent = new Intent(SettingActivity.this, PrivacyActivity.class);
                startActivity(intent);
            }
            break;

            // 意见反馈
            case 4: {
                Intent intent = new Intent(SettingActivity.this, FeedbackActivity.class);
                startActivity(intent);
            }
            break;

            // 关于
            case 5: {
                Intent intent = new Intent(SettingActivity.this, AboutUsActivity.class);
                startActivity(intent);
            }
            break;
        }
    }

    @OnClick({
            R.id.setting_layout_back_img,
            R.id.setting_layout_title_textView,
            R.id.setting_layout_head_linearLayout
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.setting_layout_back_img: {
                finish();
            }
            break;
            case R.id.setting_layout_title_textView:
                break;
            case R.id.setting_layout_head_linearLayout:
                break;
        }
    }
}
