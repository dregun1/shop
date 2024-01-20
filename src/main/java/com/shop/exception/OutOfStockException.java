package com.shop.exception;


//예외처리 설정.
public class OutOfStockException extends RuntimeException{

    public OutOfStockException(String message){
        super(message);
    }
}
