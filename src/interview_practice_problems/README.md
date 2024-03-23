1. Blocking Queue OR Bounded Buffer OR Consumer Producer
   1. A blocking queue is defined as a queue which blocks the caller of the enqueue method if there’s no more capacity to add the new item being enqueued.
   2. the queue blocks the dequeue caller if there are no items in the queue.
   3. the queue notifies a blocked enqueuing thread when space becomes available and a blocked dequeuing thread when an item becomes available in the queue.
2. 