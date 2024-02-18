package PageObjectModel;

import com.microsoft.playwright.FileChooser;
import com.microsoft.playwright.Page;

import java.nio.file.Path;

public class HomePage {
    private Page page;

    // Lokatory
    //String selectPdfInput = "#js-file-convert";

    String selectPdfButton = "//button[normalize-space(.)='Select PDF']";

    String saveButton = "//button[normalize-space(.)='Save]";
    public HomePage(Page page) {
        this.page = page;
    }

    public void clickSaveButton() {
        page.click(saveButton);
    }

    public void uploadFile(Path filePath) {
        page.click(selectPdfButton);
        //FileChooser fileChooser = page.waitForFileChooser(() -> page.getByText("Log in").click());

        FileChooser fileChooser = page.waitForFileChooser(() -> page.locator(selectPdfButton).click());
        fileChooser.setFiles(filePath);

        //page.setInputFiles(selectPdfInput, filePath);
    }
}
