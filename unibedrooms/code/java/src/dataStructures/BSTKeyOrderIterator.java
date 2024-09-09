package dataStructures;

public class BSTKeyOrderIterator <K,V> implements Iterator<Entry<K,V>>{

    protected BSTNode<K,V> root;
    protected Stack<BSTNode<K,V>> stack;
    public BSTKeyOrderIterator(BSTNode<K, V> root) {
        this.stack = new StackInList<BSTNode<K, V>>();
        this.root = root;
        rewind();
    }

    @Override
    public boolean hasNext() {
        return !stack.isEmpty();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Entry<K, V> next() throws NoSuchElementException {
        if(!hasNext()) throw new NoSuchElementException();
        BSTNode<K,V> node =  stack.pop();
        push(node.getRight());
        return node.getEntry();
    }

    @Override
    public void rewind() {
        push(root);
    }


    private void push(BSTNode<K,V> node) {
        while(node != null) {
            stack.push(node);
            node = node.getLeft();
        }
    }

}
