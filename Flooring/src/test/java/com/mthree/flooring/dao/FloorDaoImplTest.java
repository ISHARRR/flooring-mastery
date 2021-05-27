/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.flooring.dao;

import com.mthree.flooring.models.Order;
import com.mthree.flooring.models.Product;
import com.mthree.flooring.models.Tax;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author ishar
 */
public class FloorDaoImplTest {

    FloorDao testDao;

    public FloorDaoImplTest() throws IOException {

    }

    @BeforeEach
    public void setUp() throws Exception {
        String testFile = "test.txt";
        String testFile1 = "testBackup.txt";
        // Use the FileWriter to quickly blank the file
        new FileWriter(testFile);
        new FileWriter(testFile1);
        this.testDao = new FloorDaoImpl(testFile, testFile1);

    }


    @Test
    public void testAddAndGetOrderByDate() throws OrderPersistenceException {
        //arrange
        Tax tax = new Tax("CA", "Cali", new BigDecimal(23.33).setScale(2, RoundingMode.HALF_EVEN));
        Product product = new Product("Wood", new BigDecimal(20.32).setScale(2, RoundingMode.HALF_EVEN), new BigDecimal(34.32).setScale(2, RoundingMode.HALF_EVEN));
        Order order = new Order(1, LocalDate.now(), "ishar", tax, product, new BigDecimal(234).setScale(2, RoundingMode.HALF_EVEN));
        //action
        Order orderAdded = testDao.addOrder(order);
        List gotOrder = testDao.searchByDate(LocalDate.now());
        //assert
        Assertions.assertEquals(order, gotOrder.get(0));

    }

    @Test
    public void testRemoveOrder() throws OrderPersistenceException {
        //arrange
        Tax tax = new Tax("CA", "Cali", new BigDecimal(23.33).setScale(2, RoundingMode.HALF_EVEN));
        Product product = new Product("Wood", new BigDecimal(20.32).setScale(2, RoundingMode.HALF_EVEN), new BigDecimal(34.32).setScale(2, RoundingMode.HALF_EVEN));
        Order order = new Order(1, LocalDate.now(), "ishar", tax, product, new BigDecimal(234).setScale(2, RoundingMode.HALF_EVEN));
        //action
        Order orderAdded = testDao.addOrder(order);
        Order orderRemove = testDao.removeOrder(1);
        //assert
        Assertions.assertNull(orderAdded);

    }
    
    @Test
    public void testUpdateOrder() throws OrderPersistenceException {
        //arrange
        Tax tax = new Tax("CA", "Cali", new BigDecimal(23.33).setScale(2, RoundingMode.HALF_EVEN));
        Product product = new Product("Wood", new BigDecimal(20.32).setScale(2, RoundingMode.HALF_EVEN), new BigDecimal(34.32).setScale(2, RoundingMode.HALF_EVEN));
        Order order = new Order(1, LocalDate.now(), "ishar", tax, product, new BigDecimal(234).setScale(2, RoundingMode.HALF_EVEN));
        //action
        Order orderUpdated = testDao.updateOrder(1);
        //assert
        Assertions.assertNotEquals(order, orderUpdated);

    }
    
    @Test
    public void testSearchOrderByDate() throws OrderPersistenceException {
        //arrange
        Tax tax = new Tax("CA", "Cali", new BigDecimal(23.33).setScale(2, RoundingMode.HALF_EVEN));
        Product product = new Product("Wood", new BigDecimal(20.32).setScale(2, RoundingMode.HALF_EVEN), new BigDecimal(34.32).setScale(2, RoundingMode.HALF_EVEN));
        Order order = new Order(1, LocalDate.now(), "ishar", tax, product, new BigDecimal(234).setScale(2, RoundingMode.HALF_EVEN));
        //action
        testDao.addOrder(order);
        List orderToday = testDao.searchByDate(LocalDate.now());
        //assert
        Assertions.assertTrue(orderToday.contains(order));

    }
}
