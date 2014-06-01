/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redonz.pms.server.pool.id;

import com.redonz.pms.server.model.ID;
import com.redonz.pms.server.others.IDGen;
import java.util.List;
import java.util.Vector;


/**
 *
 * @author cmjd
 */
public abstract class AbstractIdPool {

    protected final List<ID> idList;

    public AbstractIdPool() {
        idList = new Vector<>();
    }

    public abstract ID addId();

    public abstract String addId(String id);

    protected String increaseId(String id) {
        return IDGen.getNextId(id);
    }

    public ID getId() {
        for (ID id : idList) {
//            System.out.println("------getID : id : "+id);
            if (id.use()) {
//                System.out.println("-----This is use");
                return id;
            }
        }
//        System.out.println("-----retruned....");
        return addId();

    }
    public List getAll(){
        return idList;
    }

//    public boolean remove(String id) {
//        for (ID id1 : idList) {
//            if (id1.getId().equals(id) && id1.release()) {
//                idList.remove(id1);
//                return true;
//            }
//        }
//        return false;
//    }

    public boolean releaseID(String id) {
        for (ID id1 : idList) {
            if (id1.getId().equals(id)) {
                return id1.release();
            }
        }
        return false;
    }
}
