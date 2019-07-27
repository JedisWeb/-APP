package cn.edu.hbuas.sl.listViewArray;

public class AboutUsListViewArray {
    private String name;
    private int rightImgId;

    public AboutUsListViewArray() {
    }

    public AboutUsListViewArray(String name, int rightImgId) {
        this.name = name;
        this.rightImgId = rightImgId;
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
