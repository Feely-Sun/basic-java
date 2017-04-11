package com.basic.leanring.java.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.log4j.Logger;

import com.basic.leanring.java.serializer.EnumSerializerFactory;
import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.caucho.hessian.io.SerializerFactory;

/**
 * @author sunzihan
 * @version $Id: Cloner.java V 0.1 3/15/17 16:19 sunzihan EXP $
 */
public class Cloner {

    private final static Logger logger         = Logger.getLogger(Cloner.class);
    private static EnumSerializerFactory eunmFactory = new EnumSerializerFactory();
    private static final SerializerFactory factory;
    static {
        factory = new SerializerFactory();
        factory.addFactory(eunmFactory);
        //factory.addFactory(null);
        factory.setAllowNonSerializable(true);
    }

    /**
     * 深度对象拷贝，支持非 Serializable 实例。
     *
     * @param original 原对象
     * @return 拷贝后的对象
     */
    public static Object copy(Object original) {
        byte[] data = writeObject(original);
        return readObject(data);
    }

    /**
     * 将 Hessian 协议的字节数组反序列化为对象。
     *
     * @param bytes Hessian 序列化数组
     * @return 反序列化的对象实例
     */
    public static Object readObject(byte[] bytes) {
        Object obj = null;
        try {
            ByteArrayInputStream bin = new ByteArrayInputStream(bytes);
            Hessian2Input in = new Hessian2Input(bin);
            in.setSerializerFactory(factory);
            in.startMessage();
            obj = in.readObject();
            in.completeMessage();
            in.close();
            bin.close();
        } catch (Exception e) {
            ToStringBuilder sb = new ToStringBuilder(null);
            sb.append("Hessian反序列化出错!");
            sb.append(bytes);
            logger.error(sb.toString(), e);
        }
        return obj;
    }

    /**
     * 将一个对象序列化为 Hessian 协议字节数组。
     *
     * @param obj 要序列化的对象
     * @return Hessian 协议字节数组
     */
    public static byte[] writeObject(Object obj) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Hessian2Output out = new Hessian2Output(bos);
        out.setSerializerFactory(factory);
        try {
            out.startMessage();
            out.writeObject(obj);
            out.completeMessage();
            out.close();
        } catch (Exception e) {
            logger.error("Hessian序列化出错!" + obj, e);
        }
        return bos.toByteArray();
    }
}

