package com.butbetter.storage.model;

import com.opencsv.bean.CsvBindByName;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;
import java.util.UUID;

public class Address {

	private UUID uuid;

	@CsvBindByName(column = "name", required = true)
	private String name;

	@CsvBindByName(column = "companyName", required = true)
	private String companyName;

	@CsvBindByName(column = "street", required = true)
	private String street;

	@CsvBindByName(column = "city", required = true)
	private String city;

	@CsvBindByName(column = "postCode", required = true)
	private String postCode;

	@CsvBindByName(column = "country", required = true)
	private String country;

	/**
	 * Model of Product
	 *
	 * @param uuid        = identifier
	 * @param name        = your name
	 * @param companyName = your company name
	 * @param street      = your street
	 * @param city        = your city
	 * @param postCode    = your postcode
	 * @param country     = your country
	 */
	public Address(UUID uuid, String name, String companyName, String street, String city, String postCode, String country) {
		this.uuid = uuid;
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
		return Objects.equals(uuid, address.uuid)
				&& Objects.equals(name, address.name)
				&& Objects.equals(companyName, address.companyName)
				&& Objects.equals(street, address.street)
				&& Objects.equals(city, address.city)
				&& Objects.equals(postCode, address.postCode)
				&& Objects.equals(country, address.country);
	}

	@Override
	public int hashCode() {
		return Objects.hash(uuid, name, companyName, street, city, postCode, country);
	}

	@Override
	public String toString() {
		return "Address{" +
				"uuid=" + uuid +
				", name='" + name + '\'' +
				", companyName='" + companyName + '\'' +
				", street='" + street + '\'' +
				", city='" + city + '\'' +
				", postCode='" + postCode + '\'' +
				", country='" + country + '\'' +
				'}';
	}
}