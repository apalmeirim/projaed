package dataStructures;

/**
 * @author António Palmeirim numero 63667
 * @author Duarte Inácio numero 62397
 *
 * Classe SpecialAVLTreeIterator - iterador especial para a AVLTree
 * O objetivo deste iterador é não permitir que seja devolvido um iterador de um dicionário para a Main
 */
public class SpecialAVLTreeIterator<K extends Comparable<K>,E extends Comparable<E>, V> implements Iterator<V> {

    protected OrderedDictionary<K,OrderedDictionary<E,V>> avl;
    protected Iterator<Entry<K, OrderedDictionary<E,V>>> itAVL;
    protected Iterator<Entry<E,V>> it;
    protected int maxSize;

    /**
     * Construtor do iterador especial.
     * @param avl arvore equilibrado recebido para iterar
     * @param maxSize numero de elementos existentes
     */
    public SpecialAVLTreeIterator(OrderedDictionary<K, OrderedDictionary<E, V>> avl, int maxSize){
        this.avl = avl;
        this.maxSize = maxSize;
        rewind();
    }

    /**
     * Analisa se o maxSize é maior do que 0.
     * @return true caso maxSize > 0, false caso contrario
     */
    public boolean hasNext(){
        return maxSize > 0;
    }

    /**
     * Vai procurar o proximo valor.
     * O maxSize é descrementado, quando chega a 0 significa que não existem mais elementos
     * logo não será necessário iterar o resto das sub árvores, enviando excepcao caso não hajam
     * mais elementos
     * @return V value a devolver
     * @throws NoSuchElementException caso não haja mais elementos
     */
    public V next() throws NoSuchElementException {
        while(!it.hasNext() && hasNext()) {
            it = itAVL.next().getValue().iterator();
        }
        if(!hasNext()) throw new NoSuchElementException();
        maxSize--;
        return it.next().getValue();
    }

    /**
     * Prepara os iteradores itAVL e it, em que itAVL será o iterador da arvore principal e it o
     * iterador da primeira posição da árvore principal.
     */
    public void rewind() {
        this.itAVL = avl.iterator();
        this.it = itAVL.next().getValue().iterator();
    }

}
