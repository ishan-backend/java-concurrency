package callstack;

public class Driver {
    public static int func1() {
        return 1;
    }

    public static int func2() {
        int x = func1();
        if(x == 1)
            throw new RuntimeException();
        return x;
    }


    public static void main(String[] args) {
        try {
            // func2();
            Thread t = new Thread(new MyRunnable());
            t.start();
        } catch (Exception e) { // exception Thread t threw exception, but there was no one to catch the exception and do something on top of it
            System.out.println(e);
        }
    }
}
