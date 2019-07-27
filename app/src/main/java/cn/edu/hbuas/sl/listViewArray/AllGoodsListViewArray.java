package cn.edu.hbuas.sl.listViewArray;

import android.graphics.Bitmap;

public class AllGoodsListViewArray {
    private Bitmap pic;
    private String goods;
    private String place;
    private String time;

    public AllGoodsListViewArray() {
    }

    public AllGoodsListViewArray(Bitmap pic, String goods, String place, String time) {
        this.pic = pic;
        this.goods = goods;
        this.place = place;
        this.time = time;
    }

    public Bitmap getPic() {
        return pic;
    }

    public void setPic(Bitmap pic) {
        this.pic = pic;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
