package cn.edu.hbuas.sl.listViewAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.edu.hbuas.sl.R;
import cn.edu.hbuas.sl.listViewArray.MySeekListViewArray;

public class MySeekListViewAdapter extends ArrayAdapter<MySeekListViewArray> {
    private int recourceId;

    /*
    ImageListAdapter( Context context,  int resource,  List<ImageListArray> objects)解析
    Context context ：当前类或者当前类的Context上下文
    int resource  ：ListView的一行布局，它将会导入到适配器中与数据自动适配
    List<ImageListArray> objects ：数据的List集合
     */
    public MySeekListViewAdapter(Context context, int resource, List<MySeekListViewArray> objects) {
        super(context, resource, objects);
        recourceId = resource;

    }

    @NonNull
    @Override
    /*
    为什么要重写getView？因为适配器中其实自带一个返回布局的方法，
    这个方法可以是自定义适配一行的布局显示，因为我们需要更复杂的布局内容，
    所以我们直接重写它，，不需要在导入一个简单的TextView或者ImageView布局让适配器在写入布局数据。
    所以在recourceId自定义布局id直接导入到getView里面，getView方法不在convertView中获取布局了。
    最后只要返回一个view布局就可以。
     */
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        MySeekListViewArray imageListArray = getItem(position); //得到集合中指定位置的一组数据，并且实例化
        View view = LayoutInflater.from(getContext()).inflate(recourceId, parent, false); //用布局裁剪器(又叫布局膨胀器)，将导入的布局裁剪并且放入到当前布局中
        ImageView pic = view.findViewById(R.id.my_seek_listview_layout_pic_img);
        TextView goods = view.findViewById(R.id.my_seek_listview_layout_goods_textView); //从裁剪好的布局里获取TextView布局Id
        TextView place = view.findViewById(R.id.my_seek_listview_layout_place_textVeiw);
        TextView time = view.findViewById(R.id.my_seek_listview_layout_time_textView);
        pic.setImageBitmap(imageListArray.getPic());
        goods.setText(imageListArray.getGoods());//将当前一组imageListArray类中的TextView内容导入到TextView布局中
        place.setText(imageListArray.getPlace());
        time.setText(imageListArray.getTime());
//        pic.set
        return view;
    }
}
