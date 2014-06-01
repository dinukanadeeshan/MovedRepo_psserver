/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redonz.pms.server.dao.impl;

import com.redonz.pms.common.model.Payment;
import com.redonz.pms.server.dao.PaymentDAO;
import com.redonz.pms.server.db.DBConnection;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DI_SH
 */
public class PaymentDAOImpl implements PaymentDAO {

    private static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    @Override
    public ArrayList getAll() throws SQLException, ClassNotFoundException , FileNotFoundException, IOException{
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String lastId() throws SQLException, ClassNotFoundException , FileNotFoundException, IOException{
        String lastId = null;
        readWriteLock.readLock()
                .lock();
        try {
            String sql = "select paymentId from Payment order by 1 desc limit 1";
            Connection connection = DBConnection.getConnection();
            ResultSet rst = connection.createStatement().executeQuery(sql);

            if (rst.next()) {
                lastId = rst.getString(1);
            }
            DBConnection.getPool().returnConnection((com.redonz.pms.server.pool.connection.DBConnection) connection);
        } finally {
            readWriteLock.readLock().unlock();
        }
        return lastId;

    }

    @Override
    public boolean addList(ArrayList tList) throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean insert(Payment t) throws SQLException, ClassNotFoundException , FileNotFoundException, IOException{
        String sql = "insert into Payment values(?,?,?,?)";
        Connection connection = DBConnection.getConnection();
        readWriteLock.writeLock().lock();
        try {
            connection.setAutoCommit(false);
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setString(1, t.getOrderId());
            prepareStatement.setString(2, t.getPaymentId());
            prepareStatement.setDouble(3, t.getAmount());
            prepareStatement.setString(4, t.getDate());
            System.out.println("---1");
            int res = prepareStatement.executeUpdate();
            System.out.println("---2");
            if (res > 0) {
                System.out.println("---3");
                boolean r = new CustomerOrderDAOImpl().updateOrderBalance(t.getOrderId(), t.getAmount(), connection);
                System.out.println("---4 " + r);
                if (r) {
                    System.out.println("yyyyy");
                    connection.commit();
                    System.out.println("commited");
                    return true;
                } else {
                    connection.rollback();
                    return false;
                }
            } else {
                connection.rollback();
                return false;
            }
        } catch (SQLException e) {
            System.out.println("----------");
            Logger.getLogger(PaymentDAOImpl.class.getName()).log(Level.SEVERE, null, e);
            connection.rollback();
            e.printStackTrace();
            System.out.println(e);

            System.out.println("----------2");
            throw e;
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex);
            return false;
//            throw ex;
        } finally {
            connection.setAutoCommit(true);
            DBConnection.getPool().returnConnection((com.redonz.pms.server.pool.connection.DBConnection) connection);
            readWriteLock.writeLock().unlock();
        }
    }

    @Override
    public boolean insert(Payment t, Connection connection) throws SQLException, ClassNotFoundException , FileNotFoundException, IOException{
        String sql = "insert into Payment values(?,?,?,?)";

        readWriteLock.writeLock().lock();
        try {

            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setString(1, t.getOrderId());
            prepareStatement.setString(2, t.getPaymentId());
            prepareStatement.setDouble(3, t.getAmount());
            prepareStatement.setString(4, t.getDate());
            System.out.println("---1");
            int res = prepareStatement.executeUpdate();
            System.out.println("---2");
            if (res > 0) {
                System.out.println("---3");
                boolean r = new CustomerOrderDAOImpl().updateOrderBalance(t.getOrderId(), t.getAmount(), connection);
                System.out.println("---4 " + r);
                if (r) {

                    return true;
                } else {

                    return false;
                }
            } else {

                return false;
            }
        } catch (SQLException e) {
            System.out.println("----------");
            Logger.getLogger(PaymentDAOImpl.class.getName()).log(Level.SEVERE, null, e);

            e.printStackTrace();
            System.out.println(e);

            System.out.println("----------2");
            throw e;
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex);
            return false;
//            throw ex;
        } finally {


            readWriteLock.writeLock().unlock();
        }
    }

    @Override
    public Payment search(String e) throws SQLException, ClassNotFoundException , FileNotFoundException, IOException{
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(Payment t) throws SQLException, ClassNotFoundException , FileNotFoundException, IOException{
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(String e) throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
