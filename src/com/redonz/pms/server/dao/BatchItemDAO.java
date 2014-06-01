/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redonz.pms.server.dao;

import com.redonz.pms.common.model.BatchItem;
import com.redonz.pms.common.model.CustomerOrderDetail;
import com.redonz.pms.common.model.Item;
import com.redonz.pms.common.model.ItemDetail;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Nadeeshan
 */
public interface BatchItemDAO extends DAO<BatchItem, String> {

    public ArrayList<BatchItem> goingToExpItems(String date) throws SQLException, ClassNotFoundException;

    public ArrayList<BatchItem> batchesForBarcode(String barcode) throws SQLException, ClassNotFoundException;

    public ArrayList<BatchItem> availableBatchesForBarcode(String barcode) throws SQLException, ClassNotFoundException;

    public ItemDetail batchesForBatchNo(String batchNo) throws SQLException, ClassNotFoundException;

    public ItemDetail availableBatchesForBatchNo(String batchNo) throws SQLException, ClassNotFoundException;

    public ArrayList<ItemDetail> batchesByExpDate(String from, String to) throws SQLException, ClassNotFoundException;

    public ArrayList<ItemDetail> availableBatchesByExpDate(String from, String to) throws SQLException, ClassNotFoundException;

    public String barcodeForItemCode(String itemCode) throws SQLException, ClassNotFoundException;

    public Item itemForItemCode(String itemCode) throws SQLException, ClassNotFoundException;

    public boolean updateItemQty(ArrayList<CustomerOrderDetail> customerOrderDetails, Connection connection) throws ClassNotFoundException, SQLException;
}
