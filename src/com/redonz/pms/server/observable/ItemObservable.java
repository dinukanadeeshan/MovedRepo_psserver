/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redonz.pms.server.observable;

import com.redonz.pms.common.model.Item;
import com.redonz.pms.common.model.ObserverTO;
import java.rmi.RemoteException;

/**
 *
 * @author DI_SH
 */
public class ItemObservable extends Observable{
//    private List<Observer> observers;
//    public ItemObservable(){
//        observers = new ArrayList<>();
//    }
//    
//    public void addObserver(Observer observer){
//        observers.add(observer);
//    }
//    public void removeObserver(Observer observer)throws RemoteException{
//        for (Observer o : observers) {
//            if (o.equals(observer)) {
//                observers.remove(o);
//                break;
//            }
//        }
//    }
//    
    public void itemAdded(Item item)throws RemoteException{
        notifyObservers(new ObserverTO(ObserverTO.ADD, item));
    }
    
//    protected  void notifyObservers(ObserverTO observerTO)throws RemoteException{
//        for (Observer observer : observers) {
//            if (!observer.isChanged()) {
//                observer.update(observerTO);
//            }
//        }
//    }
}
