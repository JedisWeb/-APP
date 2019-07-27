package cn.edu.hbuas.sl.fragment;

import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.dou361.dialogui.DialogUIUtils;
import com.dou361.dialogui.bean.TieBean;
import com.dou361.dialogui.listener.DialogUIItemListener;
import com.google.gson.Gson;

import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.edu.hbuas.sl.R;
import cn.edu.hbuas.sl.activity.InformationActivity;
import cn.edu.hbuas.sl.activity.LoginActivity;
import cn.edu.hbuas.sl.activity.MyLostActivity;
import cn.edu.hbuas.sl.activity.MySeekActivity;
import cn.edu.hbuas.sl.activity.SettingActivity;
import cn.edu.hbuas.sl.bean.MyLost;
import cn.edu.hbuas.sl.bean.MySeek;
import cn.edu.hbuas.sl.bean.User;
import cn.edu.hbuas.sl.bean.utils.MyLostDaoUtils;
import cn.edu.hbuas.sl.bean.utils.MySeekDaoUtils;
import cn.edu.hbuas.sl.bean.utils.UserDaoUtils;
import cn.edu.hbuas.sl.listViewAdapter.MineListViewAdapter;
import cn.edu.hbuas.sl.listViewAdapter.ShareListViewAdapter;
import cn.edu.hbuas.sl.listViewArray.MineListViewArray;
import cn.edu.hbuas.sl.listViewArray.ShareListViewArray;
import cn.edu.hbuas.sl.view.HorizontalListView;


public class MineFragment extends Fragment implements View.OnClickListener, PopupWindow.OnDismissListener {

    @BindView(R.id.mine_layout_head_img)
    ImageView mineLayoutHeadImg;
    @BindView(R.id.mine_layout_username_textview)
    TextView mineLayoutUsernameTextview;
    @BindView(R.id.mine_layout_head_linearLayout)
    LinearLayout mineLayoutHeadLinearLayout;
    Unbinder unbinder;

    private Context context;
    private View view;

    private List<MineListViewArray> mainListViewArrays = new ArrayList<>();
    private ListView mainListView;

    private List<ShareListViewArray> shareListViewArrays = new ArrayList<>();
    private HorizontalListView shareHorizontalListView;
    private View shareView;
    private View parentView;
    private PopupWindow popupWindow = null;
    private TextView btnCancel;
    private WindowManager.LayoutParams params;
    public static User user = null;

    private IntentFilter intentFilter;
    private LocalBroadcastManager localBroadcastManager;
    private LocalReceiver localReceiver;

    private SharedPreferences sp;

    private class LocalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            user = new Gson().fromJson(intent.getStringExtra("user"), User.class);
            if (user == null) {
                Toast.makeText(context, "空", Toast.LENGTH_SHORT).show();
            } else if (user.getNickname().equals("")) {
                mineLayoutUsernameTextview.setText(user.getTelphone() + "");
            } else {
                mineLayoutUsernameTextview.setText(user.getNickname());
            }
            Toast.makeText(context, "收到本地广播", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        sp = context.getSharedPreferences("loginData", Context.MODE_PRIVATE);
        receiverBroadcase();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mine_layout, container, false);

        mainListView = view.findViewById(R.id.mine_layout_listview); //将适配器导入Listview
        shareView = LayoutInflater.from(context).inflate(R.layout.activity_share_layout, null);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initMineListView(); //初始化数据
        initShareLayout();
        initShareListView();//初始化数据
        String tel = sp.getString("tel", "");
        String password = sp.getString("password", "");
        if (StringUtils.isEmpty(tel) && StringUtils.isEmpty(password)) {
            Intent intent = new Intent(context, LoginActivity.class);
            startActivity(intent);
        } else {
            user = new User();
            user.setTelphone(Long.valueOf(sp.getString("tel", "")));
            user.setPassword(sp.getString("password", ""));
            user.setNickname(sp.getString("nickname", ""));
            user.setGender(sp.getString("gender", ""));
            user.setBirthday(sp.getString("birthday", ""));
            user.setArea(sp.getString("area", ""));
        }
    }

    private void initTestData() {
        user = new User();
        user.setTelphone(Long.valueOf("18827519355"));
        user.setNickname("Jedis");
        user.setPassword("111111");
        user.setGender("男");
        user.setBirthday("1998-12-10");
        user.setArea("湖北省武穴市");
        UserDaoUtils.insertData(context, user);
    }

    private void initShareLayout() {

        parentView = LayoutInflater.from(context).inflate(R.layout.fragment_mine_layout, null);
        shareHorizontalListView = shareView.findViewById(R.id.share_layout_horizontal_listView); //将适配器导入Listview
        btnCancel = shareView.findViewById(R.id.share_layout_cancel_textView);
        popupWindow = new PopupWindow(shareView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
        popupWindow.setOutsideTouchable(true);
        params = getActivity().getWindow().getAttributes();
        popupWindow.setOnDismissListener(this);
        btnCancel.setOnClickListener(this);
    }

    /*
    导入数据
     */
    public void initShareListView() {
        ShareListViewArray qq = new ShareListViewArray(R.drawable.qq, "QQ好友");
        shareListViewArrays.add(qq);
        ShareListViewArray wechat = new ShareListViewArray(R.drawable.wechat, "微信好友");
        shareListViewArrays.add(wechat);
        ShareListViewArray circleOfFriends = new ShareListViewArray(R.drawable.circle_of_friends, "朋友圈");
        shareListViewArrays.add(circleOfFriends);
        ShareListViewArray weibo = new ShareListViewArray(R.drawable.weibo, "微博");
        shareListViewArrays.add(weibo);
        ShareListViewArray qqZone = new ShareListViewArray(R.drawable.qzone, "QQ空间");
        shareListViewArrays.add(qqZone);

        //创建适配器，在适配器中导入数据 1.当前类 2.list_view一行的布局 3.数据集合
        ShareListViewAdapter shareListViewAdapter = new ShareListViewAdapter(context, R.layout.activity_share_listview_layout, shareListViewArrays);
        shareHorizontalListView.setAdapter(shareListViewAdapter);
        shareHorizontalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    // QQ
                    case 0: {
                        openApp("com.tencent.mobileqq");
                    }
                    break;

                    // 微信
                    case 1: {
                        openApp("tencent.micromsg.weixin");
                    }
                    break;

                    // 朋友圈
                    case 2: {
                        openApp("com.android.settings");
                    }
                    break;

                    // 微博
                    case 3: {
                        openApp("com.sina.weibo");
                    }
                    break;

                    // QQ空间
                    case 4: {
                        openApp("com.qzone");
                    }
                    break;
                }
                // 获取系统剪贴板
                ClipboardManager myClipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                myClipboard.setPrimaryClip(ClipData.newPlainText("text", "weibo"));
                Toast.makeText(context, "weibo", Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
            }
        });
    }

    /*
    导入数据
     */
    public void initMineListView() {
//        MineListViewArray information = new MineListViewArray(R.drawable.edit, "编辑资料", R.drawable.right);
//        mainListViewArrays.add(information);
        MineListViewArray mySeek = new MineListViewArray(R.drawable.seek, "我的招领", R.drawable.right);
        mainListViewArrays.add(mySeek);
        MineListViewArray myLost = new MineListViewArray(R.drawable.lost, "我的失物", R.drawable.right);
        mainListViewArrays.add(myLost);
        MineListViewArray share = new MineListViewArray(R.drawable.share, "分享给好友", R.drawable.right);
        mainListViewArrays.add(share);
        MineListViewArray helpAndSupport = new MineListViewArray(R.drawable.help, "帮助与支持", R.drawable.right);
        mainListViewArrays.add(helpAndSupport);
        MineListViewArray setting = new MineListViewArray(R.drawable.setting, "设置", R.drawable.right);
        mainListViewArrays.add(setting);

        //创建适配器，在适配器中导入数据 1.当前类 2.list_view一行的布局 3.数据集合
        MineListViewAdapter mineListViewAdapter = new MineListViewAdapter(context, R.layout.fragment_mine_listview_layout, mainListViewArrays);
        mainListView.setAdapter(mineListViewAdapter);
        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    // 编辑资料
//                    case 0: {
//                        Intent intent = new Intent(context, InformationActivity.class);
//                        intent.putExtra("user", new Gson().toJson(user));
//                        startActivity(intent);
//                    }
//                    break;

                    // 我的招领
                    case 0: {
                        Intent intent = new Intent(context, MySeekActivity.class);
                        startActivity(intent);
                    }
                    break;

                    // 我的失物
                    case 1: {
                        Intent intent = new Intent(context, MyLostActivity.class);
                        startActivity(intent);
                    }
                    break;

                    // 分享好友
                    case 2: {
                        popupWindow.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
                        params.alpha = 0.5f;
                        getActivity().getWindow().setAttributes(params);
                    }
                    break;

                    // 联系客服
                    case 3: {
                        List<TieBean> strings = new ArrayList<TieBean>();
                        strings.add(new TieBean("客服-九"));
                        strings.add(new TieBean("客服-IU"));
                        strings.add(new TieBean("客服-BE"));
                        strings.add(new TieBean("客服-拾"));
                        DialogUIUtils.showSheet(context, strings, "取消", Gravity.BOTTOM, true, true, new DialogUIItemListener() {
                            @Override
                            public void onItemClick(CharSequence text, int position) {
                                switch (position) {
                                    case 0: {
                                        try {
                                            String url = "mqqwpa://im/chat?chat_type=wpa&uin=2772656784";
                                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                                            Toast.makeText(context, "客服QQ已连接，请先添加好友", Toast.LENGTH_SHORT).show();
                                        } catch (Exception e) {
                                            // 获取系统剪贴板
                                            ClipboardManager myClipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                                            myClipboard.setPrimaryClip(ClipData.newPlainText("text", "2772656784"));
                                            Toast.makeText(context, "客服QQ已复制，请先添加好友", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    break;
                                    case 1: {
                                        try {
                                            String url = "mqqwpa://im/chat?chat_type=wpa&uin=2509836462";
                                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                                            Toast.makeText(context, "客服QQ已连接，请先添加好友", Toast.LENGTH_SHORT).show();
                                        } catch (Exception e) {
                                            // 获取系统剪贴板
                                            ClipboardManager myClipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                                            myClipboard.setPrimaryClip(ClipData.newPlainText("text", "2509836462"));
                                            Toast.makeText(context, "客服QQ已复制，请先添加好友", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    break;
                                    case 2: {
                                        try {
                                            String url = "mqqwpa://im/chat?chat_type=wpa&uin=2582803842";
                                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                                            Toast.makeText(context, "客服QQ已连接，请先添加好友", Toast.LENGTH_SHORT).show();
                                        } catch (Exception e) {
                                            // 获取系统剪贴板
                                            ClipboardManager myClipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                                            myClipboard.setPrimaryClip(ClipData.newPlainText("text", "2582803842"));
                                            Toast.makeText(context, "客服QQ已复制，请先添加好友", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    break;
                                    case 3: {
                                        try {
                                            String url = "mqqwpa://im/chat?chat_type=wpa&uin=2844340931";
                                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                                            Toast.makeText(context, "客服QQ已连接，请先添加好友", Toast.LENGTH_SHORT).show();
                                        } catch (Exception e) {
                                            // 获取系统剪贴板
                                            ClipboardManager myClipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                                            myClipboard.setPrimaryClip(ClipData.newPlainText("text", "2844340931"));
                                            Toast.makeText(context, "客服QQ已复制，请先添加好友", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    break;
                                }
                            }

                            @Override
                            public void onBottomBtnClick() {
                            }
                        }).show();
                    }
                    break;

                    // 设置
                    case 4: {
                        Intent intent = new Intent(context, SettingActivity.class);
                        startActivity(intent);
                    }
                    break;
                }
            }
        });
    }

    private void receiverBroadcase() {
        localBroadcastManager = LocalBroadcastManager.getInstance(context);
        intentFilter = new IntentFilter();
        intentFilter.addAction("cn.edu.hbuas.sl.MineFragment");
        localReceiver = new LocalReceiver();
        localBroadcastManager.registerReceiver(localReceiver, intentFilter);
    }

    private void openApp(String str) {
        PackageInfo packageInfo;
        try {
            packageInfo = getContext().getPackageManager().getPackageInfo(str, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        if (packageInfo != null) {
            Intent intent = getContext().getPackageManager().getLaunchIntentForPackage(str);
            startActivity(intent);
        } else {
            Toast.makeText(context, "未找到应用，请先安装", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({
            R.id.mine_layout_head_img,
            R.id.mine_layout_username_textview,
            R.id.mine_layout_head_linearLayout
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mine_layout_head_img:
            case R.id.mine_layout_username_textview:
            case R.id.mine_layout_head_linearLayout: {
                if (user == null) {
                    Intent intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(context, InformationActivity.class);
                    intent.putExtra("user", new Gson().toJson(user));
                    startActivity(intent);
                }
            }
            break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.share_layout_cancel_textView: {
                popupWindow.dismiss();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        receiverBroadcase();

        if (user != null) {
            if (user.getHeadPic() != null) {
                mineLayoutHeadImg.setImageBitmap(downloadPic());
            }
            if (user.getNickname().equals("")) {
                mineLayoutUsernameTextview.setText(user.getTelphone() + "");
            } else {
                mineLayoutUsernameTextview.setText(user.getNickname());
            }
        } else {
            mineLayoutUsernameTextview.setText("点击头像登录");
        }
    }

    public Bitmap downloadPic() {
        byte[] bytes = Base64.decode(user.getHeadPic(), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return bitmap;
    }

    @Override
    public void onDismiss() {
        params.alpha = 1.0f;
        getActivity().getWindow().setAttributes(params);
        popupWindow.dismiss();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(localReceiver);
    }

    public void initLost() {
        MyLost myLost;
        myLost = new MyLost();
        myLost.setTelphone("18827519355");
        myLost.setGoods("耳机");
        myLost.setDescription("");
        myLost.setLostTime("2019-05-30");
        myLost.setLostArea("湖北省 襄樊市 襄城区");
        myLost.setAddress("N4-JT04");
        myLost.setContacts("XXX");
        myLost.setContactsTel("653139");
        myLost.setPic1(toString(R.drawable.pic2));
        MyLostDaoUtils.insertData(context, myLost);

        myLost = new MyLost();
        myLost.setTelphone("18827519355");
        myLost.setGoods("校园卡");
        myLost.setDescription("");
        myLost.setLostTime("2019-05-30");
        myLost.setLostArea("湖北省 襄樊市 襄城区");
        myLost.setAddress("坠落街");
        myLost.setContacts("XXX");
        myLost.setContactsTel("18827519355");
        myLost.setPic1(toString(R.drawable.pic20));
        MyLostDaoUtils.insertData(context, myLost);

        myLost = new MyLost();
        myLost.setTelphone("18827519355");
        myLost.setGoods("眼镜");
        myLost.setDescription("");
        myLost.setLostTime("2019-05-30");
        myLost.setLostArea("湖北省 襄樊市 襄城区");
        myLost.setAddress("图书馆前的椅子");
        myLost.setContacts("XXX");
        myLost.setContactsTel("18827519355");
        MyLostDaoUtils.insertData(context, myLost);

        myLost = new MyLost();
        myLost.setTelphone("18827519355");
        myLost.setGoods("蓝牙耳机");
        myLost.setDescription("一对粉色蓝牙耳机");
        myLost.setLostTime("2019-06-01");
        myLost.setLostArea("湖北省 襄樊市 襄城区");
        myLost.setAddress("N6-305");
        myLost.setContacts("数学1812陈思静");
        myLost.setContactsTel("18162768919");
        myLost.setPic1(toString(R.drawable.pic10));
        MyLostDaoUtils.insertData(context, myLost);

        myLost = new MyLost();
        myLost.setTelphone("18827519355");
        myLost.setGoods("眼镜");
        myLost.setDescription("眼镜是刚刚配的，很关键，在此之前已经丢了一副眼镜，真的很难受，希望拾到的同学能联系我，非常感谢");
        myLost.setLostTime("2019-06-01");
        myLost.setLostArea("湖北省 襄樊市 襄城区");
        myLost.setAddress("不确定");
        myLost.setContacts("");
        myLost.setContactsTel("2837829356");
        myLost.setPic1(toString(R.drawable.pic12));
        MyLostDaoUtils.insertData(context, myLost);

        myLost = new MyLost();
        myLost.setTelphone("18827519355");
        myLost.setGoods("雨伞");
        myLost.setDescription("下午六点18分的时候在六栋旁还了一辆哈喽助力车，车篓里有把伞忘记拿了，外面有紫色的绒布套，如有拾到请联系");
        myLost.setLostTime("2019-05-31");
        myLost.setLostArea("湖北省 襄樊市 襄城区");
        myLost.setAddress("6栋");
        myLost.setContacts("");
        myLost.setContactsTel("1620783645");
        myLost.setPic1(toString(R.drawable.pic9));
        MyLostDaoUtils.insertData(context, myLost);

        myLost = new MyLost();
        myLost.setTelphone("18827519355");
        myLost.setGoods("身份证");
        myLost.setDescription("本人身份证不慎丢失，姓名：王芙玲，若有拾到，请联系我，非常感谢");
        myLost.setLostTime("2019-05-31");
        myLost.setLostArea("湖北省 襄樊市 襄城区");
        myLost.setAddress("不确定");
        myLost.setContacts("");
        myLost.setContactsTel("3332377313");
        MyLostDaoUtils.insertData(context, myLost);

        myLost = new MyLost();
        myLost.setTelphone("18827519355");
        myLost.setGoods("钥匙");
        myLost.setDescription("早上9点多在老区食堂吃完热干面，随手把一串钥匙放在桌子上，这把钥匙很重要！有好心拾到者请联系我，非常感谢！");
        myLost.setLostTime("2019-05-31");
        myLost.setLostArea("湖北省 襄樊市 襄城区");
        myLost.setAddress("老区食堂");
        myLost.setContacts("");
        myLost.setContactsTel("1727954337");
        myLost.setPic1(toString(R.drawable.pic13));
        MyLostDaoUtils.insertData(context, myLost);

        myLost = new MyLost();
        myLost.setTelphone("18827519355");
        myLost.setGoods("钥匙和体测卡");
        myLost.setDescription("早上，在四栋排球场遗失体测卡和一串钥匙，希望拾到者联系，非常感谢！");
        myLost.setLostTime("2019-05-31");
        myLost.setLostArea("湖北省 襄樊市 襄城区");
        myLost.setAddress("四栋");
        myLost.setContacts("");
        myLost.setContactsTel("1774696300");
        myLost.setPic1(toString(R.drawable.pic13));
        MyLostDaoUtils.insertData(context, myLost);

        myLost = new MyLost();
        myLost.setTelphone("18827519355");
        myLost.setGoods("笔袋");
        myLost.setDescription("上个星期四考高数好像掉了，拾到者请联系。");
        myLost.setLostTime("2019-05-30");
        myLost.setLostArea("湖北省 襄樊市 襄城区");
        myLost.setAddress("N6-307");
        myLost.setContacts("");
        myLost.setContactsTel("13277925051");
        myLost.setPic1(toString(R.drawable.pic13));
        MyLostDaoUtils.insertData(context, myLost);

        myLost = new MyLost();
        myLost.setTelphone("18827519355");
        myLost.setGoods("眼镜");
        myLost.setDescription("在图书馆前面的椅子丢失一副眼镜，拾到者请联系。");
        myLost.setLostTime("2019-05-30");
        myLost.setLostArea("湖北省 襄樊市 襄城区");
        myLost.setAddress("图书馆");
        myLost.setContacts("");
        myLost.setContactsTel("1398107412");
        MyLostDaoUtils.insertData(context, myLost);

        myLost = new MyLost();
        myLost.setTelphone("18827519355");
        myLost.setGoods("校园卡");
        myLost.setDescription("本人于今天中午19栋凤雏家园超市遗失一张校园卡，姓名：安国圣，学号：2016118101，若有抢到，请联系我，谢谢各位了。");
        myLost.setLostTime("2019-05-30");
        myLost.setLostArea("湖北省 襄樊市 襄城区");
        myLost.setAddress("19栋凤雏家园超市");
        myLost.setContacts("");
        myLost.setContactsTel("805644087");
        MyLostDaoUtils.insertData(context, myLost);

        myLost = new MyLost();
        myLost.setTelphone("18827519355");
        myLost.setGoods("身份证");
        myLost.setDescription("本人身份证不慎丢失，姓名：孟亚楠，若有捡到的，请联系我，非常感谢！");
        myLost.setLostTime("2019-05-30");
        myLost.setLostArea("湖北省 襄樊市 襄城区");
        myLost.setAddress("不确定");
        myLost.setContacts("");
        myLost.setContactsTel("1309020590");
        MyLostDaoUtils.insertData(context, myLost);

        myLost = new MyLost();
        myLost.setTelphone("18827519355");
        myLost.setGoods("U盘");
        myLost.setDescription("上周五在五栋103遗失优盘，优盘名字：渣男，如拾到联系，里面有很多学习的资料很重要。");
        myLost.setLostTime("2019-05-30");
        myLost.setLostArea("湖北省 襄樊市 襄城区");
        myLost.setAddress("N5-103");
        myLost.setContacts("");
        myLost.setContactsTel("1902003166");
        myLost.setPic1(toString(R.drawable.pic5));
        MyLostDaoUtils.insertData(context, myLost);
    }

    public void initSeek() {
        MySeek mySeek;
        mySeek = new MySeek();
        mySeek.setTelphone("18827519355");
        mySeek.setGoods("校园卡");
        mySeek.setDescription("在2栋309教室有一张校园卡，16级刘嘉宝，放14号机的键盘下面，失主请自行取回。");
        mySeek.setSeekTime("2019-05-31");
        mySeek.setSeekArea("湖北省 襄樊市 襄城区");
        mySeek.setAddress("N2-309");
        mySeek.setContacts("XXX");
        mySeek.setContactsTel("");
        mySeek.setPic1(toString(R.drawable.pic16));
        MySeekDaoUtils.insertData(context, mySeek);

        mySeek = new MySeek();
        mySeek.setTelphone("18827519355");
        mySeek.setGoods("校园卡");
        mySeek.setDescription("新区4栋教学楼拾到校园卡，请失主到12栋宿管处认领。");
        mySeek.setSeekTime("2019-05-31");
        mySeek.setSeekArea("湖北省 襄樊市 襄城区");
        mySeek.setAddress("新区12栋宿管");
        mySeek.setContacts("XXX");
        mySeek.setContactsTel("");
        mySeek.setPic1(toString(R.drawable.pic18));
        MySeekDaoUtils.insertData(context, mySeek);

        mySeek = new MySeek();
        mySeek.setTelphone("18827519355");
        mySeek.setGoods("雨伞");
        mySeek.setDescription("新区N4-303最后一排发布一把伞，请丢失的小伙伴自己去拿。");
        mySeek.setSeekTime("2019-05-31");
        mySeek.setSeekArea("湖北省 襄樊市 襄城区");
        mySeek.setAddress("N4-303");
        mySeek.setContacts("XXX");
        mySeek.setContactsTel("");
        mySeek.setPic1(toString(R.drawable.pic8));
        MySeekDaoUtils.insertData(context, mySeek);

        mySeek = new MySeek();
        mySeek.setTelphone("18827519355");
        mySeek.setGoods("眼镜");
        mySeek.setDescription("四栋JT02拾到一副眼镜，失主请联系。");
        mySeek.setSeekTime("2019-05-31");
        mySeek.setSeekArea("湖北省 襄樊市 襄城区");
        mySeek.setAddress("N4-JT02");
        mySeek.setContacts("XXX");
        mySeek.setContactsTel("1397868946");
        mySeek.setPic1(toString(R.drawable.pic7));
        MySeekDaoUtils.insertData(context, mySeek);

        mySeek = new MySeek();
        mySeek.setTelphone("18827519355");
        mySeek.setGoods("眼镜");
        mySeek.setDescription("操场捡到，失主请联系。");
        mySeek.setSeekTime("2019-05-31");
        mySeek.setSeekArea("湖北省 襄樊市 襄城区");
        mySeek.setAddress("操场");
        mySeek.setContacts("XXX");
        mySeek.setContactsTel("2830277304");
        mySeek.setPic1(toString(R.drawable.pic4));
        MySeekDaoUtils.insertData(context, mySeek);

        mySeek = new MySeek();
        mySeek.setTelphone("18827519355");
        mySeek.setGoods("书");
        mySeek.setDescription("周三下午七八节在N4-408拾到一本书，失主请联系。");
        mySeek.setSeekTime("2019-05-31");
        mySeek.setSeekArea("湖北省 襄樊市 襄城区");
        mySeek.setAddress("N4-408");
        mySeek.setContacts("XXX");
        mySeek.setContactsTel("2253372103");
        mySeek.setPic1(toString(R.drawable.pic6));
        MySeekDaoUtils.insertData(context, mySeek);

        mySeek = new MySeek();
        mySeek.setTelphone("18827519355");
        mySeek.setGoods("书");
        mySeek.setDescription("本人于今天上午9点50在6栋104拾到手机一部，请失主联系。");
        mySeek.setSeekTime("2019-05-31");
        mySeek.setSeekArea("湖北省 襄樊市 襄城区");
        mySeek.setAddress("N6-104");
        mySeek.setContacts("XXX");
        mySeek.setContactsTel("18871005915");
        mySeek.setPic1(toString(R.drawable.pic11));
        MySeekDaoUtils.insertData(context, mySeek);

        mySeek = new MySeek();
        mySeek.setTelphone("18827519355");
        mySeek.setGoods("排球");
        mySeek.setDescription("今天在老区排球场拾到一个排球，失主请联系我领回。");
        mySeek.setSeekTime("2019-05-31");
        mySeek.setSeekArea("湖北省 襄樊市 襄城区");
        mySeek.setAddress("老区排球场");
        mySeek.setContacts("XXX");
        mySeek.setContactsTel("1939309616");
        mySeek.setPic1(toString(R.drawable.pic1));
        MySeekDaoUtils.insertData(context, mySeek);
    }

    public String toString(int dra) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), dra);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();//将Bitmap转成Byte[]
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, baos);//压缩
        String pic = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);//加密转换成String
        return pic;
    }

}
