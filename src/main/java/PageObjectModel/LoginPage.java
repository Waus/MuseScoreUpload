package PageObjectModel;

import com.microsoft.playwright.Page;

public class LoginPage {
    private Page page;

    // Lokatory
    String usernameSelector = "#username";
    String passwordSelector = "#password";
    String loginButtonXPath = "//button[normalize-space(.)='Log in']";

    public LoginPage(Page page) {
        this.page = page;
    }

    public void enterUsername(String username) {
        page.fill(usernameSelector, username);
    }

    public void enterPassword(String password) {
        page.fill(passwordSelector, password);
    }

    public HomePage clickLoginButton() {
        page.click(loginButtonXPath);
        return new HomePage(page);
    }
}
