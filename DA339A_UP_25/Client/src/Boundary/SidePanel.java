package Boundary;

import Control.Client;
import javax.swing.*;
import java.util.HashMap;
import java.awt.*;

//This class is used to show the side panel witch friends and users.

public class SidePanel {

    private JScrollPane friendsScrollPane, globalScrollPane;
    private JLabel friends,globalUsers, userNameLabel;
    private JPanel sidePanel, friendPanel, globalUserPanel, userPanel;
    private Client client;
    private MainFrame mainFrame;
    private HashMap<String, JPanel> currentUsers;

    //This method initiates mainframe, client and start and method.
    public SidePanel(MainFrame mf, Client client) {
        this.mainFrame = mf;
        this.client = client;
        currentUsers = new HashMap<>();
        startSidePanel();
    }

    // This method creates the side panel which show the friends and users.
    public void startSidePanel() {
        
        sidePanel = new JPanel(null);
        sidePanel.setBounds(620, 0, 350, 650);
        sidePanel.setBackground(Color.GREEN);

        globalUsers = new JLabel("All Users");
        globalUsers.setBackground(Color.GRAY);
        globalUsers.setBounds(132, 330, 340, 50);
        globalUsers.setFont(new Font("Calibri", Font.PLAIN, 24));
        sidePanel.add(globalUsers);

        globalScrollPane = new JScrollPane();
        globalScrollPane.setBounds(5, 370, 340, 270);
        globalScrollPane.setVisible(true);
        sidePanel.add(globalScrollPane);

        friendsScrollPane = new JScrollPane();
        friendsScrollPane.setBounds(5, 50, 340, 250);
        friendsScrollPane.setVisible(true);
        sidePanel.add(friendsScrollPane);

        friends = new JLabel("Friends");
        friends.setBackground(Color.GRAY);
        friends.setBounds(140, 5, 340, 50);
        friends.setFont(new Font("Calibri", Font.PLAIN, 24));
        sidePanel.add(friends);

        mainFrame.add(sidePanel);
        mainFrame.repaint();

    }

    //THis mehtod is used to create user panel to show which users has joined.
    public void addUserToAllUsers(ImageIcon userProfilePic, String userName) {
        if (globalUserPanel == null) {
            globalUserPanel = new JPanel();
            globalUserPanel.setLayout(new BoxLayout(globalUserPanel, BoxLayout.Y_AXIS));
            globalScrollPane.setViewportView(globalUserPanel);
        }

        JPanel userPanel = new JPanel(new BorderLayout(10, 0));
        userPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        userPanel.setBackground(Color.gray);

        JLabel profileLabel = new JLabel(new ImageIcon(userProfilePic.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH)));
        userPanel.add(profileLabel, BorderLayout.WEST);

        JLabel userNameLabel = new JLabel(userName, SwingConstants.CENTER);
        userNameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        userPanel.add(userNameLabel, BorderLayout.CENTER);

        JButton detailsButton = new JButton("Add");
        detailsButton.addActionListener(e -> client.addFriend(userName));

        userPanel.add(detailsButton, BorderLayout.EAST);

        globalUserPanel.add(userPanel);
        globalUserPanel.revalidate();
        globalUserPanel.repaint();

        currentUsers.put(userName, userPanel);
    }

    //This method adds the friends to the panel by creating panel/labels and more.
    public void addUserToFriends(String name, ImageIcon image) {
        if (friendPanel == null) {
            friendPanel = new JPanel();
            friendPanel.setLayout(new BoxLayout(friendPanel, BoxLayout.Y_AXIS));
            friendsScrollPane.setViewportView(friendPanel);
        }

        userPanel = new JPanel(new BorderLayout(10, 0));
        userPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        userPanel.setBackground(Color.gray);

        JLabel profileLabel = new JLabel(new ImageIcon(image.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH)));
        userPanel.add(profileLabel, BorderLayout.WEST);

        userNameLabel = new JLabel(name, SwingConstants.CENTER);
        userNameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        userPanel.add(userNameLabel, BorderLayout.CENTER);

        friendPanel.add(userPanel);
        friendPanel.revalidate();
        friendPanel.repaint();

    }
    //This method is used to remove all the users from the GUI.

    public void removeFromAllUsers(String name)
    {

        globalUserPanel.remove(currentUsers.get(name));
        currentUsers.remove(name);

        globalUserPanel.revalidate();
        globalUserPanel.repaint();
    }


}
