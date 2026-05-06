package com.company.legacy.soap;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;


import com.company.legacy.dao.EmployeeDao;
import com.company.legacy.model.Employee;


@WebService
public class EmployeeService {
    
    private EmployeeDao employeeDao = new EmployeeDao();

    @WebMethod
    public List<Employee> getAllEmployees() throws Exception {
        return employeeDao.findAll();
    }


}
