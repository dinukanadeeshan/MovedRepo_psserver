/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redonz.pms.server.controller.impl;

import com.redonz.pms.common.controller.UserController;
import com.redonz.pms.common.model.User;
import com.redonz.pms.server.dao.UserDAO;
import com.redonz.pms.server.dao.impl.UserDAOImpl;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author DI_SH
 */
public class UserControllerImpl extends  UnicastRemoteObject implements  UserController{

    private UserDAO userDAO = new UserDAOImpl();
    
    public UserControllerImpl()throws RemoteException{
        
    }

    @Override
    public boolean addUser(User user) throws RemoteException, SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean updateUser(User user) throws RemoteException, SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public User getUser(User user) throws RemoteException, SQLException, ClassNotFoundException {
        return userDAO.get(user);
    }
    @Override
    public boolean removeUser(String user) throws RemoteException, SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<User> getAllUsers() throws RemoteException, SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
}
