package com.my.ems.dao;

import java.util.List;
import com.my.ems.common.entity.Employee;
import com.my.ems.common.utils.MyDbUtil;

public class EmployeeDaoImpl implements EmployeeDao {
    @Override
    public void insertEmployee(Employee employee) {
        String sql = "insert into employee (name,sal,hire_date) values(?,?,?)";
        MyDbUtil.executeUpdate(sql,employee.getName(),employee.getSal(),employee.getHireDate());
    }

    @Override
    public List<Employee> AllEmployee() {
        String sql = "select id,name,sal,hire_date 'hireDate' from employee";
        return MyDbUtil.executeQuery(Employee.class,sql);
    }

    @Override
    public void deleteEmployeeById(int id) {
        String sql = "delete from employee where id = ?";
        MyDbUtil.executeUpdate(sql,id);
    }


}
