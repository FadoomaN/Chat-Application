package Entity;

import javax.swing.*;
import java.io.Serializable;
import java.util.HashMap;

//This method creates and user object and implements serializable.

public class User implements Serializable {

    private String userName;
    private ImageIcon image;
    private boolean connected;
    private HashMap<String, User> friendList;

    //Creates a new user with a username, image, and sets them as online with an empty friendList.
    public User(String userName, String imagePath) {
        this.userName = userName;
        image = new ImageIcon(imagePath);
        connected = true;
        friendList = new HashMap<>();
    }

    //This method retrieves the user's username.
    public String getUserName() {
        return userName;
    }


    //This method retrieves the user's image.
    public ImageIcon getImage() {
        return image;
    }


    //This method checks if the user is currently connected.
    public boolean getConnected() {
        return connected;
    }


    //This method updates the user's connectivity status.
    public void setConnected(boolean connected) {
        this.connected = connected;
    }


    //This method retrieves the user's friendList.
    public HashMap<String, User> getFriendList() {
        return friendList;
    }


    //This method simply adds a new friend to the user's friendList using their username.
    public void addFriend(String userName, User newFriend) {
        friendList.put(userName, newFriend);
    }
}
