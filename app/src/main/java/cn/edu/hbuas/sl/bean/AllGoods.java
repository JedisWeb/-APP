package cn.edu.hbuas.sl.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class AllGoods {

    @Id(autoincrement = true)
    private Long number;
    @NotNull
    private String telphone;
    @NotNull
    private String goods;
    private String description;
    @NotNull
    private String seekTime;
    @NotNull
    private String seekArea;
    @NotNull
    private String address;
    @NotNull
    private String contacts;
    @NotNull
    private String contactsTel;
    private String pic1;
    private String pic2;
    private String pic3;
    private String pic4;

    @Generated(hash = 2063943020)
    public AllGoods(Long number, @NotNull String telphone, @NotNull String goods,
            String description, @NotNull String seekTime, @NotNull String seekArea,
            @NotNull String address, @NotNull String contacts,
            @NotNull String contactsTel, String pic1, String pic2, String pic3,
            String pic4) {
        this.number = number;
        this.telphone = telphone;
        this.goods = goods;
        this.description = description;
        this.seekTime = seekTime;
        this.seekArea = seekArea;
        this.address = address;
        this.contacts = contacts;
        this.contactsTel = contactsTel;
        this.pic1 = pic1;
        this.pic2 = pic2;
        this.pic3 = pic3;
        this.pic4 = pic4;
    }

    @Generated(hash = 1080634193)
    public AllGoods() {
    }

    @Override
    public String toString() {
        return "AllGoods{" +
                "number=" + number +
                ", telphone='" + telphone + '\'' +
                ", goods='" + goods + '\'' +
                ", description='" + description + '\'' +
                ", seekTime='" + seekTime + '\'' +
                ", seekArea='" + seekArea + '\'' +
                ", address='" + address + '\'' +
                ", contacts='" + contacts + '\'' +
                ", contactsTel='" + contactsTel + '\'' +
                '}';
    }

    public Long getNumber() {
        return this.number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getTelphone() {
        return this.telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getGoods() {
        return this.goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSeekTime() {
        return this.seekTime;
    }

    public void setSeekTime(String seekTime) {
        this.seekTime = seekTime;
    }

    public String getSeekArea() {
        return this.seekArea;
    }

    public void setSeekArea(String seekArea) {
        this.seekArea = seekArea;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContacts() {
        return this.contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getContactsTel() {
        return this.contactsTel;
    }

    public void setContactsTel(String contactsTel) {
        this.contactsTel = contactsTel;
    }

    public String getPic1() {
        return this.pic1;
    }

    public void setPic1(String pic1) {
        this.pic1 = pic1;
    }

    public String getPic2() {
        return this.pic2;
    }

    public void setPic2(String pic2) {
        this.pic2 = pic2;
    }

    public String getPic3() {
        return this.pic3;
    }

    public void setPic3(String pic3) {
        this.pic3 = pic3;
    }

    public String getPic4() {
        return this.pic4;
    }

    public void setPic4(String pic4) {
        this.pic4 = pic4;
    }

}
