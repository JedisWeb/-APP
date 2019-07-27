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
import cn.edu.hbuas.sl.listViewAdapter.NoticeListViewAdapter;
import cn.edu.hbuas.sl.listViewArray.NoticeListViewArray;

public class NoticeActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @BindView(R.id.notice_layout_back_img)
    ImageView noticeLayoutBackImg;
    @BindView(R.id.notice_layout_title_textView)
    TextView noticeLayoutTitleTextView;
    @BindView(R.id.notice_layout_head_linearLayout)
    RelativeLayout noticeLayoutHeadLinearLayout;

    private List<NoticeListViewArray> listViewArrays = new ArrayList<>();
    private ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_layout);
        ButterKnife.bind(this);

        listView = findViewById(R.id.notice_layout_listview); //将适配器导入Listview

        initListView(); //初始化数据
    }

    /*
    导入数据
     */
    public void initListView() {
        NoticeListViewArray sounds = new NoticeListViewArray("声音", new SwitchButton(this));
        listViewArrays.add(sounds);
        NoticeListViewArray newNotice = new NoticeListViewArray("接受新消息通知", new SwitchButton(this));
        listViewArrays.add(newNotice);
        NoticeListViewArray showNotice = new NoticeListViewArray("通知显示消息详情", new SwitchButton(this));
        listViewArrays.add(showNotice);
        NoticeListViewArray systemNotice = new NoticeListViewArray("系统通知", new SwitchButton(this));
        listViewArrays.add(systemNotice);

        //创建适配器，在适配器中导入数据 1.当前类 2.list_view一行的布局 3.数据集合
        NoticeListViewAdapter noticeListViewAdapter = new NoticeListViewAdapter(NoticeActivity.this, R.layout.activity_notice_listview_layout, listViewArrays);
        listView.setAdapter(noticeListViewAdapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0: {
                listViewArrays.get(0).getSwitchButton().setChecked(!listViewArrays.get(0).getSwitchButton().isChecked());
                Toast.makeText(this, "声音", Toast.LENGTH_SHORT).show();
            }
            break;
            case 1: {
                listViewArrays.get(1).getSwitchButton().setChecked(!listViewArrays.get(1).getSwitchButton().isChecked());
            }
            break;
            case 2: {
                listViewArrays.get(2).getSwitchButton().setChecked(!listViewArrays.get(2).getSwitchButton().isChecked());
            }
            break;
            case 3: {
                listViewArrays.get(3).getSwitchButton().setChecked(!listViewArrays.get(3).getSwitchButton().isChecked());
            }
            break;
        }
    }

    @OnClick({
            R.id.notice_layout_back_img,
            R.id.notice_layout_title_textView,
            R.id.notice_layout_head_linearLayout
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.notice_layout_back_img: {
                finish();
            }
            break;
            case R.id.notice_layout_title_textView:
                break;
            case R.id.notice_layout_head_linearLayout:
                break;

        }
    }
}
