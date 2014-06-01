/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redonz.pms.server.model;

/**
 *
 * @author cmjd
 */
public class ID implements Comparable<ID>{

    private String id;

    private boolean inUsed = false;

    public ID() {
    }

    public ID(String id) {
        this.id = id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return id;
    }

    public boolean use() {
        if (!inUsed) {
            inUsed = true;
            return true;
        }
        return false;
    }

    public boolean release() {
        if (inUsed) {
            inUsed = false;
            return true;
        }
        return false;

    }

    public boolean isInUsed() {
        return inUsed;
    }

    
    @Override
    public int compareTo(ID o) {
       return id.compareTo(o.getId());
    }

}
