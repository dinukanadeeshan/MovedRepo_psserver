/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redonz.pms.server.serverutilities;

import com.redonz.pms.server.factoryimpl.RemoteFactoryImpl;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nadeeshan
 */
public class ServerStart {

    public static void main(String[] args) {
        try {
            System.setProperty("sun.rmi.transport.tcp.responseTimeout", 2*60*60*1000+"");
            Registry pharmacyServerRegistry = LocateRegistry.createRegistry(1055);
            pharmacyServerRegistry.rebind("PharmacyServerRemoteFactory", new RemoteFactoryImpl());
            System.out.println("Server Started....");
        } catch (RemoteException ex) {
            Logger.getLogger(ServerStart.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
