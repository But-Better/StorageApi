package com.butbetter.storage.model;

import com.opencsv.bean.CsvBindByName;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id", insertable = false, updatable = false, nullable = false)
	private UUID id;

	@CsvBindByName(column = "name", required = true)
	@Column(name = "name", nullable = false)
	private String name;

	@CsvBindByName(column = "companyName", required = true)
	@Column(name = "company_name")
	private String companyName;

	@CsvBindByName(column = "street", required = true)
	@Column(name = "street", nullable = false, columnDefinition = "TEXT")
	private String street;

	@CsvBindByName(column = "city", required = true)
	@Column(name = "city", nullable = false)
	private String city;

	@CsvBindByName(column = "postCode", required = true)
	@Column(name = "post_code", nullable = false, length = 10)
	private String postCode;

	@CsvBindByName(column = "country", required = true)
	@Column(name = "country", nullable = false)
	private String country;

	/**
	 * Model of Product
	 *
	 * @param id        = identifier
	 * @param name        = your name
	 * @param companyName = your company name
	 * @param street      = your street
	 * @param city        = your city
	 * @param postCode    = your postcode
	 * @param country     = your country
	 */
	public Address(UUID id, String name, String companyName, String street, String city, String postCode, String country) {
		this.id = id;
		this.name = name;
		this.companyName = companyName;
		this.street = street;
		this.city = city;
		this.postCode = postCode;
		this.country = country;
	}

	public Address(String name, String companyName, String street, String city, String postCode, String country) {
		this.name = name;
		this.companyName = companyName;
		this.street = street;
		this.city = city;
		this.postCode = postCode;
		this.country = country;
	}

	public Address() {

	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Address address = (Address) o;
		return Objects.equals(id, address.id)
				&& Objects.equals(name, address.name)
				&& Objects.equals(companyName, address.companyName)
				&& Objects.equals(street, address.street)
				&& Objects.equals(city, address.city)
				&& Objects.equals(postCode, address.postCode)
				&& Objects.equals(country, address.country);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, companyName, street, city, postCode, country);
	}

	@Override
	public String toString() {
		return "Address{" +
				"uuid=" + id +
				", name='" + name + '\'' +
				", companyName='" + companyName + '\'' +
				", street='" + street + '\'' +
				", city='" + city + '\'' +
				", postCode='" + postCode + '\'' +
				", country='" + country + '\'' +
				'}';
	}
}