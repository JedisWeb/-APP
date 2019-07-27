package cn.edu.hbuas.sl.listViewArray;

public class ShareListViewArray {
    private int iconImg;
    private String name;

    public ShareListViewArray() {
    }

    public ShareListViewArray(int iconImg, String name) {
        this.iconImg = iconImg;
        this.name = name;
    }

    public int getIconImg() {
        return iconImg;
    }

    public void setIconImg(int iconImg) {
        this.iconImg = iconImg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
