package controller;

import model.Customer;
import service.CustomerDAO;
import utils.CheckTools;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "CustomerServlet", urlPatterns = "/customers")
public class CustomerServlet extends HttpServlet {
    private CustomerDAO customerDAO = new CustomerDAO();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        try {
            switch (action) {
                case "create":
                    insertCustomer(request, response);
                    break;
                case "edit":
                    editCustomer(request, response);
                    break;
                case "delete":
                    deleteCustomer(request, response);
                    break;
                default:
                    listCustomer(request, response);
                break;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }

        try {
        switch (action) {
            case "create":
                showCreateCustomer(request, response);
                break;
            case "edit":
                showEditCustomer(request, response);
                break;
            case "delete":
                showDeleteCustomer(request, response);
                break;
            case "search":
                listSearchCustomer(request, response);
                break;
            default:
                listCustomer(request, response);
                break;
        }
    } catch (SQLException e) {
        throw new ServletException(e);
    }
    }

    private void editCustomer(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("fullName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        Customer editCustomer = customerDAO.selectCustomerById(id);
        RequestDispatcher dispatcher;
        if (name == "" || email == "" || phone == "" || address == "") {
            request.setAttribute("success", null);
            request.setAttribute("error", "All field is required");
            showEditCustomer(request, response);
        } else {
            if (CheckTools.isEmail(email)) {
                request.setAttribute("success", null);
                request.setAttribute("error", "Invalid Value");
                listCustomer(request, response);
            } else {
                if (editCustomer == null) {
                    dispatcher = request.getRequestDispatcher("customer/error-404.jsp");
                } else {
                    editCustomer.setId(id);
                    editCustomer.setFullName(name);
                    editCustomer.setEmail(email);
                    editCustomer.setPhone(phone);
                    editCustomer.setAddress(address);
                    customerDAO.updateCustomer(editCustomer);
                    request.setAttribute("success", "Customer was edit success!");
                    request.setAttribute("error", null);
                    showEditCustomer(request, response);
                }
            }
        }
    }

    private void deleteCustomer(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Customer customer = customerDAO.selectCustomerById(id);
        RequestDispatcher dis;
        if(customer == null){
            dis = request.getRequestDispatcher("customer/error-404.jsp");
        }else{
            boolean check = true;
            if(check){
                customerDAO.deleteCustomer(id);
                List<Customer> customerList = customerDAO.selectAllCustomer();
                request.setAttribute("customers",customerList);
                response.sendRedirect("customers");
                request.setAttribute("error",null);
                request.setAttribute("success","Customer was deleted");
            }else{
                response.sendRedirect("customers");
                request.setAttribute("error","Traded Customer cannot be deleted");
                request.setAttribute("success",null);

            }

        }
    }

    private void showDeleteCustomer(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        customerDAO.deleteCustomer(id);

        List<Customer> customerList = customerDAO.selectAllCustomer();
        request.setAttribute("customer", customerList);
        request.setAttribute("message_delete", "User has been deleted");
        RequestDispatcher dispatcher = request.getRequestDispatcher("customer/delete.jsp");
        dispatcher.forward(request, response);
    }

    private void insertCustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String name = request.getParameter("fullName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        Customer newCustomer = new Customer(name, email, phone, address);
        customerDAO.insertCustomer(newCustomer);
//        request.setAttribute("success", "OK");
//        request.setAttribute("error", null);
//        request.setAttribute("warning", null);
        RequestDispatcher dispatcher = request.getRequestDispatcher("customer/create.jsp");
        dispatcher.forward(request, response);
    }

    private void listSearchCustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String search = request.getParameter("search");
        List<Customer> customers = customerDAO.selectSearchCustomer(search);
        request.setAttribute("customers", customers);
        RequestDispatcher dis = request.getRequestDispatcher("customer/list.jsp");
        dis.forward(request, response);
    }

    private void showCreateCustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("customer/create.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditCustomer(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Customer existingCustomer = customerDAO.selectCustomerById(id);
        request.setAttribute("customer", existingCustomer);
        RequestDispatcher dispatcher = request.getRequestDispatcher("customer/edit.jsp");
        dispatcher.forward(request, response);
    }

    public void listCustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Customer> customers = customerDAO.selectAllCustomer();
        request.setAttribute("listCustomer", customers);
        request.getRequestDispatcher("/customer/list.jsp").forward(request, response);
    }

}
