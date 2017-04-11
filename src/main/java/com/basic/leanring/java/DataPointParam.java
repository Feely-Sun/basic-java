package com.basic.leanring.java;

import java.io.Serializable;

import com.alibaba.common.lang.StringUtil;

/**
 * @author sunzihan
 * @version $Id: DataPointParam.java V 0.1 3/1/17 19:50 sunzihan EXP $
 */
public class DataPointParam implements Serializable{

    /** sId */
    private static final long serialVersionUID = -2897659991448365394L;

    private String            desc;

    private String            name;

    private String            paramType;

    /**
     * Getter method for property <tt>name</tt>.
     *
     * @return property value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method for property <tt>name</tt>.
     *
     * @param name value to be assigned to property name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter method for property <tt>desc</tt>.
     *
     * @return property value of desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * Setter method for property <tt>desc</tt>.
     *
     * @param desc value to be assigned to property desc
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * Getter method for property <tt>paramType</tt>.
     *
     * @return property value of ParamType
     */
    public String getParamType() {
        return paramType;
    }

    /**
     * Setter method for property <tt>paramType</tt>.
     *
     * @param paramType value to be assigned to property paramType
     */
    public void setParamType(String paramType) {
        this.paramType = paramType;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DataPointParam)) {
            return false;
        }
        DataPointParam other = (DataPointParam) obj;
        return StringUtil.equals(name, other.name) && StringUtil.equals(paramType, other.paramType);
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 17;
        hash = hash * 31 + (name == null ? 0 : name.hashCode());
        hash = hash * 31 + (paramType == null ? 0 : paramType.hashCode());
        return hash;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "name[" + name + "],desc[" + desc + "],paramType[" + paramType + "]";
    }
}

