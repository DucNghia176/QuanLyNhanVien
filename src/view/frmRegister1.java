package view;

import dto.Connect;
import entity.UserCache;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import process.check;
import view.frmLogin;

public class frmRegister1 extends javax.swing.JFrame {

    private String name;
    private String password;
    private String email;
    private String phone;

    public frmRegister1() {
        setUndecorated(true);
        initComponents();
        this.setLocationRelativeTo(null);

        // Lấy thông tin người dùng đã lưu tạm thời từ UserCache
        String username = UserCache.getUsername();
        String password = UserCache.getPassword();
        String email = UserCache.getEmail();
        String phone = UserCache.getPhone();

        setTitle(config.AppConfig.appTitle);
        setResizable(false);
        // Hiển thị thông tin người dùng vào các trường trong form nếu có
//        txtUsername.setText(username != null ? username : "");
//        txtEmail.setText(email != null ? email : "");
//        txtPhone.setText(phone != null ? phone : "");
    }

    // Constructor với tham số
    public frmRegister1(String name, String password, String email, String phone) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.phone = phone;
        initComponents();
        pack();
        setSize(412, 433);
        setLocationRelativeTo(null);
    }

    // Phương thức chung để lưu dữ liệu vào cơ sở dữ liệu
    private boolean saveDataToDatabase(String sql, Object[] params) {
        try {
            Connect cn = new Connect();
            int result = cn.executeUpdateQuery(sql, params);
            return result > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi cơ sở dữ liệu: " + e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Có lỗi xảy ra: " + e.getMessage());
        }
        return false;
    }

    // Phương thức lưu người dùng vào cơ sở dữ liệu với empID
    public void saveUserToDatabase(int empId) {
        int roleId = 2;
        Object[] params = new Object[]{name, password, email, phone, roleId, empId}; // Thêm empID vào tham số
        String sql = "INSERT INTO users (username, password, email, phone, roleId, empId) VALUES (?, ?, ?, ?, ?, ?)";
        if (saveDataToDatabase(sql, params)) {
            JOptionPane.showMessageDialog(null, "Thêm mới thành công!");
        }
    }

    // Phương thức tạo và lưu thông tin đăng ký nhân viên
    public boolean createRegister() {
        String fullname = txtFullname.getText().trim();
        Date dob = jdate.getDate();
        String gender = (String) boxGender.getSelectedItem();

        // Kiểm tra dữ liệu nhập vào
        if (fullname.isEmpty() || dob == null || gender.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
            return false;
        }

        // Kiểm tra ngày sinh không phải là ngày trong tương lai
        if (dob.after(new Date())) {
            JOptionPane.showMessageDialog(this, "Ngày sinh không được trong tương lai!");
            return false;
        }

        // Định dạng ngày sinh
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(dob);

        // Lưu nhân viên vào bảng employees
        try {
            Connect cn = new Connect();
            int empId = getLastInsertedEmpIdAndInsert(cn, fullname, formattedDate, gender);

            if (empId != -1) {
                saveUserToDatabase(empId); // Lưu người dùng với empId
                JOptionPane.showMessageDialog(this, "Đăng ký thành công: " + fullname);
                clearText();
                return true;
            } else {
                JOptionPane.showMessageDialog(this, "Không thể lấy empId sau khi thêm nhân viên!");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi cơ sở dữ liệu: " + e.getMessage());
        }

        return false;
    }
    // Phương thức lấy empID của nhân viên vừa được thêm vào bảng employees

    private int getLastInsertedEmpIdAndInsert(Connect cn, String fullname, String formattedDate, String gender) throws SQLException {
        String sql = "INSERT INTO employees (name, dob, gender) OUTPUT INSERTED.empId VALUES (?, ?, ?)";
        try (ResultSet rs = cn.selectQuery(sql, new Object[]{fullname, formattedDate, gender})) {
            if (rs != null && rs.next()) {
                return rs.getInt(1); // Lấy empId trả về
            }
        }
        return -1; // Không tìm thấy empId
    }

    public void clearText() {
        txtFullname.setText("");
        jdate.setDate(null);
        boxGender.setSelectedIndex(0);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtFullname = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jdate = new com.toedter.calendar.JDateChooser();
        boxGender = new javax.swing.JComboBox<>();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sign Up");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(800, 500));
        jPanel1.setLayout(null);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setBackground(new java.awt.Color(0, 102, 102));
        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel4.setText("Complete Registration");

        jLabel5.setBackground(new java.awt.Color(102, 102, 102));
        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Full name");

        txtFullname.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtFullname.setForeground(new java.awt.Color(102, 102, 102));

        jLabel6.setBackground(new java.awt.Color(102, 102, 102));
        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Date");

        jLabel7.setBackground(new java.awt.Color(102, 102, 102));
        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Gender:");

        jButton1.setBackground(new java.awt.Color(0, 102, 102));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Register");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jdate.setDateFormatString("dd-MM-yyyy");

        boxGender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {"Nam","Nữ"})
        );

        jButton2.setText("Back");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(43, 43, 43))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(boxGender, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jdate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(txtFullname, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addGap(25, 25, 25)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtFullname, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jdate, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(boxGender, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel1.add(jPanel3);
        jPanel3.setBounds(0, 0, 380, 410);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 454, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            boolean success = createRegister();
            if (success) {
                this.setVisible(false);
                new frmLogin().setVisible(true); // Chuyển sang form đăng nhập
            } else {
//                JOptionPane.showMessageDialog(this, "Đăng ký không thành công. Vui lòng thử lại.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Có lỗi xảy ra: " + ex.getMessage());
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        frmRegister registerForm = new frmRegister();
        registerForm.setVisible(true);
        registerForm.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> boxGender;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private com.toedter.calendar.JDateChooser jdate;
    private javax.swing.JTextField txtFullname;
    // End of variables declaration//GEN-END:variables
}
