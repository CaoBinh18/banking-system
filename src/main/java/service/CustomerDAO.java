package service;

import model.Customer;
import utils.MySQLUtils;

import javax.servlet.RequestDispatcher;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO implements ICustomerDAO{

    public CustomerDAO() {
    };

    Connection connection = MySQLUtils.getConnection();

    @Override
    public void insertCustomer(Customer customer) throws SQLException {
        String query = "INSERT INTO customers (fullName, email, phone, address) VALUES (?, ?, ?, ?);";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1 , customer.getFullName());
            ps.setString(2, customer.getEmail());
            ps.setString(3, customer.getPhone());
            ps.setString(4, customer.getAddress());
            ps.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    @Override
    public List<Customer> selectAllCustomer() {
        List<Customer> customersList = new ArrayList<>();
        String query = "SELECT id, fullName, email, phone, address, balance FROM customers;";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("fullName");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                double balance = rs.getDouble("balance");
                customersList.add(new Customer(id, name, email, phone, address, balance));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return customersList;
    }

    @Override
    public boolean deleteCustomer(int id) throws SQLException {
        boolean check = false;
        String query = "DELETE FROM customers WHERE id = ?;";
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            check = ps.executeUpdate() > 0;
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            connection.rollback();
            printSQLException(e);
        }
        return check;
    }

    @Override
    public boolean updateCustomer(Customer customer) throws SQLException {
        boolean check = false;
        String query = "UPDATE customers SET fullName = ?,email = ?, phone = ?, address = ?, balance = ? WHERE id = ?;";
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1 , customer.getFullName());
            ps.setString(2, customer.getEmail());
            ps.setString(3, customer.getPhone());
            ps.setString(4, customer.getAddress());
            ps.setDouble(5, customer.getBalance());
            ps.setInt(6, customer.getId());
            ps.executeUpdate();
            connection.commit();
            connection.setAutoCommit(true);
            check = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            connection.rollback();
            printSQLException(e);
        }
        return check;
    }

    @Override
    public List<Customer> selectSearchCustomer(String name) {
        return null;
    }

    @Override
    public Customer selectCustomerById(int id) throws SQLException {
        Customer customer = null;
        String SQL_SELECT_CUSTOMER_BY_ID = "SELECT fullName, email, phone, address, balance FROM customers WHERE id = ?;";
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_CUSTOMER_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String name = rs.getString("fullName");
                String phone = rs.getString("email");
                String email = rs.getString("phone");
                String address = rs.getString("address");
                double balance = rs.getDouble("balance");
                customer = new Customer(id, name, email, phone, address, balance);
            }
            connection.commit();
            connection.setAutoCommit(true);

        } catch (SQLException e) {
            connection.rollback();
            printSQLException(e);
        }
        return customer;
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
