
public class Algorithm_1 {
    public static void main(String[] args) {

        int[] a = {2, 1, 3, 5, 9, 8, 7};

        bubble(a);
        System.out.println(Arrays.toString(a));
    }
    public static void bubble(int[] a){

        for (int i = 0; i < a.length-1; i++) {
            boolean swapFlag = false; //表示该轮冒泡有没有发生元素交换 如果没有发生交换则表示已经有序了
            for (int j = 0; j < a.length-1; j++) {
                if(a[j]>a[j+1]){
                    int temp = a[j];
                    a[j] = a[j+1];
                    a[j+1] = temp;
                    swapFlag = true;
                }
            }
            System.out.println("第" + (i+1) + "次冒泡"+ Arrays.toString(a));
            if(!swapFlag){
                break;
            }
        }
    }
}



public static void bubble2(int[] a){
        int n = a.length -1;
        int count = 0;
        while(n>0) {
            int last = 0; // last表示最后一次交换索引的位置
            for (int j = 0; j < n; j++) {
                if(a[j]>a[j+1]){
                    int temp = a[j];
                    a[j] = a[j+1];
                    a[j+1] = temp;
                    last = j;
                }
            }
            System.out.println("第" + (++count) + "轮冒泡"+ Arrays.toString(a));
            n = last;
        }

    }
