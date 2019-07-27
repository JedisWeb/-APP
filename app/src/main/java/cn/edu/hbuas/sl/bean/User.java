package cn.edu.hbuas.sl.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class User {

    @Id
    private Long telphone;
    @NotNull
    private String password;
    private String nickname;
    private String gender;
    private String birthday;
    private String area;
    private String headPic;


    @Generated(hash = 1286810152)
    public User(Long telphone, @NotNull String password, String nickname,
            String gender, String birthday, String area, String headPic) {
        this.telphone = telphone;
        this.password = password;
        this.nickname = nickname;
        this.gender = gender;
        this.birthday = birthday;
        this.area = area;
        this.headPic = headPic;
    }


    @Generated(hash = 586692638)
    public User() {
    }


    @Override
    public String toString() {
        return "User{" +
                "telphone='" + telphone + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", gender='" + gender + '\'' +
                ", birthday='" + birthday + '\'' +
                ", area='" + area + '\'' +
                ", headPic='" + headPic + '\'' +
                '}';
    }


    public Long getTelphone() {
        return this.telphone;
    }


    public void setTelphone(Long telphone) {
        this.telphone = telphone;
    }


    public String getPassword() {
        return this.password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public String getNickname() {
        return this.nickname;
    }


    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


    public String getGender() {
        return this.gender;
    }


    public void setGender(String gender) {
        this.gender = gender;
    }


    public String getBirthday() {
        return this.birthday;
    }


    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }


    public String getArea() {
        return this.area;
    }


    public void setArea(String area) {
        this.area = area;
    }


    public String getHeadPic() {
        return this.headPic;
    }


    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

}


