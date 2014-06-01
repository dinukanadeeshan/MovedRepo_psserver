/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redonz.pms.server.controller.impl;

import com.redonz.pms.common.controller.BatchItemController;
import com.redonz.pms.common.model.BatchItem;
import com.redonz.pms.common.model.Item;
import com.redonz.pms.common.model.ItemDetail;
import com.redonz.pms.common.observer.Observer;
import com.redonz.pms.server.reservation.Reservation;
import com.redonz.pms.server.dao.BatchItemDAO;
import com.redonz.pms.server.dao.impl.BatchItemDAOImpl;
import com.redonz.pms.server.observable.BatchItemObservable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nadeeshan
 */
public class BatchItemControllerImpl extends UnicastRemoteObject implements BatchItemController {

    private BatchItemDAO batchItemDAO;
    private static Reservation<BatchItemController> batchItemReservation = new Reservation<>();
    private static BatchItemObservable batchItemObservable = new BatchItemObservable();

    public BatchItemControllerImpl() throws RemoteException {
        this.batchItemDAO = new BatchItemDAOImpl();
    }

    @Override
    public boolean saveBatchItem(BatchItem batchItem) throws SQLException, ClassNotFoundException, RemoteException , FileNotFoundException, IOException{
        boolean res = batchItemDAO.insert(batchItem);
        if (res) {
            batchItemObservable.batchItemAdded(batchItem);
        }
        return res;
    }

    @Override
    public BatchItem searchBatchItem(String itemCode) throws SQLException, ClassNotFoundException, RemoteException, FileNotFoundException, IOException {
        return batchItemDAO.search(itemCode);
    }

    @Override
    public boolean updateBatchItem(BatchItem batchItem) throws SQLException, ClassNotFoundException, RemoteException , FileNotFoundException, IOException{
        return batchItemDAO.update(batchItem);
    }

    @Override
    public boolean deleteBatchItem(String itemCode) throws SQLException, ClassNotFoundException, RemoteException , FileNotFoundException, IOException{
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<BatchItem> getGoingToExpItems(String date) throws SQLException, ClassNotFoundException, RemoteException , FileNotFoundException, IOException{
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getLastBatchItemCode() throws SQLException, ClassNotFoundException, RemoteException, FileNotFoundException, IOException {
        return batchItemDAO.lastId();
    }

    @Override
    public boolean saveBatchItemList(final ArrayList<BatchItem> batchItemList) throws SQLException, ClassNotFoundException, RemoteException, FileNotFoundException, IOException {
        boolean res = batchItemDAO.addList(batchItemList);
        if (res) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        batchItemObservable.batchItemsAdded(batchItemList);
                    } catch (RemoteException ex) {
                        Logger.getLogger(BatchItemControllerImpl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }.start();
        }
        return res;
    }

    @Override
    public ArrayList<BatchItem> getAllBatchItemList() throws SQLException, ClassNotFoundException, RemoteException, FileNotFoundException, IOException {
        return batchItemDAO.getAll();
    }

    @Override
    public ArrayList<BatchItem> getBatchesByBarcode(String barcode) throws SQLException, ClassNotFoundException, RemoteException , FileNotFoundException, IOException{
        return batchItemDAO.batchesForBarcode(barcode);
    }

    @Override
    public String getBarcodeForItemCode(String itemCode) throws SQLException, ClassNotFoundException, RemoteException, FileNotFoundException, IOException {
        return batchItemDAO.barcodeForItemCode(itemCode);
    }

    @Override
    public ArrayList<BatchItem> getAvailableBatchesByBarcode(String barcode) throws SQLException, ClassNotFoundException, RemoteException, FileNotFoundException, IOException {
        return batchItemDAO.availableBatchesForBarcode(barcode);
    }

    @Override
    public ItemDetail getItemDetailForBatchN0(String batchNo) throws SQLException, ClassNotFoundException, RemoteException, FileNotFoundException, IOException {
        return batchItemDAO.batchesForBatchNo(batchNo);
    }

    @Override
    public ArrayList<ItemDetail> getItemDetailForExpDateRange(String beginDate, String endDate) throws SQLException, ClassNotFoundException, RemoteException , FileNotFoundException, IOException{
        return batchItemDAO.batchesByExpDate(beginDate, endDate);
    }

    @Override
    public ItemDetail getAvailableItemDetailForBatchN0(String batchNo) throws SQLException, ClassNotFoundException, RemoteException , FileNotFoundException, IOException{
        return batchItemDAO.availableBatchesForBatchNo(batchNo);
    }

    @Override
    public ArrayList<ItemDetail> getAvailableItemDetailForExpDateRange(String beginDate, String endDate) throws SQLException, ClassNotFoundException, RemoteException , FileNotFoundException, IOException{
        return batchItemDAO.availableBatchesByExpDate(beginDate, endDate);
    }

    @Override
    public Item getItemByItemCode(String itemCode) throws SQLException, ClassNotFoundException, RemoteException , FileNotFoundException, IOException{
        return batchItemDAO.itemForItemCode(itemCode);
    }

    @Override
    public void addBatchItemObserver(Observer observer) throws RemoteException {
        batchItemObservable.addObserver(observer);
    }

    @Override
    public boolean reserveBatchItem(String id) throws RemoteException {
        return batchItemReservation.reserve(id, this);
    }

    @Override
    public boolean releaseBatchItem(String id) throws RemoteException {
        return batchItemReservation.release(id, this);
    }
    public static boolean isReserved(String itemCode){
        return batchItemReservation.isReserved(itemCode);
    }
}
