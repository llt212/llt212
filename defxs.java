

import java.util.Scanner;

public class 比较3个数 {
    public static void main(String[] args) {
        Scanner in =new Scanner(System.in);
        int a,b,c;
        System.out.print("请输入3个数：");
        a= in.nextInt();
        b= in.nextInt();
        c= in.nextInt();
        int max=0;
        int min=0;
        if (a>b)
        {
            max=a;
            min=b;
            if(max>c&&min>c)
            {
                System.out.println("最大值为："+max);
                System.out.println("最小值为："+c);
                System.out.println("顺序："+max+">"+b+">"+c);
            }
            else {
                System.out.println("最大值为："+c);
                System.out.println("最小值为："+b);
                System.out.println("顺序："+c+">"+a+">"+b);
            }

        }
        else {
            max=b;
            min=a;
            if(max>c&&min >c)
            {
                System.out.println("最大值为："+max);
                System.out.println("最小值为："+c);
                System.out.println("顺序："+max+">"+a+">"+c);
            }
            else {
                System.out.println("最大值为："+c);
                System.out.println("最小值为："+a);
                System.out.println("顺序："+c+">"+b+">"+a);
            }
        }
    }
}
