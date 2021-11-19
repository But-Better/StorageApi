package com.butbetter.storage.model;

import javax.persistence.*;
import java.math.BigDecimal;
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
    @Column(name = "product_information", nullable = false)
    private ProductInformation productInformation;

    /**
     * Model of Product
     *
     * @param uuid               = identifier
     * @param name               = name of product
     * @param description        = description of product
     * @param price              = selling price
     * @param productInformation = productInformation location
     */
    public Product(UUID uuid, String name, String description, BigDecimal price, ProductInformation productInformation) {
        this.uuid = uuid;
        this.name = name;
        this.description = description;
        this.price = price;
        this.productInformation = productInformation;
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

    public ProductInformation getProductInformation() {
        return productInformation;
    }

    public void setProductInformation(ProductInformation productInformation) {
        this.productInformation = productInformation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(uuid, product.uuid)
                && Objects.equals(name, product.name)
                && Objects.equals(description, product.description)
                && Objects.equals(price, product.price)
                && Objects.equals(productInformation, product.productInformation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, name, description, price, productInformation);
    }

    @Override
    public String toString() {
        return "Product{" +
                "uuid=" + uuid +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", productInformation=" + productInformation +
                '}';
    }
}
