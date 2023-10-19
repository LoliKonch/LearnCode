package com.infobezdari.learncode;


import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Chat window controller class
 * @author Infobezdar'
 * @version 1.0
 */
public class ChatController implements Initializable {

    private final ChangeWindow  ChangeWindow = new ChangeWindow();
    @FXML
    private Button exitButton;

    @FXML
    private Button sendFlagButton;

    @FXML
    private Button sendTopButton;

    @FXML
    private TextField messageField;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox vBoxWithMessages;


    /**
     * Display someone else's message procedure
     * @param inMessageList - information about in message
     * @param vBox - vertical box with messages
     */
    public static void displayOtherMessage(String[] inMessageList, VBox vBox) {

        // Declare and customization of horizontal container (1 layer)
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5, 20, 5, 5));

        // Declare and customization of vertical container (2 layer)
        VBox messageVBox = new VBox();
        messageVBox.getStyleClass().add("other-message-vbox");

        // Declare and customization username who send message (3 layer)
        Label userName = new Label(inMessageList[1]);
        userName.getStyleClass().add("user-name");

        // Declare and customization text of the message (3 layer)
        Text inMessageText = new Text(inMessageList[2]);
        TextFlow inMessageTextFlow = new TextFlow(inMessageText);
        inMessageTextFlow.getStyleClass().add("in-message-text-flow");

        // Declare and customization date and time of sending message (3 layer)
        Label dateAndTime = new Label(inMessageList[0]);
        dateAndTime.getStyleClass().add("other-date-and-time");

        // Making message whole
        messageVBox.getChildren().add(userName);
        messageVBox.getChildren().add(inMessageTextFlow);
        messageVBox.getChildren().add(dateAndTime);
        hBox.getChildren().add(messageVBox);

        // Add message to scene
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vBox.getChildren().add(hBox);
            }
        });
    }

    /**
     * Display your own message procedure*
     * @param yourMessage - information about sent message
     * @param vBox - vertical box with messages
     */
    public static void displayYourMessage(String[] yourMessage, VBox vBox) {

        // Declare and customization of horizontal container (1 layer)
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_RIGHT);
        hBox.setPadding(new Insets(5, 5, 5, 20));

        // Declare and customization of vertical container (2 layer)
        VBox messageVBox = new VBox();
        messageVBox.getStyleClass().add("your-message-vbox");

        // Declare and customization your username (3 layer)
        Text yourMessageText = new Text(yourMessage[2]);
        TextFlow yourMessageTextFlow = new TextFlow(yourMessageText);
        yourMessageTextFlow.getStyleClass().add("your-message-text-flow");

        // Declare and customization text of the message (3 layer)
        Label dateAndTime = new Label(yourMessage[0]);
        dateAndTime.getStyleClass().add("your-date-and-time");

        // Making message whole
        messageVBox.getChildren().add(yourMessageTextFlow);
        messageVBox.getChildren().add(dateAndTime);
        hBox.getChildren().add(messageVBox);

        // Add message to scene
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vBox.getChildren().add(hBox);
            }
        });
    }


    /**
     * Window initialization and control procedure
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Start receiving messages
        Client.receiveMessage(vBoxWithMessages);
        // Adding a limiter (5000) to the message field
        TextFieldLimiter.addTextLimiter(messageField, 5000);

        vBoxWithMessages.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                scrollPane.setVvalue((Double) newValue);
            }
        });


        // The event listener for the exit button open a Sign_in window
        exitButton.setOnAction(event -> {
            ChangeWindow.changeWindowTo(vBoxWithMessages, "Sign_in.fxml");
            Client.closeEverything();
        });

        // The event listener for the send flag button
        sendFlagButton.setOnAction(event -> {
            String outMessage = messageField.getText();
            messageField.clear();

            if (!outMessage.trim().isEmpty()) {
                Date date = new Date();
                SimpleDateFormat formatForDate = new SimpleDateFormat("dd.MM.yy H:mm");
                String[] message = {formatForDate.format(date), "", outMessage};
                displayYourMessage(message, vBoxWithMessages);
                Client.sendMessage("answer_check|" + outMessage);
            }
        });

        // The event listener for the send top button
        sendTopButton.setOnAction(event -> {
            Client.sendMessage("!rating");
        });
    }
}