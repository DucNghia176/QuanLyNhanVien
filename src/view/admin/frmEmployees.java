/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package view.admin;

import dto.Connect;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import process.get;

/**
 *
 * @author Asus
 */
public class frmEmployees extends javax.swing.JInternalFrame {

    /**
     * Creates new form Employees
     */
    public frmEmployees() {
        initComponents();

        getEmployees();
        boxDerpartment();
        boxPosition();
    }

    public void getEmployees() {
        try {
            Connect cn = new Connect();
            DefaultTableModel dt = (DefaultTableModel) tbEmployees.getModel();
            dt.setRowCount(0); // Làm sạch bảng trước khi thêm dữ liệu mới

            // Câu lệnh truy vấn để lấy dữ liệu từ Employees và Users
            String query = "SELECT employees.empId, employees.name, employees.dob, employees.gender, "
                    + "employees.sal, positions.posName, departments.deptName,"
                    + "users.email, users.phone "
                    + "FROM employees "
                    + "JOIN users ON employees.empId = users.empId "
                    + "JOIN positions ON employees.posId = positions.posId "
                    + "JOIN departments ON employees.deptId = departments.deptId";

            try (ResultSet resultSet = cn.selectQuery(query, new Object[0])) {
                while (resultSet.next()) {
                    Vector v = new Vector();
                    v.add(resultSet.getString("empId"));          // empId
                    v.add(resultSet.getString("name"));        // Name
                    v.add(resultSet.getString("dob"));         // Date of birth (dob)
                    v.add(resultSet.getString("gender"));      // Gender
                    v.add(resultSet.getString("sal"));  // Salary with formatted value
                    v.add(resultSet.getString("posName"));     // Position (from positions table)
                    v.add(resultSet.getString("deptName"));    // Department (from departments table)                              
                    v.add(resultSet.getString("email"));       // Email
                    v.add(resultSet.getString("phone"));
                    dt.addRow(v); // Thêm Vector vào bảng
                }
                System.out.println("Lấy dữ liệu thành công");
            }

            cn.close(); // Đóng kết nối
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi lấy dữ liệu: " + e.getMessage());
            System.out.println(e);
        }
    }
    
    // Làm sạch dữ liệu sau khi thêm
    private void clearInputFields() {
        txtName.setText("");
        txtDate.setDate(null);
        boxGender.setSelectedIndex(0);
        boxPosition.setSelectedIndex(0);
        boxDerpartment.setSelectedIndex(0);
        txtSalary.setText("");
    }

    //lấy dữ liệu từ department
    private void boxDerpartment() {
        get.loadDepartmentsToComboBox(boxDerpartment);
    }

    private void boxPosition() {
        get.loadPositionsToComboBox(boxPosition);
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
        jTextField7 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbEmployees = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
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
        Refet = new javax.swing.JButton();

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
                .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 453, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btSearch)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btSearch)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                "ID", "Name", "Date", "Gender", "Salary", "Position", "Department", "Email", "Phone"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(44, 44, 44))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(94, 94, 94)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtId, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
                    .addComponent(txtName)
                    .addComponent(txtDate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(100, 100, 100)
                        .addComponent(jLabel6)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                    .addComponent(txtPhone)
                    .addComponent(boxGender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(34, 34, 34)
                        .addComponent(boxPosition, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(44, 44, 44)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(boxDerpartment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSalary, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(100, 100, 100))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(boxPosition, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(boxDerpartment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(jLabel9))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(txtSalary, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(boxGender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(22, 22, 22)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel3)
                                .addComponent(jLabel6)
                                .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(37, Short.MAX_VALUE))
        );

        btAdd.setText("Add");
        btAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAddActionPerformed(evt);
            }
        });

        btUpdate.setText("Update");

        btDelete.setText("Delete");

        btExit.setText("Exit");
        btExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btExitActionPerformed(evt);
            }
        });

        Refet.setText("jButton1");
        Refet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RefetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
            .addGroup(layout.createSequentialGroup()
                .addComponent(btAdd)
                .addGap(81, 81, 81)
                .addComponent(btUpdate)
                .addGap(88, 88, 88)
                .addComponent(btDelete)
                .addGap(103, 103, 103)
                .addComponent(btExit)
                .addGap(58, 58, 58)
                .addComponent(Refet)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btAdd)
                        .addComponent(btUpdate))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btDelete)
                        .addComponent(btExit)
                        .addComponent(Refet)))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btSearchActionPerformed

    private void btExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btExitActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btExitActionPerformed

    private void btAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAddActionPerformed
        // TODO add your handling code here:
//        AddActionPerformed();
        getEmployees();
    }//GEN-LAST:event_btAddActionPerformed

    private void tbEmployeesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbEmployeesMouseClicked
        int i = tbEmployees.getSelectedRow();
        if (i >= 0) {
            // Lấy giá trị của từng cột và kiểm tra null trước khi gọi toString()
            String id = tbEmployees.getValueAt(i, 0) != null ? tbEmployees.getValueAt(i, 0).toString() : "";
            String name = tbEmployees.getValueAt(i, 1) != null ? tbEmployees.getValueAt(i, 1).toString() : "";
            String dob = tbEmployees.getValueAt(i, 2) != null ? tbEmployees.getValueAt(i, 2).toString() : "";
            String gender = tbEmployees.getValueAt(i, 3) != null ? tbEmployees.getValueAt(i, 3).toString() : "";
            String email = tbEmployees.getValueAt(i, 7) != null ? tbEmployees.getValueAt(i, 7).toString() : "";
            String phone = tbEmployees.getValueAt(i, 8) != null ? tbEmployees.getValueAt(i, 8).toString() : "";
            String posId = tbEmployees.getValueAt(i, 5) != null ? tbEmployees.getValueAt(i, 5).toString() : "";
            String sal = tbEmployees.getValueAt(i, 4) != null ? tbEmployees.getValueAt(i, 4).toString() : "";
            String deptId = tbEmployees.getValueAt(i, 6) != null ? tbEmployees.getValueAt(i, 6).toString() : "";

            // Cập nhật các trường trong UI
            txtId.setText(id);
            txtName.setText(name);

            // Cập nhật ngày sinh
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  // Định dạng ngày từ cơ sở dữ liệu
                java.util.Date date = sdf.parse(dob);
                txtDate.setDate(date);  // Cập nhật vào JDateChooser
            } catch (ParseException e) {
                e.printStackTrace();
            }

            // Cập nhật giới tính
            boxGender.setSelectedItem(gender); // Giả sử boxGender là JComboBox cho gender

            txtEmail.setText(email);
            txtPhone.setText(phone);

            // Cập nhật chức vụ (Position)
            for (int j = 0; j < boxPosition.getItemCount(); j++) {
                String item = boxPosition.getItemAt(j).toString();
                if (item.startsWith(posId + " - ")) { // Kiểm tra xem ID có trùng với mục không
                    boxPosition.setSelectedIndex(j); // Chọn mục tương ứng
                    break; // Thoát khỏi vòng lặp sau khi tìm thấy
                }
            }

            // Cập nhật lương
            txtSalary.setText(sal);

            // Cập nhật phòng ban (Department)
            for (int j = 0; j < boxDerpartment.getItemCount(); j++) {
                String item = boxDerpartment.getItemAt(j).toString();
                if (item.startsWith(deptId + " - ")) { // Kiểm tra xem ID có trùng với mục không
                    boxDerpartment.setSelectedIndex(j); // Chọn mục tương ứng
                    break; // Thoát khỏi vòng lặp sau khi tìm thấy
                }
            }
        }
    }//GEN-LAST:event_tbEmployeesMouseClicked

    private void RefetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefetActionPerformed
        // TODO add your handling code here:
        getEmployees();
        clearInputFields();
    }//GEN-LAST:event_RefetActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Refet;
    private javax.swing.JComboBox<String> boxDerpartment;
    private javax.swing.JComboBox<String> boxGender;
    private javax.swing.JComboBox<String> boxPosition;
    private javax.swing.JButton btAdd;
    private javax.swing.JButton btDelete;
    private javax.swing.JButton btExit;
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
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTable tbEmployees;
    private com.toedter.calendar.JDateChooser txtDate;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JTextField txtSalary;
    // End of variables declaration//GEN-END:variables

}