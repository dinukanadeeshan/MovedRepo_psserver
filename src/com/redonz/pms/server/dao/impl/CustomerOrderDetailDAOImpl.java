/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redonz.pms.server.dao.impl;

import com.redonz.pms.common.model.CustomerOrderDetail;
import com.redonz.pms.server.dao.BatchItemDAO;
import com.redonz.pms.server.dao.CustomerOrderDetailDAO;
import com.redonz.pms.server.db.DBConnection;
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
public class CustomerOrderDetailDAOImpl implements CustomerOrderDetailDAO {

    private static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    @Override
    public boolean insert(CustomerOrderDetail t) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CustomerOrderDetail search(String e) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }

    @Override
    public boolean update(CustomerOrderDetail t) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(String e) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<CustomerOrderDetail> getAll() throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String lastId() throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @Deprecated
    public boolean addList(ArrayList<CustomerOrderDetail> tList) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("No Use now.");
    }

    @Override
    public boolean addOrderDetailList(ArrayList<CustomerOrderDetail> orderDetails, Connection connection) throws SQLException, ClassNotFoundException {
        boolean r = false;
        readWriteLock.writeLock().lock();
        try {
            String sql = "insert into orderdetail values(?,?,?,?)";

            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            for (CustomerOrderDetail customerOrderDetail : orderDetails) {
                prepareStatement.setString(1, customerOrderDetail.getItemCode());
                prepareStatement.setString(2, customerOrderDetail.getOrderId());
                prepareStatement.setDouble(4, customerOrderDetail.getUnitprice());
                prepareStatement.setDouble(3, customerOrderDetail.getQty());
                prepareStatement.addBatch();
            }
            int[] res = prepareStatement.executeBatch();

            for (int i : res) {
                if (i < 0) {
                    return false;
                }
            }
            BatchItemDAO batchItemDAO = new BatchItemDAOImpl();
            r = batchItemDAO.updateItemQty(orderDetails, connection);
        } finally {
            readWriteLock.writeLock().unlock();
        }

        return r && true;
    }

    @Override
    public ArrayList<CustomerOrderDetail> orderDetailsForOrderId(String e) throws SQLException, ClassNotFoundException {
        ArrayList<CustomerOrderDetail> customerOrderDetails = new ArrayList<>();
        readWriteLock.readLock().lock();
        try {
            String sql = "select od.*, batchNo from OrderDetail od, BatchItem bi where bi.itemCode = od.itemCode and orderId = ?";
            Connection connection = DBConnection.getConnection();
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setString(1, e);
            ResultSet rst = prepareStatement.executeQuery();

            while (rst.next()) {
                String itemCode = rst.getString(1);
                String batchNo = rst.getString(5);
                double qty = rst.getDouble(3);
                double unitPrice = rst.getDouble(4);

                customerOrderDetails.add(new CustomerOrderDetail(itemCode, e, batchNo, qty, unitPrice, ""));
            }

            DBConnection.getPool().returnConnection((com.redonz.pms.server.pool.connection.DBConnection) connection);
        } finally {
            readWriteLock.readLock().unlock();
        }
        return customerOrderDetails;
    }
}
