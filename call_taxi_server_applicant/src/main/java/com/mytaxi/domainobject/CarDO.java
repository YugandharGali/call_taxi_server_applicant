package com.mytaxi.domainobject;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "car", uniqueConstraints = @UniqueConstraint(name = "uc_carlicensePlate", columnNames = { "licensePlate" }))
public class CarDO {

	@Id
	@GeneratedValue
	private Long carid;

	@Column(nullable = false)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private ZonedDateTime dateCreated = ZonedDateTime.now();

	@Column(nullable = false)
	@NotNull(message = "License Plate can not be null!")
	private String licensePlate;

	@Column(nullable = false)
	private int seatCount;

	@Column(nullable = false)
	private String convertible;

	@Column(nullable = false)
	private int rating;

	@Column(nullable = false)
	private String engineType;
	
	@Column(nullable = false)
	private String manufacturer;

	@Column(nullable = false)
	private Boolean deleted = false;
	
	@OneToOne(fetch=FetchType.EAGER, mappedBy="carDO")
	private DriverDO driverDO;

	public CarDO() {
	}

	public CarDO(String licensePlate, int seatCount, String convertible, int rating, String engineType, String manufacturer) {
		this.licensePlate = licensePlate;
		this.seatCount = seatCount;
		this.convertible = convertible;
		this.rating = rating;
		this.engineType = engineType;
		this.deleted = false;
		this.manufacturer = manufacturer;
	}


	public Long getCarid() {
		return carid;
	}

	public void setCarid(Long carid) {
		this.carid = carid;
	}

	public DriverDO getDriverDO() {
		return driverDO;
	}

	public void setDriverDO(DriverDO driverDO) {
		this.driverDO = driverDO;
	}

	public ZonedDateTime getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(ZonedDateTime dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public int getSeatCount() {
		return seatCount;
	}

	public void setSeatCount(int seatCount) {
		this.seatCount = seatCount;
	}

	public String getConvertible() {
		return convertible;
	}

	public void setConvertible(String convertible) {
		this.convertible = convertible;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getEngineType() {
		return engineType;
	}

	public void setEngineType(String engineType) {
		this.engineType = engineType;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

}
