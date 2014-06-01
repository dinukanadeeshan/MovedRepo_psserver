/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redonz.pms.server.observable;

import com.redonz.pms.common.model.BatchItem;
import com.redonz.pms.common.model.ObserverTO;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author DI_SH
 */
public class BatchItemObservable extends Observable {

    public void batchItemAdded(BatchItem batchItem) throws RemoteException {
        notifyObservers(new ObserverTO(ObserverTO.ADD, batchItem));
    }

    public void batchItemsAdded(ArrayList<BatchItem> batchItems) throws RemoteException {
        notifyObservers(new ObserverTO(ObserverTO.ADD, batchItems));
    }
}
