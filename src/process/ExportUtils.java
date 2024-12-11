/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package process;

/**
 *
 * @author Asus
 */
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import dto.Connect;
import javax.swing.table.DefaultTableModel;

public class ExportUtils {

    // Phương thức xuất dữ liệu ra file Excel và cho phép người dùng chọn tên và chỗ lưu
    public static void exportToExcel(String sqlQuery, Connect connect) {
        ResultSet rs = null;
        JFileChooser fileChooser = new JFileChooser();

        // Thiết lập hộp thoại cho phép lưu file Excel
        fileChooser.setDialogTitle("Chọn nơi lưu và tên file Excel");
        fileChooser.setSelectedFile(new File("Data.xlsx")); // Đặt tên mặc định cho file

        // Hiển thị hộp thoại lưu file và lấy kết quả
        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection != JFileChooser.APPROVE_OPTION) {
            System.out.println("Người dùng đã hủy thao tác.");
            return; // Nếu người dùng hủy, không làm gì cả
        }

        // Lấy file path người dùng đã chọn
        File fileToSave = fileChooser.getSelectedFile();
        String outputFileName = fileToSave.getAbsolutePath();

        try {
            // Thực thi truy vấn SQL
            rs = connect.selectQuery(sqlQuery, new Object[]{});

            // Tạo workbook và sheet
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Data");

            // Tạo dòng tiêu đề
            Row headerRow = sheet.createRow(0);
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Đặt tiêu đề cho các cột
            for (int i = 1; i <= columnCount; i++) {
                Cell cell = headerRow.createCell(i - 1);
                cell.setCellValue(metaData.getColumnName(i));
            }

            // Đưa dữ liệu vào file Excel
            int rowNum = 1;
            while (rs.next()) {
                Row row = sheet.createRow(rowNum++);
                for (int i = 1; i <= columnCount; i++) {
                    Cell cell = row.createCell(i - 1);
                    cell.setCellValue(rs.getString(i)); // Lấy giá trị từ ResultSet và đặt vào ô
                }
            }

            // Ghi dữ liệu vào file Excel
            FileOutputStream fileOut = new FileOutputStream(outputFileName);
            workbook.write(fileOut);
            fileOut.close();

            // Thông báo thành công
            JOptionPane.showMessageDialog(null, "Dữ liệu đã được xuất thành công vào file Excel: " + outputFileName);

        } catch (SQLException | IOException e) {
            // Thông báo lỗi
            JOptionPane.showMessageDialog(null, "Lỗi khi xuất file Excel: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();  // Đóng ResultSet sau khi sử dụng
                if (connect != null) connect.close();  // Đảm bảo đóng kết nối
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
//xuất file từ table
    public static void exportTableToExcel(JTable table) {
        // Tạo đối tượng JFileChooser để cho phép người dùng chọn tên và vị trí lưu file
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu và tên file Excel");
        fileChooser.setSelectedFile(new File("Data.xlsx"));  // Đặt tên mặc định cho file

        // Hiển thị hộp thoại để người dùng chọn vị trí lưu file
        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection != JFileChooser.APPROVE_OPTION) {
            System.out.println("Người dùng đã hủy thao tác.");
            return;  // Nếu người dùng hủy, không làm gì cả
        }

        // Lấy đường dẫn file mà người dùng đã chọn
        File fileToSave = fileChooser.getSelectedFile();
        String outputFileName = fileToSave.getAbsolutePath();

        // Tạo workbook và sheet để lưu trữ dữ liệu Excel
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Data");

        // Lấy dữ liệu từ JTable
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int rowCount = model.getRowCount();
        int columnCount = model.getColumnCount();

        // Tạo dòng tiêu đề
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < columnCount; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(model.getColumnName(i));  // Đặt tên cột
        }

        // Thêm dữ liệu vào file Excel
        for (int rowNum = 0; rowNum < rowCount; rowNum++) {
            Row row = sheet.createRow(rowNum + 1);  // Bắt đầu từ dòng 1
            for (int colNum = 0; colNum < columnCount; colNum++) {
                Cell cell = row.createCell(colNum);
                Object value = model.getValueAt(rowNum, colNum);  // Lấy giá trị tại mỗi ô
                if (value != null) {
                    cell.setCellValue(value.toString());  // Ghi giá trị vào ô
                }
            }
        }

        // Ghi dữ liệu vào file Excel
        try (FileOutputStream fileOut = new FileOutputStream(outputFileName)) {
            workbook.write(fileOut);
            JOptionPane.showMessageDialog(null, "Dữ liệu đã được xuất thành công vào file Excel: " + outputFileName);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi xuất file Excel: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                workbook.close();  // Đảm bảo đóng workbook
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
