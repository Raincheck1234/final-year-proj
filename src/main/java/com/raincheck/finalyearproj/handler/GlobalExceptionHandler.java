package com.raincheck.finalyearproj.handler;

import com.raincheck.finalyearproj.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 捕获所有业务异常
    @ExceptionHandler(RuntimeException.class)
    public Result<String> handleRuntimeException(RuntimeException e) {
        log.error(e.getMessage()); // 控制台打印方便调试
        return Result.error(500, e.getMessage());
    }

    // 捕获兜底异常
    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception e) {
        return Result.error(500, "Internal Server Error: " + e.getMessage());
    }
}
