package com.butbetter.storage.restApi.model;


import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "product_information")
public class ProductInformation implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", insertable = false, updatable = false, nullable = false)
	private UUID uuid;

	@Column(name = "delivery_time", nullable = false)
	private OffsetDateTime date;

	@Range(min = 0, max = 999999)
	@Column(name = "amount", nullable = false)
	private int amount;

	@ManyToOne()
	@JoinColumn(name = "address")
	private Address address;

	/**
	 * Model of ProductInformation
	 *
	 * @param date    = sent time of product
	 * @param amount  = amount of product
	 * @param address = address of product location
	 */
	public ProductInformation(OffsetDateTime date, int amount, Address address) {
		this.date = date;
		this.amount = amount;
		this.address = address;
	}

	public ProductInformation() {

	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public OffsetDateTime getDeliveryTime() {
		return date;
	}

	public void setDeliveryTime(OffsetDateTime date) {
		this.date = date;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		ProductInformation that = (ProductInformation) o;
		return amount == that.amount &&
				Objects.equals(uuid, that.uuid) &&
				Objects.equals(date, that.date) &&
				Objects.equals(address, that.address);
	}

	@Override
	public int hashCode() {
		return Objects.hash(uuid, date, amount, address);
	}

	@Override
	public String toString() {
		return "ProductInformation{" +
				"uuid=" + uuid +
				", deliveryTime=" + date +
				", amount=" + amount +
				", address=" + address +
				'}';
	}
}
