# Spring MVC 异常处理例子

本文使用Spring Boot 1.5.2.RELEASE([doc][spring-boot-doc])

## Spring Boot Error Handling

### 默认行为

根据Spring Boot官方文档的说法：

> For machine clients it will produce a JSON response with details of the error, the HTTP status and the exception message. For browser clients there is a ‘whitelabel’ error view that renders the same data in HTML format

也就是说，当发生异常时：

* 如果请求是从浏览器发送出来的，那么返回一个`Whitelabel Error Page`
* 如果请求是从machine客户端发送出来的，那么会返回相同信息的`json`

你可以在浏览器中依次访问以下地址：

1. `http://localhost:8080/return-model-and-view`，
1. `http://localhost:8080/return-view-name`，
1. `http://localhost:8080/return-view`，
1. `http://localhost:8080/return-plain-text`，
1. `http://localhost:8080/return-json-1`，
1. `http://localhost:8080/return-json-2`，

会发现[FooController][def-foo]和[FooRestController][def-foo-rest]返回的结果都是一个`Whitelabel Error Page`也就是html。

但是如果你使用`curl`访问上述地址，那么返回的都是如下的`json`：

```json
{
  "timestamp": 1498886969426,
  "status": 500,
  "error": "Internal Server Error",
  "exception": "me.chanjar.exception.SomeException",
  "message": "...",
  "trace": "...",
  "path": "..."
}
```

本章节的代码使用[DefaultExample][boot-DefaultExample]运行。

### 自定义Error页面

前面看到了，Spring Boot针对浏览器发起的请求的error页面是`Whitelabel Error Page`，下面讲解如何自定义error页面。

注意1：在下面的例子里我们输出了stacktrace，其实Spring Boot的默认是不会将stacktrace放到Model中的，是我们在`application.properties`添加了`server.error.include-stacktrace=always`。

注意2：自定义Error页面不会影响machine客户端的输出结果

#### 方法1

根据Spring Boot官方文档，如果想要定制这个页面只需要：

> to customize it just add a `View` that resolves to ‘error’

这句话讲的不是很明白，其实只要看`ErrorMvcAutoConfiguration.WhitelabelErrorViewConfiguration`的代码就知道，只需注册一个名字叫做`error`的`View`类型的`Bean`就行了。

本例的[CustomErrorViewConfiguration][boot-CustomErrorViewConfiguration]注册将`error`页面改到了[templates/custom-error-view/error.html][boot-custom-error-view-error-html]上。


本章节的代码在[me.chanjar.boot.view][pkg-me.chanjar.boot.view]，使用[CustomErrorViewExample][boot-CustomErrorViewExample]运行。

#### 方法2

方法2比方法1简单很多，在Spring官方文档中没有说明。其实只需要提供`error` `View`所对应的页面文件即可。

比如在本例里，因为使用的是Thymeleaf模板引擎，所以在classpath `/templates`放一个自定义的`error.html`就能够自定义error页面了。

本章节就不提供代码了，有兴趣的你可以自己尝试。


### TODO

1. ErrorViewResolver的例子
1. ErrorController
1. ErrorAttributes


## Spring MVC Error Handling

1. TODO HandlerExceptionResolver的例子
1. TODO @ExceptionHandler的例子
1. TODO @ControllerAdivce的例子
1. TODO 自定义Status Code的例子
1. TODO 返回Html时的例子
1. TODO 返回Json时的例子


  [spring-boot-doc]: http://docs.spring.io/spring-boot/docs/1.5.4.RELEASE/reference/htmlsingle/#boot-features-error-handling
  [def-foo]: src/main/java/me/chanjar/controllers/FooController.java
  [def-foo-rest]: src/main/java/me/chanjar/controllers/FooRestController.java
  [pkg-me.chanjar.boot.def]: src/main/java/me/chanjar/boot/def
  [boot-CustomErrorViewConfiguration]: src/main/java/me/chanjar/boot/view/CustomErrorViewConfiguration.java
  [boot-DefaultExample]: src/main/java/me/chanjar/boot/def/DefaultExample.java
  [pkg-me.chanjar.boot.view]: src/main/java/me/chanjar/boot/view
  [boot-custom-error-view-error-html]: src/main/resources/templates/custom-error-view/error.html
  [boot-CustomErrorViewExample]: src/main/java/me/chanjar/boot/view/CustomErrorViewExample.java
