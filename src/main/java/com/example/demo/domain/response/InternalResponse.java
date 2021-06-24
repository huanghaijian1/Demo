package com.example.demo.domain.response;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;

@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel
public class InternalResponse<T> {

    private String code;
    private String message;
    private T body;

    public InternalResponse() {
    }


    public InternalResponse(String code, Object... msgObjects) {
        this.code = code;
        this.message = InternalMessage.getMessage(code, msgObjects);
    }


    public static <T> InternalResponse<T> success() {
        return new InternalResponse<T>(ResCodeConstant.SUC_CODE);
    }

    public static <T> InternalResponse<T> success(String code) {
        return new InternalResponse<T>(code);
    }

    public static <T> InternalResponse<T> withParams(String code, String message) {
        InternalResponse<T> internalResponse = new InternalResponse<T>();
        internalResponse.code = code;
        internalResponse.message = message;
        return internalResponse;
    }

    public static <T> InternalResponse<T> withParamsMessage(String code) {
        InternalResponse<T> internalResponse = new InternalResponse<T>();
        internalResponse.code = code;
        internalResponse.message = InternalMessage.getMessage(code);
        return internalResponse;
    }


    public static <T> InternalResponse<T> fail() {
        return new InternalResponse<T>(ResCodeConstant.FAIL_CODE);
    }

    public static <T> InternalResponse<T> fail(String code) {
        return new InternalResponse<T>(code);
    }

    public static <T> InternalResponse<T> fail(String code, Object... msgObjects) {
        return new InternalResponse<T>(code, msgObjects);
    }

    public InternalResponse<T> withBody(T body) {
        this.body = body;
        return this;
    }


    public String getMessage() {
        return message;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

}
