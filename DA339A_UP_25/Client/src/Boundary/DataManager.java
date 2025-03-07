package Boundary;

import java.io.*;

//This class manages all the users by writing it down to the disc.
public class DataManager {

    //This method is used to save every object to the txt file.
    public void saveObject(Object object) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data.txt"));
            oos.writeObject(object);
            oos.reset();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    //This method is used to get the object from the txt file.
    public Object getObject() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data.txt"));
            Object object = ois.readObject();
            ois.close();
            return object;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
