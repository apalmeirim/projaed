package dataStructures;

public interface ConcatenableQueue<E> extends Queue<E>{
    // Removes all of the elements from the specied queue and
    // inserts them at the end of the queue (in proper order).
    void append( ConcatenableQueue<E> queue );
}
