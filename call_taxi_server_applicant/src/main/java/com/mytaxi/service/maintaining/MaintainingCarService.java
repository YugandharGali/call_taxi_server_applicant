package com.mytaxi.service.maintaining;

import java.util.List;

import com.mytaxi.domainobject.CarDO;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;

public interface MaintainingCarService
{

    CarDO find(Long carId) throws EntityNotFoundException;

    CarDO create(CarDO CarDO) throws ConstraintsViolationException;

    void delete(Long carId) throws EntityNotFoundException;

    void updateCharacteristics(CarDO carDO) throws EntityNotFoundException;

    List<CarDO> findAvailableCars();
}
