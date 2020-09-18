package com.mohammad.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mohammad.exception.ResourceNotFoundException;
import com.mohammad.model.Employee;
import com.mohammad.repository.EmployeeRepository;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class EmployeeController {
	
	@Autowired
	private EmployeeRepository repository;
	
	//Getting all the records by rest api from database.
	@GetMapping("/employees")
	public List<Employee> getAllEmployees() {
		return repository.findAll();
	} 
	
	//Adding Employee details using restful services in database with postman and browser.
	@PostMapping("/employees")
	public Employee createEmployee(@RequestBody Employee employee) {
		return repository.save(employee);
	} 

	  //Getting all the records by rest api from database.
	  @GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") Long id) { 
		 Employee employee = repository.findById(id).orElseThrow(()-> new	ResourceNotFoundException("Employee not Exist by this id"+id)); 
		 return ResponseEntity.ok(employee);
	}
	 
	//Update employee by rest api from database.
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable("id") Long id, @RequestBody Employee employeeDetails) {
		
		Employee employee = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Employee not Exist by this id"+id));
		
		employee.setFirstname(employeeDetails.getFirstname());
		employee.setLastname(employeeDetails.getLastname());
		employee.setEmail(employeeDetails.getEmail());
		employee.setPassword(employeeDetails.getPassword());
		
		Employee updatedEmployee = repository.save(employee);
		
		return ResponseEntity.ok(updatedEmployee);
	}
	
	// Delete employee by rest api from database
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable("id") Long id){
		
		Employee employee = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Employee not Exist by this id"+id));
		repository.delete(employee);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted Successfully", Boolean.TRUE);
		return ResponseEntity.ok(response);
		
	}
	
	/*
	 * @GetMapping("/employees/{email}") public ResponseEntity<Employee>
	 * search(@PathVariable("email") String email) {
	 * 
	 * Employee searchEmployee = repository.findByEmail(email); return
	 * ResponseEntity.ok(searchEmployee); }
	 */
}
