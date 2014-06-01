/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redonz.pms.server.db;

import com.redonz.pms.server.connectionpool.DBConnectionDriver;
import com.redonz.pms.server.connectionpool.DBConnectionPool;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Nadeeshan
 */
public class DBConnection {
    private static DBConnectionDriver driver;
    public static Connection getConnection() throws SQLException, ClassNotFoundException{
        driver=DBConnectionDriver.initializeDriver("jdbc:mysql://localhost/ps", "root", "123", "com.mysql.jdbc.Driver");
        return DriverManager.getConnection("jdbc:pool");
    }
    
    public static DBConnectionPool getPool(){
        if (driver != null) {
            return driver.getConnectionPool();
        }
        throw new UnsupportedOperationException("Driver not initialized yet...");
    }
}
