package cn.edu.hbuas.sl.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.dou361.dialogui.DialogUIUtils;
import com.dou361.dialogui.bean.TieBean;
import com.dou361.dialogui.listener.DialogUIDateTimeSaveListener;
import com.dou361.dialogui.listener.DialogUIItemListener;
import com.dou361.dialogui.widget.DateSelectorWheelView;
import com.google.gson.Gson;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.lzy.imagepicker.view.CropImageView;

import org.json.JSONArray;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.edu.hbuas.sl.R;
import cn.edu.hbuas.sl.adapter.ImagePickerAdapter;
import cn.edu.hbuas.sl.bean.JsonBean;
import cn.edu.hbuas.sl.bean.MySeek;
import cn.edu.hbuas.sl.bean.utils.MySeekDaoUtils;
import cn.edu.hbuas.sl.fragment.MineFragment;
import cn.edu.hbuas.sl.uploadImg.GlideImageLoader;
import cn.edu.hbuas.sl.uploadImg.HttpUtil;
import cn.edu.hbuas.sl.uploadImg.MyStringCallBack;
import cn.edu.hbuas.sl.util.GetJsonDataUtil;
import cn.edu.hbuas.sl.util.WeiboDialogUtils;
import okhttp3.Call;

public class AddSeekActivity extends AppCompatActivity implements ImagePickerAdapter.OnRecyclerViewItemClickListener, AdapterView.OnItemSelectedListener {


    @BindView(R.id.add_seek_layout_close_img)
    ImageView addSeekLayoutCloseImg;
    @BindView(R.id.add_seek_layou_title_textView)
    TextView addSeekLayouTitleTextView;
    @BindView(R.id.add_seek_layout_publish_textView)
    TextView addSeekLayoutPublishTextView;
    @BindView(R.id.add_seek_layout_head_relativeLayout)
    RelativeLayout addSeekLayoutHeadRelativeLayout;
    @BindView(R.id.add_seek_layout_spinner)
    Spinner addSeekLayoutSpinner;
    @BindView(R.id.add_seek_layout_goods_editText)
    EditText addSeekLayoutGoodsEditText;
    @BindView(R.id.add_seek_layout_description_editText)
    EditText addSeekLayoutDescriptionEditText;
    @BindView(R.id.add_seek_layout_time_textView)
    TextView addSeekLayoutTimeTextView;
    @BindView(R.id.add_seek_layout_time_linearLayout)
    LinearLayout addSeekLayoutTimeLinearLayout;
    @BindView(R.id.add_seek_layout_area_textView)
    TextView addSeekLayoutAreaTextView;
    @BindView(R.id.add_seek_layout_area_right_img)
    ImageView addSeekLayoutAreaRightImg;
    @BindView(R.id.add_seek_layout_area_linearLayout)
    LinearLayout addSeekLayoutAreaLinearLayout;
    @BindView(R.id.add_seek_layout_address_editText)
    EditText addSeekLayoutAddressEditText;
    @BindView(R.id.add_seek_layout_address_linearLayout)
    LinearLayout addSeekLayoutAddressLinearLayout;
    @BindView(R.id.add_seek_layout_contacts_editText)
    EditText addSeekLayoutContactsEditText;
    @BindView(R.id.add_seek_layout_contacts_linearLayout)
    LinearLayout addSeekLayoutContactsLinearLayout;
    @BindView(R.id.add_seek_layout_contacts_tel_editText)
    EditText addSeekLayoutContactsTelEditText;
    @BindView(R.id.add_seek_layout_contacts_tel_linearLayout)
    LinearLayout addSeekLayoutContactsTelLinearLayout;
    @BindView(R.id.add_seek_layout_condition_textView)
    TextView addSeekLayoutConditionTextView;
    @BindView(R.id.add_seek_layout_condition_right_img)
    ImageView addSeekLayoutConditionRightImg;
    @BindView(R.id.add_seek_layout_condition_linearLayout)
    LinearLayout addSeekLayoutConditionLinearLayout;
    @BindView(R.id.add_seek_layout_description_textInputLayout)
    TextInputLayout addSeekLayoutDescriptionTextInputLayout;
    @BindView(R.id.add_seek_layout_address_textInputLayout)
    TextInputLayout addSeekLayoutAddressTextInputLayout;

    private List<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private Thread thread = null;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static boolean isLoaded = false;
    String area = "";

    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;

    private ImagePickerAdapter adapter;
    private ArrayList<ImageItem> selImageList; //当前选择的所有图片
    private int maxImgCount = 4;               //允许选择图片最大数

    private HttpUtil httpUtil;
    private MySeek mySeek = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_seek_layout);
        ButterKnife.bind(this);
        initJSON();

        httpUtil = new HttpUtil();

        //最好放到 Application oncreate执行
        initImagePicker();
        initWidget();
        addSeekLayoutSpinner.setOnItemSelectedListener(this);
    }

    private void initJSON() {
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);

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

    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(true);                            //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
        imagePicker.setSelectLimit(maxImgCount);              //选中数量限制
        imagePicker.setMultiMode(false);                      //多选
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);                         //保存文件的高度。单位像素
    }

    private void initWidget() {
        RecyclerView recyclerView = findViewById(R.id.add_seek_layout_recyclerView);
        selImageList = new ArrayList<>();
        adapter = new ImagePickerAdapter(this, selImageList, maxImgCount);
        adapter.setOnItemClickListener(this);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    private void showPickerView() {
        // 弹出选择器
        OptionsPickerView pvOptions = new OptionsPickerBuilder(AddSeekActivity.this, new OnOptionsSelectListener() {
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
                addSeekLayoutAreaTextView.setText(area);
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

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case IMAGE_ITEM_ADD:
                List<TieBean> names = new ArrayList<TieBean>();
                names.add(new TieBean("拍照"));
                names.add(new TieBean("相册"));
                DialogUIUtils.showSheet(AddSeekActivity.this, names, "取消", Gravity.BOTTOM, true, true, new DialogUIItemListener() {
                    @Override
                    public void onItemClick(CharSequence text, int position) {
                        switch (position) {
                            case 0: // 直接调起相机
                                Toast.makeText(AddSeekActivity.this, "拍照功能还未完善", Toast.LENGTH_SHORT).show();
                                //打开选择,本次允许选择的数量
//                                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
//                                Intent intent = new Intent(AddSeekActivity.this, ImageGridActivity.class);
//                                intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
//                                startActivityForResult(intent, REQUEST_CODE_SELECT);
                                break;
                            case 1:
                                //打开选择,本次允许选择的数量
                                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                                Intent intent1 = new Intent(AddSeekActivity.this, ImageGridActivity.class);
                                startActivityForResult(intent1, REQUEST_CODE_SELECT);
                                break;
                            default:
                                break;
                        }
                    }

                    @Override
                    public void onBottomBtnClick() {
//                        Toast.makeText(AddSeekActivity.this, "取消", Toast.LENGTH_SHORT).show();
                    }
                }).show();
                break;
            default:
                //打开预览
                Intent intentPreview = new Intent(this, ImagePreviewDelActivity.class);
                intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) adapter.getImages());
                intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
                intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
                startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //添加图片返回
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images != null) {
                    selImageList.addAll(images);
                    adapter.setImages(selImageList);
                }
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            //预览图片返回
            if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                if (images != null) {
                    selImageList.clear();
                    selImageList.addAll(images);
                    adapter.setImages(selImageList);
                }
            }
        }
    }

    private String url = "http...";

    private void uploadImage(ArrayList<ImageItem> pathList) {
        httpUtil.postFileRequest(url, null, pathList, new MyStringCallBack() {

            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
            }

            @Override
            public void onResponse(String response, int id) {
                super.onResponse(response, id);
                //返回图片的地址
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String[] goods = getResources().getStringArray(R.array.goods);
        addSeekLayoutGoodsEditText.setText(goods[position]);
//        Toast.makeText(AddSeekActivity.this, goods[position], Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private Dialog mWeiboDialog;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    WeiboDialogUtils.closeDialog(mWeiboDialog);
                    break;
            }
        }
    };

    @OnClick({
            R.id.add_seek_layout_close_img,
            R.id.add_seek_layou_title_textView,
            R.id.add_seek_layout_publish_textView,
            R.id.add_seek_layout_head_relativeLayout,
            R.id.add_seek_layout_goods_editText,
            R.id.add_seek_layout_description_editText,
            R.id.add_seek_layout_time_textView,
            R.id.add_seek_layout_time_linearLayout,
            R.id.add_seek_layout_area_textView,
            R.id.add_seek_layout_area_right_img,
            R.id.add_seek_layout_area_linearLayout,
            R.id.add_seek_layout_address_editText,
            R.id.add_seek_layout_address_linearLayout,
            R.id.add_seek_layout_contacts_editText,
            R.id.add_seek_layout_contacts_linearLayout,
            R.id.add_seek_layout_contacts_tel_editText,
            R.id.add_seek_layout_contacts_tel_linearLayout,
            R.id.add_seek_layout_condition_textView,
            R.id.add_seek_layout_condition_right_img,
            R.id.add_seek_layout_condition_linearLayout
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_seek_layout_close_img: {
                finish();
            }
            break;
            case R.id.add_seek_layou_title_textView:
                break;
            case R.id.add_seek_layout_publish_textView: {
                if (MineFragment.user != null) {
                    mWeiboDialog = WeiboDialogUtils.createLoadingDialog(AddSeekActivity.this, "发布中...");
                    mySeek = new MySeek();
                    mySeek.setTelphone(String.valueOf(MineFragment.user.getTelphone()));
                    mySeek.setGoods(addSeekLayoutGoodsEditText.getText().toString().trim());
                    mySeek.setDescription(addSeekLayoutDescriptionTextInputLayout.getEditText().getText().toString().trim());
                    mySeek.setSeekTime(addSeekLayoutTimeTextView.getText().toString().trim());
                    mySeek.setSeekArea(addSeekLayoutAreaTextView.getText().toString().trim());
                    mySeek.setAddress(addSeekLayoutAddressTextInputLayout.getEditText().getText().toString().trim());
                    mySeek.setContacts(addSeekLayoutContactsEditText.getText().toString().trim());
                    mySeek.setContactsTel(addSeekLayoutContactsTelEditText.getText().toString().trim());
                    uploadPic();
                    MySeekDaoUtils.insertData(AddSeekActivity.this, mySeek);
                    Toast.makeText(this, "发布成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
            break;
            case R.id.add_seek_layout_head_relativeLayout:
                break;
            case R.id.add_seek_layout_goods_editText:
                break;
            case R.id.add_seek_layout_description_editText:
                break;
            case R.id.add_seek_layout_time_textView:
            case R.id.add_seek_layout_time_linearLayout: {
                DialogUIUtils.showDatePick(this, Gravity.CENTER, "选择日期", System.currentTimeMillis() + 60000, DateSelectorWheelView.TYPE_YYYYMMDD, 0, new DialogUIDateTimeSaveListener() {
                    @Override
                    public void onSaveSelectedDate(int tag, String selectedDate) {
                        addSeekLayoutTimeTextView.setText(selectedDate);
                    }
                }).show();
            }
            break;
            case R.id.add_seek_layout_area_textView:
            case R.id.add_seek_layout_area_right_img:
            case R.id.add_seek_layout_area_linearLayout: {
                if (isLoaded) {
                    showPickerView();
                } else {
                    Toast.makeText(AddSeekActivity.this, "数据加载失败，请重试", Toast.LENGTH_SHORT).show();
                }
            }
            break;
            case R.id.add_seek_layout_address_editText:
                break;
            case R.id.add_seek_layout_address_linearLayout:
                break;
            case R.id.add_seek_layout_contacts_editText:
                break;
            case R.id.add_seek_layout_contacts_linearLayout:
                break;
            case R.id.add_seek_layout_contacts_tel_editText:
                break;
            case R.id.add_seek_layout_contacts_tel_linearLayout:
                break;
            case R.id.add_seek_layout_condition_textView:
            case R.id.add_seek_layout_condition_right_img:
            case R.id.add_seek_layout_condition_linearLayout: {
                Intent intent = new Intent(AddSeekActivity.this, ConditionActivity.class);
                startActivity(intent);
            }
            break;
        }
    }

    private void uploadPic() {
        List<String> strings = new ArrayList<>();
        for (ImageItem item : selImageList) {
            if (item != null) {
                Bitmap bitMap = BitmapFactory.decodeFile(item.path);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();//将Bitmap转成Byte[]
                bitMap.compress(Bitmap.CompressFormat.PNG, 50, baos);//压缩
                String pic = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);//加密转换成String
                strings.add(pic);
            } else {
                break;
            }
        }
        switch (strings.size()) {
            case 4: {
                mySeek.setPic4(strings.get(3));
            }
            case 3: {
                mySeek.setPic3(strings.get(2));
            }
            case 2: {
                mySeek.setPic2(strings.get(1));
            }
            case 1: {
                mySeek.setPic1(strings.get(0));
            }
            default:
                break;
        }
    }
}
