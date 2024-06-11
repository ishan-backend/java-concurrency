**Problem Statement**:
* You have a data (string), multiple threads are trying to read from or write to it.
* Multiple readers should be allowed to read the data. (Mutual exclusion should not be ensured).
  * Some other thread should be allowed to read when some thread is currently reading from data.
* When a thread is writing, no other thread should be allowed to either read/write data.

**Approach**:

1. Synchronized:
   * read() and write() synchronized for the same class.
   * read() would ensure mutual exclusion which we dont want.

2. Custom Lock:
   * Design own read write lock.
   * Multiple reads at a time.
   * When write is in progress, nothing else should be.

**Re-entrance**:
* Entering something again
* 4 methods here:
  * LockRead()
  * UnlockRead()
  * LockWrite()
  * UnlockWrite()
* Scenarios with respect to re-entrance:
  * Read to Read Re-entrance: Thread which has done LockRead(), does it again. without invoking UnlockRead().
  * Read-to-write-lock Re-entrance: Thread -> ReadLock() -> WriteLock().
  * Write to Write
  * Write to Read
* Think if your ReadLock() is re-entrant:
  * 