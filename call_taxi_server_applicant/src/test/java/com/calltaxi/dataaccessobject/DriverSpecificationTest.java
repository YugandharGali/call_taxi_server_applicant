package com.calltaxi.dataaccessobject;

import java.time.ZonedDateTime;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.jpa.domain.Specification;

import com.calltaxi.domainobject.CarDO;
import com.calltaxi.domainobject.DriverDO;

public class DriverSpecificationTest {
	CarDO carDO;;

	@Before
	public void setUp() throws Exception {
		carDO = new CarDO();
		carDO.setCarid(11l);
		carDO.setConvertible("N");
		carDO.setDateCreated(ZonedDateTime.now());
		carDO.setDriverDO(new DriverDO("User", "Password"));
		carDO.setEngineType("D");
		carDO.setLicensePlate("DU123");
		carDO.setManufacturer("BENZ");
		carDO.setRating(4);
		carDO.setSeatCount(6);
	}

	@After
	public void tearDown() throws Exception {
		carDO = null;
	}

	@Test
	public void test() {
		Specification<DriverDO> driverDOSpecList = DriverSpecification.getDriverSpecifications(carDO);
		
		org.junit.Assert.assertNotNull(driverDOSpecList);
		}

}
