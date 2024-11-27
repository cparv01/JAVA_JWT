package com.firstProject.springbootCrud.serviceimpl;

import com.firstProject.springbootCrud.entity.Employee;
import com.firstProject.springbootCrud.repos.EmployeeRepository;
import com.firstProject.springbootCrud.service.EmployeeService;
import com.firstProject.springbootCrud.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository crudRepo;

    private static final List<String> VALID_DEPARTMENTS = List.of("HR", "IT", "Finance", "Marketing");

    // Validate email format
    private boolean isEmailValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
        return email.matches(emailRegex);
    }

    private boolean isDepartmentValid(String department) {
        return VALID_DEPARTMENTS.stream()
                .anyMatch(validDept -> validDept.equalsIgnoreCase(department));
    }

    // Save a single employee
    @Override
    public ResponseEntity<ResponseDTO> saveEmp(Employee employee) {
        try {
            Employee existingUser= crudRepo.findByEmail(employee.getEmail());
            
        	if (!isEmailValid(employee.getEmail())) {
                return ResponseEntity.badRequest().body(new ResponseDTO("Invalid email format.", false, null));
            }
        	
        	if(null != existingUser) {
        		return ResponseEntity.badRequest().body(new ResponseDTO("Email  is already in use.", false, null));
        	}
        	if (!isDepartmentValid(employee.getDepartment())) {
                return ResponseEntity.badRequest().body(new ResponseDTO("Invalid department.", false, null));
            }

            Employee savedEmployee = crudRepo.save(employee);
            return ResponseEntity.ok(new ResponseDTO("Employee saved successfully", true, savedEmployee));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO("Error saving employee", false, null));
        }
    }

    // Save a list of employees
    @Override
    public ResponseEntity<ResponseDTO> saveEmps(List<Employee> employees) {
        try {
            for (Employee employee : employees) {
                // Perform validations
            	
            	Employee existingUser= crudRepo.findByEmail(employee.getEmail());
            	if(null != existingUser) {
            		return ResponseEntity.badRequest().body(new ResponseDTO("Email  is already in use.", false, null));
            	}
                if (!isEmailValid(employee.getEmail())) {
                    return ResponseEntity.badRequest().body(new ResponseDTO("Invalid email format for one or more employees.", false, null));
                }
                if (!isDepartmentValid(employee.getDepartment())) {
                    return ResponseEntity.badRequest().body(new ResponseDTO("Invalid department for one or more employees.", false, null));
                }
            }

            // Save all employees
            List<Employee> savedEmployees = crudRepo.saveAll(employees);
            return ResponseEntity.ok(new ResponseDTO("Employees saved successfully", true, savedEmployees));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO("Error saving employees", false, null));
        }
    }

    // Get all employees
    @Override
    public ResponseEntity<ResponseDTO> getEmployee() {
        try {
            List<Employee> employees = crudRepo.findAll();
            return ResponseEntity.ok(new ResponseDTO("Employees retrieved successfully", true, employees));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO("Error retrieving employees", false, null));
        }
    }

    // Get employee by ID
    @Override
    public ResponseEntity<ResponseDTO> getEmployeeById(Long id) {
        try {
            Employee employee = crudRepo.findById(id).orElse(null);
            if (employee == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDTO("Employee ID not found", false, null));
            }
            return ResponseEntity.ok(new ResponseDTO("Employee retrieved successfully", true, employee));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO("Error retrieving employee", false, null));
        }
    }

    // Get employee by email
//    @Override
//    public ResponseEntity<ResponseDTO> getEmployeeByEmail(String email) {
//        try {
//            Employee employee = crudRepo.findByEmail(email).orElse(null);
//            if (employee == null) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                        .body(new ResponseDTO("Employee Email not found", false, null));
//            }
//            return ResponseEntity.ok(new ResponseDTO("Employee retrieved successfully", true, employee));
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new ResponseDTO("Error retrieving employee", false, null));
//        }
//    }

    // Delete employee by ID
    @Override
    public ResponseEntity<ResponseDTO> deleteEmployeeById(Long id) {
        try {
            // Fetch employee by ID (to validate if the employee exists)
            Employee employee = crudRepo.findById(id).orElse(null);
            if (employee == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDTO("Employee ID not found", false, null));
            }
            crudRepo.deleteById(id);
            return ResponseEntity.ok(new ResponseDTO("Employee deleted successfully", true, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO("Error deleting employee", false, null));
        }
    }

    // Update employee
    @Override
    public ResponseEntity<ResponseDTO> updateEmployee(Long id, Employee employeeDetails) {
        try {
            // Perform validations
            if (!isEmailValid(employeeDetails.getEmail())) {
                return ResponseEntity.badRequest().body(new ResponseDTO("Invalid email format.", false, null));
            }
            if (!isDepartmentValid(employeeDetails.getDepartment())) {
                return ResponseEntity.badRequest().body(new ResponseDTO("Invalid department.", false, null));
            }

            // Check if employee exists
            ResponseEntity<ResponseDTO> response = getEmployeeById(id);
            if (response.getBody() != null && response.getBody().isSuccess()) {
                employeeDetails.setId(id);
                return saveEmp(employeeDetails);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO("Employee ID not found", false, null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO("Error updating employee", false, null));
        }
    }

	@Override
	public ResponseEntity<ResponseDTO> getEmployeeByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}
}
