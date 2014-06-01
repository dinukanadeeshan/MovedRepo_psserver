/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redonz.pms.server.dao.impl;

import com.redonz.pms.common.model.BatchItem;
import com.redonz.pms.common.model.CustomerOrderDetail;
import com.redonz.pms.common.model.Item;
import com.redonz.pms.common.model.ItemDetail;
import com.redonz.pms.server.controller.impl.BatchItemControllerImpl;
import com.redonz.pms.server.dao.BatchItemDAO;
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
public class BatchItemDAOImpl implements BatchItemDAO {

    private static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    @Override
    public ArrayList<BatchItem> goingToExpItems(String date) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String lastId() throws SQLException, ClassNotFoundException {
        String lastId = null;
        readWriteLock.readLock().lock();
        try {
            String sql = "select itemCode from BatchItem order by 1 desc limit 1";
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
    public boolean insert(BatchItem t) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BatchItem search(String e) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(BatchItem t) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(String e) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Batch Item Deleteing not allowed..."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<BatchItem> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<BatchItem> batchItems = new ArrayList<>();
        readWriteLock.readLock().lock();
        try {
            String sql = "select * from BatchItem";
            Connection connection = DBConnection.getConnection();
            ResultSet rst = connection.createStatement().executeQuery(sql);

            while (rst.next()) {
                batchItems.add(new BatchItem(rst.getString("itemCode"), rst.getString("barcode"), rst.getString("batchNo"), rst.getString("expDate"), rst.getDouble("unitPrice"), rst.getInt("qtyOnHand")));
            }
            DBConnection.getPool().returnConnection((com.redonz.pms.server.pool.connection.DBConnection) connection);
        } finally {
            readWriteLock.readLock().unlock();
        }
        return batchItems;
    }

    @Override
    public boolean addList(ArrayList<BatchItem> tList) throws SQLException, ClassNotFoundException {
        String sql = "insert into batchItem values(?,?,?,?,?,?)";
        readWriteLock.writeLock().lock();
        Connection connection = DBConnection.getConnection();

        try {
            connection.setAutoCommit(false);
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            for (int i = 0; i < tList.size(); i++) {
                BatchItem batchItem = tList.get(i);
                prepareStatement.setString(1, batchItem.getBarCode());
                prepareStatement.setString(3, batchItem.getBatchNo());
                prepareStatement.setString(2, batchItem.getItemCode());
                prepareStatement.setDouble(4, batchItem.getQty());
                prepareStatement.setString(5, batchItem.getExpDate());
                prepareStatement.setDouble(6, batchItem.getUnitPrice());
                prepareStatement.addBatch();
            }
            int[] exeRes = prepareStatement.executeBatch();
            for (int i = 0; i < exeRes.length; i++) {
                int j = exeRes[i];
                if (j <= 0) {
                    connection.rollback();
                    return false;
                }

            }
            connection.commit();

            return true;
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        } finally {
            connection.setAutoCommit(true);
            DBConnection.getPool().returnConnection((com.redonz.pms.server.pool.connection.DBConnection) connection);
            readWriteLock.writeLock().unlock();
        }
    }

    @Override
    public ArrayList<BatchItem> batchesForBarcode(String barcode) throws SQLException, ClassNotFoundException {
        ArrayList<BatchItem> batchItems = new ArrayList<>();
        readWriteLock.readLock().lock();
        try {
            String sql = "select itemCode, batchNo, unitPrice, qtyOnHand, expDate from BatchItem where barcode = ?";

            Connection connection = DBConnection.getConnection();
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setString(1, barcode);
            ResultSet rst = prepareStatement.executeQuery();


            while (rst.next()) {
                batchItems.add(new BatchItem(rst.getString("itemCode"), barcode, rst.getString("batchNo"), rst.getString("expDate"), rst.getDouble("unitPrice"), rst.getInt("qtyOnHand")));
            }
            DBConnection.getPool().returnConnection((com.redonz.pms.server.pool.connection.DBConnection) connection);
        } finally {
            readWriteLock.readLock().unlock();
        }
        return batchItems;
    }

    @Override
    public String barcodeForItemCode(String itemCode) throws SQLException, ClassNotFoundException {
        String barcode = null;
        readWriteLock.readLock().lock();
        try {
            String sql = "select barcode from BatchItem where itemCode = ?";
            Connection connection = DBConnection.getConnection();
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setString(1, itemCode);
            ResultSet rst = prepareStatement.executeQuery();

            if (rst.next()) {
                barcode = rst.getString(1);
            }
            DBConnection.getPool().returnConnection((com.redonz.pms.server.pool.connection.DBConnection) connection);
        } finally {
            readWriteLock.readLock().unlock();
        }
        return barcode;
    }

    @Override
    public ArrayList<BatchItem> availableBatchesForBarcode(String barcode) throws SQLException, ClassNotFoundException {
        ArrayList<BatchItem> batchItems = new ArrayList<>();
        readWriteLock.readLock().lock();
        try {
            String sql = "select itemCode, batchNo, unitPrice, qtyOnHand, expDate from BatchItem where barcode = ? and qtyOnHand > 0";

            Connection connection = DBConnection.getConnection();
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setString(1, barcode);
            ResultSet rst = prepareStatement.executeQuery();


            while (rst.next()) {
//                if (!BatchItemControllerImpl.isReserved(rst.getString("itemCode"))) {
                batchItems.add(new BatchItem(rst.getString("itemCode"), barcode, rst.getString("batchNo"), rst.getString("expDate"), rst.getDouble("unitPrice"), rst.getInt("qtyOnHand")));

//                }
            }
            DBConnection.getPool().returnConnection((com.redonz.pms.server.pool.connection.DBConnection) connection);
        } finally {
            readWriteLock.readLock().unlock();
        }
        return batchItems;
    }

    @Override
    public ItemDetail batchesForBatchNo(String batchNo) throws SQLException, ClassNotFoundException {
        ItemDetail detail = null;
        readWriteLock.readLock().lock();
        try {
            String sql = "select barcode, itemCode, batchNo, unitPrice, qtyOnHand, expDate from BatchItem where batchNo = ?";

            Connection connection = DBConnection.getConnection();
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setString(1, batchNo);
            ResultSet rst = prepareStatement.executeQuery();

            ArrayList<BatchItem> batchItems = new ArrayList<>();

            if (rst.next()) {
                batchItems.add(new BatchItem(rst.getString("itemCode"), rst.getString(1), rst.getString("batchNo"), rst.getString("expDate"), rst.getDouble("unitPrice"), rst.getInt("qtyOnHand")));
                Item item = new ItemDAOImpl().search(rst.getString(1));
                detail = new ItemDetail(item, batchItems);
            }

            DBConnection.getPool().returnConnection((com.redonz.pms.server.pool.connection.DBConnection) connection);
        } finally {
            readWriteLock.readLock().unlock();
        }
        return detail;
    }

    @Override
    public ArrayList<ItemDetail> batchesByExpDate(String from, String to) throws SQLException, ClassNotFoundException {
        ArrayList<ItemDetail> itemDetalList = new ArrayList<>();
        readWriteLock.readLock().lock();
        try {
            Connection connection = DBConnection.getConnection();
            String sql = "SELECT barcode FROM batchitem b where expDate between ? and ? group by(barcode)";
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setString(1, from);
            prepareStatement.setString(2, to);
            ResultSet rst1 = prepareStatement.executeQuery();



            sql = "SELECT * FROM batchitem b where expDate between ? and ? and barcode = ?";
            prepareStatement = connection.prepareStatement(sql);
            ItemDAO itemDAO = new ItemDAOImpl();
            while (rst1.next()) {
                String barcode = rst1.getString(1);
                prepareStatement.setString(1, from);
                prepareStatement.setString(2, to);
                prepareStatement.setString(3, barcode);

                ResultSet rst2 = prepareStatement.executeQuery();


                Item item = itemDAO.search(barcode);

                ArrayList<BatchItem> batchItems = new ArrayList<>();

                while (rst2.next()) {
                    batchItems.add(new BatchItem(rst2.getString("itemCode"), rst2.getString(1), rst2.getString("batchNo"), rst2.getString("expDate"), rst2.getDouble("unitPrice"), rst2.getInt("qtyOnHand")));
                }
                itemDetalList.add(new ItemDetail(item, batchItems));

            }

            DBConnection.getPool().returnConnection((com.redonz.pms.server.pool.connection.DBConnection) connection);
        } finally {
            readWriteLock.readLock().unlock();
        }
        return itemDetalList;
    }

    @Override
    public ItemDetail availableBatchesForBatchNo(String batchNo) throws SQLException, ClassNotFoundException {
        ItemDetail detail = null;
        readWriteLock.readLock().lock();
        try {
            String sql = "select barcode, itemCode, batchNo, unitPrice, qtyOnHand, expDate from BatchItem where batchNo = ? qtyOnHand > 0";

            Connection connection = DBConnection.getConnection();
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setString(1, batchNo);
            ResultSet rst = prepareStatement.executeQuery();

            ArrayList<BatchItem> batchItems = new ArrayList<>();

            if (rst.next()) {
                batchItems.add(new BatchItem(rst.getString("itemCode"), rst.getString(1), rst.getString("batchNo"), rst.getString("expDate"), rst.getDouble("unitPrice"), rst.getInt("qtyOnHand")));
                Item item = new ItemDAOImpl().search(rst.getString(1));
                detail = new ItemDetail(item, batchItems);
            }

            DBConnection.getPool().returnConnection((com.redonz.pms.server.pool.connection.DBConnection) connection);
        } finally {
            readWriteLock.readLock().unlock();
        }
        return detail;
    }

    @Override
    public ArrayList<ItemDetail> availableBatchesByExpDate(String from, String to) throws SQLException, ClassNotFoundException {
        ArrayList<ItemDetail> itemDetalList = new ArrayList<>();
        readWriteLock.readLock().lock();
        try {
            Connection connection = DBConnection.getConnection();
            String sql = "SELECT barcode FROM batchitem b where expDate between ? and ? and qtyOnHand > 0 group by(barcode) ";
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setString(1, from);
            prepareStatement.setString(2, to);
            ResultSet rst1 = prepareStatement.executeQuery();




            sql = "SELECT * FROM batchitem b where expDate between ? and ? and barcode = ? and qtyOnHand > 0";
            prepareStatement = connection.prepareStatement(sql);
            ItemDAO itemDAO = new ItemDAOImpl();
            while (rst1.next()) {
                String barcode = rst1.getString(1);
                prepareStatement.setString(1, from);
                prepareStatement.setString(2, to);
                prepareStatement.setString(3, barcode);

                ResultSet rst2 = prepareStatement.executeQuery();


                Item item = itemDAO.search(barcode);

                ArrayList<BatchItem> batchItems = new ArrayList<>();

                while (rst2.next()) {
                    batchItems.add(new BatchItem(rst2.getString("itemCode"), rst2.getString(1), rst2.getString("batchNo"), rst2.getString("expDate"), rst2.getDouble("unitPrice"), rst2.getInt("qtyOnHand")));
                }
                itemDetalList.add(new ItemDetail(item, batchItems));

            }

            DBConnection.getPool().returnConnection((com.redonz.pms.server.pool.connection.DBConnection) connection);
        } finally {
            readWriteLock.readLock().unlock();
        }
        return itemDetalList;
    }

    @Override
    public Item itemForItemCode(String itemCode) throws SQLException, ClassNotFoundException {
        Item item = null;
        readWriteLock.readLock().lock();
        try {
            String sql = "select i.* from BatchItem Natural join Item i where itemCode = ?";

            Connection connection = DBConnection.getConnection();
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setString(1, itemCode);
            ResultSet rst = prepareStatement.executeQuery();



            if (rst.next()) {

                item = new Item(rst.getString(2), rst.getString(1), rst.getString("description"), rst.getDouble(4));

            }

            DBConnection.getPool().returnConnection((com.redonz.pms.server.pool.connection.DBConnection) connection);
        } finally {
            readWriteLock.readLock().unlock();
        }
        return item;
    }

    @Override
    public boolean updateItemQty(ArrayList<CustomerOrderDetail> customerOrderDetails, Connection connection) throws ClassNotFoundException, SQLException {
        readWriteLock.writeLock().lock();

        try {
            String sql = "update batchItem set qtyOnHand = qtyOnHand - ? where itemCode = ? ";
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            for (CustomerOrderDetail customerOrderDetail : customerOrderDetails) {
                prepareStatement.setDouble(1, customerOrderDetail.getQty());
                prepareStatement.setString(2, customerOrderDetail.getItemCode());
                prepareStatement.addBatch();
            }
            int[] res = prepareStatement.executeBatch();
            for (int i : res) {
                if (i <= 0) {
                    return false;
                }
            }
        } finally {
            readWriteLock.writeLock().unlock();
        }
        return true;
    }
}
