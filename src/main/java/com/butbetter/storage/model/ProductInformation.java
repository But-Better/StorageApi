package com.butbetter.storage.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
public class ProductInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id", insertable = false, updatable = false, nullable = false)
    private UUID uuid;

    @Column(name = "delivery_time", nullable = false)
    private Date deliveryTime;

    @Column(name = "amount", nullable = false)
    private int amount;

    @ManyToOne
    @Column(name = "address", nullable = false)
    private Address address;

    /**
     * Model of ProductInformation
     *
     * @param deliveryTime = sent time of product
     * @param amount       = amount of product
     * @param address      = address of product location
     */
    public ProductInformation(Date deliveryTime, int amount, Address address) {
        this.deliveryTime = deliveryTime;
        this.amount = amount;
        this.address = address;
    }

    public ProductInformation() {

    }

    public UUID getUuid() {
        return uuid;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public int getAmount() {
        return amount;
    }

    public Address getAddress() {
        return address;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductInformation that = (ProductInformation) o;
        return amount == that.amount
                && Objects.equals(uuid, that.uuid)
                && Objects.equals(deliveryTime, that.deliveryTime)
                && Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, deliveryTime, amount, address);
    }

    @Override
    public String toString() {
        return "ProductInformation{" +
                "uuid=" + uuid +
                ", deliveryTime=" + deliveryTime +
                ", amount=" + amount +
                ", address=" + address +
                '}';
    }
}
