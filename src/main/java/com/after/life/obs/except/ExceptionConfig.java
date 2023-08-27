package com.after.life.obs.except;

import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * @Author Lostceleste
 * @Version 1.0
 * @Date 2023-08-21 15:15
 */
@RestControllerAdvice
public class ExceptionConfig {

    @ExceptionHandler(CommonException.class)
    public ResponseEntity<?> commonException(CommonException e) {
        return ResponseEntity.status(500).body(e.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return ResponseEntity.status(500).body("请求方式不支持");
    }
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<?> MaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        return ResponseEntity.status(500).body("文件大小超过限制");
    }


}
