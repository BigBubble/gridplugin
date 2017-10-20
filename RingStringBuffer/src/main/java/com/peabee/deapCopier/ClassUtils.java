package com.peabee.deapCopier;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pengbo on 17-10-19.
 */
public class ClassUtils {
    private static final Map<Class<?>, Class<?>> primitiveMap = new HashMap<>(10);

    static {
        primitiveMap.put(String.class, String.class);
        primitiveMap.put(Boolean.class, boolean.class);
        primitiveMap.put(Byte.class, byte.class);
        primitiveMap.put(Character.class, char.class);
        primitiveMap.put(Double.class, double.class);
        primitiveMap.put(Float.class, float.class);
        primitiveMap.put(Integer.class, int.class);
        primitiveMap.put(Long.class, long.class);
        primitiveMap.put(Short.class, short.class);
        primitiveMap.put(Date.class, Date.class);
    }

    /**
     * 判断是否是基本类型，Date,String
     * @param clazz
     * @return
     */
    public static boolean isPrimitive(Class<?> clazz) {
        if (primitiveMap.containsKey(clazz)) {
            return true;
        }
        return clazz.isPrimitive();
    }

    /**
     * 获取方法返回值类型
     * @param tartget
     * @param fieldName
     * @return
     */
    public static Class<?> getElementType(Class<?> tartget, String fieldName) {
        Class<?> elementTypeClass = null;
        try {
            Type type = tartget.getDeclaredField(fieldName).getGenericType();
            ParameterizedType t = (ParameterizedType) type;
            String classStr = t.getActualTypeArguments()[0].toString().replace("class ", "");
            elementTypeClass = Thread.currentThread().getContextClassLoader().loadClass(classStr);
        } catch (ClassNotFoundException e){
            throw new RuntimeException("get fieldName[" + fieldName + "] error", e);
        } catch (NoSuchFieldException e){
            throw new RuntimeException("get fieldName[" + fieldName + "] error", e);
        } catch (SecurityException e) {
            throw new RuntimeException("get fieldName[" + fieldName + "] error", e);
        }
        return elementTypeClass;
    }
}
