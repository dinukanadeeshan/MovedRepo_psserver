/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redonz.pms.server.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Nadeeshan
 */
public interface DAO<T, E> {

    public boolean insert(T t) throws SQLException, ClassNotFoundException, FileNotFoundException, IOException;

    public T search(E e) throws SQLException, ClassNotFoundException, FileNotFoundException, IOException;

    public boolean update(T t) throws SQLException, ClassNotFoundException, FileNotFoundException, IOException;

    public boolean delete(E e) throws SQLException, ClassNotFoundException, FileNotFoundException, IOException;

    public ArrayList<T> getAll() throws SQLException, ClassNotFoundException, FileNotFoundException, IOException;

    public String lastId() throws SQLException, ClassNotFoundException, FileNotFoundException, IOException;

    public boolean addList(ArrayList<T> tList) throws SQLException, ClassNotFoundException, FileNotFoundException, IOException;
}
