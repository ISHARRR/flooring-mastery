/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.flooring.service;

import com.mthree.flooring.dao.OrderPersistenceException;
import com.mthree.flooring.dao.FloorDao;
import com.mthree.flooring.models.Order;
import com.mthree.flooring.models.Product;
import com.mthree.flooring.models.Tax;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ishar
 */
public class FloorDaoStubImpl implements FloorDao {

    public Order singleOrder;

    public FloorDaoStubImpl() {
        LocalDate date = LocalDate.now();
        Tax taxes = new Tax("CA", "Cali", new BigDecimal(10).setScale(2, RoundingMode.HALF_EVEN));
        Product products = new Product("Tile", new BigDecimal(9).setScale(2, RoundingMode.HALF_EVEN),
                new BigDecimal(10).setScale(2, RoundingMode.HALF_EVEN));
        singleOrder = new Order(1, date, "Bob", taxes, products,
                new BigDecimal(11).setScale(2, RoundingMode.HALF_EVEN));
    }

    public FloorDaoStubImpl(Order testOrder) {
        this.singleOrder = testOrder;
    }

    @Override
    public List<Order> getAllOrder() throws OrderPersistenceException {
        List<Order> orderList = new ArrayList<>();
        orderList.add(singleOrder);
        return orderList;
    }

    @Override
    public Order addOrder(Order order) throws OrderPersistenceException {
        if (order.equals(singleOrder.getOrderNumber())) {
            return singleOrder;
        } else {
            return null;
        }
    }

    @Override
    public Order removeOrder(int orderId) throws OrderPersistenceException {
        if (orderId == singleOrder.getOrderNumber()) {
            return singleOrder;
        } else {
            return null;
        }
    }

    @Override
    public List<Order> exportOrderData() throws OrderPersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
    @Override
    public Order updateOrder(int orderId) throws OrderPersistenceException {
        if (orderId == singleOrder.getOrderNumber()) {
            return singleOrder;
        } else {
            return null;
        }
    }

    @Override
    public List<Order> searchByDate(LocalDate date) throws OrderPersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
