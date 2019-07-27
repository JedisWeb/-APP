package cn.edu.hbuas.sl.banner.loader;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

import cn.edu.hbuas.sl.banner.ui.RoundAngleImageView;

public class GlideImageLoader extends ImageLoader implements com.lzy.imagepicker.loader.ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        //具体方法内容自己去选择，次方法是为了减少banner过多的依赖第三方包，所以将这个权限开放给使用者去选择
        Glide.with(context.getApplicationContext())
                .load(path)
                .into(imageView);
    }

    @Override
    public ImageView createImageView(Context context) {
        //圆角
        return new RoundAngleImageView(context);
    }

    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {

    }

    @Override
    public void clearMemoryCache() {

    }
}
