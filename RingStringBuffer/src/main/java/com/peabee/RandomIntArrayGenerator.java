package com.peabee;

import java.util.Arrays;
import java.util.Random;

/**
 *
 * 根据原数组产生随机顺序数组
 * Created by pengbo on 17-2-14.
 */
public class RandomIntArrayGenerator {

    public static void main(String[] args) {
        int a[] = new int[]{0,1,2,3,4,5,6,7,8,9};
        for(int i=0; i<5;i++){
            System.out.println(Arrays.toString(randomIntArray(a)));
        }
    }

    /**
     * 产生随机顺序数组,交换法
     * @param a
     * @return
     */
    public static int[] randomIntArray(int[] a){
        int length = a.length;
        int b[] = new int[length];
        System.arraycopy(a,0,b,0,10);

        Random random = new Random();
        for(int i=0; i<length; i++){
            int randomInt = random.nextInt(length);
            if(randomInt != i){
                int temp = b[i];
                b[i] = b[randomInt];
                b[randomInt] = temp;
            }
        }
        return b;
    }
}
