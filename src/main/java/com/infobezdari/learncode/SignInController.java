package chat.local.javalocalchat;

import customexceptions.InvalidDataException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.io.IOException;

/**
 * Sign in window controller class
 * @author Infobezdar'
 * @version 1.0
 */
public class SignInController {

    private final ChangeWindow  ChangeWindow = new ChangeWindow();

    @FXML
    private Hyperlink forgotPassword;

    @FXML
    private TextField loginField;

    @FXML
    private TextField IPAddressField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button registrationButton;

    @FXML
    private Pane sideBackground;

    @FXML
    private Button signInButton;

    /**
     * Window initialization and control procedure
     */
    @FXML
    void initialize() {

        // Adding a limiter (40) to the password field
        TextFieldLimiter.addTextLimiter(passwordField, 40);
        // Adding a limiter (40) to the login field
        TextFieldLimiter.addTextLimiter(loginField, 40);

        // The event listener for the registration button opens a Sign_up window
        registrationButton.setOnAction(event ->
            ChangeWindow.changeWindowTo(sideBackground, "Sign_up.fxml"));

        // The event listener for the forgot password button opens a Forgot_your_password window
        forgotPassword.setOnAction(event ->
            ChangeWindow.changeWindowTo(sideBackground, "Forgot_your_password.fxml"));

        // The event listener for the enter button can open a Chat window
        signInButton.setOnAction(event ->{

            // Validating login password and IP
            try {
                Client.setEmail(Validators.loginValidator(loginField));
                Client.setPassword(Validators.passwordValidator(passwordField));
                Client.setIPAddress(Validators.ipValidator(IPAddressField));
            } catch (InvalidDataException e) {
                ExceptionBox.createExceptionBox(sideBackground, e.getMessage());
                return;
            }

            try {
                // Start connection to server
                Client.startClient();
                // Send enter to chat request to server
                Client.sendMessage(Client.getUsername());
                Client.sendMessage(
                        "sign_in" +
                        "|" + Client.getUsername() +
                        "|" + Client.getPassword()
                );

                String answer = Client.waitMessage();
                if (answer.equals("successful_sign_in")) {
                    ChangeWindow.changeWindowTo(sideBackground, "Chat.fxml");

                } else {
                    ExceptionBox.createExceptionBox(sideBackground,
                            "         Incorrect login or password");
                            Client.closeEverything();
                }
            } catch (IOException e) {
                Client.closeEverything();
                ExceptionBox.createExceptionBox(sideBackground,
                        "        Unable to connect to server" +
                        "\n             Please try again later");
            }
        });
    }
}