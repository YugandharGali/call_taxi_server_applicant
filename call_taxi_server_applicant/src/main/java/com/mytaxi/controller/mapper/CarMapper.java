package com.mytaxi.controller.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.mytaxi.datatransferobject.CarDTO;
import com.mytaxi.domainobject.CarDO;

/**
 * 
 * This class used to convert DTO to DO object vies versa.
 *
 */
public class CarMapper {

	private CarMapper() {}
	
	public static CarDO makeCarDO(CarDTO carDTO) {
		return new CarDO(carDTO.getLicensePlate(), carDTO.getSeatCount(), carDTO.getConvertible(), carDTO.getRating(),
				carDTO.getEngineType(), carDTO.getEngineType());
	}

	public static CarDTO makeCarDTO(CarDO carDO) {
		CarDTO.CarDTOBuilder carDTOBuilder = CarDTO.newBuilder().setId(carDO.getCarid())
				.setLicensePlate(carDO.getLicensePlate()).setSeatCount(carDO.getSeatCount())
				.setCoordinate(carDO.getConvertible()).setRating(carDO.getRating())
				.setEngineType(carDO.getEngineType()).setManufacturer(carDO.getManufacturer());

		return carDTOBuilder.createCarDTO();
	}

	public static List<CarDTO> makeCarDTOList(Collection<CarDO> drivers) {
		return drivers.stream().map(CarMapper::makeCarDTO).collect(Collectors.toList());
	}
}
