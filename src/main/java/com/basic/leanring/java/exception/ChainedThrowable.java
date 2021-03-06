package com.basic.leanring.java.exception;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Serializable;

/**
 * @author sunzihan
 * @version $Id: ChainedThrowable.java V 0.1 3/15/17 16:44 sunzihan EXP $
 */
public interface ChainedThrowable extends Serializable {
    /**
     * 取得异常的起因.
     *
     * @return 异常的起因.
     */
    Throwable getCause();

    /**
     * 打印调用栈到标准错误.
     */
    void printStackTrace();

    /**
     * 打印调用栈到指定输出流.
     *
     * @param stream 输出字节流.
     */
    void printStackTrace(PrintStream stream);

    /**
     * 打印调用栈到指定输出流.
     *
     * @param writer 输出字符流.
     */
    void printStackTrace(PrintWriter writer);

    /**
     * 打印异常的调用栈, 不包括起因异常的信息.
     *
     * @param writer 打印到输出流
     */
    void printCurrentStackTrace(PrintWriter writer);
}
