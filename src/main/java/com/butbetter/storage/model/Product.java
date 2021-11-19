package com.butbetter.storage.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Product {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private UUID uuid;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @OneToOne
    @Column(name = "address", nullable = false)
    private Address address;

    @Column(name = "delivery_time", nullable = false)
    private Date deliveryTime;

    @Column(name = "amount", nullable = false)
    private int amount;

    /**
     * Model of Product
     *
     * @param uuid         = identifier
     * @param name         = name of product
     * @param description  = description of product
     * @param price        = selling price
     * @param address      = product location
     * @param deliveryTime = time of delivery
     * @param amount       = amount of product
     */
    public Product(UUID uuid, String name, String description, BigDecimal price, Address address, Date deliveryTime, int amount) {
        this.uuid = uuid;
        this.name = name;
        this.description = description;
        this.price = price;
        this.address = address;
        this.deliveryTime = deliveryTime;
        this.amount = amount;
    }

    public Product() {

    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return amount == product.amount
                && Objects.equals(uuid, product.uuid)
                && Objects.equals(name, product.name)
                && Objects.equals(description, product.description)
                && Objects.equals(price, product.price)
                && Objects.equals(address, product.address)
                && Objects.equals(deliveryTime, product.deliveryTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, name, description, price, address, deliveryTime, amount);
    }

    @Override
    public String toString() {
        return "Product{" +
                "uuid=" + uuid +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", address=" + address +
                ", deliveryTime=" + deliveryTime +
                ", amount=" + amount +
                '}';
    }
}
