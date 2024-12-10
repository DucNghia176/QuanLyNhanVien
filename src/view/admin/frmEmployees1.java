/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package view.admin;

import dto.Connect;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
import process.get;

public class frmEmployees1 extends javax.swing.JInternalFrame {

    /**
     * Creates new form Employees
     */
    public frmEmployees1() {
        initComponents();
        loadComboBoxData();
        getEmployees();
        panInput.setVisible(false);
    }

    /**
     * Tải dữ liệu nhân viên từ cơ sở dữ liệu và hiển thị lên bảng.
     */
    public void getEmployees() {
        new SwingWorker<Void, Vector<Object>>() {
            @Override
            protected Void doInBackground() throws Exception {
                Connect cn = null;
                ResultSet resultSet = null;
                DefaultTableModel dt = (DefaultTableModel) tbEmployees.getModel();
                dt.setRowCount(0); // Xóa sạch bảng trước khi tải dữ liệu mới

                try {
                    cn = new Connect(); // Mở kết nối

                    // Truy vấn tất cả dữ liệu nhân viên
                    String query = "SELECT e.empId, e.name, e.dob, e.gender, "
                            + "p.posName, d.deptName, e.sal, u.email, u.phone "
                            + "FROM employees e "
                            + "LEFT JOIN users u ON e.empId = u.empId "
                            + "LEFT JOIN positions p ON e.posId = p.posId "
                            + "LEFT JOIN departments d ON e.deptId = d.deptId";

                    // Thực thi truy vấn và lấy kết quả
                    resultSet = cn.selectQuery(query, new Object[0]);

                    DecimalFormat df = new DecimalFormat("#,###");
                    int count = 0; // Đếm số dòng
                    Vector<Object> chunk = new Vector<>(); // Chứa dữ liệu từng nhóm

                    while (resultSet.next()) {
                        Vector<Object> v = new Vector<>();
                        v.add(resultSet.getInt("empId"));           // Mã nhân viên
                        v.add(resultSet.getString("name"));         // Tên
                        v.add(resultSet.getString("dob"));          // Ngày sinh
                        v.add(resultSet.getString("gender"));       // Giới tính
                        v.add(resultSet.getString("email"));        // Email
                        v.add(resultSet.getString("phone"));        // Điện thoại
                        v.add(resultSet.getString("posName"));      // Chức vụ
                        v.add(resultSet.getString("deptName"));     // Phòng ban

                        // Xử lý lương
                        Object salaryObj = resultSet.getObject("sal");
                        if (salaryObj == null || !(salaryObj instanceof Number)) {
                            v.add("Không xác định"); // Hiển thị khi không có lương
                        } else {
                            double salary = resultSet.getDouble("sal");
                            v.add(df.format(salary)); // Định dạng lương
                        }

                        chunk.add(v); // Thêm dòng vào chunk
                        count++;

                        // Gửi dữ liệu mỗi 50 dòng
                        if (count % 50 == 0) {
                            publish(new Vector<>(chunk));
                            chunk.clear(); // Xóa chunk
                        }
                    }

                    // Đảm bảo các dòng còn lại cũng được gửi lên
                    if (!chunk.isEmpty()) {
                        publish(new Vector<>(chunk));
                    }

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu: " + e.getMessage());
                    e.printStackTrace();
                } finally {
                    try {
                        if (resultSet != null) {
                            resultSet.close(); // Đóng kết quả
                        }
                        if (cn != null) {
                            cn.close(); // Đóng kết nối
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }

            @Override
            protected void process(java.util.List<Vector<Object>> chunks) {
                DefaultTableModel dt = (DefaultTableModel) tbEmployees.getModel();
                for (Vector<Object> chunk : chunks) {
                    for (Object row : chunk) {
                        dt.addRow((Vector<?>) row);
                    }
                }
            }

            @Override
            protected void done() {
                JOptionPane.showMessageDialog(null, "Dữ liệu nhân viên đã được tải thành công.");
            }
        }.execute(); // Bắt đầu công việc
    }

    /**
     * Tải dữ liệu vào ComboBox (Phòng ban và chức vụ).
     */
    private void loadComboBoxData() {
        get.loadDepartmentsToComboBox(boxDerpartment);
        get.loadPositionsToComboBox(boxPosition);
    }

    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtDate.setDate(null);
        boxGender.setSelectedIndex(0);
        txtEmail.setText("");
        txtPhone.setText("");
        boxPosition.setSelectedIndex(0);
        boxDerpartment.setSelectedIndex(0);
        txtSalary.setText("");
    }

    public void addEmployee() {
        // Lấy thông tin từ các trường nhập liệu
        String name = txtName.getText().trim();
        String dob = new SimpleDateFormat("yyyy-MM-dd").format(txtDate.getDate());  // Ngày sinh
        String gender = boxGender.getSelectedItem().toString();  // Giới tính
        String position = boxPosition.getSelectedItem().toString();  // Chức vụ
        String department = boxDerpartment.getSelectedItem().toString();  // Phòng ban
        String salaryText = txtSalary.getText().trim();  // Mức lương

        // Kiểm tra dữ liệu nhập vào
        if (name.isEmpty() || salaryText.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        // Kiểm tra xem lương có hợp lệ không
        double salary = 0;
        try {
            salary = Double.parseDouble(salaryText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Lương không hợp lệ!");
            return;
        }

        // Trích xuất mã phòng ban và mã chức vụ từ các ComboBox
        String deptId = department.split(" - ")[0];
        String posId = position.split(" - ")[0];

        // Tạo mảng đối số cho câu lệnh SQL
        Object[] params = {name, dob, gender, posId, deptId, salary};

        // Thực hiện thêm nhân viên vào cơ sở dữ liệu
        try {
            Connect cn = new Connect();
            String query = "INSERT INTO employees (name, dob, gender, posId, deptId, sal) VALUES (?, ?, ?, ?, ?, ?)";
            int result = cn.executeUpdateQuery(query, params);  // Thực thi câu lệnh SQL

            if (result > 0) {
                JOptionPane.showMessageDialog(null, "Thêm nhân viên thành công!");
                clearFields();  // Xóa các trường nhập liệu sau khi thêm thành công
            } else {
                JOptionPane.showMessageDialog(null, "Không có dữ liệu nào được thêm!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi thêm nhân viên! Lỗi: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void updateEmployee() {
        String empId = txtId.getText().trim(); // Lấy ID nhân viên từ trường nhập
        String name = txtName.getText().trim();
        String dob = txtDate.getDate() != null ? new SimpleDateFormat("yyyy-MM-dd").format(txtDate.getDate()) : ""; // Lấy ngày sinh từ JDateChooser
        String gender = boxGender.getSelectedItem().toString(); // Lấy giới tính từ JComboBox
        String email = txtEmail.getText().trim();
        String phone = txtPhone.getText().trim();
        String position = boxPosition.getSelectedItem().toString().split(" - ")[0]; // Tách ID vị trí (chức vụ)
        String department = boxDerpartment.getSelectedItem().toString().split(" - ")[0]; // Tách ID phòng ban

        // Kiểm tra dữ liệu nhập vào
        if (empId.isEmpty() || !empId.matches("\\d+")) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập ID nhân viên hợp lệ!");
            return;
        }
        if (name.isEmpty() || dob.isEmpty() || gender.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        // Mảng đối số để truyền vào câu lệnh SQL cho bảng employees
        Object[] argvEmployees = new Object[6];  // Không sử dụng phone nếu không có cột này trong bảng employees
        argvEmployees[0] = name;
        argvEmployees[1] = dob;
        argvEmployees[2] = gender;
        argvEmployees[3] = position;
        argvEmployees[4] = department;
        argvEmployees[5] = empId;

        // Mảng đối số để truyền vào câu lệnh SQL cho bảng users
        Object[] argvUsers = new Object[3];  // Sử dụng phone trong bảng users
        argvUsers[0] = email;
        argvUsers[1] = phone;
        argvUsers[2] = empId;

        try {
            Connect cn = new Connect();

            // Cập nhật thông tin trong bảng employees
            String queryEmployees = "UPDATE employees SET name = ?, dob = ?, gender = ?, posId = ?, deptId = ? WHERE empId = ?";
            int rsEmployees = cn.executeUpdateQuery(queryEmployees, argvEmployees);

            // Cập nhật thông tin trong bảng users
            String queryUsers = "UPDATE users SET email = ?, phone = ? WHERE empId = ?";
            int rsUsers = cn.executeUpdateQuery(queryUsers, argvUsers);

            if (rsEmployees > 0 && rsUsers > 0) {
                JOptionPane.showMessageDialog(null, "Cập nhật nhân viên thành công!");
                clearFields(); // Xóa dữ liệu nhập liệu
                getEmployees(); // Cập nhật lại danh sách nhân viên
            } else {
                JOptionPane.showMessageDialog(null, "Không có dữ liệu nào được cập nhật!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Cập nhật thất bại! Lỗi: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteEmployee() {
        String empId = txtId.getText().trim();  // Lấy ID nhân viên từ trường nhập

        // Kiểm tra nếu ID nhân viên hợp lệ
        if (empId.isEmpty() || !empId.matches("\\d+")) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập ID nhân viên hợp lệ!");
            return;
        }

        // Xác nhận xóa nhân viên
        int confirm = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn xóa nhân viên này?",
                "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.NO_OPTION) {
            return; // Nếu người dùng không xác nhận, không làm gì cả
        }

        // Mảng đối số để truyền vào câu lệnh SQL
        Object[] argv = {empId}; // Truyền empId vào câu lệnh SQL

        try {
            Connect cn = new Connect();

            // Xóa nhân viên khỏi bảng employees
            String queryEmployees = "DELETE FROM employees WHERE empId = ?";
            int rsEmployees = cn.executeUpdateQuery(queryEmployees, argv);

            // Xóa luôn thông tin trong bảng users nếu có
            String queryUsers = "DELETE FROM users WHERE empId = ?";
            System.out.println("Câu lệnh SQL xóa người dùng: " + queryUsers);  // In câu lệnh SQL để kiểm tra
            int rsUsers = cn.executeUpdateQuery(queryUsers, argv);

            // Kiểm tra kết quả xóa
            if (rsEmployees > 0) {
                JOptionPane.showMessageDialog(null, "Xóa nhân viên thành công!");
                clearFields();  // Xóa dữ liệu nhập liệu
                getEmployees(); // Cập nhật lại danh sách nhân viên
            } else {
                JOptionPane.showMessageDialog(null, "Không tìm thấy nhân viên để xóa!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Xóa thất bại! Lỗi: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void refreshEmployees() {
        try {
            // Tạo kết nối với cơ sở dữ liệu
            Connect cn = new Connect();

            // Truy vấn lấy danh sách nhân viên từ cơ sở dữ liệu
            String query = "SELECT e.empId, e.name, e.dob, e.gender, u.email, u.phone, p.posName, d.deptName, e.sal "
                    + "FROM employees e "
                    + "JOIN positions p ON e.posId = p.posId "
                    + "JOIN departments d ON e.deptId = d.deptId "
                    + "LEFT JOIN users u ON e.empId = u.empId";  // Sử dụng LEFT JOIN để lấy thông tin từ bảng users

            // Gọi selectQuery để lấy kết quả
            Object[] params = {}; // Không có tham số động trong truy vấn này
            ResultSet rs = cn.selectQuery(query, params);  // Thực thi truy vấn và lấy kết quả

            // Xóa hết dữ liệu cũ trong bảng
            DefaultTableModel model = (DefaultTableModel) tbEmployees.getModel();
            model.setRowCount(0);  // Xóa tất cả các dòng trong bảng

            // Định dạng lương
            DecimalFormat df = new DecimalFormat("#,###");  // Định dạng số với dấu phẩy phân cách

            // Duyệt qua kết quả trả về từ cơ sở dữ liệu và thêm vào bảng
            while (rs.next()) {
                int empId = rs.getInt("empId");
                String name = rs.getString("name");
                String dob = rs.getString("dob");
                String gender = rs.getString("gender");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String posName = rs.getString("posName");
                String deptName = rs.getString("deptName");
                double salary = rs.getDouble("sal");

                // Định dạng lại lương với DecimalFormat
                String formattedSalary = df.format(salary); // Định dạng lương

                // Thêm dòng vào bảng (tbEmployees) với lương đã định dạng
                model.addRow(new Object[]{empId, name, dob, gender, email, phone, posName, deptName, formattedSalary});
            }

            rs.close();  // Đóng kết quả trả về
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi làm mới danh sách nhân viên! Lỗi: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Hàm tìm kiếm nhân viên
    // Hàm tìm kiếm nhân viên
    public void searchEmployees() {
        String searchTerm = txtSearch.getText().trim();  // Lấy giá trị từ ô tìm kiếm

        if (searchTerm.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập từ khóa tìm kiếm!");
            return;
        }

        try {
            Connect cn = new Connect();

            // Truy vấn tìm kiếm nhân viên từ cơ sở dữ liệu
            String query = "SELECT e.empId, e.name, e.dob, e.gender, u.email, u.phone, p.posName, d.deptName, e.sal "
                    + "FROM employees e "
                    + "JOIN positions p ON e.posId = p.posId "
                    + "JOIN departments d ON e.deptId = d.deptId "
                    + "LEFT JOIN users u ON e.empId = u.empId "
                    + "WHERE (e.empId LIKE ? OR e.name LIKE ? OR e.dob LIKE ? OR e.gender LIKE ? "
                    + "OR u.email LIKE ? OR u.phone LIKE ? OR p.posName LIKE ? OR d.deptName LIKE ? "
                    + "OR CAST(e.sal AS VARCHAR) LIKE ?)";  // Thêm trường lương vào truy vấn

            // Các tham số tìm kiếm (không loại bỏ dấu tiếng Việt)
            Object[] params = {
                "%" + searchTerm + "%",
                "%" + searchTerm + "%",
                "%" + searchTerm + "%",
                "%" + searchTerm + "%",
                "%" + searchTerm + "%",
                "%" + searchTerm + "%",
                "%" + searchTerm + "%",
                "%" + searchTerm + "%",
                "%" + searchTerm + "%" // Lương (salary)
            };

            // Thực hiện truy vấn
            ResultSet rs = cn.selectQuery(query, params);

            // Xóa hết dữ liệu cũ trong bảng
            DefaultTableModel model = (DefaultTableModel) tbEmployees.getModel();
            model.setRowCount(0);  // Xóa tất cả các dòng trong bảng

            // Khởi tạo DecimalFormat để định dạng lương
            DecimalFormat df = new DecimalFormat("#,###");

            // Duyệt qua kết quả trả về từ cơ sở dữ liệu và thêm vào bảng
            while (rs.next()) {
                int empId = rs.getInt("empId");
                String name = rs.getString("name");
                String dob = rs.getString("dob");
                String gender = rs.getString("gender");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String posName = rs.getString("posName");
                String deptName = rs.getString("deptName");
                double salary = rs.getDouble("sal");

                // Định dạng lương
                String formattedSalary = df.format(salary);

                // Thêm dòng vào bảng (tbEmployees)
                model.addRow(new Object[]{empId, name, dob, gender, email, phone, posName, deptName, formattedSalary});
            }

            rs.close();  // Đóng kết quả trả về
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi tìm kiếm nhân viên! Lỗi: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btSearch = new javax.swing.JButton();
        txtSearch = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbEmployees = new javax.swing.JTable();
        panInput = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        txtName = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        boxGender = new javax.swing.JComboBox<>();
        boxPosition = new javax.swing.JComboBox<>();
        boxDerpartment = new javax.swing.JComboBox<>();
        txtSalary = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtPhone = new javax.swing.JTextField();
        txtDate = new com.toedter.calendar.JDateChooser();
        btAdd = new javax.swing.JButton();
        btUpdate = new javax.swing.JButton();
        btDelete = new javax.swing.JButton();
        btExit = new javax.swing.JButton();
        btRefresh = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        btSearch.setText("Search");
        btSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(130, 130, 130)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 453, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btSearch)
                .addContainerGap(277, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btSearch)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 3, Short.MAX_VALUE))
        );

        tbEmployees.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Name", "Date", "Gender", "Email", "Phone", "Position", "Department", "Salary"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tbEmployees.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbEmployeesMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbEmployees);
        if (tbEmployees.getColumnModel().getColumnCount() > 0) {
            tbEmployees.getColumnModel().getColumn(0).setMaxWidth(30);
        }

        jScrollPane1.setViewportView(jScrollPane2);

        jLabel1.setText("Emlpoyees ID:");

        jLabel2.setText("Name:");

        jLabel3.setText("Date");

        txtId.setEnabled(false);

        jLabel4.setText("Gender:");

        jLabel5.setText("Email:");

        jLabel6.setText("Phone:");

        jLabel7.setText("Position:");

        jLabel8.setText("Department:");

        jLabel9.setText("Salary:");

        boxGender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {"Nam","Nữ"}));

        boxPosition.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        boxDerpartment.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        txtDate.setDateFormatString("dd/MM/yyyy");

        btAdd.setText("Add");
        btAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAddActionPerformed(evt);
            }
        });

        btUpdate.setText("Update");
        btUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btUpdateActionPerformed(evt);
            }
        });

        btDelete.setText("Delete");
        btDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btDeleteActionPerformed(evt);
            }
        });

        btExit.setText("Exit");
        btExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btExitActionPerformed(evt);
            }
        });

        btRefresh.setText("Refresh");
        btRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRefreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panInputLayout = new javax.swing.GroupLayout(panInput);
        panInput.setLayout(panInputLayout);
        panInputLayout.setHorizontalGroup(
            panInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panInputLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panInputLayout.createSequentialGroup()
                        .addGroup(panInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(44, 44, 44))
                    .addGroup(panInputLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(94, 94, 94)))
                .addGroup(panInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panInputLayout.createSequentialGroup()
                        .addGroup(panInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtId, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
                            .addComponent(txtName)
                            .addComponent(txtDate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 71, Short.MAX_VALUE)
                        .addGroup(panInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addGap(27, 27, 27))
                    .addGroup(panInputLayout.createSequentialGroup()
                        .addComponent(btAdd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btUpdate)
                        .addGap(88, 88, 88)))
                .addGroup(panInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panInputLayout.createSequentialGroup()
                        .addGroup(panInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                            .addComponent(txtPhone)
                            .addComponent(boxGender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 100, Short.MAX_VALUE))
                    .addGroup(panInputLayout.createSequentialGroup()
                        .addComponent(btDelete)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btExit)
                        .addGap(6, 6, 6)))
                .addGroup(panInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panInputLayout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(panInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panInputLayout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                            .addGroup(panInputLayout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(44, 44, 44)))
                        .addGroup(panInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(boxDerpartment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSalary, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panInputLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(34, 34, 34)
                        .addGroup(panInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btRefresh)
                            .addComponent(boxPosition, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(100, 100, 100))
        );
        panInputLayout.setVerticalGroup(
            panInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panInputLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(panInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panInputLayout.createSequentialGroup()
                        .addGroup(panInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(boxPosition, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21)
                        .addGroup(panInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(boxDerpartment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(panInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panInputLayout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(jLabel9))
                            .addGroup(panInputLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(txtSalary, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(panInputLayout.createSequentialGroup()
                        .addGroup(panInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(boxGender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21)
                        .addGroup(panInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(22, 22, 22)
                        .addGroup(panInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel3)
                                .addComponent(jLabel6)
                                .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(18, 38, Short.MAX_VALUE)
                .addGroup(panInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btExit)
                    .addComponent(btRefresh)
                    .addComponent(btDelete)
                    .addComponent(btUpdate)
                    .addComponent(btAdd))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panInput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panInput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSearchActionPerformed
        // TODO add your handling code here:
        searchEmployees();
    }//GEN-LAST:event_btSearchActionPerformed

    private void tbEmployeesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbEmployeesMouseClicked
        int i = tbEmployees.getSelectedRow();

        if (i >= 0 && tbEmployees.getValueAt(i, 0) != null) {
            String id = tbEmployees.getValueAt(i, 0).toString();
            String name = tbEmployees.getValueAt(i, 1).toString();
            String dob = tbEmployees.getValueAt(i, 2) != null ? tbEmployees.getValueAt(i, 2).toString() : "";
            String gender = tbEmployees.getValueAt(i, 3) != null ? tbEmployees.getValueAt(i, 3).toString() : "";
            String email = tbEmployees.getValueAt(i, 4) != null ? tbEmployees.getValueAt(i, 4).toString() : "";
            String phone = tbEmployees.getValueAt(i, 5) != null ? tbEmployees.getValueAt(i, 5).toString() : "";
            String position = tbEmployees.getValueAt(i, 6) != null ? tbEmployees.getValueAt(i, 6).toString() : "";
            String department = tbEmployees.getValueAt(i, 7) != null ? tbEmployees.getValueAt(i, 7).toString() : "";
            String salary = tbEmployees.getValueAt(i, 8) != null ? tbEmployees.getValueAt(i, 8).toString() : "";

            txtId.setText(id);
            txtName.setText(name);

            try {
                if (!dob.isEmpty()) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = sdf.parse(dob);
                    txtDate.setDate(date);
                } else {
                    txtDate.setDate(null);
                }
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(this, "Ngày sinh không hợp lệ: " + dob);
            }

            boxGender.setSelectedItem(gender);
            txtEmail.setText(email);
            txtPhone.setText(phone);

            if (boxPosition.getItemCount() == 0) {
                loadComboBoxData();
            }

            if (!position.isEmpty()) {
                boolean foundPosition = false;
                for (int j = 0; j < boxPosition.getItemCount(); j++) {
                    if (boxPosition.getItemAt(j).toString().contains(position)) {
                        boxPosition.setSelectedIndex(j);
                        foundPosition = true;
                        break;
                    }
                }
                if (!foundPosition) {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy chức vụ: " + position);
                }
            }

            if (!department.isEmpty()) {
                boolean foundDepartment = false;
                for (int j = 0; j < boxDerpartment.getItemCount(); j++) {
                    if (boxDerpartment.getItemAt(j).toString().contains(department)) {
                        boxDerpartment.setSelectedIndex(j);
                        foundDepartment = true;
                        break;
                    }
                }
                if (!foundDepartment) {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy phòng ban: " + department);
                }
            }

            txtSalary.setText(salary);
            panInput.setVisible(true);

        }
    }//GEN-LAST:event_tbEmployeesMouseClicked

    private void btRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRefreshActionPerformed
        refreshEmployees();
    }//GEN-LAST:event_btRefreshActionPerformed

    private void btExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btExitActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btExitActionPerformed

    private void btAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAddActionPerformed
        // TODO add your handling code here:
        // AddActionPerformed();
        addEmployee();
        getEmployees();
    }//GEN-LAST:event_btAddActionPerformed

    private void btUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btUpdateActionPerformed
        // TODO add your handling code here:
        updateEmployee();
    }//GEN-LAST:event_btUpdateActionPerformed

    private void btDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btDeleteActionPerformed
        // TODO add your handling code here:
        deleteEmployee();
    }//GEN-LAST:event_btDeleteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> boxDerpartment;
    private javax.swing.JComboBox<String> boxGender;
    private javax.swing.JComboBox<String> boxPosition;
    private javax.swing.JButton btAdd;
    private javax.swing.JButton btDelete;
    private javax.swing.JButton btExit;
    private javax.swing.JButton btRefresh;
    private javax.swing.JButton btSearch;
    private javax.swing.JButton btUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel panInput;
    private javax.swing.JTable tbEmployees;
    private com.toedter.calendar.JDateChooser txtDate;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JTextField txtSalary;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables

}
