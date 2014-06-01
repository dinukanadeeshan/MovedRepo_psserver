/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redonz.pms.server.dao;

import com.redonz.pms.common.model.User;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

/**
 *
 * @author DI_SH
 * 
 */
public interface UserDAO extends DAO<User,String>{
    User get(User u) throws ClassNotFoundException, SQLException, FileNotFoundException, IOException; 
}
