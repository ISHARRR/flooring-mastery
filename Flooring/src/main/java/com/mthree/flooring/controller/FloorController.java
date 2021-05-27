package com.mthree.flooring.controller;

import com.mthree.flooring.dao.OrderPersistenceException;
import com.mthree.flooring.models.Order;
import com.mthree.flooring.service.FloorService;
import com.mthree.flooring.service.OrderDataValidationException;
import com.mthree.flooring.view.FloorView;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ishar
 */
@Component
public class FloorController {

    private FloorView view;
    private FloorService service;

    @Autowired
    public FloorController(FloorView view, FloorService service) {
        this.view = view;
        this.service = service;

    }

    public void run() throws FileNotFoundException, OrderPersistenceException, OrderDataValidationException {

        System.out.println(view.idArrayList);
        boolean flag = true;

        while (flag) {
            int menuSelection = view.printMenuAndGetSelection(1, 7);
            switch (menuSelection) {
                case 1 -> getAll();
                case 2 -> createOrder();
                case 3 -> updateOrder();
                case 4 -> removeOrder();
                case 5 -> getOrderByDate();
                case 6 -> exportOrderData();
                case 7 -> {
                    System.out.println("exit");
                    flag = false;
                }
                default -> System.out.println("error");
            }
//                    updateItem();
//                    purchaseItem();
            
        }
    }

    public void createOrder() throws FileNotFoundException, OrderPersistenceException, OrderDataValidationException {
        view.createNewOrderBanner();
        Order orderToAdd = view.createNewOrder();
        service.addOrder(orderToAdd);
        view.taskCompletedBanner();

    }
    
    public void removeOrder() throws FileNotFoundException, OrderPersistenceException {
        view.removeOrderBanner();
        int orderToRemove = view.orderId();
        service.removeOrder(orderToRemove);
        view.taskCompletedBanner();

    }
    
    public void getAll() throws FileNotFoundException, OrderPersistenceException {
        List orders = service.getAllOrder();
        orders.forEach(System.out::println);

    }
    
    public void updateOrder() throws FileNotFoundException, OrderPersistenceException, OrderDataValidationException {
        view.updateOrderBanner();
        int id = view.orderId();
        Order orderToUpdate = view.updateOrder(id);
        service.addOrder(orderToUpdate);
        view.taskCompletedBanner();

    }
    
    
    public void getOrderByDate() throws FileNotFoundException, OrderPersistenceException {
        view.getOrderbyDate();
        LocalDate date = view.orderDate();
        List ordersByDate = service.searchByDate(date);
        ordersByDate.forEach(System.out::println);
        view.taskCompletedBanner();

    }
    
    public void exportOrderData() throws FileNotFoundException, OrderPersistenceException {
        view.exportOrderDataBanner();
        service.exportOrderData();
        view.taskCompletedBanner();

    }
    
}
