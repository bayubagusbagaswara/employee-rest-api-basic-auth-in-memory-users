package com.bayu.employee.api.service.impl;

import com.bayu.employee.api.exception.ResourceAlreadyExistsException;
import com.bayu.employee.api.exception.ResourceNotFoundException;
import com.bayu.employee.api.model.Employee;
import com.bayu.employee.api.payload.CreateEmployeeRequest;
import com.bayu.employee.api.payload.EmployeeDTO;
import com.bayu.employee.api.payload.UpdateEmployeeRequest;
import com.bayu.employee.api.repository.EmployeeRepository;
import com.bayu.employee.api.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public EmployeeDTO create(CreateEmployeeRequest createEmployeeRequest) {
        checkNikIsExists(createEmployeeRequest.getNik());
        checkEmailIsExists(createEmployeeRequest.getEmail());

        Employee employee = new Employee();
        employee.setNik(createEmployeeRequest.getNik());
        employee.setName(createEmployeeRequest.getName());
        employee.setEmail(createEmployeeRequest.getEmail());
        employee.setAddress(createEmployeeRequest.getAddress());

        employeeRepository.save(employee);

        return mapToEmployeeDTO(employee);
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        return mapToEmployeeDTOList(employeeRepository.findAll());
    }

    @Override
    public EmployeeDTO getEmployeeById(String id) {
        return mapToEmployeeDTO(employeeRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id : " + id))
        );
    }

    @Override
    public EmployeeDTO getEmployeeByNik(String nik) {
        return mapToEmployeeDTO(employeeRepository.findByNik(nik)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with NIK : " + nik))
        );
    }

    @Override
    public EmployeeDTO getEmployeeByEmail(String email) {
        return mapToEmployeeDTO(employeeRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with email : " + email))
        );
    }

    @Override
    public EmployeeDTO update(String id, UpdateEmployeeRequest updateEmployeeRequest) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id : " + id));

        checkNikIsExists(updateEmployeeRequest.getNik());
        checkEmailIsExists(updateEmployeeRequest.getEmail());

        if (!updateEmployeeRequest.getNik().isEmpty()) {
            employee.setNik(updateEmployeeRequest.getNik());
        }
        if (!updateEmployeeRequest.getName().isEmpty()) {
            employee.setName(updateEmployeeRequest.getName());
        }

        if (!updateEmployeeRequest.getEmail().isEmpty()) {
            employee.setEmail(updateEmployeeRequest.getEmail());
        }

        if (!updateEmployeeRequest.getAddress().isEmpty()) {
            employee.setAddress(updateEmployeeRequest.getAddress());
        }

        employeeRepository.save(employee);

        return mapToEmployeeDTO(employee);
    }

    @Override
    public void deleteById(String id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id : " + id));

        employeeRepository.delete(employee);
    }

    public void checkNikIsExists(String nik) {
        if (employeeRepository.existsByNik(nik)) {
            throw new ResourceAlreadyExistsException("NIK already exists");
        }
    }

    public void checkEmailIsExists(String email) {
        if (employeeRepository.existsByEmail(email)) {
            throw new ResourceAlreadyExistsException("Email already exists");
        }
    }

    public EmployeeDTO mapToEmployeeDTO(Employee employee) {
        return EmployeeDTO.builder()
                .id(employee.getId())
                .nik(employee.getNik())
                .name(employee.getName())
                .email(employee.getEmail())
                .address(employee.getAddress())
                .build();
    }

    public List<EmployeeDTO> mapToEmployeeDTOList(List<Employee> employees) {
        return employees.stream()
                .map(this::mapToEmployeeDTO)
                .collect(Collectors.toList());
    }

}
