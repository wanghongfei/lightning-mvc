lightning-mvc
============

致力于打造超轻量级的Java Web开发框架

#### 特点
* 超轻量级，不足1MB
* 支持IoC, 依赖注入，通过注解定义组件、依赖

#### 使用方法
1. 将`lightning-mvc.jar`添加到classpath中
2. 配置IoC容器. 在`/WEB-INF/lightning-config.xml`中声明组件:
```
    <?xml version="1.0"?>
    <lightning-config>
        <bean id="apple" class="cn.fh.lightning.bean.test.Apple" />
        <bean id="orange" class="cn.fh.lightning.bean.test.Orange" >
            <prop name="apple" ref-class="apple" />
        </bean>
        <bean id="homeController" class="cn.fh.controller.HomeController" />
    </lightning-config>
```
经过上述配置后，`apple`和`orange`就变成了IoC容器管理的组件，无需用户管理其生命周期。

3. MVC框架配置. 在`/WEB-INF/lightning-url-map.xml`中声明自定义的控制器及其对应的URL:
```
    <?xml version="1.0" encoding="UTF-8"?>
    <lightning-config>
        <map url="/home" controller="homeController" request-type="GET"></map>
    </lightning-config>
```
`homeController`是用户实现的控制器，必须实现`Controller`接口。
经过上述配置后，当用户发起`/home`GET请求后，框架会自动调用`homeController`的`handle()`方法来处理请求
