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
public class frmPosition extends javax.swing.JInternalFrame {

    /**
     * Creates new form frmDepartment
     */
    public frmPosition() {
        initComponents();
        getPosition();

        //ẩn các trường đi
        panInput.setVisible(false);
    }

    public void getPosition() {
        try {
            Connect cn = new Connect();
            DefaultTableModel dt = (DefaultTableModel) tbPosition.getModel();
            dt.setRowCount(0); // Làm sạch bảng trước khi thêm dữ liệu mới

            // Câu lệnh truy vấn để lấy dữ liệu từ bảng departments
            String query = "SELECT posId, posName FROM positions";

            try (ResultSet resultSet = cn.selectQuery(query, new Object[0])) {
                while (resultSet.next()) {
                    Vector v = new Vector();
                    v.add(resultSet.getInt("posId"));  // Thêm posId vào Vector
                    v.add(resultSet.getString("posName"));  // Thêm posName vào Vector
                    dt.addRow(v);  // Thêm Vector vào bảng
                }
                System.out.println("Lấy dữ liệu thành công từ positions");

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Lỗi khi lấy dữ liệu: " + e.getMessage());
                System.out.println(e);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi kết nối cơ sở dữ liệu: " + e.getMessage());
            System.out.println(e);
        }
    }

    public void addPosition() {
        String id = txtId.getText().trim();
        String name = txtName.getText().trim();

        // Kiểm tra nếu mã phòng hợp lệ
        if (id.isEmpty() || !id.matches("\\d+")) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập mã chức vụ hợp lệ!");
            return;  // Dừng thực hiện nếu có lỗi
        }

        // Kiểm tra nếu mã chức vụ hoặc tên chức vụ đã tồn tại
        if (isPositionExist(name)) {
            JOptionPane.showMessageDialog(null, "ên chức vụ đã tồn tại!");
            return;  // Dừng thực hiện nếu đã tồn tại
        }

        Object[] argv = new Object[2];
        argv[0] = Integer.parseInt(id);
        argv[1] = name;

        try {
            Connect cn = new Connect();
            int rs = cn.executeUpdateQuery("INSERT INTO positions (posId, posName) VALUES (?, ?)", argv);
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

    public int updatePosition() {
        // Lấy deptId và deptName từ các trường nhập liệu
        String posId = txtId.getText().trim();
        String posName = txtName.getText().trim();

        if (posId.isEmpty() || !posId.matches("\\d+")) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập mã chức vụ hợp lệ!");
            return 0;
        }

        if (posName.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập tên chức vụ!");
            return 0;
        }

        // Kiểm tra nếu mã chức vụ hoặc tên chức vụ đã tồn tại
        if (isPositionExist(posName)) {
            JOptionPane.showMessageDialog(null, "Tên chức vụ đã tồn tại!");
            return 0;  // Dừng thực hiện nếu đã tồn tại
        }

        Connect cn = null; // Khai báo kết nối

        try {
            cn = new Connect();

            // 1. Kiểm tra xem deptId có tồn tại trong cơ sở dữ liệu không
            String selectQuery = "SELECT posId FROM positions WHERE posId = ?";
            ResultSet resultSet = cn.selectQuery(selectQuery, new Object[]{Integer.valueOf(posId)});
            if (!resultSet.next()) {
                JOptionPane.showMessageDialog(null, "Mã chức vụ không tồn tại trong cơ sở dữ liệu!");
                return 0;
            }

            // 2. Cập nhật thông tin phòng ban trong bảng departments
            String deleteDepartment = "UPDATE positions SET posName = ? WHERE posId = ?";
            Object[] departmentArgs = new Object[]{posName, Integer.valueOf(posId)};
            int departmentResult = cn.executeUpdateQuery(deleteDepartment, departmentArgs);

            if (departmentResult > 0) {
                JOptionPane.showMessageDialog(null, "Cập nhật thông tin chức vụ thành công!");
                clearText();
                return departmentResult; // Trả về số bản ghi đã cập nhật trong departments
            } else {
                // Nếu không cập nhật được phòng ban
                JOptionPane.showMessageDialog(null, "Không thể cập nhật chức vụ.");
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

    public int deleteDepartment() {
        // Lấy posId và posName từ các trường nhập liệu
        String posId = txtId.getText().trim();
        String posName = txtName.getText().trim();
        Connect cn = null; // Khai báo kết nối

        try {
            cn = new Connect();

            // 1. Kiểm tra xem posId có tồn tại trong cơ sở dữ liệu không
            String selectQuery = "SELECT posId FROM positions WHERE posId = ?";
            ResultSet resultSet = cn.selectQuery(selectQuery, new Object[]{Integer.valueOf(posId)});
            if (!resultSet.next()) {
                JOptionPane.showMessageDialog(null, "Mã chức vụ không tồn tại trong cơ sở dữ liệu!");
                return 0;
            }

            // 2. Xóa thông tin chức vụ trong bảng positions
            String deleteDepartment = "DELETE FROM positions WHERE posId = ?";
            Object[] departmentArgs = new Object[]{Integer.valueOf(posId)};  // Chỉ cần posId để xóa
            int departmentResult = cn.executeUpdateQuery(deleteDepartment, departmentArgs);

            if (departmentResult > 0) {
                JOptionPane.showMessageDialog(null, "Xóa chức vụ thành công!");
                clearText();
                return departmentResult; // Trả về số bản ghi đã xóa
            } else {
                // Nếu không xóa được chức vụ
                JOptionPane.showMessageDialog(null, "Không thể xóa chức vụ.");
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

    //kiêmr tra id, name đã tồn tại chưa
    public boolean isPositionExist(String posName) {
        try {
            Connect cn = new Connect();

            // Kiểm tra nếu posId đã tồn tại
            String query = "SELECT posId FROM positions WHERE posName = ?";
            ResultSet resultSet = cn.selectQuery(query, new Object[]{posName});

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
        txtId = new javax.swing.JTextField();
        txtName = new javax.swing.JTextField();
        btAdd = new javax.swing.JButton();
        btUpdate = new javax.swing.JButton();
        btDelete = new javax.swing.JButton();
        btExit = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbPosition = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        jLabel1.setText("Position ID:");

        jLabel2.setText("Position Name:");

        btAdd.setText("+Add");
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
                    .addComponent(btAdd)
                    .addComponent(btUpdate))
                .addGap(91, 91, 91)
                .addGroup(panInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btExit)
                    .addComponent(btDelete))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                            .addComponent(btAdd)
                            .addComponent(btDelete))))
                .addGap(27, 27, 27)
                .addGroup(panInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btUpdate)
                    .addComponent(btExit))
                .addContainerGap(58, Short.MAX_VALUE))
        );

        tbPosition.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Position ID", "Position Name"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tbPosition.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPositionMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbPosition);
        if (tbPosition.getColumnModel().getColumnCount() > 0) {
            tbPosition.getColumnModel().getColumn(0).setMinWidth(150);
            tbPosition.getColumnModel().getColumn(0).setMaxWidth(200);
        }

        jScrollPane2.setViewportView(jScrollPane1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 667, Short.MAX_VALUE)
            .addComponent(panInput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panInput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btExitActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btExitActionPerformed

    private void btAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAddActionPerformed
//         TODO add your handling code here:
        addPosition();
        getPosition();
    
    }//GEN-LAST:event_btAddActionPerformed

    private void btUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btUpdateActionPerformed
        // TODO add your handling code here:
        updatePosition();
        getPosition();
        panInput.setVisible(false);
    }//GEN-LAST:event_btUpdateActionPerformed

    private void btDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btDeleteActionPerformed
        // TODO add your handling code here:
        int confirm = JOptionPane.showConfirmDialog(null,
                "Bạn có chắc chắn muốn xóa phòng này?", "Xác nhận",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            deleteDepartment();
        }
        getPosition();
        panInput.setVisible(false);
    }//GEN-LAST:event_btDeleteActionPerformed

    private void tbPositionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPositionMouseClicked
        int selectedRow = tbPosition.getSelectedRow();
        if (selectedRow >= 0) {
            Object idValue = tbPosition.getValueAt(selectedRow, 0);
            Object nameValue = tbPosition.getValueAt(selectedRow, 1);

            if (idValue != null && nameValue != null) {
                // Cập nhật các trường trong UI
                txtId.setText(idValue.toString());
                txtName.setText(nameValue.toString());

                // Hiện các trường nhập liệu
                panInput.setVisible(true);
            }
        }
    }//GEN-LAST:event_tbPositionMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAdd;
    private javax.swing.JButton btDelete;
    private javax.swing.JButton btExit;
    private javax.swing.JButton btUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel panInput;
    private javax.swing.JTable tbPosition;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtName;
    // End of variables declaration//GEN-END:variables
}
