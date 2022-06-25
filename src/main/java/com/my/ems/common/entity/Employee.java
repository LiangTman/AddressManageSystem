package com.my.ems.common.entity;


import com.my.ems.common.utils.DateUtils;

import java.util.Date;

public class Employee {

  private long id;
  private String name;
  private double sal;
  private Date hireDate;

  private String hireDateTxt;

  public String getHireDateTxt() {
    if(this.hireDate !=null){
        return DateUtils.parseDate2Str(this.hireDate);
    }
    return null;
  }

  public Employee() {

  }

  public Employee(String name, double sal, Date hireDate) {
    this.name = name;
    this.sal = sal;
    this.hireDate = hireDate;
  }


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public double getSal() {
    return sal;
  }

  public void setSal(double sal) {
    this.sal = sal;
  }


  public Date getHireDate() {
    return hireDate;
  }

  public void setHireDate(Date hireDate) {
    this.hireDate = hireDate;
  }
}
