/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redonz.pms.server.factoryimpl;

import com.redonz.pms.common.controller.BatchItemController;
import com.redonz.pms.common.controller.CategoryController;
import com.redonz.pms.common.controller.CustomerController;
import com.redonz.pms.common.controller.CustomerOderDetailController;
import com.redonz.pms.common.controller.CustomerOrderController;
import com.redonz.pms.common.controller.DateController;
import com.redonz.pms.common.controller.HoldOrderController;
import com.redonz.pms.common.controller.ItemController;
import com.redonz.pms.common.controller.PaymentController;
import com.redonz.pms.common.controller.UserController;
import com.redonz.pms.common.factory.RemoteFactory;
import com.redonz.pms.server.controller.impl.BatchItemControllerImpl;
import com.redonz.pms.server.controller.impl.CategoryControllerImpl;
import com.redonz.pms.server.controller.impl.CustomerControllerImpl;
import com.redonz.pms.server.controller.impl.CustomerOrderControllerImpl;
import com.redonz.pms.server.controller.impl.CustomerOrderDetailControllerImpl;
import com.redonz.pms.server.controller.impl.DateControllerImpl;
import com.redonz.pms.server.controller.impl.HoldOrderControllerImpl;
import com.redonz.pms.server.controller.impl.ItemControllerImpl;
import com.redonz.pms.server.controller.impl.PaymentControllerImpl;
import com.redonz.pms.server.controller.impl.UserControllerImpl;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Nadeeshan
 */
public class RemoteFactoryImpl extends UnicastRemoteObject implements RemoteFactory {

    public RemoteFactoryImpl() throws RemoteException {
    }

    @Override
    public DateController getDateController() throws RemoteException {
        return new DateControllerImpl();
    }

    @Override
    public PaymentController getPaymentController() throws RemoteException {
        return new PaymentControllerImpl();
    }

    @Override
    public HoldOrderController getHoldOrderController() throws RemoteException {
      
        return new HoldOrderControllerImpl();
    }

    @Override
    public BatchItemController getBatchItemController() throws RemoteException {
        return new BatchItemControllerImpl();
    }

    @Override
    public ItemController getItemController() throws RemoteException {
        return new ItemControllerImpl();
    }

    @Override
    public CustomerController getCustomerController() throws RemoteException {
        return new CustomerControllerImpl();
    }

    @Override
    public CustomerOrderController getCustomerOrderController() throws RemoteException {
       
        return new CustomerOrderControllerImpl();
    }

    @Override
    public CategoryController getCategoryController() throws RemoteException {
        return new CategoryControllerImpl();
    }

    @Override
    public CustomerOderDetailController getCustomerOderDetailController() throws RemoteException {
        return new CustomerOrderDetailControllerImpl();
    }

    @Override
    public UserController getUserController() throws RemoteException {
        return new UserControllerImpl();
    }
}
