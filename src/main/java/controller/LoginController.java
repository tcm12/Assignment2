package controller;

import model.Administrator;
import model.User;

public class LoginController extends GeneralController {

    public LoginController() {
    }

    public int login(String eMail, String password) {

        for (User user : getUserList()) {
            if (user.getMail().equals(eMail.toLowerCase()) && user.getPassword().equals(password)) {
                return 1;
            }
        }

        for (Administrator administrator : getAdminList()) {
            if (administrator.getUsername().equals(eMail) && administrator.getPassword().equals(password)) {
                return 0;
            }
        }

        return -1;
    }

}
