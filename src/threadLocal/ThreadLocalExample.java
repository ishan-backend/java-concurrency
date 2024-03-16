package threadLocal;

/*
* Synchronization not needed with ThreadLocal :-
* https://youtu.be/sjMe9aecW_A?feature=shared
*
* - Every request is a new thread with some user id who made this request
* - N services the request will flow and all need user id
* - All classes involved in flow of request - use the same context (thread-safety/confinement + per thread object for performance + per-thread-context)
* - Spring uses ContextHolder a lot
*
* Tips:
* - Try using local variables, that way we can avoid using ThreadLocal at all
* - Cleanup once out of scope
* - Delegate creation and management of ThreadLocal to frameworks for management
* */

public class ThreadLocalExample {
    public static ThreadLocal<User> holder = new ThreadLocal<>();
}

// Thread/Request -> Service 1  -> Service 2 -> Service 3  || (Each of them needs user id)
class Service1 { // Service 1 retrieves user with id 1 and saves it in ThreadLocal. Thus saving it for this thread/request
    public void process() {
        User user = new User(1);
        ThreadLocalExample.holder.set(user); // set user for this thread
    }
}

class Service2 {
    public void process() {
        ThreadLocalExample.holder.get(); // get user for this thread
        // process user
    }
}

class Service3 {
    public void process() {
        ThreadLocalExample.holder.remove(); // once processing of the thread is over, cleaning up objects
    }
}

class User {
    public Integer id;
    public User(int id) {
        this.id = id;
    }
    public Integer getId() {
        return id;
    }
}
