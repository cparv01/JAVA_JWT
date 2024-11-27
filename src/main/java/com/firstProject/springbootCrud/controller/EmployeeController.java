package com.firstProject.springbootCrud.controller;

import com.firstProject.springbootCrud.entity.Employee;
import com.firstProject.springbootCrud.service.EmployeeService;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import com.firstProject.springbootCrud.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/")
    public String greet(HttpServletRequest request) {
    	return request.getSession().getId();
    }
    
    @GetMapping("/session-info")
    public ResponseEntity<String> getSessionInfo(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // Don't create a new session if one doesn't exist
        if (session != null) {
            String sessionId = session.getId();
            return ResponseEntity.ok("Session ID: " + sessionId);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No active session.");
        }
    }
    
    @GetMapping("/session-status")
    public String checkSession(HttpServletRequest request) {
        if (request.getSession(false) != null) {
            return "Session ID: " + request.getSession().getId();
        } else {
            return "No active session.";
        }
    }
    
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // Get the current session, but don't create a new one if it doesn't exist
        if (session != null) {
            session.invalidate();  // Invalidate the session explicitly
            return ResponseEntity.ok("Session has been invalidated. User logged out.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No active session.");
        }
    }
    
    @GetMapping("/csrf-token")
    public CsrfToken getCsrfToken(HttpServletRequest request) {
    	return (CsrfToken) request.getAttribute("_csrf");
    }
    
    @GetMapping("/show")
    public ResponseEntity<ResponseDTO> getAllEmployees() {
        return employeeService.getEmployee();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }

    @PostMapping("/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<ResponseDTO> createEmployee(@RequestBody Employee employee) {
        return employeeService.saveEmp(employee);
    }

    @PostMapping("/addAll")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<ResponseDTO> createEmployees(@RequestBody List<Employee> employees) {
        return employeeService.saveEmps(employees);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseDTO> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails) {
        return employeeService.updateEmployee(id, employeeDetails);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDTO> removeEmployee(@PathVariable Long id) {
        return employeeService.deleteEmployeeById(id);
    }
}
