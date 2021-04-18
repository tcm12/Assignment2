package view;

import controller.GuiController;
import controller.LoginController;

import javax.swing.*;

public class LoginPage extends JFrame {
    private JPanel loginPagePanel;
    private JTextField eMailTextField;
    private JButton button1;
    private JPasswordField passwordTextField;

    private final GuiController guiController;
    private final LoginController loginController;

    public LoginPage() {
        super("Login Page");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(loginPagePanel);
        this.pack();

        passwordTextField.setEchoChar('*');

        guiController = new GuiController();
        guiController.setLoginPage(this);
        guiController.openLoginPage();

        loginController = new LoginController();

        activateActionListeners();
    }

    private void login() {
        String inputMail = eMailTextField.getText();
        String inputPassword = new String(passwordTextField.getPassword());

        switch (loginController.login(inputMail, inputPassword)) {
            case 0:
                AdministratorPage administratorPage = new AdministratorPage();
                guiController.setAdministratorPage(administratorPage);
                guiController.openAdminPage();
                break;
            case 1:
                UserPage userPage = new UserPage();
                guiController.setUserPage(userPage);
                guiController.openUserPage();
                break;
            default:
                JOptionPane.showMessageDialog(
                        this,
                        "Not a registered user!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
        }
    }

    public void activateActionListeners() {
        button1.addActionListener(e -> login());
    }
}
