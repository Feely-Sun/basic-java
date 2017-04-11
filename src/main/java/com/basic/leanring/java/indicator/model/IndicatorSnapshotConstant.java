package com.basic.leanring.java.indicator.model;

/**
 * @author sunzihan
 * @version $Id: IndicatorSnapshotConstant.java V 0.1 3/15/17 15:51 sunzihan EXP $
 */
public class IndicatorSnapshotConstant {

    /** rowkey的前缀的长度 - 用于散列 */
    public static final int    ROWKEY_PREFIX_SIZE            = 5;

    /** 默认的分隔符 */
    public static final String DEFAULT_SPLIT                 = ",";

    /** 下划线  */
    public static final String UNDER_SCORE                   = "_";

    /** 指标快照表 */
    public static final String TABLE_NAME                    = "indicator_snapshot";

//    /** 列簇名称byte数组 */
//    public static final byte[] COLUMN_FAMILY                 = Bytes.toBytes("i");
//
//    /** FF列簇名称byte数组 */
//    public static final byte[] FF_COLUMN_FAMILY                 = Bytes.toBytes("ff");

    /** 分包阈值  */
    public static final int    DEFAULT_SNAPSHOT_PACKAGE_SIZE = 200;

    /** 系统操作人 */
    public static final String SYS_OPERATOR                  = "SYSTEM";

    /** 正式运行常量*/
    public static final String RUN_STATUS                    = "R";

    /** 草稿常量*/
    public static final String ADD_STATUS                    = "A";

    /** 新快照表名 */
    public static final String SD_INDICATOR_SNAPSHOT         = "sd_indicator_snapshot";

    /** FF_Pipeline快照表名 */
    public static final String FF_PIPELINE_SNAPSHOT         = "ff_pipeline_snapshot";

    /** 新快照记录表名 */
    public static final String RECORDS_TABLE_NAME            = "sd_indicator_snapshot_records";

    /** 新快照记录表列族 */
    public static final String RECORDS_FAMILY_NAME           = "i";

    /** 快照策略 存储*/
    public static final int    SNAPSHOT_POLICY_SAVE          = 1;

    /** 快照策略 不存储 */
    public static final int    SNAPSHOT_POLICY_UNSAVE        = 0;

    /** 补全快照节点名称 */
    public static final String SUPPLEMENT_SNAPSHOT_NODE_NAME = "sdp";

    /** 指定快照节点名称*/
    public static final String SPECIFY_SNAPSHOT_NODE_NAME    = "specify";

    public static final String CIF_SNAPSHOT_NODE_NAME        = "cif";

    /***Pipeline 快照*/
    public static final String FEATUR_FACTORY_PIPLEINE       ="pipeline";
}

