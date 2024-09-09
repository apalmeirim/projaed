package dataStructures;

public class InvertibleQueueInList<E> extends QueueInList<E> implements InvertibleQueue<E>{

    public boolean invert;
    public InvertibleQueueInList(){
        super();
        invert = true;
    }
    public void invert() {
        invert = !invert;
    }

    public void enqueue (E element){
        if(this.invert){
            list.addLast(element);
        }else list.addFirst(element);
    }

    public E dequeue( ) throws EmptyQueueException
    {
        if ( list.isEmpty() )
            throw new EmptyQueueException();

        if(this.invert) return list.removeFirst();
        return list.removeLast();
    }
}
