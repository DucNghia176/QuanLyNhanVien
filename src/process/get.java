package process;

import dto.Connect;
import java.sql.ResultSet;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

public class get {
    public static void loadDepartmentsToComboBox(JComboBox<String> comboBox) {
        try {
            Connect cn = new Connect();
            ResultSet resultSet = cn.selectQuery("SELECT deptId, deptName FROM departments", new Object[0]);

            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
            while (resultSet.next()) {
                String deptId = resultSet.getString("deptId");
                String deptName = resultSet.getString("deptName");
                model.addElement(deptId + " - " + deptName);
            }

            comboBox.setModel(model);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Lỗi tải dữ liệu phòng: " + e.getMessage());
        }
    }
    
    
    public static void loadPositionsToComboBox(JComboBox<String> comboBox) { //lấy ra từ chuc vu
        try {
            Connect cn = new Connect();
            ResultSet resultSet = cn.selectQuery("SELECT posId, posName FROM positions", new Object[0]);

            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
            while (resultSet.next()) {
                String posId = resultSet.getString("posId");
                String posName = resultSet.getString("posName");
                model.addElement(posId + " - " + posName);
            }

            comboBox.setModel(model);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Lỗi tải dữ liệu chức vụ: " + e.getMessage());
        }
    }
    
    public static void loadRoleToComboBox(JComboBox<String> comboBox) { //lấy ra từ chuc vu
        try {
            Connect cn = new Connect();
            ResultSet resultSet = cn.selectQuery("SELECT roleId, roleName FROM roles", new Object[0]);

            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
            while (resultSet.next()) {
                String roleId = resultSet.getString("roleId");
                String roleName = resultSet.getString("roleName");
                model.addElement(roleId + " - " + roleName);
            }

            comboBox.setModel(model);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Lỗi tải dữ liệu chức vụ: " + e.getMessage());
        }
    }
}

