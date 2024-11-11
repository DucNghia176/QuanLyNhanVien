/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package view;

import dto.Connect;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Asus
 */
public class frmRole extends javax.swing.JInternalFrame {

    /**
     * Creates new form frmDepartment
     */
    public frmRole() {
        initComponents();
        getRole();

        //ẩn các trường đi
//        panInput.setVisible(false);
    }

    public void getRole() {
        try {
            Connect cn = new Connect();
            DefaultTableModel dt = (DefaultTableModel) tbDepartment.getModel();
            dt.setRowCount(0); // Làm sạch bảng trước khi thêm dữ liệu mới

            // Câu lệnh truy vấn để lấy dữ liệu từ bảng roles
            String query = "SELECT roleId, roleName FROM roles";

            try (ResultSet resultSet = cn.selectQuery(query, new Object[0])) {
                while (resultSet.next()) {
                    Vector v = new Vector();
                    v.add(resultSet.getInt("roleId"));  // Thêm roleId vào Vector
                    v.add(resultSet.getString("roleName"));  // Thêm roleName vào Vector
                    dt.addRow(v);  // Thêm Vector vào bảng
                }
                System.out.println("Lấy dữ liệu thành công từ role");

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Lỗi khi lấy dữ liệu: " + e.getMessage());
                System.out.println(e);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi kết nối cơ sở dữ liệu: " + e.getMessage());
            System.out.println(e);
        }
    }

    public void addRole() {
        String id = txtId.getText().trim();
        String name = txtName.getText().trim();

        // Kiểm tra nếu mã phòng hợp lệ
        if (id.isEmpty() || !id.matches("\\d+")) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập mã phòng hợp lệ!");
            return;  // Dừng thực hiện nếu có lỗi
        }

        Object[] argv = new Object[2];
        argv[0] = Integer.parseInt(id);
        argv[1] = name;

        try {
            Connect cn = new Connect();
            int rs = cn.executeQuery("INSERT INTO roles (roleId, roleName) VALUES (?, ?)", argv);
            if (rs > 0) {
                JOptionPane.showMessageDialog(null, "Thêm mới thành công dữ liệu id:" + id);

                // Xóa dữ liệu trong các trường nhập liệu sau khi thêm thành công
                clearText();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Thêm mới thất bại dữ liệu id:" + id + e);
            System.out.println(e);
        }
    }

    public int updateRole() {
        // Lấy roleId và roleName từ các trường nhập liệu
        String roleId = txtId.getText().trim();
        String roleName = txtName.getText().trim();

        if (roleId.isEmpty() || !roleId.matches("\\d+")) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập mã phòng hợp lệ!");
            return 0;
        }

        if (roleName.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập tên phòng!");
            return 0;
        }

        Connect cn = null; // Khai báo kết nối

        try {
            cn = new Connect();

            // 1. Kiểm tra xem roleId có tồn tại trong cơ sở dữ liệu không
            String selectQuery = "SELECT roleId FROM roles WHERE roleId = ?";
            ResultSet resultSet = cn.selectQuery(selectQuery, new Object[]{Integer.valueOf(roleId)});
            if (!resultSet.next()) {
                JOptionPane.showMessageDialog(null, "Mã phòng không tồn tại trong cơ sở dữ liệu!");
                return 0;
            }

            // 2. Cập nhật thông tin phòng ban trong bảng roles
            String deleteDepartment = "UPDATE roles SET roleName = ? WHERE roleId = ?";
            Object[] roleArgs = new Object[]{roleName, Integer.valueOf(roleId)};
            int roleResult = cn.executeQuery(deleteDepartment, roleArgs);

            if (roleResult > 0) {
                JOptionPane.showMessageDialog(null, "Cập nhật thông tin phòng ban thành công!");
                clearText();
                return roleResult; // Trả về số bản ghi đã cập nhật trong roles
            } else {
                // Nếu không cập nhật được phòng ban
                JOptionPane.showMessageDialog(null, "Không thể cập nhật phòng ban.");
                return 0;
            }

        } catch (SQLException e) {
            // Xử lý lỗi SQL nếu có
            JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi SQL: " + e.getMessage());
            System.out.println(e);
            return 0;

        } catch (Exception e) {
            // Xử lý lỗi chung
            JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi: " + e.getMessage());
            System.out.println(e);
            return 0;

        } finally {
            // Đóng kết nối cơ sở dữ liệu nếu được mở
            if (cn != null) {
                try {
                    cn.close(); // Đảm bảo đóng kết nối
                } catch (SQLException e) {
                    System.out.println("Không thể đóng kết nối: " + e.getMessage());
                }
            }
        }
    }

    public int deleteRole() {
        // Lấy roleId từ các trường nhập liệu
        String roleId = txtId.getText().trim();
        String roleName = txtName.getText().trim();
        Connect cn = null; // Khai báo kết nối

        try {
            cn = new Connect();

            // 1. Kiểm tra xem roleId có tồn tại trong cơ sở dữ liệu không
            String selectQuery = "SELECT roleId FROM roles WHERE roleId = ?";
            ResultSet resultSet = cn.selectQuery(selectQuery, new Object[]{Integer.valueOf(roleId)});
            if (!resultSet.next()) {
                JOptionPane.showMessageDialog(null, "Mã phòng không tồn tại trong cơ sở dữ liệu!");
                return 0;
            }

            // 2. Xóa thông tin phòng ban trong bảng roles
            String deleteDepartment = "DELETE FROM roles WHERE roleId = ?";
            Object[] roleArgs = new Object[]{Integer.valueOf(roleId)};
            int roleResult = cn.executeQuery(deleteDepartment, roleArgs);

            if (roleResult > 0) {
                JOptionPane.showMessageDialog(null, "Xóa phòng ban thành công!");
                clearText();
                return roleResult; // Trả về số bản ghi đã xóa trong roles
            } else {
                // Nếu không xóa được phòng ban
                JOptionPane.showMessageDialog(null, "Không thể xóa phòng ban.");
                return 0;
            }

        } catch (SQLException e) {
            // Xử lý lỗi SQL nếu có
            JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi SQL: " + e.getMessage());
            System.out.println(e);
            return 0;

        } catch (Exception e) {
            // Xử lý lỗi chung
            JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi: " + e.getMessage());
            System.out.println(e);
            return 0;

        } finally {
            // Đóng kết nối cơ sở dữ liệu nếu được mở
            if (cn != null) {
                try {
                    cn.close(); // Đảm bảo đóng kết nối
                } catch (SQLException e) {
                    System.out.println("Không thể đóng kết nối: " + e.getMessage());
                }
            }
        }
    }

    public void clearText() {
        txtId.setText("");
        txtName.setText("");
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
        tbDepartment = new javax.swing.JTable();
        panInput = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        txtName = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);

        tbDepartment.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"1", "ADMIN"},
                {"2", "USER"}
            },
            new String [] {
                "Role ID", "Role Name"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tbDepartment.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDepartmentMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbDepartment);
        if (tbDepartment.getColumnModel().getColumnCount() > 0) {
            tbDepartment.getColumnModel().getColumn(0).setMinWidth(150);
            tbDepartment.getColumnModel().getColumn(0).setMaxWidth(200);
        }

        jLabel1.setText("Role ID:");

        jLabel2.setText("Role Name:");

        jButton1.setText("Add");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
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

        javax.swing.GroupLayout panInputLayout = new javax.swing.GroupLayout(panInput);
        panInput.setLayout(panInputLayout);
        panInputLayout.setHorizontalGroup(
            panInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panInputLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panInputLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(62, 62, 62)
                        .addComponent(txtName))
                    .addGroup(panInputLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(82, 82, 82)
                        .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(55, 55, 55)
                .addGroup(panInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addGap(91, 91, 91)
                .addGroup(panInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton4)
                    .addComponent(jButton3))
                .addContainerGap(78, Short.MAX_VALUE))
        );
        panInputLayout.setVerticalGroup(
            panInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panInputLayout.createSequentialGroup()
                .addGroup(panInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panInputLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(panInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panInputLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(panInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(jButton3))))
                .addGap(27, 27, 27)
                .addGroup(panInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2)
                    .addComponent(jButton4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addComponent(panInput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        addRole();
        getRole();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        updateRole();
        getRole();
        panInput.setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        int confirm = JOptionPane.showConfirmDialog(null,
                "Bạn có chắc chắn muốn xóa quyền này?", "Xác nhận",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            deleteRole();
        }
        getRole();
        panInput.setVisible(false);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void tbDepartmentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDepartmentMouseClicked
        int selectedRow = tbDepartment.getSelectedRow();
        if (selectedRow >= 0) {
            Object idValue = tbDepartment.getValueAt(selectedRow, 0);
            Object nameValue = tbDepartment.getValueAt(selectedRow, 1);

            if (idValue != null && nameValue != null) {
                // Cập nhật các trường trong UI
                txtId.setText(idValue.toString());
                txtName.setText(nameValue.toString());

                // Hiện các trường nhập liệu
                panInput.setVisible(true);
            }
        }
    }//GEN-LAST:event_tbDepartmentMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panInput;
    private javax.swing.JTable tbDepartment;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtName;
    // End of variables declaration//GEN-END:variables
}
