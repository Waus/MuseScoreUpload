package MuseScoreUpload;

import PageObjectModel.*;
import com.microsoft.playwright.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {

    static String login;
    static String password;
    static Path fileToImportPath;

    static String loginPageLink;
    static String filePathIdentyfikatory = "C:\\Git - java\\\\MuseScoreUpload\\Resources\\Identyfikatory.xlsx";



    public static void main(String[] args) {

        loadConfigValues(filePathIdentyfikatory);

        executePlaywrightTasks(login, password);

    }

    private static void loadConfigValues(String filePath) {
        ExcelReader readerIdentyfikatory = new ExcelReader(filePath);
        Map<String, String> credentials = readerIdentyfikatory.readLoginAndPassword();
        login = credentials.getOrDefault("login", "Brak danych");
        password = credentials.getOrDefault("hasło", "Brak danych");
        fileToImportPath = Paths.get(credentials.getOrDefault("lokalizacja pliku do importu", "Brak danych"));
        loginPageLink = credentials.getOrDefault("link do strony logowania", "Brak danych");
    }

    private static void executePlaywrightTasks(String login, String password) {
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        BrowserContext context = browser.newContext(new Browser.NewContextOptions()
                // Tutaj możesz dodać opcje dla kontekstu przeglądarki, np. wtyczki, jeśli są wspierane
                // Nie ma bezpośredniego odpowiednika addExtensions jak w Selenium
        );

        Page page = context.newPage();
        page.setViewportSize(1920, 1080);
        page.navigate(loginPageLink);

        LoginPage loginPage = new LoginPage(page);
        loginPage.enterUsername(login);
        loginPage.enterPassword(password);

        HomePage homePage = loginPage.clickLoginButton();
        homePage.uploadFile(fileToImportPath);
        homePage.clickSaveButton();


    }
}