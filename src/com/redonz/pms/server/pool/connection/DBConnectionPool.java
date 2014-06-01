/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redonz.pms.server.pool.connection;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nadeeshan
 */
public class DBConnectionPool {
    private List<DBConnection> connectionList;
    private String url;
    private String userName;
    private String password;
    private final int MAX_CONNECTION_COUNT = 10;

    public DBConnectionPool(String url, String userName, String password) {
       
        this.url = url;
        this.userName = userName;
        this.password = password;
        connectionList = new ArrayList<>(MAX_CONNECTION_COUNT);
        new ReapConnections(this).start();
    }
    
    
    
    public DBConnection getConnection() throws SQLException{
        
        for (DBConnection con : connectionList) {
            if (con.lease()) {
                return con;
            }
        }
        
        DBConnection con = new DBConnection(DriverManager.getConnection(url, userName, password),connectionList.size()+1);
       
        connectionList.add(con);
        return con;
    }
    
    public void reapConnections(){
        for (DBConnection con : connectionList) {
            if (System.currentTimeMillis() - con.getLastUsed() > 1500 && !con.isInUse() && !con.isValid()) {
                removeConnection(con);
            
            }
        }
    }
    
    public void removeConnection(DBConnection con){
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBConnectionPool.class.getName()).log(Level.SEVERE, null, ex);
        }
        connectionList.remove(con);
    }
    public void returnConnection(DBConnection con){
       
        con.stopLease();
    }
    class ReapConnections extends Thread{
        private DBConnectionPool pool;
        public ReapConnections(DBConnectionPool pool) {
            this.pool = pool;
            setDaemon(true);
        }
        
        @Override
        public void run(){
            while (true) {
                pool.reapConnections();
//      
                try {
                    Thread.sleep(1000 * 60 * 60);
                } catch (InterruptedException ex) {
                    Logger.getLogger(DBConnectionDriver.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        }
    }
}
