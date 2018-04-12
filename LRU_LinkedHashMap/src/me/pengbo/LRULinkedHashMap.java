package me.pengbo;

import java.util.LinkedHashMap;

/**
 * 具有容量限制的LRU缓存
 * Created by pengbo on 18-4-12.
 */
public class LRULinkedHashMap<K, V> extends LinkedHashMap<K,V>{

    private long maxSize = 1000;

    public LRULinkedHashMap(int initialCapacity, float loadFactor){
        super(initialCapacity, loadFactor, true);
    }

    public LRULinkedHashMap(int initialCapacity, float loadFactor, long maxSize){
        super(initialCapacity, loadFactor,true);
        this.maxSize = maxSize;
    }

    public boolean removeEldestEntry(java.util.Map.Entry<K, V> eldest) {
        return size() > maxSize;
    }
}
