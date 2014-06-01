/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.redonz.pms.server.others;

/**
 *
 * @author cmjd
 */
public class IDGen {
    public static String getNextId(String id){
        int index = 0;
        for (int i = 0; i < id.length(); i++) {
            if (Character.isDigit(id.charAt(i))) {
                index = i;
                break;
            }
        }
        String prefix;
        int num;
 
        prefix = id.substring(0, index);
        num = Integer.parseInt(id.substring(index+1));
        num++;
        if (num < 10) {
            id = prefix + "000" + num;
        } else if (num < 100) {
            id = prefix + "00" + num;
        } else if (num < 1000) {
            id = prefix + "0" + num;
        } else if (num < 10000) {
            id = prefix + num;
        }
        return id;
    }
}
