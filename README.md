lightning-mvc
============

致力于打造超轻量级的Java Web开发框架

#### 特点
* 超轻量级，不足1MB
* 支持IoC, 依赖注入，通过注解定义组件、依赖

#### 配置方法
* 将`lightning-mvc.jar`添加到classpath中
* MVC框架配置(`lightning-url-map.xml`):
```
    <?xml version="1.0" encoding="UTF-8"?>
    <lightning-config>
        <map url="/home" controller="homeController" request-type="GET"></map>
    </lightning-config>
```
`homeController`是容器管理的组件，必须实现`Controller`接口


* IoC容器配置文件示例(`lightning-config.xml`)：
```
    <?xml version="1.0"?>
    <lightning-config>
        <bean id="apple" class="cn.fh.lightning.bean.test.Apple" />

        <bean id="orange" class="cn.fh.lightning.bean.test.Orange" >
            <prop name="apple" ref-class="apple" />
        </bean>
    </lightning-config>
```


#### Class Diagram
* <img src="https://raw.githubusercontent.com/wanghongfei/lightning-mvc/master/doc/diagram.png" />
* <img src="https://raw.githubusercontent.com/wanghongfei/lightning-mvc/master/doc/mvc-diagram.png" />
