/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redonz.pms.server.dao.impl;

import com.redonz.pms.common.model.Category;
import com.redonz.pms.server.dao.CategoryDAO;
import com.redonz.pms.server.db.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *
 * @author DI_SH
 */
public class CategoryDAOImpl implements CategoryDAO{
    private static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    @Override
    public boolean insert(Category t) throws SQLException, ClassNotFoundException {
        int res = -1;
        readWriteLock.writeLock().lock();
        try {
            String sql = "insert into category values(?,?)";
            Connection connection = DBConnection.getConnection();
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setString(1, t.getCategoryId());
            prepareStatement.setString(2, t.getDescription());
            res = prepareStatement.executeUpdate();
            DBConnection.getPool().returnConnection((com.redonz.pms.server.connectionpool.DBConnection) connection);
        } finally {
            readWriteLock.writeLock().unlock();
        }
        return res > 0;
    }

    @Override
    public Category search(String e) throws SQLException, ClassNotFoundException {
        Category category = null;
        readWriteLock.readLock().lock();
        try {
            String sql = "select * from Category where categoryId = ?";
            Connection connection = DBConnection.getConnection();
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setString(1, e);
            ResultSet rst = prepareStatement.executeQuery();
            
            if (rst.next()) {
                category = new Category(e, rst.getString(2));
            }
        } finally {
            readWriteLock.readLock().unlock();
        }
        return category;
    }

    @Override
    public boolean update(Category t) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(String e) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Category> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Category> categorys = new ArrayList<>();
        readWriteLock.readLock().lock();
        try {
            String sql = "select * from Category";
            Connection connection = DBConnection.getConnection();
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery(sql);
            categorys = new ArrayList<>();
            while (rst.next()) {
                categorys.add(new Category(rst.getString(1), rst.getString(2)));
            }
            DBConnection.getPool().returnConnection((com.redonz.pms.server.connectionpool.DBConnection) connection);
        } finally {
            readWriteLock.readLock().unlock();
        }
        return categorys;
    }

    @Override
    public String lastId() throws SQLException, ClassNotFoundException {
        String lastId = null;
        readWriteLock.readLock().lock();
        try {
            String sql = "select categoryId from Category order by 1 desc limit 1 ";
            Connection connection = DBConnection.getConnection();
            ResultSet rst = connection.createStatement().executeQuery(sql);
       
            if (rst.next()) {
                lastId = rst.getString(1);
            }
        } finally {
            readWriteLock.readLock().unlock();
        }
        return lastId;
    }

    @Override
    public boolean addList(ArrayList<Category> tList) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
