package test;

import com.my.ems.common.entity.Employee;
import com.my.ems.service.EmployeeService;
import com.my.ems.service.EmployeeServiceImpl;

import java.util.Date;

public class mainTest {
    public static void main(String[] args) {
        EmployeeService employeeDao = new EmployeeServiceImpl();
        for (int i = 43; i < 50; i++) {
            employeeDao.saveEmployee(new Employee("员工"+i,88.44+i,new Date()));
        }
        System.out.println("ok");

    }
}
