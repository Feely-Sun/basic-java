package com.basic.leanring.java.antfeature.featurelifetime.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sunzihan
 * @version $Id: IValidResult.java V 0.1 5/8/17 19:49 sunzihan EXP $
 */
public class IValidResult<T> implements ValidResult<T> {

    //验证结果
    private boolean success;

    //下一个验证的值
    private List<T>       failed = new ArrayList<>();


    public IValidResult(){}


    public IValidResult(boolean success ){
        this.success = success;
    }

    @Override
    public boolean passed() {
        return false;
    }


    @Override
    public List<T> faied() {
        return null;
    }


    @Override
    public void mark() {
        this.success = true;
    }

    @Override
    public void addFailed(T t) {
        this.failed.add(t);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}

