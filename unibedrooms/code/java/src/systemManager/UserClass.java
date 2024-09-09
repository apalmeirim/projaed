package systemManager;

import java.io.Serializable;

public abstract class UserClass implements User, Serializable {

    String login, name, university;


    public UserClass(String login, String name, String university) {
        this.login = login;
        this.name = name;
        this.university = university;
    }

    public String getLogin(){
        return this.login;
    }

    public String getName() {
        return name;
    }

    public String getUniversity() {
        return university;
    }


}
