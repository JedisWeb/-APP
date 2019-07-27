package cn.edu.hbuas.sl.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
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
import cn.edu.hbuas.sl.listViewAdapter.AboutUsListViewAdapter;
import cn.edu.hbuas.sl.listViewArray.AboutUsListViewArray;

public class AboutUsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {


    @BindView(R.id.about_us_layout_back_img)
    ImageView aboutUsLayoutBackImg;
    @BindView(R.id.about_us_layout_version_textView)
    TextView aboutUsLayoutVersionTextView;

    private List<AboutUsListViewArray> listViewArrays = new ArrayList<>();
    private ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us_layout);
        ButterKnife.bind(this);

        listView = findViewById(R.id.about_us_layout_listview); //将适配器导入Listview

        initListView(); //初始化数据
    }

    /*
   导入数据
    */
    public void initListView() {
        AboutUsListViewArray appExplain = new AboutUsListViewArray("APP介绍", R.drawable.right);
        listViewArrays.add(appExplain);
        AboutUsListViewArray update = new AboutUsListViewArray("检测更新", R.drawable.right);
        listViewArrays.add(update);
        AboutUsListViewArray copyright = new AboutUsListViewArray("版权信息", R.drawable.right);
        listViewArrays.add(copyright);

        //创建适配器，在适配器中导入数据 1.当前类 2.list_view一行的布局 3.数据集合
        AboutUsListViewAdapter aboutUsListViewAdapter = new AboutUsListViewAdapter(AboutUsActivity.this, R.layout.activity_about_us_listview_layout, listViewArrays);
        listView.setAdapter(aboutUsListViewAdapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0: {
                Intent intent = new Intent(AboutUsActivity.this, ExplainActivity.class);
                startActivity(intent);
            }
            break;
            case 1: {
                try {
                    Thread.sleep(500);
                    Toast.makeText(this, "当前已是最新版本", Toast.LENGTH_SHORT).show();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            break;
            case 2: {
                Intent intent = new Intent(AboutUsActivity.this, CopyrightActivity.class);
                startActivity(intent);
            }
            break;
        }
    }

    @OnClick({R.id.about_us_layout_back_img})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.about_us_layout_back_img: {
                finish();
            }
            break;
        }
    }

    //版本更新代码
//    private void checkAppUpdate() {
//        PgyUpdateManager.register(MainActivity.this,
//                new UpdateManagerListener() {
//                    @Override
//                    public void onUpdateAvailable(final String result) {
//                        // 将新版本信息封装到AppBean中
//                        final AppBean appBean = getAppBeanFromString(result);
//                        new AlertDialog.Builder(MainActivity.this)
//                                .setIcon(R.drawable.update_bg)
//                                .setTitle("发现新版本,立即更新?")
//                                .setMessage(appBean.getReleaseNote())
//                                .setNegativeButton(
//                                        "确定",
//                                        new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(
//                                                    DialogInterface dialog,
//                                                    int which) {
//                                                startDownloadTask(
//                                                        MainActivity.this,
//                                                        appBean.getDownloadURL());
//
//                                            }
//                                        }).show();
//                    }
//
//                    @Override
//                    public void onNoUpdateAvailable() {
//                        Toast.makeText(MainActivity.this, "当前应用已经是最新版了！", Toast.LENGTH_SHORT).show();
//                    }
//                });
}
