/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redonz.pms.server.db;

import com.redonz.pms.server.pool.connection.DBConnectionDriver;
import com.redonz.pms.server.pool.connection.DBConnectionPool;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author Nadeeshan
 */
public class DBConnection {
    private static DBConnectionDriver driver;
    public static Connection getConnection() throws SQLException, ClassNotFoundException, FileNotFoundException, IOException{
        Properties dbConfig = new Properties();
        dbConfig.load(new FileInputStream(new File("dbconfig.properties")));
        String user = (String) dbConfig.get("user");
        String password = (String) dbConfig.get("password");
        String dbName = (String) dbConfig.get("dbname");
        String host= (String) dbConfig.get("host");
        
        driver=DBConnectionDriver.initializeDriver("jdbc:mysql://"+host+"/"+dbName, user, password, "com.mysql.jdbc.Driver");
        return DriverManager.getConnection("jdbc:pool");
    }
    
    public static DBConnectionPool getPool(){
        if (driver != null) {
            return driver.getConnectionPool();
        }
        throw new UnsupportedOperationException("Driver not initialized yet...");
    }
}
