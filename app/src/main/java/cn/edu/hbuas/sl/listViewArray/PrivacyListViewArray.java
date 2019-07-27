package cn.edu.hbuas.sl.listViewArray;

import com.suke.widget.SwitchButton;

public class PrivacyListViewArray {
    private String name;
    private SwitchButton switchButton;

    public PrivacyListViewArray() {
    }

    public PrivacyListViewArray(String name, SwitchButton switchButton) {
        this.name = name;
        this.switchButton = switchButton;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SwitchButton getSwitchButton() {
        return switchButton;
    }

    public void setSwitchButton(SwitchButton switchButton) {
        this.switchButton = switchButton;
    }
}
