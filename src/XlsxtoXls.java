import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Iterator;

import javax.swing.JOptionPane;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.Boolean;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XlsxtoXls {

    public static void main(String[] args) {

    	JOptionPane.showMessageDialog(null, "Ali Afridi \nCS 498 - XlsxtoXls");
        //reading file from desktop
        File inputFile = new File("M0163.xlsx");
        //writing excel data to csv 
        File outputFile = new File("M0163.xls");
        xlsx(inputFile, outputFile);
        JOptionPane.showMessageDialog(null, "Conversion Complete");
    }
    
    static void xlsx(File inputFile, File outputFile) {
        // For storing data into Xls files
        StringBuffer data = new StringBuffer();

        try {
            FileOutputStream fos = new FileOutputStream(outputFile);
            // Get the workbook object for XLSX file
            XSSFWorkbook wBook = new XSSFWorkbook(new FileInputStream(inputFile));
            // Get first sheet from the workbook
            XSSFSheet sheet = wBook.getSheetAt(0);
            Row row;
            Cell cell;
            
            
            File exlFile = outputFile;
			WritableWorkbook writableWorkbook = Workbook.createWorkbook(exlFile);
			WritableSheet writableSheet = writableWorkbook.createSheet("Sheet1", 0);
            
            // Iterate through each rows from first sheet
            Iterator<Row> rowIterator = sheet.iterator();
            int cols = -1, rows = -1;

            while (rowIterator.hasNext()) {
                row = rowIterator.next();
                rows++;
                cols = -1;
                // For each row, iterate through each columns
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {

                    cell = cellIterator.next();
                    cols++;

                    switch (cell.getCellType()) {
                        case Cell.CELL_TYPE_BOOLEAN:
                            Boolean value0 = new Boolean (cols, rows, cell.getBooleanCellValue());
                            writableSheet.addCell(value0);
                            break;
                        case Cell.CELL_TYPE_NUMERIC:
                            Number value1 = new Number (cols, rows, cell.getNumericCellValue());
                            writableSheet.addCell(value1);
                            break;
                        case Cell.CELL_TYPE_STRING:
                            Label value2 = new Label (cols, rows, cell.getStringCellValue());
                            writableSheet.addCell(value2);
                            break;
                        case Cell.CELL_TYPE_BLANK:
                        	Label value3 = new Label (cols, rows, cell.getStringCellValue());
                        	writableSheet.addCell(value3);
                        	break;
                        default:
                        	Label value4 = new Label (cols, rows, cell.getStringCellValue());
                        	writableSheet.addCell(value4);
                            break;

                    }
                    
                }
            }

          //Write and close the workbook
			writableWorkbook.write();
			writableWorkbook.close();

        } catch (Exception ioe) {
            ioe.printStackTrace();
        }
    }
}