/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redonz.pms.server.pool.id;

import com.redonz.pms.server.model.ID;
import java.util.Collections;

/**
 *
 * @author cmjd
 */
public class CustomerIdPool extends AbstractIdPool {

    @Override
    public ID addId() {
        Collections.sort(idList);
        ID newID = null;
        if (!idList.isEmpty()) {
            ID id = idList.get(idList.size() - 1);
            newID = new ID(increaseId(id.getId()));
            idList.add(newID);
            newID.use();
//            System.out.println("-----Added() " + newID);
            return newID;
        }
        return null;


    }

    @Override
    public String addId(String id) {
//        System.out.println("-----Added(id) " + id);
        ID iD = new ID(id);
        idList.add(iD);
        iD.use();
        return id;
    }
}
