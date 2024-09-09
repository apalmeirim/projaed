package dataStructures;

public class EntryClass<K, V> implements Entry<K,V>{

    protected K key;
    protected V value;
    public EntryClass(K key, V value){
        this.key = key;
        this.value = value;
    }
    @Override
    public K getKey() {
        return this.key;
    }

    @Override
    public V getValue() {
        return this.value;
    }

    @SuppressWarnings("unchecked")
    public void setValue(Object value){
        this.value = (V) value;
    }

    @SuppressWarnings("unchecked")
    public void setKey(Object key){
        this.key = (K) key;
    }
}
