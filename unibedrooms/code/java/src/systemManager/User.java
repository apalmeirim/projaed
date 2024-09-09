package systemManager;

import java.io.Serializable;

/**
 * @author António Palmeirim numero 63667
 * @author Duarte Inácio numero 62397
 *
 * Interface que representa um utilizador no sistema e contem todos os metodos de consulta.
 *
 */

public interface User extends Serializable {

    /**
     * Devolve o login do utilizador.
     * @return String, Login do User
     */
    String getLogin();

    /**
     * Devolve o nome do utilizador.
     * @return String, Nome do User
     */
    String getName();

    /**
     * Devolve o nome da universidade do utilizador
     * @return String, Universidade do User
     */
    String getUniversity();

}
