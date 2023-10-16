package chat.local.javalocalchat;

import customexceptions.InvalidDataException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.io.IOException;

/**
 * Request for changing password window controller class
 * @author Infobezdar'
 * @version 1.0
 */
public class ForgotYourPasswordController {

    private final ChangeWindow  ChangeWindow= new ChangeWindow();

    @FXML
    private Button backButton;

    @FXML
    private Button confirmLoginButton;

    @FXML
    private TextField loginField;

    @FXML
    private Pane sideBackground;

    /**
     * Window initialization and control procedure
     */
    @FXML
    void initialize() {

        // Adding a limiter (40) to the login field
        TextFieldLimiter.addTextLimiter(loginField, 40);

        // The event listener for the back button opens a Sign_in window
        backButton.setOnAction(event ->
            ChangeWindow.changeWindowTo(sideBackground, "Sign_in.fxml"));

        // The event listener for the change password request can open a New_password window
        confirmLoginButton.setOnAction(event ->{

            // Validating login
            try {
                Client.setUsername(Validators.loginValidator(loginField));
            } catch (InvalidDataException e) {
                ExceptionBox.createExceptionBox(sideBackground, e.getMessage());
                return;
            }

            try {
                // Start connection to server
                Client.startClient();
                // Send password recovery request to server
                Client.sendMessage(Client.getUsername());
                Client.sendMessage("password_recovery" + "|" + Client.getUsername());

                String answer = Client.waitMessage();
                if (answer.equals("begin_password_recovery")) {
                    ChangeWindow.changeWindowTo(sideBackground, "New_password.fxml", true);
                } else {
                    ExceptionBox.createExceptionBox(sideBackground,
                            "                   Incorrect login");
                            Client.closeEverything();
                }
            } catch (IOException e) {
                Client.closeEverything();
                ExceptionBox.createExceptionBox(sideBackground,
                        "        Unable to connect to server" +
                                "\n         Please try again later");
            }
        });
    }
}