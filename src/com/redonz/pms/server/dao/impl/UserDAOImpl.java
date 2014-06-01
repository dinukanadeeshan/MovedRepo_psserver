/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redonz.pms.server.dao.impl;

import com.redonz.pms.common.model.User;
import com.redonz.pms.server.dao.UserDAO;
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
public class UserDAOImpl implements UserDAO{
private static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    @Override
    public User get(User u) throws ClassNotFoundException, SQLException , FileNotFoundException, IOException{
        User user = null;
        readWriteLock.readLock().lock();
        try {
            String sql = "select * from User where userName = ? and password = (password(?))";
            Connection connection = DBConnection.getConnection();
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setString(1, u.getUser());
            prepareStatement.setString(2, u.getPassword());
            ResultSet rst = prepareStatement.executeQuery();
            
            if (rst.next()) {
                u.setPrivilage(rst.getInt(3));
                user = u;
            }
            DBConnection.getPool().returnConnection((com.redonz.pms.server.pool.connection.DBConnection) connection);
        } finally {
            readWriteLock.readLock().unlock();
        }
        return user;
        
    }

    @Override
    public boolean insert(User t) throws SQLException, ClassNotFoundException , FileNotFoundException, IOException{
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public User search(String e) throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(User t) throws SQLException, ClassNotFoundException , FileNotFoundException, IOException{
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(String e) throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<User> getAll() throws SQLException, ClassNotFoundException , FileNotFoundException, IOException{
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String lastId() throws SQLException, ClassNotFoundException , FileNotFoundException, IOException{
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addList(ArrayList<User> tList) throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
