package cn.edu.hbuas.sl.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.dou361.dialogui.DialogUIUtils;
import com.dou361.dialogui.bean.TieBean;
import com.dou361.dialogui.listener.DialogUIDateTimeSaveListener;
import com.dou361.dialogui.listener.DialogUIItemListener;
import com.dou361.dialogui.listener.DialogUIListener;
import com.dou361.dialogui.widget.DateSelectorWheelView;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.edu.hbuas.sl.BuildConfig;
import cn.edu.hbuas.sl.R;
import cn.edu.hbuas.sl.bean.JsonBean;
import cn.edu.hbuas.sl.bean.User;
import cn.edu.hbuas.sl.bean.utils.UserDaoUtils;
import cn.edu.hbuas.sl.fragment.MineFragment;
import cn.edu.hbuas.sl.util.FileUtil;
import cn.edu.hbuas.sl.util.GetJsonDataUtil;
import cn.edu.hbuas.sl.view.CircleImageView;

import static cn.edu.hbuas.sl.util.FileUtil.getRealFilePathFromUri;

public class InformationActivity extends AppCompatActivity {

    private List<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private Thread thread = null;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static boolean isLoaded = false;
    String area = "";

    @BindView(R.id.information_layout_back_img)
    ImageView informationLayoutbackImg;
    @BindView(R.id.information_layout_title_relativeLayout)
    RelativeLayout informationLayoutTitleRelativeLayout;
    @BindView(R.id.information_layout_head_textView)
    TextView informationLayoutHeadTextView;
    @BindView(R.id.information_layout_head_right_img)
    ImageView informationLayoutHeadRightImg;
    @BindView(R.id.information_layout_head_relativeLayout)
    RelativeLayout informationLayoutHeadRelativeLayout;
    @BindView(R.id.information_layout_nickname_textView)
    TextView informationLayoutNicknameTextView;
    @BindView(R.id.information_layout_nickname_right_img)
    ImageView informationLayoutNicknameRightImg;
    @BindView(R.id.information_layout_nickname_linearLayout)
    LinearLayout informationLayoutNicknameLinearLayout;
    @BindView(R.id.information_layout_sex_textView)
    TextView informationLayoutSexTextView;
    @BindView(R.id.information_layout_sex_right_img)
    ImageView informationLayoutSexRightImg;
    @BindView(R.id.information_layout_sex_linearLayout)
    LinearLayout informationLayoutSexLinearLayout;
    @BindView(R.id.information_layout_birthday_textView)
    TextView informationLayoutBirthdayTextView;
    @BindView(R.id.information_layout_birthday_right_img)
    ImageView informationLayoutBirthdayRightImg;
    @BindView(R.id.information_layout_birthday_linearLayout)
    LinearLayout informationLayoutBirthdayLinearLayout;
    @BindView(R.id.information_layout_area_textView)
    TextView informationLayoutAreaTextView;
    @BindView(R.id.information_layout_area_right_img)
    ImageView informationLayoutAreaRightImg;
    @BindView(R.id.information_layout_area_linearLayout)
    LinearLayout informationLayoutAreaLinearLayout;

    @BindView(R.id.information_layout_head_img)
    CircleImageView informationLayoutHeadImg;

    //请求相机
    private static final int REQUEST_CAPTURE = 100;
    //请求相册
    private static final int REQUEST_PICK = 101;
    //请求截图
    private static final int REQUEST_CROP_PHOTO = 102;
    //请求访问外部存储
    private static final int READ_EXTERNAL_STORAGE_REQUEST_CODE = 103;
    //请求写入外部存储
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 104;
    //调用照相机返回图片文件
    private File tempFile;
    private User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_layout);

        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
        ButterKnife.bind(this);

        user = new Gson().fromJson(getIntent().getStringExtra("user"), User.class);
        updateInf();
    }

    private void updateInf() {
        if (user != null) {
            if (user.getHeadPic() != null) {
                informationLayoutHeadImg.setImageBitmap(downloadPic());
            }
            informationLayoutNicknameTextView.setText(user.getNickname());
            informationLayoutSexTextView.setText(user.getGender());
            informationLayoutBirthdayTextView.setText(user.getBirthday());
            informationLayoutAreaTextView.setText(user.getArea());
        }
    }

    @OnClick({
            R.id.information_layout_back_img,
            R.id.information_layout_title_relativeLayout,
            R.id.information_layout_head_img,
            R.id.information_layout_head_right_img,
            R.id.information_layout_head_relativeLayout,
            R.id.information_layout_nickname_textView,
            R.id.information_layout_nickname_right_img,
            R.id.information_layout_nickname_linearLayout,
            R.id.information_layout_sex_textView,
            R.id.information_layout_sex_right_img,
            R.id.information_layout_sex_linearLayout,
            R.id.information_layout_birthday_textView,
            R.id.information_layout_birthday_right_img,
            R.id.information_layout_birthday_linearLayout,
            R.id.information_layout_area_textView,
            R.id.information_layout_area_right_img,
            R.id.information_layout_area_linearLayout
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.information_layout_back_img: {
                finish();
            }
            break;
            case R.id.information_layout_title_relativeLayout:
                break;
            case R.id.information_layout_head_img:
            case R.id.information_layout_head_right_img:
            case R.id.information_layout_head_relativeLayout: {
                List<TieBean> names = new ArrayList<TieBean>();
                names.add(new TieBean("拍照"));
                names.add(new TieBean("相册"));
                DialogUIUtils.showSheet(InformationActivity.this, names, "取消", Gravity.BOTTOM, true, true, new DialogUIItemListener() {
                    @Override
                    public void onItemClick(CharSequence text, int position) {
                        switch (position) {
                            case 0: // 直接调起相机
                            {
                                Toast.makeText(InformationActivity.this, "拍照功能还未完善", Toast.LENGTH_SHORT).show();
//                                //权限判断
//                                if (ContextCompat.checkSelfPermission(InformationActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                                        != PackageManager.PERMISSION_GRANTED) {
//                                    //申请WRITE_EXTERNAL_STORAGE权限
//                                    ActivityCompat.requestPermissions(InformationActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                                            WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
//                                } else {
//                                    //跳转到调用系统相机
//                                    gotoCamera();
//                                }
                            }
                            break;
                            case 1: {
                                //权限判断
                                if (ContextCompat.checkSelfPermission(InformationActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                                        != PackageManager.PERMISSION_GRANTED) {
                                    //申请READ_EXTERNAL_STORAGE权限
                                    ActivityCompat.requestPermissions(InformationActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                            READ_EXTERNAL_STORAGE_REQUEST_CODE);
                                } else {
                                    //跳转到相册
                                    gotoPhoto();
                                }
                            }
                            break;
                        }
                    }

                    @Override
                    public void onBottomBtnClick() {
                        Toast.makeText(InformationActivity.this, "取消", Toast.LENGTH_SHORT).show();
                    }
                }).show();

            }
            break;
            case R.id.information_layout_nickname_textView:
            case R.id.information_layout_nickname_right_img:
            case R.id.information_layout_nickname_linearLayout: {
                DialogUIUtils.showAlert(InformationActivity.this, "", "", "昵称", "", "确定", "取消", false, true, true, new DialogUIListener() {
                    @Override
                    public void onPositive() {
                    }

                    @Override
                    public void onNegative() {

                    }

                    @Override
                    public void onGetInput(CharSequence input1, CharSequence input2) {
                        super.onGetInput(input1, input2);
                        informationLayoutNicknameTextView.setText(input1);
                        MineFragment.user.setNickname(input1 + "");
                        UserDaoUtils.updateData(InformationActivity.this, MineFragment.user);
                    }
                }).show();
            }
            break;
            case R.id.information_layout_sex_textView:
            case R.id.information_layout_sex_right_img:
            case R.id.information_layout_sex_linearLayout: {
                List<TieBean> strings = new ArrayList<TieBean>();
                strings.add(new TieBean("男"));
                strings.add(new TieBean("女"));
                DialogUIUtils.showSheet(InformationActivity.this, strings, "", Gravity.CENTER, true, true, new DialogUIItemListener() {
                    @Override
                    public void onItemClick(CharSequence text, int position) {
                        informationLayoutSexTextView.setText(text);
                        MineFragment.user.setGender(text + "");
                        UserDaoUtils.updateData(InformationActivity.this, MineFragment.user);
                    }
                }).show();
            }
            break;
            case R.id.information_layout_birthday_textView:
            case R.id.information_layout_birthday_right_img:
            case R.id.information_layout_birthday_linearLayout: {
                DialogUIUtils.showDatePick(this, Gravity.CENTER, "选择日期", System.currentTimeMillis() + 60000, DateSelectorWheelView.TYPE_YYYYMMDD, 0, new DialogUIDateTimeSaveListener() {
                    @Override
                    public void onSaveSelectedDate(int tag, String selectedDate) {
                        informationLayoutBirthdayTextView.setText(selectedDate);
                        MineFragment.user.setBirthday(selectedDate + "");
                        UserDaoUtils.updateData(InformationActivity.this, MineFragment.user);
                    }
                }).show();
            }
            break;
            case R.id.information_layout_area_textView:
            case R.id.information_layout_area_right_img:
            case R.id.information_layout_area_linearLayout: {
                if (isLoaded) {
                    showPickerView();
                } else {
                    Toast.makeText(InformationActivity.this, "数据加载失败，请重试", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread == null) {//如果已创建就不再重新创建子线程了
                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 子线程中解析省市区数据
                                initJsonData();
                            }
                        });
                        thread.start();
                    }
                    break;
                case MSG_LOAD_SUCCESS:
                    isLoaded = true;
                    break;
            }
        }
    };


    private void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(this, "province.json");//获取assets目录下的json文件数据
//        String JsonData = getJson("province.json");


        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> cityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String cityName = jsonBean.get(i).getCityList().get(c).getName();
                cityList.add(cityName);//添加城市
                ArrayList<String> city_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                /*if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    city_AreaList.add("");
                } else {
                    city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }*/
                city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                province_AreaList.add(city_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(cityList);

            /**
             * 添加地区数据
             */
            options3Items.add(province_AreaList);
        }
        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);
    }

    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }

    private void showPickerView() {
        // 弹出选择器
        OptionsPickerView pvOptions = new OptionsPickerBuilder(InformationActivity.this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String opt1tx = options1Items.size() > 0 ?
                        options1Items.get(options1).getPickerViewText() : "";

                String opt2tx = options2Items.size() > 0
                        && options2Items.get(options1).size() > 0 ?
                        options2Items.get(options1).get(options2) : "";

                String opt3tx = options2Items.size() > 0
                        && options3Items.get(options1).size() > 0
                        && options3Items.get(options1).get(options2).size() > 0 ?
                        options3Items.get(options1).get(options2).get(options3) : "";

                area = opt1tx + " " + opt2tx + " " + opt3tx;
                informationLayoutAreaTextView.setText(area);
            }
        })
                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(R.color.colorPrimary) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }

    /**
     * 跳转到相册
     */
    private void gotoPhoto() {
        Log.d("evan", "*****************打开图库********************");
        //跳转到调用系统图库
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "请选择图片"), REQUEST_PICK);
    }

    /**
     * 跳转到照相机
     */
    private void gotoCamera() {
        Log.d("evan", "*****************打开相机********************");
        //创建拍照存储的图片文件
        tempFile = new File(FileUtil.checkDirPath(Environment.getExternalStorageDirectory().getPath() + "/image/"), System.currentTimeMillis() + ".jpg");

        //跳转到调用系统相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //设置7.0中共享文件，分享路径定义在xml/file_paths.xml
            intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileProvider", tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        }
        startActivityForResult(intent, REQUEST_CAPTURE);
    }

    /**
     * 打开截图界面
     */
    public void gotoClipActivity(Uri uri) {
        if (uri == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setClass(InformationActivity.this, ClipImageActivity.class);
        intent.setData(uri);
        startActivityForResult(intent, REQUEST_CROP_PHOTO);
    }


    /**
     * 外部存储权限申请返回
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == WRITE_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                gotoCamera();
            }
        } else if (requestCode == READ_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                gotoPhoto();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {
            case REQUEST_CAPTURE: //调用系统相机返回
                if (resultCode == RESULT_OK) {
                    gotoClipActivity(Uri.fromFile(tempFile));
                }
                break;
            case REQUEST_PICK:  //调用系统相册返回
                if (resultCode == RESULT_OK) {
                    Uri uri = intent.getData();
                    gotoClipActivity(uri);
                }
                break;
            case REQUEST_CROP_PHOTO:  //剪切图片返回
                if (resultCode == RESULT_OK) {
                    final Uri uri = intent.getData();
                    if (uri == null) {
                        return;
                    }
                    String cropImagePath = getRealFilePathFromUri(getApplicationContext(), uri);
                    Bitmap bitMap = BitmapFactory.decodeFile(cropImagePath);
                    uploadHeadPic(bitMap);
                    informationLayoutHeadImg.setImageBitmap(downloadPic());
                }
                break;
        }
    }

    private void uploadHeadPic(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();//将Bitmap转成Byte[]
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, baos);//压缩
        String headPicture = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);//加密转换成String
        MineFragment.user.setHeadPic(headPicture);
        UserDaoUtils.updateData(InformationActivity.this, MineFragment.user);
    }

    public static Bitmap downloadPic() {
        byte[] bytes = Base64.decode(MineFragment.user.getHeadPic(), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return bitmap;
    }

}
