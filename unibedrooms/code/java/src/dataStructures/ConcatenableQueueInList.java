package dataStructures;

public class ConcatenableQueueInList<E> extends QueueInList<E> implements ConcatenableQueue<E> {

    public ConcatenableQueueInList() {
        super();
    }

    public void append(ConcatenableQueue<E> queue) {
        if (queue instanceof ConcatenableQueueInList<E>){
            DoubleList<E> l1 = (DoubleList<E>) this.list;
            DoubleList<E> l2 = (DoubleList<E>) ((ConcatenableQueueInList<E>) queue).list;
            //l1.append(l2);
        }else
            while(queue.isEmpty()){
                this.enqueue(queue.dequeue());
            }
        {
    }
}
}
