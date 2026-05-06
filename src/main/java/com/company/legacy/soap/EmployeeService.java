package com.company.legacy.soap;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import com.company.legacy.dao.EmployeeDao;
import com.company.legacy.model.Employee;

@WebService(name = "EmployeeService", targetNamespace = "http://legacy.company.com/soap", portName = "EmployeeServicePort")
public class EmployeeService {

    private EmployeeDao employeeDao = new EmployeeDao();

    @WebMethod
    public List<Employee> getAllEmployees() throws Exception {
        return employeeDao.findAll();
    }

    @WebMethod
    public Employee getEmployeeById(@WebParam(name = "id") int id) {
        return employeeDao.findById(id);
    }

    @WebMethod
    public void addEmployee(@WebParam(name = "employee") Employee employee) {
        employeeDao.insert(employee);
    }

    @WebMethod
    public void updateEmployee(@WebParam(name = "employee") Employee employee) {
        employeeDao.update(employee);
    }

    @WebMethod
    public void deleteEmployee(@WebParam(name = "id") int id) {
        employeeDao.delete(id);
    }

}
