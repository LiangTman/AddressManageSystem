package com.my.ems.service;

import com.my.ems.common.entity.Employee;

import java.util.List;

public interface EmployeeService {
    public void saveEmployee(Employee employee);

    public List<Employee> findAllEmployee();

    void removeEmployeeByID(int id);
}
