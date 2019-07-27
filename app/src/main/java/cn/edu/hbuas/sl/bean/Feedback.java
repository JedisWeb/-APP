package cn.edu.hbuas.sl.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Feedback {
    @Id(autoincrement = true)
    Long _id;
    String content;
    String tel;


    @Generated(hash = 1796896502)
    public Feedback(Long _id, String content, String tel) {
        this._id = _id;
        this.content = content;
        this.tel = tel;
    }


    @Generated(hash = 802671868)
    public Feedback() {
    }


    @Override
    public String toString() {
        return "Feedback{" +
                "content='" + content + '\'' +
                ", tel='" + tel + '\'' +
                '}';
    }


    public Long get_id() {
        return this._id;
    }


    public void set_id(Long _id) {
        this._id = _id;
    }


    public String getContent() {
        return this.content;
    }


    public void setContent(String content) {
        this.content = content;
    }


    public String getTel() {
        return this.tel;
    }


    public void setTel(String tel) {
        this.tel = tel;
    }
}
