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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.calltaxi.controller.mapper.CarMapper;
import com.calltaxi.controller.mapper.DriverMapper;
import com.calltaxi.datatransferobject.CarDTO;
import com.calltaxi.datatransferobject.DriverDTO;
import com.calltaxi.domainobject.CarDO;
import com.calltaxi.domainobject.DriverDO;
import com.calltaxi.domainvalue.CarStatus;
import com.calltaxi.domainvalue.OnlineStatus;
import com.calltaxi.exception.CarAlreadyInUseException;
import com.calltaxi.exception.ConstraintsViolationException;
import com.calltaxi.exception.EntityNotFoundException;
import com.calltaxi.service.driver.DriverService;

/**
 * All operations with a driver will be routed by this controller.
 * <p/>
 */
@RestController
@RequestMapping("v1/drivers")
public class DriverController {

	private final DriverService driverService;

	@Autowired
	public DriverController(final DriverService driverService) {
		this.driverService = driverService;
	}

	@GetMapping("/{driverId}")
	public DriverDTO getDriver(@Valid @PathVariable long driverId) throws EntityNotFoundException {
		return DriverMapper.makeDriverDTO(driverService.find(driverId));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public DriverDTO createDriver(@Valid @RequestBody DriverDTO driverDTO) throws ConstraintsViolationException {
		DriverDO driverDO = DriverMapper.makeDriverDO(driverDTO);
		return DriverMapper.makeDriverDTO(driverService.create(driverDO));
	}

	@DeleteMapping("/{driverId}")
	public void deleteDriver(@Valid @PathVariable long driverId) throws EntityNotFoundException {
		driverService.delete(driverId);
	}

	@PutMapping("/{driverId}")
	public void updateLocation(@Valid @PathVariable long driverId, @RequestParam double longitude,
			@RequestParam double latitude) throws ConstraintsViolationException, EntityNotFoundException {
		driverService.updateLocation(driverId, longitude, latitude);
	}

	@GetMapping
	public List<DriverDTO> findDrivers(@RequestParam OnlineStatus onlineStatus)
			throws ConstraintsViolationException, EntityNotFoundException {
		return DriverMapper.makeDriverDTOList(driverService.find(onlineStatus));
	}

	@PutMapping("/selectOrDeselectCar/{driverId}")
	public DriverDTO selectOrDeselectCar(@Valid @PathVariable long driverId, @RequestParam long carId,
			@RequestParam CarStatus carStatus) throws EntityNotFoundException, CarAlreadyInUseException {
		DriverDO driverDO = driverService.selectOrDeselectCar(driverId, carId, carStatus);
		return DriverMapper.makeDriverDTO(driverDO);
	}

	@PostMapping("/byCharacteristics")
	public List<DriverDTO> getDriverByCharacteristics(@Valid @RequestBody CarDTO carDTO) throws EntityNotFoundException {
		CarDO carDO = CarMapper.makeCarDO(carDTO);
		List<DriverDO> drivers = driverService.getDriverByCharacteristics(carDO);
		return DriverMapper.makeDriverDTOList(drivers);
	}
	
}
