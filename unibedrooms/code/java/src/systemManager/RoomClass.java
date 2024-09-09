package systemManager;

import dataStructures.DoubleList;
import dataStructures.Iterator;
import dataStructures.List;

import java.io.Serializable;

public class RoomClass implements RoomEdit, Serializable {

    public enum state{
        livre, ocupado
    }
    private String code, loginManager, residenceName, university, local, description;
    private int floor;

    private String stateOfRoom;
    private List<Student> candidacys;


    public RoomClass(String code, String loginManager, String residenceName, String university, String local, int floor, String description) {
        this.code = code;
        this.loginManager = loginManager;
        this.residenceName = residenceName;
        this.university = university;
        this.local = local;
        this.description = description;
        this.floor = floor;
        this.stateOfRoom = String.valueOf(state.livre);
        this.candidacys = new DoubleList<>();
    }

    public void modifyState(String state) {
        stateOfRoom = state;
    }

    public boolean studentHasCandidacy(Student student) {
        return (candidacys.find(student) != -1);
    }

    public String getCode() {
        return code;
    }

    public void addCandidacy(Student student) {
        candidacys.addLast(student);
    }

    public void removeCandidacy(Student student){
        candidacys.remove(student);
    }

    public boolean roomIsOccupied() {
        return stateOfRoom.equals(String.valueOf(state.ocupado));
    }

    public String getResidenceName(){
        return this.residenceName;
    }

    public String getUniversity(){
        return this.university;
    }

    public String getLocal(){
        return this.local;
    }

    public int getFloor(){
        return this.floor;
    }

    public String getDescription(){
        return this.description;
    }

    public String getStateOfRoom(){
        return this.stateOfRoom;
    }

    public String getManagerLogin() {
        return loginManager;
    }

    public boolean hasActiveCandidacys() {
        return !candidacys.isEmpty();
    }

    public Iterator<Student> getCandidacys(){
        return candidacys.iterator();
    }

    public void removeAllCandidacys(){
        this.candidacys = new DoubleList<>();
    }

}
