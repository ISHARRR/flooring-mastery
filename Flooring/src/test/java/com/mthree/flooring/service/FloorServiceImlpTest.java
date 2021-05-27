/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.flooring.service;

import com.mthree.flooring.dao.FloorDao;
import com.mthree.flooring.dao.OrderPersistenceException;
import com.mthree.flooring.models.Order;
import com.mthree.flooring.models.Product;
import com.mthree.flooring.models.Tax;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author ishar
 */
public class FloorServiceImlpTest {

    FloorService service;

    public FloorServiceImlpTest() {
        FloorDao dao = new FloorDaoStubImpl();
        service = new FloorServiceImlp(dao);
    }

//    @BeforeAll
//    public static void setUpClass() {
//    }
//
//    @AfterAll
//    public static void tearDownClass() {
//    }
//
    @BeforeEach
    public void setUp() throws OrderPersistenceException, OrderDataValidationException {
        Tax tax = new Tax("CA", "Cali", new BigDecimal(23.33).setScale(2, RoundingMode.HALF_EVEN));
        Product product = new Product("Wood", new BigDecimal(20.32).setScale(2, RoundingMode.HALF_EVEN), new BigDecimal(34.32).setScale(2, RoundingMode.HALF_EVEN));
        Order order = new Order(1, LocalDate.now(), "ishar", tax, product, new BigDecimal(234).setScale(2, RoundingMode.HALF_EVEN));
        Order orderAdded = service.addOrder(order);
    }

    @Test
    public void testGetAllOrders() throws Exception {
        // ARRANGE
        Tax tax = new Tax("CA", "Cali", new BigDecimal(23.33).setScale(2, RoundingMode.HALF_EVEN));
        Product product = new Product("Wood", new BigDecimal(20.32).setScale(2, RoundingMode.HALF_EVEN), new BigDecimal(34.32).setScale(2, RoundingMode.HALF_EVEN));
        Order order = new Order(2, LocalDate.now(), "ishar", tax, product, new BigDecimal(234).setScale(2, RoundingMode.HALF_EVEN));
        // ACT & ASSERT
        assertEquals(1, service.getAllOrder().size(),
                "Should only have one item.");

    }

    @Test
    public void testAddvalidOrdder() throws Exception {
        //Arrage
        Tax tax = new Tax("CA", "Cali", new BigDecimal(23.33).setScale(2, RoundingMode.HALF_EVEN));
        Product product = new Product("Wood", new BigDecimal(20.32).setScale(2, RoundingMode.HALF_EVEN), new BigDecimal(34.32).setScale(2, RoundingMode.HALF_EVEN));
        Order order = new Order(2, LocalDate.now(), "ishar", tax, product, new BigDecimal(234).setScale(2, RoundingMode.HALF_EVEN));
        //Act
        try {
            service.addOrder(order);
        } catch (OrderDataValidationException
                | OrderPersistenceException e) {
            // ASSERT
            fail("Student was valid. No exception should have been thrown.");
        }
    }

    @Test
    public void testRemoveOrder() throws Exception {
        // ARRANGE
            LocalDate date = LocalDate.now();
        Tax taxes = new Tax("CA", "Cali", new BigDecimal(10).setScale(2, RoundingMode.HALF_EVEN));
        Product products = new Product("Tile", new BigDecimal(9).setScale(2, RoundingMode.HALF_EVEN),
                new BigDecimal(10).setScale(2, RoundingMode.HALF_EVEN));
        Order order = new Order(1, date, "Bob", taxes, products,
                new BigDecimal(11).setScale(2, RoundingMode.HALF_EVEN));

        // ACT & ASSERT
        Order shouldBeAda = service.removeOrder(1);
        assertNotNull(shouldBeAda);
        assertEquals(order, shouldBeAda);
    }
    
        public void testUdateOrder() throws Exception {
        // ARRANGE
            LocalDate date = LocalDate.now();
        Tax taxes = new Tax("CA", "Cali", new BigDecimal(10).setScale(2, RoundingMode.HALF_EVEN));
        Product products = new Product("Tile", new BigDecimal(9).setScale(2, RoundingMode.HALF_EVEN),
                new BigDecimal(10).setScale(2, RoundingMode.HALF_EVEN));
        Order order = new Order(1, date, "James", taxes, products,
                new BigDecimal(11).setScale(2, RoundingMode.HALF_EVEN));

        Tax taxesOrginal = new Tax("CA", "Cali", new BigDecimal(10).setScale(2, RoundingMode.HALF_EVEN));
        Product productsOrginal = new Product("Tile", new BigDecimal(9).setScale(2, RoundingMode.HALF_EVEN),
                new BigDecimal(10).setScale(2, RoundingMode.HALF_EVEN));
        Order orderOrginal = new Order(1, date, "Ishar", taxes, products,
                new BigDecimal(11).setScale(2, RoundingMode.HALF_EVEN));

        // ACT & ASSERT
        Order shouldBe = service.updateOrder(1);
        Order orginal = orderOrginal;
        
        assertEquals(shouldBe,orginal);

    }

}
