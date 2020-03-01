package com.example.habin.lostpropertyproject.Presenter.fragment;

import com.example.habin.lostpropertyproject.Base.RxPresenter;
import com.example.habin.lostpropertyproject.Bean.entity.ArticleTypeEntity;
import com.example.habin.lostpropertyproject.Http.HttpClient;
import com.example.habin.lostpropertyproject.Http.HttpHelper;
import com.example.habin.lostpropertyproject.Presenter.fragment.contract.TypePageContract;

/**
 * created by habin
 * on 2019/12/30
 * Email 739115041@qq.com
 * 分类碎片P层
 */
public class TypePagePresenter extends RxPresenter<TypePageContract.View> implements TypePageContract.Presenter {
    @Override
    public void getType() {
        HttpClient.getInstance().startTask(HttpHelper.TaskType.queryType,this,null, ArticleTypeEntity.class);
    }
}
