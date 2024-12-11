/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package view.admin;

import dto.Connect;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import process.get;  // Chứa các lớp cho các sheet, row, cell, v.v.
import org.apache.poi.xssf.usermodel.XSSFWorkbook;  // Để thao tác với file Excel .xlsx

// Nếu sử dụng FileOutputStream để lưu file
import java.io.File;
import java.io.FileOutputStream;
import javax.swing.JTable;
import process.ExportUtils;

/**
 *
 * @author Asus
 */
public class frmUser extends javax.swing.JInternalFrame {

    /**
     * Creates new form frmUser
     */
    public frmUser() {
        initComponents();
        pack();
        this.setSize(919, 608);
        getUser();

        boxRoles();

        panInput.setVisible(false);
    }

    public void getUser() {
        try {
            Connect cn = new Connect(); // Kết nối tới cơ sở dữ liệu
            DefaultTableModel dt = (DefaultTableModel) tbUser.getModel();
            dt.setRowCount(0); // Làm sạch bảng trước khi thêm dữ liệu mới

            // Câu lệnh truy vấn để lấy dữ liệu từ bảng Users
            String query = "SELECT u.userId, u.username, u.password, u.email, "
                    + "u.phone, e.name AS empName, r.roleName "
                    + "FROM users u "
                    + "JOIN employees e ON u.empId = e.empId "
                    + "JOIN roles r ON u.roleId = r.roleId";

            try (ResultSet resultSet = cn.selectQuery(query, new Object[0])) { // Thực thi truy vấn
                while (resultSet.next()) {
                    Vector<Object> v = new Vector<>();
                    v.add(resultSet.getInt("userId")); // Lấy giá trị userId
                    v.add(resultSet.getString("username")); // Lấy giá trị username
                    v.add(resultSet.getString("password")); // Lấy giá trị password
                    v.add(resultSet.getString("email")); // Lấy giá trị email

                    // Kiểm tra kiểu dữ liệu của phone. Nếu là chuỗi thì dùng getString
                    v.add(resultSet.getString("phone"));

                    v.add(resultSet.getString("empName")); // Lấy giá trị empId
                    v.add(resultSet.getString("roleName")); // Lấy giá trị roleId
                    dt.addRow(v); // Thêm dữ liệu vào bảng
                }
                System.out.println("Lấy dữ liệu thành công từ Users");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Lỗi khi xử lý dữ liệu: " + e.getMessage());
                e.printStackTrace();
            } finally {
                cn.close(); // Đóng kết nối
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi lấy dữ liệu: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void exportToExcel() {
        try {
            Connect cn = new Connect(); // Kết nối đến cơ sở dữ liệu
            // Câu lệnh truy vấn để lấy dữ liệu từ bảng Users
            String query = "SELECT u.userId, u.username, u.password, u.email, "
                    + "u.phone, e.name AS empName, r.roleName "
                    + "FROM users u "
                    + "JOIN employees e ON u.empId = e.empId "
                    + "JOIN roles r ON u.roleId = r.roleId";

            ResultSet resultSet = cn.selectQuery(query, new Object[0]); // Thực thi truy vấn

            // Tạo workbook và sheet cho Excel
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("User Data");

            // Tạo tiêu đề cho bảng
            Row headerRow = sheet.createRow(0);
            String[] columns = {"User ID", "Username", "Password", "Email", "Phone", "Employee Name", "Role Name"};

            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }

            // Thêm dữ liệu vào sheet
            int rowNum = 1;
            while (resultSet.next()) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(resultSet.getInt("userId"));
                row.createCell(1).setCellValue(resultSet.getString("username"));
                row.createCell(2).setCellValue(resultSet.getString("password"));
                row.createCell(3).setCellValue(resultSet.getString("email"));
                row.createCell(4).setCellValue(resultSet.getString("phone"));
                row.createCell(5).setCellValue(resultSet.getString("empName"));
                row.createCell(6).setCellValue(resultSet.getString("roleName"));
            }

            // Mở hộp thoại để chọn vị trí và tên tệp để lưu
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save Excel File");
            fileChooser.setSelectedFile(new File("user_data.xlsx"));
            int userSelection = fileChooser.showSaveDialog(null);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                try (FileOutputStream fileOut = new FileOutputStream(fileToSave)) {
                    workbook.write(fileOut);
                    JOptionPane.showMessageDialog(null, "Xuất dữ liệu thành công!");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Lỗi khi lưu file: " + e.getMessage());
                }
            }

            // Đóng kết nối và giải phóng tài nguyên
            cn.close();
            workbook.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi xuất dữ liệu: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void addUser() {
        String name = txtName.getText().trim();
        String password = txtPassword.getText().trim();
        String email = txtEmail.getText().trim();
        String phone = txtPhone.getText().trim();
        String employees = txtEployeesID.getText().trim();
        String role = boxRole.getSelectedItem().toString().split(" - ")[0];

        // Kiểm tra dữ liệu nhập vào
        if (name.isEmpty() || password.isEmpty() || email.isEmpty() || phone.isEmpty() || employees.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        // Kiểm tra tên user đã tồn tại
        if (isUserExist(name)) {
            JOptionPane.showMessageDialog(null, "Tên user đã tồn tại!");
            return;
        }

        // Lấy empId từ tên nhân viên
        int empId = -1;
        try {
            empId = getEmpIdByName(employees);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Không tìm thấy nhân viên với tên: " + employees);
            return;
        }

        // Kiểm tra nếu không tìm thấy empId
        if (empId == -1) {
            JOptionPane.showMessageDialog(null, "Không tìm thấy nhân viên với tên: " + employees);
            return;
        }

        Object[] argv = new Object[6];
        argv[0] = name;
        argv[1] = password;
        argv[2] = email;
        argv[3] = phone;
        argv[4] = empId;
        argv[5] = role;

        try {
            Connect cn = new Connect();
            String query = "INSERT INTO users (username, password, email, phone, empId, roleId) VALUES (?, ?, ?, ?, ?, ?)";
            int rs = cn.executeUpdateQuery(query, argv);
            if (rs > 0) {
                JOptionPane.showMessageDialog(null, "Thêm mới thành công dữ liệu!");
                clearText();
            } else {
                JOptionPane.showMessageDialog(null, "Không có dữ liệu nào được thêm!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Thêm mới thất bại! Lỗi: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void updateUser() {
        String id = txtId.getText().trim(); // Lấy ID từ trường nhập
        String name = txtName.getText().trim();
        String password = txtPassword.getText().trim();
        String email = txtEmail.getText().trim();
        String phone = txtPhone.getText().trim();
        String empName = txtEployeesID.getText().trim(); // Trường nhập tên nhân viên
        String role = boxRole.getSelectedItem().toString().split(" - ")[0];

        // Kiểm tra dữ liệu nhập vào
        if (id.isEmpty() || !id.matches("\\d+")) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập ID hợp lệ!");
            return;
        }
        if (name.isEmpty() || password.isEmpty() || email.isEmpty() || phone.isEmpty() || empName.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        try {
            // Lấy empId từ tên nhân viên
            int empId = getEmpIdByName(empName);

            Object[] argv = new Object[6];
            argv[0] = name;
            argv[1] = password;
            argv[2] = email;
            argv[3] = phone;
            argv[4] = empId; // Dùng empId lấy được từ tên nhân viên
            argv[5] = role;

            Connect cn = new Connect();
            String query = "UPDATE users SET username = ?, password = ?, email = ?, phone = ?, empId = ?, roleId = ? WHERE userId = ?";
            Object[] finalArgs = new Object[argv.length + 1];
            System.arraycopy(argv, 0, finalArgs, 0, argv.length);
            finalArgs[finalArgs.length - 1] = Integer.parseInt(id); // Thêm ID làm tham số cuối

            int rs = cn.executeUpdateQuery(query, finalArgs);
            if (rs > 0) {
                JOptionPane.showMessageDialog(null, "Cập nhật thành công!");
                clearText(); // Xóa dữ liệu nhập liệu
            } else {
                JOptionPane.showMessageDialog(null, "Không có dữ liệu nào được cập nhật!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Cập nhật thất bại! Lỗi: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteUser() {
        String id = txtId.getText().trim(); // Lấy ID từ trường nhập

        // Kiểm tra dữ liệu nhập vào
        if (id.isEmpty() || !id.matches("\\d+")) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập ID hợp lệ!");
            return;
        }

        // Hiển thị hộp thoại xác nhận trước khi xóa
        int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa người dùng với ID " + id + "?",
                "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return; // Dừng nếu người dùng chọn "Không"
        }

        try {
            Connect cn = new Connect();
            String query = "DELETE FROM users WHERE userId = ?";
            Object[] argv = {Integer.parseInt(id)};
            int rs = cn.executeUpdateQuery(query, argv);

            if (rs > 0) {
                JOptionPane.showMessageDialog(null, "Xóa thành công!");
                clearText(); // Xóa dữ liệu nhập liệu
            } else {
                JOptionPane.showMessageDialog(null, "Không tìm thấy người dùng với ID đã nhập!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Xóa thất bại! Lỗi: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void clearText() {
        txtId.setText("");
        txtName.setText("");
        txtEmail.setText("");
        txtPassword.setText("");
        txtPhone.setText("");
        boxRole.setSelectedIndex(0);
        txtEployeesID.setText("");
    }

    //kiêmr tra id, name đã tồn tại chưa
    public boolean isUserExist(String username) {
        try {
            Connect cn = new Connect();

            // Kiểm tra nếu posId đã tồn tại
            String query = "SELECT userId FROM users WHERE username = ?";
            ResultSet resultSet = cn.selectQuery(query, new Object[]{username});

            // Nếu kết quả có dữ liệu, tức là tồn tại
            if (resultSet.next()) {
                return true;  // posId hoặc posName đã tồn tại
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi kiểm tra dữ liệu: " + e.getMessage());
            System.out.println(e);
        }

        return false;  // Nếu không tìm thấy, thì không tồn tại
    }

    private int getEmpIdByName(String empName) throws SQLException {
        Connect cn = new Connect(); // Kết nối
        String query = "SELECT empId FROM employees WHERE name = ?";
        ResultSet rs = cn.selectQuery(query, new Object[]{empName}); // Truy vấn với tham số động

        if (rs.next()) {
            return rs.getInt("empId"); // Trả về empId nếu tìm thấy
        } else {
            throw new SQLException("Không tìm thấy nhân viên với tên: " + empName);
        }
    }

    //lay box role
    private void boxRoles() {
        get.loadRoleToComboBox(boxRole);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panInput = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        txtPassword = new javax.swing.JTextField();
        txtId = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtPhone = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtEployeesID = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        boxRole = new javax.swing.JComboBox<>();
        jButton6 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jButton7 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbUser = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setPreferredSize(new java.awt.Dimension(975, 650));
        setVisible(true);

        jLabel1.setText("User ID:");

        jLabel2.setText("Username:");

        jLabel3.setText("Password:");

        jLabel4.setText("Email:");

        jLabel5.setText("Phone:");

        txtId.setEnabled(false);

        txtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailActionPerformed(evt);
            }
        });

        jLabel6.setText("Employees:");

        txtEployeesID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEployeesIDActionPerformed(evt);
            }
        });

        jButton2.setText("Update");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Delete");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Exit");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel7.setText("Role:");

        jButton1.setText("Add");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        boxRole.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButton6.setText("Refresh");

        jButton7.setText("Exel");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panInputLayout = new javax.swing.GroupLayout(panInput);
        panInput.setLayout(panInputLayout);
        panInputLayout.setHorizontalGroup(
            panInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panInputLayout.createSequentialGroup()
                .addGap(0, 56, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 895, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(panInputLayout.createSequentialGroup()
                .addGroup(panInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panInputLayout.createSequentialGroup()
                        .addGroup(panInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panInputLayout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panInputLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jButton7)))
                        .addGap(30, 30, 30)
                        .addGroup(panInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addGroup(panInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel7)
                                .addComponent(jLabel3)))
                        .addGap(18, 18, 18)
                        .addGroup(panInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtPassword)
                            .addComponent(boxRole, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(90, 90, 90)
                        .addGroup(panInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panInputLayout.createSequentialGroup()
                                .addGap(90, 90, 90)
                                .addComponent(txtEployeesID, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panInputLayout.createSequentialGroup()
                                .addGroup(panInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE))
                                .addGroup(panInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panInputLayout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panInputLayout.createSequentialGroup()
                                        .addGap(16, 16, 16)
                                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(panInputLayout.createSequentialGroup()
                        .addGap(87, 87, 87)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(80, 80, 80)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(80, 80, 80)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(80, 80, 80)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(80, 80, 80)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panInputLayout.setVerticalGroup(
            panInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panInputLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(panInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panInputLayout.createSequentialGroup()
                        .addGroup(panInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panInputLayout.createSequentialGroup()
                                .addGroup(panInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(25, 25, 25)
                                .addGroup(panInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(25, 25, 25)
                                .addGroup(panInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(boxRole, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(panInputLayout.createSequentialGroup()
                                .addGap(105, 105, 105)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panInputLayout.createSequentialGroup()
                                .addGroup(panInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtEployeesID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(25, 25, 25)
                                .addGroup(panInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(25, 25, 25)
                                .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE))
                    .addGroup(panInputLayout.createSequentialGroup()
                        .addGroup(panInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton7)
                        .addGap(53, 53, 53)))
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        tbUser.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "User ID", "Username", "Password", "Email", "Phone", "Employees", "Role"
            }
        ));
        tbUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbUserMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbUser);
        if (tbUser.getColumnModel().getColumnCount() > 0) {
            tbUser.getColumnModel().getColumn(0).setMinWidth(40);
            tbUser.getColumnModel().getColumn(0).setPreferredWidth(40);
            tbUser.getColumnModel().getColumn(0).setMaxWidth(50);
            tbUser.getColumnModel().getColumn(6).setMinWidth(40);
        }

        jButton5.setText("+ Create");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton5)
                .addGap(86, 86, 86))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(panInput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        addUser();
        getUser();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        updateUser();
        getUser();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void tbUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbUserMouseClicked
        int i = tbUser.getSelectedRow();
        if (i >= 0 && tbUser.getValueAt(i, 0) != null) {
            String id = tbUser.getValueAt(i, 0).toString();
            String name = tbUser.getValueAt(i, 1).toString();
            String pass = tbUser.getValueAt(i, 2).toString();
            String email = tbUser.getValueAt(i, 3).toString();
            String phone = tbUser.getValueAt(i, 4).toString();
            String employees = tbUser.getValueAt(i, 5).toString();
            String roleName = tbUser.getValueAt(i, 6).toString();

            // Cập nhật các trường trong UI
            txtId.setText(id);
            txtName.setText(name);
            txtPassword.setText(pass);
            txtEmail.setText(email);
            txtPhone.setText(phone);
            txtEployeesID.setText(employees);
            boxRole.setSelectedItem(roleName);

            for (int j = 0; j < boxRole.getItemCount(); j++) {
                String item = boxRole.getItemAt(j).toString();
                // So sánh trực tiếp với tên vai trò từ bảng
                if (item.endsWith(" - " + roleName)) {
                    boxRole.setSelectedIndex(j); // Chọn mục tương ứng
                    break;
                }
            }
            panInput.setVisible(true);
        }
    }//GEN-LAST:event_tbUserMouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        deleteUser();
        getUser();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        panInput.setVisible(true);
        clearText();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void txtEployeesIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEployeesIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEployeesIDActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        ExportUtils.exportTableToExcel(tbUser);
    }//GEN-LAST:event_jButton7ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> boxRole;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JPanel panInput;
    private javax.swing.JTable tbUser;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtEployeesID;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPassword;
    private javax.swing.JTextField txtPhone;
    // End of variables declaration//GEN-END:variables
}
