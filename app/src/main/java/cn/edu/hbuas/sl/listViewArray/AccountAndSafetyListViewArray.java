package cn.edu.hbuas.sl.listViewArray;

public class AccountAndSafetyListViewArray {
    private String name;
    private int rightImgId;

    public AccountAndSafetyListViewArray() {
    }

    public AccountAndSafetyListViewArray(String name, int rightImgId) {
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
