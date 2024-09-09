package dataStructures;  

/**
 * Separate Chaining Hash table implementation
 * @author AED  Team
 * @version 1.0
 * @param <K> Generic Key, must extend comparable
 * @param <V> Generic Value 
 */

public class SepChainHashTable<K extends Comparable<K>, V> 
    extends HashTable<K,V> 
{ 
	/**
	 * Serial Version UID of the Class.
	 */
    static final long serialVersionUID = 0L;

	/**
	 * The array of dictionaries.
	 */
    protected Dictionary<K,V>[] table;



    /**
     * Constructor of an empty separate chaining hash table,
     * with the specified initial capacity.
     * Each position of the array is initialized to a new ordered list
     * maxSize is initialized to the capacity.
     * @param capacity defines the table capacity.
     */
    @SuppressWarnings("unchecked")
    public SepChainHashTable( int capacity)
    {
        int arraySize = HashTable.nextPrime((int) (1.1 * capacity));
        initialize(arraySize);
        // Compiler gives a warning.
        maxSize = capacity;
    }                                      


    public SepChainHashTable( )
    {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Vai criar uma nova hash table com o dobro do tamanho e vai inserir todas
     * as posicoes da hash table antiga para a nova.
     * Creates a new hash table with double the size and inserts all positions of
     * old hash table two new one
     */
    @SuppressWarnings("unchecked")
    private void rehash(){
        maxSize = maxSize * 2;
        Iterator<Entry<K, V>> it = this.iterator();
        int tableSize = HashTable.nextPrime((int) 1.1 * maxSize);
        initialize(tableSize);
        while (it.hasNext()) {
            Entry<K, V> e = it.next();
            this.insert(e.getKey(), e.getValue());
        }
    }

    /**
     * Vai inicializar todas as posicoes da tabela com uma nova lista duplamente ordenada vazia
     * Initializes all the positions of the table with a new double linked list
     * @param tableSize int, size of the table
     */
    @SuppressWarnings("unchecked")
    private void initialize(int tableSize){
        this.table = (Dictionary<K, V>[]) new Dictionary[tableSize];
        for(int i = 0; i < tableSize; i++)
            table[i] = new OrderedDoubleList<>();
        currentSize = 0;
    }
    /**
     * Returns the hash value of the specified key.
     * @param key to be encoded
     * @return hash value of the specified key
     */
    protected int hash( K key )
    {
        return Math.abs( key.hashCode() ) % table.length;
    }

    /**
     * Fi
     * @param key whose associated value is to be returned
     * @return the value associated to the key given
     */
    @Override
    public V find( K key )
    {
        return table[ this.hash(key) ].find(key);
    }

    /**
     * Inserts an element to the table with the given key and value
     * @param key with which the specified value is to be associated
     * @param value to be associated with the specified key
     * @return
     */
    @Override
    public V insert( K key, V value )
    {
        if ( this.isFull() )
        	//Original commented, to compile.
            this.rehash();
        OrderedDoubleList<K, V> dictionary = (OrderedDoubleList<K, V>) table[this.hash(key)];
        V v = dictionary.insert(key, value);
        if(v == null) currentSize++;
        return v;
    }

    /**
     * Removes an element of the table with the given key
     * @param key whose entry is to be removed from the map
     * @return V, value present
     */
    @Override
    public V remove( K key )
    {
        if(currentSize == 0) return null;
        currentSize--;
        return table[this.hash(key)].remove(key);
    }

    @Override
    /**
     * Returns a seperated chain hash table iterator, with the given table and the size of the table
     */
    public Iterator<Entry<K,V>> iterator( ) {
        return new SepChainHashTableIterator<>(table, currentSize);
    }
}










