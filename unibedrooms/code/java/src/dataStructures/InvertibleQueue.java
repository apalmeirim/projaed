package dataStructures;

public interface InvertibleQueue<E> extends Queue<E> {
    void invert();

    void enqueue(E element);

    E dequeue();
}
