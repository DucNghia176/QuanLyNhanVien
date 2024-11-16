/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package process;

/**
 *
 * @author Asus
 */
public class check {
    
    // Hàm kiểm tra độ dài của tên người dùng
    public static boolean isValidLength(String a) {
        return a != null && a.length() >= 8; // Đảm bảo tên không null và đủ 8 ký tự
    }
    
    // Hàm kiểm tra độ dài của mật khẩu
    public static boolean isValidPasswordLength(char[] password) {
        return password != null && password.length >= 8;
    }
    
    // Hàm kiểm tra định dạng email
    public static boolean isValidEmailFormat(String email) {
        // Biểu thức regex kiểm tra email
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        return email != null && email.matches(emailRegex);
    }
    
    // Hàm kiểm tra số điện thoại có ít nhất 10 chữ số
    public static boolean isValidPhoneNumber(String phone) {
        // Kiểm tra nếu phone không null, chỉ chứa số và có độ dài tối thiểu là 10
        return phone != null && phone.matches("\\d{10,}");
    }
}
