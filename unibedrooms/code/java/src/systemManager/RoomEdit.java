package systemManager;

/**
 * @author António Palmeirim numero 63667
 * @author Duarte Inácio numero 62397
 *
 * Interface que contem os metodos de modificacao de um quarto.
 *
 */


interface RoomEdit extends Room{

    /**
     * Modifica o estado do quarto com o estado dado pelo utilizador
     * @param state String, o novo estado do quarto
     */
    void modifyState(String state);

    /**
     * Adiciona uma candidatura ao quarto com o estudante dado pelo utilizador
     * @param student Student, estudante dado pelo utilizador
     */
    void addCandidacy(Student student);

    /**
     * Remove a candidatura do estudante dado pelo utilizador
     * @param student Student, estudante dado pelo utilizador
     */
    void removeCandidacy(Student student);

    /**
     * Remove todas as candidaturas do quarto
     */
    void removeAllCandidacys();

}
