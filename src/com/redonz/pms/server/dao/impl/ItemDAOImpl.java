/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redonz.pms.server.dao.impl;

import com.redonz.pms.common.model.Category;
import com.redonz.pms.common.model.CustomerOrderDetail;
import com.redonz.pms.common.model.Item;
import com.redonz.pms.common.model.ItemDetail;
import com.redonz.pms.server.dao.ItemDAO;
import com.redonz.pms.server.db.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *
 * @author Nadeeshan
 */
public class ItemDAOImpl implements ItemDAO {

    private static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    @Override
    public boolean insert(Item t) throws SQLException, ClassNotFoundException {
        readWriteLock.writeLock().lock();
        boolean res = false;
        Connection connection = DBConnection.getConnection();
        try {
            String sql = "insert into item values(?,?,?,?)";

            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setString(1, t.getCategoryId());
            prepareStatement.setString(2, t.getBarcode());
            prepareStatement.setString(3, t.getDescription());
            prepareStatement.setDouble(4, t.getReorderlevel());
            res = prepareStatement.executeUpdate() > 0;

        } finally {
            DBConnection.getPool().returnConnection((com.redonz.pms.server.connectionpool.DBConnection) connection);
            readWriteLock.writeLock().unlock();
        }

        return res;

    }

    @Override
    public Item search(String e) throws SQLException, ClassNotFoundException {
        Item item = null;
        readWriteLock.readLock().lock();
        Connection connection = DBConnection.getConnection();
        try {
            String sql = "select * from item where barcode = ?";

            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setString(1, e);
            ResultSet rst = prepareStatement.executeQuery();

            if (rst.next()) {
                item = new Item(e, rst.getString(1), rst.getString("description"), rst.getDouble(4));
            }


        } finally {
            DBConnection.getPool().returnConnection((com.redonz.pms.server.connectionpool.DBConnection) connection);
            readWriteLock.readLock().unlock();
        }

        return item;
    }

    @Override
    public boolean update(Item t) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(String e) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Item> getAll() throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ItemDetail getAbstractItemDetail(String barcode) throws ClassNotFoundException, SQLException {
        ItemDetail itemDetail = null;
        readWriteLock.readLock().lock();
        Connection connection = DBConnection.getConnection();
        try {
            String sql = "select c.*, i.description from Item i, Category c where i.categoryId = c.categoryId and i.barcode = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, barcode);
            ResultSet rst = preparedStatement.executeQuery();


            if (rst.next()) {
                itemDetail = new ItemDetail(new Item(barcode, rst.getString(3)), new Category(rst.getString(1), rst.getString(2)));
            }

        } finally {
            DBConnection.getPool().returnConnection((com.redonz.pms.server.connectionpool.DBConnection) connection);
            readWriteLock.readLock().unlock();
        }
        return itemDetail;
    }

    @Override
    public String lastId() throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Can't get last id cause primary key is barcode");
    }

    @Override
    public boolean addList(ArrayList<Item> tList) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Item> itemsByDescription(String description) throws ClassNotFoundException, SQLException {
        ArrayList<Item> items = new ArrayList<>();
        readWriteLock.readLock().lock();
        Connection connection = DBConnection.getConnection();
        try {
            String sql = "select * from item where description like ?";

            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setString(1, "%" + description + "%");
            ResultSet rst = prepareStatement.executeQuery();

            while (rst.next()) {
                items.add(new Item(rst.getString(2), rst.getString(1), rst.getString("description"), rst.getDouble(4)));
            }

        } finally {
            readWriteLock.readLock().unlock();
            DBConnection.getPool().returnConnection((com.redonz.pms.server.connectionpool.DBConnection) connection);
        }

        return items;
    }

    @Override
    public ArrayList<Item> searchLike(String e) throws ClassNotFoundException, SQLException {
        ArrayList<Item> items = new ArrayList<>();
        readWriteLock.readLock().lock();
        Connection connection = DBConnection.getConnection();
        try {
            String sql = "select * from item where barcode like ?";
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setString(1, e + "%");
            ResultSet rst = prepareStatement.executeQuery();

            while (rst.next()) {
                items.add(new Item(rst.getString(2), rst.getString(1), rst.getString("description"), rst.getDouble(4)));
            }

        } finally {
            DBConnection.getPool().returnConnection((com.redonz.pms.server.connectionpool.DBConnection) connection);
            readWriteLock.readLock().unlock();
        }
        return items;
    }

    @Override
    public ArrayList<Item> itemsByCategory(String categoryId) throws ClassNotFoundException, SQLException {
        ArrayList<Item> items = new ArrayList<>();
        readWriteLock.readLock()
                .lock();
        Connection connection = DBConnection.getConnection();
        try {
            String sql = "select * from item where categoryId = ?";
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setString(1, categoryId);
            ResultSet rst = prepareStatement.executeQuery();

            while (rst.next()) {
                items.add(new Item(rst.getString(2), rst.getString(1), rst.getString("description"), rst.getDouble(4)));
            }

        } finally {
            readWriteLock.readLock().unlock();
            DBConnection.getPool().returnConnection((com.redonz.pms.server.connectionpool.DBConnection) connection);
        }
        return items;
    }

    
}
