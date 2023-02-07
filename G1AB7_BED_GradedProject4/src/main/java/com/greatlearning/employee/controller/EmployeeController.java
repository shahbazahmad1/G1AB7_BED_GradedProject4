package com.greatlearning.employee.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.greatlearning.employee.entity.Employee;
import com.greatlearning.employee.entity.Role;
import com.greatlearning.employee.entity.User;
import com.greatlearning.employee.repository.EmployeeRepository;
import com.greatlearning.employee.service.EmployeeService;
import javassist.NotFoundException;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

	@Autowired
	EmployeeService service;

	@Autowired
	EmployeeRepository employeeRepository;

	@PostMapping("/addEmployee")
	public String addSingleEmployee(@RequestBody Employee employee) {
		return service.addSingleEmployee(employee);
	}

	@PostMapping("/addAllEmployees")
	public String addMultipleEmployees(@RequestBody List<Employee> employee) {
		return service.addAllEmployees(employee);
	}

	@PostMapping("/addRole")
	public String addRole(@RequestBody Role role) {
		return service.addRole(role);
	}

	@PostMapping("/addUser")
	public String AddUser(@RequestBody User user) {
		return service.addUser(user);
	}

	@GetMapping("/getAllUsers")
	public List<User> getAllUser() {
		return service.getAllUsers();
	}

	@GetMapping("/getAllEmployees")
	public List<Employee> getAllEmployees() {
		return service.getAllEmployees();
	}

	@GetMapping("/getEmployeeById")
	public Optional<Employee> getEmployeeById(Integer id) {
		return service.getEmployeeById(id);
	}

	@GetMapping("/sort")
	public List<Employee> sortById(Direction order) {
		return service.SortById(order);
	}

	@GetMapping("/sortByName")
	public List<Employee> sortByName(Direction order) {
		return service.SortedByName(order);
	}

	@DeleteMapping("/deleteById")
	public String deleteById(Integer id) {
		return service.deleteEmployeeById(id);
	}

	@GetMapping("/search")
	public List<Employee> searchByName(@RequestParam("name") String name) {
		return employeeRepository.findByfirstNameContainsAllIgnoreCase(name);
	}

	@PutMapping("/edit/{id}")
	public Employee updateEmployee(@PathVariable("id") int id, @RequestBody Employee employee)
			throws NotFoundException {
		Employee currentEmp = employeeRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Employee not found"));
		currentEmp.setFirstName(employee.getFirstName());
		currentEmp.setLastName(employee.getLastName());
		currentEmp.setEmail(employee.getEmail());
		return employeeRepository.save(currentEmp);
	}
}
