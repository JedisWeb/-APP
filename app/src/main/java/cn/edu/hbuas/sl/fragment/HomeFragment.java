package cn.edu.hbuas.sl.fragment;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.edu.hbuas.sl.R;
import cn.edu.hbuas.sl.activity.PositionActivity;
import cn.edu.hbuas.sl.activity.SearchActivity;
import cn.edu.hbuas.sl.banner.loader.GlideImageLoader;
import cn.edu.hbuas.sl.bean.AllGoods;
import cn.edu.hbuas.sl.bean.MyLost;
import cn.edu.hbuas.sl.bean.MySeek;
import cn.edu.hbuas.sl.bean.utils.AllGoodsDaoUtils;
import cn.edu.hbuas.sl.bean.utils.MyLostDaoUtils;
import cn.edu.hbuas.sl.bean.utils.MySeekDaoUtils;
import cn.edu.hbuas.sl.listViewAdapter.AllGoodsListViewAdapter;
import cn.edu.hbuas.sl.listViewArray.AllGoodsListViewArray;


public class HomeFragment extends Fragment implements OnBannerListener, AdapterView.OnItemClickListener, BGARefreshLayout.BGARefreshLayoutDelegate {

    private final int SDK_PERMISSION_REQUEST = 127;
    private String permissionInfo;

    @BindView(R.id.home_layout_position_img)
    ImageView homeLayoutPositionImg;
    @BindView(R.id.home_layout_position_textView)
    TextView homeLayoutPositionTextView;
    @BindView(R.id.home_layout_position_down_img)
    ImageView homeLayoutPositionDownImg;
    @BindView(R.id.home_layout_position_linearLayout)
    LinearLayout homeLayoutPositionLinearLayout;
    @BindView(R.id.home_layout_search_editText)
    EditText homeLayoutSearchEditText;
    @BindView(R.id.home_layout_search_img)
    ImageView homeLayoutSearchImg;
    @BindView(R.id.home_layout_search_relativeLayout)
    RelativeLayout homeLayoutSearchRelativeLayout;
    @BindView(R.id.home_layout_head_linearLayout)
    LinearLayout homeLayoutHeadLinearLayout;
    @BindView(R.id.home_layout_banner)

    Banner homeLayoutBanner;
    Unbinder unbinder;

    private List<AllGoodsListViewArray> allGoodsListViewArrays = new ArrayList<>();
    private ListView listView;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private Context context;
    private View view;

    private IntentFilter intentFilter;
    private LocalBroadcastManager localBroadcastManager;
    private LocalReceiver localReceiver;

    private String city = null;

    private BGARefreshLayout refreshLayout;

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
//        if (AllGoodsDaoUtils.queryAll(context).size() >= 100) {
        Toast.makeText(context, "onBGARefreshLayoutBeginRefreshing", Toast.LENGTH_SHORT).show();
//            refreshLayout.endRefreshing();
//            return;
//        }else{
//
//        }
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        Toast.makeText(context, "onBGARefreshLayoutBeginLoadingMore", Toast.LENGTH_SHORT).show();
        return false;
    }

    private class LocalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            city = intent.getStringExtra("city");
            if (city != null) {
                homeLayoutPositionTextView.setText(city);
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_layout, container, false);

        unbinder = ButterKnife.bind(this, view);

        listView = view.findViewById(R.id.home_layout_listView);
        refreshLayout = view.findViewById(R.id.home_layout_refreshLayout);
        initData();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getPersimmions();
        initBanner();
        receiverBroadcase();
        initData();
        refreshLayout.setDelegate(this);
    }

    private void initBanner() {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        List list = Arrays.asList(getResources().getStringArray(R.array.url));
        List images = new ArrayList(list);
        homeLayoutBanner.setImages(images)
                .setImageLoader(new GlideImageLoader())
                .setOnBannerListener(this)
                .start();
    }

    private void getPersimmions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> permissions = new ArrayList<String>();
            /***
             * 定位权限为必须权限，用户如果禁止，则每次进入都会申请
             */
            // 定位精确位置
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }

            /*
             * 读写权限和电话状态权限非必要权限(建议授予)只会申请一次，用户同意或者禁止，只会弹一次
             */
            // 读写权限
            if (addPermission(permissions, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissionInfo += "Manifest.permission.WRITE_EXTERNAL_STORAGE Deny \n";
            }
            // 读取电话状态权限
            if (addPermission(permissions, Manifest.permission.READ_PHONE_STATE)) {
                permissionInfo += "Manifest.permission.READ_PHONE_STATE Deny \n";
            }

            if (permissions.size() > 0) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), SDK_PERMISSION_REQUEST);
            }
        }
    }

    private boolean addPermission(ArrayList<String> permissionsList, String permission) {
        if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) { // 如果应用没有获得对应权限,则添加到列表中,准备批量申请
            if (shouldShowRequestPermissionRationale(permission)) {
                return true;
            } else {
                permissionsList.add(permission);
                return false;
            }

        } else {
            return true;
        }
    }

    Long dataLen = 0l;

    private void initData() {
        Log.i("Informationinformation", "HomeFragmentinitData");
        AllGoodsDaoUtils.deleteAllData(context);
        List<MyLost> myLosts = MyLostDaoUtils.queryAll(context);
        AllGoods allgoods = null;
        if (myLosts.size() > 0) {
            for (MyLost m : myLosts) {
                allgoods = new AllGoods();
                allgoods.setTelphone(m.getTelphone());
                allgoods.setGoods(m.getGoods());
                allgoods.setDescription(m.getDescription());
                allgoods.setSeekTime(m.getLostTime());
                allgoods.setSeekArea(m.getLostArea());
                allgoods.setAddress(m.getAddress());
                allgoods.setContacts(m.getContacts());
                allgoods.setContactsTel(m.getContactsTel());
                allgoods.setPic1(m.getPic1());
                allgoods.setPic2(m.getPic2());
                allgoods.setPic3(m.getPic3());
                allgoods.setPic4(m.getPic4());
                AllGoodsDaoUtils.insertData(context, allgoods);
            }
        }

        List<MySeek> mySeeks = MySeekDaoUtils.queryAll(context);
        if (mySeeks.size() > 0) {
            for (MySeek m : mySeeks) {
                allgoods = new AllGoods();
                allgoods.setTelphone(m.getTelphone());
                allgoods.setGoods(m.getGoods());
                allgoods.setDescription(m.getDescription());
                allgoods.setSeekTime(m.getSeekTime());
                allgoods.setSeekArea(m.getSeekArea());
                allgoods.setAddress(m.getAddress());
                allgoods.setContacts(m.getContacts());
                allgoods.setContactsTel(m.getContactsTel());
                allgoods.setPic1(m.getPic1());
                allgoods.setPic2(m.getPic2());
                allgoods.setPic3(m.getPic3());
                allgoods.setPic4(m.getPic4());
                AllGoodsDaoUtils.insertData(context, allgoods);
            }
        }

        flushId();

        List<AllGoods> allGoods = AllGoodsDaoUtils.queryAll(context);
        if ((dataLen != allGoods.size()) || (allGoods.size() > 0)) {
            dataLen = Long.valueOf(myLosts.size());
            allGoodsListViewArrays.clear();
            for (AllGoods a : allGoods) {
                AllGoodsListViewArray array = new AllGoodsListViewArray(downloadPic(a.getPic1()), a.getGoods(), a.getSeekArea(), a.getSeekTime());
                allGoodsListViewArrays.add(array);
            }

            AllGoodsListViewAdapter allGoodsListViewAdapter = new AllGoodsListViewAdapter(context, R.layout.activity_my_lost_listview_layout, allGoodsListViewArrays);
            listView.setAdapter(allGoodsListViewAdapter);
            listView.setOnItemClickListener(this);
        }
    }

    public void flushId() {
        int i = 1;
        List<AllGoods> list = AllGoodsDaoUtils.queryAll(context);
        AllGoodsDaoUtils.deleteAllData(context);
        for (AllGoods a : list) {
            a.setNumber(Long.valueOf(i++));
            AllGoodsDaoUtils.insertData(context, a);
        }
    }

    private void receiverBroadcase() {
        localBroadcastManager = LocalBroadcastManager.getInstance(context);
        intentFilter = new IntentFilter();
        intentFilter.addAction("cn.edu.hbuas.sl.HomeFragment");
        localReceiver = new LocalReceiver();
        localBroadcastManager.registerReceiver(localReceiver, intentFilter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(context, "" + position, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void OnBannerClick(int position) {
        Toast.makeText(context, "你点击了：" + position, Toast.LENGTH_SHORT).show();
    }


    //如果你需要考虑更好的体验，可以这么操作
    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
        homeLayoutBanner.startAutoPlay();
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
        Log.i("Informationinformation", "HomeFragmentonResume");
    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        homeLayoutBanner.stopAutoPlay();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({
            R.id.home_layout_position_img,
            R.id.home_layout_position_textView,
            R.id.home_layout_position_down_img,
            R.id.home_layout_position_linearLayout,
            R.id.home_layout_search_editText,
            R.id.home_layout_search_img,
            R.id.home_layout_search_relativeLayout,
            R.id.home_layout_head_linearLayout,
            R.id.home_layout_banner
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.home_layout_position_img:
            case R.id.home_layout_position_textView:
            case R.id.home_layout_position_down_img:
            case R.id.home_layout_position_linearLayout: {
                Intent intent = new Intent(context, PositionActivity.class);
                startActivity(intent);
            }
            break;
            case R.id.home_layout_search_editText:
            case R.id.home_layout_search_img:
            case R.id.home_layout_search_relativeLayout: {
                Intent intent = new Intent(context, SearchActivity.class);
                startActivity(intent);
            }
            break;
            case R.id.home_layout_head_linearLayout:
                break;
            case R.id.home_layout_banner:
                break;
        }
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
}
