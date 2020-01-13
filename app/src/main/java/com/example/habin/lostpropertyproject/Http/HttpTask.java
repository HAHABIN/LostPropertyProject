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

    private HttpHelper.TaskType type;
    private HashMap<String, Object> params;

    private TaskListener mListener;
    private ApiServer mApiServer;
    private Context mContext;
    private Class mItem;

    private Observable<Response<ResponseBody>> callBack;

    private HttpItem result;
    private JSONObject reustObject;

    public HttpHelper.TaskType getType() {
        return type;
    }

    public HttpTask(Context context, ApiServer apiServer, TaskListener listener, Class item_c) {
        this.mContext = context;
        this.mApiServer = apiServer;
        this.mListener = listener;
        this.mItem = item_c;
    }

    public HttpTask(Context context, ApiServer apiServer, TaskListener listener) {
        this.mContext = context;
        this.mApiServer = apiServer;
        this.mListener = listener;
    }

    public HttpTask load(HttpHelper.TaskType type, HashMap<String, Object> params) {
        this.type = type;
        if (!UiUtils.isNetworkConnected()) {
            errorHandle(ApiError.ErrorType.ApiError_NetworkDisconnected);
            return this;
        }
        this.params = params;
        if (params == null) params = new HashMap<>();
        try {
            callBack = mApiServer.postJSON(HttpHelper.getMethod(type), params);
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


    private Observer<Response<ResponseBody>> observer = new Observer<Response<ResponseBody>>() {
        @Override
        public void onSubscribe(Disposable d) {
//            LogUtil.d(TAG, "是否取消");
        }

        @Override
        public void onNext(Response<ResponseBody> res) {
            try {
                ResponseBody object = res.body();
                String response = null == object ? "" : object.string();

                HttpItem httpItem =  JsonUtil.GsonToBean(response, mItem != null ? mItem : HttpItem.class);
                Log.d(TAG, "ResponseBody："+response);
                if (httpItem != null) {
                    if (httpItem.getCode() == 1) {
                        if (mItem == null) {
                            reustObject = new JSONObject(response);
                        } else {
                            result = httpItem;
                        }
                    } else {
                        errorHandle(ApiError.ErrorType.valueOf(httpItem.getCode(), httpItem.getMessage()));
                    }
                } else {
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
