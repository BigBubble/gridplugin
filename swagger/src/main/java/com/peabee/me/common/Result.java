package com.peabee.me.common;

/**
 * Created by pengbo on 16-4-28.
 */
public class Result<T> {
    private boolean status;
    private T data;
    private String errMsg;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
