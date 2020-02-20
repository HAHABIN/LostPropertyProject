package com.example.habin.lostpropertyproject.Bean.entity;

import com.contrarywind.interfaces.IPickerViewData;


import java.util.ArrayList;

/**
 * Create by HABIN on 2020/2/1614:02
 * Email:739115041@qq.com
 * 一级
 * 省级实体类
 */
public class Province  implements IPickerViewData {

    private int provinceId;

    private int pid;

    private String provinceName;

    private int type;

    private ArrayList<City> cityList;

//    public Province(int provinceId, String provinceName, int type) {
//        this.provinceId = provinceId;
//        this.provinceName = provinceName;
//        this.type = type;
//    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public ArrayList<City> getCityList() {
        return cityList;
    }

    public void setCityList(ArrayList<City> cityList) {
        this.cityList = cityList;
    }

    @Override
    public String getPickerViewText() {
        return this.provinceName;
    }
}
