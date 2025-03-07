package Boundary;

import Control.Client;

import javax.swing.*;
import java.awt.*;

//This class creates the side panel which is used when a client joins, and it also shows the messages.

public class ChatPanel {
    private JPanel chatPanel, singleMessagePanel, imageTimePanel;
    private MainFrame mainFrame;
    private JButton sendButton;
    private JTextField messageField;
    private JScrollPane messageWindow;
    private Client client;

    //This method initiates mainframe/client and also starts a method.
    public ChatPanel(MainFrame mf, Client client)
    {
        this.mainFrame = mf;
        this.client = client;
        startChatPanel();
    }

    //This method creates the chat panel, holds the message, creates a scroll panel and creates the text field.
    public void startChatPanel()
    {
        //Chat panel
        chatPanel = new JPanel(null);
        chatPanel.setBackground(Color.BLUE);
        chatPanel.setBounds(10, 0, 600, 650);
        chatPanel.setLayout(null);
        chatPanel.setVisible(true);
        // Message Area
        chatPanel.setLayout(null);

        // Create the panel that will actually hold the messages
        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));
        messagePanel.setBackground(Color.WHITE);

        // Create a scroll pane around the message panel
        messageWindow = new JScrollPane(messagePanel);
        messageWindow.setBounds(5, 5, 590, 550);
        messageWindow.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        chatPanel.add(messageWindow);

        // Message text
        messageField = new JFormattedTextField();
        messageField.setBounds(5, 575, 500, 50);
        messageField.addActionListener(e -> getReceivers());
        messageWindow.setVisible(true);
        chatPanel.add(messageField);
        setImageButton();
        mainFrame.add(chatPanel);

        mainFrame.repaint();
    }

    //This method creates the send button.
    public void setImageButton() {
        //Send Button
        sendButton = new JButton("image");
        sendButton.setBounds(510, 575, 80, 50);
        chatPanel.add(sendButton);
        sendButton.addActionListener(e -> {
            String filePath = mainFrame.profilePicChooser();
            client.setFilePath(filePath);
        });
    }

    //This method is used to show the message in the chat panel, it also creates new component to show the message.
    public void addMessageToChat(String name, ImageIcon image, String message, String currentTime, ImageIcon imageToSend) {
        if(!message.isEmpty()) {

            singleMessagePanel = new JPanel();
            singleMessagePanel.setLayout(new BoxLayout(singleMessagePanel, BoxLayout.X_AXIS));
            singleMessagePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            singleMessagePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            imageTimePanel = new JPanel();
            imageTimePanel.setLayout(new BoxLayout(imageTimePanel, BoxLayout.Y_AXIS));
            imageTimePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

            ImageIcon originalIcon = image;
            Image scaledImage = originalIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
            imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            imageTimePanel.add(imageLabel);

            JLabel timeLabel = new JLabel(currentTime);
            timeLabel.setFont(new Font("Arial", Font.PLAIN, 10));
            timeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            imageTimePanel.add(timeLabel);

            singleMessagePanel.add(imageTimePanel);

            JLabel textLabel = new JLabel(name + ": " + message);
            textLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            singleMessagePanel.add(textLabel);

            JPanel messageContainer = (JPanel) messageWindow.getViewport().getView();
            messageContainer.add(singleMessagePanel);
            messageContainer.add(Box.createRigidArea(new Dimension(0, 10)));
            messageContainer.revalidate();
            messageContainer.repaint();

            JScrollBar verticalBar = messageWindow.getVerticalScrollBar();
            verticalBar.setValue(verticalBar.getMaximum());

        }

        if (imageToSend != null)
        {
            sendImage(name, image, currentTime, imageToSend);
        }

    }

    //This method gets the message from the text field in chat panel.
    public String getTextFromMessageField() {
        String text = messageField.getText();
        messageField.setText("");
        return text;
    }

    //This method is used to ask the user sending the message to whom.
    private void getReceivers() {

        String recievers = JOptionPane.showInputDialog("Enter receivers (name1/name2/...)");
        client.sendTextMessage(recievers);

    }

    //This method is used when sending an image, it also creates new panels/components.
    public void sendImage(String name, ImageIcon pic, String time, ImageIcon imageToSend) {

        ImageIcon imageIcon = imageToSend;
        Image scaledImage = imageIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        singleMessagePanel = new JPanel();
        singleMessagePanel.setLayout(new BoxLayout(singleMessagePanel, BoxLayout.X_AXIS));
        singleMessagePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        singleMessagePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        imageTimePanel = new JPanel();
        imageTimePanel.setLayout(new BoxLayout(imageTimePanel, BoxLayout.Y_AXIS));
        imageTimePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        ImageIcon profileIcon = new ImageIcon(pic.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        JLabel imageLabel = new JLabel(profileIcon);
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        imageTimePanel.add(imageLabel);

        JLabel timeLabel = new JLabel(time);
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        timeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        imageTimePanel.add(timeLabel);

        singleMessagePanel.add(imageTimePanel);

        JLabel textLabel = new JLabel(name + ": ");
        textLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel sendImageLabel = new JLabel(scaledIcon);
        sendImageLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        singleMessagePanel.add(textLabel);
        singleMessagePanel.add(sendImageLabel);

        JPanel messageContainer = (JPanel) messageWindow.getViewport().getView();
        messageContainer.add(singleMessagePanel);
        messageContainer.add(Box.createRigidArea(new Dimension(0, 10)));
        messageContainer.revalidate();
        messageContainer.repaint();

        JScrollBar verticalBar = messageWindow.getVerticalScrollBar();
        verticalBar.setValue(verticalBar.getMaximum());
    }

}
