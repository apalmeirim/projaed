package systemManager;

import dataStructures.Iterator;

import java.io.Serializable;

/**
 * @author António Palmeirim numero 63667
 * @author Duarte Inácio numero 62397
 *
 * Interface que representa um quarto no sistema e contem os metodos de consulta dele.
 *
 */

public interface Room extends Serializable {

    /**
     * Devolve o estado atual do quarto.
     * @return String, o estado atual do quarto
     */
    String getStateOfRoom();

    /**
     * Devolve a descricao do quarto.
     * @return String, a descricao do quarto
     */
    String getDescription();

    /**
     * Devolve o andar do quarto.
     * @return int, o andar do quarto
     */
    int getFloor();

    /**
     * Devolve a localidade do quarto.
     * @return String, a localidade do quarto
     */
    String getLocal();

    /**
     * Devolve o nome da universidade do quarto
     * @return String, a universidade do quarto
     */
    String getUniversity();

    /**
     * Deolve o nome do quarto.
     * @return String, o nome do quarto
     */
    String getResidenceName();

    /**
     * Devolve o login do gerente do quarto.
     * @return String, o login do gerente do quarto
     */
    String getManagerLogin();

    /**
     * Verifica se o quarto tem candidaturas ativas ou nao.
     * @return boolean, true caso houver candidaturas ativas, false caso contrario
     */
    boolean hasActiveCandidacys();

    /**
     * Verifica se o quarto esta ocupado ou nao.
     * @return boolean, true caso o quarto estiver ocupado, false caso contario
     */
    boolean roomIsOccupied();

    /**
     * Devolve o codigo do quarto.
     * @return String, o codigo do quarto
     */
    String getCode();

    /**
     * Verifica se o estudante tem candidatura no quarto ou nao, dado um estudando enviado.
     * @param student Student, estudante dado pelo utilizador
     * @return boolean, true caso o estudante tenha uma cadidatura para o quarto,
     * false caso contrario
     */
    boolean studentHasCandidacy(Student student);

    /**
     * Devolve todas as candidaturas de um quarto.
     * @return Iterator, iterador de todas as candidaturas de um quarto
     */
    Iterator<Student> getCandidacys();

}
