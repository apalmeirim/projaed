
import dataStructures.Entry;
import dataStructures.Iterator;
import systemManager.*;
import systemManager.exceptions.*;

import java.io.*;
import java.util.Scanner;


/**
 * @author António Palmeirim numero 63667
 * @author Duarte Inácio numero 62397
 *
 * Classe Main - programa principal.
 *
 */
public class Main {

    //Constants
    private static final String EXIT = "XS";
    private static final String EXIT_MESSAGE = "Obrigado. Ate a proxima.\n";
    private static final String ADD_STUDENT = "IE";
    private static final String INFO_STUDENT = "DE";
    private static final String ADD_MANAGER = "IG";
    private static final String INFO_MANAGER = "DG";
    private static final String ADD_ROOM = "IQ";
    private static final String INFO_ROOM = "DQ";
    private static final String MODIFY_ROOM = "MQ";
    private static final String REMOVE_ROOM = "RQ";
    private static final String ADD_APPLICATION = "IC";
    private static final String ACCEPT_APPLICATION = "AC";
    private static final String LIST_APP_ROOM = "LC";
    private static final String LIST_ALL_ROOMS = "LQ";
    private static final String LIST_VACANT_ROOMS = "LL";
    private static final String STUDENT = "estudante";
    private static final String MANAGER = "gerente";
    private static final String NEW_STUDENT_SUCCESS = "Registo de estudante executado.\n";
    private static final String NEW_MANAGER_SUCCESS = "Registo de gerente executado.\n";
    private static final String NEW_CANDIDACY_SUCCESS = "Registo de candidatura executado.\n";
    private static final String NEW_ROOM_SUCCESS = "Registo de quarto executado.\n";
    private static final String ROOM_UPDATED = "Estado de quarto actualizado.\n";
    private static final String CANDIDACY_ACCEPT = "Aceitacao de candidatura executada.\n";
    private static final String ROOM_REMOVED = "Remocao de quarto executada.\n";
    private static final String DATA_FILE = "SystemManager.dat";

    //Exceptions
    private static final String ACTIVE_CANDIDACYS_EXCEPTION = "Candidaturas activas.\n";
    private static final String CANDIDACY_ALREADY_EXISTS_EXCEPTION = "Candidatura existente.\n";
    private static final String CANDIDACY_DOES_NOT_EXIST_EXCEPTION ="Inexistencia da candidatura referida.\n";
    private static final String NO_CANDIDACYS_FOR_ROOM_EXCEPTION ="Inexistencia de candidaturas.\n";
    private static final String NO_ROOM_EXIST_EXCEPTION = "Inexistencia de quartos.\n";
    private static final String NO_ROOMS_IN_LOCATION_EXCEPTION = "Inexistencia de quartos na localidade referida.\n";
    private static final String NOT_AUTHORIZED_OPERATION_EXCEPTION = "Operacao nao autorizada.\n";
    private static final String ROOM_ALREADY_EXISTS_EXCEPTION = "Quarto existente.\n";
    private static final String ROOM_DOES_NOT_EXISTS_EXCEPTION = "Inexistencia do quarto referido.\n";
    private static final String ROOM_IS_OCCUPIED_EXCEPTION = "Quarto ocupado.\n";
    private static final String STUDENT_DOES_NOT_EXIST_EXCEPTION = "Inexistencia do estudante referido.\n";
    private static final String USER_ALREADY_EXISTS_EXCEPTION = "Utilizador ja existente.\n";
    private static final String USER_DOES_NOT_EXIST_EXCEPTION = "Inexistencia do %s referido.\n\n";



    /**
     * Programa principal. Invoca interpretador de comandos.
     *
     * @param args - argumentos para execucao da aplicacao. Nao sao usados neste programa.
     */
    public static void main(String[] args) {
        //Initializing the scanner
        Scanner in = new Scanner(System.in);
        SystemManager s = load();
        //Switch for commands
        String cmd = in.next().toUpperCase();
        while(!cmd.equals(EXIT)) {
            switch(cmd) {
                case ADD_STUDENT:
                    addStudent(in, s);
                    break;
                case INFO_STUDENT:
                    studentInformation(in, s);
                    break;
                case ADD_MANAGER:
                    addManager(in, s);
                    break;
                case INFO_MANAGER:
                    managerInformation(in, s);
                    break;
                case ADD_ROOM:
                    insertNewRoom(in, s);
                    break;
                case INFO_ROOM:
                    roomInformation(in, s);
                    break;
                case MODIFY_ROOM:
                    modifyRoom(in, s);
                    break;
                case REMOVE_ROOM:
                    removeRoom(in, s);
                    break;
                case ADD_APPLICATION:
                    addCandidacy(in, s);
                    break;
                case ACCEPT_APPLICATION:
                    acceptCandidacy(in, s);
                    break;
                case LIST_APP_ROOM:
                    listCandidacys(in, s);
                    break;
                case LIST_ALL_ROOMS:
                    listAllRooms(s);
                    break;
                case LIST_VACANT_ROOMS:
                    listAllAvailableRooms(in, s);
                    break;
                default:
                    break;
            }
            cmd = in.next().toUpperCase();
        }
        save(s);
        System.out.println(EXIT_MESSAGE);
    }

    /**
     * Guarda o estado do programa quando o utilizador executa o comando para sair.
     * @param s SystemManager
     */
    private static void save(SystemManager s) {
        try{
            ObjectOutputStream streamOutput = new ObjectOutputStream(new FileOutputStream(DATA_FILE));
            streamOutput.writeObject(s);
            streamOutput.close();
        }catch(IOException e) {
        }
    }

    /**
     * Carrega o estado do programa guardado em memória quando o programa é executado
     */
    @SuppressWarnings("unchecked")
    private static SystemManager load() {
        SystemManager loadedSystem;
        try{
            ObjectInputStream streamInput = new ObjectInputStream(new FileInputStream(DATA_FILE));
            loadedSystem = (SystemManager) streamInput.readObject();
            streamInput.close();
            return loadedSystem;
        }catch(IOException | ClassNotFoundException e){
            return new SystemManagerClass();
        }
    }

    /**
     * Adiciona um novo estudante ao programa, com o login, nome, idade, localidade e universidade dado pelo utilzador
     * @param in scanner para ler informação
     * @param s SystemManager
     */
    private static void addStudent(Scanner in, SystemManager s)  {
        String login = in.next();
        String nameOfStudent = in.nextLine().trim();
        int age = in.nextInt();
        String local = in.nextLine().trim();
        String university = in.nextLine();
        try  {
            s.newStudent(login, nameOfStudent, age, local, university);
            System.out.println(NEW_STUDENT_SUCCESS);
        } catch (UserAlreadyExistsException e) {
            System.out.println(USER_ALREADY_EXISTS_EXCEPTION);
        }
    }

    /**
     * Devolve a informação de um estudante, com o login dado pelo utilizador
     * @param in scanner para ler informação
     * @param s SystemManager
     */
    private static void studentInformation(Scanner in, SystemManager s) {
        String login = in.next();
        try {
            Student student = (Student) s.getStudentInformation(login);
            System.out.printf("%s %s %d\n", student.getLogin(), student.getName(), student.getAge());
            System.out.println(student.getLocal());
            System.out.println(student.getUniversity());
            System.out.println();
        } catch (UserDoesNotExistException e) {
            System.out.printf(USER_DOES_NOT_EXIST_EXCEPTION, STUDENT);
        }
    }

    /**
     * Adiciona um novo gerente ao programa, com o login, nome e universidade dado pelo utilzador
     * @param in scanner para ler informação
     * @param s SystemManager
     */
    private static void addManager(Scanner in, SystemManager s) {
        String login = in.next();
        String nameOfManager = in.nextLine().trim();
        String university = in.nextLine().trim();
        try {
            s.newManager(login, nameOfManager, university);
            System.out.println(NEW_MANAGER_SUCCESS);
        } catch (UserAlreadyExistsException e) {
            System.out.println(USER_ALREADY_EXISTS_EXCEPTION);
        }
    }

    /**
     * Devolve a informação de um gerente, com o login dado pelo utilizador
     * @param in scanner para ler informação
     * @param s SystemManager
     */
    private static void managerInformation(Scanner in, SystemManager s){
        String login = in.next();
        try{
            Manager manager = (Manager) s.getManagerInformation(login);
            System.out.printf("%s %s\n", manager.getLogin(), manager.getName());
            System.out.println(manager.getUniversity());
            System.out.println();
        } catch (UserDoesNotExistException e) {
            System.out.printf(USER_DOES_NOT_EXIST_EXCEPTION, MANAGER);
        }
    }

    /**
     * Adiciona um novo quarto ao programa, com o codigo, login do gerente, nome da residencia, universidade e
     * localidade dado pelo utilzador
     * @param in scanner para ler informacao
     * @param s SystemManager
     */
    private static void insertNewRoom(Scanner in, SystemManager s){
        String code = in.next();
        String loginManager = in.nextLine().trim();
        String residenceName = in.nextLine().trim();
        String university = in.nextLine().trim();
        String local = in.nextLine().trim();
        int floor = in.nextInt(); in.nextLine();
        String description = in.nextLine().trim();
        try{
            s.insertNewRoom(code, loginManager, residenceName, university, local, floor, description);
            System.out.println(NEW_ROOM_SUCCESS);
        } catch (RoomAlreadyExistsException e) {
            System.out.println(ROOM_ALREADY_EXISTS_EXCEPTION);
        } catch (UserDoesNotExistException e) {
            System.out.printf(USER_DOES_NOT_EXIST_EXCEPTION, MANAGER);
        } catch (NotAuthorizedOperationException e) {
            System.out.println(NOT_AUTHORIZED_OPERATION_EXCEPTION);
        }
    }

    /**
     * Devolve a informação de um room, com o code do room dado pelo utilizador
     * @param in scanner para ler informação
     * @param s SystemManager
     */
    private static void roomInformation(Scanner in ,SystemManager s){
        String code = in.nextLine().trim();
        try{
            Room room = s.getRoomInformation(code);
            System.out.printf("%s %s\n", room.getCode(),  room.getResidenceName());
            System.out.println(room.getUniversity());
            System.out.println(room.getLocal());
            System.out.println(room.getFloor());
            System.out.println(room.getDescription());
            System.out.println(room.getStateOfRoom());
            System.out.println();
        } catch (RoomDoesNotExistException e) {
            System.out.println(ROOM_DOES_NOT_EXISTS_EXCEPTION);
        }
    }

    /**
     * Modifica o estado de um quarto, com o codigo do quarto, o codigo do gerente e o novo estado enviado pelo utilizador
     * @param in scanner para ler informação
     * @param s SystemManager
     */
    private static void modifyRoom(Scanner in, SystemManager s) {
        String roomCode = in.next();
        String managerCode = in.nextLine().trim();
        String state = in.next();
        try {
            s.modifyRoom(roomCode, managerCode, state);
            System.out.println(ROOM_UPDATED);
        } catch(RoomDoesNotExistException e) {
            System.out.println(ROOM_DOES_NOT_EXISTS_EXCEPTION);
        } catch (NotAuthorizedOperationException e) {
            System.out.println(NOT_AUTHORIZED_OPERATION_EXCEPTION);
        } catch(ActiveCandidacysException e) {
            System.out.println(ACTIVE_CANDIDACYS_EXCEPTION);
        }
    }

    /**
     * Remove o quarto com o codigo e o login do gerente dado pelo utilizador
     * @param in scanner para ler informacao
     * @param s SystemManager
     */
    private static void removeRoom(Scanner in, SystemManager s){
        try{
            String roomCode = in.next();
            String loginManager = in.nextLine().trim();
            s.removeRoom(roomCode, loginManager);
            System.out.println(ROOM_REMOVED);
        } catch(RoomDoesNotExistException e) {
            System.out.println(ROOM_DOES_NOT_EXISTS_EXCEPTION);
        } catch (NotAuthorizedOperationException e) {
            System.out.println(NOT_AUTHORIZED_OPERATION_EXCEPTION);
        } catch(ActiveCandidacysException e) {
            System.out.println(ACTIVE_CANDIDACYS_EXCEPTION);
        }
    }

    /**
     * Adiciona uma nova candidacy a um room com o login do estudante e o codigo do quarto enviados pelo utilizador
     * @param in scanner para ler informacao
     * @param s SystemManager
     */
    private static void addCandidacy(Scanner in, SystemManager s) {
        String loginStudent = in.next();
        String roomCode = in.nextLine().trim();
        try {
            s.addCandidacy(loginStudent, roomCode);
            System.out.println(NEW_CANDIDACY_SUCCESS);
        } catch (StudentDoesNotExistException e) {
            System.out.println(STUDENT_DOES_NOT_EXIST_EXCEPTION);
        } catch (NotAuthorizedOperationException e) {
            System.out.println(NOT_AUTHORIZED_OPERATION_EXCEPTION);
        } catch (RoomDoesNotExistException e) {
            System.out.println(ROOM_DOES_NOT_EXISTS_EXCEPTION);
        } catch (RoomIsOccupiedException e) {
            System.out.println(ROOM_IS_OCCUPIED_EXCEPTION);
        } catch (CandidacyAlreadyExistsException e) {
            System.out.println(CANDIDACY_ALREADY_EXISTS_EXCEPTION);
        }
    }


    /**
     * Aceita uma candidatura dado o codigo do quarto, o login do gerente e o login do estudante dado pelo utilizador
     * @param in scanner para ler informacao
     * @param s SystemManager
     */
    private static void acceptCandidacy(Scanner in, SystemManager s){
        String roomCode = in.next();
        String managerLogin = in.next();
        String studentLogin = in.nextLine().trim();
        try{
            s.acceptCandidacy(roomCode, managerLogin, studentLogin);
            System.out.println(CANDIDACY_ACCEPT);
        } catch (RoomDoesNotExistException e) {
            System.out.println(ROOM_DOES_NOT_EXISTS_EXCEPTION);
        } catch (NotAuthorizedOperationException e) {
            System.out.println(NOT_AUTHORIZED_OPERATION_EXCEPTION);
        } catch (CandidacyDoesNotExistException e) {
            System.out.println(CANDIDACY_DOES_NOT_EXIST_EXCEPTION);
        }
    }


    /**
     * Lista todas as candidaturas de um quarto dado o codigo do quanto e o codigo do gerente enviados pelo utilizador
     * @param in scanner para ler informacao
     * @param s SystemManager
     */
    private static void listCandidacys(Scanner in, SystemManager s) {
        String roomCode = in.next();
        String managerCode = in.nextLine().trim();
        try {
            Iterator<Student> listCandidacys = s.listOfCandidacysOfRoom(roomCode, managerCode);
            while(listCandidacys.hasNext()) {
                Student student = listCandidacys.next();
                System.out.printf("%s %s %s\n", student.getLogin(), student.getName(), student.getUniversity());
            }
            System.out.println();
        } catch (RoomDoesNotExistException e) {
            System.out.println(ROOM_DOES_NOT_EXISTS_EXCEPTION);
        } catch (NotAuthorizedOperationException e) {
            System.out.println(NOT_AUTHORIZED_OPERATION_EXCEPTION);
        } catch (NoCandidacysForRoomException e) {
            System.out.println(NO_CANDIDACYS_FOR_ROOM_EXCEPTION);
        }
    }

    /**
     * Lista todos os quartos no sistema e toda a informacao sobre os quartos
     * Esta copl
     * @param s SystemManager
     */
    private static void listAllRooms(SystemManager s) {
        try {
            Iterator<Room> it = s.allRooms();
            while(it.hasNext()) {
                Room room = it.next();
                System.out.printf("%s %s\n", room.getLocal(), room.getCode());
                System.out.println(room.getUniversity());
                System.out.println(room.getResidenceName());
                System.out.println();
            }
        } catch(NoRoomsExistException e) {
            System.out.println(NO_ROOM_EXIST_EXCEPTION);
        }
    }


    /**
     * Lista todos os quartos disponiveis na localizacao dado pelo utilizador e toda a informacao sobre os quartos
     * @param in scanner para ler informacao
     * @param s SystemManager
     */
    private static void listAllAvailableRooms(Scanner in, SystemManager s) {
        String location = in.nextLine().trim();
        try{
            Iterator<Entry<String, Room>> it = s.listAllAvailableRooms(location);
            while (it.hasNext()) {
                Room room = it.next().getValue();
                if(!room.roomIsOccupied()) {
                    System.out.printf("%s %s\n", room.getLocal(), room.getCode());
                    System.out.println(room.getUniversity());
                    System.out.println(room.getResidenceName());
                    System.out.println();
                }
            }
        } catch(NoRoomsInLocationException e) {
            System.out.println(NO_ROOMS_IN_LOCATION_EXCEPTION);
        }
    }

}
