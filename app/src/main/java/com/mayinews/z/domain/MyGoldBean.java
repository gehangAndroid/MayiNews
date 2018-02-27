package com.mayinews.z.domain;

/**
 * Created by h on 2018/2/26 0026.
 */

public class MyGoldBean {

    private String title;
    private String gold;
    private String desc;
    private String time;

    public MyGoldBean(String title, String gold, String desc, String time) {
        this.title = title;
        this.gold = gold;
        this.desc = desc;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGold() {
        return gold;
    }

    public void setGold(String gold) {
        this.gold = gold;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
