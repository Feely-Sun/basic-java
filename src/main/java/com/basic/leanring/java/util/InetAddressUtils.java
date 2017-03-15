package com.basic.leanring.java.util;

import com.alibaba.common.lang.StringUtil;

import java.net.InetAddress;

/**
 * @author sunzihan
 * @version $Id: InetAddressUtils.java V 0.1 3/15/17 15:45 sunzihan EXP $
 */
public class InetAddressUtils {

    /** 预发布机器标识 */
    private final static String    PUB_SERVER_GROUP = "99";

    /** 本机IP地址 */
    private static volatile String ipAddress;

    /** 本机域名 */
    private static volatile String hostName;

    /** 机器 组 */
    private static volatile String serverGroup;

    /**
     * 私有构造函数，屏蔽外围调用
     */
    private InetAddressUtils() {
        // ignore
    }

    /**
     * 获取本机IP
     *
     * @return 如果获取成功返回本机IP，否则返回null
     */
    public static String getServerIp() {
        if (StringUtil.isBlank(ipAddress)) {
            initInetAddress();
        }
        return ipAddress;
    }

    /**
     * 获取本机服务器名称
     *
     * @return
     */
    public static String getServerHostName() {
        if (StringUtil.isBlank(hostName)) {
            initInetAddress();
        }
        return hostName;
    }

    /**
     * 是否是预发布环境
     *
     * @return
     */
    public static boolean isPrePubEnv() {
        if (StringUtil.isBlank(serverGroup)) {
            initInetAddress();
        }
        return StringUtil.equals(serverGroup, PUB_SERVER_GROUP);
    }

    /**
     * 获取IP地址信息相关对象
     *
     * @return
     */
    private static void initInetAddress() {
        InetAddress address = getInetAddress();
        if (address != null) {
            hostName = address.getHostName();
            ipAddress = address.getHostAddress();
        }

        /**
         * 对于线上机器hostname格式为:systemName-serverGroup-serNo
         * 例如:ctu-60-1、rulecenter-60-1、rulecenter-99-1
         */
        if (StringUtil.isNotBlank(hostName)) {
            String[] hostNameArray = StringUtil.split(hostName, SymbolConstants.HORIZONTAL_LINE);
            if (hostNameArray.length == 3) {
                serverGroup = hostNameArray[1];
            }
        }
    }

    /**
     * 获取inetAddress对象
     *
     * @return
     */
    private static InetAddress getInetAddress() {
        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
        } catch (Exception e) {
            return address;
        }
        return address;
    }

}

