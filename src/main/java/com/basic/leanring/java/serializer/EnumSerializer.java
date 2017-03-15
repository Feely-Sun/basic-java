package com.basic.leanring.java.serializer;

import com.caucho.hessian.io.AbstractHessianOutput;
import com.caucho.hessian.io.AbstractSerializer;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @author sunzihan
 * @version $Id: EnumSerializer.java V 0.1 3/15/17 17:04 sunzihan EXP $
 */
public class EnumSerializer  extends AbstractSerializer{

    private Method _name;

    public EnumSerializer(Class cl) {
        try {
            _name = cl.getMethod("getName", new Class[0]);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void writeObject(Object obj, AbstractHessianOutput out) throws IOException {
        if(null == obj){
            return;
        }
        if (out.addRef(obj))
            return;

        Class cl = obj.getClass();

        String name = null;
        try {
            name = (String) _name.invoke(obj, (Object[]) null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        int ref = out.writeObjectBegin(cl.getName());

        if (ref < -1) {
            out.writeString("name");
            out.writeString(name);
            out.writeMapEnd();
        } else {
            if (ref == -1) {
                out.writeClassFieldLength(1);
                out.writeString("name");
                out.writeObjectBegin(cl.getName());
            }

            out.writeString(name);
        }
    }
}

