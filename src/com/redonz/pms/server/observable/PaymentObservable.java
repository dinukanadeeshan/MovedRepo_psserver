/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redonz.pms.server.observable;

import com.redonz.pms.common.model.ObserverTO;
import com.redonz.pms.common.model.Payment;
import java.rmi.RemoteException;

/**
 *
 * @author DI_SH
 */
public class PaymentObservable extends Observable {
    
    public void paymentDone(Payment payment)throws RemoteException{
        notifyObservers(new ObserverTO(ObserverTO.ADD, payment));
    }
    
}
