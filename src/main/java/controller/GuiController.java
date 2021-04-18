package controller;

import view.AdministratorPage;
import view.LoginPage;
import view.UserPage;

public class GuiController {

    private LoginPage loginPage;
    private UserPage userPage;
    private AdministratorPage administratorPage;

    public GuiController() {
    }

    public void setLoginPage(LoginPage loginPage) {
        this.loginPage = loginPage;
    }

    public void setUserPage(UserPage userPage) {
        this.userPage = userPage;
    }

    public void setAdministratorPage(AdministratorPage administratorPage) {
        this.administratorPage = administratorPage;
    }

    public void openLoginPage() {
        closeAllPages();
        this.loginPage.setVisible(true);
    }

    public void openUserPage() {
        closeAllPages();
        this.userPage.setVisible(true);
    }

    public void openAdminPage() {
        closeAllPages();
        this.administratorPage.setVisible(true);
    }

    public void closeAllPages() {
        if (loginPage != null) {
            this.loginPage.setVisible(false);
        }
        if (userPage != null) {
            this.userPage.setVisible(false);
        }
        if (administratorPage != null) {
            this.administratorPage.setVisible(false);
        }
    }
}
