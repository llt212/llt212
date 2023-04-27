public class 水仙花 {
    public static void main(String[] args) {
        System.out.println("100至1000的水仙花数为：");
        for (int i = 100; i < 1000; i++)
        {
            if ((Math.pow(i / 100, 3) + Math.pow((i / 10) % 10, 3) + Math.pow((i % 10), 3)) == i)
            {
                System.out.println(i);
            }

        }
    }
}
