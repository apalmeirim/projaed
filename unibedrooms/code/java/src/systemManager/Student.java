package systemManager;

import dataStructures.Iterator;

import java.io.Serializable;

/**
 * @author António Palmeirim numero 63667
 * @author Duarte Inácio numero 62397
 *
 * Interface que representa um estudante no sistema e contem os metodos de consulta de dados dele.
 *
 */
public interface Student extends Serializable, User {

    /**
     * Devolve a idade do estudante
     * @return int, idade do estudante
     */
    int getAge();

    /**
     * Devolve a localidade de residencia do estudante
     * @return String, localidade de residencia
     */
    String getLocal();

    /**
     * Verifica se o estudante atigiu o maximo de candidaturas (10)
     * @return true se o estudante tiver o limite de candidaturas
     */
    boolean hasFullCandidacys();

    /**
     * Devolve um iterador com todos os quartos aos quais o estudante tem uma candidatura
     * @return iterador com todas os quartos aos quais o estudante tem uma candidatura
     */
    Iterator<RoomEdit> getCandidacys();

}
