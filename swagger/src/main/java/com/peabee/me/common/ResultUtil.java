package com.peabee.me.common;

/**
 * Created by pengbo on 16-4-28.
 */
public class ResultUtil {

    public static <T> Result<T> buildSuccessResult(T model){
        Result<T> r = new Result<>();
        r.setStatus(true);
        r.setData(model);
        return r;
    }
}
