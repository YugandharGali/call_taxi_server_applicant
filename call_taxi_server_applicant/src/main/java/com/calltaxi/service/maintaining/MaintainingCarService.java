package com.calltaxi.service.maintaining;

import java.util.List;

import com.calltaxi.domainobject.CarDO;
import com.calltaxi.exception.ConstraintsViolationException;
import com.calltaxi.exception.EntityNotFoundException;

public interface MaintainingCarService
{

    CarDO find(Long carId) throws EntityNotFoundException;

    CarDO create(CarDO CarDO) throws ConstraintsViolationException;

    void delete(Long carId) throws EntityNotFoundException;

    void updateCharacteristics(CarDO carDO) throws EntityNotFoundException;

    List<CarDO> findAvailableCars();
}
