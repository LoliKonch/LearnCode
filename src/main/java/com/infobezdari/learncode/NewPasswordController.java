package chat.local.javalocalchat;

import customexceptions.InvalidDataException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.Pane;

/**
 * Set new password window controller class
 * @author Infobezdar'
 * @version 1.0
 */
public class NewPasswordController {

    private final ChangeWindow  ChangeWindow= new ChangeWindow();

    @FXML
    private Button backButton;

    @FXML
    private PasswordField confirmNewPasswordField;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private PasswordField confirmationCodeField;

    @FXML
    private Button setNewPasswordButton;

    @FXML
    private Pane sideBackground;

    /**
     * Window initialization and control procedure
     */
    @FXML
    void initialize() {

        // Adding a limiter (20) to the confirmation code field
        TextFieldLimiter.addTextLimiter(confirmationCodeField, 20);
        // Adding a limiter (40) to the password field
        TextFieldLimiter.addTextLimiter(newPasswordField, 40);
        // Adding a limiter (40) to the confirmation password field
        TextFieldLimiter.addTextLimiter(confirmNewPasswordField, 40);

        // The event listener for the back button opens a Sign_in window
        backButton.setOnAction(event -> {
            ChangeWindow.changeWindowTo(sideBackground, "Sign_in.fxml");
            Client.sendMessage("back");
        });

        // The event listener for the confirm new password button can open a Sign_in window
        setNewPasswordButton.setOnAction(event ->{

            // Validating new password and confirmation code
            try {
                Client.setNewPassword(Validators.NewPasswordValidator(newPasswordField, confirmNewPasswordField));
                Client.setConfirmationCode(Validators.confirmationCodeValidator(confirmationCodeField));
            } catch (InvalidDataException e) {
                ExceptionBox.createExceptionBox(sideBackground, e.getMessage());
                return;
            }

            // Send set new password request to server
            Client.sendMessage("new_password +" +
                    "|" + Client.getUsername() +
                    "|" + Client.getNewPassword() +
                    "|" + Client.getConfirmationCode());

            // confirmation code authentication
            String answer = Client.waitMessage();
            if (answer.equals("successful_password_recovery")) {
                ChangeWindow.changeWindowTo(sideBackground, "Sign_in.fxml");
            } else {
                ExceptionBox.createExceptionBox(sideBackground,
                        "                 Invalid secret code");
            }
        });
    }
}