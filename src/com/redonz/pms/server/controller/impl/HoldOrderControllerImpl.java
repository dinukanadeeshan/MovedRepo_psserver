/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redonz.pms.server.controller.impl;

import com.redonz.pms.common.controller.HoldOrderController;
import com.redonz.pms.common.model.HoldOrder;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 *
 * @author Nadeeshan
 */
public class HoldOrderControllerImpl extends UnicastRemoteObject implements HoldOrderController {

    
    private static ArrayList<HoldOrder> holdOrderList= new ArrayList<>();

    public HoldOrderControllerImpl()throws RemoteException{
        System.out.println("Constructor....");
    }
    @Override
    public boolean addHoldOrder(HoldOrder holdOrder) throws RemoteException {
        return holdOrderList.add(holdOrder);
    }

    @Override
    public boolean removeHoldOrder(HoldOrder holdOrder) throws RemoteException {
        return holdOrderList.remove(holdOrder);
    }

    @Override
    public boolean removeHoldOrder(int holdOrderId) throws RemoteException {
        for (int i = 0; i < holdOrderList.size(); i++) {
            HoldOrder holdOrder = holdOrderList.get(i);
            if (holdOrder.getHoldOrderId() == holdOrderId) {
                holdOrderList.remove(i);
                return true;
            }

        }
        return false;
    }

    @Override
    public HoldOrder getHoldOrder(int holdOrderId) throws RemoteException {
        for (int i = 0; i < holdOrderList.size(); i++) {
            HoldOrder holdOrder = holdOrderList.get(i);
            if (holdOrder.getHoldOrderId() == holdOrderId) {
                return holdOrder;
            }

        }
        return null;
    }

    @Override
    public int getHoldOrderCount() throws RemoteException {
        return holdOrderList.size();
    }

    @Override
    public ArrayList<HoldOrder> getAllOrders() throws RemoteException {
        System.out.println(holdOrderList);
        return holdOrderList;
    }

    @Override
    public boolean removeAllOrders() throws RemoteException {
        return holdOrderList.removeAll(holdOrderList);
    }

    @Override
    public int getLastHoldOrderId() throws RemoteException {
        if (holdOrderList.isEmpty()) {
            return 0;
        }
        return holdOrderList.get(holdOrderList.size()-1).getHoldOrderId();
    }

    @Override
    public ArrayList<HoldOrder> getHoldOrderByCustId(String custId) throws RemoteException {
        ArrayList<HoldOrder> hol = new ArrayList<>();
        for (HoldOrder holdOrder : holdOrderList) {
            if (holdOrder.getCustId().equals(custId)) {
                hol.add(holdOrder);
            }
        }
        return hol;
    }
    
    
}
