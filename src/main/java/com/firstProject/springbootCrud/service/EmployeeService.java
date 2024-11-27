package com.firstProject.springbootCrud.service;

import org.springframework.http.ResponseEntity;

import com.firstProject.springbootCrud.dto.ResponseDTO;
import com.firstProject.springbootCrud.entity.Employee;

import java.util.List;

public interface EmployeeService {

    ResponseEntity<ResponseDTO> saveEmp(Employee employee);

    ResponseEntity<ResponseDTO> saveEmps(List<Employee> employees);

    ResponseEntity<ResponseDTO> getEmployee();

    ResponseEntity<ResponseDTO> getEmployeeById(Long id);

    ResponseEntity<ResponseDTO> getEmployeeByEmail(String email);

    ResponseEntity<ResponseDTO> deleteEmployeeById(Long id);

	ResponseEntity<ResponseDTO> updateEmployee(Long id, Employee employeeDetails);

}









//
//@Service
//public class EmployeeService {
//
//    @Autowired
//    private EmployeeRepository crudRepo;
//
//    // Save a single employee
//    public Employee saveEmp(Employee employee) {
//        return crudRepo.save(employee);
//    }
//
//    // Save a list of employees
//    public List<Employee> saveEmps(List<Employee> employees) {
//        return crudRepo.saveAll(employees);
//    }
//
//    // Get all employees
//    public List<Employee> getEmployee() {
//        return crudRepo.findAll();
//    }
//
//    // Get employee by ID
//    public Employee getEmployeeId(Long id) {
//        return crudRepo.findById(id).orElse(null);
//    }
//    
//    public Employee getEmployeeByEmail(String email) {
//        return crudRepo.findByEmail(email).orElse(null);
//    }
//
//    // Delete employee by ID
//    public void deleteEmployeeById(Long id) {
//        crudRepo.deleteById(id);
//    }
//}
