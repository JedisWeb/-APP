package cn.edu.hbuas.sl.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.edu.hbuas.sl.R;
import cn.edu.hbuas.sl.listViewAdapter.ConditionListViewAdapter;
import cn.edu.hbuas.sl.listViewArray.ConditionListViewArray;

public class ConditionActivity extends AppCompatActivity {

    @BindView(R.id.condition_layout_listview)
    ListView conditionLayoutListview;
    @BindView(R.id.condition_layout_back_img)
    ImageView conditionLayoutBackImg;
    @BindView(R.id.condition_layout_confirm_textView)
    TextView conditionLayoutConfirmTextView;
    @BindView(R.id.condition_layout_add_btn)
    Button conditionLayoutAddBtn;

    private List<ConditionListViewArray> listViewArrays = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_condition_layout);
        ButterKnife.bind(this);
        initListView(); //初始化数据
    }

    /*
    导入数据
     */
    public void initListView() {
        ConditionListViewArray question1 = new ConditionListViewArray(listViewArrays.size() + 1 + "", new EditText(ConditionActivity.this), new EditText(ConditionActivity.this));
        listViewArrays.add(question1);

        //创建适配器，在适配器中导入数据 1.当前类 2.list_view一行的布局 3.数据集合
        ConditionListViewAdapter conditionListViewAdapter = new ConditionListViewAdapter(ConditionActivity.this, R.layout.activity_condition_listview_layout, listViewArrays);
        conditionLayoutListview.setAdapter(conditionListViewAdapter);
    }

    @OnClick({
            R.id.condition_layout_back_img,
            R.id.condition_layout_confirm_textView,
            R.id.condition_layout_add_btn
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.condition_layout_back_img: {
                finish();
            }
            break;
            case R.id.condition_layout_confirm_textView: {
                finish();
            }
            break;
            case R.id.condition_layout_add_btn: {
                if (listViewArrays.size() <= 4) {
                    initListView();
                } else {
                    Toast.makeText(this, "问题数量已达到上限", Toast.LENGTH_SHORT).show();
                }
            }
            break;
        }
    }
}
