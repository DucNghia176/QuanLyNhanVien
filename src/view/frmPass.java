package view;

import view.admin.frmMain;
import view.user.frmMainUser;
import java.sql.Connection;
import dto.Connect;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import process.PasswordUtils;
import quanlynhanvien.frmRun1;

public class frmPass extends javax.swing.JFrame {

    private String email;  // Biến lưu trữ email
    private String pass;

    private frmMain mainFrm;
    private frmMainUser mainUFrm;
    
    public frmPass(String email, frmMainUser mainUFrm) {
        setUndecorated(true);
        initComponents();
        this.email = email;
        this.mainUFrm = mainUFrm;
        // Set email hoặc các cài đặt khác
        this.setLocationRelativeTo(null);
    }
    
    public frmPass(String email, frmMain mainFrm) {
        setUndecorated(true);
        initComponents();
        this.email = email;
        this.mainFrm = mainFrm;
        this.setLocationRelativeTo(null);
        // Set email hoặc các cài đặt khác
    }
    // Constructor nhận email
    public frmPass(String email, String pass) {
        initComponents();
        this.email = email;  // Lưu email
        this.pass = pass;

//         Hiển thị mật khẩu cũ trong txtOld
        txtOld.setText(pass);
        this.setLocationRelativeTo(null);
        btnExit.setVisible(false);
    }

    public frmPass(String email) {
        initComponents();
        this.email = email;
    }

    public frmPass() {
        initComponents();
        this.setLocationRelativeTo(null);
    }

    private boolean changePassword(String oldPassword, String newPassword, String confirmPassword) throws Exception {
        Connection conn = null;
        try {
            // Tạo đối tượng Connect để lấy kết nối
            Connect connect = new Connect();
            conn = connect.getConnection();

            // Kiểm tra mật khẩu cũ
            String sqlSelect = "SELECT password FROM users WHERE email = ?";
            PreparedStatement psSelect = conn.prepareStatement(sqlSelect);
            psSelect.setString(1, email);
            ResultSet rs = psSelect.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password");

                // Kiểm tra mật khẩu cũ với mật khẩu đã lưu
                if (!PasswordUtils.verifyPassword(oldPassword.toCharArray(), storedHash)) {
                    JOptionPane.showMessageDialog(this, "Mật khẩu cũ không chính xác!");
                    return false;
                }
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy người dùng với email này!");
                return false;
            }

            // Kiểm tra mật khẩu mới và mật khẩu xác nhận
            if (!newPassword.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "Mật khẩu mới và mật khẩu xác nhận không khớp!");
                return false;
            }

            // Tạo salt và băm mật khẩu mới
            byte[] salt = PasswordUtils.generateSalt();
            String hashedPassword = PasswordUtils.hashPassword(newPassword.toCharArray(), salt);

            // Cập nhật mật khẩu mới vào cơ sở dữ liệu
            String sqlUpdate = "UPDATE users SET password = ? WHERE email = ?";
            PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate);
            psUpdate.setString(1, hashedPassword);
            psUpdate.setString(2, email);

            int rowsAffected = psUpdate.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Đổi mật khẩu thành công!");
                return true;
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy người dùng với email này!");
                return false;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi kết nối cơ sở dữ liệu hoặc truy vấn!");
            Logger.getLogger(frmPass.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            // Đảm bảo đóng kết nối
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(frmPass.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        Right = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        Left = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtCf = new javax.swing.JPasswordField();
        jButton1 = new javax.swing.JButton();
        txtOld = new javax.swing.JPasswordField();
        jLabel4 = new javax.swing.JLabel();
        txtNew = new javax.swing.JPasswordField();
        btnExit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("LOGIN");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(800, 500));
        jPanel1.setLayout(null);

        Right.setBackground(new java.awt.Color(0, 102, 102));
        Right.setPreferredSize(new java.awt.Dimension(400, 500));

        jLabel6.setFont(new java.awt.Font("Showcard Gothic", 1, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Company Name");

        jLabel7.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(204, 204, 204));
        jLabel7.setText("360 Kim Mã, Ngọc Khánh, quận Ba Đình, Hà Nội");

        javax.swing.GroupLayout RightLayout = new javax.swing.GroupLayout(Right);
        Right.setLayout(RightLayout);
        RightLayout.setHorizontalGroup(
            RightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, RightLayout.createSequentialGroup()
                .addGap(0, 77, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addGap(40, 40, 40))
            .addGroup(RightLayout.createSequentialGroup()
                .addGroup(RightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(RightLayout.createSequentialGroup()
                        .addGap(103, 103, 103)
                        .addComponent(jLabel6))
                    .addGroup(RightLayout.createSequentialGroup()
                        .addGap(145, 145, 145)
                        .addComponent(jLabel5)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        RightLayout.setVerticalGroup(
            RightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RightLayout.createSequentialGroup()
                .addGap(136, 136, 136)
                .addComponent(jLabel5)
                .addGap(26, 26, 26)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 91, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addGap(78, 78, 78))
        );

        jPanel1.add(Right);
        Right.setBounds(0, 0, 400, 500);

        Left.setBackground(new java.awt.Color(255, 255, 255));
        Left.setMinimumSize(new java.awt.Dimension(400, 500));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 102));
        jLabel1.setText("PASSWORD");

        jLabel2.setBackground(new java.awt.Color(102, 102, 102));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("New Password");

        jLabel3.setBackground(new java.awt.Color(102, 102, 102));
        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Comfirm Password");

        jButton1.setBackground(new java.awt.Color(0, 102, 102));
        jButton1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Confirm");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel4.setBackground(new java.awt.Color(102, 102, 102));
        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Old Password");

        btnExit.setBackground(new java.awt.Color(0, 102, 102));
        btnExit.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnExit.setForeground(new java.awt.Color(255, 255, 255));
        btnExit.setText("Exit");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout LeftLayout = new javax.swing.GroupLayout(Left);
        Left.setLayout(LeftLayout);
        LeftLayout.setHorizontalGroup(
            LeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LeftLayout.createSequentialGroup()
                .addContainerGap(39, Short.MAX_VALUE)
                .addGroup(LeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LeftLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(87, 87, 87))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LeftLayout.createSequentialGroup()
                        .addGroup(LeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(LeftLayout.createSequentialGroup()
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(LeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtNew, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel4)
                                .addComponent(txtOld, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel2)
                                .addComponent(jLabel3)
                                .addComponent(txtCf, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18))))
        );
        LeftLayout.setVerticalGroup(
            LeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LeftLayout.createSequentialGroup()
                .addContainerGap(47, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(46, 46, 46)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtOld, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(txtNew, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtCf, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(LeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45))
        );

        jPanel1.add(Left);
        Left.setBounds(400, 0, 400, 500);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // Lấy giá trị từ các trường nhập liệu
        String oldPassword = new String(txtOld.getPassword());  // Mật khẩu cũ
        String newPassword = new String(txtNew.getPassword());     // Mật khẩu mới
        String confirmPassword = new String(txtCf.getPassword()); // Xác nhận mật khẩu

        // Gọi hàm changePassword để kiểm tra và thay đổi mật khẩu
        boolean result;
        try {
            result = changePassword(oldPassword, newPassword, confirmPassword);
            // Kiểm tra kết quả trả về từ hàm changePassword
            if (result) {
                if (mainFrm != null) {
                    mainFrm.dispose();// Đóng frmMain
                    frmRun1 run= new frmRun1();
                    run.setVisible(true);
                }
                if (mainUFrm != null) {
                    mainUFrm.dispose();// Đóng frmMain
                    frmRun1 run= new frmRun1();
                    run.setVisible(true);
                }
                frmLogin lg = new frmLogin();
                lg.setVisible(true);
                this.dispose();
            }
        } catch (Exception ex) {
            Logger.getLogger(frmPass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnExitActionPerformed

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(frmMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(frmMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(frmMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(frmMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                frmLogin loginFrame = new frmLogin();
//                loginFrame.setVisible(true);
//                loginFrame.setLocationRelativeTo(null);
//            }
//        });
//    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Left;
    private javax.swing.JPanel Right;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField txtCf;
    private javax.swing.JPasswordField txtNew;
    private javax.swing.JPasswordField txtOld;
    // End of variables declaration//GEN-END:variables

}
