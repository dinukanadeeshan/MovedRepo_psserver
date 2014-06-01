/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redonz.pms.server.reservation;

import java.util.Hashtable;
import java.util.Map;

/**
 *
 * @author DI_SH
 */
public class Reservation<T> {

    private Map<String, T> reserveData = new Hashtable<>();

    public boolean reserve(String id, T controller) {
        if (reserveData.containsKey(id)) {
            if (reserveData.get(id) == controller) {
                return true;
            }
            return false;
        }
        reserveData.put(id, controller);
        return true;
    }

    public boolean release(String id, T controller) {
        if (reserveData.get(id) == controller) {
            reserveData.remove(id);
            return true;
        }
        return false;
    }

    public boolean isReserved(String itemCode) {
        return reserveData.containsKey(itemCode);
    }
}
