/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redonz.pms.server.controller.impl;

import com.redonz.pms.common.controller.CustomerController;
import com.redonz.pms.common.model.Customer;
import com.redonz.pms.server.dao.CustomerDAO;
import com.redonz.pms.server.dao.impl.CustomerDAOImpl;
import com.redonz.pms.server.model.ID;
import com.redonz.pms.server.others.IDGen;
import com.redonz.pms.server.pool.id.CustomerIdPool;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Nadeeshan
 */
public class CustomerControllerImpl extends UnicastRemoteObject implements  CustomerController{
    private CustomerDAO customerDAO = new CustomerDAOImpl();
    private static CustomerIdPool customerIdPool = new CustomerIdPool();
    
    public CustomerControllerImpl() throws RemoteException{
        
    }
    
    @Override
    public boolean saveCustomer(Customer customer) throws RemoteException,SQLException,ClassNotFoundException , FileNotFoundException, IOException{
        return customerDAO.insert(customer);
    }

    @Override
    public Customer searchCustomer(String custId) throws RemoteException ,SQLException,ClassNotFoundException, FileNotFoundException, IOException{
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean updateCustomer(Customer customer) throws RemoteException ,SQLException,ClassNotFoundException, FileNotFoundException, IOException{
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteCustomer(String custId) throws RemoteException ,SQLException,ClassNotFoundException, FileNotFoundException, IOException{
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Customer> getAllCustomers() throws RemoteException ,SQLException,ClassNotFoundException, FileNotFoundException, IOException{
        return customerDAO.getAll();
    }

    @Override
    public String getLastCustomerID() throws RemoteException, SQLException, ClassNotFoundException , FileNotFoundException, IOException{
        return customerDAO.lastId();
    }

    @Override
    public String getNextCustomerId() throws RemoteException, SQLException, ClassNotFoundException, FileNotFoundException, IOException {
        ID id = customerIdPool.getId();
        if (id == null) {
            String nextID = IDGen.getNextId(customerDAO.lastId());
            customerIdPool.addId(nextID);
            return nextID;
        }
        return id.getId();
    }

    @Override
    public boolean getReleaseCustomerId(String id) throws RemoteException {
        return customerIdPool.releaseID(id);
    }
    
}
