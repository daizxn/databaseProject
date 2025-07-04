package org.example.database.exception;

import org.example.database.common.Result;
import org.example.database.common.enums.ResultCodeEnum;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理权限不足异常
     */
    @ExceptionHandler(PermissionDeniedException.class)
    public Result handlePermissionDenied(PermissionDeniedException e) {
        return Result.error("403", e.getMessage());
    }

    /**
     * 处理其他运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public Result handleRuntimeException(RuntimeException e) {
        return Result.error("500", "系统内部错误：" + e.getMessage());
    }
}

