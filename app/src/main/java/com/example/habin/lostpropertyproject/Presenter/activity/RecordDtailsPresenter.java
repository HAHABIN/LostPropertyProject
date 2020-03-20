package com.example.habin.lostpropertyproject.Presenter.activity;

import com.example.habin.lostpropertyproject.Base.RxPresenter;
import com.example.habin.lostpropertyproject.Bean.HttpItem;
import com.example.habin.lostpropertyproject.Bean.entity.ArticleInfoEntity;
import com.example.habin.lostpropertyproject.Bean.entity.PersonInfoEntity;
import com.example.habin.lostpropertyproject.Http.HttpClient;
import com.example.habin.lostpropertyproject.Http.HttpHelper;
import com.example.habin.lostpropertyproject.MyApplication;
import com.example.habin.lostpropertyproject.Presenter.activity.contract.RecordDtailsContract;
import com.example.habin.lostpropertyproject.Ui.activity.RecordDetailsActivity;
import com.example.habin.lostpropertyproject.Util.UiUtils;

import java.util.HashMap;

/**
 * Create by HABIN on 2020/3/622:08
 * Email:739115041@qq.com
 */
public class RecordDtailsPresenter extends RxPresenter<RecordDtailsContract.View> implements RecordDtailsContract.Presenter {
    //获取当前用户信息
    private PersonInfoEntity.ResultBean userInfo = MyApplication.getUserInfo(UiUtils.getContext());
    @Override
    public void updateArticle(int id, int recordStatus) {
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("id",id);
        hashMap.put("recordStatus",recordStatus);
        HttpClient.getInstance().startTask(HttpHelper.TaskType.updateArticleStatus,this,hashMap, HttpItem.class);
    }

    @Override
    public void reArticle(int articleId) {
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("id",articleId);
        HttpClient.getInstance().startTask(HttpHelper.TaskType.QueryArticleInfo,this,hashMap);
    }

    @Override
    public void addComment(int articleId, String content) {

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("userId",userInfo.getUserId());
        hashMap.put("articleId",articleId);
        hashMap.put("content",content);
        if (userInfo.getProfileImg()!=null){
            hashMap.put("imgStr",userInfo.getProfileImg());
        }
        if (userInfo.getNickname()!=null){
            hashMap.put("nickName",userInfo.getNickname());
        }


        HttpClient.getInstance().startTask(HttpHelper.TaskType.AddComment,this,hashMap, HttpItem.class);
    }

    @Override
    public void AddLike(int articleId) {
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("userId",userInfo.getUserId());
        hashMap.put("articleId",articleId);
        HttpClient.getInstance().startTask(HttpHelper.TaskType.AddGreat,this,hashMap, HttpItem.class);
    }

    @Override
    public void CancelLike(int articleId) {
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("userId",userInfo.getUserId());
        hashMap.put("articleId",articleId);
        HttpClient.getInstance().startTask(HttpHelper.TaskType.DeleteGreat,this,hashMap, HttpItem.class);
    }
}
