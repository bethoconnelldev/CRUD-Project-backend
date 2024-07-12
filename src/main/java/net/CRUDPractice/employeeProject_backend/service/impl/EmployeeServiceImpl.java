package net.CRUDPractice.employeeProject_backend.service.impl;

import lombok.AllArgsConstructor;
import net.CRUDPractice.employeeProject_backend.dto.EmployeeDto;
import net.CRUDPractice.employeeProject_backend.entity.Employee;
import net.CRUDPractice.employeeProject_backend.exception.ResourceNotFoundException;
import net.CRUDPractice.employeeProject_backend.mapper.EmployeeMapper;
import net.CRUDPractice.employeeProject_backend.repository.EmployeeRepository;
import net.CRUDPractice.employeeProject_backend.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {

        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
        Employee savedEmployee = employeeRepository.save(employee);
        return EmployeeMapper.mapToEmployeeDto(savedEmployee);
    }

    @Override
    public EmployeeDto getEmployeeById(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Could not find employee with id: " + employeeId));
        return EmployeeMapper.mapToEmployeeDto(employee);
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employeeList = employeeRepository.findAll();
//        List<EmployeeDto> employeeDtoList = new ArrayList<EmployeeDto>();
//        int i;
//        for(i=0; i< employeeList.size(); i++){
//            Employee currentEmployee = employeeList.get(i);
//            EmployeeDto currentEmployeeDto = EmployeeMapper.mapToEmployeeDto(currentEmployee);
//            employeeDtoList.add(currentEmployeeDto);
        return employeeList.stream().map((employee) -> EmployeeMapper.mapToEmployeeDto(employee)).collect(Collectors.toList());

    }

    @Override
    public EmployeeDto updateEmployee(Long employeeId, EmployeeDto updatedEmployee) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(
                () -> new ResourceNotFoundException("Could not find employee with id: " + employeeId));
        employee.setFirstName(updatedEmployee.getFirstName());
        employee.setLastName(updatedEmployee.getLastName());
        employee.setEmail(updatedEmployee.getEmail());

        Employee updatedEmployeeObject = employeeRepository.save(employee);

        return EmployeeMapper.mapToEmployeeDto(updatedEmployeeObject);
    }

    @Override
    public void deleteEmployee(Long employeeId) {
        employeeRepository.findById(employeeId).orElseThrow(
                () -> new RuntimeException("employee with id: " + employeeId + " not found"));
        employeeRepository.deleteById(employeeId);
    }
}
