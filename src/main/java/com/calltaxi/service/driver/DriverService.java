package com.calltaxi.service.driver;

import java.util.List;

import com.calltaxi.domainobject.CarDO;
import com.calltaxi.domainobject.DriverDO;
import com.calltaxi.domainvalue.CarStatus;
import com.calltaxi.domainvalue.OnlineStatus;
import com.calltaxi.exception.CarAlreadyInUseException;
import com.calltaxi.exception.ConstraintsViolationException;
import com.calltaxi.exception.EntityNotFoundException;

public interface DriverService {

	DriverDO find(Long driverId) throws EntityNotFoundException;

	DriverDO create(DriverDO driverDO) throws ConstraintsViolationException;

	void delete(Long driverId) throws EntityNotFoundException;

	void updateLocation(long driverId, double longitude, double latitude) throws EntityNotFoundException;

	List<DriverDO> find(OnlineStatus onlineStatus);

	DriverDO selectOrDeselectCar(long driverId, long carId, CarStatus carStatus)
			throws EntityNotFoundException, CarAlreadyInUseException;

	List<DriverDO> getDriverByCharacteristics(CarDO carDO) throws EntityNotFoundException;

}
