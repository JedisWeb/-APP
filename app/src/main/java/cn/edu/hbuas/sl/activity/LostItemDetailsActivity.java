package cn.edu.hbuas.sl.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.edu.hbuas.sl.R;
import cn.edu.hbuas.sl.bean.MyLost;
import cn.edu.hbuas.sl.bean.User;
import cn.edu.hbuas.sl.bean.utils.UserDaoUtils;
import cn.edu.hbuas.sl.listViewAdapter.ShareListViewAdapter;
import cn.edu.hbuas.sl.listViewArray.ShareListViewArray;
import cn.edu.hbuas.sl.view.CircleImageView;
import cn.edu.hbuas.sl.view.HorizontalListView;

public class LostItemDetailsActivity extends AppCompatActivity implements PopupWindow.OnDismissListener, View.OnClickListener {

    @BindView(R.id.lost_item_details_layout_back_img)
    ImageView lostItemDetailsLayoutBackImg;
    @BindView(R.id.lost_item_details_layout_title_textView)
    TextView lostItemDetailsLayoutTitleTextView;
    @BindView(R.id.lost_item_details_layout_share_img)
    ImageView lostItemDetailsLayoutShareImg;
    @BindView(R.id.lost_item_details_layout_head_linearLayout)
    RelativeLayout lostItemDetailsLayoutHeadLinearLayout;
    @BindView(R.id.lost_item_details_layout_head_pic_img)
    CircleImageView lostItemDetailsLayoutHeadPicImg;
    @BindView(R.id.lost_item_details_layout_username_textView)
    TextView lostItemDetailsLayoutUsernameTextView;
    @BindView(R.id.lost_item_details_layout_time_interval_textView)
    TextView lostItemDetailsLayoutTimeIntervalTextView;
    @BindView(R.id.lost_item_details_layout_ask_btn)
    Button lostItemDetailsLayoutAskBtn;
    @BindView(R.id.lost_item_details_layout_goods_textView)
    TextView lostItemDetailsLayoutGoodsTextView;
    @BindView(R.id.lost_item_details_layout_pics_img)
    ImageView lostItemDetailsLayoutPicsImg;
    @BindView(R.id.lost_item_details_layout_lostTime_textView)
    TextView lostItemDetailsLayoutLostTimeTextView;
    @BindView(R.id.lost_item_details_layout_lostArea_textView)
    TextView lostItemDetailsLayoutLostAreaTextView;
    @BindView(R.id.lost_item_details_layout_description_textView)
    TextView lostItemDetailsLayoutDescriptionTextView;
    @BindView(R.id.lost_item_details_layout_contacts_textVeiw)
    TextView lostItemDetailsLayoutContactsTextVeiw;
    @BindView(R.id.lost_item_details_layout_contacts_tel_textView)
    TextView lostItemDetailsLayoutContactsTelTextView;
    @BindView(R.id.lost_item_details_layout_claim_btn)
    Button lostItemDetailsLayoutClaimBtn;

    private MyLost myLost = null;
    private User user = null;

    private List<ShareListViewArray> shareListViewArrays = new ArrayList<>();
    private HorizontalListView shareHorizontalListView;
    private View shareView;
    private View parentView;
    private PopupWindow popupWindow = null;
    private TextView btnCancel;
    private WindowManager.LayoutParams params;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_item_details_layout);
        ButterKnife.bind(this);
        initView();
        initShareLayout();
        initShareListView();//初始化数据
    }

    private void initView() {
        myLost = new Gson().fromJson(getIntent().getStringExtra("lost"), MyLost.class);
        if (myLost != null) {
            user = UserDaoUtils.queryUserById(LostItemDetailsActivity.this, Long.valueOf(myLost.getTelphone()));
            if (user != null) {
                lostItemDetailsLayoutHeadPicImg.setImageBitmap(downloadPic(user.getHeadPic()));
                lostItemDetailsLayoutUsernameTextView.setText(user.getNickname());
                lostItemDetailsLayoutTimeIntervalTextView.setText(dateFormat(myLost.getLostTime()));
                lostItemDetailsLayoutGoodsTextView.setText(myLost.getGoods());
                lostItemDetailsLayoutPicsImg.setImageBitmap(downloadPic(myLost.getPic1()));
                lostItemDetailsLayoutLostTimeTextView.setText(myLost.getLostTime());
                lostItemDetailsLayoutLostAreaTextView.setText(myLost.getLostArea());
                lostItemDetailsLayoutDescriptionTextView.setText(myLost.getDescription());
                lostItemDetailsLayoutContactsTextVeiw.setText(myLost.getContacts());
                lostItemDetailsLayoutContactsTelTextView.setText(myLost.getContactsTel());
            }
        }
    }

    private void initShareLayout() {
        shareView = LayoutInflater.from(LostItemDetailsActivity.this).inflate(R.layout.activity_share_layout, null);
        parentView = LayoutInflater.from(LostItemDetailsActivity.this).inflate(R.layout.fragment_mine_layout, null);
        shareHorizontalListView = shareView.findViewById(R.id.share_layout_horizontal_listView); //将适配器导入Listview
        btnCancel = shareView.findViewById(R.id.share_layout_cancel_textView);
        popupWindow = new PopupWindow(shareView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
        popupWindow.setOutsideTouchable(true);
        params = getWindow().getAttributes();
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
        ShareListViewAdapter shareListViewAdapter = new ShareListViewAdapter(LostItemDetailsActivity.this, R.layout.activity_share_listview_layout, shareListViewArrays);
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
                ClipboardManager myClipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                myClipboard.setPrimaryClip(ClipData.newPlainText("text", "weibo"));
                Toast.makeText(LostItemDetailsActivity.this, "weibo", Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
            }
        });
    }

    private void openApp(String str) {
        PackageInfo packageInfo;
        try {
            packageInfo = getPackageManager().getPackageInfo(str, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        if (packageInfo != null) {
            Intent intent = getPackageManager().getLaunchIntentForPackage(str);
            startActivity(intent);
        } else {
            Toast.makeText(LostItemDetailsActivity.this, "未找到应用，请先安装", Toast.LENGTH_SHORT).show();
        }
    }


    @OnClick({
            R.id.lost_item_details_layout_back_img,
            R.id.lost_item_details_layout_share_img,
            R.id.lost_item_details_layout_ask_btn,
            R.id.lost_item_details_layout_pics_img,
            R.id.lost_item_details_layout_claim_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lost_item_details_layout_back_img: {
                finish();
            }
            break;
            case R.id.lost_item_details_layout_share_img: {
                popupWindow.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
                params.alpha = 0.5f;
                getWindow().setAttributes(params);
            }
            break;
            case R.id.lost_item_details_layout_ask_btn: {
                Toast.makeText(this, "询问", Toast.LENGTH_SHORT).show();
            }
            break;
            case R.id.lost_item_details_layout_pics_img:
                break;
            case R.id.lost_item_details_layout_claim_btn:
                Toast.makeText(this, "认领", Toast.LENGTH_SHORT).show();
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

    public String dateFormat(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        Date date;
        try {
            date = sdf.parse(str);
            if (today.getYear() == date.getYear()) {
                if (today.getMonth() == date.getMonth()) {
                    if (today.getDay() == date.getDay()) {
                        if (today.getTime() - date.getTime() > 3600000) {
                            return (today.getTime() - date.getTime()) / 3600000 + "小时前";
                        } else {
                            return (today.getTime() - date.getTime()) / 60000 + "分钟前";
                        }
                    } else {
                        return ((today.getTime() - date.getTime()) / 86400000) + "天前";
                    }
                } else if ((today.getDay() + 30 - date.getDay()) < 30 && (today.getMonth() - date.getMonth() <= 1)) {
                    return ((today.getTime() - date.getTime()) / 86400000) + "天前";
                } else {
                    return (today.getMonth() - date.getMonth()) + "月前";
                }
            } else if ((today.getMonth() + 12 - date.getMonth()) < 12 && (today.getYear() - date.getYear() <= 1)) {
                return (today.getMonth() + 12 - date.getMonth()) + "月前";
            } else {
                return (today.getYear() - date.getYear()) + "年前";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
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
    public void onDismiss() {
        params.alpha = 1.0f;
        getWindow().setAttributes(params);
        popupWindow.dismiss();
    }
}
