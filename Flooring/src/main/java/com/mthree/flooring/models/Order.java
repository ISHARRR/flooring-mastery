package com.mthree.flooring.models;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Objects;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ishar
 */

public class Order {
    private int orderNumer;
    private LocalDate date;
    private String customer;    
    private Tax taxes;   
    private Product product;
//    private String productType;
    private BigDecimal area;
//    private BigDecimal costPerSquareFoot;
//    private BigDecimal laborCostPerSquareFoot;
    private BigDecimal materialCost;
    private BigDecimal laborCost;
    private BigDecimal tax;
    private BigDecimal total;


    public Order(int orderNumer, LocalDate date, String customer, Tax taxes, Product product, BigDecimal area) {
        this.orderNumer = orderNumer;
        this.date = date;
        this.customer = customer;
        this.taxes = taxes; 
        this.area = area;
        this.product = product; 
        this.materialCost = area.multiply(this.product.getCostPerSquareFoot()).setScale(2, RoundingMode.HALF_EVEN);
        this.laborCost = area.multiply(this.product.getLaborCostPerSquareFoot()).setScale(2, RoundingMode.HALF_EVEN);
        this.tax = (materialCost.add(laborCost).multiply(this.taxes.getTaxRate().divide(new BigDecimal(100))).setScale(2, RoundingMode.HALF_EVEN));
        this.total = materialCost.add(this.product.getLaborCostPerSquareFoot()).add(tax).setScale(2, RoundingMode.HALF_EVEN);
        
    }

    public Tax getTaxes() {
        return taxes;
    }

    public void setTaxes(Tax taxes) {
        this.taxes = taxes;
    }
    
    public int getOrderNumber() {
        return orderNumer;
    }

    public void setOrderNumer(int orderNumer) {
        this.orderNumer = orderNumer;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public BigDecimal getMaterialCost() {
        return materialCost;
    }

    public BigDecimal getLaborCost() {
        return laborCost;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "Order{" + "orderNumer=" + orderNumer + ", date=" + date + ", customer=" + customer + ", taxes=" + taxes + ", product=" + product + ", area=" + area + ", materialCost=" + materialCost + ", laborCost=" + laborCost + ", tax=" + tax + ", total=" + total + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + this.orderNumer;
        hash = 83 * hash + Objects.hashCode(this.date);
        hash = 83 * hash + Objects.hashCode(this.customer);
        hash = 83 * hash + Objects.hashCode(this.taxes);
        hash = 83 * hash + Objects.hashCode(this.product);
        hash = 83 * hash + Objects.hashCode(this.area);
        hash = 83 * hash + Objects.hashCode(this.materialCost);
        hash = 83 * hash + Objects.hashCode(this.laborCost);
        hash = 83 * hash + Objects.hashCode(this.tax);
        hash = 83 * hash + Objects.hashCode(this.total);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Order other = (Order) obj;
        if (this.orderNumer != other.orderNumer) {
            return false;
        }
        if (!Objects.equals(this.customer, other.customer)) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        if (!Objects.equals(this.taxes, other.taxes)) {
            return false;
        }
        if (!Objects.equals(this.product, other.product)) {
            return false;
        }
        if (!Objects.equals(this.area, other.area)) {
            return false;
        }
        if (!Objects.equals(this.materialCost, other.materialCost)) {
            return false;
        }
        if (!Objects.equals(this.laborCost, other.laborCost)) {
            return false;
        }
        if (!Objects.equals(this.tax, other.tax)) {
            return false;
        }
        if (!Objects.equals(this.total, other.total)) {
            return false;
        }
        return true;
    }



}