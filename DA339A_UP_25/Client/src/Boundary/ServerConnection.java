package Boundary;

import java.io.*;
import java.net.Socket;

//This class is used to connect with server program and sending object over.
public class ServerConnection {

    private ObjectOutputStream oos;
    private ObjectInputStream ois;


    //This method tries to initiate the input/output stream.
    public ServerConnection(Socket socket) {
        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //This method is used to send objects over to the server.
    public void sendMessage(Object object) {
        try {
            oos.writeObject(object);
            oos.reset();
            System.out.println();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //This method is used to read the object from the server.
    public Object readMessage() {
        try {
            Object object = ois.readObject();
            return object;
        } catch (IOException e) {
            return null;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    //This method gets the ObjectOutputStream.
    public ObjectOutputStream getOos() {
        return oos;
    }

    //This method gets the ObjectInputStream.
    public ObjectInputStream getOis() {
        return ois;
    }

}
