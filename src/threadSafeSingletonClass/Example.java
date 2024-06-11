package threadSafeSingletonClass;

public class Example {
    public static void main(String[] args) {
        DoubleCheckedLockingSingleton instance = DoubleCheckedLockingSingleton.getInstance();
        System.out.println(instance);

        BillPughSingletonDesign instance2 = BillPughSingletonDesign.getInstance();
        System.out.println(instance2);
    }
}
