package com.example.habin.lostpropertyproject.Http;


import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Create by HABIN on 2019/11/5
 * Time：23:50
 * Email:739115041@qq.com
 *
 * 请求体的创建。
 */
public interface ApiServer {

//    @GET("{fenzhi}{bianhao}/orderinfo")
//    Observable<List<BeanGSchaxun>> getGSxin(@Path("fenzhi") String fenzhi,
//                                            @Path("bianhao") String bianhao,
//                                            @Query("batchNo") String batchNo);
//    @POST("lostadmin/logincheck")
//    Observable<BaseResponse> login(@Query("username") String username,
//                                   @Query("password") String password);
//    @POST("lostadmin/signupcheck")
//    Observable<BaseResponse> signup(@Query("username") String username,
//                                   @Query("password") String password);
//
//    //上传图片
//    @Multipart
//    @POST("userAction_uploadImage.action")
//    Observable<BaseResponse> uploadPhoto(@Part("user.file") MultipartBody file,
//                                            @Part MultipartBody.Part... parts);


    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")//添加请求头注解 解决中文乱码
    @POST("{path}")
    Observable<Response<ResponseBody>> postJSON( @Path(value = "path", encoded = true) String path, @FieldMap Map<String, Object> param);

}
