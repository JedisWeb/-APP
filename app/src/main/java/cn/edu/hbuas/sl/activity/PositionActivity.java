package cn.edu.hbuas.sl.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.edu.hbuas.sl.R;
import cn.edu.hbuas.sl.bean.utils.UserDaoUtils;
import cn.edu.hbuas.sl.fragment.MineFragment;

public class PositionActivity extends AppCompatActivity {

    //百度地图对象
    public BaiduMap baiduMap = null;
    //定位相关声明
    public LocationClient locationClient = null;
    //自定义图标
    BitmapDescriptor mCurrentMarket = null;
    //是否首次定位
    boolean isFirstLoc = true;

    @BindView(R.id.position_layout_position_btn)
    ImageView positionLayoutPositionBtn;
    //地图控件
    @BindView(R.id.position_layout_mapView)
    MapView positionLayoutMapView;
    @BindView(R.id.position_layout_floatingActionButton_general_map)
    FloatingActionButton positionLayoutFloatingActionButtonGeneralMap;
    @BindView(R.id.position_layout_floatingActionButton_satellite_map)
    FloatingActionButton positionLayoutFloatingActionButtonSatelliteMap;
    @BindView(R.id.position_layout_floatingActionsMenu)
    FloatingActionsMenu positionLayoutFloatingActionsMenu;

    //得到经纬度
    private double longitude;
    private double latitude;
    private LatLng ll;
    private String addr;
    private String country;
    private String province;
    private String city = null;
    private String district;
    private String street;

    //注册LocationListener监听器
    private MyLocationListener myLocationListener = new MyLocationListener();

    private LocalBroadcastManager localBroadcastManager;

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            addr = location.getAddrStr();    //获取详细地址信息
            country = location.getCountry();    //获取国家
            province = location.getProvince();    //获取省份
            city = location.getCity();    //获取城市
            district = location.getDistrict();    //获取区县
            street = location.getStreet();    //获取街道信息

            longitude = location.getLongitude();
            latitude = location.getLatitude();

            Log.i("Informationinformation", "222222222222222222");
            Log.i("Informationinformation", "详细地址：" + addr + ",国家：" + country + ",省份：" + province + ",城市：" + city + ",区县：" + district + ",街道：" + street);
            localBroadcastManager = LocalBroadcastManager.getInstance(PositionActivity.this);
            Intent intent = new Intent("cn.edu.hbuas.sl.HomeFragment");
            if (city != null) {
                intent.putExtra("city", city);
                localBroadcastManager.sendBroadcast(intent);
            }
            if (MineFragment.user != null) {
                MineFragment.user.setArea(province + city + district);
                UserDaoUtils.updateData(PositionActivity.this, MineFragment.user);
            }

            boolean isLocateFailed = false;//定位是否成功
            //MAP VIEW 销毁后不在处理新接收的位置
            if (location == null || positionLayoutMapView == null) {
                Toast.makeText(PositionActivity.this, "定位失败，请检查是否打开定位权限", Toast.LENGTH_SHORT).show();
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    //此处设置开发者获取到的方向信息，顺时针0-360
                    .accuracy(location.getRadius())
                    .direction(100).latitude(latitude)
                    .longitude(longitude).build();
            baiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                ll = new LatLng(latitude, longitude);
                updateLL();
                isFirstLoc = false;
            }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //在使用SDK个组件之前初始化context信息，传入ApplicationContext
        //注意改方法在在setContextView方法之前实现
        SDKInitializer.initialize(getApplicationContext());

        setContentView(R.layout.activity_position_layout);
        ButterKnife.bind(this);

        initView();
        initData();
        Log.i("Informationinformation", "1111111111111111");
        Log.i("Informationinformation", "详细地址：" + addr + ",国家：" + country + ",省份：" + province + ",城市：" + city + ",区县：" + district + ",街道：" + street);
    }

    private void initData() {
        //开启地图定位图层
        locationClient.start();//开始定位
        baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);//设置为一般地图
//        baiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);//设置为卫星地图
        baiduMap.setTrafficEnabled(true);//开启交通图
    }

    private void initView() {
        baiduMap = positionLayoutMapView.getMap();
        //开启定位图层
        baiduMap.setMyLocationEnabled(true);

        locationClient = new LocationClient(this);//实例化LocationClient类
        //设置locationClientOption
        locationClient.setLocOption(getLocationOption());
        locationClient.registerLocationListener(myLocationListener);

        //重置自定义定位图标
//        LatLng point = new LatLng(latitude, longitude);
//        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.position_select);
//        // 构建MarkerOption，用于在地图上添加Marker
//        OverlayOptions option = new MarkerOptions().position(point).icon(bitmap);
//        // 在地图上添加Marker，并显示
//        baiduMap.addOverlay(option);
//        baiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(point));
    }

    private void updateLL() {
        //设置地图中心点以及缩放级别
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLngZoom(ll, 16);
        baiduMap.animateMapStatus(mapStatusUpdate);
    }

    @OnClick({
            R.id.position_layout_position_btn,
            R.id.position_layout_floatingActionButton_general_map,
            R.id.position_layout_floatingActionButton_satellite_map,
            R.id.position_layout_floatingActionsMenu
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.position_layout_position_btn: {
                updateLL();
            }
            break;
            case R.id.position_layout_floatingActionButton_general_map: {
                baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);//设置为一般地图
            }
            break;
            case R.id.position_layout_floatingActionButton_satellite_map: {
                baiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);//设置为卫星地图
            }
            break;
            case R.id.position_layout_floatingActionsMenu:
                break;
        }
    }

    /**
     * 设置定位参数
     */
    private LocationClientOption getLocationOption() {
        //通过LocationClientOption设置LocationClient相关参数
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);//打开GPS
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//设置定位模式
        option.setCoorType("bd0911");//返回的定位结果是百度经纬度，默认值是gcj02
        option.setScanSpan(5000);//设置发起定位请求的时间间隔为5000ms
        option.setIsNeedAddress(true);//返回的定位结果饱饭地址信息
        option.setNeedDeviceDirect(true);// 返回的定位信息包含手机的机头方向
        return option;
    }

    //三个状态实现地图生命周期管理
    @Override
    protected void onDestroy() {
        //退出销毁
        locationClient.stop();
        baiduMap.setMyLocationEnabled(false);
        super.onDestroy();
        positionLayoutMapView.onDestroy();
        positionLayoutMapView = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        positionLayoutMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        positionLayoutMapView.onPause();
    }

}
