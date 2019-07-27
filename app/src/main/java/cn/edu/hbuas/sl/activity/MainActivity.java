package cn.edu.hbuas.sl.activity;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ashokvarma.bottomnavigation.TextBadgeItem;

import java.util.ArrayList;
import java.util.List;

import cn.edu.hbuas.sl.R;
import cn.edu.hbuas.sl.adapter.SectionsPagerAdapter;
import cn.edu.hbuas.sl.fragment.AddFragment;
import cn.edu.hbuas.sl.fragment.HomeFragment;
import cn.edu.hbuas.sl.fragment.MessageFragment;
import cn.edu.hbuas.sl.fragment.MineFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationBar
        .OnTabSelectedListener, ViewPager.OnPageChangeListener {

    private ViewPager viewPager;
    private static BottomNavigationBar bottomNavigationBar;
    private TextBadgeItem badgeItem;
    private List<android.support.v4.app.Fragment> fragments; //ViewPager的数据源

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        initBottomNavigationBar();
        initViewPager();
    }

    private void initBottomNavigationBar() {
        bottomNavigationBar = findViewById(R.id.main_layout_bottom_nav_bar);
        bottomNavigationBar.setTabSelectedListener(this);
        bottomNavigationBar.clearAll();
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bottomNavigationBar.setBarBackgroundColor(R.color.white);
        bottomNavigationBar.setActiveColor(R.color.colorPrimary);
        bottomNavigationBar.setInActiveColor(R.color.transparent);
        badgeItem = new TextBadgeItem()
                .setHideOnSelect(true) //设置被选中时隐藏角标
                .setBackgroundColor(Color.RED)
                .setText("1");

        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.home_select, "首页").setInactiveIcon(ContextCompat.getDrawable(this, R.drawable.home)))
                .addItem(new BottomNavigationItem(R.drawable.add_select, "发布").setInactiveIcon(ContextCompat.getDrawable(this, R.drawable.add)))
                .addItem(new BottomNavigationItem(R.drawable.message_select, "消息").setInactiveIcon(ContextCompat.getDrawable(this, R.drawable.message)).setBadgeItem(badgeItem))
                .addItem(new BottomNavigationItem(R.drawable.mine_select, "我的").setInactiveIcon(ContextCompat.getDrawable(this, R.drawable.mine)))
                .setFirstSelectedPosition(0)
                .initialise(); //所有的设置需在调用该方法前完成
    }

    private void initViewPager() {
        viewPager = findViewById(R.id.main_layout_viewPager);

        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new AddFragment());
        fragments.add(new MessageFragment());
        fragments.add(new MineFragment());

        viewPager.setAdapter(new SectionsPagerAdapter(getSupportFragmentManager(), fragments));
        viewPager.setOnPageChangeListener(this);
        viewPager.setOffscreenPageLimit(4);//ViewPager的缓存为2帧
        viewPager.setCurrentItem(0);//初始设置ViewPager选中第一帧
    }

    @Override
    public void onTabSelected(int position) {
        viewPager.setCurrentItem(position);
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        bottomNavigationBar.selectTab(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}