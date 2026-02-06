package com.sky.handler;

import com.sky.constant.MessageConstant;
import com.sky.exception.BaseException;
import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * Global Exception Handler for all exceptions
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * catch all exceptions
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(BaseException ex){
        log.error("Exception info：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }


    @ExceptionHandler
    public Result exceptionHandler(SQLIntegrityConstraintViolationException ex) {
        // Duplicate entry for unique index
        String message = ex.getMessage();
        if (message.contains("Duplicate entry")) {
            // Template Message: Duplicate entry 'admin' for key 'employee.idx_username'
            String[] split = message.split(" ");
            String username = split[2];
            String msg = username + MessageConstant.ALREADY_EXISTS;
            return Result.error(msg);
        } else {
            return Result.error(MessageConstant.UNKNOWN_ERROR);
        }
    }

}
