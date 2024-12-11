/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package process;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 *
 * @author MY PC
 */
public class PBKDF2Hashing {
    // Phương thức băm mật khẩu sử dụng PBKDF2
    public static String hashPassword(String password) {
        try {
            // Sử dụng PBKDF2 với SHA-512
            String algorithm = "PBKDF2WithHmacSHA512";
            int iterations = 10000; // Số vòng lặp
            int keyLength = 512; // Độ dài băm
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt); // Tạo ra một salt ngẫu nhiên

            // Sử dụng MessageDigest để băm mật khẩu
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            digest.update(salt);
            byte[] hash = digest.digest(password.getBytes());

            // Băm nhiều lần để tăng độ an toàn
            for (int i = 0; i < iterations; i++) {
                hash = digest.digest(hash);
            }

            // Kết hợp salt và băm mật khẩu, sau đó mã hóa bằng Base64
            String saltBase64 = Base64.getEncoder().encodeToString(salt);
            String hashBase64 = Base64.getEncoder().encodeToString(hash);
            return saltBase64 + "$" + hashBase64;

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("PBKDF2 algorithm not found.", e);
        }
    }

    // Phương thức kiểm tra mật khẩu có khớp với mật khẩu đã băm không
    public static boolean validatePassword(String password, String storedPassword) {
        // Lấy salt và băm từ mật khẩu đã lưu
        String[] parts = storedPassword.split("\\$");
        String saltBase64 = parts[0];
        String storedHashBase64 = parts[1];

        byte[] salt = Base64.getDecoder().decode(saltBase64);
        byte[] storedHash = Base64.getDecoder().decode(storedHashBase64);

        // Băm mật khẩu người dùng nhập với cùng salt
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-512");
            digest.update(salt);
            byte[] hash = digest.digest(password.getBytes());

            // Băm nhiều lần như khi tạo mật khẩu đã băm
            for (int i = 0; i < 10000; i++) {
                hash = digest.digest(hash);
            }

            // So sánh hash đã tính toán với hash đã lưu
            return MessageDigest.isEqual(hash, storedHash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Algorithm not found", e);
        }
    }
}
