/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.flooring;

import com.mthree.flooring.controller.FloorController;
import com.mthree.flooring.dao.OrderPersistenceException;
import com.mthree.flooring.service.OrderDataValidationException;
import java.io.FileNotFoundException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 *
 * @author ishar
 */
public class App {

    public static void main(String[] args) throws FileNotFoundException, OrderPersistenceException, OrderDataValidationException {
        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext();
        appContext.scan("com.mthree.flooring");
        appContext.refresh();

        FloorController controller = appContext.getBean("floorController", FloorController.class);
        controller.run();
    }
}
