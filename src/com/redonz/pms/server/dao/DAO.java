/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redonz.pms.server.dao;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Nadeeshan
 */
public interface DAO<T, E> {

    public boolean insert(T t) throws SQLException, ClassNotFoundException;

    public T search(E e) throws SQLException, ClassNotFoundException;

    public boolean update(T t) throws SQLException, ClassNotFoundException;

    public boolean delete(E e) throws SQLException, ClassNotFoundException;

    public ArrayList<T> getAll() throws SQLException, ClassNotFoundException;

    public String lastId() throws SQLException, ClassNotFoundException;

    public boolean addList(ArrayList<T> tList) throws SQLException, ClassNotFoundException;
}
