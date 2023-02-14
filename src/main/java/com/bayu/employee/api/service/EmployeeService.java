package com.bayu.employee.api.service;

import com.bayu.employee.api.payload.CreateEmployeeRequest;
import com.bayu.employee.api.payload.EmployeeDTO;
import com.bayu.employee.api.payload.UpdateEmployeeRequest;

import java.util.List;

public interface EmployeeService {

    EmployeeDTO create(CreateEmployeeRequest createEmployeeRequest);

    List<EmployeeDTO> getAllEmployees();

    EmployeeDTO getEmployeeById(String id);

    EmployeeDTO getEmployeeByNik(String nik);

    EmployeeDTO getEmployeeByEmail(String email);

    EmployeeDTO update(String id, UpdateEmployeeRequest updateEmployeeRequest);

    void deleteById(String id);
}
