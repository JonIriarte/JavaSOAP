package com.company.legacy.soap;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import com.company.legacy.dao.EmployeeDao;
import com.company.legacy.dao.RoleDao;
import com.company.legacy.model.Employee;
import com.company.legacy.model.Role;

@WebService(name = "EmployeeService", targetNamespace = "http://legacy.company.com/soap", portName = "EmployeeServicePort")
public class EmployeeService {

    private EmployeeDao employeeDao = new EmployeeDao();
    private RoleDao roleDao = new RoleDao();

    @WebMethod
    public List<Employee> getAllEmployees() throws Exception {

        List<Employee> employees = employeeDao.findAll();

        for (Employee e : employees) {
            if (e.getRole() != null) {
                Role fullRole = roleDao.findById(e.getRole().getId());
                e.setRole(fullRole);
            }
        }

        return employees;
    }

    @WebMethod
    public Employee getEmployeeById(@WebParam(name = "id") int id) {
        Employee e = employeeDao.findById(id);

        if (e != null && e.getRole() != null) {
            Role fullRole = roleDao.findById(e.getRole().getId());
            e.setRole(fullRole);
        }

        return e;

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
