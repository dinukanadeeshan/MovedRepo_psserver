/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redonz.pms.server.dao;

import com.redonz.pms.common.model.CustomerOrderDetail;
import com.redonz.pms.common.model.Item;
import com.redonz.pms.common.model.ItemDetail;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Nadeeshan
 */
public interface ItemDAO extends DAO<Item, String> {

    public ArrayList<Item> searchLike(String barcode) throws ClassNotFoundException, SQLException, FileNotFoundException, IOException;

    public ItemDetail getAbstractItemDetail(String barcode) throws ClassNotFoundException, SQLException, FileNotFoundException, IOException;

    public ArrayList<Item> itemsByDescription(String description) throws ClassNotFoundException, SQLException, FileNotFoundException, IOException;

    public ArrayList<Item> itemsByCategory(String categoryId) throws ClassNotFoundException, SQLException, FileNotFoundException, IOException;

   
}
