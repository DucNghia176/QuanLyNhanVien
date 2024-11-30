
package quanlynhanvien;

import javax.swing.JFrame;
import view.frmLogin;
import view.admin.frmMain;

public class LoginAndSignUp {


    public static void main(String[] args) {

        frmLogin LoginFrame = new frmLogin();
        LoginFrame.setVisible(true);
        LoginFrame.pack();
        LoginFrame.setLocationRelativeTo(null); 
    }
}
