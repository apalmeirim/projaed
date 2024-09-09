package systemManager;
import dataStructures.DoubleList;
import dataStructures.Iterator;
import dataStructures.List;

import java.io.Serializable;

public class StudentClass extends UserClass implements StudentEdit, Serializable {

    private int age;
    private String local;
    private List<RoomEdit> candidacys;


    public StudentClass (String login, String name, int age, String local, String university) {
        super(login, name, university);
        this.age = age;
        this.local = local;
        this.candidacys = new DoubleList<>();
    }

    public int getAge(){
        return this.age;
    }

    public String getLocal(){
        return this.local;
    }

    public void removeCandidacy(RoomEdit room){
        this.candidacys.remove(room);
    }

    public void removeAllCandidacys() {
        this.candidacys = new DoubleList<>();
    }

    public void addCandidacy(RoomEdit room) {
        this.candidacys.addLast(room);
    }

    public boolean hasFullCandidacys() {
        return candidacys.size() == 10;
    }

    public Iterator<RoomEdit> getCandidacys() {
        return candidacys.iterator();
    }

}
