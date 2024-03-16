Produces continuously produces messages, consumers consume message.
Among producer and consumer, is a shared queue (bounded i.e. fixed size)

- Try to decouple work of producer from work of consumer
- There is only one thing linking i.e shared resource (queue)
  - If queue is full, wait for consumer to remove certain things
  - If queue is empty, wait for producer to push certain messages
  - Producer and consumer threads should somehow try to talk to one another

- Whenever a producer is waiting, and a consumer is scheduled. It can notify producer that it can be scheduled now and it can push something to queue.
- And vice-versa.
- To do this, we need to acquire a monitor (lock) on an object -> queue instance common to both producer and consumer
  - Whenever, queue (shared resource) is being mutated, should not be done at same time by both producer and consumer
  - Acquire lock using synchronized