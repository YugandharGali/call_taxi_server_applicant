package com.calltaxi.dataaccessobject;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.calltaxi.domainobject.CarDO;
import com.calltaxi.domainobject.DriverDO;

public class DriverSpecification {
	
	private DriverSpecification() {}

	public static Specification<DriverDO> getDriverSpecifications(CarDO searchInfo) {
		return (root, query, cb) -> {
			Join<DriverDO, CarDO> driverCar = root.join("carDO", JoinType.LEFT);
			List<Predicate> predicates = new ArrayList<>();

			constructPredictions(searchInfo, cb, driverCar, predicates);

			Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
			
			if(p.length == 0) {
				return null;
			}else if(p.length == 1) {
				return p[0];
			}else {
				return cb.and(p);
			}
		};
	}

	private static void constructPredictions(CarDO searchInfo, CriteriaBuilder cb, Join<DriverDO, CarDO> driverCar,
			List<Predicate> predicates) {
		if (searchInfo.getLicensePlate() != null) {
			predicates.add(cb.equal(driverCar.get("licensePlate"), searchInfo.getLicensePlate()));
		}

		if (searchInfo.getSeatCount() > 0) {
			predicates.add(cb.equal(driverCar.get("seatCount"), searchInfo.getSeatCount()));
		}

		if (searchInfo.getConvertible() != null) {
			predicates.add(cb.equal(driverCar.get("convertible"), searchInfo.getConvertible()));
		}

		if (searchInfo.getRating() > 0) {
			predicates.add(cb.equal(driverCar.get("rating"), searchInfo.getRating()));
		}

		if (searchInfo.getEngineType() != null) {
			predicates.add(cb.equal(driverCar.get("engineType"), searchInfo.getEngineType()));
		}

		if (searchInfo.getManufacturer() != null) {
			predicates.add(cb.equal(driverCar.get("manufacturer"), searchInfo.getManufacturer()));
		}
	}

}
