package com.example.habin.lostpropertyproject.Http;


import com.example.habin.lostpropertyproject.Bean.HttpItem;

import org.json.JSONObject;

public interface TaskListener {

    void taskStarted(HttpHelper.TaskType type);
    void taskError(HttpHelper.TaskType type, ApiError error);

    // 统一放到业务界面解析数据结构
    void taskFinished(HttpHelper.TaskType type, JSONObject object);

    void taskFinished(HttpHelper.TaskType type, HttpItem item);
}
