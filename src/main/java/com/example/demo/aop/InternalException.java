package com.example.demo.aop;

/**
 * 自定义异常类，自定义异常可继承此类，catch该异常即可
 */
public abstract class InternalException extends RuntimeException  {

    public abstract String getErrorCode();

    public String getCustomizeMessage() {
        return "";
    }

    public Object[] getParams() {
        return null;
    }

}
