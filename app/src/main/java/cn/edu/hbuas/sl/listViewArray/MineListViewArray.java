package cn.edu.hbuas.sl.listViewArray;

public class MineListViewArray {
    private int iconImg;
    private String name;
    private int rightImgId;

    public MineListViewArray() {
    }

    public MineListViewArray(int iconImg, String name, int rightImgId) {
        this.iconImg = iconImg;
        this.name = name;
        this.rightImgId = rightImgId;
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

    public int getRightImgId() {
        return rightImgId;
    }

    public void setRightImgId(int rightImgId) {
        this.rightImgId = rightImgId;
    }
}
