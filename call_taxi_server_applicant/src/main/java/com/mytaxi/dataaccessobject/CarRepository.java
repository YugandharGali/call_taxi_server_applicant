package com.mytaxi.dataaccessobject;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.mytaxi.domainobject.CarDO;

/**
 * Database Access Object for car table.
 * <p/>
 */
public interface CarRepository extends CrudRepository<CarDO, Long> {

	@Query("select car from CarDO car where car.deleted = 'false' " )
	List<CarDO> findAvailableCars();

	@Query("select car from CarDO car where car.id = :carId and car.deleted = 'false' " )
	CarDO checkCarAvailable(@Param("carId") long carId);
}
