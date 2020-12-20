package ru.tecom.test.demo.exporter;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ru.tecom.test.demo.entity.Car;

public class CarExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Car> cars;

    public CarExcelExporter(List<Car> cars) {
        this.cars = cars;
        workbook = new XSSFWorkbook();
    }


    private void writeHeaderLine() {
        sheet = workbook.createSheet("Cars");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "Car ID", style);
        createCell(row, 1, "Brand", style);
        createCell(row, 2, "Model", style);
        createCell(row, 3, "Year", style);
        createCell(row, 4, "Month", style);
        createCell(row, 5, "Engine Displacement", style);
        createCell(row, 6, "Turbo", style);
        createCell(row, 7, "Power", style);
        createCell(row, 8, "Transmission Type", style);
        createCell(row, 9, "Drive Type", style);
        createCell(row, 10, "Body Type", style);
        createCell(row, 11, "Color", style);
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else if (value instanceof Long) {
            cell.setCellValue((Long) value);
        }else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (Car car : cars) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, car.getId(), style);
            createCell(row, columnCount++, car.getBrand(), style);
            createCell(row, columnCount++, car.getModel(), style);
            createCell(row, columnCount++, car.getYear(), style);
            createCell(row, columnCount++, car.getMonth(), style);
            createCell(row, columnCount++, car.getEngineDisplacement(), style);
            createCell(row, columnCount++, writeBoolean(car.isTurbo()), style);
            createCell(row, columnCount++, car.getPower(), style);
            createCell(row, columnCount++, car.getTransmissionType().toString(), style);
            createCell(row, columnCount++, car.getDriveType().toString(), style);
            createCell(row, columnCount++, car.getBodyType().toString(), style);
            createCell(row, columnCount++, car.getColor(), style);
        }
    }

    private String writeBoolean(Boolean bool){
        return bool ? "Yes" : "No";
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();

    }
}
