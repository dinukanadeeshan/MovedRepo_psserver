/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redonz.pms.server.controller.impl;

import com.redonz.pms.common.controller.ItemController;
import com.redonz.pms.common.model.Item;
import com.redonz.pms.common.model.ItemDetail;
import com.redonz.pms.common.observer.Observer;
import com.redonz.pms.server.reservation.Reservation;
import com.redonz.pms.server.dao.ItemDAO;
import com.redonz.pms.server.dao.impl.ItemDAOImpl;
import com.redonz.pms.server.observable.ItemObservable;
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
public class ItemControllerImpl extends UnicastRemoteObject implements ItemController {

    private ItemDAO itemDAO = new ItemDAOImpl();
    private static ItemObservable itemObservable = new ItemObservable();
    private static Reservation<ItemController> itemReservation = new Reservation<>();

    public ItemControllerImpl() throws RemoteException {
    }

    @Override
    public boolean saveItem(final Item item) throws RemoteException, SQLException, ClassNotFoundException, FileNotFoundException, IOException {
        boolean res = itemDAO.insert(item);
        if (res) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        itemObservable.itemAdded(item);
                    } catch (RemoteException ex) {
                        Logger.getLogger(ItemControllerImpl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }.start();
        }
        return res;
    }

    @Override
    public Item searchItem(String barcode) throws RemoteException, SQLException, ClassNotFoundException , FileNotFoundException, IOException{
        return itemDAO.search(barcode);
    }

    @Override
    public boolean updateItem(Item item) throws RemoteException, SQLException, ClassNotFoundException, FileNotFoundException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteItem(String barcode) throws RemoteException, SQLException, ClassNotFoundException, FileNotFoundException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ItemDetail getAbstractItemDetail(String barcode) throws RemoteException, SQLException, ClassNotFoundException , FileNotFoundException, IOException{
        return itemDAO.getAbstractItemDetail(barcode);
    }

    @Override
    public ArrayList<Item> getItemsByDescription(String desc) throws RemoteException, SQLException, ClassNotFoundException, FileNotFoundException, IOException {
        return itemDAO.itemsByDescription(desc);
    }

    @Override
    public ArrayList<Item> searchItemLike(String barcode) throws RemoteException, SQLException, ClassNotFoundException, FileNotFoundException, IOException {
        return itemDAO.searchLike(barcode);
    }

    @Override
    public ArrayList<Item> getItemsByCategory(String categoryId) throws RemoteException, SQLException, ClassNotFoundException, FileNotFoundException, IOException {
        return itemDAO.itemsByCategory(categoryId);
    }

    @Override
    public void addItemObserver(Observer observer) throws RemoteException {
        itemObservable.addObserver(observer);
    }

    @Override
    public boolean reserveItem(String barcode) throws RemoteException {
        return itemReservation.reserve(barcode, this);
    }

    @Override
    public boolean releaseItem(String barcode) throws RemoteException {
        return itemReservation.release(barcode, this);
    }
}
