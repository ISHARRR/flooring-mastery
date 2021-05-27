/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.flooring.models;

import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author ishar
 */
public class Product {
    private String ProductType;
    private BigDecimal CostPerSquareFoot;
    private BigDecimal LaborCostPerSquareFoot;

    public Product(String ProductType, BigDecimal CostPerSquareFoot, BigDecimal LaborCostPerSquareFoot) {
        this.ProductType = ProductType;
        this.CostPerSquareFoot = CostPerSquareFoot;
        this.LaborCostPerSquareFoot = LaborCostPerSquareFoot;
    }

    public String getProductType() {
        return ProductType;
    }

    public void setProductType(String ProductType) {
        this.ProductType = ProductType;
    }

    public BigDecimal getCostPerSquareFoot() {
        return CostPerSquareFoot;
    }

    public void setCostPerSquareFoot(BigDecimal CostPerSquareFoot) {
        this.CostPerSquareFoot = CostPerSquareFoot;
    }

    public BigDecimal getLaborCostPerSquareFoot() {
        return LaborCostPerSquareFoot;
    }

    public void setLaborCostPerSquareFoot(BigDecimal LaborCostPerSquareFoot) {
        this.LaborCostPerSquareFoot = LaborCostPerSquareFoot;
    }

    @Override
    public String toString() {
        return "Product{" + "ProductType=" + ProductType + ", CostPerSquareFoot=" + CostPerSquareFoot + ", LaborCostPerSquareFoot=" + LaborCostPerSquareFoot + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.ProductType);
        hash = 59 * hash + Objects.hashCode(this.CostPerSquareFoot);
        hash = 59 * hash + Objects.hashCode(this.LaborCostPerSquareFoot);
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
        final Product other = (Product) obj;
        if (!Objects.equals(this.ProductType, other.ProductType)) {
            return false;
        }
        if (!Objects.equals(this.CostPerSquareFoot, other.CostPerSquareFoot)) {
            return false;
        }
        if (!Objects.equals(this.LaborCostPerSquareFoot, other.LaborCostPerSquareFoot)) {
            return false;
        }
        return true;
    }
    
    
    
}
