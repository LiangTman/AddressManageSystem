package com.my.ems.web;


import com.my.ems.common.entity.Employee;
import com.my.ems.common.utils.DateUtils;
import com.my.ems.common.utils.JSONUtil;
import com.my.ems.service.EmployeeService;
import com.my.ems.service.EmployeeServiceImpl;
import framework.simplemvc.RequestPath;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.PrivateKey;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RequestPath("/emp")
public class EmpAction {
    //web层注入 service
    EmployeeService employeeService = new EmployeeServiceImpl();

    @RequestPath("/add.do")
    public String addEmployee(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");
        String salTxt = request.getParameter("sal");
        // 三目运算符
        double sal = (salTxt != null ? Double.parseDouble(salTxt) : 0);
        String hireDateStr = request.getParameter("hireDate");
        Date hireDate = DateUtils.parseStr2Date(hireDateStr);

        Employee employee = new Employee(name, sal, hireDate);
        employeeService.saveEmployee(employee);
        request.setAttribute("employee", employee); //存储数据,交给视图
        return "addEmpSuccess"; //视图
    }


    // 查询员工数据
    @RequestPath("/list.do")
    public String listEmp(HttpServletRequest request, HttpServletResponse response) {
        List<Employee> emps = employeeService.findAllEmployee();
        request.setAttribute("emps",emps);
        return "empList";
    }


    // 删除员工数据
    @RequestPath("/del.do")
    public void delEmp(HttpServletRequest request, HttpServletResponse response) {
        String idStr = request.getParameter("empId");
        int id = Integer.parseInt(idStr);
        employeeService.removeEmployeeByID(id);
        Map <String,Object> map = new HashMap<>();
        map.put("code",200);
        JSONUtil.printByJSON(response,map);
    }
}
