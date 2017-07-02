# Spring MVC 异常处理例子

本文使用Spring Boot 1.5.2.RELEASE([doc][spring-boot-doc])，Spring framework 4.3.7.RELEASE。

## Spring Boot Error Handling

### 默认行为

根据Spring Boot官方文档的说法：

> For machine clients it will produce a JSON response with details of the error, the HTTP status and the exception message. For browser clients there is a ‘whitelabel’ error view that renders the same data in HTML format

也就是说，当发生异常时：

* 如果请求是从浏览器发送出来的，那么返回一个`Whitelabel Error Page`
* 如果请求是从machine客户端发送出来的，那么会返回相同信息的`json`

你可以在浏览器中依次访问以下地址：

1. `http://localhost:8080/return-model-and-view`
1. `http://localhost:8080/return-view-name`
1. `http://localhost:8080/return-view`
1. `http://localhost:8080/return-text-plain`
1. `http://localhost:8080/return-json-1`
1. `http://localhost:8080/return-json-2`

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

但是有一个URL除外：`http://localhost:8080/return-text-plain`，它不会返回任何结果，原因稍后会有说明。

本章节代码在[me.chanjar.boot.def][pkg-me.chanjar.boot.def]，使用[DefaultExample][boot-DefaultExample]运行。

注意：我们必须在`application.properties`添加`server.error.include-stacktrace=always`才能够得到stacktrace。

#### 为何curl text/plain资源无法获得error

如果你在[logback-spring.xml][logback-spring.xml]里一样配置了这么一段：

```xml
<logger name="org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod" level="TRACE"/>
```

那么你就能在日志文件里发现这么一个异常：

```
org.springframework.web.HttpMediaTypeNotAcceptableException: Could not find acceptable representation
...
```

要理解这个异常是怎么来的，那我们来简单分析以下Spring MVC的处理过程：

1. `curl http://localhost:8080/return-text-plain`，会隐含一个请求头`Accept: */*`，这在后面匹配[@RequestMapping][RequestMapping]时有用。
1. [RequestMappingHandlerMapping][RequestMappingHandlerMapping]根据url匹配到了(见[AbstractHandlerMethodMapping.lookupHandlerMethod#L341][AbstractHandlerMethodMapping_L341])``FooController.returnTextPlan``(``produces=text/plain``)。
1. 方法抛出了异常，forward到`/error`。
1. [RequestMappingHandlerMapping][RequestMappingHandlerMapping]根据url匹配到了(见[AbstractHandlerMethodMapping.lookupHandlerMethod#L341][AbstractHandlerMethodMapping_L341])[BasicErrorController][BasicErrorController]的两个方法[errorHtml][BasicErrorController_errorHtml](``produces=text/html``)和[error][BasicErrorController_error](``produces=null``，相当于``produces=*/*``)。
1. 因为请求头`Accept: */*`，所以会匹配[error][BasicErrorController_error]方法上(见[AbstractHandlerMethodMapping#L352][AbstractHandlerMethodMapping_L352]，[RequestMappingInfo.compareTo][RequestMappingInfo_L266]，[ProducesRequestCondition.compareTo][ProducesRequestCondition_L235])。
1. `error`方法返回的是``ResponseEntity<Map<String, Object>>``，会被[HttpEntityMethodProcessor.handleReturnValue][HttpEntityMethodProcessor_L159]处理。
1. [HttpEntityMethodProcessor][HttpEntityMethodProcessor]进入[AbstractMessageConverterMethodProcessor.writeWithMessageConverters][AbstractMessageConverterMethodProcessor_L163]，发现请求头``Accept: */*``，``produces=text/plain``(还记得``FooController.returnTextPlan``吗？)，那它会去找能够将`Map`转换成`String`的[HttpMessageConverter][HttpMessageConverter]，结果是找不到。
1. [AbstractMessageConverterMethodProcessor][AbstractMessageConverterMethodProcessor]抛出[HttpMediaTypeNotAcceptableException][AbstractMessageConverterMethodProcessor_L259]。


那么为什么浏览器访问`http://localhost:8080/return-text-plain`就可以呢？你只需打开浏览器的开发者模式看看请求头就会发现`Accept:text/html,...`，所以在第4步会匹配到[BasicErrorController.errorHtml][BasicErrorController_errorHtml]方法，那结果自然是没有问题了。

那么这个问题怎么解决呢？我会在*自定义ErrorController*里说明。

### 自定义Error页面

前面看到了，Spring Boot针对浏览器发起的请求的error页面是`Whitelabel Error Page`，下面讲解如何自定义error页面。

注意2：自定义Error页面不会影响machine客户端的输出结果

#### 方法1

根据Spring Boot官方文档，如果想要定制这个页面只需要：

> to customize it just add a `View` that resolves to ‘error’

这句话讲的不是很明白，其实只要看`ErrorMvcAutoConfiguration.WhitelabelErrorViewConfiguration`的代码就知道，只需注册一个名字叫做`error`的`View`类型的`Bean`就行了。

本例的[CustomErrorViewConfiguration][boot-CustomErrorViewConfiguration]注册将`error`页面改到了[templates/custom-error-page/error.html][boot-custom-error-page-error-html]上。

本章节代码在[me.chanjar.boot.customerrorpage][pkg-me.chanjar.boot.customerrorpage]，使用[CustomErrorViewExample][boot-CustomErrorViewExample]运行。

#### 方法2

方法2比方法1简单很多，在Spring官方文档中没有说明。其实只需要提供`error` `View`所对应的页面文件即可。

比如在本例里，因为使用的是Thymeleaf模板引擎，所以在classpath `/templates`放一个自定义的`error.html`就能够自定义error页面了。

本章节就不提供代码了，有兴趣的你可以自己尝试。

### 自定义Error属性

前面看到了不论error页面还是error json，能够得到的属性就只有：timestamp、status、error、exception、message、trace、path。

如果你想自定义这些属性，可以如Spring Boot官方文档所说的：

> simply add a bean of type `ErrorAttributes` to use the existing mechanism but replace the contents

在`ErrorMvcAutoConfiguration.errorAttributes`提供了`DefaultErrorAttributes`，我们也可以参照这个提供一个自己的[CustomErrorAttributes][boot-CustomErrorAttributes]覆盖到它。

如果使用curl访问相关地址可以看到，返回的json里的出了修改过的属性，还有添加的属性：

```json
{
  "exception": "customized exception",
  "add-attribute": "add-attribute",
  "path": "customized path",
  "trace": "customized trace",
  "error": "customized error",
  "message": "customized message",
  "timestamp": 1498892609326,
  "status": 100
}
```

本章节代码在[me.chanjar.boot.customerrorattributes][pkg-me.chanjar.boot.customerrorattributes]，使用[CustomErrorAttributesExample][boot-CustomErrorAttributesExample]运行。

### 自定义ErrorController

在前面提到了`curl http://localhost:8080/return-text-plain`得不到error信息，解决这个问题有两个关键点：

1. 请求的时候指定`Accept`头，避免匹配到[BasicErrorController.error][BasicErrorController_error]方法。比如：`curl -H 'Accept: text/plain' http://localhost:8080/return-text-plain`
1. 提供自定义的``ErrorController``。

下面将如何提供自定义的``ErrorController``。按照Spring Boot官方文档的说法：

> To do that just extend ``BasicErrorController`` and add a public method with a ``@RequestMapping`` that has a ``produces`` attribute, and create a bean of your new type.

所以我们提供了一个[CustomErrorController][boot-CustomErrorController]，并且通过[CustomErrorControllerConfiguration][boot-CustomErrorControllerConfiguration]将其注册为Bean。

本章节代码在[me.chanjar.boot.customerrorcontroller][pkg-me.chanjar.boot.customerrorcontroller]，使用[CustomErrorControllerExample][boot-CustomErrorControllerExample]运行。

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
  [RequestMapping]: https://github.com/spring-projects/spring-framework/blob/v4.3.7.RELEASE/spring-web/src/main/java/org/springframework/web/bind/annotation/RequestMapping.java
  [RequestMappingHandlerMapping]: https://github.com/spring-projects/spring-framework/blob/v4.3.7.RELEASE/spring-webmvc/src/main/java/org/springframework/web/servlet/mvc/method/annotation/RequestMappingHandlerMapping.java
  [AbstractHandlerMethodMapping_L341]: https://github.com/spring-projects/spring-framework/blob/v4.3.7.RELEASE/spring-webmvc/src/main/java/org/springframework/web/servlet/handler/AbstractHandlerMethodMapping.java#L341
  [AbstractHandlerMethodMapping_L352]: https://github.com/spring-projects/spring-framework/blob/v4.3.7.RELEASE/spring-webmvc/src/main/java/org/springframework/web/servlet/handler/AbstractHandlerMethodMapping.java#L352 
  [BasicErrorController]: https://github.com/spring-projects/spring-boot/blob/v1.5.2.RELEASE/spring-boot-autoconfigure/src/main/java/org/springframework/boot/autoconfigure/web/BasicErrorController.java
  [BasicErrorController_errorHtml]: https://github.com/spring-projects/spring-boot/blob/v1.5.2.RELEASE/spring-boot-autoconfigure/src/main/java/org/springframework/boot/autoconfigure/web/BasicErrorController.java#L86
  [BasicErrorController_error]: https://github.com/spring-projects/spring-boot/blob/v1.5.2.RELEASE/spring-boot-autoconfigure/src/main/java/org/springframework/boot/autoconfigure/web/BasicErrorController.java#L98
  [RequestMappingInfo_L266]: https://github.com/spring-projects/spring-framework/blob/v4.3.7.RELEASE/spring-webmvc/src/main/java/org/springframework/web/servlet/mvc/method/RequestMappingInfo.java#L266
  [ProducesRequestCondition_L235]: https://github.com/spring-projects/spring-framework/blob/v4.3.7.RELEASE/spring-webmvc/src/main/java/org/springframework/web/servlet/mvc/condition/ProducesRequestCondition.java#L235
  [HttpEntityMethodProcessor_L159]: https://github.com/spring-projects/spring-framework/blob/v4.3.7.RELEASE/spring-webmvc/src/main/java/org/springframework/web/servlet/mvc/method/annotation/HttpEntityMethodProcessor.java#L159
  [HttpEntityMethodProcessor]: https://github.com/spring-projects/spring-framework/blob/v4.3.7.RELEASE/spring-webmvc/src/main/java/org/springframework/web/servlet/mvc/method/annotation/HttpEntityMethodProcessor.java
  [AbstractMessageConverterMethodProcessor]: https://github.com/spring-projects/spring-framework/blob/v4.3.7.RELEASE/spring-webmvc/src/main/java/org/springframework/web/servlet/mvc/method/annotation/AbstractMessageConverterMethodProcessor.java
  [AbstractMessageConverterMethodProcessor_L259]: https://github.com/spring-projects/spring-framework/blob/v4.3.7.RELEASE/spring-webmvc/src/main/java/org/springframework/web/servlet/mvc/method/annotation/AbstractMessageConverterMethodProcessor.java#L259
  [AbstractMessageConverterMethodProcessor_L163]: https://github.com/spring-projects/spring-framework/blob/v4.3.7.RELEASE/spring-webmvc/src/main/java/org/springframework/web/servlet/mvc/method/annotation/AbstractMessageConverterMethodProcessor.java#L163
  [HttpMessageConverter]: https://github.com/spring-projects/spring-framework/blob/v4.3.7.RELEASE/spring-web/src/main/java/org/springframework/http/converter/HttpMessageConverter.java
  
  [def-foo]: src/main/java/me/chanjar/controllers/FooController.java
  [def-foo-rest]: src/main/java/me/chanjar/controllers/FooRestController.java
  
  [pkg-me.chanjar.boot.def]: src/main/java/me/chanjar/boot/def
  [boot-CustomErrorViewConfiguration]: src/main/java/me/chanjar/boot/customerrorpage/CustomErrorViewConfiguration.java
  [boot-DefaultExample]: src/main/java/me/chanjar/boot/def/DefaultExample.java
  
  [pkg-me.chanjar.boot.customerrorpage]: src/main/java/me/chanjar/boot/customerrorpage
  [boot-custom-error-page-error-html]: src/main/resources/templates/custom-error-page/error.html
  [boot-CustomErrorViewExample]: src/main/java/me/chanjar/boot/customerrorpage/CustomErrorViewExample.java
  
  [pkg-me.chanjar.boot.customerrorattributes]: src/main/java/me/chanjar/boot/customerrorattributes
  [boot-CustomErrorAttributes]: src/main/java/me/chanjar/boot/customerrorattributes/CustomErrorAttributes.java
  [boot-CustomErrorAttributesExample]: src/main/java/me/chanjar/boot/customerrorattributes/CustomErrorAttributesExample.java

  [pkg-me.chanjar.boot.customerrorcontroller]: src/main/java/me/chanjar/boot/customerrorcontroller
  [boot-CustomErrorController]: src/main/java/me/chanjar/boot/customerrorcontroller/CustomErrorController.java
  [boot-CustomErrorControllerConfiguration]: src/main/java/me/chanjar/boot/customerrorcontroller/CustomErrorControllerConfiguration.java
  [boot-CustomErrorControllerExample]: src/main/java/me/chanjar/boot/customerrorcontroller/CustomErrorControllerExample.java
  
  [logback-spring.xml]: src/main/resources/logback-spring.xml
