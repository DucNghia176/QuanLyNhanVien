package dto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Connect {

    // Bước 1: Thiết lập các tham số kết nối
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=qlnv2";
    private static final String USER = "sa";
    private static final String PASSWORD = "12345";

    private Connection conn;

    public Connect() throws SQLException {
        try {
            // Bước 2: Thiết lập kết nối
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
            throw new SQLException("Lỗi kết nối đến cơ sở dữ liệu", ex);
        }
    }

    public ResultSet selectQuery(String query, Object[] params) throws SQLException {
        // Mở kết nối và chuẩn bị statement
        PreparedStatement stmt = conn.prepareStatement(query);

        // Thiết lập các tham số động
        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }

        // Thực hiện truy vấn và trả về ResultSet
        return stmt.executeQuery();
    }

    // Phương thức thực hiện các truy vấn SQL động (INSERT, UPDATE, DELETE)
    public int executeUpdateQuery(String query, Object[] params) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            // Thiết lập tham số động
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            // Thực hiện INSERT, UPDATE, DELETE        
            return stmt.executeUpdate();
        }
    }

    // Phương thức để đóng kết nối
    public void close() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }

    public void setAutoCommit(boolean autoCommit) throws SQLException {
        conn.setAutoCommit(autoCommit);
    }

    public void commit() throws SQLException {
        conn.commit();
    }

    public void rollback() throws SQLException {
        conn.rollback();
    }
}
