package Control;

import Boundary.DataManager;
import Boundary.MainFrame;
import Boundary.ServerConnection;
import Entity.ClientUpdate;
import Entity.TextMessage;
import Entity.User;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

//This class handles the flow, communication and logic between boundary and entity packages.

public class Client extends Thread {

    private User user;
    private Socket socket;
    private ServerConnection serverConnection;
    private DataManager dataManager;
    private HashMap<String, User> userList;
    private MainFrame mainFrame;
    private String filePath;

    //Sets up GUI, checks if user is new and if not asks for a profile pic, sends update message to server
    //and starts thread
    public Client() {
        try {
            mainFrame = new MainFrame(this);
            String userName = mainFrame.requestUserName();
            dataManager = new DataManager();
            boolean exists = readData(userName);
            if (!exists) {
                String imagePath = mainFrame.profilePicChooser();
                user = new User(userName, imagePath);
            }
            socket = new Socket("192.168.0.108", 4455);
            serverConnection = new ServerConnection(socket);
            serverConnection.sendMessage(new ClientUpdate(user));
            Read read = new Read();
            read.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    //Sends new list of online users to GUI
    private void setUserList(HashMap<String, User> userList)
    {
        if (this.userList == null) {
            for (HashMap.Entry<String, User> entry : userList.entrySet()) {
                mainFrame.getSidePanel().addUserToAllUsers(entry.getValue().getImage(), entry.getValue().getUserName());
            }
        } else {
            for (HashMap.Entry<String, User> entry : userList.entrySet()) {
                if (!this.userList.containsKey(entry.getKey())) {
                    mainFrame.getSidePanel().addUserToAllUsers(entry.getValue().getImage(), entry.getValue().getUserName());
                }
            }
        }

    }

    //Sends list of friends to GUI
    private void setFriendList(HashMap<String, User> friendList) {
        for (HashMap.Entry<String, User> entry : friendList.entrySet()) {
            mainFrame.getSidePanel().addUserToFriends(entry.getValue().getUserName(), entry.getValue().getImage());
        }
    }

    //Sends update message to server, closes all streams and socket and saves user and exits the program
    public void disconnect() {
        try {
            user.setConnected(false);
            serverConnection.sendMessage(new ClientUpdate(user));
            serverConnection.getOos().close();
            serverConnection.getOis().close();
            socket.close();
            saveData();
            System.exit(0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Checks if user already exists and overwrites the save otherwise adds user to list and saves list of users
    private void saveData() {
        HashMap<String, User> savedUsers = new HashMap<>();
        boolean exists = false;
        File file = new File("data.txt");
        if (!file.isFile()) {
            savedUsers.put(user.getUserName(), user);
            exists = true;
        } else {
            Object object = dataManager.getObject();
            savedUsers = (HashMap<String, User>) object;
            if (savedUsers.containsKey(user.getUserName())) {
                savedUsers.replace(user.getUserName(), savedUsers.get(user.getUserName()), user);
                exists = true;
            }
        }
        if (!exists) {
            savedUsers.put(user.getUserName(), user);
        }
        dataManager.saveObject(savedUsers);
    }

    //Checks if user already exists in saves list of users or is new and returns the boolean
    private boolean readData(String userName) {
        HashMap<String, User> savedUsers;
        boolean exists = false;
        File file = new File("data.txt");
        if (!file.isFile()) {
            return exists;
        } else {
            savedUsers = (HashMap<String, User>) dataManager.getObject();
            if (savedUsers.containsKey(userName)) {
                user = savedUsers.get(userName);
                user.setConnected(true);
                setFriendList(user.getFriendList());
                exists = true;
            }
        }
        return exists;
    }

    //Sends textmessage to server
    public void sendTextMessage(String users) {
        HashMap<String, User> receivers = new HashMap<>();
        String[] tempRecArray = users.split("/");
        for (int i = 0; i < tempRecArray.length; i++) {
            if(userList.containsKey(tempRecArray[i])) {
                receivers.put(tempRecArray[i], userList.get(tempRecArray[i]));
            }
        }
        String text = mainFrame.getChatPanel().getTextFromMessageField();
        TextMessage message = new TextMessage(text, filePath, user, receivers);
        filePath = "";
        serverConnection.sendMessage(message);
    }

    //Sets filepath of image chosen from filechooser
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    //Adds user to friends
    public void addFriend(String userName) {
        if (!user.getFriendList().containsKey(userName) && !userName.equals(user.getUserName())) {
            User newFriend = userList.get(userName);
            user.addFriend(userName, newFriend);
            mainFrame.getSidePanel().addUserToFriends(newFriend.getUserName(), newFriend.getImage());
        } else if (userName.equals(user.getUserName())) {
            mainFrame.showMessage("Cannot add yourself");
        } else {
            mainFrame.showMessage("User is already your friend");
        }
    }

    //Starts the client program
    public static void main(String[] args) {
        Client client = new Client();
    }

    //Class is a thread that constantly reads from server and forwards messages to GUI
    public class Read extends Thread {

        @Override
        public void run() {
            while (true) {
                Object object = serverConnection.readMessage();
                if (object instanceof TextMessage message) {
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
                    LocalDateTime ldt = LocalDateTime.now();
                    String clientTime = dtf.format(ldt);
                    message.setClientTime(clientTime);
                    mainFrame.getChatPanel().addMessageToChat(message.getSender().getUserName(), message.getSender().getImage(), message.getText(), message.getClientTime(), message.getImage());
                } else if (object instanceof ClientUpdate cu) {
                    setUserList(cu.getUserList());
                    userList = cu.getUserList();
                    if (!cu.getUser().getConnected()) {
                        mainFrame.getSidePanel().removeFromAllUsers(cu.getUser().getUserName());
                    }
                } else {
                    break;
                }
            }
        }

    }

}
