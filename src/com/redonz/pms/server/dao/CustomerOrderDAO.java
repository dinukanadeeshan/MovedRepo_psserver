/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redonz.pms.server.dao;

import com.redonz.pms.common.model.CustomerOrder;
import com.redonz.pms.common.model.Payment;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Nadeeshan
 */
public interface CustomerOrderDAO extends DAO<CustomerOrder, String> {

    boolean updateOrderBalance(String orderId, double amount, Connection connection) throws SQLException, ClassNotFoundException;

    ArrayList<CustomerOrder> ordersForDateRange(String bDate, String eDate) throws SQLException, ClassNotFoundException;

    ArrayList<CustomerOrder> ordersByOrderDate(String orderDate) throws SQLException, ClassNotFoundException;

    ArrayList<CustomerOrder> ordersByCustId(String custId) throws SQLException, ClassNotFoundException;

    public boolean saveWithPayment(CustomerOrder customerOrder, Payment payment)throws SQLException, ClassNotFoundException;
}
