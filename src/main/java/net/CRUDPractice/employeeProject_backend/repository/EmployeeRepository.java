package net.CRUDPractice.employeeProject_backend.repository;

import net.CRUDPractice.employeeProject_backend.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
