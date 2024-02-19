package PageObjectModel;

import com.microsoft.playwright.FileChooser;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;

public class HomePage {
    private Page page;

    // Lokatory
    //String selectPdfInput = "#js-file-convert";

    //String selectPdfButton = "//button[normalize-space(.)='Select PDF']";
    //String selectPdfButton = "//button[@class='HFvdW Dhs0s nOTLW Cl2SE u_VDg']//span[text()='Select PDF']";
    //Locator selectPDFButton;

    //String saveButton = "//button[normalize-space(.)='Save']";
    //Locator saveButton;
    //String saveButton = "//button[normalize-space(.)='Save']";

    public HomePage(Page page) {
        this.page = page;
        //selectPDFButton = page.locator("button:has-text('Select PDF')");
        //saveButton = page.locator("button:has-text('Save')");
    }

    public void uploadFile(Path filePath) {

        FileChooser fileChooser = page.waitForFileChooser(() -> page.getByRole(AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName(
                        Pattern.compile("Select PDF", Pattern.CASE_INSENSITIVE))).click());
        fileChooser.setFiles(Paths.get("test.pdf"));

//        FileChooser fileChooser = page.waitForFileChooser(() -> {
//            selectPDFButton.click();
//        });
        fileChooser.setFiles(Paths.get("test.pdf"));

    }

    public void clickSaveButton() {

        page.getByRole(AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName(
                        Pattern.compile("Save", Pattern.CASE_INSENSITIVE))).click();

                        //saveButton.click();
    }
}
