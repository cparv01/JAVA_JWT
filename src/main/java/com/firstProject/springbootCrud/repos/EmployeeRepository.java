package com.firstProject.springbootCrud.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.firstProject.springbootCrud.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	Employee findByEmail(String email);
	
	boolean existsByEmail(String email);
	
}
 