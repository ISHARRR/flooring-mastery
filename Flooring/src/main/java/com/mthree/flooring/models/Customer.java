//package com.mthree.flooring.models;
//
//
//import java.math.BigDecimal;
//
///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//
///**
// *
// * @author ishar
// */
//public class Customer {
//    private String customerName;
//    private String state;
//    private BigDecimal taxRate;
//
//    public Customer(String customerName, String state, BigDecimal taxRate) {
//        this.customerName = customerName;
//        this.state = state;
//        this.taxRate = taxRate;
//    }
//
//    public String getCustomerName() {
//        return customerName;
//    }
//
//    public void setCustomerName(String customerName) {
//        this.customerName = customerName;
//    }
//
//    public String getState() {
//        return state;
//    }
//
//    public void setState(String state) {
//        this.state = state;
//    }
//
//    public BigDecimal getTaxRate() {
//        return taxRate;
//    }
//
//    public void setTaxRate(BigDecimal taxRate) {
//        this.taxRate = taxRate;
//    }
//
//    @Override
//    public String toString() {
//        return "Customer{" + "customerName=" + customerName + ", state=" + state + ", taxRate=" + taxRate + '}';
//    }
//    
//    //------------------ testing
//
//    @Override
//    public int hashCode() {
//        int hash = 7;
//        hash = 67 * hash + (this.customerName != null ? this.customerName.hashCode() : 0);
//        hash = 67 * hash + (this.state != null ? this.state.hashCode() : 0);
//        hash = 67 * hash + (this.taxRate != null ? this.taxRate.hashCode() : 0);
//        return hash;
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj) {
//            return true;
//        }
//        if (obj == null) {
//            return false;
//        }
//        if (getClass() != obj.getClass()) {
//            return false;
//        }
//        final Customer other = (Customer) obj;
//        if ((this.customerName == null) ? (other.customerName != null) : !this.customerName.equals(other.customerName)) {
//            return false;
//        }
//        if ((this.state == null) ? (other.state != null) : !this.state.equals(other.state)) {
//            return false;
//        }
//        if (this.taxRate != other.taxRate && (this.taxRate == null || !this.taxRate.equals(other.taxRate))) {
//            return false;
//        }
//        return true;
//    }
//    
//    
//}
