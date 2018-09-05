package com.calltaxi.dataaccessobject;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.calltaxi.domainobject.CarDO;
import com.calltaxi.domainobject.DriverDO;
import com.calltaxi.domainvalue.OnlineStatus;

/**
 * Database Access Object for driver table.
 * <p/>
 */
public interface DriverRepository extends CrudRepository<DriverDO, Long>, JpaSpecificationExecutor<DriverDO> {

	List<DriverDO> findByOnlineStatus(OnlineStatus onlineStatus);

	List<DriverDO> findByCarDO(CarDO carDO);

}
