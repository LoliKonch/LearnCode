package chat.local.javalocalchat;

import customexceptions.InvalidDataException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.Pane;

/**
 * E-mail confirmation window controller class
 * @author Infobezdar'
 * @version 1.1
 */
public class EmailConfirmationController {

    private final ChangeWindow  ChangeWindow = new ChangeWindow();

    @FXML
    private Button backButton;

    @FXML
    private PasswordField confirmationCodeField;

    @FXML
    private Button sendConfirmationCodeButton;

    @FXML
    private Pane sideBackground;

    /**
     * Window initialization and control procedure
     */
    @FXML
    void initialize() {

        // Adding a limiter (20) to the confirmation code field
        TextFieldLimiter.addTextLimiter(confirmationCodeField, 20);

        // The event listener for the back button opens a Sign_in window
        backButton.setOnAction(event -> {
            ChangeWindow.changeWindowTo(sideBackground, "Sign_in.fxml");
            Client.sendMessage("back");
        });

        // The event listener for the send code button can open a Sign_in window
        sendConfirmationCodeButton.setOnAction(event ->{

            // Validating and sending message
            try {
                String confirmationCode = Validators.confirmationCodeValidator(confirmationCodeField);
                Client.setConfirmationCode(confirmationCode);
            } catch (InvalidDataException e) {
                ExceptionBox.createExceptionBox(sideBackground, e.getMessage());
                return;
            }

            Client.sendMessage(Client.getConfirmationCode());

            // Waiting to server reply
            String answer = Client.waitMessage();
            if (answer.equals("successful_sign_up")) {
                ChangeWindow.changeWindowTo(sideBackground, "Sign_in.fxml");
            } else {
                ExceptionBox.createExceptionBox(sideBackground, "Invalid secret code");
            }
        });
    }
}