package com.xslong.xslonglib.base.rx.exception;


import retrofit2.Response;

/**
 * @author xslong
 * @time 2017/11/6  14:26
 * @desc ${TODD}
 */


public class HttpException extends RuntimeException {
    private static final long serialVersionUID = 8773734741709178425L;

    private int code;                               //HTTP status code
    private String message;                         //HTTP status message
    private transient Response<?> response;         //The full HTTP response. This may be null if the exception was serialized

    public HttpException(String message) {
        super(message);
    }

    public HttpException(Response<?> response) {
        super(getMessage(response));
        this.code = response.code();
        this.message = response.message();
        this.response = response;
    }

    private static String getMessage(Response<?> response) {
        checkNotNull(response, "response == null");
        return "HTTP " + response.code() + " " + response.message();
    }

    public int code() {
        return code;
    }

    public String message() {
        return message;
    }

    public Response<?> response() {
        return response;
    }

    public static HttpException NET_ERROR() {
        return new HttpException("network error! http response code is 404 or 5xx!");
    }

    public static HttpException COMMON(String message) {
        return new HttpException(message);
    }

    public static <T> T checkNotNull(T object, String message) {
        if (object == null) {
            throw new NullPointerException(message);
        }
        return object;
    }
}
