package com.basic.leanring.java.util;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.common.lang.enumeration.internal.EnumConstant;

/**
 * @author sunzihan
 * @version $Id: EnumUtil.java V 0.1 3/15/17 16:24 sunzihan EXP $
 */
public class EnumUtil {

    private static final Map entries = new WeakHashMap();

    /**
     * 取得<code>Enum</code>值的类型.
     *
     * @param enumClass
     *            枚举类型
     *
     * @return <code>Enum</code>值的类型
     */
    public static Class getUnderlyingClass(Class enumClass) {
        ClassLoader oldClassLoader = Thread.currentThread()
                .getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(
                    enumClass.getClassLoader());
            return getEnumType(enumClass).getUnderlyingClass();
        } finally {
            Thread.currentThread().setContextClassLoader(oldClassLoader);
        }
    }

    /**
     * 判断指定名称的枚举量是否被定义.
     *
     * @param enumClass
     *            枚举类型
     * @param name
     *            枚举量的名称
     *
     * @return 如果存在, 则返回<code>true</code>
     */
    public static boolean isNameDefined(Class enumClass, String name) {
        if (name == null) {
            return false;
        }
        ClassLoader oldClassLoader = Thread.currentThread()
                .getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(
                    enumClass.getClassLoader());
           Enum.EnumType enumType = getEnumType(enumClass);

            if (enumType.enumList.size() != enumType.nameMap.size()) {
                enumType.populateNames(enumClass);
            }
            return enumType.nameMap.containsKey(name);
        } finally {
            Thread.currentThread().setContextClassLoader(oldClassLoader);
        }
    }

    /**
     * 判断指定值的枚举量是否被定义.
     *
     * @param enumClass
     *            枚举类型
     * @param value
     *            枚举量的值
     *
     * @return 如果存在, 则返回<code>true</code>
     */
    public static boolean isValueDefined(Class enumClass, Number value) {
        if (value == null) {
            return false;
        }
        ClassLoader oldClassLoader = Thread.currentThread()
                .getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(
                    enumClass.getClassLoader());
            Enum.EnumType enumType = getEnumType(enumClass);
            if (enumType.enumList.size() != enumType.valueMap.size()) {
                enumType.populateNames(enumClass);
            }
            return enumType.valueMap.containsKey(value);
        } finally {
            Thread.currentThread().setContextClassLoader(oldClassLoader);
        }
    }

    /**
     * 取得指定名称的枚举量.
     *
     * @param enumClass
     *            枚举类型
     * @param name
     *            枚举量的名称
     *
     * @return 枚举量, 如果不存在, 则返回<code>null</code>
     */
    public static Enum getEnumByName(Class enumClass, String name) {
        if (name == null) {
            return null;
        }
        ClassLoader oldClassLoader = Thread.currentThread()
                .getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(
                    enumClass.getClassLoader());
            Enum.EnumType enumType = getEnumType(enumClass);

            if (enumType.enumList.size() != enumType.nameMap.size()) {
                enumType.populateNames(enumClass);
            }

            return (Enum) enumType.nameMap.get(name);
        } finally {
            Thread.currentThread().setContextClassLoader(oldClassLoader);
        }
    }

    /**
     * 取得指定值的枚举量.
     *
     * @param enumClass
     *            枚举类型
     * @param value
     *            枚举量的值
     *
     * @return 枚举量, 如果不存在, 则返回<code>null</code>
     */
    public static Enum getEnumByValue(Class enumClass, Number value) {
        if (value == null) {
            return null;
        }
        ClassLoader oldClassLoader = Thread.currentThread()
                .getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(
                    enumClass.getClassLoader());
           Enum.EnumType enumType = getEnumType(enumClass);
            if (enumType.enumList.size() != enumType.valueMap.size()) {
                enumType.populateNames(enumClass);
            }
            return (Enum) enumType.valueMap.get(value);
        } finally {
            Thread.currentThread().setContextClassLoader(oldClassLoader);
        }
    }

    /**
     * 取得指定值的枚举量.
     *
     * @param enumClass
     *            枚举类型
     * @param value
     *            枚举量的值
     *
     * @return 枚举量, 如果不存在, 则返回<code>null</code>
     */
    public static Enum getEnumByValue(Class enumClass, int value) {
        ClassLoader oldClassLoader = Thread.currentThread()
                .getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(
                    enumClass.getClassLoader());
            Enum.EnumType enumType = getEnumType(enumClass);
            if (enumType.enumList.size() != enumType.valueMap.size()) {
                enumType.populateNames(enumClass);
            }
            return (Enum) enumType.valueMap.get(new Integer(value));
        } finally {
            Thread.currentThread().setContextClassLoader(oldClassLoader);
        }
    }

    /**
     * 取得指定值的枚举量.
     *
     * @param enumClass
     *            枚举类型
     * @param value
     *            枚举量的值
     *
     * @return 枚举量, 如果不存在, 则返回<code>null</code>
     */
    public static Enum getEnumByValue(Class enumClass, long value) {
        ClassLoader oldClassLoader = Thread.currentThread()
                .getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(
                    enumClass.getClassLoader());
           Enum.EnumType enumType = getEnumType(enumClass);
            if (enumType.enumList.size() != enumType.valueMap.size()) {
                enumType.populateNames(enumClass);
            }
            return (Enum) enumType.valueMap.get(new Long(value));
        } finally {
            Thread.currentThread().setContextClassLoader(oldClassLoader);
        }
    }

    /**
     * 取得指定类型的所有枚举量的<code>Map</code>, 此<code>Map</code>是有序的.
     *
     * @param enumClass
     *            枚举类型
     *
     * @return 指定类型的所有枚举量的<code>Map</code>
     */
    public static Map getEnumMap(Class enumClass) {
        ClassLoader oldClassLoader = Thread.currentThread()
                .getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(
                    enumClass.getClassLoader());
            Enum.EnumType enumType = getEnumType(enumClass);
            if (enumType.enumList.size() != enumType.nameMap.size()) {
                enumType.populateNames(enumClass);
            }
            return Collections.unmodifiableMap(getEnumType(enumClass).nameMap);
        } finally {
            Thread.currentThread().setContextClassLoader(oldClassLoader);
        }
    }

    /**
     * 取得指定类型的所有枚举量的<code>Iterator</code>.
     *
     * @param enumClass
     *            枚举类型
     *
     * @return 指定类型的所有枚举量的<code>Iterator</code>
     */
    public static Iterator getEnumIterator(Class enumClass) {
        ClassLoader oldClassLoader = Thread.currentThread()
                .getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(
                    enumClass.getClassLoader());
            return getEnumType(enumClass).enumList.iterator();
        } finally {
            Thread.currentThread().setContextClassLoader(oldClassLoader);
        }
    }

    /**
     * 取得指定类的<code>ClassLoader</code>对应的entry表. 线程安全。
     *
     * @param enumClass
     *            <code>Enum</code>类
     *
     * @return entry表
     */
    static Map getEnumEntryMap(Class enumClass) {
        ClassLoader classLoader = enumClass.getClassLoader();
        Map entryMap = null;

        synchronized (entries) {
            entryMap = (Map) entries.get(classLoader);

            if (entryMap == null) {
                entryMap = new ConcurrentHashMap(); // pangu
                entries.put(classLoader, entryMap);
            }
        }

        return entryMap;
    }

    /**
     * 取得<code>Enum</code>类的<code>EnumType</code>
     *
     * @param enumClass
     *            <code>Enum</code>类
     *
     * @return <code>Enum</code>类对应的<code>EnumType</code>对象
     */
    public static Enum.EnumType getEnumType(Class enumClass) {
        if (enumClass == null) {
            throw new NullPointerException(EnumConstant.ENUM_CLASS_IS_NULL);
        }

        // pangu：去掉类锁，防止 aliEnum 2种不同类初始化入口互相死锁。
        // synchronized (enumClass) {
        if (!Enum.class.isAssignableFrom(enumClass)) {
            throw new IllegalArgumentException(MessageFormat.format(
                    EnumConstant.CLASS_IS_NOT_ENUM,
                    new Object[] { enumClass.getName() }));
        }

        ConcurrentHashMap entryMap = (ConcurrentHashMap) getEnumEntryMap(enumClass);
        Enum.EnumType enumType = (Enum.EnumType) entryMap.get(enumClass.getName());

        if (enumType == null) {
            Method createEnumTypeMethod = findStaticMethod(enumClass,
                    EnumConstant.CREATE_ENUM_TYPE_METHOD_NAME, new Class[0]);

            if (createEnumTypeMethod != null) {
                try {
                    enumType = (Enum.EnumType) createEnumTypeMethod.invoke(null,
                            new Object[0]);
                } catch (IllegalAccessException e) {
                } catch (IllegalArgumentException e) {
                } catch (InvocationTargetException e) {
                } catch (ClassCastException e) {
                }
            }

            if (enumType != null) {
                enumType.populateNames(enumClass);
                // pangu thread safe
               Enum.EnumType existing = (Enum.EnumType) entryMap.putIfAbsent(
                        enumClass.getName(), enumType);

                // 在JDK5下面，class loader完成并不意味着所有的常量被装配
                // 下面的代码强制装配常量。
                if (existing != null) {
                    enumType = existing;
                }
            }
        }

        if (enumType == null) {
            throw new UnsupportedOperationException(MessageFormat.format(
                    EnumConstant.FAILED_CREATING_ENUM_TYPE,
                    new Object[] { enumClass.getName() }));
        }

        return enumType;
        // }
    }

    /**
     * 查找方法.
     *
     * @param enumClass
     *            枚举类型
     * @param methodName
     *            方法名
     * @param paramTypes
     *            参数类型表
     *
     * @return 方法对象, 或<code>null</code>表示未找到
     */
    private static Method findStaticMethod(Class enumClass, String methodName,
                                           Class[] paramTypes) {
        Method method = null;

        for (Class clazz = enumClass; !clazz.equals(Enum.class); clazz = clazz
                .getSuperclass()) {
            try {
                method = clazz.getDeclaredMethod(methodName, paramTypes);
                break;
            } catch (NoSuchMethodException e) {
            }
        }

        if ((method != null) && Modifier.isStatic(method.getModifiers())) {
            return method;
        }

        return null;
    }

}

