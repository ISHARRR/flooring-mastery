/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.flooring.service;

/**
 *
 * @author ishar
 */
public class OrderDuplicateIdException extends Exception{
    
    public OrderDuplicateIdException(String message) {
        super(message);
    }

    public OrderDuplicateIdException(String message,
            Throwable cause) {
        super(message, cause);
    }
    
}
