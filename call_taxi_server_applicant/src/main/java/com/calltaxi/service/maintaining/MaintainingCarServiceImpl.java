package com.calltaxi.service.maintaining;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.calltaxi.dataaccessobject.CarRepository;
import com.calltaxi.domainobject.CarDO;
import com.calltaxi.exception.ConstraintsViolationException;
import com.calltaxi.exception.EntityNotFoundException;

/**
 * Service to encapsulate the link between DAO and controller and to have
 * business logic for some Car specific things.
 * <p/>
 */
@Service
public class MaintainingCarServiceImpl implements MaintainingCarService {

	private static org.slf4j.Logger LOG = LoggerFactory.getLogger(MaintainingCarServiceImpl.class);

	private final CarRepository carRepository;

	public MaintainingCarServiceImpl(final CarRepository carRepository) {
		this.carRepository = carRepository;
	}

	/**
	 * Selects a car by id.
	 *
	 * @param carId
	 * @return found car
	 * @throws EntityNotFoundException
	 */
	@Override
	public CarDO find(Long carId) throws EntityNotFoundException {
		return findCarChecked(carId);
	}

	public CarDO findCarChecked(Long carId) throws EntityNotFoundException {
		CarDO carDO = carRepository.findOne(carId);
		if (carDO == null) {
			throw new EntityNotFoundException("Could not find entity with id: " + carId);
		}
		return carDO;
	}

	/**
	 * Creates a new car.
	 *
	 * @param CarDO
	 * @return
	 * @throws ConstraintsViolationException
	 */
	@Override
	public CarDO create(CarDO carDO) throws ConstraintsViolationException {
		CarDO car;
		try {
			car = carRepository.save(carDO);
		} catch (DataIntegrityViolationException e) {
			LOG.warn("Some constraints are thrown due to car creation", e);
			throw new ConstraintsViolationException(e.getMessage());
		}
		return car;
	}

	/**
	 * Deletes an existing car by id.
	 *
	 * @param carId
	 * @throws EntityNotFoundException
	 * 
	 */
	@Override
	@Transactional
	public void delete(Long carId) throws EntityNotFoundException {
		CarDO carDO = findCarChecked(carId);
		carDO.setDeleted(true);
	}

	/**
	 * Update the car Characteristics.
	 *
	 * @throws EntityNotFoundException
	 */
	@Override
	@Transactional
	public void updateCharacteristics(CarDO car) throws EntityNotFoundException {

		// Check car exists..
		CarDO carDO = findCarChecked(car.getCarid());
		
		// Update only modified data..
		if (car.getLicensePlate() != null) {
			carDO.setLicensePlate(car.getLicensePlate());
		}
		if (car.getSeatCount() > 0) {
			carDO.setSeatCount(car.getSeatCount());
		}
		if (car.getConvertible() != null) {
			carDO.setConvertible(car.getConvertible());
		}
		if (car.getRating() > 0) {
			carDO.setRating(car.getRating());
		}
		if (car.getEngineType() != null) {
			carDO.setEngineType(car.getEngineType());
		}
		if (car.getManufacturer() != null) {
			carDO.setManufacturer(car.getManufacturer());
		}
	}

	/**
	 * Find all Cars which not deleted.
	 */
	@Override
	public List<CarDO> findAvailableCars() {
		return carRepository.findAvailableCars();
	}

}
