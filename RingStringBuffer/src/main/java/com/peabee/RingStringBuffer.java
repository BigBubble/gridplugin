package com.peabee;

import javax.annotation.concurrent.ThreadSafe;
import java.util.Arrays;

/**
 * A ring string buffer with a initial size.
 * When there is enough space in the buffer, append the input string.
 * When there is not enough space in the buffer, double the size.
 *
 * The class should be thread safe.
 *
 * Example:
 * Assume initial size = 5.
 *
 * RingStringBuffer buffer = new RingStringBuffer(5);
 * buffer.put("abc"); // abc
 * buffer.get(1); -> return "a".
 * buffer.put("def"); // bcdef
 * buffer.put("aaa"); // bcdefaaa. When the size is not enough, double the size. size=10
 * buffer.size(); -> return 10;
 * buffer.get(6); -> return "bcdefa"
 * buffer.size(); -> return 10;
 * buffer.put("aaa")// aaaaa
 * buffer.get(10); -> throw exception
 * buffer.get(5) -> return "aaaaa"
 *
 * refer to AbstractStringBuilder
 */
@ThreadSafe
public class RingStringBuffer {

    /** storage */
    private char[] value ;
    /** buffer内字符个数 */
    private int count;

    /**
     * Constructor.
     * @param size Initial size of the StringBuffer.
     */
    public RingStringBuffer(int size){
        if(size > 0){
            value = new char[size];
            count = 0;
        }else{
            throw new IllegalArgumentException("非法参数");
        }
    }


    /**
     * Put input string to the buffer.
     * @param input input string.
     */
    public synchronized void put(String input){
        if(input == null)return;

        int len = input.length();

        int minimumCapacity = count + len;
        if(minimumCapacity - value.length > 0){
            expandCapacity(count + len);
        }

        input.getChars(0, len, value, count);
        count = count + len;
    }


    /**
     * Return the string with length from buffer.
     *
     * @param length the length of result string.
     * @return the result string.
     */
    public synchronized String get(int length){
        if(length <= 0){
            throw new IllegalArgumentException("非法参数(" + length + ")");
        }

        if(length > count){
            throw new IllegalArgumentException("非法参数(" + length + ">" + count + ")");
        }
        //取值
        String result = new String(value,0,length);
        //删除
        System.arraycopy(value, length, value, 0, count-length);
        count -= length;
        return result;
    }


    /**
     * @return return the size of the buffer.
     */
    public synchronized int size(){
        return value.length;
    }

    /**
     * expansion capacity
     * if real minimumCapacity value is greater than Integer.MAX_VALUE,then throw OutOfMemorError Exception
     * @param minimumCapacity
     */
    private void expandCapacity(int minimumCapacity){
        int newCapacity = value.length * 2;
        if (newCapacity - minimumCapacity < 0)
            newCapacity = minimumCapacity;
        if (newCapacity < 0) {
            if (minimumCapacity < 0) // overflow
                throw new OutOfMemoryError();
            newCapacity = Integer.MAX_VALUE;
        }
        value = Arrays.copyOf(value,newCapacity);
    }

    /**
     * toString
     * @return
     */
    @Override
    public String toString(){
        return new String(value,0,count);
    }
}
