package com.dbs.service.ex;

/** 表示收货地址超出限制的异常 (20) */
public class AddressExceedLimitException extends ServiceException{
    public AddressExceedLimitException() {
        super();
    }

    public AddressExceedLimitException(String message) {
        super(message);
    }

    public AddressExceedLimitException(String message, Throwable cause) {
        super(message, cause);
    }

    public AddressExceedLimitException(Throwable cause) {
        super(cause);
    }

    protected AddressExceedLimitException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
