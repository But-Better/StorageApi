package com.butbetter.storage.model;

import com.butbetter.storage.customConverter.BeanAddressConverter;
import com.butbetter.storage.customConverter.BeanOffsetDateTimeConverter;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
public class ProductInformation {

	@Id
	@GeneratedValue
	private UUID uuid;

	@CsvCustomBindByName(converter = BeanOffsetDateTimeConverter.class, column = "deliveryTime", required = true)
	private OffsetDateTime deliveryTime;

	@Column(name = "amount", nullable = false)
	@CsvBindByName(column = "amount", required = true)
	private int amount;

	@OneToOne
	@Column(name = "address", nullable = false)
	@CsvCustomBindByName(converter = BeanAddressConverter.class, column = "address", required = true)
	private Address address;

	/**
	 * Model of ProductInformation
	 *
	 * @param deliveryTime = sent time of product
	 * @param amount       = amount of product
	 * @param address      = address of product location
	 */
	public ProductInformation(OffsetDateTime deliveryTime, int amount, Address address) {
		this.deliveryTime = deliveryTime;
		this.amount = amount;
		this.address = address;
	}

	public ProductInformation() {}

	public UUID getUuid() {
		return uuid;
	}

	public OffsetDateTime getDeliveryTime() {
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

	public void setDeliveryTime(OffsetDateTime deliveryTime) {
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
				&& address.equals(that.address);
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