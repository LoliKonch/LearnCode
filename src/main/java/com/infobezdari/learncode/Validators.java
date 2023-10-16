package chat.local.javalocalchat;

import customexceptions.InvalidDataException;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.regex.Pattern;


/**
 * @author Infobezdar'
 * @version 0.1
 */
public class Validators {

    private static final Pattern RCF2822_MAIL_PATTERN = Pattern.compile(
            "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"" +
                    "(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")" +
                    "@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])" +
                    "?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]" +
                    "?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\" +
                    "\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+))");

    private static final Pattern LOGIN_PATTERN = Pattern.compile("[a-zA-Z0-9_]");

    private static final Pattern PASSWORD_PATTERN = Pattern.compile("[a-zA-Z0-9_!@#$%]");

    private static final Pattern IP_PATTERN = Pattern.compile(
            "^(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                    "(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                    "(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                    "(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$"
    );

    public static String confirmationCodeValidator(PasswordField confirmationCodeField) throws InvalidDataException {
        if (confirmationCodeField.getText() != null && !confirmationCodeField.getText().trim().isEmpty()){
            return confirmationCodeField.getText().trim();
        } else {
            throw new InvalidDataException("All fields must be filled in");
        }
    }

    public static String emailValidator(TextField mailField) throws InvalidDataException {
        if (mailField.getText() != null && !mailField.getText().trim().isEmpty()){
            if (RCF2822_MAIL_PATTERN.matcher(mailField.getText()).matches()) {
                return mailField.getText().trim();
            } else {
                throw new InvalidDataException("Invalid E-mail address");
            }
        } else {
            throw new InvalidDataException("All fields must be filled in");
        }
    }

    public static String loginValidator(TextField loginField) throws InvalidDataException {
        if (loginField.getText() != null && !loginField.getText().trim().isEmpty()){
            if (LOGIN_PATTERN.matcher(loginField.getText()).matches()) {
                return loginField.getText().trim();
            } else {
                throw new InvalidDataException("Invalid Login");
            }
        } else {
            throw new InvalidDataException("All fields must be filled in");
        }
    }

    public static String passwordValidator(PasswordField passwordField) throws InvalidDataException {
        if (passwordField.getText() != null && !passwordField.getText().trim().isEmpty()){
            if (PASSWORD_PATTERN.matcher(passwordField.getText()).matches()) {
                return passwordField.getText().trim();
            } else {
                throw new InvalidDataException("Invalid Password");
            }
        } else {
            throw new InvalidDataException("All fields must be filled in");
        }
    }

    public static String ipValidator(TextField ipField) throws InvalidDataException {
        if (ipField.getText() != null && !ipField.getText().trim().isEmpty()){
            if (IP_PATTERN.matcher(ipField.getText()).matches()) {
                return ipField.getText().trim();
            } else {
                throw new InvalidDataException("Invalid IP address");
            }
        } else {
            throw new InvalidDataException("All fields must be filled in");
        }
    }

    public static String NewPasswordValidator(PasswordField newPasswordField, PasswordField confirmNewPasswordField) throws InvalidDataException {
        if (newPasswordField.getText() != null && !newPasswordField.getText().trim().isEmpty() &&
            confirmNewPasswordField.getText() != null && !confirmNewPasswordField.getText().trim().isEmpty()){
            if (PASSWORD_PATTERN.matcher(newPasswordField.getText()).matches()) {
                if (newPasswordField.getText().trim().equals(confirmNewPasswordField.getText().trim())) {
                    return newPasswordField.getText().trim();
                } else {
                    throw new InvalidDataException("Passwords must match");
                }
            } else {
                throw new InvalidDataException("Invalid Password");
            }
        } else {
            throw new InvalidDataException("All fields must be filled in");
        }
    }
}
