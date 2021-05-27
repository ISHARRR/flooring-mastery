/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.flooring.dao;

import java.util.List;
import com.mthree.flooring.models.Order;
import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author ishar
 */
public interface FloorDao {
    
    List<Order> getAllOrder() throws OrderPersistenceException ;
    
    Order addOrder(Order order)throws OrderPersistenceException;

    Order removeOrder(int orderId)throws OrderPersistenceException;
    
    List<Order> exportOrderData() throws OrderPersistenceException;
    
    Order updateOrder(int orderId)throws OrderPersistenceException;
    
    List<Order> searchByDate(LocalDate date)throws OrderPersistenceException;

}
