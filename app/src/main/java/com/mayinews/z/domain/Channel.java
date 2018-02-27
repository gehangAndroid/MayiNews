package com.mayinews.z.domain;

/**
 * Created by h on 2018/1/4 0004.
 */

public class Channel {
    public static final int TYPE_MY = 1;
    public static final int TYPE_OTHER = 2;
    public static final int TYPE_MY_CHANNEL = 3;
    public static final int TYPE_OTHER_CHANNEL = 4;
    public String title;
    public String name;
    public String id;

    public Channel() {

    }

    public Channel(String title, String name, String id) {
        this.title = title;
        this.name = name;
        this.id=id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
