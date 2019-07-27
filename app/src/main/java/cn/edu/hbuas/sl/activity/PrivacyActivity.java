package cn.edu.hbuas.sl.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.suke.widget.SwitchButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.edu.hbuas.sl.R;
import cn.edu.hbuas.sl.listViewAdapter.PrivacyListViewAdapter;
import cn.edu.hbuas.sl.listViewArray.PrivacyListViewArray;

public class PrivacyActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @BindView(R.id.privacy_layout_back_img)
    ImageView privacyLayoutBackImg;
    @BindView(R.id.privacy_layout_title_textView)
    TextView privacyLayoutTitleTextView;
    @BindView(R.id.privacy_layout_head_linearLayout)
    RelativeLayout privacyLayoutHeadLinearLayout;
    private List<PrivacyListViewArray> listViewArrays = new ArrayList<>();
    private ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_layout);
        ButterKnife.bind(this);

        listView = findViewById(R.id.privacy_layout_listview); //将适配器导入Listview

        initListView(); //初始化数据
    }

    /*
    导入数据
     */
    public void initListView() {
        PrivacyListViewArray sounds = new PrivacyListViewArray("允许通过手机号找到我", new SwitchButton(this));
        listViewArrays.add(sounds);

        //创建适配器，在适配器中导入数据 1.当前类 2.list_view一行的布局 3.数据集合
        PrivacyListViewAdapter noticeListViewAdapter = new PrivacyListViewAdapter(PrivacyActivity.this, R.layout.activity_privacy_listview_layout, listViewArrays);
        listView.setAdapter(noticeListViewAdapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0: {
                listViewArrays.get(0).getSwitchButton().setChecked(!listViewArrays.get(0).getSwitchButton().isChecked());
                Toast.makeText(this, "声音", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(NoticeActivity.this, AccountAndSafetyActivity.class);
//                startActivity(intent);
            }
            break;
        }
    }

    @OnClick({
            R.id.privacy_layout_back_img,
            R.id.privacy_layout_title_textView,
            R.id.privacy_layout_head_linearLayout
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.privacy_layout_back_img: {
                finish();
            }
            break;
            case R.id.privacy_layout_title_textView:
                break;
            case R.id.privacy_layout_head_linearLayout:
                break;
        }
    }
}