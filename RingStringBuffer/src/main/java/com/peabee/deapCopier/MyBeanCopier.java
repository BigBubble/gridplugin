package com.peabee.deapCopier;


import org.springframework.cglib.beans.BeanCopier;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by pengbo on 17-10-19.
 */
public class MyBeanCopier {
    /**
     * the beanCopierMap
     */
    private static final ConcurrentMap<String, BeanCopier> beanCopierMap = new ConcurrentHashMap<>();

    private Set<String> excludeProperties  = new HashSet<>();
    /**
     * @description 两个类对象之间转换
     * @param source
     * @param target
     * @return
     * @return T
     */
    public <T> T convert(Object source, Class<T> target) {
        T ret = null;
        if (source != null) {
            try {
                ret = target.newInstance();
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException("create class[" + target.getName()
                        + "] instance error", e);
            }
            BeanCopier beanCopier = getBeanCopier(source.getClass(), target);
            beanCopier.copy(source, ret, new DeepCopyConverter(this, target));
        }
        return ret;
    }
    /**
     * 获取BeanCopier
     * @param source
     * @param target
     * @return
     */
    public static BeanCopier getBeanCopier(Class<?> source, Class<?> target) {
        String beanCopierKey = generateBeanKey(source, target);
        if (beanCopierMap.containsKey(beanCopierKey)) {
            return beanCopierMap.get(beanCopierKey);
        } else {
            BeanCopier beanCopier = BeanCopier.create(source, target, true);
            beanCopierMap.putIfAbsent(beanCopierKey, beanCopier);
        }
        return beanCopierMap.get(beanCopierKey);
    }

    /**
     * 生成两个类的key
     * @param source
     * @param target
     * @return
     */
    public static String generateBeanKey(Class<?> source, Class<?> target) {
        return source.getName() + "@" + target.getName();
    }
    /**
     * 生成两个类的key
     * @return
     */
    public static String generateKey(Object object) {
        return object.getClass().getName() + "@" + object.hashCode();
    }

    public Set<String> getExcludeProperties() {
        return excludeProperties;
    }

    public void setExcludeProperties(Set<String> excludeProperties) {
        this.excludeProperties = excludeProperties;
    }
}
