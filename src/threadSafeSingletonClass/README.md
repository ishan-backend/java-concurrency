**Design and Implement Thread Safe Singleton Class**:

1. Singleton class ensures = only one instance of class is created during lifetime of app
2. There are several ways to achieve this:
   1. Bill Pugh Singleton Design
   2. Double-checked locking - Done
   3. Use of an enum

**Bill Pugh:**
* The Bill Pugh Singleton Design uses an inner static helper class to hold the Singleton instance. The Singleton instance is created when the inner static class is loaded, which happens only when the getInstance method is called for the first time. This approach is thread-safe and doesn't require explicit synchronization.

**Enum**:
* 
