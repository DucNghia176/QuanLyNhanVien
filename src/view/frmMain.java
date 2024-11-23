/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
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
                btUser.setVisible(false);
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

    //phóng to
    private void openInternalFrameFullScreen(javax.swing.JInternalFrame frame) {
        frame.setSize(myDesktop.getWidth(), myDesktop.getHeight()); // Thiết lập kích thước bằng myDesktop
        myDesktop.add(frame); // Thêm frame vào JDesktopPane
        frame.setVisible(true); // Hiển thị frame
        try {
            frame.setMaximum(true); // Phóng to toàn màn hình
        } catch (java.beans.PropertyVetoException ex) {
            ex.printStackTrace(); // In lỗi nếu có
        }
    }

    private void runForm(javax.swing.JInternalFrame form) {
        closeAllInternalFrames(); // Đóng tất cả các form hiện tại
//        form.setSize(myDesktop.getWidth(), myDesktop.getHeight()); // Thiết lập kích thước toàn màn hình?
        myDesktop.add(form); // Thêm form vào JDesktopPane
        form.setVisible(true); // Hiển thị form
//        try {
//            form.setMaximum(true); // Phóng to toàn màn hình
//        } catch (java.beans.PropertyVetoException ex) {
//            ex.printStackTrace(); // In lỗi nếu có
//        }
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
        btDerpartment = new javax.swing.JButton();
        btEmployess = new javax.swing.JButton();
        btUser = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        myDesktop = new javax.swing.JDesktopPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 153, 153));

        btDerpartment.setText("Derpartment");
        btDerpartment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btDerpartmentActionPerformed(evt);
            }
        });

        btEmployess.setText("Employess");
        btEmployess.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEmployessActionPerformed(evt);
            }
        });

        btUser.setText("User");
        btUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btUserActionPerformed(evt);
            }
        });

        jButton1.setText("Position");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btEmployess, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btDerpartment, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(btUser, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btDerpartment, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btEmployess, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 176, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout myDesktopLayout = new javax.swing.GroupLayout(myDesktop);
        myDesktop.setLayout(myDesktopLayout);
        myDesktopLayout.setHorizontalGroup(
            myDesktopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 838, Short.MAX_VALUE)
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
        runForm(new frmEmployees());
    }//GEN-LAST:event_btEmployessActionPerformed

    private void btDerpartmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btDerpartmentActionPerformed
        // TODO add your handling code here:
        runForm(new frmDepartment());
    }//GEN-LAST:event_btDerpartmentActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        runForm(new frmPosition());
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btUserActionPerformed
        // TODO add your handling code here:
        runForm(new frmUser());
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
            public void run() {
                frmMain frm = new frmMain();
                frm.setExtendedState(JFrame.MAXIMIZED_BOTH);
                frm.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btDerpartment;
    private javax.swing.JButton btEmployess;
    private javax.swing.JButton btUser;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JDesktopPane myDesktop;
    // End of variables declaration//GEN-END:variables
}
