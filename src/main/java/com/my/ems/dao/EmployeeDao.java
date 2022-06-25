package com.my.ems.dao;

import com.my.ems.common.entity.Employee;

import java.util.List;

public interface EmployeeDao {
    public void insertEmployee(Employee employee);

    List<Employee> AllEmployee();

    void deleteEmployeeById(int id);
}
