package com.calltaxi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.calltaxi.controller.mapper.CarMapper;
import com.calltaxi.datatransferobject.CarDTO;
import com.calltaxi.domainobject.CarDO;
import com.calltaxi.exception.ConstraintsViolationException;
import com.calltaxi.exception.EntityNotFoundException;
import com.calltaxi.service.maintaining.MaintainingCarService;

/**
 * All operations with a maintaining cars will be routed by this controller.
 * 
 */
@RestController
@RequestMapping("v1/maintainingCars")
public class MaintainingCarsController {

	private final MaintainingCarService maintainingCarService;

	@Autowired
	public MaintainingCarsController(final MaintainingCarService maintainingCarService) {
		this.maintainingCarService = maintainingCarService;
	}

	@GetMapping("/{carId}")
	public CarDTO getCar(@Valid @PathVariable long carId) throws EntityNotFoundException {
		return CarMapper.makeCarDTO(maintainingCarService.find(carId));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CarDTO createCar(@Valid @RequestBody CarDTO carDTO) throws ConstraintsViolationException {
		CarDO carDO = CarMapper.makeCarDO(carDTO);
		return CarMapper.makeCarDTO(maintainingCarService.create(carDO));
	}

	@DeleteMapping("/{carId}")
	public void deleteCar(@Valid @PathVariable long carId) throws EntityNotFoundException {
		maintainingCarService.delete(carId);
	}

	@PutMapping("/{carId}")
	public void updateCharacteristics(@Valid @PathVariable long carId, @RequestBody CarDTO carDTO)
			throws ConstraintsViolationException, EntityNotFoundException {
		CarDO carDO = CarMapper.makeCarDO(carDTO);
		carDO.setCarid(carId);
		maintainingCarService.updateCharacteristics(carDO);
	}

	@GetMapping
	public List<CarDTO> findAvailableCars() throws ConstraintsViolationException, EntityNotFoundException {
		return CarMapper.makeCarDTOList(maintainingCarService.findAvailableCars());
	}

}
