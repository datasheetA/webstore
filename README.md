# webstore
本项目以MVC模式为基础，将系统的功能划分为多个模块，以便灵活的进行分布式部署。系统实现的功能包括：首页展示、商品搜索、后台管理、订单和购物车、单点登录系统等。
#### 项目的包结构：
![](https://github.com/RisenZhong/RisenZhong.github.io/blob/master/uploads/images/webstore/struct.png)
- webstore-parent：所有maven依赖的版本管理。
- webstore-common：包含一些通用的类，RedisDao、通用的pojo和工具类等。
- webstore-portal：门户系统。包含首页、购物车、订单等页面的展示。
- webstore-rest：rest服务。包含获取商品列表、获取商品详细信息、Redis缓存同步等接口供其他系统调用。
- webstore-search：搜索服务。提供与搜索相关的接口。
- webstore-order：订单服务。提供与订单相关的接口：创建、删除、修改订单等。
- webstore-sso：单点登录系统，提供与用户相关的接口：用户注册、登录、退出等。
#### 系统架构：
&emsp;&emsp;&emsp;![](https://github.com/RisenZhong/RisenZhong.github.io/blob/master/uploads/images/webstore/frame.png)

