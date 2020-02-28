package com.example.habin.lostpropertyproject.Http;

import android.content.Context;
import android.util.Log;

import com.example.habin.lostpropertyproject.Bean.HttpItem;
import com.example.habin.lostpropertyproject.Util.JsonUtil;
import com.example.habin.lostpropertyproject.Util.UiUtils;

import org.json.JSONObject;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;

public class HttpTask {

    private static final String TAG = "HttpTask";
    //请求接口类型
    private HttpHelper.TaskType type;
    //请求参数
    private HashMap<String, Object> params;
    //请求接口监听返回
    private TaskListener mListener;
    //
    private ApiServer mApiServer;
    //上下问
    private Context mContext;
    //Class对象
    private Class mItem;
    //被观察者 当监听到变化 提醒观察者Observer
    private Observable<Response<ResponseBody>> callBack;
    //返回结果 对象数据
    private HttpItem result;
    //返回结果 JSON数据
    private JSONObject reustObject;

    //获取当前请求接口类型
    public HttpHelper.TaskType getType() {
        return type;
    }

    //初始化访问数据 有对象情况
    public HttpTask(Context context, ApiServer apiServer, TaskListener listener, Class item_c) {
        this.mContext = context;
        this.mApiServer = apiServer;
        this.mListener = listener;
        this.mItem = item_c;
    }
    //初始化访问数据 无对象情况
    public HttpTask(Context context, ApiServer apiServer, TaskListener listener) {
        this.mContext = context;
        this.mApiServer = apiServer;
        this.mListener = listener;
    }
    //加载请求
    public HttpTask load(HttpHelper.TaskType type, HashMap<String, Object> params) {
        //获取当前请求网络接口类型 并赋值成员变量
        this.type = type;
        //判断是否有网络
        if (!UiUtils.isNetworkConnected()) {
            errorHandle(ApiError.ErrorType.ApiError_NetworkDisconnected);
            return this;
        }

        //获取当前请求参数 并赋值成员变量
        this.params = params;
        //判断请求参数是否为空
        if (params == null) params = new HashMap<>();

        try {
            //通过请求网络接口类型返回访问接口
            String path = HttpHelper.getMethod(type);
            //设置请求参数和接口
            callBack = mApiServer.postJSON(path, params);
            //
            callBack.subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer);
        } catch (Exception e) {
            e.printStackTrace();
            errorHandle(ApiError.ErrorType.ApiError_Retorfit);
        }
        return this;
    }

    //观察者
    private Observer<Response<ResponseBody>> observer = new Observer<Response<ResponseBody>>() {
        @Override
        public void onSubscribe(Disposable d) {
//            LogUtil.d(TAG, "是否取消");
        }

        @Override
        public void onNext(Response<ResponseBody> res) {
            try {
                //网络请求体数据
                ResponseBody object = res.body();
                //获取数据object为空 则设置空 否则将数据返回为字符串数据
                String response = null == object ? "" : object.string();
                //将字符串数据返回成对象 如果mItem不为空情况  否则转为HttpItem对象
                HttpItem httpItem =  JsonUtil.GsonToBean(response, mItem != null ? mItem : HttpItem.class);
                //日志
                Log.d(TAG, "ResponseBody："+response);
                //如果对象不为空
                if (httpItem != null) {
                    //判断请求结果
                    if (httpItem.getCode() == 1) {
                        //如果mItem为空
                        if (mItem == null) {
                            //获取JSONObject数据
                            reustObject = new JSONObject(response);
                        } else {
                            //获取对象数据
                            result = httpItem;
                        }
                    } else {
                        //返回结果失败 提示
                        errorHandle(ApiError.ErrorType.valueOf(httpItem.getCode(), httpItem.getMessage()));
                    }
                } else {
                    //获取数据失败
                    errorHandle(ApiError.ErrorType.ApiError_Data);
                }
            } catch (Exception e) {
                e.printStackTrace();
                errorHandle(ApiError.ErrorType.ApiError_Data);
            }
        }

        @Override
        public void onError(Throwable e) {
            ApiError.ErrorType type = ApiError.ErrorType.ApiError_Unknown;

            if (e instanceof HttpException)
                type = ApiError.ErrorType.valueOf(((HttpException) e).code());

            if (e instanceof SocketTimeoutException)
                type = ApiError.ErrorType.ApiError_TimeOut;

            if (e instanceof UnknownHostException)
                type = ApiError.ErrorType.ApiError_UnknownHostException;

            errorHandle(type);
        }

        @Override
        public void onComplete() {
            if (mItem != null) {
                if (mListener != null && result != null) {
                    mListener.taskFinished(type, result);
                }
            } else {
                if (mListener != null && reustObject != null) {
                    mListener.taskFinished(type, reustObject);
                }
            }
        }
    };

    public void cancel() {

    }

    private void errorHandle(ApiError.ErrorType errorType) {
        if (mListener != null) mListener.taskError(type, new ApiError(errorType));
    }

}
