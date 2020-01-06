package com.example.habin.lostpropertyproject.Http;

public class ApiError {

    private ErrorType type;
    private String message;

    public String getMessage() {
        return message;
    }

    public ErrorType getType() {
        return type;
    }

    public ApiError(ErrorType type) {
        this.type = type;
        this.message = type.message;
    }

    public ApiError(int code, String message) {
        this.message = message!=null?message:"";
        type = ErrorType.valueOf(code);
        if (type != ErrorType.ApiError_Unknown) {
            this.message = type.message;
        }
    }

    public enum ErrorType {
        //2xx （成功）
        ApiError_204("服务器成功处理了请求，没有返回任何内容！",204),
        ApiError_205("服务器成功处理了请求，没有返回任何内容！",205),

        //4xx（请求错误）
        ApiError_400("服务器不理解请求的语法！",400),
        ApiError_401("请求要求身份验证！",401), //（未授权） 请求要求身份验证。 对于需要登录的网页，服务器可能返回此响应
        ApiError_403("服务器拒绝请求！",403), //（禁止） 服务器拒绝请求
        ApiError_404("服务器找不到请求的地址！",404), //（未找到） 服务器找不到请求的网页
        ApiError_405("禁用请求中指定的方法！",405), //（方法禁用） 禁用请求中指定的方法
        ApiError_408("服务器等候请求发生超时！",408), //（请求超时） 服务器等候请求时发生超时

        //5xx（服务器错误）
        ApiError_500("服务器遇到错误，无法完成请求！",500), //（解决办法传参数不正确）
        ApiError_502("错误网关！",502), //（错误网关） 服务器作为网关或代理，从上游服务器收到无效响应
        ApiError_503("服务器目前无法使用！",503), // 服务器目前无法使用（由于超载或停机维护）。 通常，这只是暂时状态
        ApiError_505("HTTP 版本不受支持！",505), // 服务器不支持请求中所用的 HTTP 协议版本


        ApiError_UnknownHostException("网络似乎出现了点问题，请稍后再试...",3),
        ApiError_TimeOut("网络似乎出现了点问题，请稍后再试...",1),
        ApiError_Unknown("网络似乎出现了点问题，请稍后再试...",0),

        //自定义
        ApiError_NetworkDisconnected("网络似乎出现了点问题，请稍后再试...",-1),
        ApiError_Retorfit("Retorfit配置出错，请检查!",-2),
        ApiError_Entity("页面好像失联了，稍后再试试吧..",-3),
        ApiError_Local("本地代码错误，请检查!",-4),
        ApiError_Data("页面好像失联了，稍后再试试吧..",-5);

        //可与服务器约定

        private int value;
        private String message;

        ErrorType(String message, int value) {
            this.value = value;
            this.message = message;
        }

        public static ErrorType valueOf(int value) {
            ErrorType type = ApiError_Unknown;
            for (ErrorType sub : values()) {
                if (sub.value == value) {
                    type = sub;
                }
            }
            return type;
        }

        public static ErrorType valueOf(int value, String msg) {
            ErrorType type = ApiError_Unknown;
            type.message = msg;
            for (ErrorType sub : values()) {
                if (sub.value == value) {
                    type = sub;
                    type.message = msg;
                }
            }
            return type;
        }
    }
}
