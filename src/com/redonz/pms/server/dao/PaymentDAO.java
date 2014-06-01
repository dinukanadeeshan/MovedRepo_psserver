/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redonz.pms.server.dao;

import com.redonz.pms.common.model.Payment;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Nadeeshan
 */
public interface PaymentDAO extends  DAO<Payment, String>{
    boolean insert(Payment payment, Connection connection) throws SQLException , ClassNotFoundException, FileNotFoundException, IOException;
}
