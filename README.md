

失物招领Android端

我简要搭建的MVP模式下常见的目录结构，其中：

Disposable:订阅关系处理  
Base ：存放app的基类  
Common：存放异常常量，接口，公用的东西  
Bean：用来存放定义的数据  
Contract：连接V层和P层的一个契约包  
Parser：存放一些json等字符串的解析  
Presenter：P层，负责连接V层和M层，中心管理器。  
Retrofit:MVP通信  
View：这个包下面主要存放一下跟UI相关的部分  
 &emsp;activity：存放Activity类  
 &emsp;fragment：存放Fragment类  
 &emsp;adapter：适配器类  
Utils：存放工具类  
Widget：存放自定义的一些组件  
后台服务端
https://github.com/HAHABIN/LostFoundSSM/create/master
