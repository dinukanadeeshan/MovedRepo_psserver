/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redonz.pms.server.pool.connection;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 *
 * @author Nadeeshan
 */
public class DBConnectionDriver implements Driver{
    private static DBConnectionDriver driver;
    private final String PREFIX = "jdbc:pool";
    private DBConnectionPool pool;

    private  DBConnectionDriver(DBConnectionPool pool, String className) throws SQLException, ClassNotFoundException {
       
        this.pool = pool;
        DriverManager.registerDriver(this);
        Class.forName(className);
        
    }
    public DBConnectionPool getConnectionPool(){
        return pool;
    }
    public static DBConnectionDriver initializeDriver(String url, String user, String password, String className) throws SQLException, ClassNotFoundException{
       
        if (driver == null) {
            driver = new DBConnectionDriver(new DBConnectionPool(url, user, password),className);
        }
        return driver;
    }
    
    @Override
    public Connection connect(String url, Properties info) throws SQLException {
      
        if (acceptsURL(url)) {
            return pool.getConnection();
        } 
        throw new SQLException("Invalid url : "+url);
    }

    @Override
    public boolean acceptsURL(String url) throws SQLException {
        if (url.startsWith(PREFIX)) {
            return true;
        }
        return false;
    }

    @Override
    public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
        return new DriverPropertyInfo[]{};
    }

    @Override
    public int getMajorVersion() {
        return 1;
    }

    @Override
    public int getMinorVersion() {
        return 0;
    }

    @Override
    public boolean jdbcCompliant() {
        return false;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
    
}
