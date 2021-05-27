/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.flooring.service;

import com.mthree.flooring.dao.OrderPersistenceException;
import com.mthree.flooring.models.Order;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author ishar
 */
public interface FloorService {

    List<Order> getAllOrder() throws 
            OrderPersistenceException;

    Order addOrder(Order order) throws
            OrderPersistenceException,
            OrderDataValidationException,
            OrderPersistenceException;

    Order removeOrder(int orderId) throws 
            OrderPersistenceException;

    List<Order> exportOrderData() throws 
            OrderPersistenceException;

    Order updateOrder(int orderId) throws
            OrderPersistenceException,
            OrderDataValidationException,
            OrderPersistenceException;

    List<Order> searchByDate(LocalDate date) throws 
            OrderPersistenceException;

}
