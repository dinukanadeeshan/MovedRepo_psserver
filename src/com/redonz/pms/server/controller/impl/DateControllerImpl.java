/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redonz.pms.server.controller.impl;

import com.redonz.pms.common.controller.DateController;
import com.redonz.pms.server.db.DBConnection;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nadeeshan
 */
public class DateControllerImpl extends UnicastRemoteObject implements DateController{
    public DateControllerImpl()throws RemoteException{
        
    }
    @Override
    public String getCurrentDate() throws RemoteException, SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getConnection();
        ResultSet rst = connection.createStatement().executeQuery("select curdate()");
        rst.next();
        DBConnection.getPool().returnConnection((com.redonz.pms.server.connectionpool.DBConnection)connection);
        return rst.getString(1);
    }

    @Override
    public String getCurrentTime() throws RemoteException, SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getConnection();
        ResultSet rst = connection.createStatement().executeQuery("select curtime()");
        rst.next();
        DBConnection.getPool().returnConnection((com.redonz.pms.server.connectionpool.DBConnection)connection);
        return rst.getString(1);
    }

    @Override
    public boolean changeSystemDate(String date) throws RemoteException{
        int waitFor = -1;
        try {
//             waitFor = new ProcessBuilder("cmd","/C","date",date).start().waitFor();
            waitFor = Runtime.getRuntime().exec("cmd /C date "+date).waitFor();
             System.out.println(waitFor);
        } catch (IOException ex) {
            Logger.getLogger(DateControllerImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(DateControllerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return waitFor == 0;
    }
    
}
