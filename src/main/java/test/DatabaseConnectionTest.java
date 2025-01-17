package test;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnectionTest {
    public static void main(String[] args) {
        // Thông tin kết nối
        String jdbcUrl = "jdbc:mysql://localhost:3306/shoestore"; // Thay 'shoe_store' bằng tên database của bạn
        String username = "root"; // Thay bằng username của bạn
        String password = "123456"; // Thay bằng mật khẩu của bạn

        try {
            // Tải driver MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Kết nối cơ sở dữ liệu
            System.out.println("Đang kiểm tra kết nối...");
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);

            if (connection != null) {
                System.out.println("Kết nối thành công!");
                connection.close();
            } else {
                System.out.println("Không thể kết nối.");
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi kết nối cơ sở dữ liệu:");
            e.printStackTrace();
        }
    }

}
