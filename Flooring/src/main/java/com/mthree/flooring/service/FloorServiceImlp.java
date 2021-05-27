/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.flooring.service;

import com.mthree.flooring.dao.FloorDao;
import com.mthree.flooring.dao.OrderPersistenceException;
import com.mthree.flooring.models.Order;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author ishar
 */
@Component
public class FloorServiceImlp implements FloorService {

    private final FloorDao dao;

    @Autowired
    public FloorServiceImlp(FloorDao dao) {
        this.dao = dao;
    }

    @Override
    public List<Order> getAllOrder() throws OrderPersistenceException {

        return dao.getAllOrder();
    }

    @Override
    public Order addOrder(Order order) throws OrderPersistenceException, OrderDataValidationException, OrderPersistenceException {
        validateOrderData(order);
        return dao.addOrder(order);
    }

    @Override
    public Order removeOrder(int orderId) throws OrderPersistenceException {
        return dao.removeOrder(orderId);
    }

    @Override
    public List<Order> exportOrderData() throws OrderPersistenceException {
        return dao.exportOrderData();
    }

    @Override
    public Order updateOrder(int orderId) throws OrderPersistenceException, OrderDataValidationException, OrderPersistenceException {
        Order order = dao.updateOrder(orderId);
        validateOrderUpdateData(order);
        return order;
    }

    @Override
    public List<Order> searchByDate(LocalDate date) throws OrderPersistenceException {
        return dao.searchByDate(date);
    }

    private void validateOrderData(Order order) throws
            OrderDataValidationException {

        try {
            if (order.getOrderNumber() == 0
                    || order.getDate() == null
                    || order.getCustomer() == null
                    || order.getTaxes().getStateName() == null
                    || order.getTaxes().getStateName().matches(".*\\d.*")
                    || order.getProduct().getProductType() == null
                    || order.getProduct().getProductType().matches(".*\\d.*")
                    || order.getArea() == null) {

                throw new OrderDataValidationException(
                        "ERROR: All fields are required.");

            }
        } catch (OrderDataValidationException e) {
            System.out.println("Invalid entry - Try again");
        }
    }

    private void validateOrderUpdateData(Order order) throws
            OrderDataValidationException {

        try {
            if (order.getCustomer() == null
                    || order.getTaxes().getStateName() == null
                    || order.getTaxes().getStateName().matches(".*\\d.*")
                    || order.getProduct().getProductType() == null
                    || order.getProduct().getProductType().matches(".*\\d.*")
                    || order.getArea() == null) {

                throw new OrderDataValidationException(
                        "ERROR: All fields are required.");
            }

        } catch (OrderDataValidationException e) {
            System.out.println("Invalid entry - Try again");
        }
    }

}
