package Boundary;

import javax.swing.*;

import Control.*;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

//This class creates the JFrame, shows message on it and sets all the panels/labes etc on it.
public class MainFrame extends JFrame {

    private Client client;
    private ChatPanel chatPanel;
    private SidePanel sidePanel;

    //This constructor initiates a mainframe and start a method startFrame.
    public MainFrame(Client client) {
        this.client = client;
        startFrame();
    }

    //This method creates the mainframe and creates other panels/icons on it.
    public void startFrame() {
        setTitle("WishCord");
        ImageIcon img = new ImageIcon("src/View/images/Discord-logo.png");
        setIconImage(img.getImage());
        setBounds(200, 200, 1000, 700);
        chatPanel = new ChatPanel(this, client);
        sidePanel = new SidePanel(this, client);
        setLayout(null);
        setResizable(false);
        setVisible(true);
        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
                client.disconnect();
                System.exit(0);
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
        repaint();
    }

    //This method is used to get the name of the user when the program starts.
    public String requestUserName()
    {
        String userName = JOptionPane.showInputDialog(this, "Enter username");
        return userName;
    }

    //This method uses JFileChooser to ask the user to select a profilepicture to use in the chatroom.
    public String profilePicChooser()
    {
        String imagePath = null;
        JFileChooser fileChooser = new JFileChooser();
        int response = fileChooser.showOpenDialog(null);
        System.out.println("klar1?");
        if(response == JFileChooser.APPROVE_OPTION)
        {
            imagePath = fileChooser.getSelectedFile().getAbsolutePath();
        }
        return  imagePath;
    }

    //This method is used to show the message in the GUI.
    public void showMessage(String mess)
    {
        JOptionPane.showMessageDialog(this, mess);
    }

    //This method returns the chat panel.
    public ChatPanel getChatPanel() {
        return chatPanel;
    }

    //This method returns the side panel.
    public SidePanel getSidePanel() {
        return sidePanel;
    }
}



