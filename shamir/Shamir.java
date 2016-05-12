import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

public class Shamir {

    public static final int t = 3;

    public static void main(String[] args) {
        BigInteger p,m;
        BigInteger[] y = new BigInteger[t],x = new BigInteger[t], s = new BigInteger[t-1];
        System.out.println("请输入要加密的信息\n m = ");
        Scanner sc = new Scanner(System.in);
        m = sc.nextBigInteger();
        Random r = new Random();
        do{
            p = new BigInteger(100, r);
        }while(!p.isProbablePrime(100));
        
		System.out.println("p="+p);
       
		 for(int i=0; i<t-1; i++){
            s[i] = new BigInteger(100,r);
        }
        //printf("请输入分发的个数:\n");
        //scanf("%d",&g);
        for(int i=0;i<t;i++){
            x[i] = new BigInteger(String.valueOf(i+1));
            y[i] = ff_m(s, m,x[i],p);
            System.out.println("("+x[i]+","+y[i]+")");
        }
        //重构算法,利用拉格朗日差值重构 m
		BigInteger sum = BigInteger.ZERO;
        for(int i=0;i<t;i++){
            BigInteger pro = BigInteger.ONE;
            for(int j=0;j<t;j++){
                if(j!=i){
                    BigInteger tag = x[j].negate();//对 x[j]去相反数
                    BigInteger l = x[i].subtract(x[j]);
                    l = l.modInverse(p);
                    l = tag.multiply(l);
                    pro = pro.multiply(l);
                }
            }
            pro = pro.multiply(y[i]);
            sum = sum.add(pro);
        }
        sum = sum.mod(p);
        System.out.println("重构 m="+sum);
        if(m.compareTo(sum) == 0){
            System.out.println("恢复密文成功!");
        }
        else{
            System.out.println("恢复失败!");
        }
    }

    public static BigInteger ff_m(BigInteger[] s, BigInteger m, BigInteger c, BigInteger p){
        BigInteger yy = m;
        //yy=m+s[0]x[k]+...+s[t-1]x[t-1]
        for(int i=0;i<t-1;i++){
            BigInteger pro = c.modPow(new BigInteger(String.valueOf(i+1)),p);
            pro = pro.multiply(s[i]);
            yy = yy.add(pro);
        }
        yy = yy.mod(p);
        return yy;
    }
}
