package com.bayu.employee.api.controller;

import com.bayu.employee.api.payload.*;
import com.bayu.employee.api.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello World!!";
    }

    @PostMapping("/employees")
    public ResponseEntity<ApiResponse<EmployeeDTO>> create(@RequestBody CreateEmployeeRequest createEmployeeRequest) {
        EmployeeDTO employeeDTO = employeeService.create(createEmployeeRequest);
        ApiResponse<EmployeeDTO> apiResponse = ApiResponse.<EmployeeDTO>builder()
                .code(HttpStatus.CREATED.value())
                .success(Boolean.TRUE)
                .message("Successfully created new employee")
                .data(employeeDTO)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<ApiResponse<EmployeeDTO>> getEmployeeById(@PathVariable(name = "id") String id) {
        EmployeeDTO employeeDTO = employeeService.getEmployeeById(id);
        ApiResponse<EmployeeDTO> apiResponse = ApiResponse.<EmployeeDTO>builder()
                .code(HttpStatus.OK.value())
                .success(Boolean.TRUE)
                .message("Successfully retrieved employee by id : " + id)
                .data(employeeDTO)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/employees")
    public ResponseEntity<ApiResponse<List<EmployeeDTO>>> getAllEmployees() {
        List<EmployeeDTO> employees = employeeService.getAllEmployees();
        ApiResponse<List<EmployeeDTO>> apiResponse = ApiResponse.<List<EmployeeDTO>>builder()
                .code(HttpStatus.OK.value())
                .success(Boolean.TRUE)
                .message("Successfully retrieved all employees")
                .data(employees)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/employees/nik")
    public ResponseEntity<ApiResponse<EmployeeDTO>> getEmployeeByNik(@RequestParam(name = "nik") String nik) {
        EmployeeDTO employeeDTO = employeeService.getEmployeeByNik(nik);
        ApiResponse<EmployeeDTO> apiResponse = ApiResponse.<EmployeeDTO>builder()
                .code(HttpStatus.OK.value())
                .success(Boolean.TRUE)
                .message("Successfully retrieved employee with nik : " + nik)
                .data(employeeDTO)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/employees/email")
    public ResponseEntity<ApiResponse<EmployeeDTO>> getEmployeesByEmail(@RequestParam(name = "email") String email) {
        EmployeeDTO employeeDTO = employeeService.getEmployeeByEmail(email);
        ApiResponse<EmployeeDTO> apiResponse = ApiResponse.<EmployeeDTO>builder()
                .code(HttpStatus.OK.value())
                .success(Boolean.TRUE)
                .message("Successfully retrieved employee with email : " + email)
                .data(employeeDTO)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<ApiResponse<EmployeeDTO>> updateEmployee(@PathVariable(name = "id") String id,
                                                                   @RequestBody UpdateEmployeeRequest updateEmployeeRequest) {

        EmployeeDTO employeeDTO = employeeService.update(id, updateEmployeeRequest);
        ApiResponse<EmployeeDTO> apiResponse = ApiResponse.<EmployeeDTO>builder()
                .code(HttpStatus.CREATED.value())
                .success(Boolean.TRUE)
                .message("Successfully updated employee with id : " + id)
                .data(employeeDTO)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<MessageResponse> deleteEmployee(@PathVariable(name = "id") String id) {
        employeeService.deleteById(id);
        MessageResponse apiResponse = MessageResponse.builder()
                .code(HttpStatus.OK.value())
                .success(Boolean.TRUE)
                .message("Successfully delete employee with id : " + id)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

}
