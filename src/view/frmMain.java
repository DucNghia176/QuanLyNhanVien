/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import quanlynhanvien.frmRun;

/**
 *
 * @author Asus
 */
public class frmMain extends javax.swing.JFrame {

    /**
     * Creates new form Main
     */
    private int role;

    public frmMain(int role) {
        initComponents();
        this.role = role;
        setLocationRelativeTo(null);
        setupRoleFeatures(); // Gọi hàm thiết lập quyền
    }

    private void setupRoleFeatures() {
        // Quản lý quyền truy cập theo role
        switch (role) {
            case 1: // Admin
                // Hiển thị đầy đủ các tính năng
                break;
            case 2: // User
                // Ẩn các chức năng dành cho admin

                break;
            case 3: // Manager
                // Ẩn một số tính năng không liên quan
                btHome.setVisible(false);
                btDerpartment.setVisible(false);
                break;
            default:
                JOptionPane.showMessageDialog(this, "Unknown role. Some features may not work.", "Warning", JOptionPane.WARNING_MESSAGE);
                break;
        }
    }

    //đóng tất cả jfarme
    private void closeAllInternalFrames() {
        for (javax.swing.JInternalFrame frame : myDesktop.getAllFrames()) {
            frame.dispose();
        }
    }

    private void runForm(javax.swing.JInternalFrame form, javax.swing.JButton button) {
        resetButtonColors();
        button.setBackground(new java.awt.Color(128, 0, 128));
        closeAllInternalFrames(); // Đóng tất cả các form hiện tại
        myDesktop.add(form); // Thêm form vào JDesktopPane
        form.setVisible(true); // Hiển thị form

        form.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent e) {
                resetButtonColors();  // Đặt lại màu cho tất cả các nút khi cửa sổ bị đóng
            }
        });
        
        try {
            form.setMaximum(true); // Phóng to cửa sổ
        } catch (java.beans.PropertyVetoException ex) {
            ex.printStackTrace(); // In lỗi nếu có
        }
    }

    private void resetButtonColors() {
        // Đặt lại màu nền mặc định cho tất cả các nút
        btHome.setBackground(new java.awt.Color(76, 175, 80));
        btEmployess.setBackground(new java.awt.Color(76, 175, 80));
        btUser.setBackground(new java.awt.Color(76, 175, 80));
        btDerpartment.setBackground(new java.awt.Color(76, 175, 80));
        btPosition.setBackground(new java.awt.Color(76, 175, 80));
    }

    public frmMain() {
        initComponents();
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
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        btHome = new javax.swing.JButton();
        btEmployess = new javax.swing.JButton();
        btUser = new javax.swing.JButton();
        btDerpartment = new javax.swing.JButton();
        btPosition = new javax.swing.JButton();
        myDesktop = new javax.swing.JDesktopPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(999, 535));

        jPanel1.setBackground(new java.awt.Color(82, 83, 81));

        jPanel2.setBackground(new java.awt.Color(255, 0, 0));
        jPanel2.setForeground(new java.awt.Color(255, 0, 0));

        jLabel2.setBackground(new java.awt.Color(232, 64, 60));
        jLabel2.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/tq.png"))); // NOI18N
        jLabel2.setText("QUẢN LÍ NHÂN VIÊN");
        jLabel2.setIconTextGap(3);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(13, 13, 13))
        );

        btHome.setBackground(new java.awt.Color(76, 175, 80));
        btHome.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btHome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/home.png"))); // NOI18N
        btHome.setText("Màn Hình Chính");
        btHome.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btHome.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btHomeActionPerformed(evt);
            }
        });

        btEmployess.setBackground(new java.awt.Color(76, 175, 80));
        btEmployess.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btEmployess.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/employ.png"))); // NOI18N
        btEmployess.setText("Employee Management ");
        btEmployess.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btEmployess.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btEmployess.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEmployessActionPerformed(evt);
            }
        });

        btUser.setBackground(new java.awt.Color(76, 175, 80));
        btUser.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/user.png"))); // NOI18N
        btUser.setText("Account Management");
        btUser.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btUser.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btUserActionPerformed(evt);
            }
        });

        btDerpartment.setBackground(new java.awt.Color(76, 175, 80));
        btDerpartment.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btDerpartment.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/derp.png"))); // NOI18N
        btDerpartment.setText("Derpartment Management ");
        btDerpartment.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btDerpartment.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btDerpartment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btDerpartmentActionPerformed(evt);
            }
        });

        btPosition.setBackground(new java.awt.Color(76, 175, 80));
        btPosition.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btPosition.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/pos.png"))); // NOI18N
        btPosition.setText("Position Management ");
        btPosition.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btPosition.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btPosition.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btPositionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 300, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(btDerpartment, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btPosition, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btUser, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btHome, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btEmployess, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(btHome, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btEmployess, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btUser, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btDerpartment, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btPosition, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(98, 98, 98))
        );

        javax.swing.GroupLayout myDesktopLayout = new javax.swing.GroupLayout(myDesktop);
        myDesktop.setLayout(myDesktopLayout);
        myDesktopLayout.setHorizontalGroup(
            myDesktopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 661, Short.MAX_VALUE)
        );
        myDesktopLayout.setVerticalGroup(
            myDesktopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(myDesktop))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(myDesktop)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btEmployessActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btEmployessActionPerformed
        // TODO add your handling code here:
        runForm(new frmEmployees(), btEmployess);
    }//GEN-LAST:event_btEmployessActionPerformed

    private void btDerpartmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btDerpartmentActionPerformed
        // TODO add your handling code here:
        runForm(new frmDepartment(), btDerpartment);
    }//GEN-LAST:event_btDerpartmentActionPerformed

    private void btPositionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btPositionActionPerformed
        // TODO add your handling code here:
        runForm(new frmPosition(), btPosition);
    }//GEN-LAST:event_btPositionActionPerformed

    private void btHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btHomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btHomeActionPerformed

    private void btUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btUserActionPerformed
        // TODO add your handling code here:
        runForm(new frmUser(), btUser);

        frmUser userForm = new frmUser();

        // Sử dụng invokeLater để đảm bảo form đã được vẽ xong
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Lấy kích thước của frmUser sau khi form đã được hiển thị
                System.out.println("Chiều rộng của frmUser: " + userForm.getWidth());
                System.out.println("Chiều cao của frmUser: " + userForm.getHeight());
            }
        });
    }//GEN-LAST:event_btUserActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frmMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                frmMain frm = new frmMain();
                frm.setExtendedState(JFrame.MAXIMIZED_BOTH);
                frm.setVisible(true);

                frm.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowOpened(java.awt.event.WindowEvent e) {
                        // In ra chiều rộng và chiều cao của myDesktop sau khi cửa sổ đã được mở
                        System.out.println("Chiều rộng của myDesktop: " + frm.myDesktop.getWidth());
                        System.out.println("Chiều cao của myDesktop: " + frm.myDesktop.getHeight());
                    }
                });

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btDerpartment;
    private javax.swing.JButton btEmployess;
    private javax.swing.JButton btHome;
    private javax.swing.JButton btPosition;
    private javax.swing.JButton btUser;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JDesktopPane myDesktop;
    // End of variables declaration//GEN-END:variables
}
