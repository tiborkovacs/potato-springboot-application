package com.epam.potato.dao.entity.bag;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.epam.potato.dao.entity.supplier.SupplierEntity;

@Entity
@Table(name = "potatoBags")
public class PotatoBagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, unique = true)
    private long id;
    @Column(nullable = false, name = "number_of_potatoes")
    private int numberOfPotatoes;
    @JoinColumn(name = "supplier_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private SupplierEntity supplierEntity;
    @Column(name = "packed")
    @Temporal(TemporalType.TIMESTAMP)
    private Date packedDate;
    @Column(nullable = false)
    private double price;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getNumberOfPotatoes() {
        return numberOfPotatoes;
    }

    public void setNumberOfPotatoes(int numberOfPotatoes) {
        this.numberOfPotatoes = numberOfPotatoes;
    }

    public SupplierEntity getSupplierEntity() {
        return supplierEntity;
    }

    public void setSupplierEntity(SupplierEntity supplierEntity) {
        this.supplierEntity = supplierEntity;
    }

    public Date getPackedDate() {
        return packedDate;
    }

    public void setPackedDate(Date packedDate) {
        this.packedDate = packedDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
