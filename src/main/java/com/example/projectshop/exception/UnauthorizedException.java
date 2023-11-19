package com.example.projectshop.exception;
// class nhận tham số là message sẽ thông báo khi exception được ném ra
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
