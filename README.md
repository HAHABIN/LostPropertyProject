
3/24/2020 1:21:32 AM 
#失物招领Android端

###App下载链接  
[http://habin-picload.oss-cn-beijing.aliyuncs.com/Android/app-release.apk](http://habin-picload.oss-cn-beijing.aliyuncs.com/Android/app-release.apk)

##我简要搭建的MVP模式下常见的目录结构

+ **Base** ：存放app的基类  
+ **Bean**：用来存放定义的数据  
 &emsp;entity：存放实体类  
 &emsp;HttpItem 对象通用类
+ **Http**：访问数据封装（model层）  
 &emsp;Constants：存放异常常量，接口，公用的东西  
 &emsp;HttpClient： HttpClient管理器，保证Retrofit在类中只有一个实例，避免请求体的多次创建。  
 &emsp;ApiServer:请求体的创建。  
 &emsp;HttpTask: Rxjava异步访问数据处理管理器  
 &emsp;HttpHelper: 数据访问接口枚举  
 &emsp;ApiError:返回错误解析类  
 &emsp;TaskLitener:访问接口监听  
+ **Presenter**：P层，负责连接V层和M层，中心管理器。（P层）  
 &emsp;Contract：连接V层和P层的一个契约包  
+ **Ui**:存放一下跟UI相关的部分  ，控制层（V层）  
 &emsp;activity：存放Activity类  
 &emsp;fragment：存放Fragment类  
 &emsp;adapter：适配器类  
+ **Utils**：存放工具类  
+ **Widget**：存放自定义的一些组件 
+ **MyApplication**


##后台服务端
https://github.com/HAHABIN/LostFoundSSM/create/master
