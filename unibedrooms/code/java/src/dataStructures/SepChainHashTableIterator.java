package dataStructures;

/**
 * @author António Palmeirim numero 63667
 * @author Duarte Inácio numero 62397
 *
 * Classe SepChainHashTableIterator - iterador especial para a AVLTree
 * O objetivo deste iterador é não permitir que seja devolvido um iterador de um dicionário para a Main
 */
public class SepChainHashTableIterator<K,V> implements Iterator<Entry<K,V>> {

    protected int current;
    protected int maxSize;//numero de elementos existentes
    protected Dictionary<K,V>[] table;
    protected Iterator<Entry<K,V>> it;

    /**
     * Construtor do iterador da tabela de dispressão.
     * @param table tabela de dispersao recebido para iterar
     * @param maxSize numero de elementos existentes
     */
    public SepChainHashTableIterator(Dictionary<K,V>[] table, int maxSize){
        this.table = table;
        this.maxSize = maxSize;
        rewind();
    }

    /**
     * Analisa se o maxSize é maior do que 0.
     * @return true caso maxSize > 0, false caso contrario
     */
    @Override
    public boolean hasNext() {
        return maxSize > 0;
    }

    /**
     * Vai procurar a proxima entry.
     * O maxSize é descrementado, quando chega a 0 significa que não existem mais entrys
     * logo não será necessário iterar o resto da tabela, enviando excepcao caso não hajam
     * mais elementos
     * @return Entry entrada a devolver
     * @throws NoSuchElementException caso nao hajam mais entrys na tabela de dispersão
     */
    @Override
    public Entry<K, V> next() throws NoSuchElementException {
        while((it == null || !it.hasNext()) && hasNext()){
            it = table[++current].iterator();
        }
        if(!hasNext()) throw new NoSuchElementException();
        maxSize--;
        return it.next();
    }

    /**
     * Prepara o current para a posição 0 e coloca o iterador igual ao iterador da primeira posição
     * da tabela de dispersão.
     */
    @Override
    public void rewind() {
        this.current = 0;
        it = table[current].iterator();
    }
}
