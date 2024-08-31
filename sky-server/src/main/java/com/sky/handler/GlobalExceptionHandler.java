package com.sky.handler;

import com.sky.constant.MessageConstant;
import com.sky.exception.BaseException;
import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获业务异常
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(BaseException ex){
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }

    /**
     * catch the exception of duplicate username when adding a new employee
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(SQLIntegrityConstraintViolationException ex){
        // Duplicate entry 'liming' for key 'employee.idx_username'
        String message = ex.getMessage();

        if(message.contains("Duplicate entry")) {
            // to get the duplicated username
            String[] split = message.split(" ");
            String username = split[2];
            String newMessage = username + MessageConstant.ALREADY_EXIST;
            return Result.error(newMessage);
        }else {
            return Result.error(MessageConstant.UNKNOWN_ERROR);
        }

    }

}
