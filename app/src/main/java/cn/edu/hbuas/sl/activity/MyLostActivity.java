package cn.edu.hbuas.sl.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.edu.hbuas.sl.R;
import cn.edu.hbuas.sl.bean.MyLost;
import cn.edu.hbuas.sl.bean.utils.MyLostDaoUtils;
import cn.edu.hbuas.sl.listViewAdapter.MyLostListViewAdapter;
import cn.edu.hbuas.sl.listViewArray.MyLostListViewArray;

public class MyLostActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, OnRefreshListener, OnLoadMoreListener {

    @BindView(R.id.my_lost_layout_layout_back_img)
    ImageView myLostLayoutLayoutBackImg;
    @BindView(R.id.my_lost_layout_title_textView)
    TextView myLostLayoutTitleTextView;
    @BindView(R.id.my_lost_layout_refreshLayout)
    SmartRefreshLayout myLostLayoutRefreshLayout;

    private List<MyLostListViewArray> myLostListViewArrays = new ArrayList<>();
    private ListView listView;
    private List<MyLost> myLosts;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_lost_layout);
        ButterKnife.bind(this);

        listView = findViewById(R.id.my_lost_layout_listview);
        Log.i("Informationinformation", "MyLostActivityonCreate");
        myLostListViewArrays.clear();
        initData();
        myLostLayoutRefreshLayout.setOnRefreshListener(this); //顶部刷新
        myLostLayoutRefreshLayout.setOnLoadMoreListener(this);  //底部加载
    }

    Long dataLen = 0l;

    public void initData() {
        for (int i = 0; i < 5; i++) {
            MyLost myLost = new MyLost();
            myLost.setTelphone("18827519355");
            myLost.setGoods("校园卡");
            myLost.setDescription("Description");
            myLost.setLostTime("2019-05-30");
            myLost.setLostArea("湖北省 襄樊市 襄城区");
            myLost.setAddress("N6-307");
            myLost.setContacts("Jedis");
            myLost.setContactsTel("1175449172");
            MyLostDaoUtils.insertData(MyLostActivity.this, myLost);
        }


        myLosts = MyLostDaoUtils.queryAll(MyLostActivity.this);
        Log.i("Informationinformation", "MyLostActivityinitData");
        if ((dataLen != myLosts.size()) || (myLosts.size() > 0)) {
            dataLen = Long.valueOf(myLosts.size());
            for (MyLost m : myLosts) {
                Log.i("Informationinformation", "MyLostActivity:" + m.toString());
                MyLostListViewArray a = new MyLostListViewArray(downloadPic(m.getPic1()), m.getGoods(), m.getLostArea(), m.getLostTime());
                myLostListViewArrays.add(a);
            }

            MyLostListViewAdapter myLostListViewAdapter = new MyLostListViewAdapter(MyLostActivity.this, R.layout.activity_my_lost_listview_layout, myLostListViewArrays);
            listView.setAdapter(myLostListViewAdapter);
            listView.setOnItemClickListener(this);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(MyLostActivity.this, LostItemDetailsActivity.class);
        intent.putExtra("goods", new Gson().toJson(myLosts.get(position + 1)));
        startActivity(intent);
    }

    public Bitmap downloadPic(String str) {
        Bitmap bitmap;
        if (str == null) {
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.no_pic);
        } else {
            byte[] bytes = Base64.decode(str, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
        return bitmap;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("Informationinformation", "MyLostActivityonResume");
//        initData();
    }

    @OnClick({R.id.my_lost_layout_layout_back_img, R.id.my_lost_layout_title_textView})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.my_lost_layout_layout_back_img: {
                finish();
            }
            break;
            case R.id.my_lost_layout_title_textView:
                break;
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        refreshLayout.getLayout().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (myLostListViewArrays.size() >= 50) {
                    Toast.makeText(MyLostActivity.this, "全部加载完毕", Toast.LENGTH_SHORT).show();
                    refreshLayout.finishRefresh();
                } else {
                    initData();
                    refreshLayout.finishRefresh();
                }
            }
        }, 2000);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        refreshLayout.getLayout().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (myLostListViewArrays.size() >= 50) {
                    Toast.makeText(MyLostActivity.this, "全部加载完毕", Toast.LENGTH_SHORT).show();
                    refreshLayout.finishLoadMoreWithNoMoreData();
                } else {
                    initData();
                    refreshLayout.finishLoadMore();
                }
            }
        }, 2000);
    }
}
