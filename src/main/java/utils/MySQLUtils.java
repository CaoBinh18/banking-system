package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLUtils {
    public static Connection getConnection(){
        Connection connection = null;
        String jdbcURL = "jdbc:mysql://localhost:3306/customer_banking?useSSL=false";
        String jdbcUsername = "root";
        String jdcbPassword = "123456";

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL,jdbcUsername,jdcbPassword);
        }catch(SQLException e){
            e.printStackTrace();
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        return connection;
    }
}
