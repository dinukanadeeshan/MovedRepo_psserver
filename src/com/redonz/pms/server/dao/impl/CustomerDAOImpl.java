/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redonz.pms.server.dao.impl;

import com.redonz.pms.server.dao.CustomerDAO;
import com.redonz.pms.common.model.Customer;
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
 * @author Nadeeshan
 */
public class CustomerDAOImpl implements CustomerDAO {

    private static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    @Override
    public boolean insert(Customer t) throws SQLException, ClassNotFoundException , FileNotFoundException, IOException{
        int res = -1;
        readWriteLock.writeLock().lock();
        try {
            String query = "insert into Customer values(?,?,?,?)";

            Connection connection = DBConnection.getConnection();
            try (PreparedStatement prepareStatement = connection.prepareStatement(query)) {
                prepareStatement.setString(1, t.getCustId());
                prepareStatement.setString(2, t.getName());
                prepareStatement.setString(3, t.getAddress());
                prepareStatement.setString(4, t.getContact());
                res = prepareStatement.executeUpdate();
            }
            DBConnection.getPool().returnConnection((com.redonz.pms.server.pool.connection.DBConnection) connection);
        } finally {
            readWriteLock.writeLock().unlock();
        }
        return res > 0;
    }

    @Override
    public ArrayList<Customer> getAll() throws ClassNotFoundException, SQLException , FileNotFoundException, IOException{
        ArrayList<Customer> customers = new ArrayList<>();
        readWriteLock.readLock().lock();
        try {
            String sql = "select * from Customer";
            Connection connection = DBConnection.getConnection();
            ResultSet rst = connection.createStatement().executeQuery(sql);

            while (rst.next()) {
                customers.add(new Customer(rst.getString("custId"), rst.getString("name"), rst.getString("address"), rst.getString("contact")));
            }
            DBConnection.getPool().returnConnection((com.redonz.pms.server.pool.connection.DBConnection) connection);
        } finally {
            readWriteLock.readLock().unlock();
        }
        return customers;
    }

    @Override
    public String lastId() throws SQLException, ClassNotFoundException , FileNotFoundException, IOException{
        String lastId = null;
        readWriteLock.readLock().lock();
        try {
            String sql = "select custId from Customer order by 1 desc limit 1";
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
    public Customer search(String e) throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(Customer t) throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(String e) throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addList(ArrayList<Customer> tList) throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
}
