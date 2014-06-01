/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redonz.pms.server.dao.impl;

import com.redonz.pms.common.model.CustomerOrder;
import com.redonz.pms.common.model.Payment;
import com.redonz.pms.server.dao.CustomerOrderDAO;
import com.redonz.pms.server.db.DBConnection;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *
 * @author DI_SH
 */
public class CustomerOrderDAOImpl implements CustomerOrderDAO {

    private static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    @Override
    public boolean insert(CustomerOrder t) throws SQLException, ClassNotFoundException , FileNotFoundException, IOException{
        Connection connection = DBConnection.getConnection();
        readWriteLock.writeLock().lock();
        try {
            connection.setAutoCommit(false);
            String sql = "insert into Orders values(?,?,?,?,?,?)";
            System.out.println(sql);
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setString(1, t.getCustId());
            prepareStatement.setString(2, t.getOrderId());
            prepareStatement.setString(3, t.getDate());
            prepareStatement.setDouble(4, t.getBalance());
            prepareStatement.setDouble(5, t.getTotalAmount());
            prepareStatement.setDouble(6, t.getDiscount());

            int execute = prepareStatement.executeUpdate();

            if (execute > 0) {
                boolean res = new CustomerOrderDetailDAOImpl().addOrderDetailList(t.getCustomerOrderDetailList(), connection);


                if (res) {
                    connection.commit();
                    return true;
                } else {
                    connection.rollback();
                    return false;
                }
            } else {
                connection.rollback();
                return false;
            }

        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            connection.setAutoCommit(true);
            DBConnection.getPool().returnConnection((com.redonz.pms.server.pool.connection.DBConnection) connection);
            readWriteLock.writeLock().unlock();
        }
    }

    @Override
    public CustomerOrder search(String e) throws SQLException, ClassNotFoundException , FileNotFoundException, IOException{
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(CustomerOrder t) throws SQLException, ClassNotFoundException , FileNotFoundException, IOException{
        int res = -1;
        readWriteLock.writeLock().lock();
        try {
            String sql = "update orders set ";
            if (t.getCustId() != null) {
                sql += "custId = '" + t.getCustId() + "', ";
            }
            if (t.getBalance() != 0) {
                sql += "balance = " + t.getBalance() + ", ";
            }
            if (t.getDiscount() != 0) {
                sql += "discount = " + t.getDiscount() + ", ";
            }
            if (t.getTotalAmount() != 0) {
                sql += "totalAmount = " + t.getTotalAmount() + ", ";
            }
            if (t.getDate() != null) {
                sql += "orderDate = '" + t.getDate() + "', ";
            }

            sql = sql.substring(0, sql.length() - 2) + "where orderId = '" + t.getOrderId() + "'";
            Connection connection = DBConnection.getConnection();
            res = connection.createStatement().executeUpdate(sql);
            DBConnection.getPool().returnConnection((com.redonz.pms.server.pool.connection.DBConnection) connection);
        } finally {
            readWriteLock.writeLock()
                    .unlock();
        }
        return res > 0;


    }

    @Override
    public boolean delete(String e) throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<CustomerOrder> getAll() throws SQLException, ClassNotFoundException , FileNotFoundException, IOException{
        ArrayList<CustomerOrder> customerOrders = new ArrayList<>();
        readWriteLock.readLock().lock();
        try {
            String sql = "select o.* from Orders o";
            Connection connection = DBConnection.getConnection();
            PreparedStatement prepareStatement = connection.prepareStatement(sql);


            ResultSet rst = prepareStatement.executeQuery();


            while (rst.next()) {
                String custId = rst.getString(1);
                String orderId = rst.getString(2);


                double balance = rst.getDouble(4);
                double totalAmount = rst.getDouble(5);

                customerOrders.add(new CustomerOrder(custId, orderId, rst.getString(3), totalAmount, balance, -0.0));

            }

            DBConnection.getPool().returnConnection((com.redonz.pms.server.pool.connection.DBConnection) connection);
        } finally {
            readWriteLock.readLock().unlock();
        }
        return customerOrders;
    }

    @Override
    public String lastId() throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
        String lastId = null;
        readWriteLock.readLock().lock();
        try {
            String sql = "select orderId from Orders order by 1 desc limit 1";
            Connection connection = DBConnection.getConnection();
            ResultSet rst = connection.createStatement().executeQuery(sql);

            if (rst.next()) {
                lastId = rst.getString(1);
            }
            DBConnection.getPool().returnConnection((com.redonz.pms.server.pool.connection.DBConnection) connection);
        } finally {
            readWriteLock.readLock().unlock();
        }
        return lastId;
    }

    @Override
    public boolean addList(ArrayList<CustomerOrder> tList) throws SQLException, ClassNotFoundException , FileNotFoundException, IOException{
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean updateOrderBalance(String orderId, double amount, Connection connection) throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
        int res = -1;
        readWriteLock.writeLock().lock();
        try {
            String sql = "update orders set balance = balance - ? where orderId = ?";
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setDouble(1, amount);
            prepareStatement.setString(2, orderId);


            res = prepareStatement.executeUpdate();
        } finally {
            readWriteLock.writeLock().unlock();
        }
        return res > 0;
    }

    @Override
    public ArrayList<CustomerOrder> ordersForDateRange(String bDate, String eDate) throws SQLException, ClassNotFoundException , FileNotFoundException, IOException{
        ArrayList<CustomerOrder> customerOrders = new ArrayList<>();
        readWriteLock.readLock().lock();
        try {
            String sql = "select o.* from Orders o where orderDate between ? and ?  and balance > 0";
            Connection connection = DBConnection.getConnection();
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setString(1, bDate);
            prepareStatement.setString(2, eDate);
            ResultSet rst = prepareStatement.executeQuery();


            while (rst.next()) {
                String custId = rst.getString(1);
                String orderId = rst.getString(2);

                String orderDate = rst.getString(3);
                double balance = rst.getDouble(4);
                double totalAmount = rst.getDouble(5);

                customerOrders.add(new CustomerOrder(custId, orderId, orderDate, totalAmount, balance, -0.0));

            }

            DBConnection.getPool().returnConnection((com.redonz.pms.server.pool.connection.DBConnection) connection);
        } finally {
            readWriteLock.readLock().unlock();
        }
        return customerOrders;
    }

    @Override
    public ArrayList<CustomerOrder> ordersByOrderDate(String orderDate) throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
        ArrayList<CustomerOrder> customerOrders = new ArrayList<>();
        readWriteLock.readLock().lock();
        try {
            String sql = "select o.* from Orders o where orderDate = ? and balance > 0";
            Connection connection = DBConnection.getConnection();
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setString(1, orderDate);

            ResultSet rst = prepareStatement.executeQuery();

            while (rst.next()) {
                String custId = rst.getString(1);
                String orderId = rst.getString(2);


                double balance = rst.getDouble(4);
                double totalAmount = rst.getDouble(5);

                customerOrders.add(new CustomerOrder(custId, orderId, orderDate, totalAmount, balance, -0.0));

            }

            DBConnection.getPool().returnConnection((com.redonz.pms.server.pool.connection.DBConnection) connection);
        } finally {
            readWriteLock.readLock().unlock();
        }
        return customerOrders;
    }

    @Override
    public ArrayList<CustomerOrder> ordersByCustId(String custId) throws SQLException, ClassNotFoundException , FileNotFoundException, IOException{
        ArrayList<CustomerOrder> customerOrders = new ArrayList<>();
        readWriteLock.readLock().lock();
        try {
            String sql = "select o.* from Orders o where custId = ?  and balance > 0";
            Connection connection = DBConnection.getConnection();
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setString(1, custId);

            ResultSet rst = prepareStatement.executeQuery();
            customerOrders = new ArrayList<>();

            while (rst.next()) {

                String orderId = rst.getString(2);

                String orderDate = rst.getString(3);
                double balance = rst.getDouble(4);
                double totalAmount = rst.getDouble(5);

                customerOrders.add(new CustomerOrder(custId, orderId, orderDate, totalAmount, balance, -0.0));

            }

            DBConnection.getPool().returnConnection((com.redonz.pms.server.pool.connection.DBConnection) connection);
        } finally {
            readWriteLock.readLock().unlock();
        }
        return customerOrders;
    }

    @Override
    public boolean saveWithPayment(CustomerOrder t, Payment payment) throws SQLException, ClassNotFoundException , FileNotFoundException, IOException{
        Connection connection = DBConnection.getConnection();
        readWriteLock.writeLock().lock();
        try {
            connection.setAutoCommit(false);
            String sql = "insert into Orders values(?,?,?,?,?,?)";
            System.out.println(sql);
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setString(1, t.getCustId());
            prepareStatement.setString(2, t.getOrderId());
            prepareStatement.setString(3, t.getDate());
            prepareStatement.setDouble(4, t.getBalance());
            prepareStatement.setDouble(5, t.getTotalAmount());
            prepareStatement.setDouble(6, t.getDiscount());

            int execute = prepareStatement.executeUpdate();

            if (execute > 0) {
                boolean res = new CustomerOrderDetailDAOImpl().addOrderDetailList(t.getCustomerOrderDetailList(), connection);


                if (res) {
                    boolean res1 = new PaymentDAOImpl().insert(payment, connection);
                    if (res1) {
                        connection.commit();
                        return true;
                    } else {
                        connection.rollback();
                        return false;
                    }
                } else {
                    connection.rollback();
                    return false;
                }
            } else {
                connection.rollback();
                return false;
            }


        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            connection.setAutoCommit(true);
            DBConnection.getPool().returnConnection((com.redonz.pms.server.pool.connection.DBConnection) connection);
            readWriteLock.writeLock().unlock();
        }
    }
}
