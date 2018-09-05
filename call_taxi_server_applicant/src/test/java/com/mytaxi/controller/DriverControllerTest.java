package com.mytaxi.controller;

import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.mytaxi.controller.mapper.DriverMapper;
import com.mytaxi.datatransferobject.DriverDTO;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainvalue.GeoCoordinate;
import com.mytaxi.service.driver.DriverService;

public class DriverControllerTest {

	private DriverController driverController;
	DriverDTO driverDTO;
	DriverDO driverDO;
	GeoCoordinate geoCoordinate;

	private DriverService driverService;

	@Before
	public void setUp() throws Exception {
		driverService = Mockito.mock(DriverService.class);
		geoCoordinate = new GeoCoordinate(90, 122);
		driverDTO = new DriverDTO(1l, "User", "Passwrod@123", geoCoordinate, 11l);
		driverDO = new DriverDO("User", "Passwrod@123");
		driverController = new DriverController(driverService);
	}

	@After
	public void tearDown() throws Exception {
		driverService = null;
		driverController = null;
		driverDO = null;
		driverDTO = null;
	}

	@Test
	public void testGetDriver() throws Exception {
		given(this.driverService.find(4L)).willReturn(new DriverDO("user", "pass"));
		DriverDTO driverDTO = driverController.getDriver(4L);
		assertNotNull(driverDTO);

		Assert.assertEquals("user", driverDTO.getUsername());
	}
	
	/*@Test
	public void testcreateDriver() throws Exception {
		
		driverDO.setId(11l);
		driverDO.setCoordinate(geoCoordinate);
		driverDO.setDeleted(false);
		Mockito.when(driverService.create(driverDO)).thenReturn(driverDO);
		DriverDTO driverDTOResult = driverController.createDriver(driverDTO);
		assertNotNull(driverDTOResult);

		Assert.assertEquals("User", driverDTOResult.getUsername());
	}*/
	
	@Test
	public void testMakeDriverDTOList() throws Exception {
		List<DriverDO> driverList  = new ArrayList<>();
		driverList.add(driverDO);
		List<DriverDTO> driverDTOResult = DriverMapper.makeDriverDTOList(driverList);
		Assert.assertNotNull(driverDTOResult);
	}

}
