package com.basic.leanring.java.serializer;

import com.caucho.hessian.io.AbstractSerializerFactory;
import com.caucho.hessian.io.Deserializer;
import com.caucho.hessian.io.HessianProtocolException;
import com.caucho.hessian.io.Serializer;

/**
 * @author sunzihan
 * @version $Id: EnumSerializerFactory.java V 0.1 3/15/17 17:00 sunzihan EXP $
 */
public class EnumSerializerFactory extends AbstractSerializerFactory {
    /**
     * Returns the serializer for a class.
     *
     * @param cl
     *            the class of the object that needs to be serialized.
     *
     * @return a serializer object for the serialization.
     */
    public Serializer getSerializer(Class cl) throws HessianProtocolException {
        if (com.basic.leanring.java.util.Enum.class.isAssignableFrom(cl)) {
            return new EnumSerializer(cl);
        }
        return null;
    }

    /**
     * Returns the deserializer for a class.
     *
     * @param cl
     *            the class of the object that needs to be deserialized.
     *
     * @return a deserializer object for the serialization.
     */
    public Deserializer getDeserializer(Class cl)
            throws HessianProtocolException {
        if (Enum.class.isAssignableFrom(cl)) {
            //return new AliEnumDeserializer(cl);
        }
        return null;
    }

}

