package com.song.usercenter.exception;

import com.song.usercenter.common.BaseResponse;
import com.song.usercenter.common.ErrorCode;
import com.song.usercenter.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 *
 * @author The Outsider
 */
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
