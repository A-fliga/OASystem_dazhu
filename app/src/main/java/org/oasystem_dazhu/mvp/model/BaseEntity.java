package org.oasystem_dazhu.mvp.model;

/**
 * Created by Administrator on 2017/10/14 0014.
 */

public class BaseEntity<T> {


    private int mStatus;
    private T mData;
    private String mMsg;
    private int mCode;

    public int getStatus() {
        return mStatus;
    }

    public void setStatus(int status) {
        this.mStatus = status;
    }

    public T getData() {
        return mData;
    }

    public void setData(T data) {
        this.mData = data;
    }

    public String getMsg() {
        return mMsg;
    }

    public void setMsg(String msg) {
        this.mMsg = msg;
    }

    public int getCode() {
        return mCode;
    }

    public void setCode(int code) {
        this.mCode = code;
    }
}
