package com.my.ems.service;

import com.my.ems.common.entity.Employee;
import com.my.ems.dao.EmployeeDao;
import com.my.ems.dao.EmployeeDaoImpl;

import java.util.List;

public class EmployeeServiceImpl implements EmployeeService {
    // dao层注入到service层
    private EmployeeDao dao = new EmployeeDaoImpl();

    @Override
    public void saveEmployee(Employee employee) {
        dao.insertEmployee(employee);
    }

    @Override
    public List<Employee> findAllEmployee() {
        return dao.AllEmployee();
    }

    @Override
    public void removeEmployeeByID(int id) {
        dao.deleteEmployeeById(id);
    }
}
