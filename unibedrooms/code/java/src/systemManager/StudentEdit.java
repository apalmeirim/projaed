package systemManager;

/**
 * @author António Palmeirim numero 63667
 * @author Duarte Inácio numero 62397
 *
 * Interface que contem os metodos de modificacao de um estudante.
 *
 */


interface StudentEdit extends Student{

    /**
     * Remove uma candidatura feita pelo estudante
     */
    void removeCandidacy(RoomEdit room);

    /**
     * Adiciona uma candidatura nova ao estudante
     */
    void addCandidacy(RoomEdit room);

    /**
     * Remove todas as candidaturas ao estudante
     */
    void removeAllCandidacys();
}
