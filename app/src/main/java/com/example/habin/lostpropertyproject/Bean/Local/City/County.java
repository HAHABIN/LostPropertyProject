package com.example.habin.lostpropertyproject.Bean.Local.City;

import com.contrarywind.interfaces.IPickerViewData;


/**
 * Create by HABIN on 2020/2/1614:02
 * Email:739115041@qq.com
 * 三级
 * 区县级实体类
 */
public class County implements IPickerViewData {

    private int id;

    private int cityId;

    private String countyName;

    private int type;

    public County(String countyName) {
        this.countyName = countyName;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String getPickerViewText() {
        return this.countyName;
    }
}
