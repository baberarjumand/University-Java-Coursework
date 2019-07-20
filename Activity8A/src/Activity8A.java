public class Main {

    public static void main(String[] args) {
        int m = 3;
        int n = 2;

        int result = ackermannFunction(m, n);

        System.out.println("Result = " + result);

        System.out.println("\nEnd of processing");
    }

    private static int ackermannFunction(int m, int n) {

        if (m==0)
            return (n+1);
        else if (m>0 && n==0)
            return ackermannFunction(m-1, 1);
        else if (m>0 && n>0)
            return ackermannFunction(m-1, ackermannFunction(m, n-1));

        return 0;

    }


}
