package com.calltaxi.datatransferobject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarDTO {

	@JsonIgnore
	private Long id;

	private String licensePlate;
	private int seatCount;
	private String convertible;
	private int rating;
	private String engineType;
	private String manufacturer;

	public CarDTO() {
	}

	private CarDTO(Long id, String licensePlate, int seatCount, String convertible, int rating, String engineType,
			String manufacturer) {
		this.id = id;
		this.licensePlate = licensePlate;
		this.seatCount = seatCount;
		this.convertible = convertible;
		this.rating = rating;
		this.engineType = engineType;
		this.manufacturer = manufacturer;
	}

	@JsonProperty
	public Long getId() {
		return id;
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

	public static CarDTOBuilder newBuilder() {
		return new CarDTOBuilder();
	}

	public static class CarDTOBuilder {
		private Long id;
		private String licensePlate;
		private int seatCount;
		private String convertible;
		private int rating;
		private String engineType;
		private String manufacturer;

		public CarDTOBuilder setId(Long id) {
			this.id = id;
			return this;
		}

		public CarDTOBuilder setLicensePlate(String licensePlate) {
			this.licensePlate = licensePlate;
			return this;
		}

		public CarDTOBuilder setSeatCount(int seatCount) {
			this.seatCount = seatCount;
			return this;
		}

		public CarDTOBuilder setCoordinate(String convertible) {
			this.convertible = convertible;
			return this;
		}

		public CarDTOBuilder setRating(int rating) {
			this.rating = rating;
			return this;
		}

		public CarDTOBuilder setEngineType(String engineType) {
			this.engineType = engineType;
			return this;
		}
		
		public CarDTOBuilder setManufacturer(String manufacturer) {
			this.manufacturer = manufacturer;
			return this;
		}

		public CarDTO createCarDTO() {
			return new CarDTO(id, licensePlate, seatCount, convertible, rating, engineType, manufacturer);
		}
	}
}
