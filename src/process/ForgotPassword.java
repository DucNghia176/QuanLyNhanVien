package process;

import dto.Connect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class ForgotPassword {

    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String EMAIL_FROM = "nghianguyenduc176@gmail.com";  // Địa chỉ email của bạn
    private static final String EMAIL_PASSWORD = "wphu vdsf zsrw tuhu";  // Mật khẩu ứng dụng Gmail

    // Kiểm tra email trong cơ sở dữ liệu và gửi mật khẩu mới nếu tồn tại
    public static boolean resetPassword(String email) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            // Tạo kết nối từ lớp Connect
            Connect connection = new Connect();
            conn = connection.getConnection();

            // Kiểm tra email trong cơ sở dữ liệu
            String sql = "SELECT email FROM users WHERE email = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            rs = stmt.executeQuery();

            if (rs.next()) {
                // Nếu email tồn tại, tạo mật khẩu mới và gửi email
                String newPassword = generateNewPassword();  // Hàm tạo mật khẩu ngẫu nhiên
                byte[] salt = PasswordUtils.generateSalt();
                String hashedPassword = null;

                try {
                    // Bắt ngoại lệ từ phương thức hashPassword
                    hashedPassword = PasswordUtils.hashPassword(newPassword.toCharArray(), salt);
                } catch (Exception e) {
                    e.printStackTrace();  // In lỗi nếu có ngoại lệ
                    return false;  // Nếu có lỗi, trả về false
                }

                // Cập nhật mật khẩu mới vào cơ sở dữ liệu
                String updateSql = "UPDATE users SET password = ? WHERE email = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                updateStmt.setString(1, hashedPassword);
                updateStmt.setString(2, email);
                int rowsUpdated = updateStmt.executeUpdate();

                if (rowsUpdated > 0) {
                    // Gửi email thông báo mật khẩu mới
                    sendEmail(email, newPassword);
                    return true;  // Thành công
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Xử lý ngoại lệ SQL
        } catch (Exception e) {
            e.printStackTrace();  // Xử lý ngoại lệ khác nếu có
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();  // Xử lý ngoại lệ khi đóng kết nối
            }
        }
        return false;  // Email không tồn tại hoặc gặp lỗi
    }

    // Hàm tạo mật khẩu ngẫu nhiên
    private static String generateNewPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 8; i++) {  // Mật khẩu dài 8 ký tự
            int index = random.nextInt(chars.length());
            password.append(chars.charAt(index));
        }
        return password.toString();
    }

    // Hàm gửi email thông báo mật khẩu mới
    private static void sendEmail(String recipient, String newPassword) {
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", SMTP_HOST);
        properties.setProperty("mail.smtp.port", "587");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_FROM, EMAIL_PASSWORD);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL_FROM));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject("Mật khẩu mới của bạn");
            message.setText("Mật khẩu mới của bạn là: " + newPassword);

            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    // Kiểm tra mật khẩu người dùng nhập vào với mật khẩu đã gửi
    public static boolean checkPassword(char[] enteredPassword, String email) {
        Connection conn = null;
        try {
            // Tạo kết nối từ lớp Connect
            Connect connection = new Connect();
            conn = connection.getConnection();

            // Lấy mật khẩu đã lưu trong trường forgotPassword từ cơ sở dữ liệu
            String sql = "SELECT password FROM users WHERE email = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Mật khẩu đã lưu trong forgotPassword
                String storedHash = rs.getString("password");
                // So sánh mật khẩu đã nhập với mật khẩu đã lưu
                return PasswordUtils.verifyPassword(enteredPassword, storedHash);
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Xử lý ngoại lệ SQL
        } catch (Exception e) {
            e.printStackTrace();  // Xử lý ngoại lệ khác nếu có
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();  // Xử lý ngoại lệ khi đóng kết nối
            }
        }
        return false;  // Nếu email không tồn tại hoặc gặp lỗi
    }

}
