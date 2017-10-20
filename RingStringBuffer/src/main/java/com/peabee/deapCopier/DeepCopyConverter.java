package com.peabee.deapCopier;

import org.springframework.cglib.core.Converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by pengbo on 17-10-19.
 */
public class DeepCopyConverter implements Converter{
    private Class<?> target;

    private MyBeanCopier myBeanCopier;

    public DeepCopyConverter(MyBeanCopier myBeanCopier, Class<?> target) {
        this.target = target;
        this.myBeanCopier = myBeanCopier;
    }
    @Override
    public Object convert(Object value, Class targetClazz, Object methodName) {
        if(value == null){
            return null;
        }
        String tempFieldName = methodName.toString().replace("set", "");
        String fieldName = tempFieldName.substring(0, 1)
                .toLowerCase() + tempFieldName.substring(1);
        if (value instanceof List) {
            List values = (List) value;
            List retList = new ArrayList<>(values.size());
            for (final Object source : values) {

                Class clazz = ClassUtils.getElementType(target, fieldName);
                retList.add(myBeanCopier.convert(source, clazz));
            }
            return retList;
        } else if (value instanceof Map) {
            // TODO 暂时用不到，后续有需要再补充
        } else if (!ClassUtils.isPrimitive(targetClazz)) {
            String key = MyBeanCopier.generateKey(value);
            if(myBeanCopier.getExcludeProperties().contains(fieldName)){
                return null;
            }
            Object ret = myBeanCopier.convert(value, targetClazz);
            return ret;
        }
        return value;
    }
}
