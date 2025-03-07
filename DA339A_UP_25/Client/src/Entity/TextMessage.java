package Entity;

import javax.swing.*;
import java.util.HashMap;

//This class implements interface (Message).

public class TextMessage implements Message {

    private String text;
    private ImageIcon image;
    private User sender;
    private HashMap<String, User> receivers;
    private String serverTime;
    private String clientTime;

    //Sets text, image (if not null), sender and list of receivers
    public TextMessage(String text, String imagePath, User sender, HashMap<String, User> receivers) {
        this.text = text;
        if (imagePath != null && !imagePath.isEmpty()) {
            image = new ImageIcon(imagePath);
        } else {
            image = null;
        }
        this.sender = sender;
        this.receivers = receivers;
    }

    //This method gets the text message
    public String getText() {
        return text;
    }

    //This method gets the image.
    public ImageIcon getImage() {
        return image;
    }

    //This method gets the sender.
    public User getSender() {
        return sender;
    }

    //This method gets the time.
    public String getServerTime() {
        return serverTime;
    }

    //This method sets the time.
    public void setServerTime(String serverTime) {
        this.serverTime = serverTime;
    }

    //This method get the clients time
    public String getClientTime() {
        return clientTime;
    }

    //Sets client time
    public void setClientTime(String clientTime) {
        this.clientTime = clientTime;
    }

    //Returns list of receivers
    public HashMap<String, User> getReceivers() {
        return receivers;
    }

}
