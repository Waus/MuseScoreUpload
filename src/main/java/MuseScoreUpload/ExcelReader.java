package MuseScoreUpload;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class ExcelReader {
    private String filePath;

    public ExcelReader(String filePath) {
        this.filePath = filePath;
    }

    public Map<String, String> readLoginAndPassword() {
        Map<String, String> credentials = new HashMap<>();
        try (FileInputStream inputStream = new FileInputStream(new File(filePath));
             Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Pomijamy nagłówek

                Cell keyCell = row.getCell(0);
                Cell valueCell = row.getCell(1);

                if (keyCell != null && valueCell != null) {
                    String key = keyCell.getStringCellValue();
                    String value = getCellValueAsString(valueCell);

                    credentials.put(key, value);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return credentials;
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }
        String cellValue = "";
        switch (cell.getCellType()) {
            case STRING:
                cellValue = cell.getStringCellValue();
                break;
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    cellValue = new SimpleDateFormat("yyyy-MM-dd").format(cell.getDateCellValue());
                } else {
                    cellValue = new DecimalFormat("#.######").format(cell.getNumericCellValue());
                }
                break;
            case BOOLEAN:
                cellValue = Boolean.toString(cell.getBooleanCellValue());
                break;
            case FORMULA:
                cellValue = cell.getCellFormula();
                break;
            case BLANK:
                cellValue = "";
                break;
            case ERROR:
                cellValue = "ErrorCell";
                break;
            default:
                cellValue = "UnknownCellType";
        }
        return cellValue;
    }


}