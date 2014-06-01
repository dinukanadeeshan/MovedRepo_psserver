/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redonz.pms.server.dao;

import com.redonz.pms.common.model.CustomerOrderDetail;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Nadeeshan
 */
public interface CustomerOrderDetailDAO extends DAO<CustomerOrderDetail,String>{
    ArrayList<CustomerOrderDetail> orderDetailsForOrderId(String e)throws SQLException, ClassNotFoundException;
    public boolean addOrderDetailList(ArrayList<CustomerOrderDetail> orderDetails, Connection connection)throws SQLException, ClassNotFoundException;
}
