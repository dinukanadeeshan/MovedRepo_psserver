/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redonz.pms.server.observable;

import com.redonz.pms.common.model.ObserverTO;
import com.redonz.pms.common.observer.Observer;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DI_SH
 */
public abstract class Observable {

    private List<Observer> observers;

    public Observable() {
        observers = new ArrayList<>();
    }

    public void addObserver(Observer observer) throws RemoteException {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) throws RemoteException {
        for (Observer o : observers) {
            if (o.equals(observer)) {
                observers.remove(o);
                break;
            }
        }
    }

    protected void notifyObservers(ObserverTO observerTO) throws RemoteException {
        for (Observer observer : observers) {
            System.out.println(observer);
            if (!observer.isChanged()) {
                observer.update(observerTO);
            }
        }
    }
}
