- Every thread has its own callstack of functions.
- When we launch a thread from main thread, it will maintain a separate callstack than main, main's callstack will not contain functions which the thread calls 
- Thread that is spawned from separate method apart from main, we will not be able to catch any exception/value returned from it

- Thread t can assign the result to a global variable and when the t finishes. It can return me 