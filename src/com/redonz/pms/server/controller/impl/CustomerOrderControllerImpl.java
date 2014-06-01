/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redonz.pms.server.controller.impl;

import com.redonz.pms.common.controller.CustomerOrderController;
import com.redonz.pms.common.model.CustomerOrder;
import com.redonz.pms.common.model.Payment;
import com.redonz.pms.server.dao.CustomerOrderDAO;
import com.redonz.pms.server.dao.impl.CustomerOrderDAOImpl;
import com.redonz.pms.server.reservation.Reservation;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Nadeeshan
 */
public class CustomerOrderControllerImpl extends UnicastRemoteObject implements CustomerOrderController {

    private final CustomerOrderDAO customerOrderDAO = new CustomerOrderDAOImpl();
    private static final Reservation<CustomerOrderController> CUSTOMER_ORDER_RESERVATION = new Reservation<>();

    public CustomerOrderControllerImpl() throws RemoteException {
    }

    @Override
    public boolean saveCustomerOrder(CustomerOrder customerOrder) throws RemoteException, SQLException, ClassNotFoundException {
        System.out.println("orderController");
        return customerOrderDAO.insert(customerOrder);
    }

    @Override
    public CustomerOrder searchCustomerOrder(String orderId) throws RemoteException, SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean updateCustomerOrder(CustomerOrder customerOrder) throws RemoteException, SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteCustomerOrder(String orderId) throws RemoteException, SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getLastOrderId() throws RemoteException, SQLException, ClassNotFoundException {
        return customerOrderDAO.lastId();
    }

    @Override
    public boolean updateOrderDiscount(String orderId, double discount, double netAmount) throws RemoteException, SQLException, ClassNotFoundException {
        return customerOrderDAO.update(new CustomerOrder(null, orderId, null, 0, netAmount, discount));
    }

    @Override
    public ArrayList<CustomerOrder> getOrdersByDateRange(String bDate, String eDate) throws RemoteException, SQLException, ClassNotFoundException {
        return customerOrderDAO.ordersForDateRange(bDate, eDate);
    }

    @Override
    public ArrayList<CustomerOrder> getOrdersByOrderDate(String orderDate) throws RemoteException, SQLException, ClassNotFoundException {
        return customerOrderDAO.ordersByOrderDate(orderDate);
    }

    @Override
    public ArrayList<CustomerOrder> getOrdersByCustId(String custId) throws RemoteException, SQLException, ClassNotFoundException {
        return customerOrderDAO.ordersByCustId(custId);
    }

    @Override
    public boolean saveCustomerOrder(CustomerOrder customerOrder, Payment payment) throws RemoteException, SQLException, ClassNotFoundException {
        return customerOrderDAO.saveWithPayment(customerOrder, payment);
    }

    @Override
    public boolean reserveCustomerOrder(String orderId) throws RemoteException {
        return CUSTOMER_ORDER_RESERVATION.reserve(orderId, this);
    }

    @Override
    public boolean releaseCustomerOrder(String orderId) throws RemoteException {
        return CUSTOMER_ORDER_RESERVATION.release(orderId, this);
    }
}
