package com.mytaxi.service.maintaining;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;

import com.mytaxi.dataaccessobject.CarRepository;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;

public class MaintainingCarServiceImplTest {

	MaintainingCarServiceImpl maintainingCarServiceImpl;
	CarRepository carRepository;
	CarDO cardo;
	@Before
	public void setUp() throws Exception {
		cardo = new CarDO();
		carRepository = Mockito.mock(CarRepository.class);
		maintainingCarServiceImpl =new MaintainingCarServiceImpl(carRepository);
		
	}

	@After
	public void tearDown() throws Exception {
		maintainingCarServiceImpl = null;
		carRepository = null;
		cardo=null;
	}

	@Test
	public void testCreate() throws Exception{
		cardo.setCarid(1l);
		Mockito.when(carRepository.save(cardo)).thenReturn(cardo);
	CarDO carDOResult=	maintainingCarServiceImpl.create(cardo);
	Assert.assertNotNull(carDOResult);
	}

	@Test(expected=ConstraintsViolationException.class)
	public void testCreatethrowsException() throws Exception{
		cardo.setCarid(1l);
		Mockito.when(carRepository.save(cardo)).thenThrow(DataIntegrityViolationException.class);
	maintainingCarServiceImpl.create(cardo);
	}
	
	@Test
	public void testFindCar() throws Exception{
		cardo.setCarid(1l);
		Mockito.when(carRepository.findOne(1l)).thenReturn(cardo);
	CarDO carDOResult=	maintainingCarServiceImpl.find(1l);
	Assert.assertNotNull(carDOResult);
	}
	@Test(expected=EntityNotFoundException.class)
	public void testFindinValidCar() throws Exception{
		cardo.setCarid(1l);
		Mockito.when(carRepository.findOne(1l)).thenReturn(null);
	CarDO carDOResult=	maintainingCarServiceImpl.find(1l);
	Assert.assertNotNull(carDOResult);
	}

	
	@Test
	public void testDeleteCar() throws Exception{
		cardo.setCarid(1l);
		cardo.setDeleted(false);
		Mockito.when(carRepository.findOne(1l)).thenReturn(cardo);
		maintainingCarServiceImpl.delete(1l);
		Assert.assertTrue(cardo.getDeleted());
	}
	
	
	
	@Test
	public void testupdateCharacteristics() throws Exception{
		cardo.setCarid(1l);
		Mockito.when(carRepository.findOne(1l)).thenReturn(cardo);
		maintainingCarServiceImpl.updateCharacteristics(cardo);
	}
	
	@Test
	public void testupdateAllCharacteristics() throws Exception{
		cardo.setCarid(1l);
		cardo.setLicensePlate("DU123");
		cardo.setConvertible("N");
		cardo.setDateCreated(ZonedDateTime.now());
		cardo.setDeleted(false);
		cardo.setDriverDO(new DriverDO("User", "Password"));
		cardo.setEngineType("D");
		cardo.setManufacturer("BMW");
		cardo.setRating(4);
		cardo.setSeatCount(5);
		CarDO carDOInput = new CarDO();
		carDOInput.setCarid(1l);
		Mockito.when(carRepository.findOne(1l)).thenReturn(carDOInput);
		maintainingCarServiceImpl.updateCharacteristics(cardo);
		
		Assert.assertEquals("BMW", carDOInput.getManufacturer());
		Assert.assertEquals("D", carDOInput.getEngineType());
		Assert.assertEquals(5, carDOInput.getSeatCount());
	}
	
	@Test
	public void testfindAvailableCars() throws Exception{
		List<CarDO> carDOList = new ArrayList<>();
		cardo.setCarid(1l);
		carDOList.add(cardo);
		Mockito.when(carRepository.findAvailableCars()).thenReturn(carDOList);
		List<CarDO> carDOResult = maintainingCarServiceImpl.findAvailableCars();
		Assert.assertNotNull(carDOResult);
	}
	
	
}
