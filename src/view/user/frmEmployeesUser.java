/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package view.user;

import dto.Connect;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
import process.get;

/**
 *
 * @author MY PC
 */
public class frmEmployeesUser extends javax.swing.JInternalFrame {

    /**
     * Creates new form frmEmployeesUser
     */
    private int empId;

    public frmEmployeesUser(int empId) {
        this.empId = empId;
        initComponents();
        getEmployees();

        loadComboBoxData();

        panInput.setVisible(false);
    }

    public void getEmployees() {
        new SwingWorker<Void, Vector<Object>>() {
            @Override
            protected Void doInBackground() throws Exception {
                Connect cn = null; // Kết nối CSDL
                ResultSet resultSet = null;
                DefaultTableModel dt = (DefaultTableModel) tbEmployees.getModel();
                dt.setRowCount(0); // Xóa dữ liệu bảng trước khi tải mới

                try {
                    cn = new Connect(); // Tạo kết nối
                    String query = "SELECT e.empId, e.name, e.dob, e.gender, "
                            + "p.posName, d.deptName, e.sal "
                            + "FROM employees e "
                            + "LEFT JOIN positions p ON e.posId = p.posId "
                            + "LEFT JOIN departments d ON e.deptId = d.deptId "
                            + "WHERE e.empId = ?"; // Truy vấn với tham số

                    // Sử dụng phương thức `selectQuery` của lớp `Connect`
                    Object[] params = {empId}; // Truyền tham số empId
                    resultSet = cn.selectQuery(query, params);

                    DecimalFormat df = new DecimalFormat("#,###");
                    Vector<Object> chunk = new Vector<>(); // Lưu nhóm dữ liệu
                    int count = 0;

                    while (resultSet.next()) {
                        Vector<Object> v = new Vector<>();
                        v.add(resultSet.getInt("empId"));       // Mã nhân viên
                        v.add(resultSet.getString("name"));     // Tên
                        v.add(resultSet.getString("dob"));      // Ngày sinh
                        v.add(resultSet.getString("gender"));   // Giới tính
                        v.add(resultSet.getString("posName"));  // Chức vụ
                        v.add(resultSet.getString("deptName")); // Phòng ban
                        v.add(df.format(resultSet.getDouble("sal"))); // Lương

                        chunk.add(v);
                        count++;

                        // Gửi dữ liệu mỗi 50 dòng
                        if (count % 50 == 0) {
                            publish(new Vector<>(chunk));
                            chunk.clear();
                        }
                    }

                    // Gửi các dòng còn lại
                    if (!chunk.isEmpty()) {
                        publish(new Vector<>(chunk));
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu: " + e.getMessage());
                    e.printStackTrace();
                } finally {
                    if (resultSet != null) {
                        resultSet.close(); // Đóng ResultSet
                    }
                    if (cn != null) {
                        cn.close(); // Đóng kết nối
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

    public void clearText() {
        txtId.setText("");
        txtName.setText("");
        txtDate.setDate(null);
        boxGender.setSelectedIndex(0);
        boxPosition.setSelectedIndex(0);
        boxDerpartment.setSelectedIndex(0);
        txtSalary.setText("");
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

    private void loadComboBoxData() {
        get.loadDepartmentsToComboBox(boxDerpartment);
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

        jScrollPane1 = new javax.swing.JScrollPane();
        tbEmployees = new javax.swing.JTable();
        panInput = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtDate = new com.toedter.calendar.JDateChooser();
        txtName = new javax.swing.JTextField();
        txtId = new javax.swing.JTextField();
        boxGender = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        boxPosition = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        boxDerpartment = new javax.swing.JComboBox<>();
        txtSalary = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();

        tbEmployees.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Name", "Date", "Gender", "Position", "Department", "Salary"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
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
        jScrollPane1.setViewportView(tbEmployees);

        jButton4.setText("Exit");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton6.setText("Refresh");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel1.setText("Emlpoyees ID:");

        jLabel2.setText("Name:");

        jLabel3.setText("Date");

        txtDate.setDateFormatString("dd/MM/yyyy");

        txtId.setEnabled(false);

        boxGender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {"Nam","Nữ"}));

        jLabel4.setText("Gender:");

        jLabel7.setText("Position:");

        boxPosition.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel8.setText("Department:");

        boxDerpartment.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel9.setText("Salary:");

        javax.swing.GroupLayout panInputLayout = new javax.swing.GroupLayout(panInput);
        panInput.setLayout(panInputLayout);
        panInputLayout.setHorizontalGroup(
            panInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panInputLayout.createSequentialGroup()
                .addGap(101, 101, 101)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(168, 168, 168)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panInputLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator1))
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
                .addGroup(panInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtId)
                    .addComponent(txtName)
                    .addComponent(txtDate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 136, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(27, 27, 27)
                .addComponent(boxGender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 164, Short.MAX_VALUE)
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
                        .addComponent(boxPosition, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panInputLayout.setVerticalGroup(
            panInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panInputLayout.createSequentialGroup()
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
                            .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(22, 22, 22)
                        .addGroup(panInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 61, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(panInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(panInput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        getEmployees();
        clearText();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void tbEmployeesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbEmployeesMouseClicked
        int i = tbEmployees.getSelectedRow();

        if (i >= 0 && tbEmployees.getValueAt(i, 0) != null) {
            String id = tbEmployees.getValueAt(i, 0).toString();
            String name = tbEmployees.getValueAt(i, 1).toString();
            String dob = tbEmployees.getValueAt(i, 2) != null ? tbEmployees.getValueAt(i, 2).toString() : "";
            String gender = tbEmployees.getValueAt(i, 3) != null ? tbEmployees.getValueAt(i, 3).toString() : "";
            String position = tbEmployees.getValueAt(i, 4) != null ? tbEmployees.getValueAt(i, 4).toString() : "";
            String department = tbEmployees.getValueAt(i, 5) != null ? tbEmployees.getValueAt(i, 5).toString() : "";
            String salary = tbEmployees.getValueAt(i, 6) != null ? tbEmployees.getValueAt(i, 6).toString() : "";

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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> boxDerpartment;
    private javax.swing.JComboBox<String> boxGender;
    private javax.swing.JComboBox<String> boxPosition;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel panInput;
    private javax.swing.JTable tbEmployees;
    private com.toedter.calendar.JDateChooser txtDate;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtSalary;
    // End of variables declaration//GEN-END:variables
}
