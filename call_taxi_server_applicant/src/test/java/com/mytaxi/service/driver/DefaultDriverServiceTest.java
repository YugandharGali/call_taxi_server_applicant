package com.mytaxi.service.driver;


import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.type.descriptor.java.ZonedDateTimeJavaDescriptor;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;

import com.mytaxi.dataaccessobject.CarRepository;
import com.mytaxi.dataaccessobject.DriverRepository;
import com.mytaxi.dataaccessobject.DriverSpecification;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainvalue.CarStatus;
import com.mytaxi.domainvalue.OnlineStatus;
import com.mytaxi.exception.CarAlreadyInUseException;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;


public class DefaultDriverServiceTest {
	private DefaultDriverService defaultDriverService;
	private DriverRepository driverRepository;
	private CarRepository carRepository;
	DriverDO driverDO;
	@Before
	public void setUp() throws Exception {
		driverRepository = Mockito.mock(DriverRepository.class);
		carRepository = Mockito.mock(CarRepository.class);
		defaultDriverService =  new DefaultDriverService(driverRepository, carRepository);
		driverDO = new DriverDO("User", "Passwrod@123");
		driverDO.setDeleted(false);
		driverDO.setId(1234L);
		driverDO.setOnlineStatus(OnlineStatus.ONLINE);
	}

	@After
	public void tearDown() throws Exception {
		defaultDriverService = null;
		driverRepository = null;
		carRepository = null;
		driverDO=null;
	}

	@Test
	public void testfind() throws Exception {
		Long driverID = 1234L;
		Mockito.when(driverRepository.findOne(driverID)).thenReturn(driverDO);
		DriverDO driverDOResult = defaultDriverService.find(driverID);
		Assert.assertEquals(driverDO.getId(), driverDOResult.getId());
		Assert.assertEquals(driverDO.getDeleted(), driverDOResult.getDeleted());
		
	}
	@Test
	public void testcreate() throws Exception {
		Mockito.when(driverRepository.save(driverDO)).thenReturn(driverDO);
		DriverDO driverDOResult = defaultDriverService.create(driverDO);
		Assert.assertEquals(driverDO.getId(), driverDOResult.getId());
		Assert.assertEquals(driverDO.getDeleted(), driverDOResult.getDeleted());
		
	}
	
	@Test(expected=ConstraintsViolationException.class)
	public void testDataIntegrityViolationException() throws Exception {
		Mockito.when(driverRepository.save(driverDO)).thenThrow(new DataIntegrityViolationException(""));
		DriverDO driverDOResult = defaultDriverService.create(driverDO);
		
	}
	
	@Test
	public void testdelete() throws Exception {
		Long driverID = 1234L;
		Mockito.when(driverRepository.findOne(driverID)).thenReturn(driverDO);
		defaultDriverService.delete(driverID);
		Assert.assertEquals(driverDO.getDeleted(), true);
		
	}
	
	
	@Test
	public void testupdateLocation() throws Exception {
		Long driverID = 1234L;
		double longitude = 180;
		double lattitude = 90;
		Mockito.when(driverRepository.findOne(driverID)).thenReturn(driverDO);
		defaultDriverService.updateLocation(driverID,longitude,lattitude);
		Assert.assertNotNull(driverDO.getCoordinate());
		
	}
	

	@Test
	public void testFindOnlineStatus() throws Exception {
		Long driverID = 1234L;
		double longitude = 180;
		double lattitude = 90;
		List<DriverDO> driverDOList = new ArrayList<DriverDO>();
		driverDOList.add(driverDO);
		Mockito.when(driverRepository.findByOnlineStatus(OnlineStatus.ONLINE)).thenReturn(driverDOList);
		defaultDriverService.find(OnlineStatus.ONLINE);
		Assert.assertNotNull(driverDOList.get(0).getOnlineStatus());
		
	}
	
	
	@Test(expected=EntityNotFoundException.class)
	public void testupdateLocationThrowsException() throws Exception {
		Long driverID = 1234L;
		double longitude = 180;
		double lattitude = 90;
		Mockito.when(driverRepository.findOne(driverID)).thenReturn(null);
		defaultDriverService.updateLocation(driverID,longitude,lattitude);
		Assert.assertNotNull(driverDO.getCoordinate());
		
	}
	
	
	
	@Test(expected=EntityNotFoundException.class)
	public void testselectOrDeselectCar() throws Exception {
		Long driverID = 1234L;
		driverDO.setOnlineStatus(OnlineStatus.OFFLINE);		
		Mockito.when(driverRepository.findOne(driverID)).thenReturn(driverDO);
		DriverDO driverDO=defaultDriverService.selectOrDeselectCar(driverID,123l,CarStatus.SELECT);
		Assert.assertNull(driverDO);
		
	}
	
	@Test(expected=EntityNotFoundException.class)
	public void testselectOrDeselectinValidCar() throws Exception {
		Long driverID = 1234L;
		CarDO cardo = new CarDO();
		cardo.setCarid(1l);
		Mockito.when(driverRepository.findOne(driverID)).thenReturn(driverDO);
		 Mockito.when(carRepository.checkCarAvailable(1l)).thenReturn(cardo);
		 DriverDO driverDO = defaultDriverService.selectOrDeselectCar(driverID,112l,CarStatus.SELECT);
		
	}
	
	@Test
	public void testselectOrDeselectValidCar() throws Exception {
		Long driverID = 1234L;
		CarDO cardo = new CarDO();
		cardo.setCarid(1l);
		Mockito.when(driverRepository.findOne(driverID)).thenReturn(driverDO);
		 Mockito.when(carRepository.checkCarAvailable(1l)).thenReturn(cardo);
		 DriverDO driverDO = defaultDriverService.selectOrDeselectCar(driverID,1l,CarStatus.SELECT);
		 Assert.assertNotNull(driverDO);
		
	}
	
	@Test
	public void testSelectOrDeselectCar() throws Exception {
		Long driverID = 1234L;
		CarDO cardo = new CarDO();
		cardo.setCarid(1l);
		Mockito.when(driverRepository.findOne(driverID)).thenReturn(driverDO);
		 Mockito.when(carRepository.checkCarAvailable(1l)).thenReturn(cardo);
		 DriverDO driverDO = defaultDriverService.selectOrDeselectCar(driverID,1l,CarStatus.DESELECT);
		 Assert.assertNotNull(driverDO);
		
	}
	
	@Test(expected=EntityNotFoundException.class)
	public void testSelectOrDeselectCarWithAlreadySelectCarforDriver() throws Exception {
		Long driverID = 1234L;
		CarDO cardo = new CarDO();
		cardo.setCarid(12l);
		driverDO.setCarDO(cardo);
		Mockito.when(driverRepository.findOne(driverID)).thenReturn(driverDO);
		 Mockito.when(carRepository.checkCarAvailable(1l)).thenReturn(cardo);
		 DriverDO driverDO = defaultDriverService.selectOrDeselectCar(driverID,1l,CarStatus.DESELECT);
		 Assert.assertNotNull(driverDO);
		
	}
	
	@Test(expected=CarAlreadyInUseException.class)
	public void testCarAlreadyUseException() throws Exception {
		Long driverID = 1234L;
		CarDO cardo = new CarDO();
		cardo.setCarid(12l);
		driverDO.setCarDO(cardo);
		Mockito.when(driverRepository.findOne(driverID)).thenReturn(driverDO);
		 Mockito.when(carRepository.checkCarAvailable(1l)).thenReturn(cardo);
		 DriverDO driverDO = defaultDriverService.selectOrDeselectCar(driverID,1l,CarStatus.SELECT);
		 Assert.assertNotNull(driverDO);
		
	}
	
	@Test(expected=CarAlreadyInUseException.class)
	public void testCarAlreadyUseException1() throws Exception {
		Long driverID = 1234L;
		CarDO cardo = new CarDO();
		cardo.setCarid(12l);
		List<DriverDO> drivers  = new ArrayList<DriverDO>();
		DriverDO driverdo = new DriverDO("User1", "Password");
		drivers.add(driverdo);
		Mockito.when(driverRepository.findOne(driverID)).thenReturn(driverDO);
		 Mockito.when(carRepository.checkCarAvailable(1l)).thenReturn(cardo);
		 Mockito.when(driverRepository.findByCarDO(cardo)).thenReturn(drivers);
		 DriverDO driverDO = defaultDriverService.selectOrDeselectCar(driverID,1l,CarStatus.SELECT);
		 Assert.assertNotNull(driverDO);
		
	}
	
	@Test(expected=EntityNotFoundException.class)
	public void testgetDriverByCharacteristics() throws Exception {
		Long driverID = 1234L;
		CarDO cardo = new CarDO();
		cardo.setCarid(12l);
		List<DriverDO> drivers  = new ArrayList<DriverDO>();
		DriverDO driverdo = new DriverDO("User1", "Password");
		drivers.add(driverdo);
		
		Mockito.when(driverRepository.findAll( DriverSpecification.getDriverSpecifications(cardo))).thenReturn(drivers);
		
		defaultDriverService.getDriverByCharacteristics(cardo);
		 Assert.assertNotNull(driverDO);
		
	}
	
	@Test(expected=EntityNotFoundException.class)
	public void testgetDriverByCharacteristicsWithAll() throws Exception {
		Long driverID = 1234L;
		DriverDO driverdo = new DriverDO("User1", "Password");
		CarDO cardo = new CarDO();
		cardo.setCarid(12l);
		cardo.setConvertible("N");
		cardo.setDateCreated(ZonedDateTime.now());
		cardo.setDeleted(false);
		cardo.setDriverDO(driverDO);
		cardo.setEngineType("D");
		cardo.setLicensePlate("ABC1231212");
		cardo.setManufacturer("BENZ");
		cardo.setManufacturer("BENZ");
		cardo.setSeatCount(6);
		cardo.setRating(3);
		
		
		List<DriverDO> drivers  = new ArrayList<DriverDO>();
		drivers.add(driverDO);
		
		Mockito.when(driverRepository.findAll( DriverSpecification.getDriverSpecifications(cardo))).thenReturn(drivers);
		
		defaultDriverService.getDriverByCharacteristics(cardo);
		 Assert.assertNotNull(driverDO);
		
	}
	
	

}
