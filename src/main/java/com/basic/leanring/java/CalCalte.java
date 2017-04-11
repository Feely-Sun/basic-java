package com.basic.leanring.java;

import java.util.Map;

/**
 * @author sunzihan
 * @version $Id: Cache.java V 0.1 2/28/17 21:55 sunzihan EXP $
 */
public class CalCalte {

    /**
     * 节点运算实例化数据
     */
    final Map<DataPointParam, Object> dataPointParams;
    /**
     * 关联的节点ID
     */
    private final String              nodeId;


    private final String              dname;

    public CalCalte(String nodeId, String dname,Map<DataPointParam, Object> dataPointParams) {

        this.nodeId = nodeId;
        this.dataPointParams = dataPointParams;
        this.dname = dname;

    }


    public Map<DataPointParam, Object> getDataPointParams() {
        return dataPointParams;
    }

    public String getNodeId() {
        return nodeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CalCalte that = (CalCalte) o;
        if (!this.dname.equals(that.dname)) {
            return false;
        }
        if (!this.dataPointParams.equals(that.dataPointParams)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + this.dname.hashCode();
        result = prime * result + ((dataPointParams == null) ? 0 : dataPointParams.hashCode());

        System.out.println(result);
        return result;
    }

    public String getDname() {
        return dname;
    }
}
