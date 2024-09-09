package systemManager;

import dataStructures.*;
import systemManager.exceptions.*;


import java.io.Serializable;


public class SystemManagerClass implements SystemManager, Serializable {


    private static final int USERS_CAPACITY = 100;
    private static final int ROOMSCODE_CAPACITY = 100;
    private Dictionary<String, Student> students;
    private Dictionary<String, Manager> managers;
    private Dictionary<String, RoomEdit> roomCodes;
    private OrderedDictionary<String, OrderedDictionary<String, Room>> roomsByLocation;

    public SystemManagerClass() {
        this.students = new SepChainHashTable<>(USERS_CAPACITY);
        this.managers = new SepChainHashTable<>(USERS_CAPACITY);
        this.roomCodes = new SepChainHashTable<>(ROOMSCODE_CAPACITY);
        this.roomsByLocation = new AVLTree<>();
    }


    public void newStudent(String login, String name, int age, String local, String university) throws UserAlreadyExistsException {
        if (!userExists(login)) {
            students.insert(login.toLowerCase(), new StudentClass(login, name, age, local, university));
        } else throw new UserAlreadyExistsException();
    }


    public void newManager(String login, String name, String university) throws UserAlreadyExistsException {
        if (!userExists(login)) {
            managers.insert(login.toLowerCase(), new ManagerClass(login, name, university));
        } else throw new UserAlreadyExistsException();
    }


    public Student getStudentInformation(String login) throws UserDoesNotExistException {
        Student student = getStudent(login);
        if (student != null) {
            return getStudent(login);
        } else throw new UserDoesNotExistException();
    }


    public Manager getManagerInformation(String login) throws UserDoesNotExistException {
        Manager manager = getManager(login) ;
        if (manager != null) {
            return managers.find(login.toLowerCase());
        } else throw new UserDoesNotExistException();
    }


    public void insertNewRoom(String code, String loginManager, String residenceName, String university, String local, int floor, String description) throws RoomAlreadyExistsException, UserDoesNotExistException, NotAuthorizedOperationException {
        if (roomExists(code)) throw new RoomAlreadyExistsException();
        if (!(managerExists(loginManager))) throw new UserDoesNotExistException();
        if (!university.equals(getManager(loginManager).getUniversity())) throw new NotAuthorizedOperationException();
        else{
            RoomEdit room = new RoomClass(code, loginManager, residenceName, university, local, floor, description);
            if(roomsByLocation.find(local.toLowerCase()) == null) {
                roomsByLocation.insert(local.toLowerCase(), new AVLTree<>());
            }
            roomsByLocation.find(local.toLowerCase()).insert(code.toLowerCase(), room);
            roomCodes.insert(code.toLowerCase(), room);
        }
    }


    public Room getRoomInformation(String code) throws RoomDoesNotExistException{
        Room room = getRoom(code);
        if(room == null) throw new RoomDoesNotExistException();
        return room;
    }

    public void modifyRoom(String code, String loginManager, String state) throws RoomDoesNotExistException, NotAuthorizedOperationException, ActiveCandidacysException{
        RoomEdit room = getRoom(code);
        if(room != null) {
            if(!room.getManagerLogin().equalsIgnoreCase(loginManager)) {
                throw new NotAuthorizedOperationException();
            }
            if(room.hasActiveCandidacys() && state.equals(String.valueOf(RoomClass.state.ocupado))) {
                throw new ActiveCandidacysException();
            }
            room.modifyState(state);
        }
        else throw new RoomDoesNotExistException();
    }

    public void removeRoom(String roomCode, String loginManager)throws RoomDoesNotExistException, NotAuthorizedOperationException, ActiveCandidacysException{
        RoomEdit room = getRoom(roomCode);
        if(room != null){
            if(!room.getManagerLogin().equalsIgnoreCase(loginManager.toLowerCase())){
                throw new NotAuthorizedOperationException();
            }
            if(room.hasActiveCandidacys()) {
                throw new ActiveCandidacysException();
            }
            roomsByLocation.find(room.getLocal().toLowerCase()).remove(roomCode.toLowerCase());
            roomCodes.remove(roomCode.toLowerCase());
        }else throw new RoomDoesNotExistException();
    }


    public void addCandidacy(String loginStudent, String roomCode) throws StudentDoesNotExistException, NotAuthorizedOperationException, RoomDoesNotExistException, CandidacyAlreadyExistsException, RoomIsOccupiedException {
        StudentEdit user = (StudentEdit) getStudent(loginStudent);
        if(user == null) throw new StudentDoesNotExistException();
            if (user.hasFullCandidacys()) {
                throw new NotAuthorizedOperationException();
            }
            if (!roomExists(roomCode)) {
                throw new RoomDoesNotExistException();
            }
            RoomEdit room = getRoom(roomCode);
            if (room.roomIsOccupied()) {
                throw new RoomIsOccupiedException();
            }
            if (room.studentHasCandidacy(user)){
                throw new CandidacyAlreadyExistsException();
            }
            room.addCandidacy(user);
            user.addCandidacy(room);
    }


    public void acceptCandidacy(String roomCode, String loginManager, String loginStudent) throws RoomDoesNotExistException, NotAuthorizedOperationException, CandidacyDoesNotExistException {
        RoomEdit room = getRoom(roomCode);
        if (room == null) throw new RoomDoesNotExistException();
        if (!loginManager.equalsIgnoreCase(room.getManagerLogin())) throw new NotAuthorizedOperationException();
        Student student = getStudent(loginStudent);
        if (!room.hasActiveCandidacys() || student == null || !(room.studentHasCandidacy(student)))  {
            throw new CandidacyDoesNotExistException();
        }
        room.modifyState(String.valueOf(RoomClass.state.ocupado));
        Iterator<Student> candidacys = room.getCandidacys();
        while (candidacys.hasNext()) {
            StudentEdit tempStudent = (StudentEdit) candidacys.next();
            if (tempStudent.getLogin().equalsIgnoreCase(loginStudent)) {
                Iterator<RoomEdit> it2 = student.getCandidacys();
                while(it2.hasNext())
                    it2.next().removeCandidacy(student);
                tempStudent.removeAllCandidacys();
            } else tempStudent.removeCandidacy(room);
            room.removeAllCandidacys();
        }
    }

    public Iterator<Student> listOfCandidacysOfRoom(String roomCode, String loginManager) throws RoomDoesNotExistException, NotAuthorizedOperationException, NoCandidacysForRoomException {
        RoomEdit room = getRoom(roomCode);
        if(!roomExists(roomCode)) throw new RoomDoesNotExistException();
        if(!(room.getManagerLogin().equalsIgnoreCase(loginManager))) throw new NotAuthorizedOperationException();
        if(!(room.hasActiveCandidacys())) throw new NoCandidacysForRoomException();
        return room.getCandidacys();
    }

    /**
     * Metodo auxiliar para verfiicar a existencia de um quarto no sistema
     * @param code codigo do quarto para verificar
     * @return true se o quarto existir, false caso contrario
     */
    private boolean roomExists(String code) {
        return (roomCodes.find(code.toLowerCase()) != null);
    }

    /**
     * Metodo auxiliar para verificar a existencia de um utilizador no sistema
     * @param code codigo do utilizador para verificar
     * @return true se o utilizador existir, false caso contrario
     */
    private boolean userExists(String code) {
        return (studentExists(code.toLowerCase()) || managerExists(code.toLowerCase()));
    }

    /**
     * Metodo auxiliar para verificar se o estudante existe
     * @param code codigo do estudante para verificar
     * @return true se o estudante existir, false caso contrario
     */
    private boolean studentExists(String code) {
        return (students.find(code.toLowerCase()) != null);
    }

    /**
     * Metodo auxiliar para verificar se o gerente existe
     * @param code codigo do gerente para verificar
     * @return true se o gerente existir, false caso contrario
     */
    private boolean managerExists(String code) {
        return (managers.find(code.toLowerCase()) != null);
    }

    /**
     * Metodo auxiliar para buscar o estudante com o codigo associado
     * @param code codigo do estudante para buscar
     * @return o estudante com o codigo associado
     */
    private Student getStudent(String code) {
        return students.find(code.toLowerCase());
    }

    /**
     * Metodo auxiliar para buscar o gerente com o codigo associado
     * @param code codigo do gerente para buscar
     * @return o gerente com o codigo associado
     */
    private Manager getManager(String code) {
        return managers.find(code.toLowerCase());
    }

    /**
     * Metodo auxiliar para buscar o quarto com o codigo associado
     * @param code codigo do quarto para buscar
     * @return o quarto com o codigo associado
     */
    private RoomEdit getRoom(String code) {
        return roomCodes.find(code.toLowerCase());
    }


    public Iterator<Room> allRooms() throws NoRoomsExistException{
        if(roomCodes.isEmpty()) throw new NoRoomsExistException();
        return new SpecialAVLTreeIterator<>(roomsByLocation, roomCodes.size());
    }

    public Iterator<Entry<String, Room>> listAllAvailableRooms(String location) throws NoRoomsInLocationException{
        OrderedDictionary<String, Room> avl = roomsByLocation.find(location.toLowerCase());
        if( avl == null || avl.isEmpty() || !hasFreeRoom(location.toLowerCase())) throw new NoRoomsInLocationException();
        return avl.iterator();
    }

    /**
     * Metodo auxiliar para verificar se a localidade tem quartos vagos
     * @param location localidade para verificar
     * @return true se a localidade tive quartos vagos, false caso contrario
     */
    private boolean hasFreeRoom(String location){
        Iterator<Entry<String, Room>> it = roomsByLocation.find(location).iterator();
        while(it.hasNext()){
            if(!it.next().getValue().roomIsOccupied()) return true;
        }
        return false;
    }

}
