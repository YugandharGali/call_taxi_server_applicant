package com.calltaxi.service.driver;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.calltaxi.dataaccessobject.CarRepository;
import com.calltaxi.dataaccessobject.DriverRepository;
import com.calltaxi.dataaccessobject.DriverSpecification;
import com.calltaxi.domainobject.CarDO;
import com.calltaxi.domainobject.DriverDO;
import com.calltaxi.domainvalue.CarStatus;
import com.calltaxi.domainvalue.GeoCoordinate;
import com.calltaxi.domainvalue.OnlineStatus;
import com.calltaxi.exception.CarAlreadyInUseException;
import com.calltaxi.exception.ConstraintsViolationException;
import com.calltaxi.exception.EntityNotFoundException;

/**
 * Service to encapsulate the link between DAO and controller and to have
 * business logic for some driver specific things.
 * <p/>
 */
@Service
public class DefaultDriverService implements DriverService {

	private static org.slf4j.Logger LOG = LoggerFactory.getLogger(DefaultDriverService.class);

	private final DriverRepository driverRepository;
	private final CarRepository carRepository;

	public DefaultDriverService(final DriverRepository driverRepository, final CarRepository carRepository) {
		this.driverRepository = driverRepository;
		this.carRepository = carRepository;
	}

	/**
	 * Selects a driver by id.
	 *
	 * @param driverId
	 * @return found driver
	 * @throws EntityNotFoundException
	 *             if no driver with the given id was found.
	 */
	@Override
	public DriverDO find(Long driverId) throws EntityNotFoundException {
		return findDriverChecked(driverId);
	}

	/**
	 * Creates a new driver.
	 *
	 * @param driverDO
	 * @return
	 * @throws ConstraintsViolationException
	 *             if a driver already exists with the given username, ... .
	 */
	@Override
	public DriverDO create(DriverDO driverDO) throws ConstraintsViolationException {
		DriverDO driver;
		try {
			driver = driverRepository.save(driverDO);
		} catch (DataIntegrityViolationException e) {
			LOG.warn("Some constraints are thrown due to driver creation", e);
			throw new ConstraintsViolationException(e.getMessage());
		}
		return driver;
	}

	/**
	 * Deletes an existing driver by id.
	 *
	 * @param driverId
	 * @throws EntityNotFoundException
	 *             if no driver with the given id was found.
	 */
	@Override
	@Transactional
	public void delete(Long driverId) throws EntityNotFoundException {
		DriverDO driverDO = findDriverChecked(driverId);
		driverDO.setDeleted(true);
	}

	/**
	 * Update the location for a driver.
	 *
	 * @param driverId
	 * @param longitude
	 * @param latitude
	 * @throws EntityNotFoundException
	 */
	@Override
	@Transactional
	public void updateLocation(long driverId, double longitude, double latitude) throws EntityNotFoundException {
		DriverDO driverDO = findDriverChecked(driverId);
		driverDO.setCoordinate(new GeoCoordinate(latitude, longitude));
	}

	/**
	 * Find all drivers by online state.
	 *
	 * @param onlineStatus
	 */
	@Override
	public List<DriverDO> find(OnlineStatus onlineStatus) {
		return driverRepository.findByOnlineStatus(onlineStatus);
	}

	private DriverDO findDriverChecked(Long driverId) throws EntityNotFoundException {
		DriverDO driverDO = driverRepository.findOne(driverId);
		if (driverDO == null) {
			throw new EntityNotFoundException("Could not find entity with id: " + driverId);
		}
		return driverDO;
	}

	@Override
	public DriverDO selectOrDeselectCar(long driverId, long carid, CarStatus carStatus)
			throws EntityNotFoundException, CarAlreadyInUseException {

		// Check Driver available.
		DriverDO driverDO = findDriverChecked(driverId);

		if (!"ONLINE".equalsIgnoreCase(driverDO.getOnlineStatus().toString())) {
			throw new EntityNotFoundException("Driver status is not ONLINE.");
		}

		// Check the car available.
		CarDO carDO = carRepository.checkCarAvailable(carid);
		if (carDO == null) {
			throw new EntityNotFoundException("Could not find car entity with id: " + carid);
		}

		if ("SELECT".equalsIgnoreCase(carStatus.toString())) {
			// Check driver already selected a car..
			if (driverDO.getCarDO() != null && driverDO.getCarDO().getCarid() != null) {
				throw new CarAlreadyInUseException("Car already in use by the driverid :" + driverId);
			}

			// Check Car in use.
			List<DriverDO> drivers = driverRepository.findByCarDO(carDO);
			if (drivers != null && drivers.size() > 0) {
				throw new CarAlreadyInUseException("Could not select the car which already in use : " + carid);
			} else {
				driverDO.setCarDO(carDO);
			}
		} else {
			// Validate Car is status already selected.
			if (driverDO.getCarDO() != null && carid != driverDO.getCarDO().getCarid()) {
				throw new EntityNotFoundException("Car is not selected by this driver to deselect.");
			}
			driverDO.setCarDO(null);
		}
		driverRepository.save(driverDO);
		return driverDO;
	}

	@Override
	public List<DriverDO> getDriverByCharacteristics(CarDO carDO) throws EntityNotFoundException {

		List<DriverDO> drivers = null;

		Specification<DriverDO> spec = DriverSpecification.getDriverSpecifications(carDO);
		drivers = driverRepository.findAll(spec);

		if (drivers == null || drivers.size() == 0) {
			throw new EntityNotFoundException("Could not find drivers with given characteristics.");
		}

		return drivers;
	}

}
