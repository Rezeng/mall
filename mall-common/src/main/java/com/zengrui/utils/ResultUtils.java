package com.zengrui.utils;

/**
 * 返回数据生成工具
 * Created by Zeng Rui on 2018/3/10.
 */

import com.zengrui.pojo.Result;
import com.zengrui.utils.ResultUtils;

public class ResultUtils<T> {
    Result<T> result;


    public ResultUtils(boolean isSuccess) {
        result = new Result<T>();
        if (isSuccess) {
            result.setSuccess(true);
            result.setMessage("success");
        }
        else {
            result.setSuccess(false);
        }
    }

    public Result<T> getResult(){
        return this.result;
    }

    public void setData(T t) {
        this.result.setResult(t);
    }

    public void setErrorMsg(String msg){
        this.result.setMessage(msg);
    }
}
