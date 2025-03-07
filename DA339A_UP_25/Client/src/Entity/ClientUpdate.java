package Entity;

import java.util.HashMap;

//This class implements Interface
public class ClientUpdate implements Message {

    private User user;
    private HashMap<String, User> userList;

    //Sets user
    public ClientUpdate(User user) {
        this.user = user;
    }

    //Returns user
    public User getUser() {
        return user;
    }

    //Returns list of users
    public HashMap<String, User> getUserList() {
        return userList;
    }

    //Sets list of users
    public void setUserList(HashMap<String, User> userList) {
        this.userList = userList;
    }

}
