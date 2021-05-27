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
public class Tax {
    private String StateAbbreviation;
    private String StateName;
    private BigDecimal TaxRate;

    public Tax(String StateAbbreviation, String StateName, BigDecimal TaxRate) {
        this.StateAbbreviation = StateAbbreviation;
        this.StateName = StateName;
        this.TaxRate = TaxRate;
    }

    public String getStateAbbreviation() {
        return StateAbbreviation;
    }

    public void setStateAbbreviation(String StateAbbreviation) {
        this.StateAbbreviation = StateAbbreviation;
    }

    public String getStateName() {
        return StateName;
    }

    public void setStateName(String StateName) {
        this.StateName = StateName;
    }

    public BigDecimal getTaxRate() {
        return TaxRate;
    }

    public void setTaxRate(BigDecimal TaxRate) {
        this.TaxRate = TaxRate;
    }

    @Override
    public String toString() {
        return "Tax{" + "StateAbbreviation=" + StateAbbreviation + ", StateName=" + StateName + ", TaxRate=" + TaxRate + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        
        hash = 79 * hash + Objects.hashCode(this.StateAbbreviation);
        hash = 79 * hash + Objects.hashCode(this.StateName);
        hash = 79 * hash + Objects.hashCode(this.TaxRate);
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
        final Tax other = (Tax) obj;
        
        if (!Objects.equals(this.StateAbbreviation, other.StateAbbreviation)) {
            return false;
        }
        if (!Objects.equals(this.StateName, other.StateName)) {
            return false;
        }
        if (!Objects.equals(this.TaxRate, other.TaxRate)) {
            return false;
        }
        return true;
    }
}
