package systemManager;

import dataStructures.Entry;
import dataStructures.Iterator;
import systemManager.exceptions.*;
import java.io.Serializable;

/**
 * @author António Palmeirim numero 63667
 * @author Duarte Inácio numero 62397
 *
 * Interface SystemManager, programa de interpretacao de dados.
 *
 */

public interface SystemManager extends Serializable{

    /**
     * Adiciona um novo estudante ao sistema dado o login, o nome, a idade, a localidade da residencia
     * a universidade, enviando uma excepcao caso ja exista um utilizador com o codigo dado
     * @param login String, login do estudante
     * @param name String, nome do estudante
     * @param age int, idade do estudante
     * @param local String, localidade da residência
     * @param university String, nome da universidade
     * @throws UserAlreadyExistsException utilizador existente
     */
    void newStudent(String login, String name, int age, String local, String university)
            throws UserAlreadyExistsException;

    /**
     * Adiciona um novo gerente ao sistema dado o login, o nome e o nome da universidade,
     * enviando uma excepcao caso ja exista um utilizador com o codigo dado
     * @param login String, login do gerente
     * @param name String, nome do gerente
     * @param university String, nome da universidade
     * @throws UserAlreadyExistsException utilizador existente
     */
    void newManager(String login, String name, String university) throws UserAlreadyExistsException;

    /**
     * Devolve a informacao de um estudante dado o login, enviando
     * uma excepcao caso o estudante nao exista no sistema
     * @param login String, login do user
     * @return User, devolve um estudante dado o login enviado
     * @throws UserDoesNotExistException estudante nao existe
     */
    User getStudentInformation(String login) throws UserDoesNotExistException;

    /**
     * Devolve a informacao de um manager dado o login, enviando
     * uma excepcao caso o gerente nao exista no sistema
     * @param login String, login do user
     * @return User, devolve um gerente dado o login enviado
     * @throws UserDoesNotExistException  estudante nao existe
     */
    User getManagerInformation(String login) throws UserDoesNotExistException;

    /**
     * Insere um novo quarto a localizacao introduzida, caso a localizacao
     * ainda nao exista, insere uma nova localizacao e insere o quarto na localizadade
     * referida, enviando excepcao caso o quarto ja exista, caso o gerente nao exista ou o
     * user nao seja um gerente e caso o nome universidade nao seja igual
     * ao nome da localidade do gerente
     *
     * @param code String, codigo do quarto
     * @param loginManager String, login do gerente
     * @param residenceName String, nome do quarto/residencia
     * @param university String, nome da universidade
     * @param local String, localidade
     * @param floor int, piso
     * @param description String, descricao do quarto
     * @throws RoomAlreadyExistsException quarto ja existe
     * @throws UserDoesNotExistException utilizador nao existe
     * @throws NotAuthorizedOperationException operacao nao autorizada
     */
    void insertNewRoom(String code, String loginManager, String residenceName, String university,
                       String local, int floor, String description)
            throws RoomAlreadyExistsException, UserDoesNotExistException, NotAuthorizedOperationException;

    /**
     * Devolve a informacao de um quarto dado o codigo de um quarto, enviando
     * excepcao caso o quarto ja exista
     * @param code String, codigo do quarto
     * @return Room, o quarto
     * @throws RoomDoesNotExistException quarto nao existe
     */
    Room getRoomInformation(String code) throws RoomDoesNotExistException;

    /**
     * Modifica o estado de um quarto com o novo estado dado pelo utilizador, enviando uma excepcao caso o quarto
     * nao exista, caso a operacao nao for permitida ou o quarto tem candidaturas ativas
     * @param code String, o codigo do quarto
     * @param loginManager String, o login do gerente do quarto
     * @param state String, o novo estado para o quarto
     * @throws RoomDoesNotExistException quarto nao existe
     * @throws NotAuthorizedOperationException operacao nao autorizada
     * @throws ActiveCandidacysException quarto com candidaturas ativas
     */
    void modifyRoom(String code, String loginManager, String state)
            throws RoomDoesNotExistException, NotAuthorizedOperationException, ActiveCandidacysException;

    /**
     * Remove um quarto, enviando uma excepcao caso o quarto nao exista, caso a operacao nao for
     * permitida ou o quarto tem candidaturas ativas
     * @param roomCode String, o codigo do quarto
     * @param loginManager String, o login do gerente do quarto
     * @throws RoomDoesNotExistException quarto nao existe
     * @throws NotAuthorizedOperationException operacao nao autorizada
     * @throws ActiveCandidacysException quarto com candidaturas ativas
     */
    void removeRoom(String roomCode, String loginManager)
            throws RoomDoesNotExistException, NotAuthorizedOperationException, ActiveCandidacysException;

    /**
     * Aceita uma candidatura de um estudante e remove todas as outras candidaturas presentes no quarto e remove a
     * candidatura de cada estudante que tenha candidatura nesse quarto, enviando excepcao caso o quarto
     * nao exista ou quando o login do gerente dado nao corresponde ao gerente do quarto ou caso o quarto nao tenha
     * candidaturas ativas ou caso o estudante nao exista ou quando o estudante exista
     * mas nao tem candidatura no quarto
     * @param roomCode String, o codigo do quarto
     * @param managerLogin String, o login do gerente do quarto
     * @param studentLogin String, o login do estudante
     * @throws RoomDoesNotExistException quarto nao existe
     * @throws NotAuthorizedOperationException operacao nao autorizada
     * @throws CandidacyDoesNotExistException candidatura ja existe
     */
    void acceptCandidacy(String roomCode, String managerLogin, String studentLogin)
            throws RoomDoesNotExistException, NotAuthorizedOperationException, CandidacyDoesNotExistException;

    /**
     * Adiciona uma nova candidatura a um estudante, enviando excepcao caso o estudante nao exista ou quando o login
     * dado é de um gerente ou o estudante atingiu o limite de candidaturas ou quando o quarto nao existe
     * ou quando o quarto esta ocupado ou quando o estudante ja tem candidatura neste quarto.
     * @param loginStudent String, o login do estudante
     * @param roomCode String, o codigo do quarto
     * @throws StudentDoesNotExistException estudante nao existente
     * @throws NotAuthorizedOperationException operacao nao autorizada
     * @throws RoomDoesNotExistException quarto nao existe
     * @throws CandidacyAlreadyExistsException candidatura ja existente
     * @throws RoomIsOccupiedException quarto esta ocupado
     */
    void addCandidacy(String loginStudent, String roomCode)
            throws StudentDoesNotExistException, NotAuthorizedOperationException,
            RoomDoesNotExistException, CandidacyAlreadyExistsException, RoomIsOccupiedException;

    /**
     * lista todos os estudantes presentes num quarto, enviando excepcao caso
     * o quarto nao exista ou o login do manager nao seja igual ao login enviado
     * ou caso o quarto nao tenha candidaturas ativas
     * @param roomCode String, codigo do quarto
     * @param loginManager String, login do gerente
     * @return Iterator StudentVisual, devolve um iterador de todos os estudantes que tem candidatura em um quarto
     * @throws RoomDoesNotExistException Quarto nao existente
     * @throws NotAuthorizedOperationException Operacao nao autorizada
     * @throws NoCandidacysForRoomException Quarto nao apresenta qualquer candidatura
     */
    Iterator<Student> listOfCandidacysOfRoom(String roomCode, String loginManager)
            throws RoomDoesNotExistException, NotAuthorizedOperationException, NoCandidacysForRoomException;

    /**
     * Devolve um iterador especial com os quartos presentes numa dada localidade, enviando
     * excepcao caso a localidade nao tenha quartos ou a localidade nao exista ou
     * caso a localidade nao tenha quartos vagos
     * @param location String, localizacao dado pelo utilizador para ver os quartos disponiveis nessa localizacao
     * @return Iterador RoomVisual, devolve um iterador de todos os quartos disponiveis na localizacao dado
     * @throws NoRoomsInLocationException Localizacao sem quartos
     */
    Iterator<Entry<String, Room>> listAllAvailableRooms(String location) throws NoRoomsInLocationException;

    /**
     * Devolve um iterador de quartos com todos os quartos existentes, enviando excepcao
     * caso ainda nao existam quartos
     * @return Iterador RoomVisual, devolve um iterador de todos os quartos no sistema
     * @throws NoRoomsExistException Nao existem quartos no sistema
     */
    Iterator<Room> allRooms() throws NoRoomsExistException;
}
