# 用户管理中心项目后端
## 自定义后端返回以及异常处理

+ 若请求成功，则使用返回工具类`ResultUtils`按照统一返回格式`BaseResponse`返回

+ 若请求失败，则抛出异常

  > 自定义异常类：`BusinessException` 
  >
  > 抛出异常：`throw new BusinessException(ErrorCode.***ERROR)`

  + 则错误码`ErrorCode`中定义了各种错误，如：无权限、未登录或请求参数错误等
  + 而抛出异常后返回前端的形式也需统一，故定义异常处理器`GlobalExceptionHandler`，捕获所有异常统一使用返回工具类`ResultUtils`按照统一返回格式`BaseResponse`返回

### 通用返回类

```java
@Data
public class BaseResponse<T> implements Serializable {
    private static final long serialVersionUID = -2300736619514645930L;
    private int code;
    private T data;
    private String message;
    private String description;

    public BaseResponse(int code, T data, String message, String description) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.description = description;
    }
    public BaseResponse(int code, T data, String message) {
        this(code, data, message,  "");
    }
    public BaseResponse(int code, T data) {
        this(code, data, "",  "");
    }
    public BaseResponse(ErrorCode errorCode){
        this(errorCode.getCode(), null, errorCode.getMessage(),
             errorCode.getDescription());
    }
}
```



### 错误码

> 错误码中需要包含的内容
>
> + code 状态码
> + message 状态码信息
> + description 状态码详细描述
>   + 在错误码中`description`属性不会填写，由不同业务逻辑根据具体情况去填写
>   + 比如：40000请求参数错误中，会根据具体问情况返回哪个或哪些参数错误

```java
public enum ErrorCode {

    /**
     * 请求成功
     */
    SUCCESS(0, "ok", ""),
    /**
     * 请求参数错误
     */
    PARAMS_ERROR(40000, "请求参数错误", ""),

    /**
     * 请求数据为空
     */
    NULL_ERROR(40001, "请求数据为空", ""),

    /**
     * 未登录
     */
    NOT_LOGIN(40100, "未登录", ""),

    /**
     * 该用户没有权限
     */
    NO_AUTH(40101, "无权限", ""),

    /**
     * 系统内部异常
     */
    SYSTEM_ERROR(50000, "系统内部异常", "");

    private final int code;
    /**
     * 状态码信息
     */
    private final String message;
    /**
     * 状态码描述(详情)
     */
    private final String description;

    ErrorCode(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}
```
### 异常类

> 自定义异常类继承`RuntimeException`类，在`RuntimeException`的构造函数中有message字段，那么在自定义的异常类里需要自己定义`code`和`description`字段
>
> 构造各种异常类的构造器，以便各种情况时使用

```java
public class BusinessException extends RuntimeException{
    private final int code;
    private final String description;

    public BusinessException(String message, int code, String description) {
        super(message);
        this.code = code;
        this.description = description;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = errorCode.getDescription();
    }

    public BusinessException(ErrorCode errorCode, String description) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
```
### 返回工具类
```java
public class ResultUtils {
    /**
     * 成功
     * @param data
     * @param <T>
     * @return
     */
    public static <T> BaseResponse<T> success(T data){
        return new BaseResponse<>(0, data, "ok");
    }

    /**
     * 失败
     * @param errorCode
     * @return
     */
    public static BaseResponse error(ErrorCode errorCode){
        return new BaseResponse<>(errorCode);
    }

    /**
     * 自定义状态码、状态码信息以及状态码详细描述
     * @param code
     * @param message
     * @param description
     * @return
     */
    public static BaseResponse error(int code, String message, String description){
        return new BaseResponse<>(code, null, message, description);
    }

    /**
     * 自定义消息和描述
     * @param errorCode
     * @param description
     * @return
     */
    public static BaseResponse error(ErrorCode errorCode, String message, String description){
        return new BaseResponse<>(errorCode.getCode(), null, message, description);
    }

    /**
     * 自定义描述
     * @param errorCode
     * @param description
     * @return
     */
    public static BaseResponse error(ErrorCode errorCode, String description){
        return new BaseResponse<>(errorCode.getCode(), null, errorCode.getMessage(), description);
    }
}
```
### 全局异常处理器
```java
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 此方法仅捕获BusinessException异常
     * @return
     */
    @ExceptionHandler(value = BusinessException.class)
    public BaseResponse businessExceptionHandler(BusinessException e) {
        log.error("businessException: " + e.getMessage(), e);
        return ResultUtils.error(e.getCode(), e.getMessage(), e.getDescription());
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse runtimeExceptionHandler(RuntimeException e) {
        log.error("runtimeException", e);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR, e.getMessage(), "");
    }
}
```