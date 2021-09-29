package service;

import model.Customer;

import java.sql.SQLException;
import java.util.List;

public interface ICustomerDAO {
    public void insertCustomer(Customer customer) throws SQLException;

    public List<Customer> selectAllCustomer();

    public boolean deleteCustomer(int id) throws SQLException;

    public boolean updateCustomer(Customer customer) throws  SQLException;

    public List<Customer> selectSearchCustomer(String name);

    public Customer selectCustomerById(int id) throws SQLException;
}
