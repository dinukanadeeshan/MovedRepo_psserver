/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redonz.pms.server.dao;

import com.redonz.pms.common.model.CustomerOrderDetail;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Nadeeshan
 */
public interface CustomerOrderDetailDAO extends DAO<CustomerOrderDetail, String> {

    ArrayList<CustomerOrderDetail> orderDetailsForOrderId(String e) throws SQLException, ClassNotFoundException, FileNotFoundException, IOException;

    public boolean addOrderDetailList(ArrayList<CustomerOrderDetail> orderDetails, Connection connection) throws SQLException, ClassNotFoundException, FileNotFoundException, IOException;
}
