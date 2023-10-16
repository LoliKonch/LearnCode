package chat.local.javalocalchat;

import customexceptions.InvalidDataException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.io.IOException;

/**
 * Sign up window controller class
 * @author Infobezdar'
 * @version 1.0
 */
public class SignUpController {

    private final ChangeWindow  ChangeWindow= new ChangeWindow();

    @FXML
    private Button backButton;

    @FXML
    private TextField loginField;

    @FXML
    private TextField mailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Pane sideBackground;

    @FXML
    private Button signUpButton;

    /**
     * Window initialization and control procedure
     */
    @FXML
    void initialize() {

        // Adding a limiter (40) to the login field
        TextFieldLimiter.addTextLimiter(loginField, 40);
        // Adding a limiter (40) to the password field
        TextFieldLimiter.addTextLimiter(passwordField, 40);
        // Adding a limiter (100) to the E-mail field
        TextFieldLimiter.addTextLimiter(mailField, 100);

        // The event listener for the back button opens a Sign_in window
        backButton.setOnAction(event ->
            ChangeWindow.changeWindowTo(sideBackground, "Sign_in.fxml"));

        // The event listener for the creating an account request button can open an E-mail_confirmation window
        signUpButton.setOnAction(event ->{

            // Validating login password and E-mail
            try {
                Client.setEmail(Validators.loginValidator(loginField));
                Client.setPassword(Validators.passwordValidator(passwordField));
                Client.setPassword(Validators.emailValidator(mailField));
            } catch (InvalidDataException e) {
                ExceptionBox.createExceptionBox(sideBackground, e.getMessage());
                return;
            }

            try {
                // Start connection to server
                Client.startClient();
                // Send creating an account request to server
                Client.sendMessage(Client.getUsername());
                Client.sendMessage("sign_up" +
                        "|" + Client.getUsername() +
                        "|" + Client.getPassword() +
                        "|" + Client.getEmail());

                String answer = Client.waitMessage();
                if (answer.equals("successful_pre_sign_up")) {
                    ChangeWindow.changeWindowTo(sideBackground, "E-mail_confirmation.fxml", true);

                } else {
                    ExceptionBox.createExceptionBox(sideBackground,
                            "         Username already occupied");
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