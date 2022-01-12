package com.butbetter.storage.csvImport.model;


import com.butbetter.storage.csvImport.customConverter.BeanAddressCsvConverter;
import com.butbetter.storage.csvImport.customConverter.BeanOffsetDateTimeConverter;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "product_information_csv")
public class ProductInformationCsv implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", insertable = false, updatable = false, nullable = false)
	private UUID uuid;

	@CsvCustomBindByName(converter = BeanOffsetDateTimeConverter.class, column = "deliveryTime", required = true)
	@Column(name = "delivery_time", nullable = false)
	private OffsetDateTime date;

	@CsvBindByName(column = "amount", required = true)
	@Range(min = 0, max = 999999)
	@Column(name = "amount", nullable = false)
	private int amount;

	@CsvCustomBindByName(converter = BeanAddressCsvConverter.class, column = "address", required = true)
	@ManyToOne()
	@JoinColumn(name = "address")
	private AddressCsv address;

	/**
	 * Model of ProductInformation
	 *
	 * @param date    = sent time of product
	 * @param amount  = amount of product
	 * @param address = address of product location
	 */
	public ProductInformationCsv(OffsetDateTime date, int amount, AddressCsv address) {
		this.date = date;
		this.amount = amount;
		this.address = address;
	}

	public ProductInformationCsv() {

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

	public AddressCsv getAddress() {
		return address;
	}

	public void setAddress(AddressCsv address) {
		this.address = address;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		ProductInformationCsv that = (ProductInformationCsv) o;
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
