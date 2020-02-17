package com.example.habin.lostpropertyproject.Bean.Local.City;

import com.contrarywind.interfaces.IPickerViewData;


import java.util.ArrayList;
import java.util.List;

/**
 * Create by HABIN on 2020/2/1614:02
 * Email:739115041@qq.com
 * 二级
 * 市级实体类
 * extends LitePalSupport //移除litepal数据库
 */
public class City implements IPickerViewData {


    private int cityId;

    private int provinceId;

    private String cityName;

    private int type;

    private ArrayList<County> countyList;

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ArrayList<County> getCountyList() {
        return countyList;
    }

    public void setCountyList(ArrayList<County> countyList) {
        this.countyList = countyList;
    }

    @Override
    public String getPickerViewText() {
        return this.cityName;
    }
}
