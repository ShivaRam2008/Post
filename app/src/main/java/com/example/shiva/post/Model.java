package com.example.shiva.post;

import java.util.ArrayList;

/**
 * Created by shiva on 7/8/2017.
 */

public class Model {

    public String actualNews;
    public String user;
    public String area;
    public String time;

    public Model()  {

    }

    public Model(String actualNews, String user, String area,String time) {
        this.actualNews = actualNews;
        this.user = user;
        this.area = area;
        this.time=time;
    }


    public String getActualNews() {
        return actualNews;
    }

    public String getUser() {
        return user;
    }

    public String getArea() {
        return area;
    }

    public String getTime() {
        return time;
    }
}
