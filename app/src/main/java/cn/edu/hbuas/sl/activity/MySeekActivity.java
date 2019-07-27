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
import cn.edu.hbuas.sl.bean.MySeek;
import cn.edu.hbuas.sl.bean.utils.MySeekDaoUtils;
import cn.edu.hbuas.sl.listViewAdapter.MySeekListViewAdapter;
import cn.edu.hbuas.sl.listViewArray.MySeekListViewArray;

public class MySeekActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, OnLoadMoreListener, OnRefreshListener {

    @BindView(R.id.my_seek_layout_back_img)
    ImageView mySeekLayoutBackImg;
    @BindView(R.id.my_seek_layout_title_textView)
    TextView mySeekLayoutTitleTextView;
    @BindView(R.id.my_seek_layout_refreshLayout)
    SmartRefreshLayout mySeekLayoutRefreshLayout;

    private List<MySeekListViewArray> mySeekListViewArrays = new ArrayList<>();
    private ListView listView;
    private List<MySeek> mySeeks;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_seek_layout);
        ButterKnife.bind(this);

        listView = findViewById(R.id.my_seek_layout_listview);
        Log.i("Informationinformation", "MySeekActivityonCreate");
        mySeekListViewArrays.clear();
        initData();
        mySeekLayoutRefreshLayout.setOnRefreshListener(this); //顶部刷新
        mySeekLayoutRefreshLayout.setOnLoadMoreListener(this);  //底部加载
    }

    Long dataLen = 0l;

    public void initData() {
        for (int i = 0; i < 5; i++) {
            MySeek mySeek = new MySeek();
            mySeek.setTelphone("18827519355");
            mySeek.setGoods("校园卡");
            mySeek.setDescription("Description");
            mySeek.setSeekTime("2019-05-30");
            mySeek.setSeekArea("湖北省 襄樊市 襄城区");
            mySeek.setAddress("N6-307");
            mySeek.setContacts("Jedis");
            mySeek.setContactsTel("1175449172");
            MySeekDaoUtils.insertData(MySeekActivity.this, mySeek);
        }


        Log.i("Informationinformation", "MySeekActivityinitData");
        mySeeks = MySeekDaoUtils.queryAll(MySeekActivity.this);
        if ((dataLen != mySeeks.size()) || (mySeeks.size() > 0)) {
            dataLen = Long.valueOf(mySeeks.size());
            for (MySeek m : mySeeks) {
                MySeekListViewArray a = new MySeekListViewArray(downloadPic(m.getPic1()), m.getGoods(), m.getSeekArea(), m.getSeekTime());
                mySeekListViewArrays.add(a);
            }

            MySeekListViewAdapter mySeekListViewAdapter = new MySeekListViewAdapter(MySeekActivity.this, R.layout.activity_my_seek_listview_layout, mySeekListViewArrays);
            listView.setAdapter(mySeekListViewAdapter);
            listView.setOnItemClickListener(this);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(MySeekActivity.this, SeekItemDetailsActivity.class);
        intent.putExtra("seek", new Gson().toJson(mySeeks.get(position + 1)));
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
//        initData();
        Log.i("Informationinformation", "MySeekActivityonResume");
    }

    @OnClick({R.id.my_seek_layout_back_img, R.id.my_seek_layout_title_textView})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.my_seek_layout_back_img: {
                finish();
            }
            break;
            case R.id.my_seek_layout_title_textView:
                break;
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        refreshLayout.getLayout().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mySeekListViewArrays.size() >= 50) {
                    Toast.makeText(MySeekActivity.this, "全部加载完毕", Toast.LENGTH_SHORT).show();
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
                if (mySeekListViewArrays.size() >= 50) {
                    Toast.makeText(MySeekActivity.this, "全部加载完毕", Toast.LENGTH_SHORT).show();
                    refreshLayout.finishLoadMoreWithNoMoreData();
                } else {
                    initData();
                    refreshLayout.finishLoadMore();
                }
            }
        }, 2000);
    }
}
