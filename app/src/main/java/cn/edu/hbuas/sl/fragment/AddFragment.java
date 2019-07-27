package cn.edu.hbuas.sl.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.edu.hbuas.sl.R;
import cn.edu.hbuas.sl.activity.AddLostActivity;
import cn.edu.hbuas.sl.activity.AddSeekActivity;
import cn.edu.hbuas.sl.activity.MySeekActivity;
import cn.edu.hbuas.sl.activity.SearchActivity;


public class AddFragment extends Fragment {

    @BindView(R.id.add_layout_lost_img)
    ImageView addLayoutLostImg;
    @BindView(R.id.add_layout_lost_textView)
    TextView addLayoutLostTextView;
    @BindView(R.id.add_layout_lost_linearLayout)
    LinearLayout addLayoutLostLinearLayout;
    @BindView(R.id.add_layout_seek_img)
    ImageView addLayoutSeekImg;
    @BindView(R.id.add_layout_seek_textView)
    TextView addLayoutSeekTextView;
    @BindView(R.id.add_layout_seek_linearLayout)
    LinearLayout addLayoutSeekLinearLayout;

    Unbinder unbinder;

    private Context context;
    private View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_layout, container, false);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({
            R.id.add_layout_lost_img,
            R.id.add_layout_lost_textView,
            R.id.add_layout_lost_linearLayout,
            R.id.add_layout_seek_img,
            R.id.add_layout_seek_textView,
            R.id.add_layout_seek_linearLayout
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_layout_lost_img:
            case R.id.add_layout_lost_textView:
            case R.id.add_layout_lost_linearLayout: {
                Intent intent = new Intent(context, AddSeekActivity.class);
                startActivity(intent);
            }
            break;
            case R.id.add_layout_seek_img:
            case R.id.add_layout_seek_textView:
            case R.id.add_layout_seek_linearLayout: {
                Intent intent = new Intent(context, AddLostActivity.class);
                startActivity(intent);
            }
            break;
        }
    }
}
