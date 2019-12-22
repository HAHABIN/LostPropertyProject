package com.example.habin.lostpropertyproject.Service;

import com.example.habin.lostpropertyproject.Bean.BaseResponse;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Create by HABIN on 2019/11/5
 * Time：23:50
 * Email:739115041@qq.com
 *
 * 请求体的创建。
 */
public interface Apiservice {

//    @GET("{fenzhi}{bianhao}/orderinfo")
//    Observable<List<BeanGSchaxun>> getGSxin(@Path("fenzhi") String fenzhi,
//                                            @Path("bianhao") String bianhao,
//                                            @Query("batchNo") String batchNo);
    @POST("lostadmin/logincheck")
    Observable<BaseResponse> login(@Query("username") String username,
                                   @Query("password") String password);


}
